package com.orderdetail.controller;

import java.io.*;
import java.sql.Timestamp;
import java.util.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.orderdet.model.OrderdetService;
import com.orderdet.model.OrderdetVO;
import com.orderdetail.model.OrderDetailService;
import com.orderdetail.model.OrderDetailVO;

@WebServlet("/orderdetail/OrderDetailServlet")
public class OrderDetailServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("getOneResOrderinfo_For_Display".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String str = req.getParameter("resid");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入餐廳編號");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/res/orderdetail/listOneOrderInfoForCentre.jsp");
					failureView.forward(req, res);
					return;
				}

				String resid = null;
				try {
					resid = new String(str);
				} catch (Exception e) {
					errorMsgs.add("餐廳編號格式不正確");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/res/orderdetail/listOneOrderInfoForCentre.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始查詢資料 *****************************************/
				OrderDetailService orderDetailSvc = new OrderDetailService();
				List<OrderDetailVO> orderDetailVO = orderDetailSvc.getOrderInfo(resid);
				if (orderDetailVO == null) {
					errorMsgs.add("查無資料");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/res/orderdetail/listOneOrderInfoForCentre.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("orderDetailVO", orderDetailVO);
				String url = "/front-end/res/orderdetail/listResOrderInfoForCentre.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/res/orderdetail/listOneOrderInfoForCentre.jsp");
				failureView.forward(req, res);
			}
		}

		if ("getAllResOrderinfo_For_Display".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String str = req.getParameter("resid");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入餐廳編號");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/res/orderdetail/listOneOrderInfoForCentre.jsp");
					failureView.forward(req, res);
					return;
				}

				String resid = null;
				try {
					resid = new String(str);
				} catch (Exception e) {
					errorMsgs.add("餐廳編號格式不正確");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/res/orderdetail/listOneOrderInfoForCentre.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始查詢資料 *****************************************/
				OrderDetailService orderDetailSvc = new OrderDetailService();
				List<OrderDetailVO> orderDetailVO = orderDetailSvc.getAllDetail(resid);
				if (orderDetailVO == null) {
					errorMsgs.add("查無資料");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/res/orderdetail/listOneOrderInfoForCentre.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("orderDetailVO", orderDetailVO);
				String url = "/front-end/res/orderdetail/listResOrderInfoForCentre.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/res/orderdetail/listOneOrderInfoForCentre.jsp");
				failureView.forward(req, res);
			}
		}

		if ("getOneInfo_For_Display".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String str = req.getParameter("orderid");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入訂單編號");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/res/orderdetail/listOneOrderInfoForCentre.jsp");
					failureView.forward(req, res);
					return;
				}

				String orderid = null;
				try {
					orderid = new String(str);
				} catch (Exception e) {
					errorMsgs.add("訂單編號格式不正確");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/res/orderdetail/listOneOrderInfoForCentre.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始查詢資料 *****************************************/
				OrderDetailService orderDetailSvc = new OrderDetailService();
				List<OrderDetailVO> orderDetailVO = orderDetailSvc.getOneDetail(orderid);
				if (orderDetailVO == null) {
					errorMsgs.add("查無資料");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/res/orderdetail/listOneOrderInfoForCentre.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("orderDetailVO", orderDetailVO);
				String url = "/front-end/res/orderdetail/listOneOrderInfoForCentre.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/res/orderdetail/listOneOrderInfoForCentre.jsp");
				failureView.forward(req, res);
			}
		}

		/* 消費者端送請求 */
		if ("getOneDetail".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String orderid = req.getParameter("orderid");

				/*************************** 2.開始查詢資料 *****************************************/
				OrderdetService orderDetSvc = new OrderdetService();
				List<OrderdetVO> orderDetVO = orderDetSvc.finOne(orderid);
				if (orderDetVO == null) {
					errorMsgs.add("查無資料");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/cust/orderinfo_orderdet/ListAllOrder.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("orderDetVO", orderDetVO);
				String url = "/front-end/cust/orderinfo_orderdet/ListAllOderDetail.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/cust/orderinfo_orderdet/ListAllOrder.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("getOne_For_Update".equals(action)) { 
			
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數****************************************/
				String orderid = req.getParameter("orderid");
				String checkin = req.getParameter("checkin");
				System.out.println(orderid);
				System.out.println(checkin);
				
				/***************************2.開始查詢資料****************************************/
				OrderDetailService orderDetailSvc = new OrderDetailService();
				OrderDetailVO orderDetailVO = orderDetailSvc.update(checkin, orderid);
					
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("orderDetailVO", orderDetailVO);         
				String url = "/front-end/res/orderdetail/listResOrderInfoForCentre.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/res/orderdetail/listResOrderInfoForCentre.jsp");
				failureView.forward(req, res);
			}
		}
		
//		if ("update".equals(action)) { 
//			
//			List<String> errorMsgs = new LinkedList<String>();
//			req.setAttribute("errorMsgs", errorMsgs);
//		
//			try {
//				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
//				String orderid = req.getParameter("orderid");
//				String checkin = req.getParameter("checkin");
//				
//				
//				OrderDetailVO orderDetailVO = new OrderDetailVO();
//				orderDetailVO.setOrderid(orderid);
//				orderDetailVO.setCheckin(checkin);
//				
//				
//				if (!errorMsgs.isEmpty()) {
//					req.setAttribute("orderDetailVO", orderDetailVO); 
//					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/res/orderdetail/listResOrderInfoForCentre.jsp");
//					failureView.forward(req, res);
//					return;
//				}
//				
//				/***************************2.開始修改資料*****************************************/
//				OrderDetailService orderDetailSvc = new OrderDetailService();
//				orderDetailVO = orderDetailSvc.update(orderid,checkin);
//				System.out.println(orderid);
//				System.out.println(checkin);
//				/***************************3.修改完成,準備轉交(Send the Success view)*************/
//				req.setAttribute("orderDetailVO", orderDetailVO); 
//				String url = "/front-end/res/orderdetail/listResOrderInfoForCentre.jsp";
//				RequestDispatcher successView = req.getRequestDispatcher(url); 
//				successView.forward(req, res);
//				/***************************其他可能的錯誤處理*************************************/
//			} catch (Exception e) {
//				errorMsgs.add("修改資料失敗:"+e.getMessage());
//				RequestDispatcher failureView = req
//						.getRequestDispatcher("/front-end/res/orderdetail/listResOrderInfoForCentre.jsp");
//				failureView.forward(req, res);
//			}
//		}
	}
}
