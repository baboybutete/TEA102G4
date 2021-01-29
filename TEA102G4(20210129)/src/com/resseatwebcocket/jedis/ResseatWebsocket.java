package com.resseatwebcocket.jedis;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.google.gson.Gson;
import com.resinfo.model.ResInfoService;
import com.resinfo.model.ResInfoVO;
import com.resseat.model.ResSeatElement;
import com.resseat.model.ResSeatState;
import com.resseat.model.TouristCounter;
import com.ressseat.jedis.JedisHandleSeat;


@ServerEndpoint("/Resseatstate/{resid}/{custid}")
public class ResseatWebsocket {
	private static final Map<String, Map<String,Session>> sessionsMap = new ConcurrentHashMap<String, Map<String,Session>>();
	private static final Map<String,String> clientChooseTime = new HashMap<String,String>(); 
	private static Gson gson = new Gson();
	
	@OnOpen
	public void onOpen( Session userSession, @PathParam("resid")String resid,@PathParam("custid")String custid) throws IOException {
//		System.out.println(custid);
		//記住使用者的連線
		if(!custid.equals("tourist")) {
			//非遊客和餐廳業者
			Map<String,Session> custidsAskResid = sessionsMap.get(resid);
			System.out.println("get resid :"+resid+" and "+" custid : "+custid);
			//若沒有放使用該餐廳專門的Map的session 則創立一個該餐廳專屬的Map存特定使用者
			if(custidsAskResid == null) {
				custidsAskResid = new ConcurrentHashMap<String,Session>();
				custidsAskResid.put(custid,userSession);
				sessionsMap.put(resid,custidsAskResid);			
			}else {
				//將session放入專屬於該餐廳的map
				custidsAskResid.put(custid,userSession);
			}
			
		}else {
			//遊客
			//拿到號碼牌
			String touristNumber = TouristCounter.getNumber(resid);
			Map<String,Session> custidsAskResid = sessionsMap.get(resid);
			System.out.println("連結餐廳 :"+resid+" and "+" 遊客 : "+touristNumber);
			//若沒有放使用該餐廳專門的Map的session 則創立一個該餐廳專屬的Map存特定使用者
			if(custidsAskResid == null) {
				custidsAskResid = new ConcurrentHashMap<String,Session>();
				custidsAskResid.put(touristNumber,userSession);
				sessionsMap.put(resid,custidsAskResid);			
			}else {
				//將session放入專屬於該餐廳的map
				custidsAskResid.put(touristNumber,userSession);
			}
			//傳送遊客號碼給前端
			userSession.getAsyncRemote().sendText(touristNumber);
			
		}
		
		
		
		
		String text = String.format("Session ID = %s, connected", userSession.getId());
		
		
	}
	
	//server端收到資訊執行
	@OnMessage
	public void onMessage(Session userSession, String message) {
		//message include resid{resid: xxx, time:"",seatData:"[]"}
		if(message == null) {
			System.out.println("message is null");
		}
//		System.out.println("get message from client : "+message);
		ResSeatState resseatstate = gson.fromJson(message,ResSeatState.class);
		
		List<ResSeatElement> seatDataFromClient = resseatstate.getSeatData();
		String resid = resseatstate.getResid();
		String time = resseatstate.getTime();
		String custid = resseatstate.getCustid();
		System.out.println("遊客測試 設定好後要求座位 :"+ custid);
	
		
		
		//選取時間
		if(seatDataFromClient.isEmpty()) {
			System.out.println("選取時間");
			String lastTime = null;
			//若消費者之前有點擊過時間，則先把先前的時間拿出來
			if(clientChooseTime.containsKey(custid)) {
				lastTime = clientChooseTime.get(custid);
			}
			//測試
			//如果之前有切換過時間，就有可能選座位，因此交給delete去刪除(0109多家&&)
			//先前選過座位，且切換不同的時間下會取消座位。
			if(clientChooseTime.containsKey(custid) && (!clientChooseTime.get(custid).equals(time))) {
				//點擊相同時間這裡不會進來
				System.out.println("點擊相同時間test1 : 先前有點過時間，並且與先前的時間不同因此要準備取消座位");
				lastTime = clientChooseTime.get(custid);
				clientChooseTime.put(custid,time);//加到其他時間分類
				Map<String,String> stringArray = JedisHandleSeat.deletedeleteOneClientSelectedSeats2(resid,lastTime, custid);
				//有回傳值代表有選位置
				if(stringArray != null) {
					System.out.println("點擊相同時間test2 : 先前有點過時間，並且與先前的時間不同，且有選取座位，因此取消後要將新狀態傳給先前時間的消費者");
					sendMessage(stringArray, resid, custid,lastTime);//傳訊息給相同時間相同餐廳的人
				}
			}
			
			//個人切完時間後拿到目標時間的座位
			clientChooseTime.put(custid,time);//加到其他時間分類
			//若lastTime是空值代表第一次進來選取時間,若不是第一次選取時間但選的是不同時間也可以直接拿到沒有選取過的座位
			//(若有選擇座位後，從確認訂單回來再點不會是null)
			if(lastTime == null ) {//上面的if 沒進去lastTime就會變成空值
				//不同時間下的座位狀態
				System.out.println("點擊相同時間test3 : 第一次進來該餐廳的座位圖");
				String seatData = JedisHandleSeat.getSeatState(resid,time);// raw seat state
				if(seatData != null)
					userSession.getAsyncRemote().sendText(seatData);
			}else if(lastTime.equals(time)){
				
				//點擊相同時間，不用推播給起他人，上面的刪除不會進去，因此還沒傳送訊息給該消費者
				//相同時間下該拿到有處理過的座位圖
				/**********************************************************/
				//seatData不能為空，一定要傳給消費者，如果他沒有選座位就傳給他raw seat state;
				//getSeatSate2 是傳送有處理過的
				System.out.println("點擊相同時間test4 : ");
				String seatData = JedisHandleSeat.getSpecificSeatSate(resid,custid,time);//可能上面已經發過了
				if(seatData != null)
					userSession.getAsyncRemote().sendText(seatData);
				
			}else if (!lastTime.equals(time)){
				System.out.println("點擊相同時間test3 : 消費者不是第一次點選時間，且點選的是不同的時間");
				String seatData = JedisHandleSeat.getSeatState(resid,time);// raw seat state
				if(seatData != null)
					userSession.getAsyncRemote().sendText(seatData);
			}
			
			
		//選座位
		}else {
			System.out.println("選座位");
			
			//已經拿到座位並且在選取
			/*拿到兩種seatdata*/
//			String[] stringArray =  JedisHandleSeat.savaSeatState(resseatstate.getResid(), resseatstate.getTime(), resseatstate.getSeatData(),resseatstate.getCustid());
			Map<String,String> stringArray =  JedisHandleSeat.savaSeatState(resseatstate.getResid(), resseatstate.getTime(), resseatstate.getSeatData(),resseatstate.getCustid());
		
			sendMessage(stringArray, resid, custid,time);
		}
		
	}

	@OnClose
	public void onClose(Session userSession, CloseReason reason) {
		Set<String> restids = sessionsMap.keySet();
		String closeCustid = null;
		String resid = null;
		String time = null;
		
		//移除close的session
		for(String restid : restids) {
			Map<String,Session> custidAndSession = sessionsMap.get(restid);
			Set<String> custids = custidAndSession.keySet();
			for(String custid: custids) {
				Session removeSession = custidAndSession.get(custid);
				if(removeSession.equals(userSession)) {
					resid = restid;
					custidAndSession.remove(custid);
					time = clientChooseTime.get(custid);
					closeCustid = custid;
				}
			}
		}
		
		System.out.println("close code 測試: "+reason.getCloseCode().getCode());
		
		/*拿取餐廳業者的email帳號*/
		ResInfoService ResSer = new ResInfoService();
		String resmail = null;
		ResInfoVO resInfoVO = ResSer.getOneResInfo(resid);
		if(resInfoVO != null) {
			resmail = resInfoVO.getResemail();
		}
		//表示不正常關閉
		//上一頁是1001 ,重新整理:在前端js寫關閉
		if(reason.getCloseCode().getCode() != 1000) {
			//方法回傳空值時表示該消費者沒有選座位，因此不需要再推播出去
			if(resmail != null&&resmail.equals(closeCustid))return;//若是餐廳業者選位置離開則不用取消
			Map<String,String> stringArray = JedisHandleSeat.deletedeleteOneClientSelectedSeats2(resid, time, closeCustid);
			if(stringArray != null) {
				sendMessage(stringArray, resid, closeCustid,time);
			}
			
		}
		
		
		
	}

	@OnError
	public void onError(Session userSession, Throwable e) {
		System.out.println("Error: " + e.toString());
//		e.printStackTrace(System.err);
	}
	
	public static List<String> getCustid(Map<String,String> clientChooseTime,String time) {
		 Set<Entry<String,String>> entrys = clientChooseTime.entrySet();
		 List<String> list = entrys.stream()
		 .filter(e->e.getValue().equals(time))
		 .map(Map.Entry::getKey)
		 .collect(Collectors.toList());
		
		return list;
	}
	
	public static void sendMessage(Map<String,String> stringArray, String resid,String custid,String time) {
		//使用相同餐廳的session
		System.out.println("發送訊息給"+"resid = "+resid + "custid : "+custid+"time : "+time);
		Map<String,Session> custidsAskResid = sessionsMap.get(resid);
		
		
		List<String> sendClients= getCustid(clientChooseTime,time);//回傳設定同樣時間的client
		
		//從這個userTime找到不同的key
		for(String onecustid:sendClients) {
			//如果還在線上就傳訊息給他
			System.out.println("相同時間下的人有 :"+onecustid);
			if(custidsAskResid.containsKey(onecustid)) {
				Session custidSession = custidsAskResid.get(onecustid);
				//傳送訊息給有選位置的人
				if(stringArray.containsKey(onecustid)) {//沒選位置stringArray 會是回傳空值
					custidSession.getAsyncRemote().sendText(stringArray.get(onecustid));
					System.out.println("sendMeassage to "+onecustid);
				}else {
					//傳送訊息給沒選位置的人
					custidSession.getAsyncRemote().sendText(stringArray.get("all"));
				}
			}else {
				System.out.println("他不在線上");
			}	
		}
	}

}
