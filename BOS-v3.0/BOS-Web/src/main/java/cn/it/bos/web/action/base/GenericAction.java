package cn.it.bos.web.action.base;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.util.ValueStack;

import cn.it.bos.domain.auth.Role;
import cn.it.bos.service.auth.FunctionService;
import cn.it.bos.service.auth.RoleService;
import cn.it.bos.service.bc.DecidedZoneService;
import cn.it.bos.service.bc.RegionService;
import cn.it.bos.service.bc.StaffService;
import cn.it.bos.service.bc.StandardService;
import cn.it.bos.service.bc.SubareaService;
import cn.it.bos.service.qp.NoticeBillService;
import cn.it.bos.service.qp.WorkBillService;
import cn.it.bos.service.qp.WorkOrderManageService;
import cn.it.bos.service.user.UserService;
import cn.it.crm.service.CustomerService;

@Component
@SuppressWarnings("all")
public abstract class GenericAction<T> extends ActionSupport implements ModelDriven<T> {

	@Resource(name = "roleService")
	protected RoleService roleService;
	
	@Resource(name = "functionService")
	protected FunctionService functionService;
	
	@Resource(name = "userService")
	protected UserService userService;

	@Resource(name = "standardService")
	protected StandardService standardService;

	@Resource(name = "staffService")
	protected StaffService staffService;

	@Resource(name = "regionService")
	protected RegionService regionService;

	@Resource(name = "subareaService")
	protected SubareaService subareaService;

	@Resource(name = "decidedZoneService")
	protected DecidedZoneService decidedZoneService;

	@Resource(name = "customerService")
	protected CustomerService customerService;

	@Resource(name = "noticeBillService")
	protected NoticeBillService noticeBillService;

	@Resource(name = "workBillService")
	protected WorkBillService workBillService;

	@Resource(name = "workOrderManageService")
	protected WorkOrderManageService workOrderManageService;
	protected T model;

	public int page;
	public int rows;

	public T getModel() {
		return model;
	}

	/**
	 * 构造函数初始化时候赋值model
	 */
	public GenericAction() {
		try {
			Type genericSuperclass = getClass().getGenericSuperclass();
			if (!(genericSuperclass instanceof ParameterizedType)) {
				genericSuperclass = this.getClass().getSuperclass().getGenericSuperclass();
			}
			ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
			Class clazz = (Class) parameterizedType.getActualTypeArguments()[0];
			model = (T) clazz.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 获取值栈
	 */
	public ActionContext getMapValueStack() {
		return ActionContext.getContext();
	}

	public ValueStack getRootValueStack() {
		return ActionContext.getContext().getValueStack();
	}

	/**
	 * 压入map值栈
	 */
	public void pushMapValueStack(String key, Object value) {
		getMapValueStack().put(key, value);
	}

	public void pushRootValueStack(Object o) {
		getRootValueStack().push(o);
	}

	public HttpSession getSession() {
		return ServletActionContext.getRequest().getSession();
	}

	public String getParamter(String name) {
		return ServletActionContext.getRequest().getParameter(name);
	}

	public String[] getParamters(String name) {
		return ServletActionContext.getRequest().getParameterValues(name);
	}

	public Map<String, String> getParameterMap(String name) {
		return ServletActionContext.getRequest().getParameterMap();
	}

	PageRequest pageRequestBean;
	Sort sort;

	/**
	 * 分页抽取,封装请求数据
	 * 
	 * @return
	 */
	protected PageRequest getPageRequest() {
		Sort sort = getSort();
		return new PageRequest(page - 1, rows, sort);
	}

	/**
	 * 是否需要排序查询
	 * 
	 * @return
	 */
	protected Sort getSort() {
		String orderParam = getParamter("order");
		String sortParam = getParamter("sort");
		if (StringUtils.isNotBlank(sortParam) && StringUtils.isNotBlank(orderParam)) {
			Direction direction = Direction.fromString(orderParam);// asc desc
			return new Sort(direction, sortParam);
		}
		return null;
	}

	/**
	 * 
	 * @return
	 */
	Page<T> pageQyery;

	protected void setPageQyery(Page<T> pageQyery) {
		this.pageQyery = pageQyery;
	}

	/**
	 * 提供get方法,页面直接在值栈中获取
	 * 
	 * @return
	 */
	Map<String, Object> pageResponseBean;

	public void setPageResponseBean(Map<String, Object> pageResponseBean) {
		this.pageResponseBean = pageResponseBean;
	}

	protected Map<String, Object> getPageResponseBean() {
		return (Map<String, Object>) getObj();
	}

	public Object getObj() {
		if (pageResponseBean != null)
			return pageResponseBean;
		else {
			pageResponseBean = new HashMap<String, Object>();
		}
		if (pageQyery != null) {
			pageResponseBean.put("rows", pageQyery.getContent());
			pageResponseBean.put("total", pageQyery.getTotalElements());// 总记录数
		}
		return pageResponseBean;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

}
