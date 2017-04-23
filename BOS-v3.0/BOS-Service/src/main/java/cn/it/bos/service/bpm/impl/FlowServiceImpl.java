package cn.it.bos.service.bpm.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.it.bos.dao.bpm.IApplicationDao;
import cn.it.bos.dao.bpm.IApproveInfoDao;
import cn.it.bos.domain.bpm.Application;
import cn.it.bos.domain.bpm.ApproveInfo;
import cn.it.bos.domain.bpm.TaskView;
import cn.it.bos.domain.user.User;
import cn.it.bos.service.bpm.IFlowService;

/**
 * 流程控制Service
 * 
 * @author zhaoqx
 * 
 */
@Service
@Transactional
public class FlowServiceImpl implements IFlowService {
	@Autowired
	private IApplicationDao applicationDao;
	@Autowired
	private IApproveInfoDao approveInfoDao;
	@Autowired
	private ProcessEngine processEngine;

	/**
	 * 业务方法：提交申请
	 */
	public void submit(Application app) {
		// 2、保存一个申请实体
		applicationDao.save(app);// 持久对象
		// 3、启动一个流程实例
		String key = app.getTemplate().getPdKey();
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("application", app);
		variables.put("applicationId", app.getId());
		ProcessInstance pi = processEngine.getRuntimeService()
				.startProcessInstanceByKey(key, variables);
		// 4、办理第一个提交任务
		TaskQuery query = processEngine.getTaskService().createTaskQuery();
		query.taskAssignee(app.getApplicant().getUsername());
		query.processInstanceId(pi.getId());
		Task task = query.singleResult();
		String taskId = task.getId();
		processEngine.getTaskService().complete(taskId);
	}

	public InputStream getPngStream(String deploymentId, String imageName) {
		return processEngine.getRepositoryService().getResourceAsStream(
				deploymentId, imageName);
	}

	/**
	 * 根据申请id（流程变量）查询任务
	 */
	public Task findTaskByApplicationId(Integer applicationId) {
		TaskQuery query = processEngine.getTaskService().createTaskQuery();
		// 根据设置的流程变量进行过滤
		query.processVariableValueEquals("applicationId", applicationId);
		return query.singleResult();
	}

	/**
	 * 根据任务查询流程定义对象
	 */
	public ProcessDefinition findPDByTask(Task task) {
		String processDefinitionId = task.getProcessDefinitionId();
		ProcessDefinitionQuery query = processEngine.getRepositoryService()
				.createProcessDefinitionQuery();
		query.processDefinitionId(processDefinitionId);
		return query.singleResult();
	}

	/**
	 * 根据任务查询坐标
	 */
	public Map<String, Object> findCoordingByTask(Task task) {
		// 获得流程定义id
		String processDefinitionId = task.getProcessDefinitionId();
		// 获得流程实例id
		String processInstanceId = task.getProcessInstanceId();

		// 返回的流程定义对象中包含坐标信息
		ProcessDefinitionEntity pd = (ProcessDefinitionEntity) processEngine
				.getRepositoryService().getProcessDefinition(
						processDefinitionId);

		ProcessInstanceQuery query = processEngine.getRuntimeService()
				.createProcessInstanceQuery();
		// 根据流程实例id过滤
		query.processInstanceId(processInstanceId);
		ProcessInstance processInstance = query.singleResult();

		// 根据流程实例对象获得当前的获得节点
		String activityId = processInstance.getActivityId();// usertask2

		ActivityImpl activityImpl = pd.findActivity(activityId);
		int x = activityImpl.getX();
		int y = activityImpl.getY();
		int height = activityImpl.getHeight();
		int width = activityImpl.getWidth();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("x", x);
		map.put("y", y);
		map.put("height", height);
		map.put("width", width);

		return map;
	}

	/**
	 * 根据当前登录人查询对应的任务列表，包装成List<TaskView>返回
	 */
	public List<TaskView> findList(User loginUser) {
		TaskQuery query = processEngine.getTaskService().createTaskQuery();
		query.taskAssignee(loginUser.getUsername());// 添加过滤条件
		query.orderByTaskCreateTime().desc();// 添加排序条件
		List<Task> taskList = query.list();

		List<TaskView> list = new ArrayList<TaskView>();
		for (Task task : taskList) {
			String taskId = task.getId();
			Application application = (Application) processEngine
					.getTaskService().getVariable(taskId, "application");
			TaskView taskView = new TaskView(task, application);
			list.add(taskView);
		}
		return list;
	}

	/**
	 * 审批处理
	 */
	public void approve(ApproveInfo ai, String taskId) {
		Task task = processEngine.getTaskService().createTaskQuery()
				.taskId(taskId).singleResult();
		// 获得流程实例id
		String processInstanceId = task.getProcessInstanceId();
		// 1、保存审批实体
		approveInfoDao.save(ai);// 持久对象
		// 2、办理当前的任务
		processEngine.getTaskService().complete(taskId);

		// 查询当前的流程实例是否存在
		ProcessInstance processInstance = processEngine.getRuntimeService()
				.createProcessInstanceQuery()
				.processInstanceId(processInstanceId).singleResult();

		Application application = ai.getApplication();// 持久对象

		if (ai.getApproval()) {// 3、如果审批的结果为“通过”
			if (processInstance == null) {// 如果当前办理人为最后一个节点，将申请的状态修改为“已通过”
				application.setStatus(Application.STATUS_APPROVED);
			}
		} else {// 如果审批的结果为"不通过"
			// 将申请的状态修改为“不通过”
			application.setStatus(Application.STATUS_UNAPPROVED);
			// 如果当前办理人不是最后一个节点，手动结束流程
			if (processInstance != null) {
				processEngine.getRuntimeService().deleteProcessInstance(
						processInstanceId, Application.STATUS_UNAPPROVED);
			}
		}
	}
}
