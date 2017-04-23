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
import cn.it.bos.domain.bc.Subarea;

@Repository
public interface SubareaDao extends JpaRepository<Subarea, String>, JpaSpecificationExecutor<Subarea> {

	@Query("from Subarea s where s.decidedZone =(:decidedZone) ")
	List<Subarea> queryList(@Param("decidedZone") DecidedZone queryDecidedZone);

	/**
	 * 通过地址关键字查询
	 * 
	 * @param addresskey
	 * @return
	 */
	@Query("from Subarea s where s.addresskey =(:addresskey) ")
	List<Subarea> findByAddresskey(@Param("addresskey") String addresskey);

	@Query("from Subarea s where s.decidedZone is null")
	List<Subarea> findStandardByDecidedZone();

	@Modifying
	@Query("update from Subarea set decidedZone=?1 where id = ?2 ")
	void updateSubareaDecidedZoneById(DecidedZone decidedZone, String id);

}
