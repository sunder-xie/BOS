package cn.it.bos.service.bpm.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;


import org.activiti.engine.ProcessEngine;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.it.bos.service.bpm.IProcessDefinitionService;


/**
 * 流程定义管理Service
 * 
 * @author zhaoqx
 * 
 */
@Service
@Transactional
public class ProcessDefinitionServiceImpl implements IProcessDefinitionService {
	@Autowired
	ProcessEngine processEngine;

	/**
	 * 查询最新版本的流程定义列表
	 */
	public List<ProcessDefinition> findLastList() {
		ProcessDefinitionQuery query = processEngine.getRepositoryService()
				.createProcessDefinitionQuery();
		// 按照版本升序排序
		query.orderByProcessDefinitionVersion().asc();
		List<ProcessDefinition> list = query.list();

		Map<String, ProcessDefinition> map = new HashMap<String, ProcessDefinition>();
		for (ProcessDefinition pd : list) {
			map.put(pd.getKey(), pd);
		}
		return new ArrayList<ProcessDefinition>(map.values());
	}

	/**
	 * 根据key删除流程定义
	 */
	public void deleteByKey(String key) {
		// 根据key查询流程定义列表
		ProcessDefinitionQuery query = processEngine.getRepositoryService()
				.createProcessDefinitionQuery();
		query.processDefinitionKey(key);
		List<ProcessDefinition> list = query.list();
		for (ProcessDefinition pd : list) {
			String deploymentId = pd.getDeploymentId();
			processEngine.getRepositoryService().deleteDeployment(deploymentId,
					true);
		}
	}

	/**
	 * 部署流程定义
	 */
	public void deploy(File resource) {
		DeploymentBuilder deploymentBuilder = processEngine
				.getRepositoryService().createDeployment();
		ZipInputStream zipInputStream = null;
		try {
			zipInputStream = new ZipInputStream(new FileInputStream(resource));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		deploymentBuilder.addZipInputStream(zipInputStream);
		deploymentBuilder.deploy();
	}

	/**
	 * 根据流程定义id获得png图片的输入流
	 */
	public InputStream findPngStream(String pdId) {
		return processEngine.getRepositoryService().getProcessDiagram(pdId);
	}
}
