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
	
	public DailyCouponExpirationTask() throws CouponSystemException {
		
		couponDAO = new CouponDBDAO();
		quit = false;
	}
	
	@Override
	public void run() {
		
		// TODO set the thread to daemon and check that it works
//		Thread.currentThread().setDaemon(true);		
		while (! quit) {
			try {
			
				Calendar calendar = Calendar.getInstance();
				calendar.set(Calendar.HOUR_OF_DAY, 0);
				calendar.set(Calendar.MINUTE, 0);
				calendar.set(Calendar.SECOND, 0);
				calendar.set(Calendar.MILLISECOND, 0);
				Date today = calendar.getTime();
				
				Collection<Coupon> coupons = couponDAO.getAllCoupons();
				for (Coupon coupon : coupons) {
					
					if (coupon.getEndDate().before(today)) {
						
						couponDAO.removeCoupon(coupon);
					}
				}
				
	//				Thread.sleep(MILLI_SEC_IN_DAY);
					Thread.sleep(TESTING);
			} catch (InterruptedException e) {
				break;
			} catch (CouponSystemException e) {
				// TODO what should we do?
			}
		}
	}
	
	public void stopTask() {
		quit = true;
	}

}
