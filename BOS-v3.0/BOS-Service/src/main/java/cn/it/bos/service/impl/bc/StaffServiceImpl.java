package cn.it.bos.service.impl.bc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import cn.it.bos.domain.bc.Staff;
import cn.it.bos.domain.bc.Standard;
import cn.it.bos.service.base.FacadeService;
import cn.it.bos.service.base.impl.BaseServiceImpl;
import cn.it.bos.service.bc.StaffService;

@SuppressWarnings("all")
@Service("staffService")
public class StaffServiceImpl extends BaseServiceImpl implements StaffService {

	@Autowired
	private FacadeService facadeService;
	
	@Override
	@CacheEvict(allEntries=true,value="staff")
	public void save(Staff staff) {
		facadeService.getStaffDao().save(staff);
	}

	@Override
	@CacheEvict(allEntries=true,value="staff")
	public void deleteById(String[] ids) {
		//删除标记 deltag 1有效  0无效
		String deltag="0";
		if (ids != null || ids.length > 0) {
			for (String id : ids) {
				facadeService.getStaffDao().updata(deltag,id);
			}
		}
	}

	@Override
	public Standard findStandardById(Standard standard) {
		Standard queryStandard = facadeService.getStandardDao().findOne(standard.getId());
		return queryStandard;
	}

	@Cacheable(key="#pageRequestBean.pageNumber+'_'+#pageRequestBean.pageSize",value="staff")
	public Page<Staff> pageQuery(PageRequest pageRequestBean,Specification specification  ) {
		Page<Staff> staffList = facadeService.getStaffDao().findAll(specification,pageRequestBean);
		return staffList;
	}

	@Override
	public List<Staff> findStandard(String deltag) {
		return  facadeService.getStaffDao().findStaff(deltag);
	}

	@Override
	public List<Staff> ajaxPhone(String telephone) {
		return facadeService.getStaffDao().ajaxPhone(telephone);
	}

	@Override
	public Staff ajaxId(String id) {
		return  facadeService.getStaffDao().findOne(id);
	}

}
