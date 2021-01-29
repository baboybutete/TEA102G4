package com.resseat.model;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TouristCounter {
	private static Map<String, Long> TouristCounter = new ConcurrentHashMap<>();
	
	//傳回目前數目resid+數字
	public static String getNumber(String resid) {
		
		if(TouristCounter.containsKey(resid)) {
			Long number = TouristCounter.get(resid);
			
			String touristNumber = resid + number.toString();
			TouristCounter.put(resid,++number);
			return touristNumber;
		}else {
			Long number = 0L;
			String touristNumber = resid + number.toString();
			TouristCounter.put(resid,++number);
			return touristNumber;
		}
	}
	
	public void minusNumber(String touristNumber) {
		System.out.println("要減嗎好麻煩 : "+touristNumber );
	}
	
}
