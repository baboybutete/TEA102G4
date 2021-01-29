package com.resempmanager.model;

import java.sql.Timestamp;

public class ResempmanagerVO {
	private String empid;
	private String purviewnum;
	private String resid;
	private String emppassword;
	private String empname;
	private String emptel;
	private String empstatus;
	private java.sql.Timestamp adddate;
	
	public ResempmanagerVO() {
	}
	
	public ResempmanagerVO(String empid, String purviewnum, String resid, String emppassword, String empname,
			String emptel, String empstatus, Timestamp adddate) {
		super();
		this.empid = empid;
		this.purviewnum = purviewnum;
		this.resid = resid;
		this.emppassword = emppassword;
		this.empname = empname;
		this.emptel = emptel;
		this.empstatus = empstatus;
		this.adddate = adddate;
	}
	public String getEmpid() {
		return empid;
	}
	public void setEmpid(String empid) {
		this.empid = empid;
	}
	public String getPurviewnum() {
		return purviewnum;
	}
	public void setPurviewnum(String purviewnum) {
		this.purviewnum = purviewnum;
	}
	public String getResid() {
		return resid;
	}
	public void setResid(String resid) {
		this.resid = resid;
	}
	public String getEmppassword() {
		return emppassword;
	}
	public void setEmppassword(String emppassword) {
		this.emppassword = emppassword;
	}
	public String getEmpname() {
		return empname;
	}
	public void setEmpname(String empname) {
		this.empname = empname;
	}
	public String getEmptel() {
		return emptel;
	}
	public void setEmptel(String emptel) {
		this.emptel = emptel;
	}
	public String getEmpstatus() {
		return empstatus;
	}
	public void setEmpstatus(String empstatus) {
		this.empstatus = empstatus;
	}
	public java.sql.Timestamp getAdddate() {
		return adddate;
	}
	public void setAdddate(java.sql.Timestamp adddate) {
		this.adddate = adddate;
	}
	
	
	
}
