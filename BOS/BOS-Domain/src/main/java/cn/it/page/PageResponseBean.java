package cn.it.page;

import java.util.List;

/**
 *	1,分页封装响应参数信息 
 *	2，tatal,总记录数，rows 当前页总记录数
 */
public class PageResponseBean {
	private Long total;  //总记录数
	private List rows;//当前页总记录数
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public List getRows() {
		return rows;
	}
	public void setRows(List rows) {
		this.rows = rows;
	}
	@Override
	public String toString() {
		return "PageResponseBean [total=" + total + ", rows=" + rows + "]";
	}
}
