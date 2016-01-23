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
	
	public CompanyFacade(){
		
		couponDAO = new CouponDBDAO();
		companyDAO = new CompanyDBDAO();
	}
	
	public void createCoupon(Coupon coupon) throws CouponSystemException{

		if(couponDAO.isTitleExists(coupon.getTitle())) {
			throw new CouponSystemException("coupon already exists");
		}
		couponDAO.createCoupon(company.getId(), coupon);
	}
	
	public void removeCoupon(Coupon coupon){

		couponDAO.removeCoupon(coupon);
	}
	
	public void updateCoupon(Coupon coupon){

			couponDAO.updateCoupon(coupon);
	}
	
	public Coupon getCoupon(long id){
		
		return couponDAO.getCoupon(id);
	}
	
	public List<Coupon> getAllCoupons(){

		return couponDAO.getAllCouponsCompany(company.getId());
	}
	
	public List<Coupon> getCouponByType(CouponType type){

		return couponDAO.getCouponsCompanyByType(company.getId(), type);
	}
	
	@Override
	public CouponClientFacade login(String name, String password) throws CouponSystemException {

		if (!companyDAO.login(name, password)) {
			throw new CouponSystemException("username or password is incorrect");
		}
		company = companyDAO.getCompanyByName(name);
		return this;
	}

}
