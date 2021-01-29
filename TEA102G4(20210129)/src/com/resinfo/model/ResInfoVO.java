package com.resinfo.model;

import java.io.Serializable;
import java.sql.Timestamp;

// ok
public class ResInfoVO implements Serializable {
	private String resid;
	private String resname;
	private String resaddid;
	private byte[] resimg;
	private String barrierfree;
	private String parentchild;
	private String traffic;
	private String parking;
	private String payinfo;
	private String notifcont;
	private String resemail;
	private String respassid;
	private Integer currentwaitingnum;
	private Integer subtimediff;
	private Integer waitdistance;
	private String contact;
	private String contactphon;
	private String corrdinate;
	private Timestamp adddate;
	private String status;
	private String lat;
	private String lng;

	public String getResid() {
		return resid;
	}

	public void setResid(String resid) {
		this.resid = resid;
	}

	public String getResname() {
		return resname;
	}

	public void setResname(String resname) {
		this.resname = resname;
	}

	public String getResaddid() {
		return resaddid;
	}

	public void setResaddid(String resaddid) {
		this.resaddid = resaddid;
	}

	public byte[] getResimg() {
		return resimg;
	}

	public void setResimg(byte[] resimg) {
		this.resimg = resimg;
	}

	public String getBarrierfree() {
		return barrierfree;
	}

	public void setBarrierfree(String barrierfree) {
		this.barrierfree = barrierfree;
	}

	public String getParentchild() {
		return parentchild;
	}

	public void setParentchild(String parentchild) {
		this.parentchild = parentchild;
	}

	public String getTraffic() {
		return traffic;
	}

	public void setTraffic(String traffic) {
		this.traffic = traffic;
	}

	public String getParking() {
		return parking;
	}

	public void setParking(String parking) {
		this.parking = parking;
	}

	public String getPayinfo() {
		return payinfo;
	}

	public void setPayinfo(String payinfo) {
		this.payinfo = payinfo;
	}

	public String getNotifcont() {
		return notifcont;
	}

	public void setNotifcont(String notifcont) {
		this.notifcont = notifcont;
	}

	public String getResemail() {
		return resemail;
	}

	public void setResemail(String resemail) {
		this.resemail = resemail;
	}

	public String getRespassid() {
		return respassid;
	}

	public void setRespassid(String respassid) {
		this.respassid = respassid;
	}

	public Integer getCurrentwaitingnum() {
		return currentwaitingnum;
	}

	public void setCurrentwaitingnum(Integer currentwaitingnum) {
		this.currentwaitingnum = currentwaitingnum;
	}

	public Integer getSubtimediff() {
		return subtimediff;
	}

	public void setSubtimediff(Integer subtimediff) {
		this.subtimediff = subtimediff;
	}

	public Integer getWaitdistance() {
		return waitdistance;
	}

	public void setWaitdistance(Integer waitdistance) {
		this.waitdistance = waitdistance;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getContactphon() {
		return contactphon;
	}

	public void setContactphon(String contactphon) {
		this.contactphon = contactphon;
	}

	public String getCorrdinate() {
		return corrdinate;
	}

	public void setCorrdinate(String corrdinate) {
		this.corrdinate = corrdinate;
	}

	public Timestamp getAdddate() {
		return adddate;
	}

	public void setAdddate(Timestamp adddate) {
		this.adddate = adddate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}
	
}