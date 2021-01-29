package com.carousel.model;

import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.*;

import com.menu.model.MenuVO;

import util.Util;

//import com.reshours.model.ResHoursVO;

public class CarouselJDBCDAO implements CarouselDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "TEA102G4";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO carousel (carouselid,resid,carouselpic,adddate) VALUES ('CARO' || lpad(carousel_seq.nextval, 5, '0'), ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT carouselid,resid,carouselpic,adddate FROM carousel order by carouselid";
	private static final String GET_ONE_STMT = "SELECT carouselid,resid,carouselpic,adddate FROM carousel where carouselid = ?";
	private static final String GET_ONE_RES = "SELECT * FROM carousel where resid = ?";
	private static final String DELETE = "DELETE FROM carousel where carouselid = ?";
	private static final String UPDATE = "UPDATE carousel set resid=?, carouselpic=?, adddate=? where carouselid = ?";
	private static final String ADD_ONE_CAROUSEL = "SELECT * FROM carousel WHERE RESID =?";
	
//	public static byte[] getPictureByteArray() throws IOException {
//		URL url = new URL("http://www.space-fox.com/wallpapers/celebsm/will-smith/will_smith_2.jpg");
//		InputStream in = url.openStream();
//		ByteArrayOutputStream output = new ByteArrayOutputStream();
//		byte[] b = new byte[1024];
//		int length = 0;
//		while((length = in.read(b)) != -1){
//			output.write(b, 0, length);
//			b = new byte[1024];
//		}
//		byte[] result = output.toByteArray();
//		return result;
//	}

	@Override
	public CarouselVO addOneCarousel(String resid) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CarouselVO carouselVO = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(ADD_ONE_CAROUSEL);
			pstmt.setString(1, resid);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				carouselVO = new CarouselVO();
				carouselVO.setResid(rs.getString("resid"));
				carouselVO.setCarouselpic(rs.getBytes("carouselpic"));
				carouselVO.setAdddate(rs.getTimestamp("adddate"));
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {

			e.printStackTrace();
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
		return carouselVO;
	}
	
	@Override
	public void insert(CarouselVO carouselVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setString(1, carouselVO.getResid());
			pstmt.setBytes(2, carouselVO.getCarouselpic());
//			pstmt.setBytes(2, getPictureByteArray());
			pstmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} 
//		catch (IOException ie) {
//			System.out.println(ie);
//		} 
		finally {
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
	public void update(CarouselVO carouselVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, carouselVO.getResid());
			pstmt.setBytes(2, carouselVO.getCarouselpic());
//			pstmt.setBytes(2, getPictureByteArray());
			pstmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			pstmt.setString(4, carouselVO.getCarouselid());

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} 
//		catch (IOException ie) {
//			System.out.println(ie);
//		} 
		finally {
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
	public void delete(String carouselid) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, carouselid);

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
	public CarouselVO findByPrimaryKey(String carouselid) {
		CarouselVO carouselVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, carouselid);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				carouselVO = new CarouselVO();
				carouselVO.setCarouselid(rs.getString("carouselid"));
				carouselVO.setResid(rs.getString("resid"));
				carouselVO.setCarouselpic(rs.getBytes("carouselpic"));
				carouselVO.setAdddate(rs.getTimestamp("adddate"));
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
		return carouselVO;
	}

	@Override
	public List<CarouselVO> getAll() {
		List<CarouselVO> list = new ArrayList<CarouselVO>();
		CarouselVO carouselVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				carouselVO = new CarouselVO();
				carouselVO.setCarouselid(rs.getString("carouselid"));
				carouselVO.setResid(rs.getString("resid"));
				carouselVO.setCarouselpic(rs.getBytes("carouselpic"));
				carouselVO.setAdddate(rs.getTimestamp("adddate"));
				list.add(carouselVO); // Store the row in the list
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
	public List<CarouselVO> getOneRes(String resid) {
		List<CarouselVO> list = new ArrayList<CarouselVO>();
		CarouselVO carouselVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_RES);
			
			pstmt.setString(1, resid);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				carouselVO = new CarouselVO();
				carouselVO.setResid(rs.getString("resid"));;
				carouselVO.setCarouselid(rs.getString("carouselid"));
				carouselVO.setCarouselpic(rs.getBytes("carouselpic"));
				carouselVO.setAdddate(rs.getTimestamp("adddate"));
				list.add(carouselVO); // Store the row in the list
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
		CarouselJDBCDAO dao = new CarouselJDBCDAO();

		// 新增
//		CarouselVO carouselVO1 = new CarouselVO();
//		carouselVO1.setResid("R00001");
//		carouselVO1.setCarouselpic(null);
//		carouselVO1.setAdddate(null);
//		dao.insert(carouselVO1);
//		System.out.println("新增成功");

		// 修改
//		CarouselVO carouselVO2 = new CarouselVO();
//		carouselVO2.setCarouselid("CARO00001");
//		carouselVO2.setResid("R00001");
//		carouselVO2.setCarouselpic(null);
//		carouselVO2.setAdddate(null);
//		dao.update(carouselVO2);
//		System.out.println("修改成功");

		// 刪除
//		dao.delete("caro00003");
//		System.out.println("刪除成功");

		// 查詢
//		CarouselVO carouselVO3 = dao.findByPrimaryKey("caro00001");
//		System.out.print(carouselVO3.getCarouselid() + ",");
//		System.out.print(carouselVO3.getResid() + ",");
//		System.out.print(carouselVO3.getCarouselpic() + ",");
//		System.out.print(carouselVO3.getAdddate());

		// 查詢全部
//		List<CarouselVO> list = dao.getAll();
//		for (CarouselVO aCarousel : list) {
//			System.out.print(aCarousel.getCarouselid() + ",");
//			System.out.print(aCarousel.getResid() + ",");
//			System.out.print(aCarousel.getCarouselpic() + ",");
//			System.out.print(aCarousel.getAdddate());
//			System.out.println();
//		}
		
		List<CarouselVO> list = dao.getOneRes("R00001");
		for (CarouselVO aCarousel : list) {
			System.out.print(aCarousel.getResid() + ",");
			System.out.print(aCarousel.getCarouselid() + ",");
			System.out.print(aCarousel.getCarouselpic() + ",");
			System.out.print(aCarousel.getAdddate());
			System.out.println();
		}
	}
}
