package com.favorites.model;
import java.sql.Timestamp;

public class FavoritesVO implements java.io.Serializable {
	private String favoritesid;
	private String custid;
	private String resid;
	private String favoritestatus;
	private Timestamp addDate;
	
	
	public FavoritesVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "FavoritesVO [favoritesid=" + favoritesid + ", custid=" + custid + ", resid=" + resid
				+ ", favoritestatus=" + favoritestatus + ", addDate=" + addDate + "]";
	}
	public String getFavoritesid() {
		return favoritesid;
	}
	public void setFavoritesid(String favoritesid) {
		this.favoritesid = favoritesid;
	}
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
	public String getFavoritestatus() {
		return favoritestatus;
	}
	public void setFavoritestatus(String favoritestatus) {
		this.favoritestatus = favoritestatus;
	}
	public Timestamp getAddDate() {
		return addDate;
	}
	public void setAddDate(Timestamp addDate) {
		this.addDate = addDate;
	}

}
