package cn.it.bos.service.auth.impl;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import cn.it.bos.domain.auth.Function;
import cn.it.bos.service.auth.FunctionService;
import cn.it.bos.service.base.FacadeService;

@Service("functionService")
public class FunctionServiceImpl implements FunctionService {
	@Autowired
	private FacadeService facadeService;

	@Override
	public Set<String> findPermissions(String username) {
		// TODO Auto-generated method stub
		return facadeService.getFunctionDao().findPermissions(username);
	}

	@Override
	public List<Function> findParentFunction() {
		// TODO Auto-generated method stub
		return facadeService.getFunctionDao().findParentFunction();
	}

	@Override
	@Cacheable(key="#pageRequestBean.pageNumber+'_'+#pageRequestBean.pageSize",value="function")
	public Page<Function> pageQuery(Specification<Function> specification, PageRequest pageRequestBean) {
		// TODO Auto-generated method stub
		return facadeService.getFunctionDao().findAll(specification, pageRequestBean);
	}

	@Override
	public Function ajaxId(String id) {
		// TODO Auto-generated method stub
		return facadeService.getFunctionDao().findOne(id);
	}

	@Override
	@CacheEvict(value="function",allEntries=true)
	public void save(Function function) {

		if (function.getFunction() != null && StringUtils.isBlank(function.getFunction().getId())) {
			function.setFunction(null);
		}

		facadeService.getFunctionDao().saveAndFlush(function);
	}

	@Override
	@Cacheable(value="function")
	public List<Function> ajaxPrivilege() {
		// TODO Auto-generated method stub
		return facadeService.getFunctionDao().findAll();
	}

	@Override
	@Cacheable(key="#username",value="function1")
	public List<Function> ajaxTreeMenu(String username) {
		if ("admin".equals(username)) {
			List<Function> findAll = facadeService.getFunctionDao().findAll();
			for (Function function : findAll) {
				Hibernate.initialize(function);
			}
			return findAll;
		}
		return facadeService.getFunctionDao().findFunctionsByUsername(username);
	}

}
