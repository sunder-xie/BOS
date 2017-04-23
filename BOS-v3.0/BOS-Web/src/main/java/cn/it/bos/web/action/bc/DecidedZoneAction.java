package cn.it.bos.web.action.bc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import cn.it.bos.domain.bc.DecidedZone;
import cn.it.bos.domain.bc.Subarea;
import cn.it.bos.web.action.base.GenericAction;
import cn.it.crm.domain.Customer;

@SuppressWarnings("all")
@Controller
@Scope("prototype")
@ParentPackage("basicstruts2")
@Namespace("/")
public class DecidedZoneAction extends GenericAction<DecidedZone> {

	// 避免id冲突
	private String[] sid;// decidedZone表id
	private DecidedZone decidedZone = model;
	private String[] customerIds;

	public void setCustomerIds(String[] customerIds) {
		this.customerIds = customerIds;
	}

	/**
	 * ajax进行主键唯一性校验
	 * 
	 * @return
	 */
	@Action(value = "decidedZone_ajaxId", results = { @Result(name = "ajaxId", type = "json") })
	public String ajaxId() {
		String id = decidedZone.getId();
		DecidedZone decidedZone = decidedZoneService.ajaxId(id);
		boolean flag = true;
		if (decidedZone != null) {
			flag = false;
		}
		pushRootValueStack(flag);
		return "ajaxId";
	}

	/**
	 * ajax进行查询没有关联定区的客户
	 * 
	 * @return
	 */
	@Action(value = "decidedZone_findNoAssociationCustomer", results = {
			@Result(name = "findNoAssociationCustomer", type = "json") })
	public String findNoAssociationCustomer() {
		List<Customer> customers = customerService.findNoAssociationCustomer();
		pushRootValueStack(customers);
		return "findNoAssociationCustomer";
	}

	/**
	 * 查询已经关联的客户
	 * 
	 * @return
	 */
	@Action(value = "decidedZone_findAssociationCustomer", results = {
			@Result(name = "findAssociationCustomer", type = "json") })
	public String findAssociationCustomer() {
		String id = decidedZone.getId();
		List<Customer> customers = customerService.findAssociationCustomer(id);
		if(customers==null){
			customers = new ArrayList<Customer>();
		}
		pushRootValueStack(customers);
		return "findAssociationCustomer";
	}

	@Action(value = "decidedZone_associateCustomerToDecidedZone", results = {
			@Result(name = "associateCustomerToDecidedZone", location = "/WEB-INF/pages/base/decidedzone.jsp") })
	public String associateCustomerToDecidedZone() {
		String id = decidedZone.getId();
		if (customerIds == null || customerIds.length == 0)
			return null;
		customerService.associateCustomerToDecidedZone(customerIds, id);
		// pushRootValueStack(customers);
		return "associateCustomerToDecidedZone";
	}

	/**
	 * 关联分区查询
	 */
	@Action(value = "decidedZone_associationSubarea", results = { @Result(name = "associationSubarea", type = "json") })
	public String associationSubarea() {
		String id = decidedZone.getId();
		List<Subarea> subareaList = decidedZoneService.findSubareaById(id);
		pushRootValueStack(subareaList);
		return "associationSubarea";
	}

	/**
	 * 分页查询，把请求数据封装到PageRequestBean中，查询结果封装 到PageResopnebean中压入值栈。
	 */
	@Action(value = "decidedZone_pageQuery")
	public String pageQuery() throws Exception {
		Page<DecidedZone> pageQyery = decidedZoneService.pageQuery(getPageRequest(), null);
		setPageQyery(pageQyery);
		return "pageQuery";
	}

	/**
	 * 保存或者修改
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "decidedZone_saveOrUpdate", results = {
			@Result(name = "save", location = "/WEB-INF/pages/base/decidedzone.jsp") })
	public String saveOrUpdate() throws Exception {
		// 保存数据到数据库
		// 需要使用ajax进行id校验
		decidedZoneService.save(decidedZone, sid);
		return "save";
	}

	// 删除
	@Action(value = "decidedZone_delete", results = {
			@Result(name = "delete", location = "/WEB-INF/pages/base/decidedzone.jsp") })
	public String delete() {
		// decidedZone.setId(did);
		String[] ids = decidedZone.getId().split(", ");
		decidedZoneService.deleteById(ids);

		return "delete";
	}

	public void setSid(String[] sid) {
		this.sid = sid;
	}
}
