package cn.it.bos.web.action.user;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

import cn.it.bos.domain.user.User;
import cn.it.bos.web.action.base.GenericAction;

public class UserAction extends GenericAction<User> {

	private User user = model;


	public String editpassword() throws Exception {
		User loginUser = (User) ServletActionContext.getRequest().getSession().getAttribute("user");
		user.setId(loginUser.getId());
		Map<String, Object> map = null;
		try {
			// 通过抓取异常来查看是否修改密码成功
			userService.editpassword(user);
			map = new HashMap<String, Object>();
			map.put("result", "success");
			map.put("msg", "修改成功");
		} catch (Exception e) {
			map.put("result", "fail");
			map.put("msg", "密码修改失败");
		}
//		需要返回给页面json数据，可以使用flexjson  也可以使用struts2自带json插件，需要配置
		pushMapValueStack("map", map);
		return "user_editpassword_success";
	}

}
