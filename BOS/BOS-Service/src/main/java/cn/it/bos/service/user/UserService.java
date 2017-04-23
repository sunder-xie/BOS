package cn.it.bos.service.user;

import cn.it.bos.domain.user.User;
import cn.it.bos.service.base.GenericService;

public interface UserService extends GenericService{

	User login(User user);

	public void editpassword(User user);

}
