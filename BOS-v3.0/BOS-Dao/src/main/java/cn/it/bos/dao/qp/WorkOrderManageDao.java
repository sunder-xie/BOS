package cn.it.bos.dao.qp;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import cn.it.bos.domain.qp.WorkOrderManage;

@Repository
public interface WorkOrderManageDao extends JpaRepository<WorkOrderManage, String> ,JpaSpecificationExecutor<WorkOrderManage>{

	@Query("from WorkOrderManage where managerCheck = '0'")
	List<WorkOrderManage> listUnChecked();
	
	@Modifying
	@Query("update WorkOrderManage set managerCheck='1' where id = ?1")
	void updateManagerCheck(String id);

}
