package cn.it.bos.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.search.FullTextQuery;

import cn.it.bos.domain.bc.Region;


/**
 * 定义一个通用的DAO,可以实现所有的增删改查功能 需要使用，泛型和反射技术。
 * 
 * 定义通用增删改差方法。
 * 
 * @param <T>
 *
 */
public interface GenericDAO<T> {

	/**
	 * 通用增加方法
	 */
	public void save(T obj);
	/**
	 * 通用删除方法
	 */
	public void delete(T obj);
	/**
	 * 通用修改方法
	 */
	public void update(T obj);
	
	/**
	 * 新增或保存
	 */
	public void saveOrUpdate(T obj);
	/**
	 * 通过主键查询
	 */
	public T findById(Serializable id);//实体类必须实现序列化接口
	/**
	 * 查询所有
	 */
	public List<T> findAll();
	/**
	 * 条件查询
	 */
	public List<T> findByCriteria(DetachedCriteria detachedcriteria);
	public List<T> findByName(String queryName , Object...objects);
	/**
	 *   分页查询，查询总记录数
	 */
	public Long findTotalCount(DetachedCriteria detachedCriteria);
	public List findByCriteria(DetachedCriteria detachedCriteria, int firstResult, int maxResults);
	/**
	 * 
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	public List findByCriteria(String conditionName, String conditionValue, int firstResult, int maxResults);
	
	public FullTextQuery getFullTextQuery(String conditionName, String conditionValue);
	
	
}
