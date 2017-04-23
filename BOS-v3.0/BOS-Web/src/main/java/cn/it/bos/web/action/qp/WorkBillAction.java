package cn.it.bos.web.action.qp;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import cn.it.bos.domain.qp.NoticeBill;
import cn.it.bos.domain.qp.WorkBill;
import cn.it.bos.web.action.base.GenericAction;

@SuppressWarnings("all")
@Controller
@Scope("prototype")
@ParentPackage("basicstruts2")
@Namespace("/")
public class WorkBillAction extends GenericAction<WorkBill> {

	private WorkBill workBill = model;
	/**
	 * 查询所有工单信息
	 * 
	 * @return
	 * @throws Exception 
	 */
	@Action(value = "workBill_pageQuery")
	public String pageQuery() throws Exception {
		Page<WorkBill> pageQyery = workBillService.pageQuery(null,getPageRequest());
		setPageQyery(pageQyery);
		return "pageQuery";
	}
}
