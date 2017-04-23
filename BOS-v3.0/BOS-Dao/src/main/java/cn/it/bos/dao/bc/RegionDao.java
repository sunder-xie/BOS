package cn.it.bos.dao.bc;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import cn.it.bos.domain.bc.Region;

@Repository
public interface RegionDao extends JpaRepository<Region, String>, JpaSpecificationExecutor<Region> {

	public Region findRegionByPostcode(String postId);

	@Query("from Region where province like %?1% or city like %?1% or district like %?1%")
	public List<Region> findRegionBySearchCode(String p);

}
