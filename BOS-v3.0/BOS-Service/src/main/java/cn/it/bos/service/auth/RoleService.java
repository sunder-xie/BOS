package cn.it.bos.service.auth;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import cn.it.bos.domain.auth.Role;
import cn.it.bos.service.base.BaseService;

public interface RoleService extends BaseService{

	Set<String> findRoles(String username);

	void save(Role role, String functionIds);

	Page<Role> pageQuery(Specification<Role> specification, PageRequest pageRequest);

	List<Role> ajaxRoles();

}
