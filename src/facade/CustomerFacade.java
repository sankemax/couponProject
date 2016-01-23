package facade;


import java.util.Date;
import java.util.List;
import DAO.*;
import DBDAO.*;
import beans.*;
import core.CouponSystemException;


public class CustomerFacade implements CouponClientFacade {
	
	private Customer customer;
	
	private CustomerDAO customerDAO;
	private CouponDAO couponDAO;
	
	public CustomerFacade(){
		
		customerDAO = new CustomerDBDAO();
		couponDAO = new CouponDBDAO();
	}
	
	public void purchaseCoupon(Coupon coupon) throws CouponSystemException{
		
		coupon.setCouponId(couponDAO.getCouponByTitle(coupon.getTitle()).getId());
		
		if(customerDAO.isPurchased(customer.getId(), coupon.getId())){
			throw new CouponSystemException("you have already purchased this coupon");
		}
		if(couponDAO.getCoupon(coupon.getId()).getAmount() <= 0){
			throw new CouponSystemException("this coupon is out of stock");
		}
		if(couponDAO.getCoupon(coupon.getId()).getEndDate().after(new Date())){
			throw new CouponSystemException("coupon expiration date has passed");			
		}
		customerDAO.purchaseCoupon(customer.getId(), coupon.getId());
	}
	
	public List<Coupon> getAllpurchasedCoupons(){
		
		return couponDAO.getAllpurchasedCoupons(customer.getId());

	}
	
	public List<Coupon> getAllpurchasedCouponByType(CouponType type){

		return couponDAO.getAllpurchasedCouponByType(customer.getId(), type);
	}
	
	public List<Coupon> getAllpurchasedCouponByPrice(double Price){

		return couponDAO.getAllpurchasedCouponByPrice(customer.getId(), Price);
	}
	
	@Override
	public CouponClientFacade login(String name, String password) throws CouponSystemException {

		if (!customerDAO.login(name, password)){
			throw new CouponSystemException("username or password is incorrect");
		}
		customer = customerDAO.getCustomerByName(name);
		return this;
	}
}
