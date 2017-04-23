package cn.it.bos.web.action.bc;

import java.sql.Timestamp;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

import cn.it.bos.domain.bc.Standard;
import cn.it.bos.domain.user.User;
import cn.it.bos.web.action.base.GenericAction;
import cn.it.page.PageRequestBean;
import cn.it.page.PageResponseBean;

public class StandardAction extends GenericAction<Standard> {

	private Standard standard  =  model;
	
	/**
	 * ajax关联查询所有的收派标准
	 * @return
	 * @throws Exception
	 */
	public String findStandard() throws Exception {
//		需要查询的收派标准是，可使用的收派标准
		DetachedCriteria detachedCriteria = 	DetachedCriteria.forClass(Standard.class);
//		需要把无效的数据进行过滤
		detachedCriteria.add(Restrictions.eq("deltag", "1"));
		List<Standard> standardList = standardService.findStandard(detachedCriteria);
		pushMapValueStack("standardList", standardList);
		return "standard_findStandard_success";
	}
	
	
	/**
	 * 分页查询，把请求数据封装到PageRequestBean中，查询结果封装
	 * 到PageResopnebean中压入值栈。
	 * 
	 * @return
	 * @throws Exception
	 */
	public String pageQuery() throws Exception {
		DetachedCriteria detachedCriteria = 	DetachedCriteria.forClass(Standard.class);
//		需要把无效的数据进行过滤
		detachedCriteria.add(Restrictions.eq("deltag", "1"));
		PageRequestBean pageRequestBean = super.pageQuery(detachedCriteria);
		PageResponseBean pageResponseBean = standardService.pageQuery(pageRequestBean);
		pushMapValueStack("pageResponseBean", pageResponseBean);
		
		return "standard_pageQuery_success";
	}
	public String saveOrUpdate() throws Exception {
		//保存数据到数据库
		standard.setUser((User)ServletActionContext.getRequest().getSession().getAttribute("user"));
		standard.setUpdatetime(new Timestamp(System.currentTimeMillis()));
//		standard.setId("1");  当映射关系表中主见策略为assigned 时候，需要手动分配主键，不设置会报错
		standardService.save(standard);
		return "standard_save_success";
	}

	public String delete(){
		String[] ids = standard.getId().split(", ");
		standardService.deleteById(ids);
		
		return "standard_delete_success";
	}
	
}
