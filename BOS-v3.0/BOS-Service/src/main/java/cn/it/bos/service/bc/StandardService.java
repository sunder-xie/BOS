package cn.it.bos.service.bc;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import cn.it.bos.domain.bc.Standard;
import cn.it.bos.service.base.BaseService;

public interface StandardService extends BaseService{

	public void save(Standard standard);

	public Page<Standard>  pageQuery(PageRequest pageRequestBean,Specification<Standard> specification);

	public void deleteById(String[] ids);

	public List<Standard> findStandard(String deltag);

}
