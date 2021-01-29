package com.map.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MapJDBCDAO implements MapDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "TEA102G4";
	String passwd = "123456";

	private static final String GET_ALL_EMPTY_SEATS = "SELECT resinfo.resid,resinfo.resname,resinfo.resaddid,resinfo.resimg,resseat.seatdata,resinfo.lat,resinfo.lng,resinfo.contactphon,reshours.opening,reshours.closing\r\n" + 
			"FROM resinfo INNER JOIN resseat ON resinfo.resid = resseat.resid INNER JOIN reshours ON resinfo.resid = reshours.resid ORDER BY resinfo.resid";

	@Override
	public List<MapVO> getAll() {

		List<MapVO> list = new ArrayList<MapVO>();
		MapVO mapVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_EMPTY_SEATS);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				mapVO = new MapVO();
				mapVO.setResid(rs.getString("resid"));
				mapVO.setResname(rs.getString("resname"));
				mapVO.setResaddid(rs.getString("resaddid"));
				mapVO.setResimg(rs.getBytes("resimg"));
				mapVO.setLat(rs.getString("lat"));
				mapVO.setLng(rs.getString("lng"));
				mapVO.setContactphon(rs.getString("contactphon"));
				mapVO.setOpening(rs.getTimestamp("opening"));
				mapVO.setClosing(rs.getTimestamp("closing"));
				if (rs.getString("seatdata") != null) {
					mapVO.setSeatData(MapJDBCDAO.jasonGetNumber(rs.getString("seatdata")));
				} else {
					mapVO.setSeatData("");
				}
				list.add(mapVO); // Store the row in the list

			}

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

	// 處理json字串,將座位狀態字串轉換成物件後，計算空位數，並回傳
	public static String jasonGetNumber(String newSeatDataString) {
		Integer availableSeat = 0;
		try {
			// 將字串轉成陣列
			
			JSONArray jsonArray = new JSONArray(newSeatDataString);
//			System.out.println(jsonArray.toString());
			
			for (int i = 0; i < jsonArray.length(); i++) {
				// 拿取陣列中的物件
				JSONObject json_ResSeatElement = jsonArray.getJSONObject(i);
				
				
				// 當itemNumber == 4時表示這個座位沒有人使用
				String itemNumber = json_ResSeatElement.getString("itemNumber");
				
				if (itemNumber.equals("4"))
					availableSeat++;
			}
			
		} catch (JSONException e) {
			
		}

		String availableSeatString = String.valueOf(availableSeat);

		return availableSeatString;

	}
}
