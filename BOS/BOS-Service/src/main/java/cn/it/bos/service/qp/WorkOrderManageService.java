package cn.it.bos.service.qp;



import cn.it.bos.domain.qp.WorkOrderManage;
import cn.it.bos.service.base.GenericService;
import cn.it.page.PageRequestBean;
import cn.it.page.PageResponseBean;

public interface WorkOrderManageService extends GenericService{

	void saveOrUpdate(WorkOrderManage workOrderManage);
	/**
	 * 分页查询
	 * @param pageRequestBean
	 * @return
	 */
	PageResponseBean pageQuery(PageRequestBean pageRequestBean);

	/**
	 * lunece搜索分页查询
	 * @param pageRequestBean
	 * @param conditionName
	 * @param conditionValue
	 * @return
	 */
	PageResponseBean pageQuery(PageRequestBean pageRequestBean, String conditionName, String conditionValue);




}
