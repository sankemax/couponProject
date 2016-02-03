package DAO;

import java.util.List;


import beans.Customer;
import core.CouponSystemException;

public interface CustomerDAO {
	public void createCustomer(Customer customer) throws CouponSystemException;
	public void removeCustomer(Customer customer) throws CouponSystemException;
	public void updateCustomer(Customer customer) throws CouponSystemException;
	public Customer getCustomer(long id) throws CouponSystemException;
	public List<Customer> getAllCustomer() throws CouponSystemException;
	public boolean login(String compName, String password) throws CouponSystemException;
	public Customer getCustomerByName(String name) throws CouponSystemException;
	public boolean isNameExists(Customer customer) throws CouponSystemException;
	public boolean isPurchased(long customerId, long CouponId) throws CouponSystemException;
	public void purchaseCoupon(long customerId, long CouponId) throws CouponSystemException;

}
