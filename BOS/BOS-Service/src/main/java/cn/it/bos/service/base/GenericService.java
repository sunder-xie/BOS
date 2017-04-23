package cn.it.bos.service.base;
import cn.it.bos.dao.GenericDAO;
import cn.it.page.PageRequestBean;
import cn.it.page.PageResponseBean;

@SuppressWarnings("all")
public interface GenericService {
	
	public <T>PageResponseBean pageQuery(PageRequestBean pageRequestBean ,GenericDAO<T>  dao) ;
}
