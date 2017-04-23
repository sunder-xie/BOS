package cn.it.bos.dao.bc;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cn.it.bos.domain.bc.Staff;


@Repository
public interface StaffDao extends JpaRepository<Staff,String> ,JpaSpecificationExecutor<Staff> {

	@Query("from Staff where deltag=(:deltag)")
	List<Staff> findStaff(@Param("deltag")String deltag);

	@Modifying
	@Query("update Staff set deltag =?1 where id=?2")
	void updata(String deltag, String id);

	@Query("from Staff where telephone =?1")
	List<Staff> ajaxPhone(String telephone);


}
