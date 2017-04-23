package cn.it.bos.service.base.impl;

import org.activiti.engine.ProcessEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import cn.it.bos.dao.auth.FunctionDao;
import cn.it.bos.dao.auth.RoleDao;
import cn.it.bos.dao.bc.DecidedZoneDao;
import cn.it.bos.dao.bc.RegionDao;
import cn.it.bos.dao.bc.StaffDao;
import cn.it.bos.dao.bc.StandardDao;
import cn.it.bos.dao.bc.SubareaDao;
import cn.it.bos.dao.qp.NoticeBillDao;
import cn.it.bos.dao.qp.WorkBillDao;
import cn.it.bos.dao.qp.WorkOrderManageDao;
import cn.it.bos.dao.user.UserDao;
import cn.it.bos.dao.zm.ZmZhongzhuaninfoDao;
import cn.it.bos.service.base.BaseService;
import cn.it.bos.service.base.FacadeService;

/**
 * 抽取的通用service层,注入dao
 * 
 * @author xieshengrong
 *
 */
@SuppressWarnings("all")
public abstract class BaseServiceImpl implements BaseService {
	@Autowired
	protected FacadeService facadeService;
	@Autowired
	protected RoleDao roleDao;
	@Autowired
	protected FunctionDao functionDao;
	@Autowired
	protected UserDao userDao;
	@Autowired    
	protected StandardDao standardDao;
	@Autowired
	protected StaffDao staffDao;
	@Autowired
	protected RegionDao regionDao;
	@Autowired
	protected SubareaDao subareaDao;
	@Autowired
	protected DecidedZoneDao decidedZoneDao;
	@Autowired
	protected NoticeBillDao noticeBillDao;
	@Autowired
	protected WorkBillDao workBillDao;
	@Autowired
	protected WorkOrderManageDao workOrderManageDao;
	
	@Autowired
	protected ProcessEngine pe;
	@Autowired
	protected ZmZhongzhuaninfoDao zhongzhuaninfoDao ;

	public <T> Page pageQuery(PageRequest pageRequestBean, JpaRepository basedao) {
		Page<T> list = basedao.findAll(pageRequestBean);
		return list;
	}
}
