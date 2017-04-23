package cn.it.bos.dao.auth;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import cn.it.bos.domain.auth.Role;

@Repository
public interface RoleDao extends JpaRepository<Role, String>, JpaSpecificationExecutor<Role> {

	@Query(nativeQuery = true, value = "select ar.code from auth_role as ar left join user_role as ur "
			+ "on ar.id = ur.role_id join user as u on u.id=ur.user_id where u.username=?1")
	Set<String> findRoles(String username);

}
