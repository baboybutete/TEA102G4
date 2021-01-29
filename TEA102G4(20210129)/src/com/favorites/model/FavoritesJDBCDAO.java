package com.favorites.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class FavoritesJDBCDAO implements FavoritesDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "TEA102G4";
	String passwd = "123456";
	private static final String INSERT_STMT = "INSERT INTO FAVORITES (FAVORITESID,CUSTID,RESID,FAVORITESTATUS,ADDDATE)VALUES('FAV' || lpad(favorites_seq.NEXTVAL, 5, '0'),?,?,?,?)";
	private static final String GET_ALL_STMT = "SELECT FAVORITESID,CUSTID,RESID,FAVORITESTATUS,ADDDATE FROM FAVORITES order by FAVORITESID";
	private static final String GET_ONE_STMT = "SELECT FAVORITESID,CUSTID,RESID,FAVORITESTATUS,ADDDATE FROM FAVORITES where FAVORITESID = ?";
	private static final String DELETE = "DELETE FROM favorites where favoritesid = ?";
	private static final String UPDATE = "UPDATE FAVORITES set CUSTID=?, RESID=?, FAVORITESTATUS=?, ADDDATE=? where FAVORITESID = ?";
	private static final String GET_ONE_accont = "SELECT * FROM FAVORITES where CUSTID = ?";

	@Override
	public void insert(FavoritesVO favoritesVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, favoritesVO.getCustid());
			pstmt.setString(2, favoritesVO.getResid());
			pstmt.setString(3, favoritesVO.getFavoritestatus());
			pstmt.setTimestamp(4, favoritesVO.getAddDate());

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
	public void update(FavoritesVO favoritesVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, favoritesVO.getCustid());
			pstmt.setString(2, favoritesVO.getResid());
			pstmt.setString(3, favoritesVO.getFavoritestatus());
			pstmt.setTimestamp(4, favoritesVO.getAddDate());
			pstmt.setString(5, favoritesVO.getFavoritesid());

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
	public void delete(String favoritesid) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, favoritesid);

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
	public FavoritesVO findByPrimaryKey(String favoritesid) {
		FavoritesVO favoritesVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, favoritesid);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				favoritesVO = new FavoritesVO();
				favoritesVO.setFavoritesid(rs.getString("favoritesid"));
				favoritesVO.setCustid(rs.getString("custid"));
				favoritesVO.setResid(rs.getString("resid"));
				favoritesVO.setFavoritestatus(rs.getString("favoritestatus"));
				favoritesVO.setAddDate(rs.getTimestamp("addDate"));

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
		return favoritesVO;
	}

	@Override
	public List<FavoritesVO> getAll() {
		List<FavoritesVO> list = new ArrayList<FavoritesVO>();
		FavoritesVO favoritesVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				favoritesVO = new FavoritesVO();
				favoritesVO.setFavoritesid(rs.getString("favoritesid"));
				favoritesVO.setCustid(rs.getString("custid"));
				favoritesVO.setResid(rs.getString("resid"));
				favoritesVO.setFavoritestatus(rs.getString("favoritestatus"));
				favoritesVO.setAddDate(rs.getTimestamp("addDate"));
				list.add(favoritesVO); // Store the row in the list
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
	public List<FavoritesVO> findByFavoritesid(String custid) {
		List<FavoritesVO> list = new ArrayList<FavoritesVO>();
		FavoritesVO favoritesVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_accont);

			pstmt.setString(1, custid);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				favoritesVO = new FavoritesVO();
				favoritesVO.setFavoritesid(rs.getString("favoritesid"));
				favoritesVO.setCustid(rs.getString("custid"));
				favoritesVO.setResid(rs.getString("resid"));
				favoritesVO.setFavoritestatus(rs.getString("favoritestatus"));
				favoritesVO.setAddDate(rs.getTimestamp("addDate"));
				list.add(favoritesVO);

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

		FavoritesJDBCDAO dao = new FavoritesJDBCDAO();

//		// 新增
		FavoritesVO favoritesVO = new FavoritesVO();
		favoritesVO.setCustid("C00001");
		favoritesVO.setResid("R00005");
		favoritesVO.setFavoritestatus("已收藏");
		favoritesVO.setAddDate(new java.sql.Timestamp(new java.util.Date().getTime()));
		dao.insert(favoritesVO);

//		// 修改
//		FavoritesVO favoritesVO1 = new FavoritesVO();
//		favoritesVO1.setFavoritesid("FAV00005");
//		favoritesVO1.setCustid("C00002");
//		favoritesVO1.setResid("R00002");
//		favoritesVO1.setFavoritestatus("以收藏");
//		favoritesVO1.setAddDate(new java.sql.Timestamp(new java.util.Date().getTime()));
//		dao.update(favoritesVO1);
//
//		// 刪除
//		dao.delete("FAV00001");
//
////		// 查詢
//		FavoritesVO favoritesVO3 = dao.findByPrimaryKey("FAV00005");
//		System.out.print(favoritesVO3.getFavoritesid() + ",");
//		System.out.print(favoritesVO3.getCustid() + ",");
//		System.out.print(favoritesVO3.getResid() + ",");
//		System.out.print(favoritesVO3.getFavoritestatus() + ",");
//		System.out.print(favoritesVO3.getAddDate());
//		System.out.println("---------------------");

		List<FavoritesVO> li = dao.findByFavoritesid("C00001");
		System.out.println(li+",");
		

	}

	

}
