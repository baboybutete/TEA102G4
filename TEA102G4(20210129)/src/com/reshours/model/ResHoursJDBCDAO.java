package com.reshours.model;

import java.sql.*;
import java.util.*;

public class ResHoursJDBCDAO implements ResHoursDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "TEA102G4";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO reshours (reshourid,resid,opening,closing) VALUES ('H' || lpad(RESHOURS_seq.NEXTVAL, 5, '0'), ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT reshourid,resid,opening,closing FROM reshours order by reshourid";
	private static final String GET_ALL_RES = "SELECT * FROM reshours where resid = ?";
	private static final String GET_ONE_STMT = "SELECT reshourid,resid,opening,closing FROM reshours where reshourid = ?";
	private static final String DELETE = "DELETE FROM reshours where reshourid = ?";
	private static final String UPDATE = "UPDATE reshours set resid=?, opening=?, closing=? where reshourid = ?";

	@Override
	public void insert(ResHoursVO reshoursvo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setString(1, reshoursvo.getResid());
			pstmt.setTimestamp(2, reshoursvo.getOpening());
			pstmt.setTimestamp(3, reshoursvo.getClosing());

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
	public void update(ResHoursVO reshoursvo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, reshoursvo.getResid());
			pstmt.setTimestamp(2, reshoursvo.getOpening());
			pstmt.setTimestamp(3, reshoursvo.getClosing());
			pstmt.setString(4, reshoursvo.getReshourid());

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
	public void delete(String reshourid) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, reshourid);

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
	public ResHoursVO findByPrimaryKey(String resid) {
		ResHoursVO resHoursVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, resid);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				resHoursVO = new ResHoursVO();
				resHoursVO.setResid(rs.getString("resid"));
				resHoursVO.setReshourid(rs.getString("reshourid"));
				resHoursVO.setOpening(rs.getTimestamp("opening"));
				resHoursVO.setClosing(rs.getTimestamp("closing"));
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
		return resHoursVO;
	}

	@Override
	public List<ResHoursVO> getAll() {
		List<ResHoursVO> list = new ArrayList<ResHoursVO>();
		ResHoursVO resHoursVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				resHoursVO = new ResHoursVO();
				resHoursVO.setReshourid(rs.getString("reshourid"));
				resHoursVO.setResid(rs.getString("resid"));
				resHoursVO.setOpening(rs.getTimestamp("opening"));
				resHoursVO.setClosing(rs.getTimestamp("closing"));
				list.add(resHoursVO); // Store the row in the list
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
	public List<ResHoursVO> getAllRes(String resid) {
		List<ResHoursVO> list = new ArrayList<ResHoursVO>();
		ResHoursVO resHoursVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_RES);
			
			pstmt.setString(1, resid);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				resHoursVO = new ResHoursVO();
				resHoursVO.setResid(rs.getString("resid"));
				resHoursVO.setReshourid(rs.getString("reshourid"));
				resHoursVO.setOpening(rs.getTimestamp("opening"));
				resHoursVO.setClosing(rs.getTimestamp("closing"));
				list.add(resHoursVO); // Store the row in the list
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
		ResHoursJDBCDAO dao = new ResHoursJDBCDAO();

		// 新增
//		ResHoursVO resHoursVO1 = new ResHoursVO();
//		resHoursVO1.setResid("R00001");
//		resHoursVO1.setOpening(java.sql.Timestamp.valueOf("2020-12-02 09:00:00"));
//		resHoursVO1.setClosing(java.sql.Timestamp.valueOf("2020-12-02 21:00:00"));
//		dao.insert(resHoursVO1);
//		System.out.println("新增成功");

		// 修改
//		ResHoursVO resHoursVO2 = new ResHoursVO();
//		resHoursVO2.setReshourid("H00008");
//		resHoursVO2.setResid("R00002");
//		resHoursVO2.setOpening(java.sql.Timestamp.valueOf("2020-12-22 09:00:00"));
//		resHoursVO2.setClosing(java.sql.Timestamp.valueOf("2020-12-22 21:00:00"));
//		dao.update(resHoursVO2);
//		System.out.println("修改成功");

		// 刪除
//		dao.delete("H00008");
//		System.out.println("刪除成功");

		// 查詢
//		ResHoursVO resHoursVO3 = dao.findByPrimaryKey("H00001");
//		System.out.print(resHoursVO3.getReshourid() + ",");
//		System.out.print(resHoursVO3.getResid() + ",");
//		System.out.print(resHoursVO3.getOpening() + ",");
//		System.out.print(resHoursVO3.getClosing());

		// 查詢全部
//		List<ResHoursVO> list = dao.getAll();
//		for (ResHoursVO aReshour : list) {
//			System.out.print(aReshour.getReshourid() + ",");
//			System.out.print(aReshour.getResid() + ",");
//			System.out.print(aReshour.getOpening() + ",");
//			System.out.print(aReshour.getClosing());
//			System.out.println();
//		}
		
		List<ResHoursVO> list = dao.getAllRes("R00002");
		for (ResHoursVO aReshour : list) {
			System.out.print(aReshour.getResid() + ",");
			System.out.print(aReshour.getReshourid() + ",");
			System.out.print(aReshour.getOpening() + ",");
			System.out.print(aReshour.getClosing());
			System.out.println();
		}
	}
}
