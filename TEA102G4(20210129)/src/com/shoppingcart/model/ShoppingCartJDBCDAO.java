package com.shoppingcart.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ShoppingCartJDBCDAO implements ShoppingCart_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "TEA102G4";
	String passwd = "123456";
	private static final String INSERT_STMT = "INSERT INTO SHOPPINGCART(SHOPPINGCARTID,CUSTID,MEALID,MEALCOUNT,ADDDATE)VALUES('S' || lpad(SHOPPINGCART_seq.NEXTVAL, 5, '0'),?,?,?,?)";
	private static final String GET_ALL_STMT = "SELECT SHOPPINGCARTID,CUSTID,MEALID,MEALCOUNT,ADDDATE FROM SHOPPINGCART order by SHOPPINGCARTID";
	private static final String GET_ONE_STMT = "SELECT SHOPPINGCARTID,CUSTID,MEALID,MEALCOUNT,ADDDATE FROM SHOPPINGCART where SHOPPINGCARTID = ?";
	private static final String DELETE = "DELETE FROM SHOPPINGCART where SHOPPINGCARTID = ?";
	private static final String UPDATE = "UPDATE SHOPPINGCART set CUSTID=?, MEALID=?, MEALCOUNT=?, ADDDATE=? where SHOPPINGCARTID = ?";
	private static final String GET_ALL_FORCUSTID = "SELECT SHOPPINGCARTID,CUSTID,MEALID,MEALCOUNT,ADDDATE FROM SHOPPINGCART where CUSTID = ?";
	private static final String GET_ONE_SHOPPINGCARTID = "SELECT SHOPPINGCARTID,CUSTID,MEALID,MEALCOUNT,ADDDATE FROM SHOPPINGCART where CUSTID = ? and MEALID=?";
	@Override
	public void insert(ShoppingCartVO shoppingcartVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, shoppingcartVO.getCustid());
			pstmt.setString(2, shoppingcartVO.getMealid());
			pstmt.setInt(3, shoppingcartVO.getMealcount());
			pstmt.setTimestamp(4, shoppingcartVO.getAddDate());

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}
	@Override
	public void update(ShoppingCartVO shoppingcartVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, shoppingcartVO.getCustid());
			pstmt.setString(2, shoppingcartVO.getMealid());
			pstmt.setInt(3, shoppingcartVO.getMealcount());
			pstmt.setTimestamp(4, shoppingcartVO.getAddDate());
			pstmt.setString(5, shoppingcartVO.getShoppingcartid());

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public void delete(String shoppingcartid) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, shoppingcartid);

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public ShoppingCartVO findByPrimaryKey(String shoppingcartid) {
		ShoppingCartVO shoppingcartVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, shoppingcartid);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				shoppingcartVO = new ShoppingCartVO();
				shoppingcartVO.setShoppingcartid(rs.getString("shoppingcartid"));
				shoppingcartVO.setCustid(rs.getString("custid"));
				shoppingcartVO.setMealid(rs.getString("mealid"));
				shoppingcartVO.setMealcount(rs.getInt("mealcount"));
				shoppingcartVO.setAddDate(rs.getTimestamp("addDate"));

			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return shoppingcartVO;
	}

	@Override
	public List<ShoppingCartVO> getAll() {
		List<ShoppingCartVO> list = new ArrayList<ShoppingCartVO>();
		ShoppingCartVO shoppingcartVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				shoppingcartVO = new ShoppingCartVO();
				shoppingcartVO.setShoppingcartid(rs.getString("shoppingcartid"));
				shoppingcartVO.setCustid(rs.getString("custid"));
				shoppingcartVO.setMealid(rs.getString("mealid"));
				shoppingcartVO.setMealcount(rs.getInt("mealcount"));
				shoppingcartVO.setAddDate(rs.getTimestamp("addDate"));
				list.add(shoppingcartVO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}
	public static void main(String[] args) {

		ShoppingCartJDBCDAO dao = new ShoppingCartJDBCDAO();

		// 新增
//		ShoppingCartVO shoppingcartVO = new ShoppingCartVO();
//		shoppingcartVO.setCustid("C00002");
//		shoppingcartVO.setMealid("EAT00002");
//		shoppingcartVO.setMealcount(5);
//		shoppingcartVO.setAddDate(new java.sql.Timestamp(new java.util.Date().getTime()));
//		dao.insert(shoppingcartVO);

		// 修改
//		ShoppingCartVO shoppingcartVO1 = new ShoppingCartVO();
//		shoppingcartVO1.setShoppingcartid("S00001");
//		shoppingcartVO1.setCustid("C00002");
//		shoppingcartVO1.setMealid("EAT00002");
//		shoppingcartVO1.setMealcount(5);
//		shoppingcartVO1.setAddDate(new java.sql.Timestamp(new java.util.Date().getTime()));
//		dao.update(shoppingcartVO1);

		// 刪除
//		dao.delete("S00002");
//
		// 查詢
//		ShoppingCartVO shoppingcartVO2 = dao.findByPrimaryKey("S00001");
//		System.out.print(shoppingcartVO2.getShoppingcartid() + ",");
//		System.out.print(shoppingcartVO2.getCustid() + ",");
//		System.out.print(shoppingcartVO2.getMealid() + ",");
//		System.out.print(shoppingcartVO2.getMealcount() + ",");
//		System.out.print(shoppingcartVO2.getAddDate());
//		System.out.println("---------------------");

		// 查詢
//		List<ShoppingCartVO> list = dao.getAll();
//		for (ShoppingCartVO shoppingcartVO3 : list) {
//	    System.out.print(shoppingcartVO3.getShoppingcartid() + ",");
//		System.out.print(shoppingcartVO3.getCustid() + ",");
//		System.out.print(shoppingcartVO3.getMealid() + ",");
//		System.out.print(shoppingcartVO3.getMealcount() + ",");
//		System.out.print(shoppingcartVO3.getAddDate());
//			System.out.println();
//		}
		
//		List<ShoppingCartVO> list = dao.getAllForCustid("C00003");
//		for (ShoppingCartVO shoppingcartVO3 : list) {
//	    System.out.print(shoppingcartVO3.getShoppingcartid() + ",");
//		System.out.print(shoppingcartVO3.getCustid() + ",");
//		System.out.print(shoppingcartVO3.getMealid() + ",");
//		System.out.print(shoppingcartVO3.getMealcount() + ",");
//		System.out.print(shoppingcartVO3.getAddDate());
//			System.out.println();
//		}
		ShoppingCartVO shoppingcartVO2 = dao.findShoppingCartid("C00001", "EAT00001");
		System.out.print(shoppingcartVO2.getShoppingcartid() + ",");
		System.out.print(shoppingcartVO2.getCustid() + ",");
		System.out.print(shoppingcartVO2.getMealid() + ",");
		System.out.print(shoppingcartVO2.getMealcount() + ",");
		System.out.print(shoppingcartVO2.getAddDate());
		System.out.println("---------------------");
	}
	@Override
	public List<ShoppingCartVO> getAllForCustid(String custid) {
		List<ShoppingCartVO> list = new ArrayList<ShoppingCartVO>();
		ShoppingCartVO shoppingcartVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_FORCUSTID);
			pstmt.setString(1, custid);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				shoppingcartVO = new ShoppingCartVO();
				shoppingcartVO.setShoppingcartid(rs.getString("shoppingcartid"));
				shoppingcartVO.setCustid(rs.getString("custid"));
				shoppingcartVO.setMealid(rs.getString("mealid"));
				shoppingcartVO.setMealcount(rs.getInt("mealcount"));
				shoppingcartVO.setAddDate(rs.getTimestamp("addDate"));
				list.add(shoppingcartVO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}
	@Override
	public ShoppingCartVO findShoppingCartid(String custid, String mealid) {
		ShoppingCartVO shoppingcartVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_SHOPPINGCARTID);

			pstmt.setString(1, custid);
			pstmt.setString(2, mealid);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				shoppingcartVO = new ShoppingCartVO();
				shoppingcartVO.setShoppingcartid(rs.getString("shoppingcartid"));
				shoppingcartVO.setCustid(rs.getString("custid"));
				shoppingcartVO.setMealid(rs.getString("mealid"));
				shoppingcartVO.setMealcount(rs.getInt("mealcount"));
				shoppingcartVO.setAddDate(rs.getTimestamp("addDate"));

			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return shoppingcartVO;
	}

}
