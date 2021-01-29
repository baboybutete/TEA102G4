package com.custinfo.model;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

//import com.favorites.model.FavoritesJDBCDAO;
//import com.favorites.model.FavoritesVO;
//import com.resempmanager.model.ResempmanagerVO;

import util.Util;

public class CustinfoJDBCDAO implements CustinfoDAO_interface {
	private static final String INSERT_STMT = "INSERT INTO CUSTINFO(CUSTID,CUSTNAME,CUSTTEL,CUSTACCOUNT,CUSTPASSWORD,CUSTSTATUS,REGISTRATIONTIME,CUSTPICTURE)VALUES('C'|| lpad(CUSTINFO_SEQ.NEXTVAL, 5, '0'),?,?,?,?,?,?,?)";
	private static final String UPDATE = "UPDATE CUSTINFO SET CUSTNAME=?, CUSTTEL=?,CUSTACCOUNT=?,CUSTPASSWORD=?,CUSTSTATUS=?,CUSTPICTURE=? WHERE CUSTID=?";
	private static final String DELETE_CUSTINFO = "DELETE FROM CUSTINFO WHERE CUSTID =?";
	private static final String GET_ONE_STMT = "SELECT * FROM CUSTINFO WHERE CUSTID =?";
	private static final String GET_ALL_STMT = "SELECT * FROM CUSTINFO  order by CUSTID";
	private static final String GET_ONE_account = "SELECT * FROM CUSTINFO WHERE custaccount =?"; 

	@Override
	public void insert(CustinfoVO custinfoVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(Util.DRIVER);
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setString(1, custinfoVO.getCustname());
			pstmt.setString(2, custinfoVO.getCusttel());
			pstmt.setString(3, custinfoVO.getCustaccount());
			pstmt.setString(4, custinfoVO.getCustpassword());
			pstmt.setString(5, custinfoVO.getCuststatus());
			pstmt.setTimestamp(6, custinfoVO.getRegistrationtime());
			pstmt.setBytes(7, custinfoVO.getCustpicture());
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
					throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public void update(CustinfoVO custinfoVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(Util.DRIVER);
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setString(1, custinfoVO.getCustname());
			pstmt.setString(2, custinfoVO.getCusttel());
			pstmt.setString(3, custinfoVO.getCustaccount());
			pstmt.setString(4, custinfoVO.getCustpassword());
			pstmt.setString(5, custinfoVO.getCuststatus());
			pstmt.setBytes(6, custinfoVO.getCustpicture());
			pstmt.setString(7, custinfoVO.getCustid());
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
					throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public void delete(String custid) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(Util.DRIVER);
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(DELETE_CUSTINFO);
			pstmt.setString(1, custid);
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
					throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public CustinfoVO findByPrimaryKey(String custid) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CustinfoVO vo = null;
		try {
			Class.forName(Util.DRIVER);
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setString(1, custid);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				vo = new CustinfoVO();
				vo.setCustid(rs.getString("custid"));
				vo.setCustaccount(rs.getString("custaccount"));
				vo.setCustname(rs.getString("custname"));
				vo.setCustpassword(rs.getString("custpassword"));
				vo.setCuststatus(rs.getString("custstatus"));
				vo.setCustpicture(rs.getBytes("custpicture"));
				vo.setCusttel(rs.getString("custtel"));
				vo.setRegistrationtime(rs.getTimestamp("registrationtime"));
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
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
		return vo;
	}

	@Override
	public List<CustinfoVO> getAll() {
		List<CustinfoVO> list = new ArrayList<CustinfoVO>();
		
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(Util.DRIVER);
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				CustinfoVO custinfoVO = new CustinfoVO();
				custinfoVO.setCustid(rs.getString("custid"));
				custinfoVO.setCustaccount(rs.getString("custaccount"));
				custinfoVO.setCustname(rs.getString("custname"));
				custinfoVO.setCustpassword(rs.getString("custpassword"));
				custinfoVO.setCuststatus(rs.getString("custstatus"));
				custinfoVO.setCustpicture(rs.getBytes("custpicture"));
				custinfoVO.setCusttel(rs.getString("custtel"));
				custinfoVO.setRegistrationtime(rs.getTimestamp("registrationtime"));
				list.add(custinfoVO);
			}
			
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	public CustinfoVO findByAccount(String custaccount) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CustinfoVO vo = null;
		try {
			Class.forName(Util.DRIVER);
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(GET_ONE_account);
			pstmt.setString(1, custaccount);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				vo = new CustinfoVO();
				vo.setCustid(rs.getString("custid"));
				vo.setCustaccount(rs.getString("custaccount"));
				vo.setCustname(rs.getString("custname"));
				vo.setCustpassword(rs.getString("custpassword"));
				vo.setCuststatus(rs.getString("custstatus"));
				vo.setCustpicture(rs.getBytes("custpicture"));
				vo.setCusttel(rs.getString("custtel"));
				vo.setRegistrationtime(rs.getTimestamp("registrationtime"));
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
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
		return vo;
	}
	
	

	public static void main(String[] args) {
		CustinfoService	service = new CustinfoService();

		CustinfoJDBCDAO dao = new CustinfoJDBCDAO();
//
//		try {
//			URL url = new URL("http://www.space-fox.com/wallpapers/celebsm/will-smith/will_smith_2.jpg");
//			InputStream in = url.openStream();
//			ByteArrayOutputStream output = new ByteArrayOutputStream();
//			byte[] b = new byte[1024];
//			int length = 0;
//			while ((length = in.read(b)) != -1) {
//				output.write(b, 0, length);
//				b = new byte[1024];
//			}
//			byte[] result = output.toByteArray();
//			CustinfoVO vo = new CustinfoVO();
//			vo.setCustaccount("name3");
//			vo.setCustname("custname22");
//			vo.setCustpassword("custpassword22");
//			vo.setCuststatus("custstatus22");
//			vo.setCustpicture(result);
//			vo.setCusttel("custtel551");
//			vo.setRegistrationtime(Timestamp.valueOf("2011-01-01 00:00:00"));
//			dao.insert(vo);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		List<CustinfoVO> list = new CustinfoJDBCDAO().getAll();
//		for (CustinfoVO vo : list) {
//			System.out.println(vo.getCustid() + ",");
//			System.out.println(vo);
//			System.out.println();
//		}
		
		CustinfoVO vo =  dao.findByAccount("如EMAIL");
		System.out.println(vo.getCustid() + ",");
		
//		try {
//			URL url = new URL("http://www.space-fox.com/wallpapers/celebsm/will-smith/will_smith_2.jpg");
//			InputStream in = url.openStream();
//			ByteArrayOutputStream output = new ByteArrayOutputStream();
//			byte[] b = new byte[1024];
//			int length = 0;
//			while((length = in.read(b)) != -1){
//				output.write(b, 0, length);
//				b = new byte[1024];
////				output.flush();
//			}
//			byte[] result = output.toByteArray();
//			service.updateCustinfo("C00006", "name15", "Custtel", "Custpassword", "停權", "Custaccount", result)
//			;
//			
//		} catch (FileNotFoundException e) {
//
//			e.printStackTrace();
//		} catch (IOException e1) {
//			e1.printStackTrace();
//		}
	}
}
