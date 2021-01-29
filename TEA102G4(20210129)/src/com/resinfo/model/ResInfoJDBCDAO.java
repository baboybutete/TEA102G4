package com.resinfo.model;

import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.*;

//OK
public class ResInfoJDBCDAO implements ResInfoDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "TEA102G4";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO resinfo (resid,resname,resaddid,resimg,barrierfree,parentchild,traffic,parking,payinfo,notifcont,resemail,respassid,currentwaitingnum,subtimediff,waitdistance,contact,contactphon,corrdinate,adddate,status,lat,lng)"
			+ "VALUES ('R' || lpad(RESINFO_seq.NEXTVAL, 5, '0'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT resid,resname,resaddid,resimg,barrierfree,parentchild,traffic,parking,payinfo,notifcont,resemail,respassid,currentwaitingnum,subtimediff,waitdistance,contact,contactphon,corrdinate,adddate,status,lat,lng"
			+ " FROM resinfo order by resid";
	private static final String GET_ONE_STMT = "SELECT resid,resname,resaddid,resimg,barrierfree,parentchild,traffic,parking,payinfo,notifcont,resemail,respassid,currentwaitingnum,subtimediff,waitdistance,contact,contactphon,corrdinate,adddate,status,lat,lng"
			+ " FROM resinfo where resid = ?";
	private static final String CHECKEMAIL = "SELECT * FROM resinfo where (resemail = ? and respassid = ?)";
	private static final String DELETE = "DELETE FROM resinfo where resid = ?";
	private static final String UPDATE = "UPDATE resinfo set resname=?, resaddid=?, resimg=?, barrierfree=?, parentchild=?, traffic=?, parking=?, payinfo=?, notifcont=?, resemail=?, respassid=?, currentwaitingnum=?, subtimediff=?, waitdistance=?, contact=?, contactphon=?, corrdinate=?, adddate=?, status=? ,lat=? ,lng=? where resid = ?";

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
	public void insert(ResInfoVO resInfoVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, resInfoVO.getResname());
			pstmt.setString(2, resInfoVO.getResaddid());
//			pstmt.setBytes(3, getPictureByteArray());
			pstmt.setBytes(3, resInfoVO.getResimg());
			pstmt.setString(4, resInfoVO.getBarrierfree());
			pstmt.setString(5, resInfoVO.getParentchild());
			pstmt.setString(6, resInfoVO.getTraffic());
			pstmt.setString(7, resInfoVO.getParking());
			pstmt.setString(8, resInfoVO.getPayinfo());
			pstmt.setString(9, resInfoVO.getNotifcont());
			pstmt.setString(10, resInfoVO.getResemail());
			pstmt.setString(11, resInfoVO.getRespassid());
			pstmt.setInt(12, resInfoVO.getCurrentwaitingnum());
			pstmt.setInt(13, resInfoVO.getSubtimediff());
			pstmt.setInt(14, resInfoVO.getWaitdistance());
			pstmt.setString(15, resInfoVO.getContact());
			pstmt.setString(16, resInfoVO.getContactphon());
			pstmt.setString(17, resInfoVO.getCorrdinate());
			pstmt.setTimestamp(18, new Timestamp(System.currentTimeMillis()));
			pstmt.setString(19, resInfoVO.getStatus());
			pstmt.setString(20, resInfoVO.getLat());
			pstmt.setString(21, resInfoVO.getLng());

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
	public void update(ResInfoVO resInfoVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, resInfoVO.getResname());
			pstmt.setString(2, resInfoVO.getResaddid());
//			pstmt.setBytes(3, getPictureByteArray());
			pstmt.setBytes(3, resInfoVO.getResimg());
			pstmt.setString(4, resInfoVO.getBarrierfree());
			pstmt.setString(5, resInfoVO.getParentchild());
			pstmt.setString(6, resInfoVO.getTraffic());
			pstmt.setString(7, resInfoVO.getParking());
			pstmt.setString(8, resInfoVO.getPayinfo());
			pstmt.setString(9, resInfoVO.getNotifcont());
			pstmt.setString(10, resInfoVO.getResemail());
			pstmt.setString(11, resInfoVO.getRespassid());
			pstmt.setInt(12, resInfoVO.getCurrentwaitingnum());
			pstmt.setInt(13, resInfoVO.getSubtimediff());
			pstmt.setInt(14, resInfoVO.getWaitdistance());
			pstmt.setString(15, resInfoVO.getContact());
			pstmt.setString(16, resInfoVO.getContactphon());
			pstmt.setString(17, resInfoVO.getCorrdinate());
			pstmt.setTimestamp(18, new Timestamp(System.currentTimeMillis()));
			pstmt.setString(19, resInfoVO.getStatus());
			pstmt.setString(20, resInfoVO.getLat());
			pstmt.setString(21, resInfoVO.getLng());
			pstmt.setString(22, resInfoVO.getResid());

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
	public void delete(String resid) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, resid);

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
	public ResInfoVO findByPrimaryKey(String resid) {
		ResInfoVO resInfoVO = null;
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
				resInfoVO = new ResInfoVO();
				resInfoVO.setResid(rs.getString("resid"));
				resInfoVO.setResname(rs.getString("resname"));
				resInfoVO.setResaddid(rs.getString("resaddid"));
				resInfoVO.setResimg(rs.getBytes("resimg"));
				resInfoVO.setBarrierfree(rs.getString("barrierfree"));
				resInfoVO.setParentchild(rs.getString("parentchild"));
				resInfoVO.setTraffic(rs.getString("traffic"));
				resInfoVO.setParking(rs.getString("parking"));
				resInfoVO.setPayinfo(rs.getString("payinfo"));
				resInfoVO.setNotifcont(rs.getString("notifcont"));
				resInfoVO.setResemail(rs.getString("resemail"));
				resInfoVO.setRespassid(rs.getString("respassid"));
				resInfoVO.setCurrentwaitingnum(rs.getInt("currentwaitingnum"));
				resInfoVO.setSubtimediff(rs.getInt("subtimediff"));
				resInfoVO.setWaitdistance(rs.getInt("waitdistance"));
				resInfoVO.setContact(rs.getString("contact"));
				resInfoVO.setContactphon(rs.getString("contactphon"));
				resInfoVO.setCorrdinate(rs.getString("corrdinate"));
				resInfoVO.setAdddate(rs.getTimestamp("adddate"));
				resInfoVO.setStatus(rs.getString("status"));
				resInfoVO.setLat(rs.getString("lat"));
				resInfoVO.setLng(rs.getString("lng"));
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
		return resInfoVO;
	}

	@Override
	public List<ResInfoVO> getAll() {
		List<ResInfoVO> list = new ArrayList<ResInfoVO>();
		ResInfoVO resInfoVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				resInfoVO = new ResInfoVO();
				resInfoVO.setResid(rs.getString("resid"));
				resInfoVO.setResname(rs.getString("resname"));
				resInfoVO.setResaddid(rs.getString("resaddid"));
				resInfoVO.setResimg(rs.getBytes("resimg"));
				resInfoVO.setBarrierfree(rs.getString("barrierfree"));
				resInfoVO.setParentchild(rs.getString("parentchild"));
				resInfoVO.setTraffic(rs.getString("traffic"));
				resInfoVO.setParking(rs.getString("parking"));
				resInfoVO.setPayinfo(rs.getString("payinfo"));
				resInfoVO.setNotifcont(rs.getString("notifcont"));
				resInfoVO.setResemail(rs.getString("resemail"));
				resInfoVO.setRespassid(rs.getString("respassid"));
				resInfoVO.setCurrentwaitingnum(rs.getInt("currentwaitingnum"));
				resInfoVO.setSubtimediff(rs.getInt("subtimediff"));
				resInfoVO.setWaitdistance(rs.getInt("waitdistance"));
				resInfoVO.setContact(rs.getString("contact"));
				resInfoVO.setContactphon(rs.getString("contactphon"));
				resInfoVO.setCorrdinate(rs.getString("corrdinate"));
				resInfoVO.setAdddate(rs.getTimestamp("adddate"));
				resInfoVO.setStatus(rs.getString("status"));
				resInfoVO.setLat(rs.getString("lat"));
				resInfoVO.setLng(rs.getString("lng"));
				list.add(resInfoVO); // Store the row in the list
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
		public ResInfoVO checkEmail(String resmail, String respassid) {
			ResInfoVO resInfoVO = null;
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {
				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(CHECKEMAIL);

				pstmt.setString(1, resmail);
				pstmt.setString(2, respassid);

				rs = pstmt.executeQuery();

				while (rs.next()) {
					resInfoVO = new ResInfoVO();
					resInfoVO.setResid(rs.getString("resid"));
					resInfoVO.setResname(rs.getString("resname"));
					resInfoVO.setResaddid(rs.getString("resaddid"));
					resInfoVO.setResimg(rs.getBytes("resimg"));
					resInfoVO.setBarrierfree(rs.getString("barrierfree"));
					resInfoVO.setParentchild(rs.getString("parentchild"));
					resInfoVO.setTraffic(rs.getString("traffic"));
					resInfoVO.setParking(rs.getString("parking"));
					resInfoVO.setPayinfo(rs.getString("payinfo"));
					resInfoVO.setNotifcont(rs.getString("notifcont"));
resInfoVO.setResemail(rs.getString("resemail"));
resInfoVO.setRespassid(rs.getString("respassid"));
					resInfoVO.setCurrentwaitingnum(rs.getInt("currentwaitingnum"));
					resInfoVO.setSubtimediff(rs.getInt("subtimediff"));
					resInfoVO.setWaitdistance(rs.getInt("waitdistance"));
					resInfoVO.setContact(rs.getString("contact"));
					resInfoVO.setContactphon(rs.getString("contactphon"));
					resInfoVO.setCorrdinate(rs.getString("corrdinate"));
					resInfoVO.setAdddate(rs.getTimestamp("adddate"));
					resInfoVO.setStatus(rs.getString("status"));
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
			return resInfoVO;
		}
	
	
	
	
	

	public static void main(String[] args) {
		ResInfoJDBCDAO dao = new ResInfoJDBCDAO();
//		新增
//		ResInfoVO resInfoVO1 = new ResInfoVO();
//		resInfoVO1.setResname("來來牛排");
//		resInfoVO1.setResaddid("台北市松山區復興南路2段5號");
//		resInfoVO1.setResimg(null);
//		resInfoVO1.setBarrierfree("無");
//		resInfoVO1.setParentchild("無");
//		resInfoVO1.setTraffic("復興南路2號出口");
//		resInfoVO1.setParking("有");
//		resInfoVO1.setPayinfo("信用卡");
//		resInfoVO1.setNotifcont("您預定的時間快到了請準時入座");
//		resInfoVO1.setResemail("beef@gmail.com");
//		resInfoVO1.setRespassid("a829337");
//		resInfoVO1.setCurrentwaitingnum(10);
//		resInfoVO1.setSubtimediff(9000000);
//		resInfoVO1.setWaitdistance(5);
//		resInfoVO1.setContact("來來");
//		resInfoVO1.setContactphon("0936726333");
//		resInfoVO1.setCorrdinate("XY座標");
//		resInfoVO1.setAdddate(null);
//		resInfoVO1.setStatus("使用");
//		dao.insert(resInfoVO1);
//		System.out.println("新增成功");

		// 修改
//		ResInfoVO resInfoVO2 = new ResInfoVO();
//		resInfoVO2.setResid("R00001");
//		resInfoVO2.setResname("來來牛排");
//		resInfoVO2.setResaddid("台北市松山區復興南路2段5號");
//		resInfoVO2.setResimg(null);
//		resInfoVO2.setBarrierfree("無");
//		resInfoVO2.setParentchild("無");
//		resInfoVO2.setTraffic("復興南路2號出口");
//		resInfoVO2.setParking("有");
//		resInfoVO2.setPayinfo("信用卡");
//		resInfoVO2.setNotifcont("您預定的時間快到了請準時入座");
//		resInfoVO2.setResemail("beef@gmail.com");
//		resInfoVO2.setRespassid("a829337");
//		resInfoVO2.setCurrentwaitingnum(10);
//		resInfoVO2.setSubtimediff(9000000);
//		resInfoVO2.setWaitdistance(5);
//		resInfoVO2.setContact("來來");
//		resInfoVO2.setContactphon("0936726333");
//		resInfoVO2.setCorrdinate("XY座標");
//		resInfoVO2.setAdddate(null);
//		resInfoVO2.setStatus("使用");
//		dao.update(resInfoVO2);
//		System.out.println("修改成功");

		// 刪除
//				dao.delete("R00002");
//				System.out.println("刪除成功");

		// 查詢
//		ResInfoVO resInfoVO = dao.findByPrimaryKey("R00001");
//		System.out.print(resInfoVO.getResid()+",");
//		System.out.print(resInfoVO.getResname()+",");
//		System.out.print(resInfoVO.getResaddid()+",");
//		System.out.print(resInfoVO.getResimg()+",");
//		System.out.print(resInfoVO.getBarrierfree()+",");
//		System.out.print(resInfoVO.getParentchild()+",");
//		System.out.print(resInfoVO.getTraffic()+",");
//		System.out.print(resInfoVO.getParking()+",");
//		System.out.print(resInfoVO.getPayinfo()+",");
//		System.out.print(resInfoVO.getNotifcont()+",");
//		System.out.print(resInfoVO.getResemail()+",");
//		System.out.print(resInfoVO.getRespassid()+",");
//		System.out.print(resInfoVO.getCurrentwaitingnum()+",");
//		System.out.print(resInfoVO.getSubtimediff()+",");
//		System.out.print(resInfoVO.getWaitdistance()+",");
//		System.out.print(resInfoVO.getContact()+",");
//		System.out.print(resInfoVO.getContactphon()+",");
//		System.out.print(resInfoVO.getCorrdinate()+",");
//		System.out.print(resInfoVO.getAdddate()+",");
//		System.out.print(resInfoVO.getStatus());	

		// 查詢全部
//		List<ResInfoVO> list = dao.getAll();
//		for (ResInfoVO Res : list) {
//			System.out.print(Res.getResid() + ",");
//			System.out.print(Res.getResname() + ",");
//			System.out.print(Res.getResaddid() + ",");
//			System.out.print(Res.getResimg() + ",");
//			System.out.print(Res.getBarrierfree() + ",");
//			System.out.print(Res.getParentchild() + ",");
//			System.out.print(Res.getTraffic() + ",");
//			System.out.print(Res.getParking() + ",");
//			System.out.print(Res.getPayinfo() + ",");
//			System.out.print(Res.getNotifcont() + ",");
//			System.out.print(Res.getResemail() + ",");
//			System.out.print(Res.getRespassid() + ",");
//			System.out.print(Res.getCurrentwaitingnum() + ",");
//			System.out.print(Res.getSubtimediff() + ",");
//			System.out.print(Res.getWaitdistance() + ",");
//			System.out.print(Res.getContact() + ",");
//			System.out.print(Res.getContactphon() + ",");
//			System.out.print(Res.getCorrdinate() + ",");
//			System.out.print(Res.getAdddate() + ",");
//			System.out.print(Res.getStatus());
//			System.out.println();
//		}
		
ResInfoVO resInfoVO = dao.checkEmail("beef@gmail.com","123");
		System.out.print(resInfoVO.getResid()+",");
		System.out.print(resInfoVO.getResname()+",");
		System.out.print(resInfoVO.getResaddid()+",");
		System.out.print(resInfoVO.getResimg()+",");
		System.out.print(resInfoVO.getBarrierfree()+",");
		System.out.print(resInfoVO.getParentchild()+",");
		System.out.print(resInfoVO.getTraffic()+",");
		System.out.print(resInfoVO.getParking()+",");
		System.out.print(resInfoVO.getPayinfo()+",");
		System.out.print(resInfoVO.getNotifcont()+",");
System.out.print(resInfoVO.getResemail()+",");
System.out.print(resInfoVO.getRespassid()+",");
		System.out.print(resInfoVO.getCurrentwaitingnum()+",");
		System.out.print(resInfoVO.getSubtimediff()+",");
		System.out.print(resInfoVO.getWaitdistance()+",");
		System.out.print(resInfoVO.getContact()+",");
		System.out.print(resInfoVO.getContactphon()+",");
		System.out.print(resInfoVO.getCorrdinate()+",");
		System.out.print(resInfoVO.getAdddate()+",");
		System.out.print(resInfoVO.getStatus());	
	}
}