package facade;


import java.util.List;
import DAO.*;
import DBDAO.*;
import beans.*;


public class CompanyFacade implements CouponClientFacade {
	
	
	private Company company;
	private CouponDAO couponDAO;
	private CompanyDAO companyDAO;
	
	public CompanyFacade(){
		
		couponDAO = new CouponDBDAO();
		companyDAO = new CompanyDBDAO();
	}
	
	public void createCoupon(Coupon coupon){

		if(couponDAO.isTitleExists(coupon.getTitle())) {
			// TODO Auto-generated catch block
			System.out.println("title of coupon already exists");
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
	
	public List<Coupon> getAllCoupon(){

		return couponDAO.getAllCouponsCompany(company.getId());
	}
	
	public List<Coupon> getCouponByType(CouponType type){

		return couponDAO.getCouponsCompanyByType(company.getId(), type);
	}
	
	@Override
	public CouponClientFacade login(String name, String password, String clientType) {

		if(!companyDAO.login(name, password)){
			// TODO Auto-generated method stub
		}
		company = companyDAO.getCompanyByName(name);
		return this;
	}

}
