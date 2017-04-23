package cn.it.bos.service.impl.bc;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;

import cn.it.bos.domain.bc.Region;
import cn.it.bos.service.base.impl.GenericServiceImpl;
import cn.it.bos.service.bc.RegionService;
import cn.it.page.PageRequestBean;
import cn.it.page.PageResponseBean;

@SuppressWarnings("all")
public class RegionServiceImpl extends GenericServiceImpl implements RegionService {
	private final Logger logger = Logger.getLogger(RegionServiceImpl.class);

	@Override
	public void save(Region region) {
		try {
			// regionDao.save(region);
			regionDao.saveOrUpdate(region);
		} catch (Exception e) {
			logger.info("保存失败", e);
		}
		// regionDao.save(region);

	}

	@Override
	public void deleteById(String[] ids) {
		if (ids != null || ids.length > 0) {
			for (String id : ids) {
				Region queryRegion = (Region) regionDao.findById(id);
				regionDao.delete(queryRegion);
			}
		}
	}

	@Override
	public PageResponseBean pageQuery(PageRequestBean pageRequestBean) {
		PageResponseBean pageQuery = super.pageQuery(pageRequestBean, regionDao);
		return pageQuery;
	}

	@Override
	public List<Region> queryRegion(DetachedCriteria detachedCriteria) {
		return regionDao.findAll();
	}

	@Override
	public Region findRegion(Region region) {
		List<Region> list = regionDao.findByName("findRegion", region.getId());
		if (list != null && list.size() > 0)
			return list.get(0);
		return null;
	}

	// @Override
	// public Region queryRegion(Region region) {
	// List<Region> list = regionDao.findByName("findRegion", region.getId());
	// return list.isEmpty() ? null : list.get(0);
	// }

}
