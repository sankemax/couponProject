package core;

import connections.ConnectionPool;
import facade.AdminFacade;
import facade.CouponClientFacade;

public class CouponSystem {
	
	private CouponSystem instance = new CouponSystem();
	private DailyCouponExpirationTask dailyCouponExpirationTask; 
	private ConnectionPool connectionPool;
	private Thread thread;
	
	private CouponSystem() {
		
		dailyCouponExpirationTask = new DailyCouponExpirationTask();
		thread = new Thread(dailyCouponExpirationTask);
		thread.start();
		connectionPool = ConnectionPool.getInstance();
	}
	
	public CouponClientFacade login(String name, String password, ClientType type) {
		
		switch(type) {
			case ADMIN:
				return(AdminFacade) new AdminFacade().login(name, password);
			case COMPANY:
				return(AdminFacade) new AdminFacade().login(name, password);
			case CUSTOMER:
				return(AdminFacade) new AdminFacade().login(name, password);
		}
		return null;
	}
	
	public void shutdown(){
		connectionPool.closeAllConnections();
		dailyCouponExpirationTask.stopTask();
		thread.interrupt();
	}
	
	public CouponSystem getInstance() {
		return instance;
	}

}
