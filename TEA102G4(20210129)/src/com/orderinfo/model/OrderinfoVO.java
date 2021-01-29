package com.orderinfo.model;

import java.sql.Timestamp;

public class OrderinfoVO implements java.io.Serializable{
	private String orderid;
	private String resid;
	private String custid;
	private String subscriber;
	private String subphone;
	private Timestamp ordertime;
	private Timestamp subtime;
	private Integer subnumber;
	private String paystatus;
	private String checkin;
	private String seat;
	private String orderstatus;
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public String getResid() {
		return resid;
	}
	public void setResid(String resid) {
		this.resid = resid;
	}
	public String getCustid() {
		return custid;
	}
	public void setCustid(String custid) {
		this.custid = custid;
	}
	public String getSubscriber() {
		return subscriber;
	}
	public void setSubscriber(String subscriber) {
		this.subscriber = subscriber;
	}
	public String getSubphone() {
		return subphone;
	}
	public void setSubphone(String subphone) {
		this.subphone = subphone;
	}
	public Timestamp getOrdertime() {
		return ordertime;
	}
	public void setOrdertime(Timestamp ordertime) {
		this.ordertime = ordertime;
	}
	public Timestamp getSubtime() {
		return subtime;
	}
	public void setSubtime(Timestamp subtime) {
		this.subtime = subtime;
	}
	public Integer getSubnumber() {
		return subnumber;
	}
	public void setSubnumber(Integer subnumber) {
		this.subnumber = subnumber;
	}
	public String getPaystatus() {
		return paystatus;
	}
	public void setPaystatus(String paystatus) {
		this.paystatus = paystatus;
	}
	public String getCheckin() {
		return checkin;
	}
	public void setCheckin(String checkin) {
		this.checkin = checkin;
	}
	public String getSeat() {
		return seat;
	}
	public void setSeat(String seat) {
		this.seat = seat;
	}
	public String getOrderstatus() {
		return orderstatus;
	}
	public void setOrderstatus(String orderstatus) {
		this.orderstatus = orderstatus;
	}
	

}
