package cn.it.bos.service.bc;


import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import cn.it.bos.domain.bc.Region;
import cn.it.bos.service.base.GenericService;
import cn.it.page.PageRequestBean;
import cn.it.page.PageResponseBean;

public interface RegionService extends GenericService{

	public void save(Region region);

	public PageResponseBean pageQuery(PageRequestBean pageRequestBean);

	public void deleteById(String[] ids);

	public List<Region> queryRegion(DetachedCriteria detachedCriteria);

	public Region findRegion(Region region);


}
