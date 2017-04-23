package cn.it.bos.service.bpm.impl;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.it.bos.dao.bpm.ITemplateDao;
import cn.it.bos.domain.bpm.Template;
import cn.it.bos.service.bpm.ITemplateService;

@Service
@Transactional
public class TemplateServiceImpl implements ITemplateService{
	@Autowired
	private ITemplateDao templateDao;
	public List<Template> findList() {
		return templateDao.findAll();
	}
	public void save(Template model) {
		templateDao.save(model);
	}
	
	/**
	 * 根据id删除对象，同时删除doc文件
	 */
	public void deleteById(Integer id) {
		Template t = templateDao.findOne(id);
		String docFilePath = t.getDocFilePath();
		File file = new File(docFilePath);
		//如果文件存在，就删除
		if(file.exists()){
			file.delete();
		}
		templateDao.delete(t);
	}
	public Template findById(Integer id) {
		return templateDao.findOne(id);
	}
	public void update(Template template) {
		templateDao.save(template);
	}

}
