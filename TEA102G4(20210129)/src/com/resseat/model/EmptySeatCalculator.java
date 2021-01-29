package com.resseat.model;

import java.util.HashMap;
import java.util.List;

import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.resinfo.model.ResInfoService;
import com.resinfo.model.ResInfoVO;
import com.ressseat.jedis.JedisPoolUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class EmptySeatCalculator {
	// connection pool
	private static JedisPool pool = JedisPoolUtil.getJedisPool();
	
	public static void main(String[] args) {
		ResInfoService service = new ResInfoService();
		List<ResInfoVO> resinfoVOList = service.getAll();//拿取所有餐廳的resid
		
		HashMap<String,String> seat = getQuantityOfAvailableSeat(resinfoVOList);
		Set<String> resids = seat.keySet();
		for(String resid:resids) {
			System.out.println(resid+"還有空位:"+seat.get(resid));
		}
		// 用完後把泳池關閉
		pool.destroy();
		
	}
	
	
	
	public static HashMap<String,String> getQuantityOfAvailableSeat(List<ResInfoVO> resinfoVOList){
		HashMap<String,String> restid_availableSeats = new HashMap<String,String>();
		
		//拿取連線
		Jedis jedis = pool.getResource();
		jedis.auth("123456");// 輸入密碼
		
		for(ResInfoVO vo : resinfoVOList) {
			String resid = vo.getResid();
			//jedis key值。從餐廳的id拿到不同餐廳的座位狀態
			String residState_resid = new StringBuilder("seatState:").append(resid).toString();
			//拿到一個hashMap資料結構。從hashMap拿出key值為now的value (代表現在的餐廳座位狀態)
			String newSeatDataString = jedis.hget(residState_resid,"now");
			if(newSeatDataString == null) continue;
			
			System.out.println(vo.getResid()+"的座位狀態(JSON格式) :"+newSeatDataString);
			//處理json字串，並將其轉成java物件後回傳該家餐廳的空位數
			String availableSeatNumber = jasonGetNumber(newSeatDataString);
			restid_availableSeats.put(vo.getResid(),availableSeatNumber);
		}
		jedis.close();//關閉連線
		
		return restid_availableSeats;
	}
	
	//處理json字串
	public static String jasonGetNumber(String newSeatDataString) {
		Integer availableSeat = 0;
		try {
			//將字串轉成陣列
			JSONArray jsonArray = new JSONArray(newSeatDataString);
			
			for(int i = 0; i < jsonArray.length();i++) {
				//拿取陣列中的物件
				JSONObject json_ResSeatElement = jsonArray.getJSONObject(i);
				//當itemNumber == 4時表示這個座位沒有人使用
				String itemNumber = json_ResSeatElement.getString("itemNumber");
				if(itemNumber.equals("4"))
					availableSeat++;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		String availableSeatString = String.valueOf(availableSeat);
		
		return availableSeatString;
		
	}
}
