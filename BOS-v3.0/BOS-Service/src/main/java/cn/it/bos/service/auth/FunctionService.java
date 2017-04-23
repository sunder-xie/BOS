package cn.it.bos.service.auth;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import cn.it.bos.domain.auth.Function;
import cn.it.bos.domain.qp.WorkBill;
import cn.it.bos.service.base.BaseService;



public interface FunctionService extends BaseService{

	Set<String> findPermissions(String username);

	List<Function> findParentFunction();
	Page<Function> pageQuery(Specification<Function> specification, PageRequest pageRequestBean);

	Function ajaxId(String id);

	void save(Function function);

	List<Function> ajaxPrivilege();

	List<Function> ajaxTreeMenu(String username);

}
