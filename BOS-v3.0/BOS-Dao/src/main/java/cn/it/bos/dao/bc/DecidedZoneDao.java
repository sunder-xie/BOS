package cn.it.bos.dao.bc;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import cn.it.bos.domain.bc.DecidedZone;

@Repository
public interface DecidedZoneDao extends JpaRepository<DecidedZone,String>,JpaSpecificationExecutor<DecidedZone>{

}
