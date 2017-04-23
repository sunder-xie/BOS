package cn.it.bos.process;

import java.util.zip.ZipInputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)  //使用junit4进行测试  
@ContextConfiguration ("classpath:applicationContext.xml")
public class ProcessTest {
	@Autowired
	ProcessEngineFactoryBean pe1;
	@Autowired
	ProcessEngine pe;
	@Test
	public void createTab(){
//		ZipInputStream zipInputStream = new ZipInputStream(this.getClass().getResourceAsStream("process1.zip"));
//		DeploymentBuilder builder = pe.getRepositoryService().createDeployment();//
//		builder.addZipInputStream(zipInputStream );
//		builder.deploy();
		
	}
	
	@Test
	public void detel(){
//		pe.getRepositoryService().deleteDeployment("");
	}

}
