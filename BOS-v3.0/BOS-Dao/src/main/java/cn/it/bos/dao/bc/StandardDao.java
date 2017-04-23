package cn.it.bos.dao.bc;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cn.it.bos.domain.bc.DecidedZone;
import cn.it.bos.domain.bc.Standard;

@Repository
public interface StandardDao extends JpaRepository<Standard,String>,JpaSpecificationExecutor<Standard> {

	@Query("from Standard where deltag=(:deltag)")
	List<Standard> findStandard(@Param("deltag")String deltag);
	
	@Modifying
	@Query("update Standard set deltag=(:deltag) where id=(:id)")
	void updata(@Param("id")String id,@Param("deltag") String deltag);

}
