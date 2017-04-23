package cn.it.bos.service.base.impl;

import java.util.List;

import javax.annotation.Resource;

import cn.it.bos.dao.GenericDAO;
import cn.it.bos.dao.bc.DecidedZoneDao;
import cn.it.bos.dao.bc.RegionDao;
import cn.it.bos.dao.bc.StaffDao;
import cn.it.bos.dao.bc.StandardDao;
import cn.it.bos.dao.bc.SubareaDao;
import cn.it.bos.dao.qp.NoticeBillDao;
import cn.it.bos.dao.qp.WorkBillDao;
import cn.it.bos.dao.qp.WorkOrderManageDao;
import cn.it.bos.dao.user.UserDao;
import cn.it.bos.service.base.GenericService;
import cn.it.page.PageRequestBean;
import cn.it.page.PageResponseBean;

/**
 * 抽取的通用service层,注入dao
 * 
 * @author xieshengrong
 *
 */
@SuppressWarnings("all")
public abstract class GenericServiceImpl implements GenericService {

	@Resource(name = "userDao")
	protected UserDao userDao;
	@Resource(name = "standardDao")
	protected StandardDao standardDao;
	@Resource(name = "staffDao")
	protected StaffDao staffDao;
	@Resource(name = "regionDao")
	protected RegionDao regionDao;
	@Resource(name = "subareaDao")
	protected SubareaDao subareaDao;
	@Resource(name = "decidedZoneDao")
	protected DecidedZoneDao decidedZoneDao;
	@Resource(name = "noticeBillDao")
	protected NoticeBillDao noticeBillDao;
	@Resource(name = "workBillDao")
	protected WorkBillDao workBillDao;
	@Resource(name = "workOrderManageDao")
	protected WorkOrderManageDao workOrderManageDao;
	
	

	public <T> PageResponseBean pageQuery(PageRequestBean pageRequestBean, GenericDAO<T> dao) {
		PageResponseBean pageResponseBean = new PageResponseBean();

		// 从哪条记录开始
		int firstResult = (pageRequestBean.getPage() - 1) * pageRequestBean.getRows();
		// 最大的记录数
		int maxResults = pageRequestBean.getRows();
		// 清除投影效果
		// pageRequestBean.getDetachedCriteria().setProjection(null);
		List<T> data = dao.findByCriteria(pageRequestBean.getDetachedCriteria(), firstResult, maxResults);
		if(data==null||data.size()<=0)return null;
		Long total = dao.findTotalCount(pageRequestBean.getDetachedCriteria());
		pageResponseBean.setTotal(total);
		pageResponseBean.setRows(data);

		return pageResponseBean;
	}

}
