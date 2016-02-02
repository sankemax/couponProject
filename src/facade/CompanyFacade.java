package facade;


import java.util.List;
import DAO.*;
import DBDAO.*;
import beans.*;
import core.CouponSystemException;


public class CompanyFacade implements CouponClientFacade {
	
	
	private Company company;
	private CouponDAO couponDAO;
	private CompanyDAO companyDAO;
	
	public CompanyFacade() throws CouponSystemException{
		
		couponDAO = new CouponDBDAO();
		companyDAO = new CompanyDBDAO();
	}
	
	public void createCoupon(Coupon coupon) throws CouponSystemException {
		coupon.setTitle(coupon.getTitle().toLowerCase());
		
		if(couponDAO.isTitleExists(coupon.getTitle())) {
			throw new CouponSystemException(CouponSystemException.COUPON_EXISTS);
		}
		couponDAO.createCoupon(company.getId(), coupon);
	}
	
	public void removeCoupon(Coupon coupon) throws CouponSystemException {
		couponDAO.removeCoupon(coupon);
	}
	
	public void updateCoupon(Coupon coupon) throws CouponSystemException {
		couponDAO.updateCoupon(coupon);
	}
	
	public Coupon getCoupon(long id) throws CouponSystemException{
		return couponDAO.getCoupon(id);
	}
	
	public List<Coupon> getAllCoupons() throws CouponSystemException{
		return couponDAO.getAllCouponsCompany(company.getId());
	}
	
	public List<Coupon> getCouponByType(CouponType type) throws CouponSystemException{
		return couponDAO.getCouponsCompanyByType(company.getId(), type);
	}
	
	@Override
	public CouponClientFacade login(String name, String password) throws CouponSystemException {
		name = name.toLowerCase();
		
		if (!companyDAO.login(name, password)) {
			throw new CouponSystemException(CouponSystemException.USERNAME_PASSWORD_ERROR);
		}
		company = companyDAO.getCompanyByName(name);
		return this;
	}
	
	public Company getCompanyByName(String name) throws CouponSystemException{
		return companyDAO.getCompanyByName(name.toLowerCase());
	}
}
