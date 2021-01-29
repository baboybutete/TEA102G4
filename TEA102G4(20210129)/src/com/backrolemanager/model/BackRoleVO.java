package com.backrolemanager.model;

public class BackRoleVO implements java.io.Serializable{
	private String manpurview;
	private String purviewcontent;
	
	public BackRoleVO() {
	}
	public BackRoleVO(String manpurview, String purviewcontent) {
		super();
		this.manpurview = manpurview;
		this.purviewcontent = purviewcontent;
	}
	@Override
	public String toString() {
		return "BackRoleVO [manpurview=" + manpurview + ", purviewcontent=" + purviewcontent + "]";
	}
	public String getManpurview() {
		return manpurview;
	}
	public void setManpurview(String manpurview) {
		this.manpurview = manpurview;
	}
	public String getPurviewcontent() {
		return purviewcontent;
	}
	public void setPurviewcontent(String purviewcontent) {
		this.purviewcontent = purviewcontent;
	}
	

}
