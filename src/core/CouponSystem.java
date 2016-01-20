package core;

import facade.AdminFacade;
import facade.CouponClientFacade;

public class CouponSystem {
	
	private CouponSystem instance = new CouponSystem();
	private DailyCouponExpirationTask dailyCouponExpirationTask; 
	
	private CouponSystem() {
		
		dailyCouponExpirationTask = new DailyCouponExpirationTask();
		Thread thread = new Thread(dailyCouponExpirationTask);
		thread.start();
	}
	
	public CouponClientFacade login(String name, String password, String type) {
		
		switch(type) {
			case "ADMIN":
				AdminFacade adminFacade = new AdminFacade().login(name, password);
			
		}
		return null;
	}
	
	public CouponSystem getInstance() {
		return instance;
	}

}
