package developersTest;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import core.*;
import core.beans.*;
import dao.dbdao.connections.DbOperations;
import facade.*;

public class TestTransactions {

	public static void main(String[] args) {
		
		Calendar cal = Calendar.getInstance();

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
			System.out.println("admin updates company1 (password & email) and gets company1: " + admin.getCompany(company1.getId()));
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
			
			// Trying to get a customer that is not registered in the system:
			try {
				admin.getCustomerByName("aaaa");
			} catch (CouponSystemException e) {
				System.err.println("Trying to get a customer that is not registered in the system prints: " + e.getMessage());
			}

			// updating customer password
			customer1.setPassword("4321");
			admin.updateCustomer(customer1);
			System.out.println("admin updates customer1 password and gets customer1 by ID: " + admin.getCustomer(customer1.getId()));
			
			admin.createCustomer(customer3);
			System.out.println("list of all customers:");
			System.out.println(admin.getAllCustomers());
			
			// remove a customer
			System.out.println("removing customer " + customer3.getId() + ":");
			admin.removeCustomer(customer3);
			
			System.out.println("list of all customers after removal:");
			System.out.println(admin.getAllCustomers());
			
			// company and his functions:
			CompanyFacade company = (CompanyFacade) couponSystem.login("Maxim", "4321", ClientType.COMPANY);
			
			// company login in with a wrong name
			try{
				@SuppressWarnings("unused")
				CompanyFacade companyTest = (CompanyFacade) couponSystem.login("Maxim", "12345", ClientType.COMPANY);
			}catch (CouponSystemException e){
				System.err.println("logging in with wrong username or password prints: " + e.getMessage());
			}
			
			//coupons
			Coupon coupon1 = new Coupon("clothes sale5", new Date(), new Date(cal.getTimeInMillis() + 1000*3600*24), 10, CouponType.SPORTS, "buy me please!", 30, "http://something.co");
			Coupon coupon2 = new Coupon("some other sale5", new Date(), new Date(cal.getTimeInMillis() + 1000*3600*24*2), 10, CouponType.HEALTH, "buy meeeeeeee", 35.5, "http://something.co");
			Coupon coupon3 = new Coupon("clock5", new Date(), new Date(cal.getTimeInMillis() - 1000*3600*24*4), 10, CouponType.ELECTRICITY, "please!", 40, "http://something.co");
			Coupon coupon4 = new Coupon("fat cat for ppl5", new Date(), new Date(cal.getTimeInMillis() + 1000*3600*24*7), 0, CouponType.CLOTHES, "me", 45.5, "http://something.co");
			
			
			// Trying to get all company coupons when there are none
			try{
				company.getAllCoupons();
			}catch (CouponSystemException e){
				System.err.println("Trying to get all company coupons when there are none, prints: " + e.getMessage());
			}
			
			// creating coupons
			try {
				
				// optional for testing purposes: remove coupons from system before creating them again
				/**
				 *  the next few rows are written to get the coupon ID into the coupon bean in order to correctly remove them
				 */
//				coupon1.setCouponId(company.getCouponByTitle(coupon1.getTitle()).getId());
//				coupon1.setCouponId(company.getCouponByTitle(coupon2.getTitle()).getId());
//				coupon1.setCouponId(company.getCouponByTitle(coupon3.getTitle()).getId());
//				coupon1.setCouponId(company.getCouponByTitle(coupon4.getTitle()).getId());
				/**
				 * 
				 */
				
				// coupon removal for convenience of testing
//				company.removeCoupon(coupon1);
//				company.removeCoupon(coupon2);
//				company.removeCoupon(coupon3);
//				company.removeCoupon(coupon4);
				
				// coupon creation
				company.createCoupon(coupon1);
				company.createCoupon(coupon2);
				company.createCoupon(coupon3);
				company.createCoupon(coupon4);
				
			} catch(CouponSystemException e) {
				System.err.println("trying to create a coupon that already exists prints: " + e.getMessage());
			}

			// get coupon queries:
			try {
				
				// get coupon by title
				System.out.println(company.getCouponByTitle(coupon1.getTitle()));
				
				// get all coupons
				System.out.println(company.getAllCoupons());
				
				//get coupon by price
				System.out.println(company.getCouponByPrice(39));
				
				//get coupon by type
				System.out.println(company.getCouponByType(CouponType.HEALTH));

				//get coupon by date
				System.out.println(company.getCouponByDate(new java.util.Date()));
				
			} catch(CouponSystemException e) {				
				System.err.println("trying to get coupons that don't exist, prints: " + e.getMessage());
			}
			
			//Trying to create a coupon with the same name
			try{
				company.createCoupon(coupon1);
			}catch (CouponSystemException e){
				System.err.println("Trying to create a coupon which already exists / with the same name, prints: " + e.getMessage());
			}
			
			//update coupon
			
			coupon1.setCouponId(company.getCouponByTitle(coupon1.getTitle()).getId());
			System.out.println("updating the coupon:\n" + company.getCoupon(coupon1.getId()));
			coupon1.setEndDate(new Date(cal.getTimeInMillis() + 1000*3600*24*3));
			coupon1.setPrice(20);
			coupon1.setMessage("hello world");
			coupon1.setType(CouponType.RESTAURANTS);
			company.updateCoupon(coupon1);
			System.out.println("coupon after update (if the coupon was already updated in other runs, you may not see any difference):\n" + company.getCoupon(coupon1.getId()));
			
			//customer and his functions:
			CustomerFacade customer = (CustomerFacade) couponSystem.login("mike", "4321", ClientType.CUSTOMER);
			
			
			//customer login with wrong name
			try{
				@SuppressWarnings("unused")
				CustomerFacade customerTest = (CustomerFacade) couponSystem.login("mike", "12345", ClientType.CUSTOMER);
			}catch (CouponSystemException e){
				System.err.println("when a customer login is incorrect, it prints: " + e.getMessage());
			}
			
			
			//Trying to get coupons before purchased
			try{
				customer.getAllpurchasedCoupons();
			}catch (CouponSystemException e){
				System.err.println("if the customer has no coupons, it prints: " + e.getMessage());
			}
			
			
				
			// in case the information of the coupon was changed during the developers test (ID and/or name) and the bean no longer
			// corresponds to the actual information in the DB we refresh the beans:
			try {
				
				List<Coupon> list = company.getAllCoupons();
				coupon1 = list.get(0);
				coupon2 = list.get(1);
				coupon3 = list.get(2);
				coupon4 = list.get(3);
				
			} catch (CouponSystemException e) {
				System.err.println(e.getMessage());
			}
			
			// a customer purchases a coupon (in this specific developers check, we suppose that the coupon exists):			
			try{
			
			customer.purchaseCoupon(coupon1);
			customer.purchaseCoupon(coupon2);
			
			//Attempt to buy a coupon that was already bought
			customer.purchaseCoupon(coupon2);
			}catch (CouponSystemException e){
				System.err.println("while attempting to buy an already bought coupon, it prints: " + e.getMessage());
			}
			
			//Attempt to buy an expired coupon:
			try{
				customer.purchaseCoupon(coupon3);
			}catch (CouponSystemException e){
				System.err.println("while attempting to buy an expired coupon, it prints (in order for this test to work, one needs to disable the DailyCouponExpirationTask, so it won't delete the expired coupon): " + e.getMessage());
			}
			
			// Attempt to buy a coupon that is out of stock:
			try{
				customer.purchaseCoupon(coupon4);
			}catch (CouponSystemException e){
				System.err.println("while attempting to buy a coupon that is out of stock, it prints: " + e.getMessage());
			}
			
			//get all purchased coupons
			System.out.println("all customers purchased coupons:\n" + customer.getAllpurchasedCoupons());
			
			//get all purchased coupon by price
			System.out.println("all customers' purchased coupons by price (39):\n" + customer.getAllpurchasedCouponByPrice(39));
			
			//get all purchased coupon by type
			System.out.println("all customers purchased couponsby type (health):\n" + customer.getAllpurchasedCouponByType(CouponType.HEALTH));
			
			// get coupons by price using company facade:
			try {
				System.out.println("20:");
				System.out.println(company.getCouponByPrice(20));
				System.out.println("36:");
				System.out.println(company.getCouponByPrice(36));
				System.out.println("47:");
				System.out.println(company.getCouponByPrice(47));
				System.out.println("5:");
				System.out.println(company.getCouponByPrice(5));
			} catch(CouponSystemException e) {
				System.err.println("when there are no coupons registered for the COMPANY under the specified amount, it prints: " + e.getMessage());
			}

			// get coupons by price using cusotmer facade:
			try {
				System.out.println("20:");
				System.out.println(customer.getAllpurchasedCouponByPrice(20));
				System.out.println("36:");
				System.out.println(customer.getAllpurchasedCouponByPrice(36));
				System.out.println("47:");
				System.out.println(customer.getAllpurchasedCouponByPrice(47));
				System.out.println("5:");
				System.out.println(customer.getAllpurchasedCouponByPrice(5));
			} catch(CouponSystemException e) {
				System.err.println("when there are no coupons registered for the CUSTOMER under the specified amount, it prints: " + e.getMessage());
			}

			// get coupons by end-date using company facade:
			try {
				// a date after the creation of the coupons
				System.out.println("coupons that are valid till: " + new java.util.Date());
				System.out.println(company.getCouponByDate(new java.util.Date()));
				
				// a date that preceeded the creation of the coupons (if they were created in this run):
				System.out.println("coupons that are valid till: " + new java.util.Date(cal.getTimeInMillis() - 990000000));
				System.out.println(company.getCouponByDate(new java.util.Date(cal.getTimeInMillis() - 999999999)));
			} catch(CouponSystemException e) {
				System.err.println("when there are no coupons registered for the COMPANY that are valid till the specified date, it prints: " + e.getMessage());
			}
			
			// get all coupons before deletion
			System.out.println("all companys' coupons before a deletion of a coupon:\n");			
			System.out.println(company.getAllCoupons());			
			//company remove coupon
			company.removeCoupon(coupon1);
			
			//get all coupons After deletion
			System.out.println("all companys' coupons after a deletion of a coupon:\n");			
			System.out.println(company.getAllCoupons());			
			
			//admin remove company
			admin.removeCompany(company1);
			
			// get all purchased customers' coupons After deletion of the company (and subsequently the coupons)
			try{
				customer.getAllpurchasedCoupons();
			}catch (CouponSystemException e){
				System.err.println("when a customer has no purchased coupons, it prints: " + e.getMessage());
			}
			
			couponSystem.shutdown();

		} catch (CouponSystemException e) {
			System.err.println(e.getMessage());
		}
	}
}
