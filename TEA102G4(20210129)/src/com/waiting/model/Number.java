package com.waiting.model;

public class Number {
	private String total;
	private String current;
	private String user;
	private String seat;
	private String currentSeat;
	private String nextSeat;
	private String openClose;

	public Number(String total, String current, String user, String seat, String currentSeat, String nextSeat, String openClose) {
		super();
		this.total = total;
		this.current = current;
		this.user = user;
		this.seat = seat;
		this.currentSeat = currentSeat;
		this.nextSeat = nextSeat;
		this.openClose = openClose;
	}
}
