package com.menu.model;

import java.sql.Timestamp;
import java.util.Arrays;

public class MenuVO {
	private String mealid;
	private String resid;
	private String classname;  
	private String mealname;
	private Integer mealprice;
	private byte[] mealimg;
	private String mealstatus;
	private Timestamp adddate;

	public String getMealid() {
		return mealid;
	}
	public void setMealid(String mealid) {
		this.mealid = mealid;
	}
	public String getResid() {
		return resid;
	}
	public void setResid(String resid) {
		this.resid = resid;
	}
	public String getClassname() {
		return classname;
	}
	public void setClassname(String classname) {
		this.classname = classname;
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
	public byte[] getMealimg() {
		return mealimg;
	}
	public void setMealimg(byte[] mealimg) {
		this.mealimg = mealimg;
	}
	public String getMealstatus() {
		return mealstatus;
	}
	public void setMealstatus(String mealstatus) {
		this.mealstatus = mealstatus;
	}
	public Timestamp getAdddate() {
		return adddate;
	}
	public void setAdddate(Timestamp adddate) {
		this.adddate = adddate;
	}
	@Override
	public String toString() {
		return "MenuVO [mealid=" + mealid + ", resid=" + resid + ", classname=" + classname + ", mealname=" + mealname
				+ ", mealprice=" + mealprice  + ", mealstatus=" + mealstatus
				+ ", adddate=" + adddate + "]";
	}
	
	

}
