package cn.it.bos.service.impl.bc;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import cn.it.bos.domain.bc.Staff;
import cn.it.bos.domain.bc.Standard;
import cn.it.bos.service.base.impl.GenericServiceImpl;
import cn.it.bos.service.bc.StaffService;
import cn.it.page.PageRequestBean;
import cn.it.page.PageResponseBean;

@SuppressWarnings("all")
public class StaffServiceImpl extends GenericServiceImpl implements StaffService {

	@Override
	public void save(Staff staff) {
		staffDao.saveOrUpdate(staff);
	}

	@Override
	public void deleteById(String[] ids) {
		if (ids != null || ids.length > 0) {
			for (String id : ids) {
				Staff queryStaff = (Staff) staffDao.findById(id);
				queryStaff.setDeltag("0");
			}
		}
	}

	@Override
	public Standard findStandardById(Standard standard) {
		Standard queryStandard = (Standard) standardDao.findById(standard.getId());
		return queryStandard;
	}

	@Override
	public PageResponseBean pageQuery(PageRequestBean pageRequestBean) {
		PageResponseBean pageQuery = super.pageQuery(pageRequestBean, staffDao);
		return pageQuery;
	}

	@Override
	public List<Staff> findStandard(DetachedCriteria detachedCriteria) {
		List<Staff> staffList = staffDao.findByCriteria(detachedCriteria);
		if (staffList != null && staffList.size() > 0)
			return staffList;
		return null;
	}

}
