package cn.it.bos.web.action.base;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.util.ValueStack;

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
import cn.it.page.PageRequestBean;

public abstract class GenericAction<T> extends ActionSupport implements ModelDriven<T> {
	@Autowired
	@Qualifier("userService")
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

	/**
	 * 构造函数初始化时候赋值model
	 */
	public GenericAction() {
		try {
			Type genericSuperclass = getClass().getGenericSuperclass();
			ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
			// String simpleName =
			// parameterizedType.getActualTypeArguments()[0].getClass().getSimpleName();
			// model = (T) Class.forName(simpleName).newInstance();
			Class clazz = (Class) parameterizedType.getActualTypeArguments()[0];
			model = (T) clazz.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected T model;

	public T getModel() {
		return model;
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

	protected int page;
	protected int rows;

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

	public PageRequestBean pageQuery(DetachedCriteria detachedCriteria) throws Exception {
		PageRequestBean pageRequestBean = new PageRequestBean();
		pageRequestBean.setPage(page);
		pageRequestBean.setRows(rows);
		pageRequestBean.setDetachedCriteria(detachedCriteria);
		return pageRequestBean;
	}

}
