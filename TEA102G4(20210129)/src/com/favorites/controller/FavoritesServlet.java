package com.favorites.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.custinfo.model.CustinfoService;
import com.custinfo.model.CustinfoVO;
import com.favorites.model.FavoritesService;
import com.favorites.model.FavoritesVO;

@WebServlet("/favorites/favorites.do")
public class FavoritesServlet extends HttpServlet {
       
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String action = req.getParameter("action");
		
		if ("delete".equals(action)) { // 來自listAllEmp.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ***************************************/
				String favoritesid = req.getParameter("favoritesid");

				/*************************** 2.開始刪除資料 ***************************************/
				FavoritesService custinfoSvc = new FavoritesService();
				custinfoSvc.deleteFavorites(favoritesid);
				
				/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
				String url = "/front-end/cust/favorites/ListAllFavorites.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/cust/favorites/ListAllFavorites.jsp");
				failureView.forward(req, res);
				System.out.println("1111");
			}
		}
		
		if ("insert".equals(action)) { // 來自addEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			List<FavoritesVO> list = new FavoritesService().getAll();
			CustinfoVO custinfoVO = (CustinfoVO) req.getSession().getAttribute("custinfoVO");
//			HttpSession sessions = req.getSession(false);
//			if(sessions != null) {
//				sessions.removeAttribute("favoritesVO");
//			}
			  
			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				String custid = custinfoVO.getCustid();
				
				String resid = req.getParameter("resid").trim();
				for (FavoritesVO vo : list) {
					if ((vo.getResid()).equals(resid) && (vo.getCustid()).equals(custid)) {
						RequestDispatcher failureView = req.getRequestDispatcher("/front-end/cust/resinfo/index_res.jsp");
						failureView.forward(req, res);
						return;
					}						
				}
				String favoritestatus = ("已收藏");

				/*************************** 2.開始新增資料 ***************************************/
				FavoritesVO favoritesVO = new FavoritesVO();
				java.util.Date date = new java.util.Date();
				Timestamp addDate = new Timestamp(date.getTime());
				FavoritesService favoritesSvc = new FavoritesService();
				favoritesVO = favoritesSvc.insertFavorites(custid, resid, favoritestatus, addDate);

				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				req.getSession().setAttribute("favoritesVO", favoritesVO);
				String url = "/front-end/cust/resinfo/index_res.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/cust/resinfo/index_res.jsp");
				failureView.forward(req, res);
			}
		}
		if ("delete2".equals(action)) { // 來自listAllEmp.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ***************************************/
				String favoritesid = req.getParameter("favoritesid");

				/*************************** 2.開始刪除資料 ***************************************/
				FavoritesService custinfoSvc = new FavoritesService();
				custinfoSvc.deleteFavorites(favoritesid);
				
				/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
				String url = "/front-end/cust/resinfo/index_res.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/cust/favorites/ListAllFavorites.jsp");
				failureView.forward(req, res);
				System.out.println("1111");
			}
		}
	}

}
