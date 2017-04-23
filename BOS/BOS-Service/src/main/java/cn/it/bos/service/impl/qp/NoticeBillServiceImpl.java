package cn.it.bos.service.impl.qp;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import cn.it.bos.domain.bc.DecidedZone;
import cn.it.bos.domain.bc.Staff;
import cn.it.bos.domain.bc.Subarea;
import cn.it.bos.domain.qp.NoticeBill;
import cn.it.bos.domain.qp.WorkBill;
import cn.it.bos.service.base.impl.GenericServiceImpl;
import cn.it.bos.service.qp.NoticeBillService;
import cn.it.crm.domain.Customer;
import cn.it.crm.service.CustomerService;
import cn.it.page.PageRequestBean;
import cn.it.page.PageResponseBean;

@SuppressWarnings("all")
public class NoticeBillServiceImpl extends GenericServiceImpl implements NoticeBillService {

	@Resource(name = "customerService")
	private CustomerService customerService;

	/*
	 * 保存业务通知单， 1，自动分担，查询客户id去crm中查询，如果查询到用户，并且用户关联取派员id存在，设置该单自动关联该取派员
	 * 2，查询通知单上地址的关键字，去分区设置中自动匹配取派员，关键字完全匹配，匹配上自动关联，分派该单给取派员 3，人工分担，设置取派员。
	 */
	@Override
	public void save(NoticeBill noticeBill) {
		// 利用快照特性
		noticeBillDao.save(noticeBill);
		WorkBill workBill = new WorkBill();
		// 业务单录入客户编号不为空时候才能去查询crm系统
		if (StringUtils.isNotBlank(noticeBill.getCustomerId())) {
			Customer customer = customerService.findCustomerById(noticeBill.getCustomerId());
			if (customer != null && StringUtils.isNotBlank(customer.getDecidedzoneid())) {
				// 查询出来关联的定区id，
				String decidedZoneId = customer.getDecidedzoneid();
				DecidedZone decidedZone = (DecidedZone) decidedZoneDao.findById(decidedZoneId);
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
			List<Subarea> subareas = subareaDao.findByCriteria(
					DetachedCriteria.forClass(Subarea.class).add(Restrictions.eq("addresskey", addresses[1])));
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
		workBill.setNoticebill(noticeBill);
		workBillDao.save(workBill);
		return;
	}

	@Override
	public PageResponseBean pageQuery(PageRequestBean pageRequestBean) {
		return super.pageQuery(pageRequestBean, noticeBillDao);
	}

}