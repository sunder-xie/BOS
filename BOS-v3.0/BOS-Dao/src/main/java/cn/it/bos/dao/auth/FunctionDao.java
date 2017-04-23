package cn.it.bos.dao.auth;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import cn.it.bos.domain.auth.Function;

@Repository
public interface FunctionDao extends JpaRepository<Function, String>, JpaSpecificationExecutor<Function> {

	@Query(nativeQuery = true, value = "select af.code from auth_function af where af.id in "
			+ " (select rf.function_id from role_function rf where rf.role_id in "
			+ " (select ar.id from auth_role ar left join user_role ur on ar.id = ur.role_id join user u on u.id=ur.user_id"+
			" where u.username=?1))")
	Set<String> findPermissions(String username);

	@Query("from Function where generatemenu = 1  order by zindex asc")
	List<Function> findParentFunction();

	@Query("from Function f join fetch f.roles r  join fetch r.users u where u.username=?1 order by zindex asc")
	List<Function> findFunctionsByUsername(String username);

}
