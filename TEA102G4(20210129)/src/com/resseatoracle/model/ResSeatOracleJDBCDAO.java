package com.resseatoracle.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import util.Util;

public class ResSeatOracleJDBCDAO implements ResSeatOracleDAO_interface{
	private static final String INSERT_STMT = "INSERT INTO RESSEAT(RESID,TIME,SEATDATA) VALUES (?,?,?)" ;
	private static final String UPDATE = "UPDATE RESSEAT SET SEATDATA=? WHERE RESID=? AND TIME=?" ;
	private static final String GET_ONE_STMT = "SELECT  RESID,TIME,SEATDATA FROM RESSEAT WHERE RESID=? AND TIME=?";
	private static final String GET_ALL_STMT = "SELECT  RESID,TIME,SEATDATA FROM RESSEAT";
	
	public static void main(String[] args) {
		ResSeatOracleJDBCDAO x = new ResSeatOracleJDBCDAO();
		List<ResSeatOracleVO> list  = x.getAll();
		List<ResSeatOracleVO> newList =list.stream()
				.filter(vo->vo.getTime().equals("now"))
				.collect(Collectors.toList());
		for(ResSeatOracleVO vo :newList) {
			System.out.println(vo.getResid());
			System.out.println(vo.getSeatData());
		}
	}
	
	@Override
	public void insert(ResSeatOracleVO resSeatOracleVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(Util.DRIVER);
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setString(1,resSeatOracleVO.getResid());
			pstmt.setString(2,resSeatOracleVO.getTime());
			pstmt.setString(3, resSeatOracleVO.getSeatData());
			pstmt.executeUpdate();
		}catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	public void update(ResSeatOracleVO resSeatOracleVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(Util.DRIVER);
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setString(1, resSeatOracleVO.getSeatData());
			pstmt.setString(2,resSeatOracleVO.getResid());
			pstmt.setString(3,resSeatOracleVO.getTime());
			
			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	public void delete(String resid, String time) {
		
		
	}
	
	@Override
	public ResSeatOracleVO findByPrimaryKey(String resid,String time) {
		ResSeatOracleVO resSeatOracleVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			
			Class.forName(Util.DRIVER);
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(GET_ONE_STMT);
			

			pstmt.setString(1, resid);
			pstmt.setString(2, time);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// deptVO 也稱為 Domain objects
				resSeatOracleVO = new ResSeatOracleVO();
				resSeatOracleVO.setResid(rs.getString("resid"));
				resSeatOracleVO.setTime(rs.getString("time"));
				resSeatOracleVO.setSeatData(rs.getString("seatData"));
			}
			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
		return resSeatOracleVO;
	}
	
	public List<ResSeatOracleVO> getAll(){
		List<ResSeatOracleVO> list = new ArrayList<ResSeatOracleVO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ResSeatOracleVO resSeatOracleVO = null;
		try {
			
			Class.forName(Util.DRIVER);
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// deptVO 也稱為 Domain objects
				resSeatOracleVO = new ResSeatOracleVO();
				resSeatOracleVO.setResid(rs.getString("resid"));
				resSeatOracleVO.setTime(rs.getString("time"));
				resSeatOracleVO.setSeatData(rs.getString("seatData"));
				list.add(resSeatOracleVO);
			}
			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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

}
