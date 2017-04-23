package cn.it.page;

import java.io.Serializable;

import org.hibernate.criterion.DetachedCriteria;

/**
 * 1,分页封装请求参数信息。
 * 2，当前页数page, 和每页记录数  rows 
 *
 */
public class PageRequestBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private int page;
	private int rows;
//	需要提供条件查询的方式，hibernate可以利用面向对象的对象DetachedCriteria
//	如果不用hibernate，需要拼接sql语句，利用limit关键字
	private DetachedCriteria detachedCriteria;

	
	
	
	public PageRequestBean(int page, int rows, DetachedCriteria detachedCriteria) {
	super();
	this.page = page;
	this.rows = rows;
	this.detachedCriteria = detachedCriteria;
}

	public PageRequestBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "PageRequestBean [page=" + page + ", rows=" + rows + ", detachedCriteria=" + detachedCriteria + "]";
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public DetachedCriteria getDetachedCriteria() {
		return detachedCriteria;
	}

	public void setDetachedCriteria(DetachedCriteria detachedCriteria) {
		this.detachedCriteria = detachedCriteria;
	}
}
