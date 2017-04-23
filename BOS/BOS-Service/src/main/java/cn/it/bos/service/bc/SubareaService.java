package cn.it.bos.service.bc;


import java.util.List;

import cn.it.bos.domain.bc.Subarea;
import cn.it.bos.service.base.GenericService;
import cn.it.page.PageRequestBean;
import cn.it.page.PageResponseBean;

public interface SubareaService extends GenericService {

	public void save(Subarea subarea);

	public PageResponseBean pageQuery(PageRequestBean pageRequestBean);

	public void deleteById(String[] ids);


}
