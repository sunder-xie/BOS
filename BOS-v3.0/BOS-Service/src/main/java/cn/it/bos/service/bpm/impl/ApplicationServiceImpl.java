package cn.it.bos.service.bpm.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.it.bos.dao.bpm.IApplicationDao;
import cn.it.bos.domain.bpm.Application;
import cn.it.bos.service.bpm.IApplicationService;

@Service
@Transactional
public class ApplicationServiceImpl implements IApplicationService {
	
	@Autowired
	private IApplicationDao applicationDao;

//	public List<Application> findByCriteria(DetachedCriteria dc) {
//		return applicationDao.findByCriteria(dc);
//	}

	public Application findById(Integer applicationId) {
		return applicationDao.findOne(applicationId);
	}
}
