package DAO;

import java.util.List;
import beans.Coupon;
import beans.CouponType;
import core.CouponSystemException;

public interface CouponDAO {
	
	public void createCoupon(long CompanyId, Coupon coupon);
	public void removeCoupon(Coupon coupon);
	public void updateCoupon(Coupon coupon);
	public boolean isTitleExists(String title);
	public Coupon getCoupon(long id);
	public Coupon getCouponByTitle(String title) throws CouponSystemException;
	public List<Coupon> getAllCoupons();
	public List<Coupon> getAllCouponsCompany(long CompanyId);
	public List<Coupon> getCouponsCompanyByType(long CompanyId, CouponType type);
	public List<Coupon> getAllpurchasedCoupons(long customerId);
	public List<Coupon> getAllpurchasedCouponByType(long customerId, CouponType type);
	public List<Coupon> getAllpurchasedCouponByPrice(long customerId, double price);

}
