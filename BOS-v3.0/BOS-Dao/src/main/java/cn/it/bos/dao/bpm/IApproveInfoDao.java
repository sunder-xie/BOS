package cn.it.bos.dao.bpm;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import cn.it.bos.domain.bpm.ApproveInfo;
@Repository
public interface IApproveInfoDao extends JpaRepository<ApproveInfo, Integer>,JpaSpecificationExecutor<ApproveInfo>  {

	@Query("FROM ApproveInfo ai WHERE ai.application.id = ? ORDER BY ai.approveDate ASC")
	public List<ApproveInfo> findListByApplicationId(Integer applicationId);

}
