package com.admininfo.model;

//管理員資料表
public class AdmininfoVO implements java.io.Serializable{
	private String manid; //管理員編號
	private String manname; //管理員帳號
	private String manpassword; //管理員密碼
	private String manmail; //管理員信箱
	private String manrealname; //管理員姓名
	private String mantel; //管理員電話
	private String manstatus; //管理員狀態
	private String manpurview; //管理員權限
	
	public AdmininfoVO(){}
	
	public AdmininfoVO(String manid, String manname, String manpassword, String manmail, String manrealname,
			String mantel, String manstatus, String manpurview) {
		super();
		this.manid = manid;
		this.manname = manname;
		this.manpassword = manpassword;
		this.manmail = manmail;
		this.manrealname = manrealname;
		this.mantel = mantel;
		this.manstatus = manstatus;
		this.manpurview = manpurview;
	}

	@Override
	public String toString() {
		return "AdmininfoVO [manid=" + manid + ", manname=" + manname + ", manpassword=" + manpassword + ", manmail="
				+ manmail + ", manrealname=" + manrealname + ", mantel=" + mantel + ", manstatus=" + manstatus
				+ ", manpurview=" + manpurview + "]";
	}

	public String getManid() {
		return manid;
	}
	public void setManid(String manid) {
		this.manid = manid;
	}
	public String getManname() {
		return manname;
	}
	public void setManname(String manname) {
		this.manname = manname;
	}
	public String getManpassword() {
		return manpassword;
	}
	public void setManpassword(String manpassword) {
		this.manpassword = manpassword;
	}
	public String getManmail() {
		return manmail;
	}
	public void setManmail(String manmail) {
		this.manmail = manmail;
	}
	public String getManrealname() {
		return manrealname;
	}
	public void setManrealname(String manrealname) {
		this.manrealname = manrealname;
	}
	public String getMantel() {
		return mantel;
	}
	public void setMantel(String mantel) {
		this.mantel = mantel;
	}
	public String getManstatus() {
		return manstatus;
	}
	public void setManstatus(String manstatus) {
		this.manstatus = manstatus;
	}
	public String getManpurview() {
		return manpurview;
	}
	public void setManpurview(String manpurview) {
		this.manpurview = manpurview;
	}
}
