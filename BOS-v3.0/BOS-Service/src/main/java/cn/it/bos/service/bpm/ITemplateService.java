package cn.it.bos.service.bpm;

import java.util.List;

import cn.it.bos.domain.bpm.Template;

public interface ITemplateService {

	public List<Template> findList();

	public void save(Template model);

	public void deleteById(Integer id);

	public Template findById(Integer id);

	public void update(Template template);

}
