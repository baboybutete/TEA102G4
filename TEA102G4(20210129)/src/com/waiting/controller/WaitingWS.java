package com.waiting.controller;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.google.gson.Gson;
import com.waiting.jedis.JedisHandler;
import com.waiting.model.Event;
import com.waiting.model.Info;
import com.waiting.model.Number;

@ServerEndpoint("/WaitingWS/{resid}/{userName}")
public class WaitingWS {
	private static Map<String, Map<String, Info>> infoMap = new ConcurrentHashMap<>();
	private static Map<String, String> openCloseMap = new ConcurrentHashMap<>();
	
	Gson gson = new Gson();
	
	@OnOpen
	public void onOpen(@PathParam("resid") String resid, @PathParam("userName") String userName, Session userSession) throws IOException {
		// 如果是未登入帳號，加上 session id 區別
		if (userName.equals("anonymous")) userName = userName + "_" + userSession.getId().toString();
		
		// 印出連線資訊
		String text = String.format("Session ID = %s, connected; resid = %s, userName = %s", userSession.getId(), resid, userName);
		System.out.println(text);
		
		// 將用戶 session 與候位號碼加到 map		
		// 如果該餐廳資料沒有在第一層的 map 中，則需要初始化
		if (!infoMap.containsKey(resid)) infoMap.put(resid, new ConcurrentHashMap<>());
		// 如果該用戶資料沒有在第二層的 map 中，則需要初始化
		if (!infoMap.get(resid).containsKey(userName)) {
			infoMap.get(resid).put(userName, new Info(userSession, "", ""));
		} else {
			// 如果有用戶資料代表用戶曾經連線過，所以只要更新 userSession 就好
			infoMap.get(resid).get(userName).setUserSession(userSession);
		}
		
		// 如果 開放 / 關閉候位 狀態尚未設定，則需要初始化
		if (!openCloseMap.containsKey(resid)) openCloseMap.put(resid, "open");
		
		// 傳送目前候位狀態給所有用戶
		sendNumber(resid);
	}
	
	@OnMessage
	public void onMessage(Session userSession, String message) {
		// 將收到的 json 格式轉成 Event 格式
		Event event = gson.fromJson(message, Event.class);
		
		if (event.getAction().equals("addCurrentNumber")) {
			// 呼叫下一號
			JedisHandler.addNumber(event.getResid(), "current");
		} else if (event.getAction().equals("delNumber")) {
			// 叫號歸零
			JedisHandler.delNumber(event.getResid(), "total");
			JedisHandler.delNumber(event.getResid(), "current");
			
			// 移除所有用戶候位號碼、候位人數
			Map<String, Info> map = infoMap.get(event.getResid()); // 去掉一層 map 比較好處理
			for (String key : map.keySet()) {
				if (map.get(key).getUserSession().isOpen()) {
					map.get(key).setUserNumber("");
					map.get(key).setUserSeat("");
				}
			}
		} else if (event.getAction().equals("setOpenClose")) {
			// 開放 / 關閉候位
			// 反轉目前狀態
			String openClose = "open";
			if (openCloseMap.get(event.getResid()).equals("open")) openClose = "close";
			
			// 儲存狀態到 map
			openCloseMap.put(event.getResid(), openClose);
		} else if (event.getAction().equals("getUserNumber")) {
			// 取得用戶候位號碼並更新號碼 map
			String userNumber = JedisHandler.addNumber(event.getResid(), "total");
			infoMap.get(event.getResid()).get(event.getUserName()).setUserNumber(userNumber);
			
			// 取得用戶候位人數並更新號碼 map
			infoMap.get(event.getResid()).get(event.getUserName()).setUserSeat(event.getUserSeat());
		}
		
		// 傳送目前候位狀態給所有用戶
		sendNumber(event.getResid());
	}
		
	@OnError
	public void onError(Session userSession, Throwable e) {
		System.out.println("Error: " + e.toString());
	}
	
	@OnClose
	public void onClose(Session userSession, CloseReason reason) {
		
	}
	
	// 傳送目前候位狀態給所有用戶
	public void sendNumber(String resid) {
		// 從 redis 取得目前候位狀態
		String totalNumber = JedisHandler.getNumber(resid, "total");
		String currentNumber = JedisHandler.getNumber(resid, "current");
		Integer nextNumber = (Integer.parseInt(currentNumber) + 1);
		
		// 印出候位狀態
		String text = String.format("resid = %s, totalNumber = %s, currentNumber = %s", resid, totalNumber, currentNumber);
		System.out.println(text);
		
		// 搜尋整個 infoMap
		String currentSeat = "";
		String nextSeat = "";
		Map<String, Info> map = infoMap.get(resid); // 去掉一層 map 比較好處理
		for (String key : map.keySet()) {
			String userNumber = map.get(key).getUserNumber();
			// 找到 目前號碼候位人(數)
			if (userNumber.equals(currentNumber)) {
				currentSeat = map.get(key).getUserSeat();
			}
			// 找到 下一組號碼候位人(數)
			if (userNumber.equals(nextNumber.toString())) {
				nextSeat = map.get(key).getUserSeat();
			}
		}
		
		// 取得 開放 / 關閉候位 狀態
		String openClose = openCloseMap.get(resid);
		
		// 傳送目前候位狀態給所有用戶
		for (String key : map.keySet()) {
			if (map.get(key).getUserSession().isOpen()) {
				String userNumber = map.get(key).getUserNumber();
				String userSeat = map.get(key).getUserSeat();
				Number number = new Number(totalNumber, currentNumber, userNumber, userSeat, currentSeat, nextSeat, openClose);
				map.get(key).getUserSession().getAsyncRemote().sendText(gson.toJson(number));
			}
		}
	}
}
