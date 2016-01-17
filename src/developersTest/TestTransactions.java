package developersTest;

import java.util.Date;

import beans.Coupon;
import beans.CouponType;
import facade.CompanyFacade;
import facade.CouponClientFacade;

public class TestTransactions {

	public static void main(String[] args) {

		Coupon updatedCoupon = new Coupon("some coupon", new Date(), new Date(), 10000, CouponType.FOOD, "hello yo mama is fat", 1.6, "some url");
		CouponClientFacade ccf = new CompanyFacade();
		ccf = ccf.login("microhard", "1234", "Company");
		CompanyFacade cf = (CompanyFacade) ccf;
	}

}
