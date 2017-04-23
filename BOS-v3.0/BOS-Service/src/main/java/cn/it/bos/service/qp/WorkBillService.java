package cn.it.bos.service.qp;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import cn.it.bos.domain.qp.WorkBill;
import cn.it.bos.service.base.BaseService;

public interface WorkBillService extends BaseService{

	Page<WorkBill> pageQuery(Specification<WorkBill> specification, PageRequest pageRequestBean);
	



}
