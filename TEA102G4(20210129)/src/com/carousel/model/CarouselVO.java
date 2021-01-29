package com.carousel.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class CarouselVO implements Serializable{
	private String carouselid;
	private String resid;
	private byte[] carouselpic;
	private Timestamp adddate;

	public String getCarouselid() {
		return carouselid;
	}

	public void setCarouselid(String carouselid) {
		this.carouselid = carouselid;
	}

	public String getResid() {
		return resid;
	}

	public void setResid(String resid) {
		this.resid = resid;
	}

	public byte[] getCarouselpic() {
		return carouselpic;
	}

	public void setCarouselpic(byte[] carouselpic) {
		this.carouselpic = carouselpic;
	}

	public Timestamp getAdddate() {
		return adddate;
	}

	public void setAdddate(Timestamp adddate) {
		this.adddate = adddate;
	}
}
