package cn.it.bos.web.action.qp.test;


import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.it.bos.dao.bc.RegionDao;
import cn.it.bos.domain.user.User;
import cn.it.bos.service.impl.user.UserServiceImpl;
import cn.it.bos.service.user.UserService;

@RunWith(SpringJUnit4ClassRunner.class)  //使用junit4进行测试  
@ContextConfiguration   
		({"classpath:applicationContext-Web.xml",
		  "classpath:applicationContext-Service.xml",
		  "classpath:applicationContext.xml",
		  "classpath:applicationContext-Dao.xml",
		  "classpath:applicationContext-Domain.xml"})
public class QbActionTest {

//	@Autowired
//	public FacadeService facadeService;
//	UserService userService = new UserServiceImpl();
//	
//	
//	@Test
//	public void test1() {
//		User u = new User();
//		u.setUsername("admin");
//		u.setPassword("123");
//		User login = userService.login(u);
//		System.out.println(login);
//	}
	 
}
