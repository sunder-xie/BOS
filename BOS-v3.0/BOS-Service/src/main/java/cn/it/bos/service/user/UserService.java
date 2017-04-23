package cn.it.bos.service.user;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import cn.it.bos.domain.auth.Function;
import cn.it.bos.domain.user.User;
import cn.it.bos.service.base.BaseService;

public interface UserService extends BaseService{

	User login(User user);

	public void editpassword(User user);

	User findByUsername(String username);

	List<User> ajaxPhone(String telephone);

	User save(User user);

	Page<User> pageQuery(Specification<User> specification, PageRequest pageRequest);

}
