package cn.it.bos.web.action.auth;

import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;

import cn.it.bos.domain.auth.Function;
import cn.it.bos.domain.bc.Region;
import cn.it.bos.domain.qp.NoticeBill;
import cn.it.bos.domain.user.User;
import cn.it.bos.web.action.base.GenericAction;
import cn.it.crm.domain.Customer;
@SuppressWarnings("all")
@Controller
@Scope("prototype")
@ParentPackage("basicstruts2")
@Namespace("/")
public class FunctionAction extends GenericAction<Function> {

	private Function function = model;
	private String parentId;

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Action(value = "function_ajaxPrivilege", results = { @Result(name = "ajaxPrivilege", type = "json") })
	public String ajaxPrivilege() throws Exception {

		List<Function> list = functionService.ajaxPrivilege();
		pushRootValueStack(list);
		return "ajaxPrivilege";
	}
	@Action(value = "function_ajaxId", results = { @Result(name = "ajaxId", type = "json") })
	public String ajaxId() throws Exception {
		String id = function.getId();
		Function function = functionService.ajaxId(id);
		boolean flag = true;
		if (function != null) {
			flag = false;
			function =null;
		}
		pushRootValueStack(flag);
		return "ajaxId";
	}
	
	@Action(value = "function_ajaxTreeMenu", results = { @Result(name = "ajaxTreeMenu",type="json") })
	public String ajaxTreeMenu() {
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		List<Function> list = functionService.ajaxTreeMenu(user.getUsername());
		pushRootValueStack(list);
		return "ajaxTreeMenu";
	}
	@Action(value = "function_findParentFunction", results = { @Result(name = "findParentFunction",type="json") })
	public String findParentFunction() {
		List<Function> list = functionService.findParentFunction();
		pushRootValueStack(list);
		return "findParentFunction";
	}
	
	
	@Action(value = "function_pageQuery")
	public String pageQuery() throws Exception {
		super.setPage(Integer.parseInt(getParamter("page1")));
		Page<Function> pageQyery = functionService.pageQuery(null,getPageRequest());
		setPageQyery(pageQyery);
		return "pageQuery";
	}

	@Action(value = "function_save", results = { @Result(name = "save", location = "/WEB-INF/pages/admin/function.jsp") })
	public String save() {
		Function f = new Function();
		f.setId(parentId);
		function.setFunction(f);
		functionService.save(function);
		return "save";
	}

}
