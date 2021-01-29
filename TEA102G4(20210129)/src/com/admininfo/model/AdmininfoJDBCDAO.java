package com.admininfo.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import util.Util;

public class AdmininfoJDBCDAO implements AdmininfoDAO_interface {

	private static final String INSERT_STMT = "INSERT INTO ADMININFO(MANID,MANNAME,MANPASSWORD,MANMAIL,MANREALNAME,MANTEL,MANSTATUS,MANPURVIEW) VALUES('MAN' || lpad(ADMININFO_seq.NEXTVAL, 5, '0'),?,?,?,?,?,?,?)";
	private static final String UPDATE = "UPDATE ADMININFO SET manname=?, manpassword=? ,manmail=?, manrealname=?, mantel=?,manstatus=?,manpurview=? WHERE MANID = ?";
	private static final String DELETE_ADMININFO = "DELETE FROM ADMININFO WHERE MANID = ?";
	private static final String GET_ONE_STMT = "SELECT * FROM ADMININFO where manid = ?";
	private static final String GET_ALL_STMT = "SELECT * FROM ADMININFO";
	private static final String GET_ONE_MANMAIL = "SELECT * FROM ADMININFO WHERE MANMAIL = ?";

	@Override
	public void insert(AdmininfoVO admininfoVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(Util.DRIVER);
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setString(1, admininfoVO.getManname());
			pstmt.setString(2, admininfoVO.getManpassword());
			pstmt.setString(3, admininfoVO.getManmail());
			pstmt.setString(4, admininfoVO.getManrealname());
			pstmt.setString(5, admininfoVO.getMantel());
			pstmt.setString(6, admininfoVO.getManstatus());
			pstmt.setString(7, admininfoVO.getManpurview());
			pstmt.executeUpdate();
		} catch (ClassNotFoundException cnfe) {
			throw new RuntimeException("Couldn't load database driver. " + cnfe.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public void update(AdmininfoVO admininfoVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(Util.DRIVER);
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, admininfoVO.getManname());
			pstmt.setString(2, admininfoVO.getManpassword());
			pstmt.setString(3, admininfoVO.getManmail());
			pstmt.setString(4, admininfoVO.getManrealname());
			pstmt.setString(5, admininfoVO.getMantel());
			pstmt.setString(6, admininfoVO.getManstatus());
			pstmt.setString(7, admininfoVO.getManpurview());
			pstmt.setString(8, admininfoVO.getManid());
			pstmt.executeUpdate();
		} catch (ClassNotFoundException cnfe) {
			throw new RuntimeException("Couldn't load database driver. " + cnfe.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public void delete(String manid) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(Util.DRIVER);
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(DELETE_ADMININFO);
			pstmt.setString(1, manid);
			pstmt.executeUpdate();
		} catch (ClassNotFoundException cnfe) {
			throw new RuntimeException("Couldn't load database driver. " + cnfe.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public AdmininfoVO findByPrimaryKey(String manid) {
		AdmininfoVO admininfoVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Class.forName(Util.DRIVER);
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setString(1, manid);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				admininfoVO = new AdmininfoVO();
				admininfoVO.setManid(rs.getString("manid"));
				admininfoVO.setManmail(rs.getString("manmail"));
				admininfoVO.setManname(rs.getString("manname"));
				admininfoVO.setManpassword(rs.getString("manpassword"));
				admininfoVO.setManpurview(rs.getString("manpurview"));
				admininfoVO.setManrealname(rs.getString("manrealname"));
				admininfoVO.setManstatus(rs.getString("manstatus"));
				admininfoVO.setMantel(rs.getString("mantel"));
			}
		} catch (ClassNotFoundException cnfe) {
			throw new RuntimeException("Couldn't load database driver. " + cnfe.getMessage());
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
		return admininfoVO;
	}

	@Override
	public List<AdmininfoVO> getAll() {
		List<AdmininfoVO> list = new ArrayList<AdmininfoVO>();
		AdmininfoVO admininfoVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(Util.DRIVER);
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				admininfoVO = new AdmininfoVO();
				admininfoVO.setManid(rs.getString("manid"));
				admininfoVO.setManmail(rs.getString("manmail"));
				admininfoVO.setManname(rs.getString("manname"));
				admininfoVO.setManpassword(rs.getString("manpassword"));
				admininfoVO.setManrealname(rs.getString("manrealname"));
				admininfoVO.setManstatus(rs.getString("manstatus"));
				admininfoVO.setMantel(rs.getString("mantel"));
				admininfoVO.setManpurview(rs.getString("manpurview"));
				list.add(admininfoVO);
			}
		} catch (ClassNotFoundException cnfe) {
			throw new RuntimeException("Couldn't load database driver. " + cnfe.getMessage());
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
	public AdmininfoVO findByManmail(String manmail) {
		AdmininfoVO admininfoVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(Util.DRIVER);
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(GET_ONE_MANMAIL);
			pstmt.setString(1, manmail);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				admininfoVO = new AdmininfoVO();
				admininfoVO.setManid(rs.getString("manid"));
				admininfoVO.setManname(rs.getString("manname"));
				admininfoVO.setManpassword(rs.getString("manpassword"));
				admininfoVO.setManmail(rs.getString("manmail"));
				admininfoVO.setManrealname(rs.getString("manrealname"));
				admininfoVO.setMantel(rs.getString("mantel"));
				admininfoVO.setManstatus(rs.getString("manstatus"));
				admininfoVO.setManpurview(rs.getString("manpurview"));
			}
			
		} catch (ClassNotFoundException cnfe) {
			throw new RuntimeException ("Couldn't load database driver. " + cnfe.getMessage());
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
		return admininfoVO;
	}
	
	
	public static void main(String[] args) {
		
		AdmininfoJDBCDAO dao = new AdmininfoJDBCDAO();
		
//		List<AdmininfoVO> list1= dao.getAll();
//		for(AdmininfoVO status: list1) {
//			if (status.getManstatus().equals("已停權")) {
//				System.out.println("被停權的:" + status.getManid());
//			} else if (status.getManstatus().equals("使用中")){
//				System.out.println("使用中的:" + status.getManid());
//			}
//		}
		
		AdmininfoVO admininfoVO = dao.findByManmail("admin_01@gmail.com");
		System.out.println(admininfoVO.getManid() + ",");
	}

}
