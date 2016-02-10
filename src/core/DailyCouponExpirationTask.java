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
		
		if(Thread.currentThread().getState().toString().equals("NEW")) {
			Thread.currentThread().setDaemon(true);		
		}
		
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
				
					Thread.sleep(MILLI_SEC_IN_DAY);
			} catch (InterruptedException e) {
				quit = true;
			} catch (CouponSystemException e) {
				// TODO what should we do?
			}
		}
	}
	
	public void stopTask() {
		quit = true;
	}

}
