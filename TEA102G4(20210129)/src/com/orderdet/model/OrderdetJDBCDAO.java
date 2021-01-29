package com.orderdet.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class OrderdetJDBCDAO implements OrderdetDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "TEA102G4";
	String passwd = "123456"; 
	
	private static final String INSERT_STMT = 
			"INSERT INTO orderdet(orderid,mealid,mealcount) VALUES(?,?,?)";
	private static final String GET_ALL_STMT =
			"SELECT orderid,mealid,mealcount FROM orderdet order by orderid";
	private static final String GET_ONE_STMT =
			"SELECT orderid,mealid,mealcount FROM orderdet WHERE orderid = ? and mealid = ?";
	private static final String DELETE =
			"DELETE FROM orderdet WHERE orderid = ? and mealid = ?";
	private static final String UPDATE =
			"UPDATE orderdet set mealcount=? where orderid=? and mealid = ?";
	private static final String GET_ONE =
			"SELECT orderid,mealid,mealcount FROM orderdet where orderid=?";
	
	
	@Override
	public void insert(OrderdetVO orderdetVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url,userid,passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, orderdetVO.getOrderid());
			pstmt.setString(2, orderdetVO.getMealid());
			pstmt.setInt(3, orderdetVO.getMealcount());
			
			pstmt.executeUpdate();
			
		}catch(ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		}finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public void update(OrderdetVO orderdetVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url,userid,passwd);
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setInt(1, orderdetVO.getMealcount());
			pstmt.setString(2, orderdetVO.getOrderid());
			pstmt.setString(3, orderdetVO.getMealid());
			
			
			pstmt.executeUpdate();
			
		}catch(ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		}finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public void delete(String orderid, String mealid) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url,userid,passwd);
			pstmt = con.prepareStatement(DELETE);
			
			pstmt.setString(1, orderid);
			pstmt.setString(2, mealid);
			pstmt.executeUpdate();
			
		}catch(ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		}finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public OrderdetVO findByPrimaryKey(String orderid, String mealid) {
		OrderdetVO orderdetVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url,userid,passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setString(1, orderid);
			pstmt.setString(2, mealid);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				orderdetVO = new OrderdetVO();
				orderdetVO.setOrderid(rs.getString("orderid"));
				orderdetVO.setMealid(rs.getString("mealid"));
				orderdetVO.setMealcount(rs.getInt("mealcount"));
				
			}
		}catch(ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		}finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return orderdetVO;
	}

	@Override
	public List<OrderdetVO> getAll() {
		List<OrderdetVO> list = new ArrayList<OrderdetVO>();
		OrderdetVO orderdetVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url,userid,passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				orderdetVO = new OrderdetVO();
				orderdetVO.setOrderid(rs.getString("orderid"));
				orderdetVO.setMealid(rs.getString("mealid"));
				orderdetVO.setMealcount(rs.getInt("mealcount"));
				
				list.add(orderdetVO);
			}
			
		}catch(ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		}finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}
	public static void main(String[] args) {

		OrderdetJDBCDAO dao = new OrderdetJDBCDAO();
		// 新增
//		OrderdetVO orderdetVO1 = new OrderdetVO();
//		orderdetVO1.setOrderid("O00001"); 
//		orderdetVO1.setMealid("EAT00010");
//		orderdetVO1.setMealcount(3);
//		dao.insert(orderdetVO1);
		
		// 修改
//		OrderdetVO orderdetVO2 = new OrderdetVO();
//		orderdetVO2.setOrderid("O00002"); 
//		orderdetVO2.setMealid("EAT00003");
//		orderdetVO2.setMealcount(5);	
//		dao.update(orderdetVO2);
//
//		// 刪除
//		dao.delete("O00001","EAT00001");
//
		// 查詢
		OrderdetVO orderdetVO3 = dao.findByPrimaryKey("O00002","EAT00002");
		System.out.print(orderdetVO3.getOrderid() + ",");
		System.out.print(orderdetVO3.getMealid() + ",");
		System.out.print(orderdetVO3.getMealcount());
		System.out.println("---------------------");
////
//		// 查詢
//		List<OrderdetVO> list = dao.getAll();
//		for (OrderdetVO aOrderdet : list) {
//			System.out.print(aOrderdet.getOrderid() + ",");
//			System.out.print(aOrderdet.getMealid() + ",");
//			System.out.print(aOrderdet.getMealcount());
//			System.out.println();
//		}
		
		// 查詢
//		List<OrderdetVO> list = dao.findOne("O00001");
//		for (OrderdetVO aOrderdet : list) {
//			System.out.print(aOrderdet.getOrderid() + ",");
//			System.out.print(aOrderdet.getMealid() + ",");
//			System.out.print(aOrderdet.getMealcount());
//			System.out.println();
//		}
	}

	@Override
	public List<OrderdetVO> findOne(String orderid) {
		List<OrderdetVO> list = new ArrayList<OrderdetVO>();
		OrderdetVO orderdetVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url,userid,passwd);
			pstmt = con.prepareStatement(GET_ONE);
			pstmt.setString(1, orderid);
			rs = pstmt.executeQuery();
			
			
			while(rs.next()) {
				orderdetVO = new OrderdetVO();
				orderdetVO.setOrderid(rs.getString("orderid"));
				orderdetVO.setMealid(rs.getString("mealid"));
				orderdetVO.setMealcount(rs.getInt("mealcount"));
				
				list.add(orderdetVO);
			}
			
		}catch(ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		}finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}

	@Override
	public void insert2(OrderdetVO orderdetVO, Connection con) {
		PreparedStatement pstmt = null;
		try {

     		pstmt = con.prepareStatement(INSERT_STMT);

     		pstmt.setString(1, orderdetVO.getOrderid());
			pstmt.setString(2, orderdetVO.getMealid());
			pstmt.setInt(3, orderdetVO.getMealcount());

			pstmt.executeUpdate();

			// Handle any SQL errors
		} catch (SQLException se) {
			if (con != null) {
				try {
					// 3●設定於當有exception發生時之catch區塊內
					System.err.print("Transaction is being ");
					System.err.println("rolled back-由-orderdet");
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. "
							+ excep.getMessage());
				}
			}
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}
	}
}
