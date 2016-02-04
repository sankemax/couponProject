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
			Coupon coupon2 = new Coupon("armpit sale", new Date(), new Date(new Date().getTime() + 1000*3600*24), 10, CouponType.HEALTH, "buy meeeeeeee", 200.5, "http://something.co");
			Coupon coupon3 = new Coupon("retard clock", new Date(), new Date(new Date().getTime() + 1000*3600*24), 10, CouponType.ELECTRICITY, "please!", 145.5, "http://something.co");
			Coupon coupon4 = new Coupon("clothes for fat ppl", new Date(), new Date(new Date().getTime() + 1000*3600*24), 10, CouponType.CLOTHES, "me", 230.5, "http://something.co");
			Customer customer = new Customer("mike", "red full");
			Customer customer2 = new Customer("herold", "shephard");
			Customer customer3 = new Customer("brian", "life");
			Customer customer4 = new Customer("brian", "smoken");
//			ad.createCompany(company);
//			ad.createCustomer(customer);
//			ad.createCustomer(customer2);
//			ad.createCustomer(customer3);
//			ad.createCustomer(customer4);
			
			CompanyFacade cf = (CompanyFacade) couponSystem.login("tryonics", "bigboss123", ClientType.COMPANY);
//			cf.createCoupon(coupon);
//			cf.createCoupon(coupon2);
//			cf.createCoupon(coupon3);
//			cf.createCoupon(coupon4);
			
			CustomerFacade cuf = (CustomerFacade) couponSystem.login("mike", "red full", ClientType.CUSTOMER);
			CustomerFacade cuf2 = (CustomerFacade) couponSystem.login("herold", "shephard", ClientType.CUSTOMER);
			CustomerFacade cuf3 = (CustomerFacade) couponSystem.login("brian", "life", ClientType.CUSTOMER);
//			cuf.purchaseCoupon(coupon);
//			cuf.purchaseCoupon(coupon2);
//			cuf.purchaseCoupon(coupon3);
//			cuf2.purchaseCoupon(coupon4);
//			cuf3.purchaseCoupon(coupon);
//			cuf3.purchaseCoupon(coupon4);
			
			coupon.setImage("changed the image");
			coupon.setMessage("changed the message");
			coupon.setType(CouponType.CLOTHES);
			coupon.setPrice(500);
			coupon2.setPrice(500);
			cf.updateCoupon(coupon);
			cf.updateCoupon(coupon2);
//			System.out.println(cf.getCouponByTitle(coupon.getTitle()));			
//			System.out.println(cf.getCoupon(coupon.getId()));			
//			System.out.println(cf.getCouponByType(CouponType.CLOTHES));			
			System.out.println(cf.getCouponByPrice(200));			
//			System.out.println(cuf.getAllpurchasedCouponByType(CouponType.SPORTS));
//			System.out.println(cuf.getAllpurchasedCoupons());		
//			System.out.println(cf.getAllCoupons());
//			System.out.println(cf.getCouponByType(CouponType.ELECTRICITY));
			
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