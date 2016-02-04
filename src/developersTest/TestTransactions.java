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
		DbOperations.createDbAndTables();;
		
		try {
			CouponSystem couponSystem = CouponSystem.getInstance();
			AdminFacade ad = (AdminFacade) couponSystem.login("admin", "1234", ClientType.ADMIN);
			
			Company company = new Company("tryonics", "bigboss123", "this@is.sparta");
			Coupon coupon = new Coupon("clothes sale", new Date(), new Date(new Date().getTime() + 1000*3600*24), 10, CouponType.SPORTS, "buy me please!", 145.5, "http://something.co");
			Customer customer = new Customer("mike", "red full");
			Customer customer2 = new Customer("herold", "shephard");
			Customer customer3 = new Customer("brian", "life");
//			ad.createCompany(company);
//			ad.createCustomer(customer);
//			ad.createCustomer(customer2);
//			ad.createCustomer(customer3);
			
			CompanyFacade cf = (CompanyFacade) couponSystem.login("tryonics", "bigboss123", ClientType.COMPANY);
//			cf.createCoupon(coupon);
			
			CustomerFacade cuf = (CustomerFacade) couponSystem.login("mike", "red full", ClientType.CUSTOMER);
//			CustomerFacade cuf2 = (CustomerFacade) couponSystem.login("herold", "smallAnt", ClientType.CUSTOMER);
			CustomerFacade cuf3 = (CustomerFacade) couponSystem.login("brian", "life", ClientType.CUSTOMER);
//			cuf.purchaseCoupon(coupon);
//			cuf2.purchaseCoupon(coupon);
//			cuf3.purchaseCoupon(coupon);
			
			System.out.println(cuf.getAllpurchasedCouponByType(CouponType.SPORTS));
			System.out.println(cuf.getAllpurchasedCoupons());		
			System.out.println(cf.getAllCoupons());
//			customer2.setPassword("smallAnt");
//			ad.updateCustomer(customer2);
//			ad.removeCustomer(customer2);
//			company.setEmail("sparta@is-no.more");
//			ad.updateCompany(company);
//			ad.removeCompany(company);
			
		} catch (CouponSystemException e) {
			e.printStackTrace();
		}
	}
}