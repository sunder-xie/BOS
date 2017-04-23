package cn.it.bos.dao.user;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cn.it.bos.domain.qp.NoticeBill;
import cn.it.bos.domain.user.User;

/**
 * 和springJPA耦合性太高,可以拆分出来一个
 * 
 * @NoRepositoryBean public interface EntityRepository<T, ID extends
 *                   Serializable> extends Repository<T, ID> { }
 * 
 *                   再设计一个借口,
 *
 */

@Repository
public interface UserDao extends JpaRepository<User, String>,JpaSpecificationExecutor<User>  {

	// 通过用户名和密码查询
	@Query(value = "from User where username = (:username) and password = (:password)")
	User findByName(@Param("username") String username, @Param("password") String md5);
	@Query(value = "from User where username = ?1")
	User findByUsername(String username);
	@Query(value = "from User where telephone = ?1")
	List<User> findByTelephone(String telephone);

}
