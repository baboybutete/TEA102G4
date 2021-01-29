package com.map.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.menu.model.MenuService;
import com.menu.model.MenuVO;
import com.resinfo.model.ResInfoService;
import com.resinfo.model.ResInfoVO;

public class MapService {

	private MapDAO_interface dao;
	
	public MapService() {
		dao = new MapJDBCDAO();
	}
	
	public List<MapVO> getAll() {
		
		return dao.getAll();
	}
	
	//給消費者查詢
	public static void main(String[] args) {
		MapService.getSearchResult("中","");
	}
	public static List<MapVO> getSearchResult(String resaddid,String classname){
			ResInfoService resInfoSer = new ResInfoService();
			MenuService menuSer = new MenuService();
			MapService mapSer = new MapService();
			
			List<ResInfoVO> resList = resInfoSer.getAll();
			List<MenuVO> menuList = menuSer.getAll();
			List<MapVO> mapList = mapSer.getAll();
			List<MapVO> resultMapList = new ArrayList<MapVO>();
		
			
			
			/**有地點 有類型 : 拿餐廳地點回傳 後查詢菜單**/
			if((!resaddid.isEmpty()) && (!classname.isEmpty())) {
				//符合地點的餐廳
				System.out.println("有地點有餐廳");
				
				List<String> resultResid =  resList.stream()
								.filter(resvo->resvo.getResaddid().contains(resaddid))
								.filter(resvo-> menuList.stream()
								.anyMatch(menuvo -> menuvo.getResid().equals(resvo.getResid())&&
										menuvo.getClassname().contains(classname)))//找符合地點的餐廳內的菜單
								.map(resvo-> resvo.getResid())
								.collect(Collectors.toList());
				//拿取符合條件的map
				resultMapList = mapList.stream()
								.filter(mapvo -> resultResid.stream()
								.anyMatch(result -> result.equals(mapvo.getResid())))
								.distinct()
								.collect(Collectors.toList());
				return resultMapList;
			}
			
			//沒地點 有類型 : 拿菜單後回傳餐廳(去掉重複的resid (set))
			if((resaddid.isEmpty()) && (!classname.isEmpty())) {
				System.out.println("沒地點有類型");
				List<String> resultResid =	menuList.stream()
									.filter(menuvo -> menuvo.getClassname().contains(classname))
									.map(menuvo -> menuvo.getResid())
									.distinct()
									.collect(Collectors.toList());
				resultMapList =	mapList.stream()
										.filter(mapvo -> resultResid.stream()
										.anyMatch(result -> result.equals(mapvo.getResid())))
										.distinct()
										.collect(Collectors.toList());
				
				return resultMapList;
			}
			
			//有地點  沒類型 : 回傳餐廳結果		
			if((!resaddid.isEmpty()) && (classname.isEmpty())) {
				System.out.println("有地點 沒類型");
				List<String> resultResid =  resList.stream()
						.filter(resvo->resvo.getResaddid().contains(resaddid))
						.map(e -> e.getResid())
						.collect(Collectors.toList());
				
				resultMapList =	mapList.stream()
						.filter(mapvo -> resultResid.stream()
						.anyMatch(result -> result.equals(mapvo.getResid())))
						.distinct()
						.collect(Collectors.toList());
				
				return resultMapList;
			}
			return resultMapList;
	}
}
