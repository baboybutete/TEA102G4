package com.reshours.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class ResHoursVO implements Serializable {
	private String reshourid;
	private String resid;
	private Timestamp opening;
	private Timestamp closing;

	public String getReshourid() {
		return reshourid;
	}

	public void setReshourid(String reshourid) {
		this.reshourid = reshourid;
	}

	public String getResid() {
		return resid;
	}

	public void setResid(String resid) {
		this.resid = resid;
	}
	
	public Timestamp getOpening() {
		return opening;
	}

	public void setOpening(Timestamp opening) {
		this.opening = opening;
	}

	public Timestamp getClosing() {
		return closing;
	}

	public void setClosing(Timestamp closing) {
		this.closing = closing;
	}
}
