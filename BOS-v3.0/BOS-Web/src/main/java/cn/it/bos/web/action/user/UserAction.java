package cn.it.bos.web.action.user;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;

import cn.it.bos.domain.auth.Function;
import cn.it.bos.domain.auth.Role;
import cn.it.bos.domain.user.User;
import cn.it.bos.web.action.base.GenericAction;

@SuppressWarnings("all")
@Controller
@Scope("prototype")
@ParentPackage("basicstruts2")
@Namespace("/")
public class UserAction extends GenericAction<User> {

	private User user = model;
	private String[] roleIds;

	public void setRoleIds(String[] roleIds) {
		this.roleIds = roleIds;
	}

	@Action(value = "user_pageQuery")
	public String pageQuery() throws Exception {
		super.setPage(Integer.parseInt(getParamter("page")));
		Page<User> pageQyery = userService.pageQuery(null,getPageRequest());
		setPageQyery(pageQyery);
		return "pageQuery";
	}
	
	@Action(value = "user_ajaxPhone", results = { @Result(name = "ajaxPhone", type = "json") })
	public String ajaxPhone() throws Exception {
		String telephone = user.getTelephone();
		List<User> list = userService.ajaxPhone(telephone);
		boolean flag = true;
		if (list != null && list.size() > 0) {
			flag = false;
			list = null;
		}
		pushRootValueStack(flag);
		return "ajaxPhone";
	}

	@Action(value = "user_ajaxName", results = { @Result(name = "ajaxName", type = "json") })
	public String ajaxName() throws Exception {
		String username = user.getUsername();
		User findByUsername = userService.findByUsername(username);
		boolean flag = true;
		if (findByUsername != null) {
			flag = false;
		}
		pushRootValueStack(flag);
		return "ajaxName";
	}

	@Action(value = "userAction_editpassword", results = { @Result(name = "user_editpassword_success", type = "json") })
	public String editpassword() throws Exception {
		User loginUser = (User) ServletActionContext.getRequest().getSession().getAttribute("user");
		user.setId(loginUser.getId());
		try {
			// 通过抓取异常来查看是否修改密码成功
			userService.editpassword(user);
			pushRootValueStack(true);
		} catch (Exception e) {
			pushRootValueStack(false);
		}
		return "user_editpassword_success";
	}

	@Action(value = "user_save", results = {
			@Result(name = "save", location = "/WEB-INF/pages/admin/userlist.jsp") })
	public String save() throws Exception {

		try {
			Set<Role> roles = user.getRoles();
			for (String roleid : roleIds) {
				Role role = new Role();
				role.setId(StringUtils.trimToNull(roleid));
				roles.add(role);
			}
			// 通过抓取异常来查看是否修改密码成功
			User save = userService.save(user);
		} catch (Exception e) {
			throw new RuntimeException("保存失败" + e);
		}
		return "save";
	}

}
