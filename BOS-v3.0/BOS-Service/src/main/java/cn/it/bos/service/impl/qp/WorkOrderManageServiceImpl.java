package cn.it.bos.service.impl.qp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.activiti.engine.RuntimeService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import cn.it.bos.dao.zm.ZmZhongzhuaninfoDao;
import cn.it.bos.domain.qp.WorkOrderManage;
import cn.it.bos.domain.zm.ZmZhongzhuaninfo;
import cn.it.bos.service.base.FacadeService;
import cn.it.bos.service.base.impl.BaseServiceImpl;
import cn.it.bos.service.qp.WorkOrderManageService;

@SuppressWarnings("all")
@Service("workOrderManageService")
public class WorkOrderManageServiceImpl extends BaseServiceImpl implements WorkOrderManageService {

	@Autowired
	private FacadeService facadeService;
	@Autowired
	private EntityManagerFactory emf;

	/**
	 * 工作单的快速录入
	 */
	@Override
	@CacheEvict(allEntries = true, value = "workOrderManage")
	public void save(WorkOrderManage workOrderManage) {
		facadeService.getWorkOrderManageDao().save(workOrderManage);
	}

	@Override
	@Cacheable(key = "#pageRequestBean.pageNumber+'_'+#pageRequestBean.pageSize", value = "workOrderManage")
	public Page<WorkOrderManage> pageQuery(Specification spec, PageRequest pageRequestBean) {
		return facadeService.getWorkOrderManageDao().findAll(spec, pageRequestBean);
	}

	// /**
	// * lunece搜索查询
	// */
	private List<WorkOrderManage> findByCondit(String conditionName, String conditionValue, int firstResult,
			int maxResults) {
		// 获取session
		FullTextQuery fullTextQuery = getFullTextQuery(conditionName, conditionValue);

		List<WorkOrderManage> list = fullTextQuery.setFirstResult(firstResult).setMaxResults(maxResults)
				.getResultList();
		return list;
	}

	//
	private FullTextQuery getFullTextQuery(String conditionName, String conditionValue) {
		// Hibernate Search 编程步骤
		// 1、 获得Session

		EntityManager em = emf.createEntityManager();
		FullTextEntityManager fManager = Search.getFullTextEntityManager(em);
		Query query = null;
		QueryBuilder qb = fManager.getSearchFactory().buildQueryBuilder().forEntity(WorkOrderManage.class).get();
		if (conditionName.equals("wid")) {
			query = qb.keyword().onFields("wid").matching(conditionValue).createQuery();
		}
		if (conditionName.equals("arrivecity")) {
			query = qb.keyword().onFields("arrivecity").matching(conditionValue).createQuery();
		}
		if (conditionName.equals("product")) {
			query = qb.keyword().onFields("product").matching(conditionValue).createQuery();
		}
		// 2、 获得全文检索Session
		FullTextQuery fullTextQuery = fManager.createFullTextQuery(query, WorkOrderManage.class);
		// 3、编写lucene的Query对象 （词条模糊搜索）
		// 4、获得全文检索的Query
		return fullTextQuery;
	}

	@Override
	public Map<String, Object> pageQuery(Specification specification, PageRequest pageRequestBean, String conditionName,
			String conditionValue) {
		FullTextQuery fullTextQuery = getFullTextQuery(conditionName, conditionValue);
		// 从哪条记录开始
		int firstResult = (pageRequestBean.getPageNumber()) * pageRequestBean.getPageSize();
		// 最大的记录数
		int maxResults = pageRequestBean.getPageSize();

		List<WorkOrderManage> resultList = fullTextQuery.setFirstResult(firstResult).setMaxResults(maxResults)
				.getResultList();
		Long total = (long) fullTextQuery.getResultSize();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", total);
		map.put("rows", resultList);
		return map;
	}

	/**
	 * 查询所有未审核的工作单
	 */
	@Override
	public List<WorkOrderManage> check() {

		return facadeService.getWorkOrderManageDao().listUnChecked();
	}

	/**
	 * 工作单审核,
	 * 
	 * 1,审核工作单,把审核标记置为1, 2,开启工单流程,设置流程变量
	 * 
	 */
	@Override
	public void workordermanage_check(WorkOrderManage workOrderManage) {
		// 把工作单的审核标记设置为已审核
		WorkOrderManage orderManage = workOrderManageDao.findOne(workOrderManage.getId());
		orderManage.setManagerCheck("1");
		// 开启工单流程
		// 在启动流程时，关联流程实例 对应 全局 中转信息对象
		ZmZhongzhuaninfo zhongZhuanInfo = new ZmZhongzhuaninfo();
		zhongZhuanInfo.setArrive("0");// 未到达
		zhongZhuanInfo.setWorkordermanage(workOrderManage);// 关联工作单 信息
		ZmZhongzhuaninfo save = zhongzhuaninfoDao.save(zhongZhuanInfo);
		
		//设置流程变量,保存中转流程信息
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("zhongZhuanInfo", zhongZhuanInfo);

		//开启流程
		String processDefinitionKey="zhongzhuan";
		pe.getRuntimeService().startProcessInstanceByKey(processDefinitionKey, variables);

	}

}
