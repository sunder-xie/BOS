package cn.it.bos.service.bpm.impl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.it.bos.dao.bpm.IApproveInfoDao;
import cn.it.bos.domain.bpm.ApproveInfo;
import cn.it.bos.service.bpm.IApproveInfoService;

@Service
@Transactional
public class ApproveInfoServiceImpl implements IApproveInfoService {

	@Autowired
	private IApproveInfoDao approveInfoDao;

	public List<ApproveInfo> findListByApplicationId(Integer applicationId) {
		return approveInfoDao.findListByApplicationId(applicationId);
	}
}
