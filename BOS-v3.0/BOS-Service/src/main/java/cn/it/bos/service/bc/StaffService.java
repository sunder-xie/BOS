package cn.it.bos.service.bc;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import cn.it.bos.domain.bc.Staff;
import cn.it.bos.domain.bc.Standard;
import cn.it.bos.service.base.BaseService;

public interface StaffService extends BaseService{

	public void save(Staff staff);

	public Page<Staff> pageQuery(PageRequest pageRequestBean,Specification<Staff> specification );

	public void deleteById(String[] ids);


	public Standard findStandardById(Standard standard);

	public List<Staff> findStandard(String deltag);

	public List<Staff> ajaxPhone(String telphone);

	public Staff ajaxId(String id);



}
