package cn.it.bos.service.bpm;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import org.activiti.engine.repository.ProcessDefinition;

public interface IProcessDefinitionService {

	public List<ProcessDefinition> findLastList();

	public void deleteByKey(String key);

	public void deploy(File resource);

	public InputStream findPngStream(String pdId);

}
