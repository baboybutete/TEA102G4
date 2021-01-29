package com.ressseat.jedis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.resseat.model.ResSeatElement;
import com.resseat.model.SelectedSeatVO;
import com.resseatoracle.model.ResSeatOracleService;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisHandleSeat {
	/*當新增完訂單後把該人的選擇座位狀態殺掉，目前是別人更改狀態會把另一人以前的狀態變成黃色。
	 * 若訂到一半離開也要把那個人的狀態殺掉*/
	/* key = SeatDitribution : ResId(variable) */
	private static JedisPool pool = JedisPoolUtil.getJedisPool();
	private static Gson gson = new Gson();
	private static ResSeatOracleService service = new ResSeatOracleService();
	private static Map<String, String> temChooseSeats = new ConcurrentHashMap<String, String>();;

	// 餐廳業者存state用 由servlet呼叫，存到oracle方法由servlet呼叫
	public static void saveSeatDistribution(String resid, String seatData) {

		Jedis jedis = pool.getResource();
		jedis.auth("123456");
		/* 改用hash? */
		jedis.hset("seatDistribution", resid, seatData);
		String residSeatDistrubution = new StringBuilder("seatState:").append(resid).toString();
		/* 將繪製好的存到狀態now，之後的時間則會用時間來存 */
		jedis.hset(residSeatDistrubution, "now", seatData);

		System.out.println("save in redis!");
		jedis.close();
	}

	/* 用於餐廳更改坐位圖後，把現有的state座位刪掉 */
	public static void deleteAllSeatState(String resid) {
		// 先刪除custid選的座位，再刪除狀態
		Jedis jedis = pool.getResource();
		jedis.auth("123456");

		String seatState_resid = new StringBuilder("seatState:").append(resid).toString();
		Map<String, String> time_seatState = jedis.hgetAll(seatState_resid);
		Set<String> allTime = time_seatState.keySet();
		for (String time : allTime) {
			System.out.println(time);
		}
		deleteAllselectedSeats(resid, allTime, jedis);
		jedis.del(seatState_resid);
		jedis.close();
	}

	// 刪除全部的消費者選取的座位
	private static void deleteAllselectedSeats(String resid, Set<String> allTime, Jedis jedis) {
		for (String time : allTime) {
			String selectedSeats_resid_time = new StringBuilder("selectedSeats:").append(resid).append(":").append(time)
					.toString();
			jedis.del(selectedSeats_resid_time);
		}
	}
	//(還未測試)刪掉消費者選取的座位，但不改變餐廳狀態。或是將該選擇座位另外移到另一個key? 之後取消訂單可以用
	public static void moveOneSeletcedSeats(String resid,String custid,String time,boolean moveToNewKeyForOrderinfo) {
		Jedis jedis = pool.getResource();
		jedis.auth("123456");
		if(moveToNewKeyForOrderinfo) {
			System.out.println("訂單成立 刪除消費者的選位狀態並移到新的key");
		}else {
			System.out.println("在其他頁面關閉也許可以呼叫delete 並連到websocket");
		}
		
		System.out.println("注意time的格式是否正確 : "+ time);
		String selectedSeats_resid = new StringBuilder("selectedSeats:").append(resid).append(":").append(time).toString();
		String selectedSeatCustidString = jedis.hget(selectedSeats_resid, custid);
		jedis.del(selectedSeats_resid, custid);
		
		
		//key = OrderedSeats:resid:time  value= <custid seat>
		if(moveToNewKeyForOrderinfo) {
			String OrderedSeats_resid = new StringBuilder("OrderedSeats:").append(resid).append(":").append(time).toString();
			if(selectedSeatCustidString != null) {
				jedis.hset(OrderedSeats_resid, custid, selectedSeatCustidString);//取消訂單可以從這裡撈資料取消
			}
		}
		jedis.close();
	}
	
	

	// 不正常關閉則取消客戶端選取的座位，其他頁面不正常關閉會離開也可以呼叫這個方法(取消座位，其他切換時間已經被前面取消掉，所以只要切換單筆的就好);
	public static Map<String, String> deleteOneClientSelectedSeats(String custid) {
		Jedis jedis = pool.getResource();
		jedis.auth("123456");

		String selectedSeats_resid = temChooseSeats.remove(custid);

		Map<String, String> stringArray = null;

		// 消費者之前有選座位
		if (selectedSeats_resid != null) {
			String resid = selectedSeats_resid.substring(selectedSeats_resid.indexOf(":") + 1,
					selectedSeats_resid.indexOf(":") + 7);
			String time = selectedSeats_resid.substring(selectedSeats_resid.indexOf(":") + 1).substring(7);
			System.out.println("resid " + resid);
			System.out.println("time " + time);
			String test = jedis.hget(selectedSeats_resid, custid);

			System.out.println("test : " + test);
			try {
				// 消費者選的位置
				JSONArray jsonArray = new JSONArray(test);// 如果拿到空值會拋exception
				List<String> seatList = new ArrayList<String>();
				for (int i = 0; i < jsonArray.length(); i++) {
					seatList.add(jsonArray.getString(i));
				}
				jedis.hdel(selectedSeats_resid, custid);
				System.out.println("不正常關閉取消座位 或是切換時間而取消座位");

				// 將總狀態回復
				String residState_resid = new StringBuilder("seatState:").append(resid).toString();
				String seatsState = jedis.hget(residState_resid, time);
				List<ResSeatElement> seatData = getListFromJson(seatsState);
				List<ResSeatElement> needToCancelled = seatData.stream()
						.filter(vo -> seatList.contains(vo.getSeatNumber())).collect(Collectors.toList());

				for (ResSeatElement vo : needToCancelled) {
					vo.setColor("no");
					vo.setItemNumber("4");
				}
//				seatsState = gson.toJson(seatData);
//				jedis.hset(residState_resid, time, seatsState);

				// 拿到全部的狀態後，要將消費者選的取消掉然後存回去
//				savaSeatState()用這著改寫?
				stringArray = savaSeatState(resid, time, seatData, custid);

			} catch (JSONException e) {

				e.printStackTrace();
			}finally {
				jedis.close();
			}
//			
		}

		return stringArray;
	}

	public static String getSeatState(String resid, String time) {
		Jedis jedis = pool.getResource();
		jedis.auth("123456");
		String residState_resid = new StringBuilder("seatState:").append(resid).toString();

		String seatsState = jedis.hget(residState_resid, time);
		// 若該時間還沒有狀態，則拿取分布圖來存，這裡要synchronize?
		if (seatsState == null) {
			//
			System.out.println("redis 該時段沒有狀態，從分布圖拿取狀態");
			seatsState = jedis.hget("seatDistribution", resid);
			if(seatsState != null)
				jedis.hset(residState_resid, time, seatsState);

		}

		// 存到oracle的 resseat表格
		if(seatsState != null ) {
			if(time.equals("now")) {
				if (service.getOneResSeatOracleVO(resid, time) == null) {
					System.out.println("oracle 該時段沒有狀態，從redis分布圖拿取狀態");
					service.addResSeatOracle(resid, time, seatsState);
					System.out.println("存到oracle");
					System.out.println("time :"+ time);
				} else {
					// 為了假資料設定
					service.updateResSeatOracle(resid, time, seatsState);
					System.out.println("time :"+ time);
				}
			}
			
		}
		

		jedis.close();
		return seatsState;

	}

	// 回傳單筆處理過的座位圖
	public static String getSpecificSeatSate(String resid, String custid, String time) {
		System.out.println("消費者點擊相同時間，要回傳原本的狀態給他");
		Jedis jedis = pool.getResource();
		jedis.auth("123456");

		/***** 從redis拿取該餐廳的狀態 *****/
		String residState_resid = new StringBuilder("seatState:").append(resid).toString();
		String seatsState = jedis.hget(residState_resid, time);
		
		// 將餐廳字串改成物件
	
		if(seatsState != null) {
			List<ResSeatElement> specificSeatList = getListFromJson(seatsState);

			/***** 從redis拿取該消費者在該餐廳下的該時間選取的座位 *****/
			String selectedSeats_resid = new StringBuilder("selectedSeats:").append(resid).append(":" + time).toString();
			String selectedSeatCustidString = jedis.hget(selectedSeats_resid, custid);

			// 若消費消費者有選取座位則要對座位圖做處理
			if (selectedSeatCustidString != null) {
			
				//
				try {
					JSONArray jsonArray = new JSONArray(selectedSeatCustidString);
					System.out.println(custid + "已經選了" + jsonArray.length() + "個座位");

					// 將原本消費者選取的座位由紅色改成黃色。
					for (ResSeatElement resSeatElement : specificSeatList) {
						for (int i = 0; i < jsonArray.length(); i++) {
							String seatNumber = jsonArray.getString(i);
							//轉成黃色
							if(resSeatElement.getSeatNumber().equals(seatNumber)) {
								resSeatElement.setItemNumber("3");
								resSeatElement.setColor("yellow");
							}
							
						}
					}
					
					seatsState = new JSONArray(specificSeatList).toString();

				} catch (JSONException e) {
					System.out.println("JSON轉換失敗: 從JSON轉換成List存座位");
					e.printStackTrace();
					jedis.close();
				}finally {
					
				}

			}
			
//				jedis.close();
			
			
		}
		

	// seatData不能為空，一定要傳給消費者，如果他沒有選座位就傳給他raw seat state;
		
			jedis.close();
		
		
		return seatsState;

	}

	// 存整體外的座位狀態外，也要另外存各消費者選取的座位
	public static Map<String, String> savaSeatState(String resid, String time, List<ResSeatElement> seatData,
			String custid) {
		Jedis jedis = pool.getResource();
		jedis.auth("123456");

		// 存放該消費者選擇的座位們selected:resid Map(time , Map (custid : list))
		List<String> selectedSeatByCustid = new ArrayList<String>();
		// 將消費者選擇的座位放入list中，並把消費者選的位置顏色(黃色)改成被鎖定的顏色(紅色)
		for (ResSeatElement vo : seatData) {
			// 3是黃色 表示消費者選的位置 (成功改成紅色了)
			if (vo.getItemNumber().equals("3")) {
				selectedSeatByCustid.add(vo.getSeatNumber());
				// 將黃色設成紅色，並改item編號
				vo.setItemNumber("2");
				vo.setColor("red");
			}
		}

		/* 紀錄和修改custid 選用的位置，若該custid先前就有紀錄則直接覆蓋 */
		String selectedSeats_resid = new StringBuilder("selectedSeats:").append(resid).append(":" + time).toString();

		String selectedSeatCustidString = gson.toJson(selectedSeatByCustid);
		// delete存這個?
		jedis.hset(selectedSeats_resid, custid, selectedSeatCustidString);// 存在redis
		System.out.println("消費者透過save存的" + selectedSeatCustidString);
		temChooseSeats.put(custid, selectedSeats_resid);

		// 儲存座位的狀態，該狀態是只要選取的就會變成紅色
		String seatStateString = gson.toJson(seatData);
		String residState_resid = new StringBuilder("seatState:").append(resid).toString();

		jedis.hset(residState_resid, time, seatStateString);// 存在redis
		System.out.println("最新狀態已存到redis");
		if(time.equals("now")) {
			service.updateResSeatOracle(resid, time, seatStateString);// 存在oracle
			System.out.println("最新狀態now已存到oracle的resseat表格");
		}
		
		

		// 處理每個人看到得不同座位狀態，自己選的座位會是黃色，別人則會看到該座位是紅色
		// <custid, selectedSeate>
		Map<String, String> AllselectedSeats = jedis.hgetAll(selectedSeats_resid);
		// 將字串轉成數字，以利後面使用
		Map<String, List<String>> AllselectedSeatsNumber = new HashMap<String, List<String>>();
		// 將value的String 轉成list<int>
		Set<String> custids = AllselectedSeats.keySet();

		for (String custidInRedis : custids) {
			String seletedSeatString = AllselectedSeats.get(custidInRedis);
			try {
				List<String> seatNumber = new ArrayList<String>();
				System.out.println("已經選了幾個位置變成jsonarray前 : " + seletedSeatString);
				JSONArray jsonArray = new JSONArray(seletedSeatString);
				System.out.println(custidInRedis + "已經選了" + jsonArray.length() + "個座位");
				for (int i = 0; i < jsonArray.length(); i++) {
					seatNumber.add(jsonArray.getString(i));
				}
				AllselectedSeatsNumber.put(custidInRedis, seatNumber);
			} catch (JSONException e) {
				System.out.println("JSON轉換失敗: 從JSON轉換成List存座位");
				e.printStackTrace();
			}
		}

		// 存不同custid 對應不同的座位分布
		Map<String, String> newSeatDataforOne = new HashMap<String, String>();

		String newSeatDataString = jedis.hget(residState_resid, time);
		newSeatDataforOne.put("all", newSeatDataString);

		// 設定不同custid的座位狀態
		for (String custidInRedis : custids) {

			List<ResSeatElement> specificSeat = getListFromJson(newSeatDataString);
			List<String> selectedNumbers = AllselectedSeatsNumber.get(custidInRedis);

			for (ResSeatElement seat : specificSeat) {
				for (String number : selectedNumbers) {
					if (seat.getSeatNumber().equals(number)) {
						seat.setItemNumber("3");
						seat.setColor("yellow");
					}
				}
			}
			String seatStringforone = gson.toJson(specificSeat);// string
			newSeatDataforOne.put(custidInRedis, seatStringforone);
		}
		jedis.close();
		return newSeatDataforOne;
	}

	public static Map<String, String> deletedeleteOneClientSelectedSeats2(String resid, String time, String custid) {
		Jedis jedis = pool.getResource();
		jedis.auth("123456");
		Map<String, String> newSeatDataforOne = null;
		// 拿到該消費者在該家餐廳該時段的選位
		String selectedSeats_resid = new StringBuilder("selectedSeats:").append(resid).append(":" + time).toString();

		String selectedSeatCustidString = jedis.hget(selectedSeats_resid, custid);
		System.out.println("消費者要被取消的位置: " + selectedSeatCustidString);
		// 若該消費者在該餐廳的該時段有選位置的資料則刪除它
		// 如果從確認訂單回到這一頁會把訂單取消掉。 也許可以從正常關閉著手?
		// 如果是正常關閉則保留不把它取消掉
		if (selectedSeatCustidString != null) {
			System.out.println("有選位置 要把它取消掉");
			jedis.hdel(selectedSeats_resid, custid);
			try {
				JSONArray jsonarry = new JSONArray(selectedSeatCustidString);// 消費者選的位置json
				String residState_resid = new StringBuilder("seatState:").append(resid).toString();
				String seatStateString = jedis.hget(residState_resid, time);// 取得該餐廳該時段的狀態

				// 將消費者選的該餐廳該時段的狀態從紅色更改為白色
				System.out.println("取消前 : " + seatStateString);

				List<ResSeatElement> beforeDelete = getListFromJson(seatStateString);// 轉成java物件
				// 跑兩個迴圈
				for (ResSeatElement seat : beforeDelete) {
					for (int i = 0; i < jsonarry.length(); i++) {
						String seatNumber = jsonarry.getString(i);
						if (seat.getSeatNumber().equals(seatNumber)) {
							seat.setItemNumber("4");
							seat.setColor("no");
						}
					}
				}

				// 將設定好的座位狀態存回去redis
				String afterDeleteString = new JSONArray(beforeDelete).toString();
				System.out.println("取消後 : " + afterDeleteString);
				jedis.hset(residState_resid, time, afterDeleteString);

				// 不同custid 對應不同的座位分布<custid, seats>
				newSeatDataforOne = new HashMap<String, String>();

				// all 是給沒有選取座位的消費者
				String newSeatDataString = jedis.hget(residState_resid, time);
				newSeatDataforOne.put("all", newSeatDataString);

				// 處理每個人看到得不同座位狀態，自己選的座位會是黃色，別人則會看到該座位是紅色
				// <custid, selectedSeate>
				Map<String, String> AllselectedSeats = jedis.hgetAll(selectedSeats_resid);
				// 將字串轉成數字，以利後面使用
				Map<String, List<String>> AllselectedSeatsNumber = new HashMap<String, List<String>>();
				// 將value的String 轉成list<int>
				Set<String> custids = AllselectedSeats.keySet();

				for (String custidInRedis : custids) {
					String seletedSeatString = AllselectedSeats.get(custidInRedis);
					try {
						List<String> seatNumber = new ArrayList<String>();
						System.out.println("已經選了幾個位置變成jsonarray前 : " + seletedSeatString);
						JSONArray jsonArray = new JSONArray(seletedSeatString);
						System.out.println(custidInRedis + "已經選了" + jsonArray.length() + "個座位");
						for (int i = 0; i < jsonArray.length(); i++) {
							seatNumber.add(jsonArray.getString(i));
						}
						AllselectedSeatsNumber.put(custidInRedis, seatNumber);
					} catch (JSONException e) {
						System.out.println("JSON轉換失敗: 從JSON轉換成List存座位");
						e.printStackTrace();
					}
				}

				for (String custidInRedis : custids) {
					System.out.println("302");// 這裡下面會有null
					List<ResSeatElement> specificSeat = getListFromJson(newSeatDataString);
					List<String> selectedNumbers = AllselectedSeatsNumber.get(custidInRedis);

					for (ResSeatElement seat : specificSeat) {
						for (String number : selectedNumbers) {
							if (seat.getSeatNumber().equals(number)) {
								seat.setItemNumber("3");
								seat.setColor("yellow");
							}
						}
					}
					String seatStringforone = gson.toJson(specificSeat);// string
					newSeatDataforOne.put(custidInRedis, seatStringforone);
				}
			} catch (JSONException e) {

				e.printStackTrace();
			} finally {
				jedis.close();
			}
			return newSeatDataforOne;

		} else {
			System.out.println("沒選位置不做事");
			jedis.close();
			return newSeatDataforOne;
		}
	}

	// 得到List
	private static List<ResSeatElement> getListFromJson(String newSeatDataString) {
		ArrayList<ResSeatElement> newSeatData = new ArrayList<ResSeatElement>();
		try {
			JSONArray jsonArray = new JSONArray(newSeatDataString);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject json_ResSeatElement = jsonArray.getJSONObject(i);
				String x = json_ResSeatElement.getString("x");
				String y = json_ResSeatElement.getString("y");
				String w = json_ResSeatElement.getString("w");
				String h = json_ResSeatElement.getString("h");
				String color = json_ResSeatElement.getString("color");
				String itemNumber = json_ResSeatElement.getString("itemNumber");
				String seatNumber = json_ResSeatElement.getString("seatNumber");
				// 修改前
				ResSeatElement resSeatElement = new ResSeatElement(x, y, w, h, color, seatNumber, itemNumber);
				newSeatData.add(resSeatElement);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return newSeatData;

	}

}
