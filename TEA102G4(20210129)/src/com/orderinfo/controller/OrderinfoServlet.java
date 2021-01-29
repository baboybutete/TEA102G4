package com.orderinfo.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.orderdet.model.OrderdetVO;
import com.orderinfo.model.OrderinfoService;
import com.orderinfo.model.OrderinfoVO;
import com.ressseat.jedis.JedisHandleSeat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@WebServlet("/orderinfo/orderinfo.do")
public class OrderinfoServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//		String uids = req.getParameter("data2");
//		String[] uid = uids.split(",");
//		System.out.println(uid);
//		for(int i=0;i<uid.length;){
//			System.out.println("uid[i]:"+uid[i]);
//		}
		Enumeration abc = req.getParameterNames();
//		while(abc.hasMoreElements()){
//			String name = (String)abc.nextElement();
//			String values[] = req.getParameterValues(name);
//			
//			if(values != null) {
//				for(int i = 0;i<values.length;i++){
//					System.out.println(name+"["+i+"]:"+values[i]);
//				}
//			}
//		}

		if ("getOne_For_Display".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String orderid = req.getParameter("orderid");
				String orderidReg = "^[O]{1}[0-9]{5}$";
				if (orderid == null || orderid.trim().length() == 0) {
					errorMsgs.add("訂單編號: 請勿空白");
				} else if (!orderid.trim().matches(orderidReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("訂單編號: 只能是O開頭後面5個數字，不能O00000");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front_end/orderinfo/select_page.jsp");
					failureView.forward(req, res);
					return;
				}

				OrderinfoService orderinfoSvc = new OrderinfoService();
				OrderinfoVO orderinfoVO = orderinfoSvc.getOneOrderinfo(orderid);
				if (orderinfoVO == null) {
					errorMsgs.add("查無資料");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front_end/orderinfo/select_page.jsp");
					failureView.forward(req, res);
					return;
				}

				req.setAttribute("orderinfoVO", orderinfoVO);
				String url = "/front_end/orderinfo/listOneOrderinfo.jsp";
				RequestDispatcher succesView = req.getRequestDispatcher(url);
				succesView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front_end/orderinfo/select_page.jsp");
				failureView.forward(req, res);
			}
		}
		if ("getAll_For_Display".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String str = req.getParameter("resid");

				OrderinfoService orderinfoSvc = new OrderinfoService();
				List<OrderinfoVO> list = orderinfoSvc.getAllByOneResid(str);
				if (list == null) {
					errorMsgs.add("查無資料");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front_end/orderinfo/select_page.jsp");
					failureView.forward(req, res);
					return;
				}

				req.setAttribute("orderinfoVO", list);
				String url = "/front_end/orderinfo/listAllByResid.jsp";
				RequestDispatcher succesView = req.getRequestDispatcher(url);
				succesView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front_end/orderinfo/select_page.jsp");
				failureView.forward(req, res);
			}
		}
		if ("getOne_For_Update".equals(action)) { // 來自listAllEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數****************************************/
				String orderid = req.getParameter("orderid");
				
				/***************************2.開始查詢資料****************************************/
				OrderinfoService orderinfoSvc = new OrderinfoService();
				OrderinfoVO orderinfoVO = orderinfoSvc.getOneOrderinfo(orderid);
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("orderinfoVO", orderinfoVO);         // 資料庫取出的empVO物件,存入req
				String url = "/front_end/orderinfo/update_orderinfo_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front_end/orderinfo/listAllOrderinfo.jsp");
				failureView.forward(req, res);
			}
		}
		if ("update".equals(action)) { // 來自update_orderinfo_input.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String orderid = req.getParameter("orderid");
				String orderidReg = "^[O]{1}[0-9]{5}$";
				if (orderid == null || orderid.trim().length() == 0) {
					errorMsgs.add("訂單編號: 請勿空白");
				} else if (!orderid.trim().matches(orderidReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("訂單編號: 只能是O開頭後面5個數字，不能R00000");
				}
				
				String resid = req.getParameter("resid");
				String residReg = "^[R]{1}[0-9]{5}$";
				if (resid == null || resid.trim().length() == 0) {
					errorMsgs.add("餐廳編號: 請勿空白");
				} else if (!resid.trim().matches(residReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("餐廳編號: 只能是R開頭後面5個數字，不能R00000");
				}

				String custid = req.getParameter("custid");
				String custidReg = "^[C]{1}[0-9]{5}$";
				if (custid == null || custid.trim().length() == 0) {
					errorMsgs.add("消費者編號: 請勿空白");
				} else if (!custid.trim().matches(custidReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("消費者編號:只能是C開頭後面5個數字，不能R00000");
				}

				String subscriber = req.getParameter("subscriber");
				String subscriberReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,30}$";
				if (subscriber == null || subscriber.trim().length() == 0) {
					errorMsgs.add("訂位姓名: 請勿空白");
				} else if (!subscriber.trim().matches(subscriberReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("訂位姓名: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
				}

				String subphone = req.getParameter("subphone");
				String subphoneReg = "^[0]{1}[9]{1}[0-9]{8}$";
				if (subphone == null || subphone.trim().length() == 0) {
					errorMsgs.add("手機號碼: 請勿空白");
				} else if (!subphone.trim().matches(subphoneReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("手機號碼: 數字 , 且長度需在10碼");
				}

				java.sql.Timestamp ordertime = null;
				try {
					ordertime = java.sql.Timestamp.valueOf(req.getParameter("ordertime").trim());
				} catch (IllegalArgumentException e) {
					ordertime = new java.sql.Timestamp(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}

				java.sql.Timestamp subtime = null;
				try {
					if(req.getParameter("subtime").trim()=="") {
						throw new IllegalArgumentException();
//						errorMsgs.add("日期:不可為空");
					}else {
						subtime = new java.sql.Timestamp(sdf.parse((req.getParameter("subtime").trim())).getTime());
					}
				} catch (IllegalArgumentException e) {
					subtime = new java.sql.Timestamp(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}

				Integer subnumber = new Integer(req.getParameter("subnumber").trim());

				String paystatus = req.getParameter("paystatus");


				String checkin = req.getParameter("checkin");


				String seat = req.getParameter("seat");
				String seatReg = "^[0-9]{1,2}$";
				if (seat == null || seat.trim().length() == 0) {
					errorMsgs.add("座位: 請勿空白");
				} else if (!seat.trim().matches(seatReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("座位: ");
				}
				String orderstatus = req.getParameter("orderstatus");
				
				
				OrderinfoVO orderinfoVO = new OrderinfoVO();
				orderinfoVO.setOrderid(orderid);
				orderinfoVO.setResid(resid);
				orderinfoVO.setCustid(custid);
				orderinfoVO.setSubscriber(subscriber);
				orderinfoVO.setSubphone(subphone);
				orderinfoVO.setOrdertime(ordertime);
				orderinfoVO.setSubtime(subtime);
				orderinfoVO.setSubnumber(subnumber);
				orderinfoVO.setPaystatus(paystatus);
				orderinfoVO.setCheckin(checkin);
				orderinfoVO.setSeat(seat);
				orderinfoVO.setOrderstatus(orderstatus);
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("orderinfoVO", orderinfoVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/front_end/orderinfo/update_orderinfo_input.jsp");
					failureView.forward(req, res);
					return;
				}
				/***************************2.開始修改資料*****************************************/
				OrderinfoService orderinfoSvc = new OrderinfoService();
				orderinfoVO = orderinfoSvc.updateOrderinfo(orderid, resid, custid, subscriber, subphone, ordertime, subtime,
						subnumber, paystatus, checkin, seat, orderstatus);

				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("orderinfoVO", orderinfoVO);
				String url = "/front_end/orderinfo/listOneOrderinfo.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front_end/orderinfo/update_orderinfo_input.jsp");
				failureView.forward(req, res);
			}
		}

		if ("insert".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String resid = req.getParameter("resid");
				String residReg = "^[R]{1}[0-9]{5}$";
				if (resid == null || resid.trim().length() == 0) {
					errorMsgs.add("餐廳編號: 請勿空白");
				} else if (!resid.trim().matches(residReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("餐廳編號: 只能是RXXXXX");
				}

				String custid = req.getParameter("custid");
				String custidReg = "^[C]{1}[0-9]{5}$";
				if (custid == null || custid.trim().length() == 0) {
					errorMsgs.add("消費者編號: 請勿空白");
				} else if (!custid.trim().matches(custidReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("消費者編號:只能是CXXXXX");
				}

				String subscriber = req.getParameter("subscriber");
				System.out.println(subscriber);
				String subscriberReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,30}$";
				if (subscriber == null || subscriber.trim().length() == 0) {
					errorMsgs.add("訂位姓名: 請勿空白");
				} else if (!subscriber.trim().matches(subscriberReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("訂位姓名: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
				}

				String subphone = req.getParameter("subphone");
				System.out.println(subphone);
				String subphoneReg = "^[0]{1}[9]{1}[0-9]{8}$";
				if (subphone == null || subphone.trim().length() == 0) {
					errorMsgs.add("手機號碼: 請勿空白");
				} else if (!subphone.trim().matches(subphoneReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("手機號碼: 數字 , 且長度需在10碼");
				}

				java.sql.Timestamp ordertime = null;
//				try {
//					ordertime = java.sql.Timestamp.valueOf(req.getParameter("ordertime").trim());
//				} catch (IllegalArgumentException e) {
					ordertime = new java.sql.Timestamp(System.currentTimeMillis());
//					errorMsgs.add("請輸入日期!");
//				}
					System.out.println(ordertime);

				java.sql.Timestamp subtime = null;
				try {
					if(req.getParameter("subtime").trim()=="") {
//						throw new IllegalArgumentException();
						errorMsgs.add("日期:不可為空");
					}else {
						subtime = new java.sql.Timestamp(sdf.parse((req.getParameter("subtime").trim())).getTime());
					}
				} catch (IllegalArgumentException e) {
					subtime = new java.sql.Timestamp(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}
				System.out.println(subtime);
				Integer subnumber = new Integer(req.getParameter("subnumber").trim());
				System.out.println(subnumber);
				String paystatus = req.getParameter("paystatus");
				System.out.println(paystatus);
				String checkin = req.getParameter("checkin");
				System.out.println(checkin);
				String seat = req.getParameter("seat");
				System.out.println(seat);
				String seatReg = "^[0-9]{1,2}$";
				if (seat == null || seat.trim().length() == 0) {
					errorMsgs.add("座位: 請勿空白");
				} else if (!seat.trim().matches(seatReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("座位: ");
				}
				String orderstatus = req.getParameter("orderstatus");
				System.out.println(orderstatus);
				System.out.println(custid);
				System.out.println(resid);
				OrderinfoVO orderinfoVO = new OrderinfoVO();
				orderinfoVO.setResid(resid);
				orderinfoVO.setCustid(custid);
				orderinfoVO.setSubscriber(subscriber);
				orderinfoVO.setSubphone(subphone);
				orderinfoVO.setOrdertime(ordertime);
				orderinfoVO.setSubtime(subtime);
				orderinfoVO.setSubnumber(subnumber);
				orderinfoVO.setPaystatus(paystatus);
				orderinfoVO.setCheckin(checkin);
				orderinfoVO.setSeat(seat);
				orderinfoVO.setOrderstatus(orderstatus);
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("orderinfoVO", orderinfoVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/front_end/orderinfo/addOrderinfo.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始新增資料 ***************************************/
				OrderinfoService orderinfoSvc = new OrderinfoService();

				orderinfoVO = orderinfoSvc.addOrderinfo(resid, custid, subscriber, subphone, ordertime, subtime,
						subnumber, paystatus, checkin, seat, orderstatus);

				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String url = "/front_end/orderinfo/listAllOrderinfo.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front_end/orderinfo/addOrderinfo.jsp");
				failureView.forward(req, res);
			}
		}

		if ("delete".equals(action)) { // 來自listAllEmp.jsp
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ***************************************/
				String orderid = req.getParameter("orderid");

				/*************************** 2.開始刪除資料 ***************************************/
				OrderinfoService orderinfoSvc = new OrderinfoService();
				orderinfoSvc.deteleOrderid(orderid);

				/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
				String url = "/front_end/orderinfo/listAllOrderinfo.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front_end/orderinfo/listAllOrderinfo.jsp");
				failureView.forward(req, res);
			}
		}
		//確認訂單送來這裡
		if ("insert2".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				
				String resid = req.getParameter("resid");
				System.out.println("redsid : "+resid);
				String residReg = "^[R]{1}[0-9]{5}$";
				if (resid == null || resid.trim().length() == 0) {
					errorMsgs.add("餐廳編號: 請勿空白");
				} else if (!resid.trim().matches(residReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("餐廳編號: 只能是RXXXXX");
				}
				System.out.println(resid);

				String custid = req.getParameter("custid");
				String custidReg = "^[C]{1}[0-9]{5}$";
				if (custid == null || custid.trim().length() == 0) {
					errorMsgs.add("消費者編號: 請勿空白");
				} else if (!custid.trim().matches(custidReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("消費者編號:只能是CXXXXX");
				}

				String subscriber = req.getParameter("subscriber");
				System.out.println(subscriber);
				String subscriberReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,30}$";
				if (subscriber == null || subscriber.trim().length() == 0) {
					errorMsgs.add("訂位姓名: 請勿空白");
				} else if (!subscriber.trim().matches(subscriberReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("訂位姓名: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
				}

				String subphone = req.getParameter("subphone");
				System.out.println(subphone);
				String subphoneReg = "^[0]{1}[9]{1}[0-9]{8}$";
				if (subphone == null || subphone.trim().length() == 0) {
					errorMsgs.add("手機號碼: 請勿空白");
				} else if (!subphone.trim().matches(subphoneReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("手機號碼: 數字 , 且長度需在10碼");
				}

				java.sql.Timestamp ordertime = null;
//				try {
//					ordertime = java.sql.Timestamp.valueOf(req.getParameter("ordertime").trim());
//				} catch (IllegalArgumentException e) {
					ordertime = new java.sql.Timestamp(System.currentTimeMillis());
//					errorMsgs.add("請輸入日期!");
//				}
					System.out.println(ordertime);

				java.sql.Timestamp subtime = null;
				try {
					if(req.getParameter("subtime").trim()=="") {
//						throw new IllegalArgumentException();
						errorMsgs.add("日期:不可為空");
					}else {
						subtime = new java.sql.Timestamp(sdf.parse((req.getParameter("subtime").trim())).getTime());
					}
				} catch (IllegalArgumentException e) {
					subtime = new java.sql.Timestamp(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}
				System.out.println(subtime);
				Integer subnumber = new Integer(req.getParameter("subnumber").trim());
				System.out.println(subnumber);
				String paystatus = req.getParameter("paystatus");
				System.out.println(paystatus);
				String checkin = req.getParameter("checkin");
				System.out.println(checkin);
				String seat = req.getParameter("seat");
				System.out.println(seat);
				
				String orderstatus = req.getParameter("orderstatus");
				System.out.println(orderstatus);
				System.out.println(custid);
				System.out.println(resid);
				OrderinfoVO orderinfoVO = new OrderinfoVO();
				orderinfoVO.setResid(resid);
				orderinfoVO.setCustid(custid);
				orderinfoVO.setSubscriber(subscriber);
				orderinfoVO.setSubphone(subphone);
				orderinfoVO.setOrdertime(ordertime);
				orderinfoVO.setSubtime(subtime);
				orderinfoVO.setSubnumber(subnumber);
				orderinfoVO.setPaystatus(paystatus);
				orderinfoVO.setCheckin(checkin);
				orderinfoVO.setSeat(seat);
				orderinfoVO.setOrderstatus(orderstatus);
				List<OrderdetVO> testList = new ArrayList<OrderdetVO>();
				
				String count2 = req.getParameter("count");
				System.out.println(count2);
				if(count2 != "") {
					Integer count = Integer.valueOf(count2);
					System.out.println(count);
					String mealall = req.getParameter("mealall");
					String numall = req.getParameter("numall");
					JSONArray jsonArray = new JSONArray(mealall);
//					JSONObject xx = jsonArray.getJSONObject(1);
//					xx.getJSONArray("xx");
					JSONArray jsonArray2 = new JSONArray(numall);
					for(int j=0;j<count;j++) {
						System.out.println(jsonArray.getString(j));
						System.out.println(Integer.valueOf(jsonArray2.getString(j)));
					}
						
					for(Integer i = 0;i<count;i++) {
						String mealid = jsonArray.getString(i);
						Integer mealcount = Integer.valueOf(jsonArray2.getString(i));
						OrderdetVO i1 = new OrderdetVO();
						i1.setMealid(mealid);
						i1.setMealcount(mealcount);
						testList.add(i1);
					}
				}
				
				
				/*************************** 2.開始新增資料 ***************************************/
				OrderinfoService orderinfoSvc = new OrderinfoService();

				
				orderinfoSvc.insertWithOrderdets(orderinfoVO, testList);
				//瓊如新增的地方----> 將暫時的選取座位狀態變成永久
				JedisHandleSeat.moveOneSeletcedSeats(resid,custid,req.getParameter("subtime"),true);

				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				// 用ajax送的不用再轉交
//				String url = "/front-end/orderinfo/listAllOrderinfo.jsp";
//				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
//				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				
//				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/orderinfo/addOrderinfo.jsp");
//				failureView.forward(req, res);
			}
		}
	}
}
