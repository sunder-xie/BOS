package cn.it.bos.dao.qp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import cn.it.bos.domain.qp.WorkBill;
@Repository
public interface WorkBillDao  extends JpaRepository<WorkBill,String>,JpaSpecificationExecutor<WorkBill>{

}
