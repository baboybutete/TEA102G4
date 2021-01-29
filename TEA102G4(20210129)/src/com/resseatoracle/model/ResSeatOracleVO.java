package com.resseatoracle.model;

public class ResSeatOracleVO implements java.io.Serializable{
	String resid;
	String time;
	String seatData;
	
	public ResSeatOracleVO() {}
	public ResSeatOracleVO(String resid,String time,String seatData) {
		this.resid = resid;
		this.time = time;
		this.seatData = seatData;
	}
	public String getResid() {
		return resid;
	}
	public void setResid(String resid) {
		this.resid = resid;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getSeatData() {
		return seatData;
	}
	public void setSeatData(String seatData) {
		this.seatData = seatData;
	}
	
	
}
