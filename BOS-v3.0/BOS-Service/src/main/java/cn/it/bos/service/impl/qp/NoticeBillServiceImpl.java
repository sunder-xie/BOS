package cn.it.bos.service.impl.qp;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import cn.it.bos.domain.bc.DecidedZone;
import cn.it.bos.domain.bc.Staff;
import cn.it.bos.domain.bc.Subarea;
import cn.it.bos.domain.qp.NoticeBill;
import cn.it.bos.domain.qp.WorkBill;
import cn.it.bos.service.base.FacadeService;
import cn.it.bos.service.base.impl.BaseServiceImpl;
import cn.it.bos.service.qp.NoticeBillService;
import cn.it.crm.domain.Customer;
import cn.it.crm.service.CustomerService;

@SuppressWarnings("all")
@Service("noticeBillService")
public class NoticeBillServiceImpl extends BaseServiceImpl implements NoticeBillService {

	@Resource(name = "customerService")
	private CustomerService customerService;
	@Autowired
	private FacadeService facadeService;

	/*
	 * 保存业务通知单， 1，自动分担，查询客户id去crm中查询，如果查询到用户，并且用户关联取派员id存在，设置该单自动关联该取派员
	 * 2，查询通知单上地址的关键字，去分区设置中自动匹配取派员，关键字完全匹配，匹配上自动关联，分派该单给取派员 3，人工分担，设置取派员。
	 */
	@Override
	@CacheEvict(allEntries=true,value="noticeBill")
	public void save(NoticeBill noticeBill) {
		// 利用快照特性
		facadeService.getNoticeBillDao().save(noticeBill);
		WorkBill workBill = new WorkBill();
		// 业务单录入客户编号不为空时候才能去查询crm系统
		if (StringUtils.isNotBlank(noticeBill.getCustomerId())) {
			Customer customer = customerService.findCustomerById(noticeBill.getCustomerId());
			if (customer != null && StringUtils.isNotBlank(customer.getDecidedzoneid())) {
				// 查询出来关联的定区id，
				String decidedZoneId = customer.getDecidedzoneid();
				DecidedZone decidedZone = facadeService.getDecidedZoneDao().findOne(decidedZoneId);
				Staff staff = decidedZone.getStaff();
				if (staff != null) {
					noticeBill.setStaff(staff);
					workBill.setStaff(staff);
					setData(noticeBill, workBill, "自动", "新", 0, "新单");
				}
			} else {
				matchingByAddresskey(noticeBill, workBill);
			}
		} else {
			// 业务单录入客户编号为空 去进行地址匹配，抽取出来方法
			matchingByAddresskey(noticeBill, workBill);

		}
	}

	/**
	 * 抽取抽来利用关键字匹配自动分担方法
	 */
	private void matchingByAddresskey(NoticeBill noticeBill, WorkBill workBill) {
		// 差询的客户为空，需要去查询地址关键字
		String pickaddress = noticeBill.getPickaddress();
		// 规定地址填写格式：XXX省XXX市XXX区 关键字 XXX路多少号
		String[] addresses = pickaddress.split(" ");
		if (addresses != null && addresses.length > 1) {
			// 取第二位为关键字位
			List<Subarea> subareas = facadeService.getSubareaDao().findByAddresskey(addresses[1]);
			if (subareas != null && subareas.size() == 1) {
				DecidedZone decidedZone = subareas.get(0).getDecidedZone();
				if (decidedZone != null) {
					Staff staff = decidedZone.getStaff();
					if (staff != null) {
						noticeBill.setStaff(staff);
						workBill.setStaff(staff);
						setData(noticeBill, workBill, "自动", "新", 0, "新单");
					}
				}
			}
			setData(noticeBill, workBill, "人工", "新", 0, "新单");
		} else
			setData(noticeBill, workBill, "人工", "新", 0, "新单");
	}

	/**
	 * 设置属性参数
	 */
	private void setData(NoticeBill noticeBill, WorkBill workBill, String orderType, String type,
			Integer attachbilltimes, String pickstate) {
		noticeBill.setOrdertype(orderType);

		workBill.setType(type);
		workBill.setAttachbilltimes(attachbilltimes);
		workBill.setBuildtime(new Timestamp(System.currentTimeMillis()));
		workBill.setPickstate(pickstate);
		workBill.setRemark(noticeBill.getRemark());
		workBill.setNoticeBill(noticeBill);
		facadeService.getWorkBillDao().save(workBill);
		return;
	}
	@Cacheable(key="#pageRequestBean.pageNumber+'_'+#pageRequestBean.pageSize",value="noticeBill")
	public Page<NoticeBill> pageQuery(Specification specification, PageRequest pageRequestBean) {
		return facadeService.getNoticeBillDao().findAll(specification, pageRequestBean);
	}

	/**
	 * 业务需求: 1) 需要有一定的使用权限。 2) 系统生成追工单 a) 系统记录追单信息，并累加改业务通知单对应的工单的追单次数； b)
	 * 已经下单成功的,通过短信平台发送”追”单短信；生成新的追工单。取件状态：已通知 c)
	 * 如果没有下单成功，则转调度，优先显示，显示“追单”。生成的一条追工单追单次数自增。取件状态：新建 3) 建议有原因录入框
	 * 
	 */
	@Override
	public void doRepeat(String[] ids) {
		if (ids == null || ids.length == 0)
			return;
		for (String id : ids) {

		}

	}

	@Override
	public Customer ajaxTel(String tel) {
		Customer customer = customerService.findCustomerByTel(tel);
		if (customer == null) {
			//如果查询不到用户,需要保存用户信息到crm系统中,

		}
		return customer;
	}

}