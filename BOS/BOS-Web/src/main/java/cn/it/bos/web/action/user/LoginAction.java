package cn.it.bos.web.action.user;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import cn.it.bos.domain.user.User;
import cn.it.bos.web.action.base.GenericAction;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

public class LoginAction extends GenericAction<User> {

	private String checkcode;
	private User user = model;
	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}

	public String login() throws Exception {
		/**
		 * 登录逻辑 1，比较验证码，session中和用户输入的验证码
		 * 
		 */
		Map<String, Object> session = getMapValueStack().getSession();
		String sessionCode = (String) session.get("key");
		if (sessionCode == null || !sessionCode.equals(checkcode)) {
			this.addActionError("验证码错误");
			return "login_fail";
		}
		User loginUser = userService.login(this.user);
		if (loginUser == null) {
			this.addActionError("用户名或密码错误");
			return "login_fail";
		}
		session.put("user", loginUser);
		return "login_success";
	}

}
