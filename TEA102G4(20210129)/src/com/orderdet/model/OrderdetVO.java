package com.orderdet.model;

public class OrderdetVO implements java.io.Serializable{
	private String orderid;
	private String mealid;
	private Integer mealcount;

	public String getMealid() {
		return mealid;
	}
	public void setMealid(String mealid) {
		this.mealid = mealid;
	}
	public Integer getMealcount() {
		return mealcount;
	}
	public void setMealcount(Integer mealcount) {
		this.mealcount = mealcount;
	}
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	
	

}
