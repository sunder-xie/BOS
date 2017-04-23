package cn.it.bos.service.bpm;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import cn.it.bos.domain.bpm.Application;

public interface IApplicationService {

//	public List<Application> findByCriteria(DetachedCriteria dc);

	public Application findById(Integer applicationId);

}
