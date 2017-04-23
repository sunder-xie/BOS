package cn.it.bos.web.action.qp;

import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;

import cn.it.bos.domain.qp.NoticeBill;
import cn.it.bos.domain.user.User;
import cn.it.bos.web.action.base.GenericAction;
import cn.it.crm.domain.Customer;
@SuppressWarnings("all")
@Controller
@Scope("prototype")
@ParentPackage("basicstruts2")
@Namespace("/")
public class NoticeBillAction extends GenericAction<NoticeBill> {

	private NoticeBill noticeBill = model;

	
	@Action(value = "noticeBill_ajaxTel", results = { @Result(name = "ajaxTel", type = "json") })
	public String ajaxTel() throws Exception {
		String tel = getParamter("tel");
		Customer customer = noticeBillService.ajaxTel(tel);
		if(customer!=null){
			pushRootValueStack(customer);
		}
		return "ajaxTel";
	}
	
	/**
	 * 保存通知单和关联的工单分单
	 * 
	 * @return
	 */
	@Action(value = "noticeBill_doRepeat", results = { @Result(name = "doRepeat", location = "/WEB-INF/pages/qupai/noticebill.jsp") })
	public String doRepeat() {
		String[] ids = getParamters("id");
		noticeBillService.doRepeat(ids);
		return "doRepeat";
	}
	
	
	/**
	 * 查询所有通知单信息
	 * 
	 * @return
	 * @throws Exception 
	 */
	@Action(value = "noticeBill_pageQuery")
	public String pageQuery() throws Exception {
		Page<NoticeBill> pageQyery = noticeBillService.pageQuery(null,getPageRequest());
		setPageQyery(pageQyery);
		return "pageQuery";
	}

	/**
	 * 保存通知单和关联的工单分单
	 * 
	 * @return
	 */
	@Action(value = "noticeBill_saveOrUpdate", results = { @Result(name = "save", location = "/WEB-INF/pages/qupai/noticebill.jsp") })
	public String saveOrUpdate() {

		Map<String, Object> map = null;
		if (noticeBill == null)
			return null;
		// 数据用封装
		User loginUser = (User) getSession().getAttribute("user");
		noticeBill.setUser(loginUser);
		noticeBillService.save(noticeBill);
		return "save";
	}

}
