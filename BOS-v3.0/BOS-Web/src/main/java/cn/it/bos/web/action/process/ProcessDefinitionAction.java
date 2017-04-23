package cn.it.bos.web.action.process;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import javax.annotation.Resource;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import cn.it.bos.service.bpm.IProcessDefinitionService;

@SuppressWarnings("all")
@Controller
@Scope("prototype")
@ParentPackage("basicstruts2")
@Namespace("/")
public class ProcessDefinitionAction extends ActionSupport {
	private static final long serialVersionUID = 1L;

	@Resource
	private IProcessDefinitionService processDefinitionService;

	private final Logger logger = Logger.getLogger(ProcessDefinitionAction.class);
	@Autowired
	private ProcessEngine pe;
	private File deploy;
	private String id;// 流程定义id
	private String key;

	private String deployFileName;
	private String deployContentType;

	/**
	 * 显示png图片(文件下载)
	 */
	@Action(value = "showPng", results = {
			@Result(name = "showPng",type="stream",params={"contentType","image/png","inputName","pngStream"}) })
	public String showPng() {
		InputStream in = processDefinitionService.findPngStream(id);
		ActionContext.getContext().put("pngStream", in);

		// setContentType()
		// ServletActionContext.getResponse().getWriter().print(b)
		return "showPng";
	}

	/**
	 * 根据id删除流程定义
	 */
	@Action(value = "deleteById", results = {
			@Result(name = "deleteByKey",type="redirectAction",location = "page_workflow_processdefinition_list")})
	public String deleteById() {
		processDefinitionService.deleteByKey(id);
		return "deleteById";
	}
	/**
	 * 根据key删除流程定义
	 */
	@Action(value = "deleteByKey", results = {
			@Result(name = "deleteByKey",type="redirectAction",location = "page_workflow_processdefinition_list")})
	public String deleteByKey() {
		processDefinitionService.deleteByKey(key);
		return "deleteByKey";
	}

	@Action(value = "processdefinition_deploy", results = {
			@Result(name = "deploy", type="redirectAction",location = "page_workflow_processdefinition_list") })
	public String deploy() {
		try {
			processDefinitionService.deploy(deploy);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
		return "deploy";
	}

	@Action(value = "page_workflow_processdefinition_list", results = {
			@Result(name = "list", location = "/WEB-INF/pages/workflow/processdefinition_list.jsp") })
	public String list() {
		List<ProcessDefinition> list = processDefinitionService.findLastList();
		ActionContext.getContext().put("pdList", list);
		return "list";
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setDeployFileName(String deployFileName) {
		this.deployFileName = deployFileName;
	}

	public void setDeployContentType(String deployContentType) {
		this.deployContentType = deployContentType;
	}

	public void setDeploy(File deploy) {
		this.deploy = deploy;
	}
}
