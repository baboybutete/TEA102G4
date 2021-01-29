package com.backrolemanager.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import util.Util;

public class BackRoleDAOJDBC implements BackRoleDAO_interface {
	private static final String INSERT_STMT = "INSERT INTO BACKROLEMANAGER(MANPURVIEW,PURVIEWCONTENT) VALUES(?,?)";
	private static final String UPDATE = "UPDATE BACKROLEMANAGER SET purviewcontent=? WHERE MANPURVIEW = ?";
	
	private static final String DELETE_BACKROLEVO = "DELETE FROM BACKROLEMANAGER WHERE MANPURVIEW = ?";
	
	private static final String GET_ONE_STMT = "SELECT * FROM BACKROLEMANAGER WHERE MANPURVIEW = ?";
	private static final String GET_ALL_STMT = "SELECT * FROM BACKROLEMANAGER";

	@Override
	public void insert(BackRoleVO backRoleVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(Util.DRIVER);
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setString(1, 	backRoleVO.getManpurview());
			pstmt.setString(2, 	backRoleVO.getPurviewcontent());
			
			pstmt.executeUpdate();
		} catch (ClassNotFoundException cnfe) {
			throw new RuntimeException("Couldn't load database driver. "
					+ cnfe.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	public void update(BackRoleVO backRoleVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(Util.DRIVER);
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setString(1, 	backRoleVO.getPurviewcontent());
			pstmt.setString(2, 	backRoleVO.getManpurview());
			pstmt.executeUpdate();	
		} catch (ClassNotFoundException cnfe) {
			throw new RuntimeException("Couldn't load database driver. "
					+ cnfe.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	public void delete(String manpurview) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(Util.DRIVER);
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(DELETE_BACKROLEVO);
			pstmt.setString(1, manpurview);
			pstmt.executeUpdate();
		} catch (ClassNotFoundException cnfe) {
			throw new RuntimeException("Couldn't load database driver. "
					+ cnfe.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	public BackRoleVO findByPrimaryKey(String manpurview) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BackRoleVO bean = null;
		try {
			Class.forName(Util.DRIVER);
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setString(1, manpurview);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				 bean = new BackRoleVO();
				bean.setManpurview(rs.getString("manpurview"));
				bean.setPurviewcontent(rs.getString("purviewcontent"));
			}
			
		} catch (ClassNotFoundException cnfe) {
			throw new RuntimeException("Couldn't load database driver. "
					+ cnfe.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
		return bean;
	}

	@Override
	public List<BackRoleVO> getAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<BackRoleVO> list = new ArrayList<BackRoleVO>();
		try {
			Class.forName(Util.DRIVER);
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				BackRoleVO bean = new BackRoleVO();
				bean.setManpurview(rs.getString("manpurview"));
				bean.setPurviewcontent(rs.getString("purviewcontent"));
				list.add(bean);
			}
		} catch (ClassNotFoundException cnfe) {
			throw new RuntimeException("Couldn't load database driver. "
					+ cnfe.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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

}
