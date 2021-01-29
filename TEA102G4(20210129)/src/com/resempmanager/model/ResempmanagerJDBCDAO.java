package com.resempmanager.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


// Resempmanager 餐廳員工管理表格, 主鍵為EMPID
public class ResempmanagerJDBCDAO implements ResempmanagerDAO_interface{
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "TEA102G4";
	String passwd = "123456";
	
	private static final String INSERT_STMT = 
			"INSERT INTO RESEMPMANAGER (empid, purviewnum, resid, emppassword, empname, emptel, empstatus, adddate) VALUES ('EMP' || lpad(RESEMPMANAGER_SEQ.NEXTVAL,5,'0'),?,?,?,?,?,?,?)";
	private static final String UPDATE = 
			"UPDATE RESEMPMANAGER set purviewnum=?, resid=?, emppassword=?, empname=?, emptel=?, empstatus=?, adddate=? where empid = ?";
	private static final String DELETE = 
			"DELETE FROM RESEMPMANAGER where empid = ?";
	private static final String GET_ONE_STMT = 
			"SELECT empid, purviewnum, resid, emppassword, empname, emptel, empstatus, adddate FROM RESEMPMANAGER where empid = ?";
	private static final String GET_ALL_STMT = 
			"SELECT empid, purviewnum, resid, emppassword, empname, emptel, empstatus, adddate FROM RESEMPMANAGER order by empid";
	
	
	
	@Override
	public void insert(ResempmanagerVO resempmanagerVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, resempmanagerVO.getPurviewnum());
			pstmt.setString(2, resempmanagerVO.getResid());
			pstmt.setString(3, resempmanagerVO.getEmppassword());
			pstmt.setString(4, resempmanagerVO.getEmpname());
			pstmt.setString(5, resempmanagerVO.getEmptel());
			pstmt.setString(6, resempmanagerVO.getEmpstatus());
			pstmt.setTimestamp(7, resempmanagerVO.getAdddate());

			pstmt.executeUpdate();

		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception se) {
					se.printStackTrace();
				}
			}
		}
	}

	@Override
	public void update(ResempmanagerVO resempmanagerVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, resempmanagerVO.getPurviewnum());
			pstmt.setString(2, resempmanagerVO.getResid());
			pstmt.setString(3, resempmanagerVO.getEmppassword());
			pstmt.setString(4, resempmanagerVO.getEmpname());
			pstmt.setString(5, resempmanagerVO.getEmptel());
			pstmt.setString(6, resempmanagerVO.getEmpstatus());
			pstmt.setTimestamp(7, resempmanagerVO.getAdddate());
			pstmt.setString(8, resempmanagerVO.getEmpid());
			
			pstmt.executeUpdate();

		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception se) {
					se.printStackTrace();
				}
			}
		}
	}

	@Override
	public void delete(String empid) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, empid);

			pstmt.executeUpdate();
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception se) {
					se.printStackTrace();
				}
			}
		}		
	}

	@Override
	public ResempmanagerVO findByPrimaryKey(String empid) {
		ResempmanagerVO resempmanagerVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, empid);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				resempmanagerVO = new ResempmanagerVO();
				resempmanagerVO.setEmpid(rs.getString("empid"));
				resempmanagerVO.setPurviewnum(rs.getString("purviewnum"));
				resempmanagerVO.setResid(rs.getString("resid"));
				resempmanagerVO.setEmppassword(rs.getString("emppassword"));
				resempmanagerVO.setEmpname(rs.getString("empname"));
				resempmanagerVO.setEmptel(rs.getString("emptel"));
				resempmanagerVO.setEmpstatus(rs.getString("empstatus"));
				resempmanagerVO.setAdddate(rs.getTimestamp("adddate"));
								
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException se) {
			se.printStackTrace();
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
		return resempmanagerVO;
	}

	@Override
	public List<ResempmanagerVO> getAll() {
		List<ResempmanagerVO> list = new ArrayList<ResempmanagerVO>();
		ResempmanagerVO resempmanagerVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				resempmanagerVO = new ResempmanagerVO();
				resempmanagerVO.setEmpid(rs.getString("empid"));
				resempmanagerVO.setPurviewnum(rs.getString("purviewnum"));
				resempmanagerVO.setResid(rs.getString("resid"));
				resempmanagerVO.setEmppassword(rs.getString("emppassword"));
				resempmanagerVO.setEmpname(rs.getString("empname"));
				resempmanagerVO.setEmptel(rs.getString("emptel"));
				resempmanagerVO.setEmpstatus(rs.getString("empstatus"));
				resempmanagerVO.setAdddate(rs.getTimestamp("adddate"));
				list.add(resempmanagerVO);
			}
			
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		} catch (SQLException se) {
			se.printStackTrace();
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
		ResempmanagerJDBCDAO dao = new ResempmanagerJDBCDAO();
		
		//新增
//		ResempmanagerVO resempmanagerVO1 = new ResempmanagerVO();
//		resempmanagerVO1.setPurviewnum("1");
//		resempmanagerVO1.setResid("R00001");
//		resempmanagerVO1.setEmppassword("555555");
//		resempmanagerVO1.setEmpname("小六");
//		resempmanagerVO1.setEmptel("099999999");
//		resempmanagerVO1.setEmpstatus("在職");
//		resempmanagerVO1.setAdddate(java.sql.Timestamp.valueOf("2020-12-05 12:34:56"));
//		dao.insert(resempmanagerVO1);
		
		//修改
//		ResempmanagerVO resempmanagerVO2 = new ResempmanagerVO();
//		resempmanagerVO2.setEmpid("EMP00006");
//		resempmanagerVO2.setPurviewnum("3");
//		resempmanagerVO2.setResid("R00002");
//		resempmanagerVO2.setEmppassword("666666");
//		resempmanagerVO2.setEmpname("王八");
//		resempmanagerVO2.setEmptel("0987654321");
//		resempmanagerVO2.setEmpstatus("離職");
//		resempmanagerVO2.setAdddate(java.sql.Timestamp.valueOf("2020-12-31 23:59:59"));
//		dao.update(resempmanagerVO2);
		
		//刪除
//		dao.delete("EMP00006");
		
		//查詢單筆資料
//		ResempmanagerVO resempmanagerVO3 = dao.findByPrimaryKey("EMP00001");
//		System.out.print(resempmanagerVO3.getEmpid() + ",");
//		System.out.print(resempmanagerVO3.getPurviewnum() + ",");
//		System.out.print(resempmanagerVO3.getResid() + ",");
//		System.out.print(resempmanagerVO3.getEmppassword() + ",");
//		System.out.print(resempmanagerVO3.getEmpname() + ",");
//		System.out.print(resempmanagerVO3.getEmptel() + ",");
//		System.out.print(resempmanagerVO3.getEmpstatus() + ",");
//		System.out.print(resempmanagerVO3.getAdddate());
		
		//查詢全部資料
		List<ResempmanagerVO> list = dao.getAll();
		for (ResempmanagerVO resempmanagerVO : list) {
//			empid, purviewnum, resid, emppassword, empname, emptel, empstatus, adddate
			
			System.out.print(resempmanagerVO.getEmpid() + ",");
			System.out.print(resempmanagerVO.getPurviewnum() + ",");
			System.out.print(resempmanagerVO.getResid() + ",");
			System.out.print(resempmanagerVO.getEmppassword() + ",");
			System.out.print(resempmanagerVO.getEmpname() + ",");
			System.out.print(resempmanagerVO.getEmptel() + ",");
			System.out.print(resempmanagerVO.getEmpstatus() + ",");
			System.out.print(resempmanagerVO.getAdddate() + ",");
			System.out.println();
		}
	}
}
