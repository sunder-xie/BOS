package cn.it.bos.service.bpm;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;

import cn.it.bos.domain.bpm.Application;
import cn.it.bos.domain.bpm.ApproveInfo;
import cn.it.bos.domain.bpm.TaskView;
import cn.it.bos.domain.user.User;

public interface IFlowService {

	public void submit(Application app);

	public InputStream getPngStream(String deploymentId, String imageName);

	public Task findTaskByApplicationId(Integer applicationId);

	public ProcessDefinition findPDByTask(Task task);

	public Map<String, Object> findCoordingByTask(Task task);

	public List<TaskView> findList(User loginUser);

	public void approve(ApproveInfo ai, String taskId);

}
