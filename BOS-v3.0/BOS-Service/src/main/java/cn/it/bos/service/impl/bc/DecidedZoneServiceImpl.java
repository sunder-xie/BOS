package cn.it.bos.service.impl.bc;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.transaction.Transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import cn.it.bos.domain.bc.DecidedZone;
import cn.it.bos.domain.bc.Subarea;
import cn.it.bos.service.base.FacadeService;
import cn.it.bos.service.base.impl.BaseServiceImpl;
import cn.it.bos.service.bc.DecidedZoneService;

@SuppressWarnings("all")
@Service("decidedZoneService")
public class DecidedZoneServiceImpl extends BaseServiceImpl implements DecidedZoneService {

	@Autowired
	private FacadeService facadeService;
	@Autowired
	private EntityManagerFactory  emf;
	@Override
	@CacheEvict(allEntries=true,value="decidedZone")
	public void save(DecidedZone decidedZone, String[] ids) {
		/**
		 * 两张表需要关联，用多的一方关联一的一方
		 */
		EntityManager em = emf.createEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			facadeService.getDecidedZoneDao().saveAndFlush(decidedZone);
			if (ids != null && ids.length > 0) {
				for (String id : ids) {
					//优化,这里先查在更新,执行了两次sql
//					Subarea subarea = facadeService.getSubareaDao().findOne(id);
//					subarea.setDecidedZone(decidedZone);
					facadeService.getSubareaDao().updateSubareaDecidedZoneById(decidedZone,id);
				}
			}
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
		}
	}

	
	/**
	 * 需求:如果有关联关系的需要先给出提示框,确认后再删除
	 */
	@Override
	@CacheEvict(allEntries=true,value="decidedZone")
	public void deleteById(String[] ids) {
		if (ids != null || ids.length > 0) {
			for (String id : ids) {
				//托管态去删除
				DecidedZone decideZone = new DecidedZone(id);
				facadeService.getDecidedZoneDao().delete(decideZone);
				decideZone = null;
			}
		}
	}

	@Override
	@Cacheable(key="#pageRequestBean.pageNumber+'_'+#pageRequestBean.pageSize",value="decidedZone")
	public Page<DecidedZone> pageQuery(PageRequest pageRequestBean,Specification specification) {
		return facadeService.getDecidedZoneDao().findAll(specification, pageRequestBean);
	}

	@Override
	public List<Subarea> findSubareaById(String id) {
		DecidedZone queryDecidedZone = facadeService.getDecidedZoneDao().findOne(id);
		List<Subarea> subareaList = facadeService.getSubareaDao().queryList(queryDecidedZone);
		return subareaList; 
	}

	@Override
	public DecidedZone ajaxId(String id) {
		return facadeService.getDecidedZoneDao().findOne(id);
	}
}
