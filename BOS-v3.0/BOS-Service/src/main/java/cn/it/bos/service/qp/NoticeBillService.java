package cn.it.bos.service.qp;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import cn.it.bos.domain.bc.Standard;
import cn.it.bos.domain.qp.NoticeBill;
import cn.it.bos.service.base.BaseService;
import cn.it.crm.domain.Customer;

public interface NoticeBillService extends BaseService{

	void save(NoticeBill noticeBill);

	Page<NoticeBill> pageQuery(Specification<NoticeBill> specification,PageRequest pageRequestBean);

	void doRepeat(String[] ids);

	Customer ajaxTel(String tel);

}
