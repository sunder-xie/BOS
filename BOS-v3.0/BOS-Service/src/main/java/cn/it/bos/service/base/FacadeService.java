package cn.it.bos.service.base;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

@Service
public class FacadeService {
	
	
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private FunctionDao functionDao;
	@Autowired
	private UserDao userDao;
	@Autowired    
	private StandardDao standardDao;
	@Autowired
	private StaffDao staffDao;
	@Autowired
	private RegionDao regionDao;
	@Autowired
	private SubareaDao subareaDao;
	@Autowired
	private DecidedZoneDao decidedZoneDao;
	@Autowired
	private NoticeBillDao noticeBillDao;
	@Autowired
	private WorkBillDao workBillDao;
	@Autowired
	private WorkOrderManageDao workOrderManageDao;

	public UserDao getUserDao() {
		return userDao;
	}

	public StandardDao getStandardDao() {
		return standardDao;
	}

	public StaffDao getStaffDao() {
		return staffDao;
	}

	public RegionDao getRegionDao() {
		return regionDao;
	}

	public SubareaDao getSubareaDao() {
		return subareaDao;
	}

	public DecidedZoneDao getDecidedZoneDao() {
		return decidedZoneDao;
	}

	public NoticeBillDao getNoticeBillDao() {
		return noticeBillDao;
	}

	public WorkBillDao getWorkBillDao() {
		return workBillDao;
	}

	public WorkOrderManageDao getWorkOrderManageDao() {
		return workOrderManageDao;
	}
	public RoleDao getRoleDao() {
		return roleDao;
	}

	public FunctionDao getFunctionDao() {
		return functionDao;
	}
}
