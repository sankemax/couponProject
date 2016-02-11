package dao;

import java.util.Date;
import java.util.List;

import core.CouponSystemException;
import core.beans.Coupon;
import core.beans.CouponType;

public interface CouponDAO {
	
	/**
	 * @param CompanyId the id of the company on which the coupon will be registered.
	 * @param coupon bean which holds the information needed to create the coupon.
	 * @throws CouponSystemException if no such company exists, coupon already exists, or communication IO failed.
	 */
	public void createCoupon(long CompanyId, Coupon coupon) throws CouponSystemException;
	
	/**
	 * @param coupon bean which holds the information needed to remove the coupon.
	 * @throws CouponSystemException if coupon does not exist, or communication IO failed.
	 */
	public void removeCoupon(Coupon coupon) throws CouponSystemException;
	
	/**
	 * In case of identical information, the update will proceed and no errors will be thrown.
	 * @param coupon bean which holds the information of the update. 
	 * @throws CouponSystemException if coupon does not exist, or communication IO failed.
	 */
	public void updateCoupon(Coupon coupon) throws CouponSystemException;
	
	/**
	 * @param title of the coupon.
	 * @return <code><b>true</b></code> if such title exists; <code><b>false</b></code> otherwise.
	 * @throws CouponSystemException if communication IO failed.
	 */
	public boolean isTitleExists(String title) throws CouponSystemException;
	
	/**
	 * @param id number of the coupon that will be returned if exists in the system.
	 * @return <code><b>Coupon</b></code> bean that holds the information about the coupon with the requested id number.
	 * @throws CouponSystemException if no such coupon with specified Id exists or if communication IO failed.
	 */
	public Coupon getCoupon(long id) throws CouponSystemException;
	
	/**
	 * @param title of the coupon that will be returned if exists in the system.
	 * @return <code><b>Coupon</b></code> bean that holds the information about the coupon with the requested title.
	 * @throws CouponSystemException if coupon title doesn't exist, or if communication IO failed.
	 */
	public Coupon getCouponByTitle(String title) throws CouponSystemException;
	
	/**
	 * @return A List that holds all the coupons.
	 * @throws CouponSystemException if there are no coupons in the system or communication IO failed.
	 */
	public List<Coupon> getAllCoupons() throws CouponSystemException;
	
	/**
	 * @param CompanyId the Id number of the company which coupons will be returned.
	 * @return A list that holds all the coupons owned by the company with the specified Id.
	 * @throws CouponSystemException if there's no company with the specified Id,
	 * if there are no coupons owned by the company with the specified Id,
	 * or communication IO failed.
	 */
	public List<Coupon> getAllCouponsCompany(long CompanyId) throws CouponSystemException;
	
	/**
	 * @param CompanyId the Id number of the company which coupons will be returned.
	 * @param type the coupon type (which is an Enum - <code>CouponType</code>) of the coupon.
	 * @return A list that holds all the coupons owned by the company with the specified Id and are of the specified
	 * <code>CouponType</code>.
	 * @throws CouponSystemException if there's no company with the specified Id,
	 * if there are no coupons of the type (<code>CouponType</code>) requested,
	 * owned by the company with the specified Id, or communication IO failed.
	 */
	public List<Coupon> getCouponsCompanyByType(long CompanyId, CouponType type) throws CouponSystemException;
	
	/**
	 * @param companyId the Id number of the company which coupons will be returned.
	 * @param price the maximum price of the coupons.
	 * @return A list that holds all the coupons owned by the company with the specified Id which price is lower
	 * or equal to the specified price.
	 * @throws CouponSystemException if there's no company with the specified Id,
	 * if there are no coupons lower or equal to the specified price, or communication IO failed.
	 */
	public List<Coupon> getCouponsCompanyByPrice(long companyId, double price) throws CouponSystemException;
	
	/**
	 * @param id number of the company which coupons will be returned.
	 * @param date the latest <b>end date</b> of the coupons.
	 * @return A list that holds all the coupons owned by the company with the specified Id which end date is earlier
	 * or the same as the specified date.
	 * @throws CouponSystemException if there's no company with the specified Id,
	 * if there are no coupons which end date is earlier or the same as the specified date,
	 * or communication IO failed.
	 */
	public List<Coupon> getCouponsCompanyByDate(long id, Date date) throws CouponSystemException;
	
	/**
	 * @param customerId the Id of the customer which coupons will be returned.
	 * @return A list of all the coupons that the customer purchased.
	 * @throws CouponSystemException if there's no customer with the specified Id,
	 * if no such coupons exist, or communication IO failed.
	 */
	public List<Coupon> getAllpurchasedCoupons(long customerId) throws CouponSystemException;
	
	/**
	 * @param customerId the Id of the customer which coupons will be returned.
	 * @param type the coupon type (which is an Enum - <code>CouponType</code>) of the coupon.
	 * @return A list that holds all the coupons purchased by the customer with the specified Id and are of the specified
	 * <code>CouponType</code>.
	 * @throws CouponSystemException if there's no customer with the specified Id,
	 * if there are no coupons of the type (<code>CouponType</code>) requested,
	 * purchased by the customer with the specified Id, or communication IO failed.
	 */
	public List<Coupon> getAllpurchasedCouponByType(long customerId, CouponType type) throws CouponSystemException;
	
	/**
	 * @param customerId the Id of the customer which coupons will be returned.
	 * @param price the maximum price of the coupons.
	 * @return A list that holds all the coupons purchased by the customer with the specified Id which price is lower
	 * or equal to the specified price.
	 * @throws CouponSystemException if there's no customer with the specified Id,
	 * if there are no coupons lower or equal to the specified price, or communication IO failed.
	 */
	public List<Coupon> getAllpurchasedCouponByPrice(long customerId, double price) throws CouponSystemException;
	
	/**
	 * @param customerId the Id of the customer which coupons will be returned.
	 * @param date the latest <b>end date</b> of the coupons.
	 * @return A list that holds all the coupons purchased by the customer with the specified Id which end date is earlier
	 * or the same as the specified date.
	 * @throws CouponSystemException if there's no customer with the specified Id,
	 * if there are no coupons which end date is earlier or the same as the specified date,
	 * or communication IO failed.
	 */
	public List<Coupon> getAllPurchacedCouponByDate(long customerId, Date date) throws CouponSystemException;

}
