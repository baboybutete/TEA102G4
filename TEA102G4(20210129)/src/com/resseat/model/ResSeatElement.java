package com.resseat.model;

public class ResSeatElement {
	String x;
	String y;
	String w;
	String h;
	String color;
	String seatNumber;/*Different state has different item number*/
	String itemNumber;
	
	public ResSeatElement() {};
	
	public ResSeatElement(String x, String y, String w, String h, String color, String seatNumber, String itemNumber) {
		super();
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.color = color;
		this.seatNumber = seatNumber;
		this.itemNumber = itemNumber;
	}
	public String getX() {
		return x;
	}
	public void setX(String x) {
		this.x = x;
	}
	public String getY() {
		return y;
	}
	public void setY(String y) {
		this.y = y;
	}
	public String getW() {
		return w;
	}
	public void setW(String w) {
		this.w = w;
	}
	public String getH() {
		return h;
	}
	public void setH(String h) {
		this.h = h;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getSeatNumber() {
		return seatNumber;
	}
	public void setSeatNumber(String seatNumber) {
		this.seatNumber = seatNumber;
	}
	public String getItemNumber() {
		return itemNumber;
	}
	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}
	
	
}
