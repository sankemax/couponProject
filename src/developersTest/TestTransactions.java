package developersTest;

import java.util.Date;
import beans.Company;
import beans.Coupon;
import beans.CouponType;
import beans.Customer;
import connections.DbOperations;
import core.ClientType;
import core.CouponSystem;
import core.CouponSystemException;
import facade.AdminFacade;
import facade.CompanyFacade;
import facade.CustomerFacade;

public class TestTransactions {

	public static void main(String[] args) {
		DbOperations.main(null);
		CouponSystem couponSystem = CouponSystem.getInstance();
		try {
			
			AdminFacade ad = (AdminFacade) couponSystem.login("admin", "1234", ClientType.ADMIN);
			
			Company company = new Company("tryonics", "bigboss123", "this@is.sparta");
			Coupon coupon = new Coupon("clothes sale", new Date(), new Date(new Date().getTime() + 1000000), 10, CouponType.SPORTS, "buy me please!", 145.5, "http://something.co");
			Customer customer = new Customer("mike", "red full");
//			ad.createCompany(company);
//			ad.createCustomer(customer);
			
			CompanyFacade cf = (CompanyFacade) couponSystem.login("tryonics", "bigboss123", ClientType.COMPANY);
//			cf.createCoupon(coupon);
			
			CustomerFacade cuf = (CustomerFacade) couponSystem.login("mike", "red full", ClientType.CUSTOMER);
			cuf.purchaseCoupon(coupon);
			
		} catch (CouponSystemException e) {
			e.printStackTrace();
		}
	}
}