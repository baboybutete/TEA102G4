package com.orderinfo.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.orderdet.model.OrderdetJDBCDAO;
import com.orderdet.model.OrderdetVO;

public class OrderinfoJDBCDAO implements OrderinfoDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "TEA102G4";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO orderinfo(orderid,resid,custid,subscriber,subphone,ordertime,subtime,subnumber,paystatus,checkin,seat,orderstatus) "
			+ "VALUES ('O'||lpad(ORDERINFO_seq.NEXTVAL,5,'0'),?,?,?,?,?,?,?,?,?,?,?)";
	private static final String GET_ALL_STMT = "SELECT orderid,resid,custid,subscriber,subphone,ordertime,subtime,subnumber,paystatus,checkin,seat,orderstatus FROM orderinfo order by orderid";
	private static final String GET_ONE_STMT = "SELECT orderid,resid,custid,subscriber,subphone,ordertime,subtime,subnumber,paystatus,checkin,seat,orderstatus FROM orderinfo WHERE orderid = ?";
	private static final String GET_ALL_ONERESID_STMT = "SELECT orderid,resid,custid,subscriber,subphone,ordertime,subtime,subnumber,paystatus,checkin,seat,orderstatus FROM orderinfo WHERE resid = ?";
	private static final String GET_ALL_ONECUSTID_STMT = "SELECT orderid,resid,custid,subscriber,subphone,ordertime,subtime,subnumber,paystatus,checkin,seat,orderstatus FROM orderinfo WHERE custid = ?";
	private static final String DELETE = "DELETE FROM orderinfo where orderid = ?";
	private static final String DELETE_DETAIL = "DELETE FROM orderdet where orderid = ?";
	private static final String UPDATE = "UPDATE orderinfo set resid=?, custid=?, subscriber=?, subphone=?, ordertime=?, subtime=?, subnumber=?, paystatus=?, checkin=?, seat=?, orderstatus=? where orderid=?";
	private static final String GET_ONE_CUSTID = "SELECT * FROM orderinfo where CUSTID = ?";
	
	@Override
	public void insert(OrderinfoVO orderinfoVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, orderinfoVO.getResid());
			pstmt.setString(2, orderinfoVO.getCustid());
			pstmt.setString(3, orderinfoVO.getSubscriber());
			pstmt.setString(4, orderinfoVO.getSubphone());
			pstmt.setTimestamp(5, orderinfoVO.getOrdertime());
			pstmt.setTimestamp(6, orderinfoVO.getSubtime());
			pstmt.setInt(7, orderinfoVO.getSubnumber());
			pstmt.setString(8, orderinfoVO.getPaystatus());
			pstmt.setString(9, orderinfoVO.getCheckin());
			pstmt.setString(10, orderinfoVO.getSeat());
			pstmt.setString(11, orderinfoVO.getOrderstatus());
			pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException se) {
			se.printStackTrace();
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public void update(OrderinfoVO orderinfoVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, orderinfoVO.getResid());
			pstmt.setString(2, orderinfoVO.getCustid());
			pstmt.setString(3, orderinfoVO.getSubscriber());
			pstmt.setString(4, orderinfoVO.getSubphone());
			pstmt.setTimestamp(5, orderinfoVO.getOrdertime());
			pstmt.setTimestamp(6, orderinfoVO.getSubtime());
			pstmt.setInt(7, orderinfoVO.getSubnumber());
			pstmt.setString(8, orderinfoVO.getPaystatus());
			pstmt.setString(9, orderinfoVO.getCheckin());
			pstmt.setString(10, orderinfoVO.getSeat());
			pstmt.setString(11, orderinfoVO.getOrderstatus());
			pstmt.setString(12, orderinfoVO.getOrderid());

			pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public void delete(String orderid) {
		int updateCount_Orderdets = 0;

		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			// 1●設定於 pstm.executeUpdate()之前
			con.setAutoCommit(false);
			// 先刪除訂單詳細
			pstmt = con.prepareStatement(DELETE_DETAIL);
			pstmt.setString(1, orderid);
			updateCount_Orderdets = pstmt.executeUpdate();
			// 再刪除訂單資訊
			pstmt = con.prepareStatement(DELETE);
			pstmt.setString(1, orderid);
			pstmt.executeUpdate();

			con.commit();
			con.setAutoCommit(true);
			System.out.println("刪除訂單編號" + orderid + "時,共有餐點" + updateCount_Orderdets + "筆同時被刪除");

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException se) {
			if (con != null) {
				try {
					// 3●設定於當有exception發生時之catch區塊內
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. " + excep.getMessage());
				}
			}
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public OrderinfoVO findByPrimaryKey(String orderid) {
		OrderinfoVO orderinfoVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setString(1, orderid);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				orderinfoVO = new OrderinfoVO();
				orderinfoVO.setOrderid(rs.getString("orderid"));
				orderinfoVO.setResid(rs.getString("resid"));
				orderinfoVO.setCustid(rs.getString("custid"));
				orderinfoVO.setSubscriber(rs.getString("subscriber"));
				orderinfoVO.setSubphone(rs.getString("subphone"));
				orderinfoVO.setOrdertime(rs.getTimestamp("ordertime"));
				orderinfoVO.setSubtime(rs.getTimestamp("subtime"));
				orderinfoVO.setSubnumber(rs.getInt("subnumber"));
				orderinfoVO.setPaystatus(rs.getString("paystatus"));
				orderinfoVO.setCheckin(rs.getString("checkin"));
				orderinfoVO.setSeat(rs.getString("seat"));
				orderinfoVO.setOrderstatus(rs.getString("orderstatus"));
			}

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return orderinfoVO;
	}

	@Override
	public List<OrderinfoVO> getAll() {
		List<OrderinfoVO> list = new ArrayList<OrderinfoVO>();
		OrderinfoVO orderinfoVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				orderinfoVO = new OrderinfoVO();
				orderinfoVO.setOrderid(rs.getString("orderid"));
				orderinfoVO.setResid(rs.getString("resid"));
				orderinfoVO.setCustid(rs.getString("custid"));
				orderinfoVO.setSubscriber(rs.getString("subscriber"));
				orderinfoVO.setSubphone(rs.getString("subphone"));
				orderinfoVO.setOrdertime(rs.getTimestamp("ordertime"));
				orderinfoVO.setSubtime(rs.getTimestamp("subtime"));
				orderinfoVO.setSubnumber(rs.getInt("subnumber"));
				orderinfoVO.setPaystatus(rs.getString("paystatus"));
				orderinfoVO.setCheckin(rs.getString("checkin"));
				orderinfoVO.setSeat(rs.getString("seat"));
				orderinfoVO.setOrderstatus(rs.getString("orderstatus"));
				list.add(orderinfoVO);
			}

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
			if (con != null) {
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
	public List<OrderinfoVO> getAllByOneResid(String resid) {
		List<OrderinfoVO> list = new ArrayList<OrderinfoVO>();
		OrderinfoVO orderinfoVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_ONERESID_STMT);
			pstmt.setString(1, resid);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				orderinfoVO = new OrderinfoVO();
				orderinfoVO.setOrderid(rs.getString("orderid"));
				orderinfoVO.setResid(rs.getString("resid"));
				orderinfoVO.setCustid(rs.getString("custid"));
				orderinfoVO.setSubscriber(rs.getString("subscriber"));
				orderinfoVO.setSubphone(rs.getString("subphone"));
				orderinfoVO.setOrdertime(rs.getTimestamp("ordertime"));
				orderinfoVO.setSubtime(rs.getTimestamp("subtime"));
				orderinfoVO.setSubnumber(rs.getInt("subnumber"));
				orderinfoVO.setPaystatus(rs.getString("paystatus"));
				orderinfoVO.setCheckin(rs.getString("checkin"));
				orderinfoVO.setSeat(rs.getString("seat"));
				orderinfoVO.setOrderstatus(rs.getString("orderstatus"));
				list.add(orderinfoVO);
			}

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
			if (con != null) {
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
	public void insertWithOrderdets(OrderinfoVO orderinfoVO, List<OrderdetVO> list) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);

			// 1●設定於 pstm.executeUpdate()之前
			con.setAutoCommit(false);

			// 先新增訂單
			String cols[] = { "ORDERID" };
			pstmt = con.prepareStatement(INSERT_STMT, cols);
			pstmt.setString(1, orderinfoVO.getResid());
			pstmt.setString(2, orderinfoVO.getCustid());
			pstmt.setString(3, orderinfoVO.getSubscriber());
			pstmt.setString(4, orderinfoVO.getSubphone());
			pstmt.setTimestamp(5, orderinfoVO.getOrdertime());
			pstmt.setTimestamp(6, orderinfoVO.getSubtime());
			pstmt.setInt(7, orderinfoVO.getSubnumber());
			pstmt.setString(8, orderinfoVO.getPaystatus());
			pstmt.setString(9, orderinfoVO.getCheckin());
			pstmt.setString(10, orderinfoVO.getSeat());
			pstmt.setString(11, orderinfoVO.getOrderstatus());
			pstmt.executeUpdate();

			String next_orderid = null;
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				next_orderid = rs.getString(1);
				System.out.println("自增主鍵值= " + next_orderid + "(剛新增成功的訂單編號)");
			} else {
				System.out.println("未取得自增主鍵值");
			}
			rs.close();

			OrderdetJDBCDAO dao = new OrderdetJDBCDAO();
			System.out.println("list.size()-A=" + list.size());
			for (OrderdetVO aOrderdet : list) {
				aOrderdet.setOrderid(next_orderid);
				dao.insert2(aOrderdet, con);
			}
			// 2●設定於 pstm.executeUpdate()之後
			con.commit();
			con.setAutoCommit(true);
			System.out.println("list.size()-B=" + list.size());
			System.out.println("新增訂單編號" + next_orderid + "時,共有訂單詳細" + list.size() + "筆同時被新增");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException se) {
			if (con != null) {
				try {
					// 3●設定於當有exception發生時之catch區塊內
					System.err.print("Transaction is being ");
					System.err.println("rolled back-由-orderinfo");
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. " + excep.getMessage());
				}
			}
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
	
	//給消費者端
	@Override
	public List<OrderinfoVO> findByCustid(String custid) {
		List<OrderinfoVO> list = new ArrayList<OrderinfoVO>();
		OrderinfoVO orderinfoVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_CUSTID);
			pstmt.setString(1, custid);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				orderinfoVO = new OrderinfoVO();
				orderinfoVO.setOrderid(rs.getString("orderid"));
				orderinfoVO.setResid(rs.getString("resid"));
				orderinfoVO.setCustid(rs.getString("custid"));
				orderinfoVO.setSubscriber(rs.getString("subscriber"));
				orderinfoVO.setSubphone(rs.getString("subphone"));
				orderinfoVO.setOrdertime(rs.getTimestamp("ordertime"));
				orderinfoVO.setSubtime(rs.getTimestamp("subtime"));
				orderinfoVO.setSubnumber(rs.getInt("subnumber"));
				orderinfoVO.setPaystatus(rs.getString("paystatus"));
				orderinfoVO.setCheckin(rs.getString("checkin"));
				orderinfoVO.setSeat(rs.getString("seat"));
				orderinfoVO.setOrderstatus(rs.getString("orderstatus"));
				list.add(orderinfoVO);
			}

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
			if (con != null) {
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

		OrderinfoJDBCDAO dao = new OrderinfoJDBCDAO();
//		Date date = new Date();
		Timestamp a = new Timestamp(new Date().getTime());

		System.out.println(new java.sql.Timestamp(System.currentTimeMillis()));
		// 新增
//		OrderinfoVO orderinfoVO1 = new OrderinfoVO();
//		orderinfoVO1.setResid("R00001");
//		orderinfoVO1.setCustid("C00004");
//		orderinfoVO1.setSubscriber("安安安");
//		orderinfoVO1.setSubphone("0912345678");
//		orderinfoVO1.setOrdertime(a);
//		orderinfoVO1.setSubtime(java.sql.Timestamp.valueOf("2002-01-01 20:20:20"));
//		orderinfoVO1.setSubnumber(5);
//		orderinfoVO1.setPaystatus("已付款");
//		orderinfoVO1.setCheckin("報到");
//		orderinfoVO1.setSeat("沒選位"); 
//		orderinfoVO1.setOrderstatus("成立");
//		dao.insert(orderinfoVO1);

		// 修改
//		OrderinfoVO orderinfoVO2 = new OrderinfoVO();
//		orderinfoVO2.setOrderid("O00002");
//		orderinfoVO2.setResid("R00002");
//		orderinfoVO2.setCustid("C00002");
//		orderinfoVO2.setSubscriber("人之初");
//		orderinfoVO2.setSubphone("0912345678");
//		orderinfoVO2.setOrdertime(java.sql.Timestamp.valueOf("2002-01-01 20:20:20"));
//		orderinfoVO2.setSubtime(java.sql.Timestamp.valueOf("2002-01-01 20:20:20"));
//		orderinfoVO2.setSubnumber(1);
//		orderinfoVO2.setPaystatus("未付款");
//		orderinfoVO2.setCheckin("未報到");
//		orderinfoVO2.setSeat("有選位");
//		orderinfoVO2.setOrderstatus("成立");
//		dao.update(orderinfoVO2);
//
//		// 刪除
//		dao.delete("O00001");
//
		// 查詢
//		OrderinfoVO orderinfoVO3 = dao.findByPrimaryKey("O00002");
//		System.out.print(orderinfoVO3.getOrderid() + ",");
//		System.out.print(orderinfoVO3.getResid() + ",");
//		System.out.print(orderinfoVO3.getCustid() + ",");
//		System.out.print(orderinfoVO3.getSubscriber() + ",");
//		System.out.print(orderinfoVO3.getSubphone() + ",");
//		System.out.print(orderinfoVO3.getOrdertime() + ",");
//		System.out.print(orderinfoVO3.getSubtime() + ",");
//		System.out.print(orderinfoVO3.getSubnumber() + ",");
//		System.out.print(orderinfoVO3.getPaystatus() + ",");
//		System.out.print(orderinfoVO3.getCheckin() + ",");
//		System.out.print(orderinfoVO3.getSeat() + ",");
//		System.out.println(orderinfoVO3.getOrderstatus());
//		System.out.println("---------------------");
//
//		// 查詢
//		List<OrderinfoVO> list = dao.getAll();
//		for (OrderinfoVO aOrderinfo : list) {
//			System.out.print(aOrderinfo.getOrderid() + ",");
//			System.out.print(aOrderinfo.getResid() + ",");
//			System.out.print(aOrderinfo.getCustid() + ",");
//			System.out.print(aOrderinfo.getSubscriber() + ",");
//			System.out.print(aOrderinfo.getSubphone() + ",");
//			System.out.print(aOrderinfo.getOrdertime() + ",");
//			System.out.print(aOrderinfo.getSubtime() + ",");
//			System.out.print(aOrderinfo.getSubnumber() + ",");
//			System.out.print(aOrderinfo.getPaystatus() + ",");
//			System.out.print(aOrderinfo.getCheckin() + ",");
//			System.out.print(aOrderinfo.getSeat() + ",");
//			System.out.print(aOrderinfo.getOrderstatus());
//			System.out.println();
//		}

//		List<OrderinfoVO> list = dao.getAllByOneResid("R00001");
//		for (OrderinfoVO aOrderinfo : list) {
//			System.out.print(aOrderinfo.getOrderid() + ",");
//			System.out.print(aOrderinfo.getResid() + ",");
//			System.out.print(aOrderinfo.getCustid() + ",");
//			System.out.print(aOrderinfo.getSubscriber() + ",");
//			System.out.print(aOrderinfo.getSubphone() + ",");
//			System.out.print(aOrderinfo.getOrdertime() + ",");
//			System.out.print(aOrderinfo.getSubtime() + ",");
//			System.out.print(aOrderinfo.getSubnumber() + ",");
//			System.out.print(aOrderinfo.getPaystatus() + ",");
//			System.out.print(aOrderinfo.getCheckin() + ",");
//			System.out.print(aOrderinfo.getSeat() + ",");
//			System.out.print(aOrderinfo.getOrderstatus());
//			System.out.println();
//		}
		
		OrderinfoVO orderinfoVO1 = new OrderinfoVO();
		orderinfoVO1.setResid("R00002");
		orderinfoVO1.setCustid("C00005");
		orderinfoVO1.setSubscriber("安安安2");
		orderinfoVO1.setSubphone("0912345678");
		orderinfoVO1.setOrdertime(a);
		orderinfoVO1.setSubtime(java.sql.Timestamp.valueOf("2002-01-01 20:20:20"));
		orderinfoVO1.setSubnumber(5);
		orderinfoVO1.setPaystatus("已付款");
		orderinfoVO1.setCheckin("報到");
		orderinfoVO1.setSeat("沒選位"); 
		orderinfoVO1.setOrderstatus("成立");
		
		List<OrderdetVO> testList = new ArrayList<OrderdetVO>();
//		OrderdetVO orderdetVO1 = new OrderdetVO();
//		orderdetVO1.setMealid("EAT00010");
//		orderdetVO1.setMealcount(3);
//		
//		OrderdetVO orderdetVO2 = new OrderdetVO();
//		orderdetVO2.setMealid("EAT00005");
//		orderdetVO2.setMealcount(4);
//		
//		testList.add(orderdetVO1);
//		testList.add(orderdetVO2);
		
		dao.insertWithOrderdets(orderinfoVO1, testList);
		

		
	}

}
