package com.waiting.model;

import javax.websocket.Session;

public class Info {
	Session userSession;
	String userNumber;
	String userSeat;
	
	public Info(Session userSession, String userNumber, String userSeat) {
		super();
		this.userSession = userSession;
		this.userNumber = userNumber;
		this.userSeat = userSeat;
	}
	
	public Session getUserSession() {
		return this.userSession;
	}
	
	public void setUserSession(Session userSession) {
		this.userSession = userSession;
	}	
	
	public String getUserNumber() {
		return this.userNumber;
	}

	public void setUserNumber(String userNumber) {
		this.userNumber = userNumber;
	}

	public String getUserSeat() {
		return this.userSeat;
	}

	public void setUserSeat(String userSeat) {
		this.userSeat = userSeat;
	}
}
