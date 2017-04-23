package cn.it.bos.web.action.process;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.task.Task;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import cn.it.bos.domain.user.User;
import cn.it.bos.service.bpm.BpmTaskService;

@SuppressWarnings("all")
@Controller
@Scope("prototype")
@ParentPackage("basicstruts2")
@Namespace("/")
public class TaskAction extends ActionSupport {
	private static final long serialVersionUID = 1L;

	@Resource
	private BpmTaskService taskService;

	private final Logger logger = Logger.getLogger(TaskAction.class);
	@Autowired
	private ProcessEngine pe;


	/**
	 * 组任务查询
	 * @return
	 */
	@Action(value = "page_workflow_grouptask", results = {
			@Result(name = "grouptask", location = "/WEB-INF/pages/workflow/grouptask.jsp") })
	public String grouptask() {
		Subject subject = SecurityUtils.getSubject();
		List<Task> taskList = taskService.grouptask(subject);
		Map<String, Object> variables = null;
		if (taskList != null && taskList.size() > 0) {
			for (Task task : taskList) {
				variables = pe.getRuntimeService().getVariables(task.getExecutionId());
			}
		}
		ActionContext.getContext().put("variables", variables);
		ActionContext.getContext().put("tasks", taskList);
		return "grouptask";
	}

	/**
	 * 个人公共任务拾取
	 * @return
	 */
	@Action(value = "task_takeTask", results = {
			@Result(name = "task_takeTask", type = "redirectAction", location = "page_workflow_personaltask") })
	public String task_takeTask() {
		String taskId = ServletActionContext.getRequest().getParameter("taskId");
		Subject subject = SecurityUtils.getSubject();
		taskService.takeTask(subject, taskId);
		return "task_takeTask";
	}
	/**
	 * 个人任务办理
	 * @return
	 */
	@Action(value = "claimTask", results = {
			@Result(name = "claimTask", type = "redirectAction", location = "page_workflow_personaltask") })
	public String claimTask() {
		String taskId = ServletActionContext.getRequest().getParameter("taskId");
		Subject subject = SecurityUtils.getSubject();
		taskService.claimTask(subject, taskId);
		return "claimTask";
	}

	
	/**
	 * 查询个人任务
	 * @return
	 */
	@Action(value = "page_workflow_personaltask", results = {
			@Result(name = "task_takeTask", location = "/WEB-INF/pages/workflow/personaltask.jsp") })
	public String listTask() {
		Subject subject = SecurityUtils.getSubject();
		List<Task> taskList = taskService.listTask(subject);
		Map<String, Object> variables = null;
		if (taskList != null && taskList.size() > 0) {
			for (Task task : taskList) {
				variables = pe.getRuntimeService().getVariables(task.getExecutionId());
			}
		}
		ActionContext.getContext().put("variables", variables);
		ActionContext.getContext().put("tasks", taskList);
		return "task_takeTask";
	}

}
