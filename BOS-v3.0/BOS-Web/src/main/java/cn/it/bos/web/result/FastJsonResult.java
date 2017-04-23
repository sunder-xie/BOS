package cn.it.bos.web.result;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.StrutsStatics;
import org.apache.struts2.json.JSONResult;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.Result;
import com.opensymphony.xwork2.util.ValueStack;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

/**
 * fastjson 完成 json序列化结果集完成编码
 * 
 */
@Component("json")
public class FastJsonResult implements Result {
	private static final Logger LOG = LoggerFactory.getLogger(FastJsonResult.class);
	private String root;

	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
	}
	@Override
	public void execute(ActionInvocation invocation) throws Exception {
		  ActionContext actionContext = invocation.getInvocationContext();
	        HttpServletRequest request = (HttpServletRequest) actionContext.get(StrutsStatics.HTTP_REQUEST);
	        HttpServletResponse response = (HttpServletResponse) actionContext.get(StrutsStatics.HTTP_RESPONSE);
	        
		// 目标 目标对象存放 值栈 进行序列化输出
		ValueStack stack = invocation.getStack();
		Object obj = findRootObject(invocation);
		// fastjosn 序列化 response写出
		String jsonString = JSON.toJSONString(obj);
		System.out.println(jsonString + "---------");
		response.setContentType("text/json;charset=utf-8");
		response.getWriter().print(jsonString);

	}

	protected Object findRootObject(ActionInvocation invocation) {
		Object rootObject;
		if (this.root != null) {
			ValueStack stack = invocation.getStack();
			rootObject = stack.findValue(root);
		} else {
			rootObject = invocation.getStack().peek(); // model overrides action
		}
		return rootObject;
	}

}
