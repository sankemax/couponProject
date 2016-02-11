package dao;

import java.util.List;

import core.CouponSystemException;
import core.beans.Customer;

public interface CustomerDAO {
	
	/**
	 * @param customer bean which holds the information needed to create the customer.
	 * @throws CouponSystemException if customer already exists, or communication IO failed.
	 */
	public void createCustomer(Customer customer) throws CouponSystemException;
	
	/**
	 * @param customer bean which holds the information necessary to remove the customer.
	 * @throws CouponSystemException if customer does not exist or if communication IO failed.
	 */
	public void removeCustomer(Customer customer) throws CouponSystemException;
	
	/**
	 * In case of identical information, the update will proceed and no errors will be thrown.
	 * @param customer bean which holds the information of the update. 
	 * @throws CouponSystemException if customer does not exist, or communication IO failed.
	 */
	public void updateCustomer(Customer customer) throws CouponSystemException;
	
	/**
	 * @param id number of the customer that will be returned if exists in the system.
	 * @return <code><b>Customer</b></code> bean that holds the information about the customer with the requested id number.
	 * @throws CouponSystemException if the specified customer id number does not exist, or if communication IO failed.
	 */
	public Customer getCustomer(long id) throws CouponSystemException;
	
	/**
	 * @return A List that holds all the customers.
	 * @throws CouponSystemException if there are no customers in the system or communication IO failed.
	 */
	public List<Customer> getAllCustomer() throws CouponSystemException;
	
	/**
	 * @param compName the name of the customer.
	 * @param password the password of the customer.
	 * @return <code><b>true</b></code> if such login exists; <code><b>false</b></code> otherwise.
	 * @throws CouponSystemException if communication IO failed.
	 */
	public boolean login(String compName, String password) throws CouponSystemException;
	
	/**
	 * @param name of the customer that will be returned if exists in the system.
	 * @return <code><b>Customer</b></code> bean that holds the information about the customer with the requested name.
	 * @throws CouponSystemException if specified name doesn't exist, if the name and password don't match or if communication IO failed.
	 */
	public Customer getCustomerByName(String name) throws CouponSystemException;
	
	/**
	 * @param customer name of the customer.
	 * @return <code><b>true</b></code> if such name exists; <code><b>false</b></code> otherwise.
	 * @throws CouponSystemException if communication IO failed.
	 */
	public boolean isNameExists(Customer customer) throws CouponSystemException;
	
	/**
	 * @param customerId the Id of the customer.
	 * @param CouponId the Id of the coupon.
	 * @return <code><b>true</b></code> if the customer with specified Id had purchased the coupon with specified Id;
	 * <code><b>false</b></code> otherwise.
	 * @throws CouponSystemException if communication IO failed.
	 */
	public boolean isPurchased(long customerId, long CouponId) throws CouponSystemException;
	
	/**
	 * @param customerId the Id of the customer.
	 * @param CouponId the Id of the coupon.
	 * @throws CouponSystemException if coupon with specified Id doesn't exist, if coupon was already purchased by the customer
	 * with specified Id, if the coupon is out of stock, if coupons' expiration date has already passed
	 * or if communication IO failed.
	 */
	public void purchaseCoupon(long customerId, long CouponId) throws CouponSystemException;

}
