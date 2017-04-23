package cn.it.bos.service.impl.bc;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import cn.it.bos.domain.bc.Standard;
import cn.it.bos.service.base.impl.GenericServiceImpl;
import cn.it.bos.service.bc.StandardService;
import cn.it.page.PageRequestBean;
import cn.it.page.PageResponseBean;

@SuppressWarnings("all")
public class StandardServiceImpl extends GenericServiceImpl implements StandardService {

	@Override
	public void save(Standard standard) {
		standardDao.saveOrUpdate(standard);
	}

	@Override
	public void deleteById(String[] ids) {
		if (ids != null || ids.length > 0) {
			for (String id : ids) {
				Standard queryStandard = (Standard) standardDao.findById(id);
				queryStandard.setDeltag("0");
			}
		}
	}

	@Override
	public PageResponseBean pageQuery(PageRequestBean pageRequestBean) {

		return super.pageQuery(pageRequestBean, standardDao);
	}

	@Override
	public List<Standard> findStandard(DetachedCriteria detachedCriteria) {
		List<Standard> standardList = standardDao.findByCriteria(detachedCriteria);
		if (standardList != null && standardList.size() > 0)
			return standardList;
		return null;
	}
}
