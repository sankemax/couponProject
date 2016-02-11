package dao.dbdao.connections;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import core.CouponSystemException;
import core.beans.Coupon;
import core.beans.CouponType;

public class SqlUtility {

	/**Rolls back the uncommitted changes that were made in the DB.
	 * @param connection the connection that was used to communicate with the DB.
	 * @throws CouponSystemException if the rollback isn't successful.
	 */
	public static void rollbackConnection(Connection connection) throws CouponSystemException {
		try {
			
			if (connection != null) connection.rollback();
			
		} catch (SQLException e) {
			throw new CouponSystemException(CouponSystemException.SYSTEM_ERROR);
		}
	}
	
	/** Closes a statement that was created by some result set.
	 * @param statement the statement which is to be closed.
	 * @throws CouponSystemException if the closure isn't successful.
	 */
	public static void closeStatement(Statement statement) throws CouponSystemException {
		
		try {
			
			if (statement != null) statement.close();
			
		} catch (SQLException e) {
			throw new CouponSystemException(CouponSystemException.SYSTEM_ERROR);
		}
	}
	
	/**Closes a result set that was created by some statement.
	 * @param resultSet the result set which is to be closed.
	 * @throws CouponSystemException if the closure isn't successful.
	 */
	public static void closeResultSet(ResultSet resultSet) throws CouponSystemException {
		
		try {
			
			if (resultSet != null) resultSet.close();
			
		} catch (SQLException e) {
			throw new CouponSystemException(CouponSystemException.SYSTEM_ERROR);
		}
	}
	
	/**Closes a connection <b>use this method in case the connection is not part of the connection pool</b>.
	 * @param connection the connection which is to be closed.
	 * @throws CouponSystemException if the closure isn't successful.
	 */
	public static void closeConnection(Connection connection) throws CouponSystemException {
		
		try {
			
			if (connection != null) connection.close();
			
		} catch (SQLException e) {
			throw new CouponSystemException(CouponSystemException.SYSTEM_ERROR);
		}
	}
	
	/**
	 * Creates a list of coupons from a given result set. Used for minimizing of duplicated code.
	 * @param resultSet the result set that stores the coupons that were extracted from the DB.
	 * @return a list of coupons.
	 * @throws CouponSystemException if there are no coupons in the result set or if encountered IO failure.
	 */
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
