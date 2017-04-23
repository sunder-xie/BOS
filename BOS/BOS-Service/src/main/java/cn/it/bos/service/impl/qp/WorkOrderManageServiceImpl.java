package cn.it.bos.service.impl.qp;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import cn.it.bos.domain.qp.WorkOrderManage;
import cn.it.bos.service.base.impl.GenericServiceImpl;
import cn.it.bos.service.qp.WorkOrderManageService;
import cn.it.page.PageRequestBean;
import cn.it.page.PageResponseBean;

@SuppressWarnings("all")
public class WorkOrderManageServiceImpl extends GenericServiceImpl implements WorkOrderManageService {

	/**
	 * 工作单的快速录入
	 */
	@Override
	public void saveOrUpdate(WorkOrderManage workOrderManage) {
		workOrderManageDao.saveOrUpdate(workOrderManage);
	}

	@Override
	public PageResponseBean pageQuery(PageRequestBean pageRequestBean) {
		PageResponseBean pageQuery = super.pageQuery(pageRequestBean, workOrderManageDao);
		return pageQuery;
	}

	@Override
	public PageResponseBean pageQuery(PageRequestBean pageRequestBean, String conditionName, String conditionValue) {
		PageResponseBean pageResponseBean = new PageResponseBean();
		List<WorkOrderManage> data = null;
		Long total = null;
		// 从哪条记录开始
		int firstResult = (pageRequestBean.getPage() - 1) * pageRequestBean.getRows();
		// 最大的记录数
		int maxResults = pageRequestBean.getRows();

		data = workOrderManageDao.findByCriteria(conditionName, conditionValue, firstResult, maxResults);
		if (data == null || data.size() <= 0)
			return null;
		total = (long) workOrderManageDao.getFullTextQuery(conditionName, conditionValue).getResultSize();

		pageResponseBean.setTotal(total);
		pageResponseBean.setRows(data);

		return pageResponseBean;
	}

}
