package facade;

import core.CouponSystemException;

public interface CouponClientFacade {
	
	public CouponClientFacade login(String name, String password) throws CouponSystemException;

}
