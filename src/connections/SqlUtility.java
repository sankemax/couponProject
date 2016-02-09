package connections;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import beans.Coupon;
import beans.CouponType;
import core.CouponSystemException;

public class SqlUtility {

	public static void rollbackConnection(Connection connection) throws CouponSystemException {
		try {
			
			if (connection != null) connection.rollback();
			
		} catch (SQLException e) {
			throw new CouponSystemException(CouponSystemException.SYSTEM_ERROR);
		}
	}
	
	public static void closeStatement(Statement statement) throws CouponSystemException {
		
		try {
			
			if (statement != null) statement.close();
			
		} catch (SQLException e) {
			throw new CouponSystemException(CouponSystemException.SYSTEM_ERROR);
		}
	}
	
	public static void closeResultSet(ResultSet resultSet) throws CouponSystemException {
		
		try {
			
			if (resultSet != null) resultSet.close();
			
		} catch (SQLException e) {
			throw new CouponSystemException(CouponSystemException.SYSTEM_ERROR);
		}
	}
	
	// in case the connection is not part of the connectionPool
	public static void closeConnection(Connection connection) throws CouponSystemException {
		
		try {
			
			if (connection != null) connection.close();
			
		} catch (SQLException e) {
			throw new CouponSystemException(CouponSystemException.SYSTEM_ERROR);
		}
	}
	
	public static List<Coupon> createCoupons(ResultSet resultSet) throws CouponSystemException{
		List<Coupon> couponList = new ArrayList<Coupon>();
		
		try {
			if(resultSet.next()){
				do{
					Coupon coupon = new Coupon(resultSet.getString(2), 
							new java.util.Date(resultSet.getTimestamp(3).getTime()), 
							new java.util.Date(resultSet.getTimestamp(4).getTime()),
							resultSet.getInt(5),
							CouponType.valueOf(resultSet.getString(6)),
							resultSet.getString(7),
							resultSet.getDouble(8),
							resultSet.getString(9));
					coupon.setCouponId(resultSet.getLong(1));
					couponList.add(coupon);
				}while (resultSet.next());
			} else {
				throw new CouponSystemException(CouponSystemException.COUPONS_NOT_EXIST);
			}
		} catch (SQLException e) {
			throw new CouponSystemException(CouponSystemException.SYSTEM_ERROR);
		}
		return couponList;
	}
}
