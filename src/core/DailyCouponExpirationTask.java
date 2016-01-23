package core;

import java.util.Collection;
import java.util.Date;

import DAO.CouponDAO;
import DBDAO.CouponDBDAO;
import beans.Coupon;

public class DailyCouponExpirationTask implements Runnable {
	
	private CouponDAO couponDAO;
	private boolean quit;
	private static final long MILLI_SEC_IN_DAY = 86_400_000;
	
	public DailyCouponExpirationTask() {
		couponDAO = new CouponDBDAO();
		quit = false;
	}
	
	@Override
	public void run() {
		
//		Thread.currentThread().setDaemon(true);
		while (!quit) {
			
			Collection<Coupon> coupons = couponDAO.getAllCoupons();
			for (Coupon coupon : coupons) {
				
				if (coupon.getEndDate().after(new Date())) {
					couponDAO.removeCoupon(coupon);
				}
			}
			try {
				Thread.sleep(MILLI_SEC_IN_DAY);
			} catch (InterruptedException e) {
				break;
			}
		}
	}
	
	public void stopTask() {
		quit = true;
	}

}
