package com.reshours.controller;

import java.io.*;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import com.reshours.model.ResHoursService;
import com.reshours.model.ResHoursVO;
@WebServlet("/reshours/ResHoursServlet")
public class ResHoursServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		if ("getOne_For_Display".equals(action)) { 

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String str = req.getParameter("reshourid");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入餐廳營業時間編號");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/res/reshours/listAllResForCentre.jsp");
					failureView.forward(req, res);
					return;
				}
				
				String reshourid = null;
				try {
					reshourid = new String(str);
				} catch (Exception e) {
					errorMsgs.add("餐廳營業時間編號格式不正確");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/res/reshours/listAllResForCentre.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始查詢資料*****************************************/
				ResHoursService reshoursSvc = new ResHoursService();
				ResHoursVO resHoursVO = reshoursSvc.getOneResHours(reshourid);
				if (resHoursVO == null) {
					errorMsgs.add("查無資料");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/res/reshours/listAllResForCentre.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("resHoursVO", resHoursVO); 
//				req.getSession().setAttribute("resHoursVO", resHoursVO);
				String url = "/front-end/res/reshours/listOneResHoursForCentre.jsp";
				
				RequestDispatcher successView = req.getRequestDispatcher(url); 
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/res/reshours/listAllResForCentre.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("getOne_For_Update".equals(action)) { 
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數****************************************/
				String reshourid = new String(req.getParameter("reshourid"));
				
				/***************************2.開始查詢資料****************************************/
				ResHoursService reshoursSvc = new ResHoursService();
				ResHoursVO resHoursVO = reshoursSvc.getOneResHours(reshourid);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("resHoursVO", resHoursVO);         
				String url = "/front-end/res/reshours/updateReshourForCentre.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/res/reshours/listAllResHoursForCentre.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("update".equals(action)) { 
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String reshourid = new String(req.getParameter("reshourid").trim());
				
				String resid = req.getParameter("resid");
				
				String openstr = req.getParameter("opening");
				Timestamp opening = new Timestamp(new java.util.Date().getTime());
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
				
				try {
					java.util.Date date = sdf.parse(openstr);
					opening = new Timestamp(date.getTime());
					
				} catch (Exception e) {
					opening=new Timestamp(System.currentTimeMillis());
					errorMsgs.add("請輸入開始營業時間!");
				} 
				
				
				String closestr = req.getParameter("closing");
				Timestamp closing = new Timestamp(new java.util.Date().getTime());
				try {
					java.util.Date date = sdf.parse(closestr);
					closing = new Timestamp(date.getTime());
				} catch (Exception e) {
					closing=new Timestamp(System.currentTimeMillis());
					errorMsgs.add("請輸入結束營業時間!");
				}

				ResHoursVO resHoursVO = new ResHoursVO();
				resHoursVO.setReshourid(reshourid);
				resHoursVO.setResid(resid);
				resHoursVO.setOpening(opening);
				resHoursVO.setClosing(closing);
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("resHoursVO", resHoursVO); 
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/res/reshours/updateReshourForCentre.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始修改資料*****************************************/
				ResHoursService reshoursSvc = new ResHoursService();
				resHoursVO = reshoursSvc.updateResHours(reshourid, resid, opening, closing);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("resHoursVO", resHoursVO); 
				String url = "/front-end/res/reshours/listOneResHoursForCentre.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); 
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/res/reshours/updateReshourForCentre.jsp");
				failureView.forward(req, res);
			}
		}

        if ("insert".equals(action)) { 
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				String resid = req.getParameter("resid");

				String residReg = "^[R]{1}[0-9]{5}$";
				if (resid == null || resid.trim().length() == 0) {
					errorMsgs.add("餐廳編號: 請勿空白");
				} else if(!resid.trim().matches(residReg)) { 
					errorMsgs.add("餐廳編號: 只能是'RXXXXX'");
	            }
				
				String openstr = req.getParameter("opening");
				Timestamp opening = new Timestamp(new java.util.Date().getTime());
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
				try {
					java.util.Date date = sdf.parse(openstr);
					opening = new Timestamp(date.getTime());
				} catch (IllegalArgumentException e) {
					opening = new Timestamp(System.currentTimeMillis());
					errorMsgs.add("請輸入開始營業時間!");
				}
				
				String closestr = req.getParameter("closing");
				Timestamp closing = new Timestamp(new java.util.Date().getTime());
				try {
					java.util.Date date = sdf.parse(closestr);
					closing = new Timestamp(date.getTime());
				} catch (IllegalArgumentException e) {
					closing = new Timestamp(System.currentTimeMillis());
					errorMsgs.add("請輸入結束營業時間!");
				}

				ResHoursVO resHoursVO = new ResHoursVO();
				resHoursVO.setResid(resid);
				resHoursVO.setOpening(opening);
				resHoursVO.setClosing(closing);
				
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("resHoursVO", resHoursVO); 
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/res/reshours/listAllResForCentre.jsp");
					failureView.forward(req, res);
					return;
				}

				/***************************2.開始新增資料***************************************/
				ResHoursService reshoursSvc = new ResHoursService();
				resHoursVO = reshoursSvc.addResHours(resid, opening, closing);
				//再加把redis的消費者選擇座位刪掉,這樣子下次同一個消費者進來看同一個餐廳同一個時間就不會亮黃燈
				
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/front-end/res/reshours/listAllResForCentre.jsp";
				res.sendRedirect(req.getContextPath()+url);
//				RequestDispatcher successView = req.getRequestDispatcher(url); 
//				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				e.printStackTrace();
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/res/reshours/listAllResForCentre.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("delete".equals(action)) { 

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/***************************1.接收請求參數***************************************/
				String reshourid = new String(req.getParameter("reshourid"));
				
				/***************************2.開始刪除資料***************************************/
				ResHoursService reshoursSvc = new ResHoursService();
				reshoursSvc.deleteResHours(reshourid);
				
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
				String url = "/front-end/res/reshours/listAllResHoursForCentre.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/res/reshours/listAllResHoursForCentre.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("getAllRes_For_Display".equals(action)) { 

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
							.getRequestDispatcher("/front-end/res/reshours/listAllResForCentre.jsp");
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
							.getRequestDispatcher("/front-end/res/reshours/listAllResForCentre.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始查詢資料*****************************************/
				ResHoursService reshoursSvc = new ResHoursService();
				List<ResHoursVO> resHoursVO = reshoursSvc.getAllRes(resid);
				if (resHoursVO == null) {
					errorMsgs.add("查無資料");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/res/reshours/listAllResForCentre.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
//				req.setAttribute("resHoursVO", resHoursVO); 
				req.getSession().setAttribute("resHoursVO", resHoursVO);
				String url = "/front-end/res/reshours/listAllResForCentre.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); 
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/res/reshours/listAllResForCentre.jsp");
				failureView.forward(req, res);
			}
		}
	}
}
