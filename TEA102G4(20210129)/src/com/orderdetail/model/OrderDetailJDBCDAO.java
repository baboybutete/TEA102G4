package com.orderdetail.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.menu.model.MenuVO;
import com.reshours.model.ResHoursService;
import com.reshours.model.ResHoursVO;

import util.Util;

public class OrderDetailJDBCDAO implements OrderDetailDAO_interface {

//	private static final String GET_ALL_DETAIL = "SELECT "	+
//			"	orderinfo.resid ,orderinfo.custid ,orderinfo.orderid ,orderinfo.SUBSCRIBER ,\r\n" + 
//			"	orderinfo.subphone ,orderinfo.subnumber ,\r\n" + 
//			"	orderinfo.seat ,orderinfo.subtime ,orderinfo.ordertime ,\r\n" + 
//			"	orderinfo.checkin ,orderinfo.paystatus ,\r\n" + 
//			"	menu.mealname, menu.mealprice, \r\n" + 
//			"	orderdet.mealcount FROM orderinfo \r\n" + 
//			"	INNER JOIN orderdet ON orderinfo.orderid = orderdet.orderid \r\n" + 
//			"	INNER JOIN menu ON orderdet.mealid = menu.mealid \r\n" + 
//			"	ORDER BY orderinfo.orderid";
	
	private static final String GETORDERINFO = "SELECT \r\n" + 
			"    orderid ,SUBSCRIBER ,\r\n" + 
			"    subphone ,subnumber ,\r\n" + 
			"	 seat ,subtime ,ordertime, \r\n" + 
			"	 checkin ,paystatus \r\n" + 
			"    from orderinfo where resid = ?\r\n" + 
			"	 ORDER BY orderid";

	private static final String GETALLDETAIL = "SELECT "	+
			"	orderinfo.resid ,orderinfo.SUBSCRIBER ,orderinfo.orderid , \r\n"  +
			"	orderinfo.subphone ,orderinfo.subnumber , \r\n"  +
			"	orderinfo.seat ,orderinfo.subtime ,orderinfo.ordertime ,\r\n" + 
			"	orderinfo.checkin ,orderinfo.paystatus ,\r\n" + 
			"	menu.mealname, menu.mealprice, \r\n" + 
			"	orderdet.mealcount FROM orderinfo  \r\n" + 
			"	INNER JOIN orderdet ON orderinfo.orderid = orderdet.orderid \r\n" + 
			"	INNER JOIN menu ON orderdet.mealid = menu.mealid \r\n" + 
			" 	where orderinfo.resid = ? \r\n" +
			"	ORDER BY orderinfo.resid"
			;
	
	private static final String GETONEDETAIL = "SELECT "	+
			"	orderinfo.SUBSCRIBER ,orderinfo.orderid , \r\n"  +
			"	orderinfo.subphone ,orderinfo.subnumber , \r\n"  +
			"	orderinfo.seat ,orderinfo.subtime ,orderinfo.ordertime ,\r\n" + 
			"	orderinfo.checkin ,orderinfo.paystatus ,\r\n" + 
			"	menu.mealname, menu.mealprice, \r\n" + 
			"	orderdet.mealcount FROM orderinfo  \r\n" + 
			"	INNER JOIN orderdet ON orderinfo.orderid = orderdet.orderid \r\n" + 
			"	INNER JOIN menu ON orderdet.mealid = menu.mealid \r\n" + 
			" 	where orderinfo.orderid = ? \r\n" +
			"	ORDER BY orderinfo.orderid";
	
	private static final String UPDATE = "UPDATE orderinfo set\r\n" + 
			"    checkin = ?\r\n" + 
			"    where orderid = ?\r\n";
	
//	@Override
//	public List<OrderDetailVO> getAll() {
//		List<OrderDetailVO> list = new ArrayList<OrderDetailVO>();
//		OrderDetailVO orderdetailVO = null;
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		try {
//			Class.forName(Util.DRIVER);
//			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
//			pstmt = con.prepareStatement(GET_ALL_DETAIL);
//			rs = pstmt.executeQuery();
//			
//			while(rs.next()) {
//				orderdetailVO = new OrderDetailVO();
//				orderdetailVO.setResid(rs.getString("resid"));
//				orderdetailVO.setCustid(rs.getString("custid"));
//				orderdetailVO.setOrderid(rs.getString("orderid"));
//				orderdetailVO.setSubscriber(rs.getString("subscriber"));
//				orderdetailVO.setSubphone(rs.getString("subphone"));
//				orderdetailVO.setSubnumber(rs.getInt("subnumber"));
//				orderdetailVO.setSeat(rs.getString("seat"));
//				orderdetailVO.setSubtime(rs.getTimestamp("subtime"));
//				orderdetailVO.setOrdertime(rs.getTimestamp("ordertime"));
//				orderdetailVO.setCheckin(rs.getString("checkin"));
//				orderdetailVO.setPaystatus(rs.getString("paystatus"));
//				orderdetailVO.setMealname(rs.getString("mealname"));
//				orderdetailVO.setMealprice(rs.getString("mealprice"));
//				orderdetailVO.setMealcount(rs.getInt("mealcount"));
//				list.add(orderdetailVO);
//			}
//			
//			
//			
//		}catch(ClassNotFoundException e) {
//			throw new RuntimeException("Couldn't load database driver. "
//					+ e.getMessage());
//		} catch (SQLException se) {
//			throw new RuntimeException("A database error occured. "
//					+ se.getMessage());
//		}finally {
//			if (rs != null) {
//				try {
//					rs.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//			if(pstmt != null) {
//				try {
//					pstmt.close();
//				} catch (SQLException e) {
//					e.printStackTrace(System.err);
//				}
//			}
//			if(con != null) {
//				try {
//					con.close();
//				} catch (SQLException e) {
//					e.printStackTrace(System.err);
//				}
//			}
//		}
//		return list;
//	}
	
	@Override
	public List<OrderDetailVO> getOrderInfo(String resid) {
		List<OrderDetailVO> list = new ArrayList<OrderDetailVO>();
		OrderDetailVO orderdetailVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Class.forName(Util.DRIVER);
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(GETORDERINFO);
			pstmt.setString(1, resid);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				orderdetailVO = new OrderDetailVO();
				orderdetailVO.setOrderid(rs.getString("orderid"));
				orderdetailVO.setSubscriber(rs.getString("subscriber"));
				orderdetailVO.setSubphone(rs.getString("subphone"));
				orderdetailVO.setSubnumber(rs.getInt("subnumber"));
				orderdetailVO.setSeat(rs.getString("seat"));
				orderdetailVO.setSubtime(rs.getTimestamp("subtime"));
				orderdetailVO.setOrdertime(rs.getTimestamp("ordertime"));
				orderdetailVO.setPaystatus(rs.getString("paystatus"));
				orderdetailVO.setCheckin(rs.getString("checkin"));
				list.add(orderdetailVO);
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
	public List<OrderDetailVO> getAllDetail(String resid) {
		List<OrderDetailVO> list = new ArrayList<OrderDetailVO>();
		OrderDetailVO orderdetailVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Class.forName(Util.DRIVER);
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(GETALLDETAIL);
			pstmt.setString(1, resid);
			rs = pstmt.executeQuery();

			while(rs.next()) {
				orderdetailVO = new OrderDetailVO();
				orderdetailVO.setResid(rs.getString("resid"));
				orderdetailVO.setOrderid(rs.getString("orderid"));
				orderdetailVO.setSubscriber(rs.getString("subscriber"));
				orderdetailVO.setSubphone(rs.getString("subphone"));
				orderdetailVO.setSubnumber(rs.getInt("subnumber"));
				orderdetailVO.setSeat(rs.getString("seat"));
				orderdetailVO.setSubtime(rs.getTimestamp("subtime"));
				orderdetailVO.setOrdertime(rs.getTimestamp("ordertime"));
				orderdetailVO.setPaystatus(rs.getString("paystatus"));
				orderdetailVO.setCheckin(rs.getString("checkin"));
				orderdetailVO.setMealname(rs.getString("mealname"));
				orderdetailVO.setMealprice(rs.getInt("mealprice"));
				orderdetailVO.setMealcount(rs.getInt("mealcount"));
				list.add(orderdetailVO);
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
	public List<OrderDetailVO> getOneDetail(String orderid) {
		List<OrderDetailVO> list = new ArrayList<OrderDetailVO>();
		OrderDetailVO orderdetailVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Class.forName(Util.DRIVER);
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(GETONEDETAIL);
			pstmt.setString(1, orderid);
			rs = pstmt.executeQuery();

			while(rs.next()) {
				orderdetailVO = new OrderDetailVO();
				orderdetailVO.setOrderid(rs.getString("orderid"));
				orderdetailVO.setSubscriber(rs.getString("subscriber"));
				orderdetailVO.setSubphone(rs.getString("subphone"));
				orderdetailVO.setSubnumber(rs.getInt("subnumber"));
				orderdetailVO.setSeat(rs.getString("seat"));
				orderdetailVO.setSubtime(rs.getTimestamp("subtime"));
				orderdetailVO.setOrdertime(rs.getTimestamp("ordertime"));
				orderdetailVO.setPaystatus(rs.getString("paystatus"));
				orderdetailVO.setCheckin(rs.getString("checkin"));
				orderdetailVO.setMealname(rs.getString("mealname"));
				orderdetailVO.setMealprice(rs.getInt("mealprice"));
				orderdetailVO.setMealcount(rs.getInt("mealcount"));
				list.add(orderdetailVO);
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
	public void update(OrderDetailVO orderDetailVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(Util.DRIVER);
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);;
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setString(1, orderDetailVO.getCheckin());   
			pstmt.setString(2, orderDetailVO.getOrderid());
			pstmt.executeUpdate();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException se) {
			se.printStackTrace();
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
	
	public static void main(String[] args) {

		OrderDetailJDBCDAO dao = new OrderDetailJDBCDAO();

		
		// 查詢全部
//				List<OrderDetailVO> list1 = dao.getOrderInfo("R00001");
//				for (OrderDetailVO aOrderinfo : list1) {
//					System.out.print(aOrderinfo.getOrderid() + ",");
//					System.out.print(aOrderinfo.getSubscriber() + ",");
//					System.out.print(aOrderinfo.getSubphone() + ",");
//					System.out.print(aOrderinfo.getSubnumber() + ",");
//					System.out.print(aOrderinfo.getSeat() + ",");
//					System.out.print(aOrderinfo.getSubtime() + ",");
//					System.out.print(aOrderinfo.getOrdertime());
//					System.out.println();
//				}
		
				// 查詢全部訂單
//				List<OrderDetailVO> list = dao.getAllDetail("R00001");
//				for (OrderDetailVO aOrderinfo : list) {
//					System.out.print(aOrderinfo.getResid() + ",");
//					System.out.print(aOrderinfo.getOrderid() + ",");
//					System.out.print(aOrderinfo.getMealname() + ",");
//					System.out.print(aOrderinfo.getMealprice() + ",");
//					System.out.print(aOrderinfo.getMealcount());
//					System.out.println();
//				}
		
				// 查詢全部訂單
//				List<OrderDetailVO> list = dao.getOneDetail("O00001");
//				for (OrderDetailVO aOrderinfo : list) {
//					System.out.print(aOrderinfo.getOrderid() + ",");
//					System.out.print(aOrderinfo.getMealname() + ",");
//					System.out.print(aOrderinfo.getMealprice() + ",");
//					System.out.print(aOrderinfo.getMealcount());
//					System.out.print(aOrderinfo.getPaystatus() + ",");
//					System.out.println();
//				}
		
		OrderDetailVO orderDetailVO = new OrderDetailVO();
		orderDetailVO.setOrderid("O00001");
		orderDetailVO.setCheckin("已報到");
		dao.update(orderDetailVO);
		System.out.println("修改成功");
			}
}
