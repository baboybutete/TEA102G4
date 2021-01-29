package com.custinfo.model;
import java.sql.Timestamp;
import java.util.Arrays;

public class CustinfoVO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	String custid;
	String custname;
	String custtel;
	String custaccount;
	String custpassword;
	String custstatus;
	Timestamp registrationtime;
	byte[] custpicture;
	
	
	@Override
	public String toString() {
		return "CustinfoVO [custid=" + custid + ", custname=" + custname + ", custtel=" + custtel + ", custaccount="
				+ custaccount + ", custpassword=" + custpassword + ", custstatus=" + custstatus + ", registrationtime="
				+ registrationtime + ", custpicture=" + Arrays.toString(custpicture) + "]";
	}
	public CustinfoVO() {
	}
	public CustinfoVO(String custid, String custname, String custtel, String custaccount, String custpassword,
			String custstatus, Timestamp registrationtime, byte[] custpicture) {
		super();
		this.custid = custid;
		this.custname = custname;
		this.custtel = custtel;
		this.custaccount = custaccount;
		this.custpassword = custpassword;
		this.custstatus = custstatus;
		this.registrationtime = registrationtime;
		this.custpicture = custpicture;
	}
	public String getCustid() {
		return custid;
	}
	public void setCustid(String custid) {
		this.custid = custid;
	}
	public String getCustname() {
		return custname;
	}
	public void setCustname(String custname) {
		this.custname = custname;
	}
	public String getCusttel() {
		return custtel;
	}
	public void setCusttel(String custtel) {
		this.custtel = custtel;
	}
	public String getCustaccount() {
		return custaccount;
	}
	public void setCustaccount(String custaccount) {
		this.custaccount = custaccount;
	}
	public String getCustpassword() {
		return custpassword;
	}
	public void setCustpassword(String custpassword) {
		this.custpassword = custpassword;
	}
	public String getCuststatus() {
		return custstatus;
	}
	public void setCuststatus(String custstatus) {
		this.custstatus = custstatus;
	}
	public Timestamp getRegistrationtime() {
		return registrationtime;
	}
	public void setRegistrationtime(Timestamp registrationtime) {
		this.registrationtime = registrationtime;
	}
	public byte[] getCustpicture() {
		return custpicture;
	}
	public void setCustpicture(byte[] custpicture) {
		this.custpicture = custpicture;
	}
	
	
	
	
	
}
