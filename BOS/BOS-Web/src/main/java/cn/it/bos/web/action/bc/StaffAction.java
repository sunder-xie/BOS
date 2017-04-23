package cn.it.bos.web.action.bc;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

import cn.it.bos.domain.bc.Staff;
import cn.it.bos.domain.bc.Standard;
import cn.it.bos.web.action.base.GenericAction;
import cn.it.page.PageRequestBean;
import cn.it.page.PageResponseBean;

public class StaffAction extends GenericAction<Staff> {

	private Staff staff  =  model;
	public String findStandard() throws Exception {
//		需要查询的收派标准是，可使用的收派标准
		DetachedCriteria detachedCriteria = 	DetachedCriteria.forClass(Staff.class);
//		需要把无效的数据进行过滤
		detachedCriteria.add(Restrictions.eq("deltag", "1"));
		List<Staff> staffList = staffService.findStandard(detachedCriteria);
		pushMapValueStack("staffList", staffList);
		return "staff_findStandard_success";
	}
	/**
	 * 分页查询，把请求数据封装到PageRequestBean中，查询结果封装
	 * 到PageResopnebean中压入值栈。
	 * 
	 * @return
	 * @throws Exception
	 */
	public String pageQuery() throws Exception {
		DetachedCriteria detachedCriteria = 	DetachedCriteria.forClass(Staff.class);
//		需要把无效的数据进行过滤
		detachedCriteria.add(Restrictions.eq("deltag", "1"));
		PageRequestBean pageRequestBean = super.pageQuery(detachedCriteria);
		PageResponseBean pageResponseBean = staffService.pageQuery(pageRequestBean);
		pushMapValueStack("pageResponseBean", pageResponseBean);
		return "staff_pageQuery_success";
	}
	public String saveOrUpdate() throws Exception {
		//保存数据到数据库
//		需要通过standardId查询出来完整信息，封装到staff中去
		Standard queryStandard = staffService.findStandardById(staff.getStandard());
		staff.setStandard(queryStandard);
//		System.out.println(staff);
		staffService.save(staff);
		return "staff_save_success";
	}
//
	public String delete(){
		String[] ids = staff.getId().split(", ");
		staffService.deleteById(ids);
		
		return "staff_delete_success";
	}
	
}
