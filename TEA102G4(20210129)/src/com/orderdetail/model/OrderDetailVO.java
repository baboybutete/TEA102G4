package com.orderdetail.model;

import java.sql.Timestamp;

public class OrderDetailVO implements java.io.Serializable{
	private String custid;
	private String resid;
	private String orderid;
	private String subscriber;
	private String subphone;
	private Integer subnumber;
	private String seat;
	private Timestamp subtime;
	private Timestamp ordertime;
	private String checkin;
	private String paystatus;
	private String mealname;
	private Integer mealprice;
	private Integer mealcount;
	private Integer totalprice;
	
	public String getCustid() {
		return custid;
	}
	public void setCustid(String custid) {
		this.custid = custid;
	}
	public String getResid() {
		return resid;
	}
	public void setResid(String resid) {
		this.resid = resid;
	}
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
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
	public Integer getSubnumber() {
		return subnumber;
	}
	public void setSubnumber(Integer subnumber) {
		this.subnumber = subnumber;
	}
	public String getSeat() {
		return seat;
	}
	public void setSeat(String seat) {
		this.seat = seat;
	}
	public Timestamp getSubtime() {
		return subtime;
	}
	public void setSubtime(Timestamp subtime) {
		this.subtime = subtime;
	}
	public Timestamp getOrdertime() {
		return ordertime;
	}
	public void setOrdertime(Timestamp ordertime) {
		this.ordertime = ordertime;
	}
	public String getCheckin() {
		return checkin;
	}
	public void setCheckin(String checkin) {
		this.checkin = checkin;
	}
	public String getPaystatus() {
		return paystatus;
	}
	public void setPaystatus(String paystatus) {
		this.paystatus = paystatus;
	}
	public String getMealname() {
		return mealname;
	}
	public void setMealname(String mealname) {
		this.mealname = mealname;
	}
	public Integer getMealprice() {
		return mealprice;
	}
	public void setMealprice(Integer mealprice) {
		this.mealprice = mealprice;
	}
	public Integer getMealcount() {
		return mealcount;
	}
	public void setMealcount(Integer mealcount) {
		this.mealcount = mealcount;
	}
	public Integer getTotalprice() {
		int totalprice = mealprice * mealcount;
		return totalprice;
	}
}
