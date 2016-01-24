package core;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import DAO.CouponDAO;
import DBDAO.CouponDBDAO;
import beans.Coupon;

public class DailyCouponExpirationTask implements Runnable {
	
	private CouponDAO couponDAO;
	private boolean quit;
	private static final long MILLI_SEC_IN_DAY = 86_400_000;
	
	// TODO only temporal for testing
	private static final long TESTING = 10_000; 
	
	public DailyCouponExpirationTask() {
		
		couponDAO = new CouponDBDAO();
		quit = false;
	}
	
	@Override
	public void run() {
		
//		Thread.currentThread().setDaemon(true);		
		while (! quit) {
			
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			Date today = calendar.getTime();
			
			Collection<Coupon> coupons = couponDAO.getAllCoupons();
			for (Coupon coupon : coupons) {
				
				if (coupon.getEndDate().before(today)) {
					
					try {
						couponDAO.removeCoupon(coupon);
					} catch (CouponSystemException e) {
						// TODO this is not an error that the user gets... internal error. what to do?
						e.printStackTrace();
					}
				}
			}
			
			try {
//				Thread.sleep(MILLI_SEC_IN_DAY);
				Thread.sleep(TESTING);
			} catch (InterruptedException e) {
				break;
			}
		}
	}
	
	public void stopTask() {
		quit = true;
	}

}
