package com.shoppingcart.model;

import java.sql.Timestamp;

public class ShoppingCartVO implements java.io.Serializable {
private String shoppingcartid;
private String custid;
private String mealid;
private Integer mealcount;
private Timestamp addDate;

public ShoppingCartVO() {
	super();
	// TODO Auto-generated constructor stub
}
@Override
public String toString() {
	return "ShoppingCartVO [shoppingcartid=" + shoppingcartid + ", custid=" + custid + ", mealid=" + mealid
			+ ", mealcount=" + mealcount + ", addDate=" + addDate + "]";
}
public String getShoppingcartid() {
	return shoppingcartid;
}
public void setShoppingcartid(String shoppingcartid) {
	this.shoppingcartid = shoppingcartid;
}
public String getCustid() {
	return custid;
}
public void setCustid(String custid) {
	this.custid = custid;
}
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
public Timestamp getAddDate() {
	return addDate;
}
public void setAddDate(Timestamp addDate) {
	this.addDate = addDate;
}

}
