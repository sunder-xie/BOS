package cn.it.crm.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cn.it.crm.domain.Customer;

@Service("customerService")
public interface CustomerService {

	/**
	 * 查询未关联客户
	 * 
	 * @return
	 */
	public List<Customer> findNoAssociationCustomer();

	/**
	 * 查询已关联客户
	 * 
	 * @return
	 */
	public List<Customer> findAssociationCustomer(String DecidedZoneId);

	/**
	 * 关联客户和定区表
	 * 
	 * @return
	 */
	public void associateCustomerToDecidedZone(String[] associationCustomerIds, String DecidedZoneId);

	/**
	 * 通过id查询关联取派员
	 */
	public Customer findCustomerById(String customerId);

}
