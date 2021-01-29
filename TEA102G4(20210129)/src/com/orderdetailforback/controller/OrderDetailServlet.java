package com.orderdetailforback.controller;


	import java.io.*;
	import java.util.*;
	import javax.servlet.RequestDispatcher;
	import javax.servlet.ServletException;
	import javax.servlet.annotation.WebServlet;
	import javax.servlet.http.HttpServlet;
	import javax.servlet.http.HttpServletRequest;
	import javax.servlet.http.HttpServletResponse;
	import com.orderdetail.model.OrderDetailService;
	import com.orderdetail.model.OrderDetailVO;

	@WebServlet("/back-end/orderdetail/OrderDetail.do")
	public class OrderDetailServlet extends HttpServlet{
		
		private static final long serialVersionUID = 1L;
		
		public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
			
			doPost(req, res);
		}

		public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
			String action = req.getParameter("action");
			req.setCharacterEncoding("UTF-8");

			if ("getOneResOrderinfo_For_Display".equals(action)) { 

				List<String> errorMsgs = new LinkedList<String>();
				req.setAttribute("errorMsgs", errorMsgs);

				try {
					/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
					String str = req.getParameter("resid");
					if (str == null || (str.trim()).length() == 0) {
						errorMsgs.add("請輸入餐廳編號");
					}
					if (!errorMsgs.isEmpty()) {
						RequestDispatcher failureView = req
								.getRequestDispatcher("/front-end/res/orderdetail/select_page.jsp");
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
								.getRequestDispatcher("/front-end/res/orderdetail/select_page.jsp");
						failureView.forward(req, res);
						return;
					}
					
					/***************************2.開始查詢資料*****************************************/
					OrderDetailService orderDetailSvc = new OrderDetailService();
					List<OrderDetailVO> orderDetailVO = orderDetailSvc.getOrderInfo(resid);
					if (orderDetailVO == null) {
						errorMsgs.add("查無資料");
					}
					if (!errorMsgs.isEmpty()) {
						RequestDispatcher failureView = req
								.getRequestDispatcher("/front-end/res/orderdetail/select_page.jsp");
						failureView.forward(req, res);
						return;
					}
					
					/***************************3.查詢完成,準備轉交(Send the Success view)*************/
					req.setAttribute("orderDetailVO", orderDetailVO); 
//					req.getSession().setAttribute("orderDetailVO", orderDetailVO);
					String url = "/front-end/res/orderdetail/listResOrderInfoForCentre.jsp";
					RequestDispatcher successView = req.getRequestDispatcher(url); 
					successView.forward(req, res);

					/***************************其他可能的錯誤處理*************************************/
				} catch (Exception e) {
					errorMsgs.add("無法取得資料:" + e.getMessage());
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/res/orderdetail/select_page.jsp");
					failureView.forward(req, res);
				}
			}
			
			
			if ("getAllResOrderinfo_For_Display".equals(action)) { 

				List<String> errorMsgs = new LinkedList<String>();
				req.setAttribute("errorMsgs", errorMsgs);

				try {
					/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
					String str = req.getParameter("resid");
					if (str == null || (str.trim()).length() == 0) {
						errorMsgs.add("請輸入餐廳編號");
					}
					if (!errorMsgs.isEmpty()) {
						RequestDispatcher failureView = req
								.getRequestDispatcher("/front-end/res/orderdetail/select_page.jsp");
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
								.getRequestDispatcher("/front-end/res/orderdetail/select_page.jsp");
						failureView.forward(req, res);
						return;
					}
					
					/***************************2.開始查詢資料*****************************************/
					OrderDetailService orderDetailSvc = new OrderDetailService();
					List<OrderDetailVO> orderDetailVO = orderDetailSvc.getAllDetail(resid);
					if (orderDetailVO == null) {
						errorMsgs.add("查無資料");
					}
					if (!errorMsgs.isEmpty()) {
						RequestDispatcher failureView = req
								.getRequestDispatcher("/front-end/res/orderdetail/select_page.jsp");
						failureView.forward(req, res);
						return;
					}
					
					/***************************3.查詢完成,準備轉交(Send the Success view)*************/
					req.setAttribute("orderDetailVO", orderDetailVO); 
//					req.getSession().setAttribute("orderDetailVO", orderDetailVO);
					String url = "/front-end/res/orderdetail/listAllOrderInfoForCentre.jsp";
					RequestDispatcher successView = req.getRequestDispatcher(url); 
					successView.forward(req, res);

					/***************************其他可能的錯誤處理*************************************/
				} catch (Exception e) {
					errorMsgs.add("無法取得資料:" + e.getMessage());
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/res/orderdetail/select_page.jsp");
					failureView.forward(req, res);
				}
			}
			
			
			if ("getOneInfo_For_Display".equals(action)) { 

				List<String> error = new LinkedList<String>();
				req.setAttribute("error", error);

				try {
					/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
					String str = req.getParameter("orderid");
					if (str == null || (str.trim()).length() == 0) {
						error.add("請輸入訂單編號");
					}
					if (!error.isEmpty()) {
						RequestDispatcher failureView = req
								.getRequestDispatcher("/back-end/orderinfo/listAllOrderinfo.jsp");
						failureView.forward(req, res);
						return;
					}
					
					
					
					String orderid = null;
					try {
						orderid = new String(str);
					} catch (Exception e) {
						error.add("訂單編號格式不正確");
					}
					if (!error.isEmpty()) {
						RequestDispatcher failureView = req
								.getRequestDispatcher("/back-end/orderinfo/listAllOrderinfo.jsp");
						failureView.forward(req, res);
						return;
					}
					
					/***************************2.開始查詢資料*****************************************/
					OrderDetailService orderDetailSvc = new OrderDetailService();
					List<OrderDetailVO> orderDetailVO = orderDetailSvc.getOneDetail(orderid);
					if (orderDetailVO == null) {
						error.add("查無資料");
					}
					if (!error.isEmpty()) {
						RequestDispatcher failureView = req
								.getRequestDispatcher("/back-end/orderinfo/listAllOrderinfo.jsp");
						failureView.forward(req, res);
						return;
					}
					
					/***************************3.查詢完成,準備轉交(Send the Success view)*************/
					req.setAttribute("orderDetailVO", orderDetailVO); 
					String url = "/back-end/orderinfo/listOneOrderDetail.jsp";
					RequestDispatcher successView = req.getRequestDispatcher(url);
					req.removeAttribute("error");
					successView.forward(req, res);

					/***************************其他可能的錯誤處理*************************************/
				} catch (Exception e) {
					error.add("無法取得資料:" + e.getMessage());
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/orderinfo/listAllOrderinfo.jsp");
					failureView.forward(req, res);
				}
			}
		}
}
