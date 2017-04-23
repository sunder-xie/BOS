package cn.it.bos.web.action.bc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.struts2.ServletActionContext;
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

import cn.it.bos.domain.bc.Standard;
import cn.it.bos.domain.user.User;
import cn.it.bos.web.action.base.GenericAction;

@SuppressWarnings("all")
@Controller
@Scope("prototype")
@ParentPackage("basicstruts2")
@Namespace("/")
public class StandardAction extends GenericAction<Standard> {

	private Standard standard = model;

	/**
	 * ajax关联查询所有的收派标准
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "standrad_findStandard", results = { @Result(name = "findstandrad", type = "json") })
	public String findStandard() throws Exception {
		// 需要查询的收派标准是，可使用的收派标准
		String deltag = "1";
		List<Standard> list = standardService.findStandard(deltag );
		pushRootValueStack(list);
		list=null;
		return "findstandrad";
	}

	/**
	 * 分页查询，把请求数据封装到PageRequestBean中，查询结果封装 到PageResopnebean中压入值栈。
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "standrad_pageQuery" )
	@RequiresRoles(value="base")
	public String pageQuery() {
		Specification specification = new Specification<Standard>() {
			@Override
			public Predicate toPredicate(Root<Standard> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate p = cb.equal(root.get("deltag").as(String.class), "1");
				query.where(p);
				return null;
			}
		};
		Page<Standard> pageQyery = standardService.pageQuery(getPageRequest(),specification);
		setPageQyery(pageQyery);
		return "pageQuery";
	}

	/**
	 * 删除或修改方法,能看到的都是有效的数据,所以deltag标志都是为1,
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "standrad_saveOrUpdate", results = { @Result(name = "save", location="/WEB-INF/pages/base/standard.jsp") })
	public String saveOrUpdate() throws Exception {
		String deltag = "1";// 第一次保存的数据或者需要修改的数据都是标志为1的,无效数据页面不显示
		// 保存数据到数据库
		standard.setUser((User) ServletActionContext.getRequest().getSession().getAttribute("user"));
		standard.setUpdatetime(new Timestamp(System.currentTimeMillis()));
		// standard.setId("1"); 当映射关系表中主见策略为assigned 时候，需要手动分配主键，不设置会报错
		// deltag 是否有效的标志,默认为1,有效,0无效 数据的逻辑删除标志
		standard.setDeltag(deltag);
		standardService.save(standard);
		return "save";
	}
	@Action(value = "standrad_delete", results = { @Result(name = "delete", location="/WEB-INF/pages/base/standard.jsp") })
	public String delete() {
		String[] ids = standard.getId().split(", ");
		standardService.deleteById(ids);
		return "delete";
	}

}
