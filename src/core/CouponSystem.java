package core;

import connections.ConnectionPool;
import facade.AdminFacade;
import facade.CompanyFacade;
import facade.CouponClientFacade;
import facade.CustomerFacade;

public class CouponSystem {
	
	private static CouponSystem instance = new CouponSystem();
	private DailyCouponExpirationTask dailyCouponExpirationTask; 
	private ConnectionPool connectionPool;
	private Thread maintenanceThread;
	
	private CouponSystem() {
		startCouponSystem();
	}
	
	private void startCouponSystem() {
		
		connectionPool = ConnectionPool.getInstance();
		dailyCouponExpirationTask = new DailyCouponExpirationTask();
		maintenanceThread = new Thread(dailyCouponExpirationTask);
		maintenanceThread.start();
	}
	
	public CouponClientFacade login(String name, String password, ClientType type) throws CouponSystemException {
		
		switch(type) {
		
			case ADMIN:
				return (AdminFacade) new AdminFacade().login(name, password);
			case COMPANY:
				return (CompanyFacade) new CompanyFacade().login(name, password);
			case CUSTOMER:
				return (CustomerFacade) new CustomerFacade().login(name, password);
		}
		return null;
	}
	
	public void shutdown() {
		dailyCouponExpirationTask.stopTask();
		maintenanceThread.interrupt();
		connectionPool.closeAllConnections();
	}
	
	public static CouponSystem getInstance() {
		return instance;
	}

}
