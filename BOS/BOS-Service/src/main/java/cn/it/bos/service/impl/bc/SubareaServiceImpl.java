package cn.it.bos.service.impl.bc;

import java.util.ArrayList;
import java.util.List;

import cn.it.bos.domain.bc.Subarea;
import cn.it.bos.service.base.impl.GenericServiceImpl;
import cn.it.bos.service.bc.SubareaService;
import cn.it.page.PageRequestBean;
import cn.it.page.PageResponseBean;
@SuppressWarnings("all")
public class SubareaServiceImpl extends GenericServiceImpl implements SubareaService {

	@Override
	public void save(Subarea subarea) {
		subareaDao.saveOrUpdate(subarea);
	}

	@Override
	public void deleteById(String[] ids) {
		if (ids != null || ids.length > 0) {
			for (String id : ids) {
				Subarea querySubarea = (Subarea) subareaDao.findById(id);
				subareaDao.delete(querySubarea);
			}
		} 
	}

	@Override
	public PageResponseBean pageQuery(PageRequestBean pageRequestBean) {

		return super.pageQuery(pageRequestBean, subareaDao);
	}


}
