package cn.it.bos.service.bpm;

import java.util.List;

import org.activiti.engine.task.Task;
import org.apache.shiro.subject.Subject;

import cn.it.bos.service.base.BaseService;

public interface BpmTaskService extends BaseService{

	/**
	 * 根据权限角色查询对应权限内组任务 
	 */
	List<Task> grouptask(Subject subject);

	void takeTask(Subject subject, String taskId);

	List<Task> listTask(Subject subject);

	void claimTask(Subject subject, String taskId);

}
