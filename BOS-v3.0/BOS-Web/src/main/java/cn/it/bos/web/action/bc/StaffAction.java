package cn.it.bos.web.action.bc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import cn.it.bos.domain.bc.Staff;
import cn.it.bos.domain.bc.Standard;
import cn.it.bos.web.action.base.GenericAction;

@SuppressWarnings("all")
@Controller
@Scope("prototype")
@ParentPackage("basicstruts2")
@Namespace("/")
public class StaffAction extends GenericAction<Staff> {

	private Staff staff = model;

	@Action(value = "staff_ajaxId", results = { @Result(name = "ajaxId", type = "json") })
	public String ajaxId() throws Exception {
		String id = staff.getId();
		Staff staff = staffService.ajaxId(id);
		boolean flag = true;
		if (staff != null) {
			flag = false;
		}
		pushRootValueStack(flag);
		return "ajaxId";
	}

	@Action(value = "staff_ajaxPhone", results = { @Result(name = "ajaxPhone", type = "json") })
	public String ajaxPhone() throws Exception {
		String telephone = staff.getTelephone();
		List<Staff> staffList = staffService.ajaxPhone(telephone);
		boolean flag = true;
		if (staffList != null && staffList.size() > 0) {
			flag = false;
			staffList = null;
		}
		pushRootValueStack(flag);
		return "ajaxPhone";
	}
	@Action(value = "staff_findStandard", results = { @Result(name = "findStandard", type = "json") })
	public String findStandard() throws Exception {
		// 需要查询的收派标准是，可使用的收派标准
		// 需要把无效的数据进行过滤
		String deltag = "1";
		List<Staff> staffList = staffService.findStandard(deltag);
		pushRootValueStack(staffList);
		staffList=null;
		return "findStandard";
	}

	/**
	 * 分页查询，把请求数据封装到PageRequestBean中，查询结果封装 到PageResopnebean中压入值栈。
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "staff_pageQuery")
	public String pageQuery() throws Exception {
		Page<Staff> pageQyery = staffService.pageQuery(getPageRequest(),null);
		setPageQyery(pageQyery);
		return "pageQuery";
	}

	@Action(value = "staff_saveOrUpdate", results = { @Result(name = "save", location="/WEB-INF/pages/base/staff.jsp") })
	public String save() throws Exception {
		// 保存数据到数据库
		// 需要通过standardId查询出来完整信息，封装到staff中去
//		Standard queryStandard = staffService.findStandardById(staff.getStandard());
		staffService.save(staff);
		return "save";
	}

	/**
	 * 进行的逻辑删除,把删除标志位 deltag 改为0
	 * 
	 * @return
	 */
	@Action(value = "staff_delete", results = { @Result(name = "delete", location="/WEB-INF/pages/base/staff.jsp") })
	public String delete() {
		String[] ids = staff.getId().split(", ");
		staffService.deleteById(ids);

		return "delete";
	}

}
