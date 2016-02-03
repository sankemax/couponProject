package core;

public class CouponSystemException extends Exception {
	
	private static final long serialVersionUID = 1L;

	// available exception messages
	public static final String USERNAME_PASSWORD_ERROR = "invalid username or password";
	public static final String SYSTEM_ERROR = "the system has encountered an error";
	public static final String COUPON_NOT_EXIST_ERROR = "such coupon does not exist";
	public static final String COMPANY_EXISTS = "such company already exists";
	public static final String CUSTOMER_EXISTS = "such customer already exists";
	public static final String COUPON_EXISTS = "such coupon already exists";
	public static final String COUPON_EXPIRATION_DATE_PASSED = "coupon expiration date has passed";
	public static final String COUPON_OUT_OF_STOCK = "this coupon is out of stock";
	public static final String COUPON_ALREADY_PURCHASED = "you have already purchased this coupon";
	public static final String CUSTOMER_NOT_EXISTS  = "such customer does not exist";
	public static final String COMPANY_NOT_EXISTS  = "such company does not exist";
	// TODO what do we use these for?
	public static final String CUSTOMERS_NOT_EXISTS  = "No customers system";
	public static final String COMPANYS_NOT_EXISTS  = "No companys system";
	public static final String COUPONS_NOT_EXISTS  = "No coupons system";
	
	public CouponSystemException(String message){
		super(message); 
	}
}
