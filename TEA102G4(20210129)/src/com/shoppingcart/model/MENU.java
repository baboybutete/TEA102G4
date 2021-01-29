package com.shoppingcart.model;

import java.sql.Timestamp;
import java.util.Arrays;

public class MENU implements java.io.Serializable{
	private String name;
	private String mealid;
	private Integer mealcount;
	private Double price;
	private byte[] mealimg; 
	private String resid;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}

	public byte[] getMealimg() {
		return mealimg;
	}
	public void setMealimg(byte[] mealimg) {
		this.mealimg = mealimg;
	}
	
	@Override
	public String toString() {
		return "MENU [name=" + name + ", mealid=" + mealid + ", mealcount=" + mealcount + ", price=" + price
				+ ", mealimg=" + Arrays.toString(mealimg) + "]";
	}
	

	public String getResid() {
		return resid;
	}
	public void setResid(String resid) {
		this.resid = resid;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mealid == null) ? 0 : mealid.hashCode());
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
		MENU other = (MENU) obj;
		if (mealid == null) {
			if (other.mealid != null)
				return false;
		} else if (!mealid.equals(other.mealid))
			return false;
		return true;
	}
}
