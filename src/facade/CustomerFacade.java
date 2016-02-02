package facade;


import java.util.Calendar;
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
			throw new CouponSystemException(CouponSystemException.COUPON_ALREADY_PURCHASED);
		}
		
		if(couponDAO.getCoupon(coupon.getId()).getAmount() <= 0){
			throw new CouponSystemException(CouponSystemException.COUPON_OUT_OF_STOCK);
		}
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		Date today = calendar.getTime();
		if(couponDAO.getCoupon(coupon.getId()).getEndDate().before(today)){
			throw new CouponSystemException(CouponSystemException.COUPON_EXPIRATION_DATE_PASSED);			
		}
		customerDAO.purchaseCoupon(customer.getId(), coupon.getId());
	}
	
	public List<Coupon> getAllpurchasedCoupons() throws CouponSystemException{
		return couponDAO.getAllpurchasedCoupons(customer.getId());

	}
	
	public List<Coupon> getAllpurchasedCouponByType(CouponType type) throws CouponSystemException{
		return couponDAO.getAllpurchasedCouponByType(customer.getId(), type);
	}
	
	public List<Coupon> getAllpurchasedCouponByPrice(double Price){
		return couponDAO.getAllpurchasedCouponByPrice(customer.getId(), Price);
	}
	
	@Override
	public CouponClientFacade login(String name, String password) throws CouponSystemException {
		name = name.toLowerCase();
		if (!customerDAO.login(name, password)){
			throw new CouponSystemException(CouponSystemException.USERNAME_PASSWORD_ERROR);
		}
		customer = customerDAO.getCustomerByName(name);
		return this;
	}

	public Customer getCustomerByName(String name) throws CouponSystemException{
		return customerDAO.getCustomerByName(name.toLowerCase());
	}
}
