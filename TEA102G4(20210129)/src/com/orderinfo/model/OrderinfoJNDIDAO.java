//package com.orderinfo.model;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.naming.Context;
//import javax.naming.InitialContext;
//import javax.naming.NamingException;
//import javax.sql.DataSource;
//
//public class OrderinfoJNDIDAO implements OrderinfoDAO_interface {
//	private static DataSource ds = null;
//	static {
//		try {
//			Context ctx = new InitialContext();
//			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/123DB");
//		} catch (NamingException e) {
//			e.printStackTrace();
//		}
//		
//	}
//	private static final String INSERT_STMT = 
//			"INSERT INTO orderinfo(orderid,resid,custid,subscriber,subphone,ordertime,subtime,subnumber,paystatus,checkin,seat) "
//			+ "VALUES ('O'||lpad(ORDERINFO_seq.NEXTVAL,5,'0'),?,?,?,?,?,?,?,?,?,?)";
//	private static final String GET_ALL_STMT = 
//			"SELECT orderid,resid,custid,subscriber,subphone,ordertime,subtime,subnumber,paystatus,checkin,seat FROM orderinfo order by orderid";
//	private static final String GET_ONE_STMT = 
//			"SELECT orderid,resid,custid,subscriber,subphone,ordertime,subtime,subnumber,paystatus,checkin,seat FROM orderinfo WHERE orderid = ?";
//	private static final String DELETE =
//			"DELETE FROM orderinfo where orderid = ?";
//	private static final String UPDATE = 
//			"UPDATE orderinfo set resid=?, custid=?, subscriber=?, subphone=?, ordertime=?, subtime=?, subnumber=?, paystatus=?, checkin=?, seat=? where orderid=?";
//	@Override
//	public void insert(OrderinfoVO orderinfoVO) {
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		try {
//			con = ds.getConnection();
//			pstmt = con.prepareStatement(INSERT_STMT);
//			
//			pstmt.setString(1, orderinfoVO.getResid());
//			pstmt.setString(2, orderinfoVO.getCustid());
//			pstmt.setString(3, orderinfoVO.getSubscriber());
//			pstmt.setString(4, orderinfoVO.getSubphone());
//			pstmt.setTimestamp(5, orderinfoVO.getOrdertime());
//			pstmt.setTimestamp(6, orderinfoVO.getSubtime());
//			pstmt.setInt(7, orderinfoVO.getSubnumber());
//			pstmt.setString(8, orderinfoVO.getPaystatus());
//			pstmt.setString(9, orderinfoVO.getCheckin());
//			pstmt.setString(10, orderinfoVO.getSeat());
//			
//			pstmt.executeUpdate();
//			
//		} catch (SQLException se) {
//			throw new RuntimeException("A database error occured. "
//					+ se.getMessage());
//		}finally {
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
//	}
//
//	@Override
//	public void update(OrderinfoVO orderinfoVO) {
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		try {
//			con = ds.getConnection();
//			pstmt = con.prepareStatement(UPDATE);
//			
//			pstmt.setString(1, orderinfoVO.getResid());
//			pstmt.setString(2, orderinfoVO.getCustid());
//			pstmt.setString(3, orderinfoVO.getSubscriber());
//			pstmt.setString(4, orderinfoVO.getSubphone());
//			pstmt.setTimestamp(5, orderinfoVO.getOrdertime());
//			pstmt.setTimestamp(6, orderinfoVO.getSubtime());
//			pstmt.setInt(7, orderinfoVO.getSubnumber());
//			pstmt.setString(8, orderinfoVO.getPaystatus());
//			pstmt.setString(9, orderinfoVO.getCheckin());
//			pstmt.setString(10, orderinfoVO.getSeat());
//			pstmt.setString(11, orderinfoVO.getOrderid());
//			
//			pstmt.executeUpdate();
//
//		} catch (SQLException se) {
//			throw new RuntimeException("A database error occured. "
//					+ se.getMessage());
//		}finally {
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
//
//	}
//
//	@Override
//	public void delete(String orderid) {
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		try {
//			con = ds.getConnection();
//			pstmt = con.prepareStatement(DELETE);
//			
//			pstmt.setString(1, orderid);
//
//			pstmt.executeUpdate();
//			
//		} catch (SQLException se) {
//			throw new RuntimeException("A database error occured. "
//					+ se.getMessage());
//		}finally {
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
//
//	}
//
//	@Override
//	public OrderinfoVO findByPrimaryKey(String orderid) {
//		OrderinfoVO orderinfoVO = null;
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		try {
//			con = ds.getConnection();
//			pstmt = con.prepareStatement(GET_ONE_STMT);
//			pstmt.setString(1, orderid);
//			
//			rs = pstmt.executeQuery();
//			
//			while(rs.next()) {
//				orderinfoVO = new OrderinfoVO();
//				orderinfoVO.setOrderid(rs.getString("orderid"));
//				orderinfoVO.setResid(rs.getString("resid"));
//				orderinfoVO.setCustid(rs.getString("custid"));
//				orderinfoVO.setSubscriber(rs.getString("subscriber"));
//				orderinfoVO.setSubphone(rs.getString("subphone"));
//				orderinfoVO.setOrdertime(rs.getTimestamp("ordertime"));
//				orderinfoVO.setSubtime(rs.getTimestamp("subtime"));
//				orderinfoVO.setSubnumber(rs.getInt("subnumber"));
//				orderinfoVO.setPaystatus(rs.getString("paystatus"));
//				orderinfoVO.setCheckin(rs.getString("checkin"));
//				orderinfoVO.setSeat(rs.getString("seat"));
//			}
//
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
//		return orderinfoVO;
//	}
//
//	@Override
//	public List<OrderinfoVO> getAll() {
//		List<OrderinfoVO> list = new ArrayList<OrderinfoVO>();
//		OrderinfoVO orderinfoVO = null;
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		try {
//			con = ds.getConnection();
//			pstmt = con.prepareStatement(GET_ALL_STMT);
//			rs = pstmt.executeQuery();
//			
//			while(rs.next()) {
//				orderinfoVO = new OrderinfoVO();
//				orderinfoVO.setOrderid(rs.getString("orderid"));
//				orderinfoVO.setResid(rs.getString("resid"));
//				orderinfoVO.setCustid(rs.getString("custid"));
//				orderinfoVO.setSubscriber(rs.getString("subscriber"));
//				orderinfoVO.setSubphone(rs.getString("subphone"));
//				orderinfoVO.setOrdertime(rs.getTimestamp("ordertime"));
//				orderinfoVO.setSubtime(rs.getTimestamp("subtime"));
//				orderinfoVO.setSubnumber(rs.getInt("subnumber"));
//				orderinfoVO.setPaystatus(rs.getString("paystatus"));
//				orderinfoVO.setCheckin(rs.getString("checkin"));
//				orderinfoVO.setSeat(rs.getString("seat"));
//				list.add(orderinfoVO);
//			}
//			
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
//
//}
