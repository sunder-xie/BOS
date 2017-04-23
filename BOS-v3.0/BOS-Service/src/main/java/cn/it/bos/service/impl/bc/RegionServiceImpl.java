package cn.it.bos.service.impl.bc;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import cn.it.bos.domain.bc.Region;
import cn.it.bos.service.base.FacadeService;
import cn.it.bos.service.base.impl.BaseServiceImpl;
import cn.it.bos.service.bc.RegionService;

@SuppressWarnings("all")
@Service("regionService")
public class RegionServiceImpl extends BaseServiceImpl implements RegionService {
	private final Logger logger = Logger.getLogger(RegionServiceImpl.class);

	@Autowired
	private FacadeService facadeService;

	@Override
	@CacheEvict(allEntries=true,value="region")
	public void save(Region region) {
		try {
			facadeService.getRegionDao().save(region);
		} catch (Exception e) {
			logger.info("保存失败", e);
		}
	}

	@Override
	@CacheEvict(allEntries=true,value="region")
	public void deleteById(String[] ids) {
		if (ids != null || ids.length > 0) {
			for (String id : ids) {
				facadeService.getRegionDao().delete(id);
			}
		}
	}
	@Override
	@Cacheable(key="#pageRequestBean.pageNumber+'_'+#pageRequestBean.pageSize",value="region")
	public Page<Region> pageQuery(PageRequest pageRequestBean,Specification specification ) {
		return facadeService.getRegionDao().findAll(specification,pageRequestBean);
	}

	@Override
	public List<Region> queryRegion(String p) {
		if(StringUtils.isBlank(p))return facadeService.getRegionDao().findAll();
		return facadeService.getRegionDao().findRegionBySearchCode(p);
	}

	@Override
	public Region findRegion(Region region) {
		Region queryRegion = facadeService.getRegionDao().findOne(region.getId());
		return queryRegion;
	}

	@Override
	public Region ajaxId(String id) {
		
		return facadeService.getRegionDao().findOne(id);
	}

	@Override
	public Region ajaxPostcode(String postId) {
		return facadeService.getRegionDao().findRegionByPostcode(postId);
	}

}
