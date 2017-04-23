package cn.it.bos.service.impl.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import cn.it.bos.domain.auth.Function;
import cn.it.bos.domain.user.User;
import cn.it.bos.service.base.FacadeService;
import cn.it.bos.service.base.impl.BaseServiceImpl;
import cn.it.bos.service.user.UserService;
import cn.it.bos.utils.MD5Utils;

@SuppressWarnings("all")
@Service("userService")
public class UserServiceImpl extends BaseServiceImpl implements UserService {

	@Autowired
	private FacadeService facadeService;

	@Override
	public User login(User user) {
		// 需要通过名称查询
		String username = user.getUsername();
		 String password = MD5Utils.md5(user.getPassword());
		User loginUser = facadeService.getUserDao().findByName(username,password);
		return loginUser;
	}

	@Override
	@CacheEvict(value="user",allEntries=true)
	public void editpassword(User user) {
		User resultUser = facadeService.getUserDao().findOne(user.getId());
		resultUser.setPassword(MD5Utils.md5(user.getPassword()));
	}

	@Override
	public User findByUsername(String username) {
		return facadeService.getUserDao().findByUsername(username);
	}

	@Override
	public List<User> ajaxPhone(String telephone) {
		// TODO Auto-generated method stub
		return facadeService.getUserDao().findByTelephone(telephone);
	}

	@Override
	@CacheEvict(value="user",allEntries=true)
	public User save(User user) {
		return facadeService.getUserDao().saveAndFlush(user);
	}

	@Override
	@Cacheable(key="#pageRequestBean.pageNumber+'_'+#pageRequestBean.pageSize",value="user")
	public Page<User> pageQuery(Specification<User> specification, PageRequest pageRequest) {
		// TODO Auto-generated method stub
		return facadeService.getUserDao().findAll(specification, pageRequest);
	}

}
