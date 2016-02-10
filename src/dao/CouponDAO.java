package dao;

import java.util.Date;
import java.util.List;

import core.CouponSystemException;
import core.beans.Coupon;
import core.beans.CouponType;

public interface CouponDAO {
	
	public void createCoupon(long CompanyId, Coupon coupon) throws CouponSystemException;
	public void removeCoupon(Coupon coupon) throws CouponSystemException;
	public void updateCoupon(Coupon coupon) throws CouponSystemException;
	public boolean isTitleExists(String title) throws CouponSystemException;
	public Coupon getCoupon(long id) throws CouponSystemException;
	public Coupon getCouponByTitle(String title) throws CouponSystemException;
	public List<Coupon> getAllCoupons() throws CouponSystemException;
	public List<Coupon> getAllCouponsCompany(long CompanyId) throws CouponSystemException;
	public List<Coupon> getCouponsCompanyByType(long CompanyId, CouponType type) throws CouponSystemException;
	public List<Coupon> getCouponsCompanyByPrice(long companyId, double price) throws CouponSystemException;
	public List<Coupon> getCouponsCompanyByDate(long id, Date date) throws CouponSystemException;
	public List<Coupon> getAllpurchasedCoupons(long customerId) throws CouponSystemException;
	public List<Coupon> getAllpurchasedCouponByType(long customerId, CouponType type) throws CouponSystemException;
	public List<Coupon> getAllpurchasedCouponByPrice(long customerId, double price) throws CouponSystemException;
	public List<Coupon> getAllPurchacedCouponByDate(long customerId, Date date) throws CouponSystemException;

}
