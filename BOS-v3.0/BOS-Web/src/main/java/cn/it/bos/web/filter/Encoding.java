package cn.it.bos.web.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class Encoding
 */
public class Encoding implements Filter {

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		resp.setContentType("text/html;charset=UTF-8");// 处理响应编码
		req.setCharacterEncoding("UTF-8");
		chain.doFilter(new MyRequest(req), resp);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}
	class MyRequest extends HttpServletRequestWrapper{

		private HttpServletRequest request = null;
		//防止重复编码标记
		private boolean flag = false;
		
		public MyRequest(HttpServletRequest request) {
			super(request);
			//将服务器创建的request对象传人我的自定义对象中
			//获取了服务器创建的request对象中的所有数据。
			this.request = request;
		}

		
		//对request对象的获取请求参数的方法进行增强
		@Override
		public Map<String ,String[]> getParameterMap() {
			//获取请求方式，根据不同的请求方式，处理中文乱码
			String method = this.request.getMethod();
			if("post".equalsIgnoreCase(method)){
				try {
					//处理post方式中文乱码
					this.request.setCharacterEncoding("utf-8");
					return this.request.getParameterMap();
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					return super.getParameterMap();
				}
			}else if("get".equalsIgnoreCase(method)){
				Map<String ,String[]> map = this.request.getParameterMap();
				if (flag) {
					//如果已经编码过了，直接返回map
					return map;
				}
				if(map == null){
					return super.getParameterMap();
				}
				Set<String> keySet = map.keySet();
				for (String key : keySet) {
					//获取map集合的value值
					String[] values = map.get(key);
					for (int i = 0; i < values.length; i++) {
						
						try {
							//将数据重新编码和解码
							String temp = new String(values[i].getBytes("iso-8859-1"),"utf-8");
							values[i] = temp;
							
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}
					}
				}
				//循环结束，标记置为true，返回map集合
				flag = true;
				//将数据返回
				return map;
				
			}else{
				
				return super.getParameterMap();
			}
		}
		
		
		
		@Override
		public String[] getParameterValues(String name) {
			Map<String, String[]> map = this.getParameterMap();
			if(map == null){
				return super.getParameterValues(name);
			}
			String[] values = map.get(name);
			return values;
		}
		
		@Override
		public String getParameter(String name) {
			String[] values = this.getParameterValues(name);
			if(values == null){
				return super.getParameter(name);
			}
			return values[0];
		}
	}

}
