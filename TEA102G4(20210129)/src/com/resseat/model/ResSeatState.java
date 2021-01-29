package com.resseat.model;

import java.util.List;

public class ResSeatState {
	String resid;
	String time;
	List<ResSeatElement> seatData;
	String custid;
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
	public List<ResSeatElement> getSeatData() {
		return seatData;
	}
	public void setSeatData(List<ResSeatElement> seatData) {
		this.seatData = seatData;
	}
	public String getCustid() {
		return custid;
	}
	public void setCustid(String custid) {
		this.custid = custid;
	}
	
	

}
