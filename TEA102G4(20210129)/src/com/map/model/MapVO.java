package com.map.model;

import java.sql.*;

//在地圖上顯示餐廳的相關資訊vo
public class MapVO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	private String resid;
	private String resname;
	private String resaddid;
	private byte[] resimg;
	private String seatData;
	private String lat;
	private String lng;
	private String contactphon;
	private Timestamp opening;
	private Timestamp closing;
	
	public String getResid() {
		return resid;
	}
	public void setResid(String resid) {
		this.resid = resid;
	}
	public String getResname() {
		return resname;
	}
	public void setResname(String resname) {
		this.resname = resname;
	}
	public String getResaddid() {
		return resaddid;
	}
	public void setResaddid(String resaddid) {
		this.resaddid = resaddid;
	}
	public byte[] getResimg() {
		return resimg;
	}
	public void setResimg(byte[] resimg) {
		this.resimg = resimg;
	}
	public String getSeatData() {
		return seatData;
	}
	public void setSeatData(String seatData) {
		this.seatData = seatData;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	public String getContactphon() {
		return contactphon;
	}
	public void setContactphon(String contactphon) {
		this.contactphon = contactphon;
	}
	public Timestamp getOpening() {
		return opening;
	}
	public void setOpening(Timestamp opening) {
		this.opening = opening;
	}
	public Timestamp getClosing() {
		return closing;
	}
	public void setClosing(Timestamp cloing) {
		this.closing = cloing;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((resid == null) ? 0 : resid.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MapVO other = (MapVO) obj;
		if (resid == null) {
			if (other.resid != null)
				return false;
		} else if (!resid.equals(other.resid))
			return false;
		return true;
	}
	
	
}
