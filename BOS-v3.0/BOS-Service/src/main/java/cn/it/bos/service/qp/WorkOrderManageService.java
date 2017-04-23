package cn.it.bos.service.qp;



import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import cn.it.bos.domain.qp.WorkOrderManage;
import cn.it.bos.service.base.BaseService;

public interface WorkOrderManageService extends BaseService{

	void save(WorkOrderManage workOrderManage);
	/**
	 * 分页查询
	 * @param pageRequestBean
	 * @return
	 */
	Page<WorkOrderManage> pageQuery(Specification<WorkOrderManage> specification,PageRequest pageRequestBean);

	/**
	 * lunece搜索分页查询
	 * @param pageRequestBean
	 * @param conditionName
	 * @param conditionValue
	 * @return
	 */

	Map<String,Object> pageQuery(Specification specification, PageRequest pageRequestBean, String conditionName,
			String conditionValue);

	List<WorkOrderManage> check();
	void workordermanage_check(WorkOrderManage workOrderManage);



}
