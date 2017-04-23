package cn.it.bos.service.bc;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import cn.it.bos.domain.bc.Region;
import cn.it.bos.service.base.BaseService;

public interface RegionService extends BaseService{

	public void save(Region region);

	public Page<Region> pageQuery(PageRequest pageRequestBean,Specification<Region> specification );

	public void deleteById(String[] ids);

	public List<Region> queryRegion(String p);

	public Region findRegion(Region region);

	public Region ajaxId(String id);

	public Region ajaxPostcode(String postId);


}
