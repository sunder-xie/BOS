package cn.it.bos.service.qp;


import java.util.List;

import cn.it.bos.domain.qp.NoticeBill;
import cn.it.bos.service.base.GenericService;
import cn.it.page.PageRequestBean;
import cn.it.page.PageResponseBean;

public interface NoticeBillService extends GenericService{

	void save(NoticeBill noticeBill);


	PageResponseBean pageQuery(PageRequestBean pageRequestBean);




}
