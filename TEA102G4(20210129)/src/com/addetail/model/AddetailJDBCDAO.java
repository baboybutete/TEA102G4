package com.addetail.model;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;

import util.Util;

// Addetail 廣告/輪播圖表格, 主鍵為ADID
public class AddetailJDBCDAO implements AddetailDAO_interface {
	private static final String INSERT_STMT = "INSERT INTO ADDETAIL(ADID, RESID, ADTITLE, ADCONTENT, ADIMG, ADDATE, ADTYPE, REVIEWSTATUS, ONSHELFTIME, OFFSHELFTIME) VALUES ('AD' || lpad(ADDETAIL_seq.NEXTVAL, 5, '0'),?,?,?,?,?,?,?,?,?)";
	private static final String UPDATE = "UPDATE ADDETAIL set RESID=?, ADTITLE=?, ADCONTENT=?, ADIMG=?, ADDATE=?, ADTYPE=?, REVIEWSTATUS=?, ONSHELFTIME=?, OFFSHELFTIME=? where ADID = ?";
	private static final String DELETE = "DELETE FROM ADDETAIL where ADID= ?";
	private static final String GET_ONE_STMT = "SELECT ADID, RESID, ADTITLE, ADCONTENT, ADIMG, ADDATE, ADTYPE, REVIEWSTATUS, ONSHELFTIME, OFFSHELFTIME FROM ADDETAIL where ADID = ?";
	private static final String GET_ALL_STMT = "SELECT ADID, RESID, ADTITLE, ADCONTENT, ADIMG, ADDATE, ADTYPE, REVIEWSTATUS, ONSHELFTIME, OFFSHELFTIME FROM ADDETAIL ORDER BY ADID";
	private static final String GET_ONE_RES_ADDETAIL = "SELECT * FROM ADDETAIL WHERE RESID = ?";
	private static final String ADD_ONE_ADDETAIL = "SELECT * FROM ADDETAIL WHERE RESID =?";
	
	@Override
	public void insert(AddetailVO addetailVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(Util.DRIVER);
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, addetailVO.getResid());
			pstmt.setString(2, addetailVO.getAdtitle());
			pstmt.setString(3, addetailVO.getAdcontent());
			pstmt.setBytes(4, addetailVO.getAdimg());
			pstmt.setTimestamp(5, addetailVO.getAddate());
			pstmt.setString(6, addetailVO.getAdtype());
			pstmt.setString(7, addetailVO.getReviewstatus());
			pstmt.setTimestamp(8, addetailVO.getOnshelftime());
			pstmt.setTimestamp(9, addetailVO.getOffshelftime());

			pstmt.executeUpdate();
		} catch (ClassNotFoundException cnfe) {
			throw new RuntimeException("Couldn't load database driver. " + cnfe.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("Couldn't load database driver. " + se.getMessage());
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
	public void update(AddetailVO addetailVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(Util.DRIVER);
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, addetailVO.getResid());
			pstmt.setString(2, addetailVO.getAdtitle());
			pstmt.setString(3, addetailVO.getAdcontent());
			pstmt.setBytes(4, addetailVO.getAdimg());
			pstmt.setTimestamp(5, addetailVO.getAddate());
			pstmt.setString(6, addetailVO.getAdtype());
			pstmt.setString(7, addetailVO.getReviewstatus());
			pstmt.setTimestamp(8, addetailVO.getOnshelftime());
			pstmt.setTimestamp(9, addetailVO.getOffshelftime());
			pstmt.setString(10, addetailVO.getAdid());

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
	public void delete(String adid) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(Util.DRIVER);
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, adid);

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
	public AddetailVO findByPrimaryKey(String adid) {
		AddetailVO addetailVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(Util.DRIVER);
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, adid);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				addetailVO = new AddetailVO();
				addetailVO.setAdid(rs.getString("adid"));
				addetailVO.setResid(rs.getString("resid"));
				addetailVO.setAdtitle(rs.getString("adtitle"));
				addetailVO.setAdcontent(rs.getString("adcontent"));
				addetailVO.setAdimg(rs.getBytes("adimg"));
				addetailVO.setAddate(rs.getTimestamp("addate"));
				addetailVO.setAdtype(rs.getString("adtype"));
				addetailVO.setReviewstatus(rs.getString("reviewstatus"));
				addetailVO.setOnshelftime(rs.getTimestamp("onshelftime"));
				addetailVO.setOffshelftime(rs.getTimestamp("offshelftime"));
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
		return addetailVO;
	}

	@Override
	public List<AddetailVO> getAll() {
		List<AddetailVO> list = new ArrayList<AddetailVO>();
		AddetailVO addetailVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(Util.DRIVER);
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				addetailVO = new AddetailVO();
				addetailVO.setAdid(rs.getString("adid"));
				addetailVO.setResid(rs.getString("resid"));
				addetailVO.setAdtitle(rs.getString("adtitle"));
				addetailVO.setAdcontent(rs.getString("adcontent"));
				addetailVO.setAdimg(rs.getBytes("adimg"));
				addetailVO.setAddate(rs.getTimestamp("addate"));
				addetailVO.setAdtype(rs.getString("adtype"));
				addetailVO.setReviewstatus(rs.getString("reviewstatus"));
				addetailVO.setOnshelftime(rs.getTimestamp("onshelftime"));
				addetailVO.setOffshelftime(rs.getTimestamp("offshelftime"));
				list.add(addetailVO);
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
	public List<AddetailVO> getOneResAddetail(String resid) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<AddetailVO> list = new ArrayList<AddetailVO>();
		
		try {
			Class.forName(Util.DRIVER);
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(GET_ONE_RES_ADDETAIL);

			pstmt.setString(1, resid);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				AddetailVO addetailVO = new AddetailVO();
				addetailVO.setAdid(rs.getString("adid"));
				addetailVO.setResid(rs.getString("resid"));
				addetailVO.setAdtitle(rs.getString("adtitle"));
				addetailVO.setAdcontent(rs.getString("adcontent"));
				addetailVO.setAdimg(rs.getBytes("adimg"));
				addetailVO.setAddate(rs.getTimestamp("addate"));
				addetailVO.setAdtype(rs.getString("adtype"));
				addetailVO.setReviewstatus(rs.getString("reviewstatus"));
				addetailVO.setOnshelftime(rs.getTimestamp("onshelftime"));
				addetailVO.setOffshelftime(rs.getTimestamp("offshelftime"));
				
				list.add(addetailVO);
				
			}

			// Handle any driver errors
		} catch (ClassNotFoundException cnfe) {
			throw new RuntimeException("Couldn't load database driver. " + cnfe.getMessage());
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
	public AddetailVO addOneAddetail(String resid) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		AddetailVO addetailVO = null;
		try {
			Class.forName(Util.DRIVER);
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(ADD_ONE_ADDETAIL);

			pstmt.setString(1, resid);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				addetailVO = new AddetailVO();
				addetailVO.setAdid(rs.getString("adid"));
				addetailVO.setResid(rs.getString("resid"));
				addetailVO.setAdtitle(rs.getString("adtitle"));
				addetailVO.setAdcontent(rs.getString("adcontent"));
				addetailVO.setAdimg(rs.getBytes("adimg"));
				addetailVO.setAddate(rs.getTimestamp("addate"));
				addetailVO.setAdtype(rs.getString("adtype"));
				addetailVO.setReviewstatus(rs.getString("reviewstatus"));
				addetailVO.setOnshelftime(rs.getTimestamp("onshelftime"));
				addetailVO.setOffshelftime(rs.getTimestamp("offshelftime"));
			}

			// Handle any driver errors
		} catch (ClassNotFoundException cnfe) {
			throw new RuntimeException("Couldn't load database driver. " + cnfe.getMessage());
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
		return addetailVO;
	}

	public static void main(String[] args) {
		AddetailJDBCDAO dao = new AddetailJDBCDAO();

		// 新增
//		try {
//			URL url = new URL("http://www.space-fox.com/wallpapers/celebsm/will-smith/will_smith_2.jpg");
//			InputStream in = url.openStream();
//			ByteArrayOutputStream output = new ByteArrayOutputStream();
//			byte[] b = new byte[1024];
//			int length = 0;
//			while((length = in.read(b)) != -1){
//				output.write(b, 0, length);
//				b = new byte[1024];
//			}
//			byte[] result = output.toByteArray();
//
//		AddetailVO addetailVO1 = new AddetailVO();
//		addetailVO1.setResid("R00002");
//		addetailVO1.setAdtitle("全館一折");
//		addetailVO1.setAdcontent("廣告內容XXXXX");
//		addetailVO1.setAdimg(result);
//		addetailVO1.setAddate(Timestamp.valueOf("2020-01-01 00:00:00"));
//		addetailVO1.setAdtype("廣告");
//		addetailVO1.setReviewstatus("審核中");
//		addetailVO1.setOnshelftime(Timestamp.valueOf("2011-01-01 00:00:00"));
//		addetailVO1.setOffshelftime(Timestamp.valueOf("2011-01-01 00:00:00"));
//		dao.insert(addetailVO1);
//		} catch (FileNotFoundException e) {
//						e.printStackTrace();
//					} catch (IOException e1) {
//						e1.printStackTrace();
//					}

		// 修改
//		try {
//			URL url = new URL("http://www.space-fox.com/wallpapers/celebsm/will-smith/will_smith_2.jpg");
//			InputStream in = url.openStream();
//			ByteArrayOutputStream output = new ByteArrayOutputStream();
//			byte[] b = new byte[1024];
//			int length = 0;
//			while((length = in.read(b)) != -1){
//				output.write(b, 0, length);
//				b = new byte[1024];
//			}
//			byte[] result = output.toByteArray();
//
//		AddetailVO addetailVO2 = new AddetailVO();
//		addetailVO2.setAdid("AD00002");
//		addetailVO2.setResid("R00005");
//		addetailVO2.setAdtitle("XXXXXXXXXXXXXXXXXXXXXXXXXX");
//		addetailVO2.setAdcontent("XXXXXXXXXXXXXXXXXXXXXXXXXXXX");
//		addetailVO2.setAdimg(result);
//		addetailVO2.setAddate(Timestamp.valueOf("2020-01-01 00:00:00"));
//		addetailVO2.setAdtype("XXXXXXXXXXXXXXXXXX");
//		addetailVO2.setReviewstatus("不須審核");
//		addetailVO2.setOnshelftime(Timestamp.valueOf("2020-01-01 00:00:00"));
//		addetailVO2.setOffshelftime(Timestamp.valueOf("2020-01-01 00:00:00"));
//		dao.update(addetailVO2);
//		} catch (FileNotFoundException e) {
//						e.printStackTrace();
//					} catch (IOException e1) {
//						e1.printStackTrace();
//					}

		// 刪除
//		dao.delete("AD00008");

		// 查詢單筆資料
//		AddetailVO addetailVO3 = dao.findByPrimaryKey("AD00003");
//		System.out.print(addetailVO3.getAdid() + ",");
//		System.out.print(addetailVO3.getResid() + ",");
//		System.out.print(addetailVO3.getAdtitle() + ",");
//		System.out.print(addetailVO3.getAdcontent() + ",");
//		System.out.print(addetailVO3.getAdimg() + ",");
//		System.out.print(addetailVO3.getAddate() + ",");
//		System.out.print(addetailVO3.getAdtype() + ",");
//		System.out.print(addetailVO3.getReviewstatus() + ",");
//		System.out.print(addetailVO3.getOnshelftime() + ",");
//		System.out.print(addetailVO3.getOffshelftime());

		// 查詢全部資料
//		List<AddetailVO> list = dao.getAll();
//		for (AddetailVO addetailVO : list) {
//			System.out.print(addetailVO.getAdid() + ",");
//			System.out.print(addetailVO.getResid() + ",");
//			System.out.print(addetailVO.getAdtitle() + ",");
//			System.out.print(addetailVO.getAdcontent() + ",");
//			System.out.print(addetailVO.getAdimg() + ",");
//			System.out.print(addetailVO.getAddate() + ",");
//			System.out.print(addetailVO.getAdtype() + ",");
//			System.out.print(addetailVO.getReviewstatus() + ",");
//			System.out.print(addetailVO.getOnshelftime() + ",");
//			System.out.print(addetailVO.getOffshelftime());
//			System.out.println();
//		}

	}

	
}
