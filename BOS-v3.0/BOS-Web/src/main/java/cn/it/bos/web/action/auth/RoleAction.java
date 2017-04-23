package cn.it.bos.web.action.auth;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;

import cn.it.bos.domain.auth.Function;
import cn.it.bos.domain.auth.Role;
import cn.it.bos.web.action.base.GenericAction;

@SuppressWarnings("all")
@Controller
@Scope("prototype")
@ParentPackage("basicstruts2")
@Namespace("/")
public class RoleAction extends GenericAction<Role> {

	private Role role = model;
	private String functionIds;
	public String getFunctionIds() {
		return functionIds;
	}
	public void setFunctionIds(String functionIds) {
		this.functionIds = functionIds;
	}

	@Action(value = "role_pageQuery")
	public String pageQuery() throws Exception {
		super.setPage(Integer.parseInt(getParamter("page")));
		Page<Role> pageQyery = roleService.pageQuery(null, getPageRequest());
		setPageQyery(pageQyery);
		return "pageQuery";
	}

	@Action(value = "role_save", results = { @Result(name = "save", location = "/WEB-INF/pages/admin/role.jsp") })
	public String save() {
		roleService.save(role,functionIds);
		return "save";
	}
	@Action(value = "role_ajaxRoles", results = { @Result(name = "ajaxRoles", type="json") })
	public String ajaxRoles() {
		List<Role> list = roleService.ajaxRoles();
		pushRootValueStack(list);
		return "ajaxRoles";
	}

}
