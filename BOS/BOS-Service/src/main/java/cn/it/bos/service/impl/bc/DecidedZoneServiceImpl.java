package cn.it.bos.service.impl.bc;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import cn.it.bos.domain.bc.DecidedZone;
import cn.it.bos.domain.bc.Subarea;
import cn.it.bos.service.base.impl.GenericServiceImpl;
import cn.it.bos.service.bc.DecidedZoneService;
import cn.it.page.PageRequestBean;
import cn.it.page.PageResponseBean;

@SuppressWarnings("all")
public class DecidedZoneServiceImpl extends GenericServiceImpl implements DecidedZoneService {

	@Override
	public void save(DecidedZone decidedZone, String[] ids) {
		// 只需要保存，
		// decidedZoneDao.saveOrUpdate(decidedZone);
		decidedZoneDao.save(decidedZone);

		/**
		 * 两张表需要关联，用多的一方关联一的一方
		 */
		if (ids != null && ids.length > 0) {
			for (String id : ids) {
				Subarea subarea = (Subarea) subareaDao.findById(id);
				subarea.setDecidedZone(decidedZone);
			}
		}
	}

	@Override
	public void deleteById(String[] ids) {
		if (ids != null || ids.length > 0) {
			for (String id : ids) {
				DecidedZone queryDecidedZone = (DecidedZone) decidedZoneDao.findById(id);
				// 当有关联数据，外键约束时候，先解除外键约束再删除数据
				List<Subarea> subareas = subareaDao.findByCriteria(
						DetachedCriteria.forClass(Subarea.class).add(Restrictions.eq("decidedZone", queryDecidedZone)));
				if (subareas != null && subareas.size() > 0) {
					for (Subarea subarea : subareas) {
						// 把外键约束删除
						subarea.setDecidedZone(null);
					}
				}
				decidedZoneDao.delete(queryDecidedZone);
			}
		}
	}

	@Override
	public PageResponseBean pageQuery(PageRequestBean pageRequestBean) {
		PageResponseBean pageQuery = super.pageQuery(pageRequestBean, decidedZoneDao);
		return pageQuery;
	}

	@Override
	public List<Subarea> findSubareaById(String id) {
		DecidedZone queryDecidedZone = (DecidedZone) decidedZoneDao.findById(id);
		List<Subarea> subareaList = subareaDao.findByName("querySubarea", queryDecidedZone);
		if (subareaList != null && subareaList.size() > 0)
			return subareaList;
		return null;
	}
}
