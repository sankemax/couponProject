package facade;


import java.util.Date;
import java.util.List;
import DAO.*;
import DBDAO.*;
import beans.*;


public class CustomerFacade implements CouponClientFacade {
	
	private Customer customer;
	
	private CustomerDAO customerDAO;
	private CouponDAO couponDAO;
	
	public CustomerFacade(){
		
		customerDAO = new CustomerDBDAO();
		couponDAO = new CouponDBDAO();
	}
	
	public void purchaseCoupon(Coupon coupon){
		
		if(customerDAO.IsPurchased(customer.getId(), coupon.getId())){
			// TODO Auto-generated catch block
		}
		if(couponDAO.getCoupon(coupon.getId()).getAmount() <= 0){
			// TODO Auto-generated catch block
		}
		if(couponDAO.getCoupon(coupon.getId()).getEndDate().after(new Date())){
			// TODO Auto-generated catch block
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
	public CouponClientFacade login(String name, String password) {

		if(!customerDAO.login(name, password)){
			// TODO Auto-generated method stub
		}
		customer = customerDAO.getCustomerByName(name);
		return this;

	}

	
	
	
	
	
}
