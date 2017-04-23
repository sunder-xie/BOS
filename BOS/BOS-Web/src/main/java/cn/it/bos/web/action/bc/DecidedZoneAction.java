package cn.it.bos.web.action.bc;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

import cn.it.bos.domain.bc.DecidedZone;
import cn.it.bos.domain.bc.Region;
import cn.it.bos.domain.bc.Staff;
import cn.it.bos.domain.bc.Standard;
import cn.it.bos.domain.bc.Subarea;
import cn.it.bos.web.action.base.GenericAction;
import cn.it.crm.domain.Customer;
import cn.it.page.PageRequestBean;
import cn.it.page.PageResponseBean;

public class DecidedZoneAction extends GenericAction<DecidedZone> {

	// 避免id冲突
	private String[] sid;// decidedZone表id
	private DecidedZone decidedZone = model;
	private String[] customerIds;
	public void setCustomerIds(String[] customerIds) {
		this.customerIds = customerIds;
	}

	/**
	 * ajax进行查询没有关联定区的客户
	 * 
	 * @return
	 */
	public String findNoAssociationCustomer() {
		List<Customer> customers = customerService.findNoAssociationCustomer();
		pushRootValueStack(customers);
		return "decidedZone_findNoAssociationCustomer_success";
	}

	/**
	 * 查询已经关联的客户
	 * 
	 * @return
	 */
	public String findAssociationCustomer() {
		String id = decidedZone.getId();
		List<Customer> customers = customerService.findAssociationCustomer(id);
		pushRootValueStack(customers);
		return "decidedZone_findAssociationCustomer_success";
	}
	
	public String associateCustomerToDecidedZone() {
		String id = decidedZone.getId();
		if(customerIds==null || customerIds.length==0)return null;
		customerService.associateCustomerToDecidedZone(customerIds, id);
//		pushRootValueStack(customers);
		return "decidedZone_associateCustomerToDecidedZone_success";
	}

	/**
	 * 关联分区查询
	 */
	public String associationSubarea() {
		String id = decidedZone.getId();
		List<Subarea> subareaList = decidedZoneService.findSubareaById(id);
		pushMapValueStack("subareaList", subareaList);
		return "decidedZone_associationSubarea_success";
	}

	/**
	 * 分页查询，把请求数据封装到PageRequestBean中，查询结果封装 到PageResopnebean中压入值栈。
	 */
	public String pageQuery() throws Exception {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(DecidedZone.class);
		PageRequestBean pageRequestBean = super.pageQuery(detachedCriteria);
		PageResponseBean pageResponseBean = decidedZoneService.pageQuery(pageRequestBean);
		pushMapValueStack("pageResponseBean", pageResponseBean);
		return "decidedZone_pageQuery_success";
	}

	/**
	 * 保存或者修改
	 * 
	 * @return
	 * @throws Exception
	 */
	public String saveOrUpdate() throws Exception {
		// 保存数据到数据库
		// 需要使用ajax进行id校验
		decidedZoneService.save(decidedZone, sid);
		return "decidedZone_save_success";
	}

	// 删除
	public String delete() {
		// decidedZone.setId(did);
		String[] ids = decidedZone.getId().split(", ");
		decidedZoneService.deleteById(ids);

		return "decidedZone_delete_success";
	}

	public void setSid(String[] sid) {
		this.sid = sid;
	}
}
