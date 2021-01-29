package com.addetail.model;

import java.sql.Timestamp;

public class AddetailVO implements java.io.Serializable{
	private String adid;
	private String resid;
	private String adtitle;
	private String adcontent;
	private byte[] adimg;
	private Timestamp addate;
	private String adtype;
	private String reviewstatus;
	private Timestamp onshelftime;
	private Timestamp offshelftime;
	
	public AddetailVO() {
	}
	
	public AddetailVO(String adid, String resid, String adtitle, String adcontent, byte[] adimg, Timestamp addate,
			String adtype, String reviewstatus, Timestamp onshelftime, Timestamp offshelftime) {
		super();
		this.adid = adid;
		this.resid = resid;
		this.adtitle = adtitle;
		this.adcontent = adcontent;
		this.adimg = adimg;
		this.addate = addate;
		this.adtype = adtype;
		this.reviewstatus = reviewstatus;
		this.onshelftime = onshelftime;
		this.offshelftime = offshelftime;
	}
	public String getAdid() {
		return adid;
	}
	public void setAdid(String adid) {
		this.adid = adid;
	}
	public String getResid() {
		return resid;
	}
	public void setResid(String resid) {
		this.resid = resid;
	}
	public String getAdtitle() {
		return adtitle;
	}
	public void setAdtitle(String adtitle) {
		this.adtitle = adtitle;
	}
	public String getAdcontent() {
		return adcontent;
	}
	public void setAdcontent(String adcontent) {
		this.adcontent = adcontent;
	}
	public byte[] getAdimg() {
		return adimg;
	}
	public void setAdimg(byte[] adimg) {
		this.adimg = adimg;
	}
	public Timestamp getAddate() {
		return addate;
	}
	public void setAddate(Timestamp addate2) {
		this.addate = addate2;
	}
	public String getAdtype() {
		return adtype;
	}
	public void setAdtype(String adtype) {
		this.adtype = adtype;
	}
	public String getReviewstatus() {
		return reviewstatus;
	}
	public void setReviewstatus(String reviewstatus) {
		this.reviewstatus = reviewstatus;
	}
	public Timestamp getOnshelftime() {
		return onshelftime;
	}
	public void setOnshelftime(Timestamp onshelftime) {
		this.onshelftime = onshelftime;
	}
	public Timestamp getOffshelftime() {
		return offshelftime;
	}
	public void setOffshelftime(Timestamp offshelftime) {
		this.offshelftime = offshelftime;
	}
	


}
