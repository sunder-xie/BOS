package cn.it.bos.service.impl.qp;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import cn.it.bos.domain.qp.NoticeBill;
import cn.it.bos.domain.qp.WorkBill;
import cn.it.bos.service.base.impl.BaseServiceImpl;
import cn.it.bos.service.qp.WorkBillService;

@SuppressWarnings("all")
@Service("workBillService")
public class WorkBillServiceImpl extends BaseServiceImpl implements WorkBillService {

	@Override
	public Page<WorkBill> pageQuery(Specification specification, PageRequest pageRequestBean) {
		return facadeService.getWorkBillDao().findAll(specification, pageRequestBean);
	}

}
