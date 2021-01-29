package com.menu.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import com.carousel.model.CarouselJDBCDAO;
import com.carousel.model.CarouselVO;

import util.Util;

public class MenuDAOJDBC implements MenuDAO_interface{

	
	private static final String INSERT_STMT = "INSERT INTO MENU(MEALID,RESID,CLASSNAME,MEALNAME,MEALPRICE,MEALIMG,MEALSTATUS,ADDDATE)VALUES ('EAT'||lpad(MENU_SEQ.NEXTVAL,5,'0'),?,?,?,?,?,?,?)";
	private static final String UPDATE = "UPDATE MENU SET RESID=?, CLASSNAME=?,MEALNAME=?,MEALPRICE=?,MEALIMG=?,MEALSTATUS=?,ADDDATE=? WHERE MEALID=?";
	private static final String DELETE_MENU = "DELETE FROM MENU WHERE MEALID =?";
	private static final String GET_ONE_STMT = "SELECT * FROM MENU WHERE MEALID =?";
	private static final String GET_ALL_STMT = "SELECT * FROM MENU order by MEALID";
	private static final String GET_ONE_RES_MENU = "SELECT * FROM MENU where resid = ?";
	private static final String ADD_ONE_MENU = "SELECT * FROM MENU WHERE RESID =?";

//	public static void main(String[] args) {
//		MenuDAOJDBC tm = new MenuDAOJDBC();
//		List<MenuVO> list =  tm.getAll();
//		System.out.println(list);
//	}
	
	
	@Override
	public void insert(MenuVO menuVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(Util.DRIVER);
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(INSERT_STMT);
//			RESID,CLASSNAME,MEALNAME,MEALPRICE,MEALIMG,MEALSTATUS,ADDDATE
			pstmt.setString(1, menuVO.getResid());
			pstmt.setString(2, menuVO.getClassname());
			pstmt.setString(3, menuVO.getMealname());
			pstmt.setInt(4,menuVO.getMealprice());
			pstmt.setBytes(5,menuVO.getMealimg());
			pstmt.setString(6,menuVO.getMealstatus());
			pstmt.setTimestamp(7, menuVO.getAdddate());// 是否改成系統時間? 從SQL端改 或從controller insert
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
	public void update(MenuVO menuVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(Util.DRIVER);
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(UPDATE);
//			RESID,CLASSNAME,MEALNAME,MEALPRICE,MEALIMG,MEALSTATUS,ADDDATE
			pstmt.setString(1, menuVO.getResid());
			pstmt.setString(2, menuVO.getClassname());
			pstmt.setString(3, menuVO.getMealname());
			pstmt.setInt(4,menuVO.getMealprice());
			pstmt.setBytes(5,menuVO.getMealimg());
			pstmt.setString(6,menuVO.getMealstatus());
			pstmt.setTimestamp(7, menuVO.getAdddate());
			pstmt.setString(8, menuVO.getMealid());
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
	public void delete(String mealid) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(Util.DRIVER);
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(DELETE_MENU);
			pstmt.setString(1, mealid);
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
	public MenuVO findByPrimaryKey(String mealid) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MenuVO vo = null;
		try {
			Class.forName(Util.DRIVER);
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setString(1, mealid);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				vo = new MenuVO();
				vo.setMealid(rs.getString("mealid"));
				vo.setResid(rs.getString("resid"));
				vo.setClassname(rs.getString("classname"));
				vo.setMealname(rs.getString("mealname"));
				vo.setMealprice(rs.getInt("mealprice"));
				vo.setMealimg(rs.getBytes("mealimg"));
				vo.setMealstatus(rs.getString("mealstatus"));
				vo.setAdddate(rs.getTimestamp("adddate"));
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
		return vo;
	}


	@Override
	public List<MenuVO> getAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<MenuVO> list = new ArrayList<MenuVO>();
		try {
			Class.forName(Util.DRIVER);
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				MenuVO vo = new MenuVO();
				vo.setMealid(rs.getString("mealid"));
				vo.setResid(rs.getString("resid"));
				vo.setClassname(rs.getString("classname"));
				vo.setMealname(rs.getString("mealname"));
				vo.setMealprice(rs.getInt("mealprice"));
				vo.setMealimg(rs.getBytes("mealimg"));
				vo.setMealstatus(rs.getString("mealstatus"));
				vo.setAdddate(rs.getTimestamp("adddate"));
				
				list.add(vo);
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
		return list;
	}
	
	
	@Override
	public List<MenuVO> getOneResMenu(String resid) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<MenuVO> list = new ArrayList<MenuVO>();
		try {
			Class.forName(Util.DRIVER);
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(GET_ONE_RES_MENU);
			pstmt.setString(1, resid);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				MenuVO vo = new MenuVO();
				vo.setResid(rs.getString("resid"));
				vo.setMealid(rs.getString("mealid"));
				vo.setClassname(rs.getString("classname"));
				vo.setMealname(rs.getString("mealname"));
				vo.setMealprice(rs.getInt("mealprice"));
				vo.setMealimg(rs.getBytes("mealimg"));
				vo.setMealstatus(rs.getString("mealstatus"));
				vo.setAdddate(rs.getTimestamp("adddate"));
				
				list.add(vo);
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
		return list;
	}
	
	@Override
	public MenuVO addOneMenu(String resid) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MenuVO vo = null;
		try {
			Class.forName(Util.DRIVER);
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(ADD_ONE_MENU);
			pstmt.setString(1, resid);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				vo = new MenuVO();
				vo.setResid(rs.getString("resid"));
				vo.setMealid(rs.getString("mealid"));
				vo.setClassname(rs.getString("classname"));
				vo.setMealname(rs.getString("mealname"));
				vo.setMealprice(rs.getInt("mealprice"));
				vo.setMealimg(rs.getBytes("mealimg"));
				vo.setMealstatus(rs.getString("mealstatus"));
				vo.setAdddate(rs.getTimestamp("adddate"));
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
		return vo;
	}
	
//	public static void main(String[] args) {
//		MenuDAOJDBC dao = new MenuDAOJDBC();
//
//		List<MenuVO> list = dao.getOneResMenu("R00001");
//		for (MenuVO aCarousel : list) {
//			System.out.print(aCarousel.getResid() + ",");
//			System.out.print(aCarousel.getClassname() + ",");
//			System.out.print(aCarousel.getMealid() + ",");
//			System.out.print(aCarousel.getMealname() + ",");
//			System.out.print(aCarousel.getMealstatus() + ",");
//			System.out.print(aCarousel.getMealprice() + ",");
//			System.out.print(aCarousel.getMealimg() + ",");
//			System.out.print(aCarousel.getAdddate());
//			System.out.println();
//		}
//	}
}
