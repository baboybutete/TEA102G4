package com.resseatoracle.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EmptySeatCalculatorOracle {
	public static void main(String[] args) {
		Map<String, String> resid_availableSeats = new HashMap<String, String>();//裝取結果
		
		ResSeatOracleService service = new ResSeatOracleService();
		//一個vo內包含resid,time(string 某時段，若是現在則使用now) 和座位狀態(string)
		List<ResSeatOracleVO> list = service.getAll();
		//只拿取now的vo,過濾要其他時段
		List<ResSeatOracleVO> newlist = list.stream().filter(vo->vo.getTime().equals("2021-01-03 11:15")).collect(Collectors.toList());
		for(ResSeatOracleVO vo : newlist) {
			String number = jasonGetNumber(vo.getSeatData());
			resid_availableSeats.put(vo.getResid(),number);
		}
		
		//結果
		System.out.println("結果如下:");
		Set<String> resids = resid_availableSeats.keySet();
		for(String resid:resids) {
			System.out.println(resid+"的空位數字:"+resid_availableSeats.get(resid));
		}
		
	}

	// 處理json字串,將座位狀態字串轉換成物件後，計算空位數，並回傳
	public static String jasonGetNumber(String newSeatDataString) {
		Integer availableSeat = 0;
		try {
			// 將字串轉成陣列
			JSONArray jsonArray = new JSONArray(newSeatDataString);

			for (int i = 0; i < jsonArray.length(); i++) {
				// 拿取陣列中的物件
				JSONObject json_ResSeatElement = jsonArray.getJSONObject(i);
				// 當itemNumber == 4時表示這個座位沒有人使用
				String itemNumber = json_ResSeatElement.getString("itemNumber");
				if (itemNumber.equals("4"))
					availableSeat++;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		String availableSeatString = String.valueOf(availableSeat);

		return availableSeatString;

	}
}
