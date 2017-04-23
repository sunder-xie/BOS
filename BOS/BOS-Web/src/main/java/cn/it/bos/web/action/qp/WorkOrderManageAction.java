package cn.it.bos.web.action.qp;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;

import java.util.Set;

import cn.it.bos.domain.bc.DecidedZone;
import cn.it.bos.domain.qp.WorkOrderManage;
import cn.it.bos.domain.user.User;
import cn.it.bos.web.action.base.GenericAction;
import cn.it.page.PageRequestBean;
import cn.it.page.PageResponseBean;

@SuppressWarnings("serial")
public class WorkOrderManageAction extends GenericAction<WorkOrderManage> {

	private WorkOrderManage workOrderManage = model;
	private String conditionName;
	private String conditionValue;

	/**
	 * 工作单的快速录入
	 * 
	 * @return
	 */
	public String save() {
		workOrderManageService.saveOrUpdate(workOrderManage);
		return "workOrderManageAction_save_success";
	}

	/**
	 * 分页查询所有有工作单， lucene搜索功能
	 * 
	 * @throws Exception
	 */

	public String pageQuery() throws Exception {
		PageResponseBean pageResponseBean = null;
		PageRequestBean pageRequestBean = null;
		
		if (StringUtils.isNotBlank(conditionValue)) {
			//当搜索框有数据时候，进行搜索查询
			pageRequestBean = new PageRequestBean();
			pageRequestBean.setPage(page);
			pageRequestBean.setRows(rows);
			pageResponseBean = workOrderManageService.pageQuery(pageRequestBean,conditionName,conditionValue);
		} else {
			//当搜索框没有输入时候，进行所有的分页查询
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(WorkOrderManage.class);
			pageRequestBean = super.pageQuery(detachedCriteria);
			pageResponseBean = workOrderManageService.pageQuery(pageRequestBean);
			if (pageResponseBean == null)
				return "workOrderManageAction_pageQuery_fail";
		}
		pushMapValueStack("pageResponseBean", pageResponseBean);
		return "workOrderManageAction_pageQuery_success";
	}

	public void setConditionName(String conditionName) {
		this.conditionName = conditionName;
	}

	public void setConditionValue(String conditionValue) {
		this.conditionValue = conditionValue;
	}
}
