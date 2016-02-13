package core;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import core.beans.Coupon;
import dao.CouponDAO;
import dao.dbdao.CouponDBDAO;

public class DailyCouponExpirationTask implements Runnable {
	
	private CouponDAO couponDAO;
	private boolean quit;
	private static final long MILLI_SEC_IN_DAY = 86_400_000;
		
	public DailyCouponExpirationTask() throws CouponSystemException {
		
		couponDAO = new CouponDBDAO();
		quit = false;
	}
	
	@Override
	public void run() {	
		
		while (! quit) {
			try {
			
				Calendar calendar = Calendar.getInstance();
				Date today = calendar.getTime();
				
				Collection<Coupon> coupons = couponDAO.getAllCoupons();
				for (Coupon coupon : coupons) {
					
					if (coupon.getEndDate().before(today)) {
						
						// DISABLE for for testing purposes
						couponDAO.removeCoupon(coupon);
					}
				}
				
			} catch (CouponSystemException e) {
				
				// it is natural for the DB to have no coupons at certain points,
				// so we exclude this exception from the problematic ones
				if(! e.getMessage().equals(CouponSystemException.COUPONS_NOT_EXIST)) {					
					System.err.println("system error in the DailyExpirationTask: " + e.getMessage());
				}
			} finally {
				try {
					
					// even if there's no coupons in the DB, the thread should sleep for the determined amount of time
					Thread.sleep(MILLI_SEC_IN_DAY);
				} catch (InterruptedException e) {
					
					/* 
					 * if the thread was interrupted by shutdown() method from CouponSystem, the value of quit was already
					 * changed to "true", so the thread will terminate. otherwise, there's no apparent logical reason for
					 * the interrupt and it may well be that the system is still functional. in that case, the thread
					 *  should continue running as normal.
					 */
					if (!quit) {
						System.err.println("sleep interrupted, but not through shutdown() method from CouponSystem."
								+ " proceeding as normal");
					}
						
				}
			}
		}
	}
	
	public void stopTask() {
		quit = true;
	}

}
