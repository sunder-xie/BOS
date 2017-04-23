package cn.it.bos.service.bpm.impl;

import java.util.List;
import java.util.Map;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import cn.it.bos.domain.user.User;
import cn.it.bos.service.base.impl.BaseServiceImpl;
import cn.it.bos.service.bpm.BpmTaskService;

@Service("taskService")
public class BpmTaskServiceImpl extends BaseServiceImpl implements BpmTaskService {

	/**
	 * 组任务查询
	 */
	public List<Task> grouptask(Subject subject) {
		/*
		 * 如果是admin,可以查看任意组任务
		 */
		List<Task> list=null;
		User loginUser = (User) subject.getPrincipal();
		if (loginUser != null && "admin".equals(loginUser.getUsername())) {
			TaskService taskService = pe.getTaskService();
			TaskQuery taskQuery = taskService.createTaskQuery();
			list = taskQuery.orderByTaskCreateTime().asc().list();
			return list;
		}
		
		/*
		 * 如果不是admin, 根据权限只能查询权限内的组任务,即用户所在组的组任务
		 */

		return list;
	}

	/**
	 * 拾取公共任务
	 */
	@Override
	public void takeTask(Subject subject, String taskId) {
		User u = (User) subject.getPrincipal();
		// 每个登录用户,自己去拾取公共任务
		pe.getTaskService().setAssignee(taskId, u.getId());
		
	}
	
	/**
	 * 查询个人任务
	 */
	@Override
	public List<Task> listTask(Subject subject) {
		User u = (User) subject.getPrincipal();
		TaskService taskService = pe.getTaskService();
		TaskQuery taskQuery = taskService.createTaskQuery();
		taskQuery.taskAssignee(u.getId());
		List<Task> list = taskQuery.list();
		return list;
	}

	/**
	 * 办理任务
	 */
	@Override
	public void claimTask(Subject subject, String taskId) {
		User u = (User) subject.getPrincipal();
		TaskService taskService = pe.getTaskService();
		taskService.claim(taskId, u.getId());
	}

}
