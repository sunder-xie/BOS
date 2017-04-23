package cn.it.bos.dao.zm;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import cn.it.bos.domain.zm.ZmZhongzhuaninfo;

@Repository
public interface ZmZhongzhuaninfoDao extends JpaRepository<ZmZhongzhuaninfo, Long> ,JpaSpecificationExecutor<ZmZhongzhuaninfo>{


}
