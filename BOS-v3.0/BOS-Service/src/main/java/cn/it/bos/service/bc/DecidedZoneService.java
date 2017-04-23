package cn.it.bos.service.bc;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import cn.it.bos.domain.bc.DecidedZone;
import cn.it.bos.domain.bc.Subarea;
import cn.it.bos.service.base.BaseService;

public interface DecidedZoneService  extends BaseService{

	public void save(DecidedZone decidedZone,String[]ids);

	public Page<DecidedZone>  pageQuery(PageRequest pageRequestBean,Specification<DecidedZone> specification);

	public void deleteById(String[] ids);
//	
	public List<Subarea> findSubareaById(String id);

	public DecidedZone ajaxId(String id);

}
