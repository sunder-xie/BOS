package cn.it.bos.dao.bpm;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import cn.it.bos.domain.bpm.Application;

@Repository
public interface IApplicationDao extends JpaRepository<Application, Integer>,JpaSpecificationExecutor<Application>  {


//	public List<Application> findByCriteria(DetachedCriteria dc);

}
