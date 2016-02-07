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
		try {
			DbOperations.createDbAndTables();
		} catch (CouponSystemException e1) {
			System.err.println("creation of the tables was unsuccessfull " + e1.getMessage());
		}
		
		try {
			
			//couponSystem load:
			CouponSystem couponSystem = CouponSystem.getInstance();
			
			//admin and his functions:
			AdminFacade admin = (AdminFacade) couponSystem.login("ADMIN", "1234", ClientType.ADMIN);
			System.err.println("admin is logged in. (printed as an error for visual convenience to represent a correct flow of events)");
			
			//companies
			Company company1 = new Company("Maxim","1234","maxim1@gmail.com");
			Company company2 = new Company("Meir","1234","meir1@gmail.com");
			Company company3 = new Company("Max","1234","meir2@gmail.com");
			
			
			try{
				admin.createCompany(company1);
				// in this demonstration, once the main runs the second time, the ID of the company will not be saved in the company bean, 
				// since it was created in the first run, so if one wants to get the company by its ID, it is necessary to get the id using the COMPANY NAME, and only then, to show
				// the functionality of the method getCompany that gets the bean form the DB using the ID.
				// this statement is valid for some other lines in the code as well, for example when trying to view a customer by ID
				long compId = admin.getCompanyByName(company1.getCompName()).getId();
				System.out.println("admin creates and gets company1: " + admin.getCompany(compId));
				admin.createCompany(company2);
				String companyName = company2.getCompName();
				System.out.println("admin creates and gets company2: " + admin.getCompanyByName(companyName));
				
			}catch (CouponSystemException e){
				System.err.println("trying to create a company that already exists prints: "+e.getMessage());
			}
			
			//Trying to create a company that already exists
			try{
				admin.createCompany(company2);
			}catch (CouponSystemException e){
				System.err.println("trying to create a company with the same name prints: "+e.getMessage());
			}
			
			
			//Trying to get a company that is not registered in the System
			try {
				admin.getCompanyByName("aaaa");
			} catch (CouponSystemException e) {
				System.err.println("trying to get a company that doesnt exists prints: "+e.getMessage());
			}
			
			//updateCompany
			company1.setPassword("4321");
			company1.setEmail("gmail@Maxim.com");
			admin.updateCompany(company1);
			System.out.println("admin update (password & email) and get company1: " + admin.getCompany(company1.getId()));
			try {
				
				admin.createCompany(company3);
			} catch(CouponSystemException e) {				
				System.err.println("trying to create a company that already exists prints: "+e.getMessage());
			}
			
			// view all companies
			try {
				
				System.out.println("admin asks to view all companies:");
				System.out.println(admin.getAllCompanies());
				
			} catch(CouponSystemException e) {
				System.err.println("trying to view all companies, while none exist prints:" + e.getMessage());
			}
			
			// admin removes a Company:
			try {
				
				admin.removeCompany(company3);
				System.out.println("all companies after removal:");
				System.out.println(admin.getAllCompanies());
			} catch(CouponSystemException e) {				
				if (e.getMessage().equals(CouponSystemException.COMPANIES_NOT_EXIST)) {
					System.err.println("trying to view all companies, while none exist prints:");
				} else {					
					System.err.println("tring to remove a company that does not exist prints:");
				}
				System.err.println(e.getMessage());
			}
			
			//customers
			Customer customer1 = new Customer("mike", "1234");
			Customer customer2 = new Customer("herold", "1234");
			Customer customer3 = new Customer("brian", "1234");
			
			try {				
				System.out.println("admin creates customer1:");
				admin.createCustomer(customer1);
			} catch (CouponSystemException e) {
				System.err.println("trying to create a customer that already exists prints: " + e.getMessage());
			}
			long customerId = admin.getCustomerByName(customer1.getCustName()).getId();
			System.out.println("admin gets customer1 by ID: " + admin.getCustomer(customerId));

			try {				
				System.out.println("admin creates customer2:");
				admin.createCustomer(customer2);
			} catch (CouponSystemException e) {
				System.err.println("trying to create a customer that already exists prints: " + e.getMessage());
			}
			
			String customerName = customer2.getCustName();
			System.out.println("admin gets customer2 by NAME: " + admin.getCustomerByName(customerName));
			
			//Trying to create a customer with the same name
			try{
				admin.createCustomer(customer2);
			}catch (CouponSystemException e){
				System.err.println("trying to create a customer with an already existing name in the DB prints:" + e.getMessage());
			}
			
			//Trying to get a customer that is not registered in the system:
			try {
				admin.getCustomerByName("aaaa");
			} catch (CouponSystemException e) {
				System.err.println("Trying to get a customer that is not registered in the system prints: " + e.getMessage());
			}
			
			//update customer
			customer1.setPassword("4321");
			admin.updateCustomer(customer1);
			System.out.println("admin updates customer1 password and gets customer1 by ID: " + admin.getCustomer(customer1.getId()));
			
			// TODO i've stopped here... continue next time
			admin.createCustomer(customer3);
			System.out.println("all customer");
			System.out.println(admin.getAllCustomer());
			
			//remove customer
			admin.removeCustomer(customer3);
			
			System.out.println("all customer");
			System.out.println(admin.getAllCustomer());
			
		
			
			//company and his functions:
			CompanyFacade company = (CompanyFacade) couponSystem.login("Maxim", "4321", ClientType.COMPANY);
			
			
			//company logging in with wrong name
			try{
				CompanyFacade companyTest = (CompanyFacade) couponSystem.login("Maxim", "12345", ClientType.COMPANY);
			}catch (CouponSystemException e){
				System.err.println(e.getMessage());
			}
			
			//coupons
			Coupon coupon1 = new Coupon("clothes sale5", new Date(), new Date(new Date().getTime() + 1000*3600*24), 10, CouponType.SPORTS, "buy me please!", 30, "http://something.co");
			Coupon coupon2 = new Coupon("armpit sale5", new Date(), new Date(new Date().getTime() + 1000*3600*24*2), 10, CouponType.HEALTH, "buy meeeeeeee", 35.5, "http://something.co");
			Coupon coupon3 = new Coupon("retard clock5", new Date(), new Date(new Date().getTime() - 1000*3600*24*4), 10, CouponType.ELECTRICITY, "please!", 40, "http://something.co");
			Coupon coupon4 = new Coupon("clothes for fat ppl5", new Date(), new Date(new Date().getTime() + 1000*3600*24*7), 0, CouponType.CLOTHES, "me", 45.5, "http://something.co");
			
			
			//Trying to get coupons before created
			try{
				company.getAllCoupons();
			}catch (CouponSystemException e){
				System.err.println(e.getMessage());
			}
			
			company.createCoupon(coupon1);
			company.createCoupon(coupon2);
			company.createCoupon(coupon3);
			company.createCoupon(coupon4);

			//get coupon by title
			System.out.println(company.getCouponByTitle(coupon1.getTitle()));
			
			//get coupons
			System.out.println(company.getAllCoupons());
			
			//get coupon by price
			System.out.println(company.getCouponByPrice(39));
			
			//get coupon by type
			System.out.println(company.getCouponByType(CouponType.HEALTH));
			
			
			//Trying to create a coupon with the same name
			try{
				company.createCoupon(coupon1);
			}catch (CouponSystemException e){
				System.err.println(e.getMessage());
			}
			
			
			//update coupon
			coupon1.setEndDate(new Date(new Date().getTime() + 1000*3600*24*3));
			coupon1.setPrice(20);
			company.updateCoupon(coupon1);
			System.out.println(company.getCoupon(coupon1.getId()));
			
			
			
			//customer and his functions:
			CustomerFacade customer = (CustomerFacade) couponSystem.login("mike", "4321", ClientType.CUSTOMER);
			
			
			//customer logging in with wrong name
			try{
				CustomerFacade customerTest = (CustomerFacade) couponSystem.login("mike", "12345", ClientType.CUSTOMER);
			}catch (CouponSystemException e){
				System.err.println(e.getMessage());
			}
			
			
			//Trying to get coupons before purchased
			try{
				customer.getAllpurchasedCoupons();
			}catch (CouponSystemException e){
				System.err.println(e.getMessage());
			}
			
			
			//purchase coupon
			customer.purchaseCoupon(coupon1);
			customer.purchaseCoupon(coupon2);
			
			
			//Attempt to buy a coupon already bought
			try{
				customer.purchaseCoupon(coupon1);
			}catch (CouponSystemException e){
				System.err.println(e.getMessage());
			}
			
			
			//Attempt to buy a coupon expired
			try{
				customer.purchaseCoupon(coupon3);
			}catch (CouponSystemException e){
				System.err.println(e.getMessage());
			}
			
			//Attempt to buy a coupon is out of stock
			try{
				customer.purchaseCoupon(coupon4);
			}catch (CouponSystemException e){
				System.err.println(e.getMessage());
			}
			
			
			//get all purchased coupons
			System.out.println(customer.getAllpurchasedCoupons());
			
			//get all purchased coupon by price
			System.out.println(customer.getAllpurchasedCouponByPrice(39));
			
			//get all purchased coupon by type
			System.out.println(customer.getAllpurchasedCouponByType(CouponType.HEALTH));
			
			//company remove coupon
			company.removeCoupon(coupon1);
			
			//get all coupons After deletion
			System.out.println(company.getAllCoupons());
			
			//admin remove company
			admin.removeCompany(company1);
			
			//get all purchased coupons After deletion
			try{
				customer.getAllpurchasedCoupons();
			}catch (CouponSystemException e){
				System.err.println(e.getMessage());
			}
			
			
			
			couponSystem.shutdown();

		} catch (CouponSystemException e) {
			e.printStackTrace();
		}
	}
}