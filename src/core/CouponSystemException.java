package core;

public class CouponSystemException extends Exception {
	
	private static final long serialVersionUID = 1L;

	// available exception messages
	// TODO should we use enum instead?
	public static final String USERNAME_PASSWORD_ERROR = "invalid username or password";
	public static final String SYSTEM_ERROR = "the system has encountered an error";
	public static final String COUPON_NOT_EXIST_ERROR = "such coupon does not exist";
	public static final String COMPANY_EXISTS = "such company already exists";
	public static final String CUSTOMER_EXISTS = "such customer already exists";
	public static final String COUPON_EXISTS = "such coupon already exists";
	public static final String COUPON_EXPIRATION_DATE_PASSED = "coupon expiration date has passed";
	public static final String COUPON_OUT_OF_STOCK = "this coupon is out of stock";
	public static final String COUPON_ALREADY_PURCHASED = "you have already purchased this coupon";
	
	public CouponSystemException(String message){
		super(message); 
	}
}
