package cn.it.bos.service.impl.user;

import java.util.List;

import cn.it.bos.domain.user.User;
import cn.it.bos.service.base.impl.GenericServiceImpl;
import cn.it.bos.service.user.UserService;
import cn.it.bos.utils.MD5Utils;



@SuppressWarnings("all")
public class UserServiceImpl extends GenericServiceImpl implements UserService {

	@Override
	public User login(User user) {
		// 需要通过名称查询
		List<User> loginUserList = userDao.findByName("User.login",
				user.getUsername(),
				MD5Utils.md5(user.getPassword()));
		if(loginUserList!=null&& loginUserList.size()>0)return loginUserList.get(0);
		return null;
	}

	@Override
	public void editpassword(User user) {
		User resultUser = (User) userDao.findById(user.getId());
		resultUser.setPassword(MD5Utils.md5(user.getPassword()));
	}

}
