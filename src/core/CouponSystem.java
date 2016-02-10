package core;

import dao.dbdao.connections.ConnectionPool;
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
	
	public static CouponSystem getInstance() throws CouponSystemException {
		if(instance == null){
			instance = new CouponSystem();
		}
		return instance;
	}
	
	private void startCouponSystem() throws CouponSystemException {
		
		connectionPool = ConnectionPool.getInstance();
		
		// instantiating a new thread that will run the daily coupon expiration task runnable, which will be set to daemon state
		dailyCouponExpirationTask = new DailyCouponExpirationTask();
		maintenanceThread = new Thread(dailyCouponExpirationTask);
		maintenanceThread.setDaemon(true);	
		maintenanceThread.start();
	}
	
	public CouponClientFacade login(String name, String password, ClientType type) throws CouponSystemException {
		
		// returning the right facade according to the client type. in case the login wasn't successful or the client somehow 
		// sent a request for an unknown client type an exception will be thrown
		switch(type) {
			case ADMIN:
				return new AdminFacade().login(name, password);
			case COMPANY:
				return new CompanyFacade().login(name, password);
			case CUSTOMER:
				return new CustomerFacade().login(name, password);
			default: 
				throw new CouponSystemException(CouponSystemException.SYSTEM_ERROR); 
		}
	}
	
	public void shutdown() throws CouponSystemException {
		
		// shutdown of the system
		dailyCouponExpirationTask.stopTask();
		maintenanceThread.interrupt();
		connectionPool.closeAllConnections();
	}
}
