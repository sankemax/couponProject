package core;

import connections.ConnectionPool;
import facade.AdminFacade;
import facade.CompanyFacade;
import facade.CouponClientFacade;
import facade.CustomerFacade;

public class CouponSystem {
	
	private static CouponSystem instance;
	private DailyCouponExpirationTask dailyCouponExpirationTask; 
	private ConnectionPool connectionPool;
	private Thread maintenanceThread;
	
	private CouponSystem() throws CouponSystemException {
		startCouponSystem();
	}
	
	private void startCouponSystem() throws CouponSystemException {
		
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
			default: 
				throw new CouponSystemException(CouponSystemException.SYSTEM_ERROR); 
		}
	}
	
	public void shutdown() throws CouponSystemException {
		
		dailyCouponExpirationTask.stopTask();
		maintenanceThread.interrupt();
		connectionPool.closeAllConnections();
	}
	
	public static CouponSystem getInstance() throws CouponSystemException {
		if(instance == null){
			instance = new CouponSystem();
		}
		return instance;
	}

}
