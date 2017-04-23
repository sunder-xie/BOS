package cn.it.bos.service.bpm;

import java.util.List;

import cn.it.bos.domain.bpm.ApproveInfo;

public interface IApproveInfoService {

	public List<ApproveInfo> findListByApplicationId(Integer applicationId);

}
