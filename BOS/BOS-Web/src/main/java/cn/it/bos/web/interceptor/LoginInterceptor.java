package cn.it.bos.web.interceptor;

import org.apache.struts2.ServletActionContext;

import cn.it.bos.domain.user.User;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

public class LoginInterceptor extends MethodFilterInterceptor{

	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		/**
		 * 登录拦截器，如果用户未登陆，
		 * 阻止访问 WEB-INF 下所有页面和 除登陆之外其它 Action  
		 * 
		 */
		User loginUser = (User) ServletActionContext.getRequest().getSession().getAttribute("user");
		
		if(loginUser!=null){
//			session中用户存在直接放行
			return invocation.invoke();
		}else{
//		如果用户不存在，拦截用户到登录页面
			ActionSupport action = (ActionSupport)invocation.getAction();
			action.addActionError("您不在线时间过长，请重新登录");
		return "login";
	}
	}
}
