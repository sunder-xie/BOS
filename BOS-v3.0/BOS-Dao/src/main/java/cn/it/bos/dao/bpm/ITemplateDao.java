package cn.it.bos.dao.bpm;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import cn.it.bos.domain.bpm.Template;
@Repository
public interface ITemplateDao extends JpaRepository<Template, Integer>,JpaSpecificationExecutor<Template> {


}
