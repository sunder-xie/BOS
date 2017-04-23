package cn.it.bos.web.action.user;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.it.bos.domain.user.User;
import cn.it.bos.utils.MD5Utils;
import cn.it.bos.web.action.base.GenericAction;

@SuppressWarnings("all")
@Controller
@Scope("prototype")
@ParentPackage("basicstruts2")
@Namespace("/")
public class LoginAction extends GenericAction<User> {

	private String checkcode;
	private User user = model;

	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}

	@Action(value = "userAction_validCheckcode", results = { @Result(name = "validcheckcode", type = "json") })
	public String validCheckcode() {
		HttpSession session = getSession();
		String input_checkcode = getParamter("checkcode");
		String sessionCode = (String) session.getAttribute("key");
		boolean flag = false;
		if (StringUtils.isNotBlank(input_checkcode)) {
			// 用户输出验证码
			if (input_checkcode.equalsIgnoreCase(sessionCode)) {
				flag = true;
			}
		}
		// 用户没有输入验证码
		pushRootValueStack(false);
		return "validcheckcode";
	}

	@Action(value = "login_ajaxChecked", results = { @Result(name = "check", type = "json") })
	public String ajaxChecked() throws Exception {
		HttpSession session = getSession();
		String sessionCode = (String) session.getAttribute("key");
		boolean flag = false;
		if (sessionCode != null && sessionCode.equalsIgnoreCase(checkcode)) {
			flag = true;
		}
		pushRootValueStack(flag);
		return "check";
	}

	@Action(value = "loginAction_logout" ,results = {@Result(name = "loginAction_logout", type = "redirect", location = "/login.jsp")})
	public String logout() throws Exception {
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		return "loginAction_logout";
	}

	@Action(value = "loginAction_login", results = {
			@Result(name = "login_success", type = "redirect", location = "/index.jsp"),
			@Result(name = "login_fail", location = "/login.jsp") })
	public String login() throws Exception {
		/**
		 * 登录逻辑 1，比较验证码，session中和用户输入的验证码
		 * 
		 */
		HttpSession session = getSession();
		String sessionCode = (String) session.getAttribute("key");
		if (sessionCode != null && sessionCode.equals(checkcode)) {

			try {
				Subject subject = SecurityUtils.getSubject();
				subject.login(new UsernamePasswordToken(user.getUsername(), MD5Utils.md5(user.getPassword())));
				session.setAttribute("key", "");
				return "login_success";
			} catch (UnknownAccountException e) {
				this.addActionError("账户不存在");
				return "login_fail";
			} catch (IncorrectCredentialsException e) {
				this.addActionError("密码错误");
				return "login_fail";
			} catch (LockedAccountException e) {
				this.addActionError("账户被锁定");
				return "login_fail";
			} catch (AuthenticationException e) {
				this.addActionError("用户名或密码错误");
				return "login_fail";
			}
		} else

		{
			this.addActionError("验证码错误");
			return "login_fail";
		}

	}

}
