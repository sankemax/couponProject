package DAO;

import java.util.List;


import beans.Customer;

public interface CustomerDAO {
	public void createCustomer(Customer customer);
	public void removeCustomer(Customer customer);
	public void updateCustomer(Customer customer);
	public Customer getCustomer(long id);
	public List<Customer> getAllCustomer();
	public boolean login(String compName, String password);
	public Customer getCustomerByName(String name);
	public boolean isNameExists(Customer customer);
	public boolean IsPurchased(long customerId, long CouponId);
	public void purchaseCoupon(long customerId, long CouponId);

}
