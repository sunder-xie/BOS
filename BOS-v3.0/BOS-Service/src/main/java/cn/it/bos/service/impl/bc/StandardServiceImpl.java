package cn.it.bos.service.impl.bc;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import cn.it.bos.domain.bc.Standard;
import cn.it.bos.service.base.FacadeService;
import cn.it.bos.service.base.impl.BaseServiceImpl;
import cn.it.bos.service.bc.StandardService;

@SuppressWarnings("all")
@Service("standardService")
public class StandardServiceImpl extends BaseServiceImpl implements StandardService {

	@Autowired
	private FacadeService facadeService;
	
	@Override
	@CacheEvict(allEntries=true,value="standard")
	public void save(Standard standard) {
		facadeService.getStandardDao().save(standard);
	}

	@Override
	@CacheEvict(allEntries=true,value="standard")
	public void deleteById(String[] ids) {
		String deltag = "0";
		if (ids != null || ids.length > 0) {
			for (String id : ids) {
				facadeService.getStandardDao().updata(id,deltag);
			}
		}
	}

	@Override
	@Cacheable(key="#pageRequestBean.pageNumber+'_'+#pageRequestBean.pageSize",value="standard")
	public Page<Standard> pageQuery(PageRequest pageRequestBean,Specification specification) {
		  return  facadeService.getStandardDao().findAll( specification ,pageRequestBean);
	}

	public List<Standard> findStandard(String deltag) {
		return facadeService.getStandardDao().findStandard(deltag);
	}

}
