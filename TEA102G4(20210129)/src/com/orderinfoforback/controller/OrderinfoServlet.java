package com.orderinfoforback.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.orderinfo.model.OrderinfoService;
import com.orderinfo.model.OrderinfoVO;

@WebServlet("/back-end/orderinfo/orderinfo.do")
public class OrderinfoServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("getOne_For_Display".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String str = req.getParameter("orderid");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入訂單編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/orderinfo/listAllOrderinfo.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}
				String orderid = null;
				try {
					orderid = new String(str);
				} catch (Exception e) {
					errorMsgs.add("訂單編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/orderinfo/listAllOrderinfo.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}
				/***************************2.開始查詢資料*****************************************/
				OrderinfoService orderinfoSvc = new OrderinfoService();
				OrderinfoVO orderinfoVO = orderinfoSvc.getOneOrderinfo(orderid);
				if (orderinfoVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/orderinfo/listAllOrderinfo.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}
				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("orderinfoVO", orderinfoVO); // 資料庫取出的orderinfoVO物件,存入req
				String url = "/back-end/orderinfo/listOneOrderinfo.jsp";
				RequestDispatcher succesView = req.getRequestDispatcher(url); // 成功轉交 listOneAddetail.jsp
				req.removeAttribute("errorMsgs");
				succesView.forward(req, res);
				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/orderinfo/listAllOrderinfo.jsp");
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
				String url = "/back-end/orderinfo/update_orderinfo_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/orderinfo/listAllOrderinfo.jsp");
				failureView.forward(req, res);
			}
		}
		if ("update".equals(action)) { // 來自update_emp_input.jsp的請求
			
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
					errorMsgs.add("訂單編號: 只能是OXXXXX");
				}
				
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
				String subscriberReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
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
					subtime = java.sql.Timestamp.valueOf(req.getParameter("subtime").trim());
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
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("orderinfoVO", orderinfoVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/orderinfo/update_orderinfo_input.jsp");
					failureView.forward(req, res);
					return;
				}
				/***************************2.開始修改資料*****************************************/
				OrderinfoService orderinfoSvc = new OrderinfoService();
				orderinfoVO = orderinfoSvc.updateOrderinfo(orderid, resid, custid, subscriber, subphone, ordertime, subtime,
						subnumber, paystatus, checkin, seat);

				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("orderinfoVO", orderinfoVO);
				String url = "/front-end/orderinfo/listOneOrderinfo.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/orderinfo/update_orderinfo_input.jsp");
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
				String subscriberReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
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
					subtime = java.sql.Timestamp.valueOf(req.getParameter("subtime").trim());
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
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("orderinfoVO", orderinfoVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/orderinfo/addOrderinfo.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始新增資料 ***************************************/
				OrderinfoService orderinfoSvc = new OrderinfoService();
				orderinfoVO = orderinfoSvc.addOrderinfo(resid, custid, subscriber, subphone, ordertime, subtime,
						subnumber, paystatus, checkin, seat);

				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String url = "/front-end/orderinfo/listAllOrderinfo.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/orderinfo/addOrderinfo.jsp");
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
				String url = "/front-end/orderinfo/listAllOrderinfo.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/orderinfo/listAllOrderinfo.jsp");
				failureView.forward(req, res);
			}
		}
	}
}
