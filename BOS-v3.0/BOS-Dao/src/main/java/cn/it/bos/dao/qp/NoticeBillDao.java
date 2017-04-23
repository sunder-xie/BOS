package cn.it.bos.dao.qp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import cn.it.bos.domain.qp.NoticeBill;
@Repository
public interface NoticeBillDao extends JpaRepository<NoticeBill,String>,JpaSpecificationExecutor<NoticeBill>  {

}
