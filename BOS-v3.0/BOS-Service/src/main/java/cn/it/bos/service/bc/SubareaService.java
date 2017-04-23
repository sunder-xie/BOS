package cn.it.bos.service.bc;



import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import cn.it.bos.domain.bc.DecidedZone;
import cn.it.bos.domain.bc.Staff;
import cn.it.bos.domain.bc.Standard;
import cn.it.bos.domain.bc.Subarea;
import cn.it.bos.service.base.BaseService;

public interface SubareaService extends BaseService {

	public void save(Subarea subarea);

	public Page<Subarea>  pageQuery(Specification<Subarea> spec,PageRequest pageRequestBean);

	public void deleteById(String[] ids);

	public List<Standard> findStandard(String deltag);

	public List<Subarea> findStandardByDecidedZone();

	public Subarea ajaxId(String id);


}
