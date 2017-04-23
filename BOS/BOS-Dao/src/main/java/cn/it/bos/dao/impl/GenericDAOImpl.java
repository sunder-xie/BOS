package cn.it.bos.dao.impl;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.WildcardQuery;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.impl.FullTextSessionImpl;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.it.bos.dao.GenericDAO;

@SuppressWarnings("all")
public class GenericDAOImpl<T> extends HibernateDaoSupport implements GenericDAO<T> {



	private Class clazz;

	public GenericDAOImpl() {
		try {
			ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
			clazz = (Class) type.getActualTypeArguments()[0];
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void save(T obj) {
		this.getHibernateTemplate().save(obj);
	}

	@Override
	public void delete(T obj) {
		this.getHibernateTemplate().delete(obj);
	}

	@Override
	public void update(T obj) {
		this.getHibernateTemplate().update(obj);
	}

	@Override
	public T findById(Serializable id) {
		/**
		 * 通过传入的类名去获取类型
		 */
		try {
			return (T) this.getHibernateTemplate().get(clazz, id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<T> findAll() {
		List<T> list = this.getHibernateTemplate().find("from " + clazz.getSimpleName());
		if (list != null && list.size() > 0)
			return list;
		return null;
	}

	@Override
	public List<T> findByCriteria(DetachedCriteria detachedcriteria) {
		List<T> list = this.getHibernateTemplate().findByCriteria(detachedcriteria);
		if (list != null && list.size() > 0)
			return list;
		return null;

	}

	@Override
	public List<T> findByName(String queryName, Object... objects) {
		List<T> list = this.getHibernateTemplate().findByNamedQuery(queryName, objects);
		if (list != null && list.size() > 0)
			return list;
		return null;

	}

	@Override
	public Long findTotalCount(DetachedCriteria detachedCriteria) {
		// 设置获取总记录数
		// select count(*) from
		detachedCriteria.setProjection(Projections.rowCount());
		List<Long> list = this.getHibernateTemplate().findByCriteria(detachedCriteria, 0, 1);
		if (list != null && list.size() > 0)
			return list.get(0);
		return null;

	}

	@Override
	public List<T> findByCriteria(DetachedCriteria detachedCriteria, int firstResult, int maxResults) {
		List<T> list = this.getHibernateTemplate().findByCriteria(detachedCriteria, firstResult, maxResults);
		if (list != null && list.size() > 0)
			return list;
		return null;

	}

	/**
	 * lunece搜索查询
	 */
	@Override
	public List<T> findByCriteria(String conditionName, String conditionValue, int firstResult, int maxResults) {
		// 获取session
		FullTextQuery fullTextQuery = getFullTextQuery(conditionName, conditionValue);

		List<T> list = fullTextQuery.setFirstResult(firstResult).setMaxResults(maxResults).list();
		if (list != null && list.size() > 0)
			return list;
		return null;
	}

	public FullTextQuery getFullTextQuery(String conditionName, String conditionValue) {
		// Hibernate Search 编程步骤
		// 1、 获得Session
		Session session = this.getSession();
		// 2、 获得全文检索Session
		FullTextSession fullTextSession = new FullTextSessionImpl(session);
		// 3、编写lucene的Query对象 （词条模糊搜索）
		Query query = new WildcardQuery(new Term(conditionName, "*" + conditionValue + "*"));
		// 4、获得全文检索的Query
		FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery(query);
		return fullTextQuery;
	}

	@Override
	public void saveOrUpdate(T obj) {
		this.getHibernateTemplate().saveOrUpdate(obj);
	}
	
}
