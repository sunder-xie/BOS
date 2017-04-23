package cn.it.bos.service.auth.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import cn.it.bos.domain.auth.Function;
import cn.it.bos.domain.auth.Role;
import cn.it.bos.service.auth.RoleService;
import cn.it.bos.service.base.FacadeService;

@Service("roleService")
public class RoleServiceImpl implements RoleService {
	@Autowired
	private FacadeService facadeService;

	@Override
	@Cacheable(key="#username",value="role")
	public Set<String> findRoles(String username) {
		return facadeService.getRoleDao().findRoles(username);
	}

	@Override
	@CacheEvict(value="role",allEntries=true)
	public void save(Role role, String functionIds) {
		
		facadeService.getRoleDao().saveAndFlush(role);
		String[] fIds = functionIds.split(",");
		Set<Function> functions = role.getFunctions();
		if(fIds!=null&&fIds.length!=0){
			for (String fid : fIds) {
				Function function = new Function();
				function.setId(fid);
				functions.add(function);
			}
		}
	}

	@Override
	@Cacheable(key="#pageRequestBean.pageNumber+'_'+#pageRequestBean.pageSize",value="role")
	public Page<Role> pageQuery(Specification<Role> specification, PageRequest pageRequest) {
		// TODO Auto-generated method stub
		return facadeService.getRoleDao().findAll(specification, pageRequest);
	}

	@Override
	@Cacheable(value="role")
	public List<Role> ajaxRoles() {
		// TODO Auto-generated method stub
		return  facadeService.getRoleDao().findAll();
	}

}
