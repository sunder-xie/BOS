package cn.it.bos.service.impl.bc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import cn.it.bos.domain.bc.DecidedZone;
import cn.it.bos.domain.bc.Staff;
import cn.it.bos.domain.bc.Standard;
import cn.it.bos.domain.bc.Subarea;
import cn.it.bos.service.base.FacadeService;
import cn.it.bos.service.base.impl.BaseServiceImpl;
import cn.it.bos.service.bc.SubareaService;
@SuppressWarnings("all")
@Service("subareaService")
public class SubareaServiceImpl extends BaseServiceImpl implements SubareaService {

	@Autowired
	private FacadeService facadeService;
	
	@Override
	@CacheEvict(allEntries=true,value="subarea")
	public void save(Subarea subarea) {
		facadeService.getSubareaDao().save(subarea);
	}

	@Override
	@CacheEvict(allEntries=true,value="subarea")
	public void deleteById(String[] ids) {
		if (ids != null || ids.length > 0) {
			for (String id : ids) {
				facadeService.getSubareaDao().delete(id);
			}
		} 
	}

	@Override
	@Cacheable(key="#pageRequestBean.pageNumber+'_'+#pageRequestBean.pageSize",value="subarea")
	public Page<Subarea> pageQuery(Specification spec ,PageRequest pageRequestBean) {
		return facadeService.getSubareaDao().findAll(spec, pageRequestBean);
	}

	@Override
	public List<Standard> findStandard(String sb) {
		List<Standard> standardList = facadeService.getStandardDao().findStandard(sb);
		return standardList;
	}

	@Override
	public List<Subarea> findStandardByDecidedZone() {
		return facadeService.getSubareaDao().findStandardByDecidedZone();
	}

	@Override
	public Subarea ajaxId(String id) {
		return facadeService.getSubareaDao().findOne(id);
	}


}
