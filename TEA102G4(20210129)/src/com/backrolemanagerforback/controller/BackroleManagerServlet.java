package com.backrolemanagerforback.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.admininfo.model.AdmininfoService;
import com.backrolemanager.model.BackRoleService;
import com.backrolemanager.model.BackRoleVO;

//@WebServlet("/BackroleManagerServlet")
public class BackroleManagerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String str = req.getParameter("manpurview");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入管理員權限");
				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/backrolemanager/select_page.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}

				String manpurview = null;
				try {
					manpurview = new String(str);
				} catch (Exception e) {
					errorMsgs.add("管理員權限格式不正確");
				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/backrolemanager/select_page.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始查詢資料 *****************************************/
				BackRoleService backRoleSvc = new BackRoleService();
				BackRoleVO backRoleVO = backRoleSvc.getOneAdmin(manpurview);
				if (backRoleVO == null) {
					errorMsgs.add("查無資料");
				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/backrolemanager/select_page.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("backRoleVO", backRoleVO); //資料庫取出的backRoleVO物件, 存入req
				String url = "/front-end/backrolemanager/listOneBackroleManager.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); //成功轉交 listOneBackroleManager.jsp
				successView.forward(req, res);
				
				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/backrolemanager/select_page.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("getOne_For_Update".equals(action)) { // 來自 listAllBackroleManager.jsp的請求
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/*************************** 1.接收請求參數 ****************************************/
				String manpurview = new String (req.getParameter("manpurview"));
				
				/*************************** 2.開始查詢資料 ****************************************/
				BackRoleService backRoleSvc = new BackRoleService();
				BackRoleVO backRoleVO = backRoleSvc.getOneAdmin(manpurview);
				
				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("backRoleVO", backRoleVO); // 資料庫取出的backRoleVO物件,存入req
				String url = "/front-end/backrolemanager/update_backrolemanager_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/backrolemanager/listAllBackroleManager.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("update".equals(action)) {   // 來自update_backrolemanager_input.jsp
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			BackRoleService backRoleService = new BackRoleService();
			List<BackRoleVO> backRoleVO_list = backRoleService.getAll();
			
			
			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String manpurview = new String(req.getParameter("manpurview").trim());
				
				//檢查權限內容是否跟原本的重複
				String purviewcontent = req.getParameter("purviewcontent");
				if (purviewcontent == null || purviewcontent.trim().length() == 0) {
					errorMsgs.add("權限內容: 請勿空白");
				} else {
					for (BackRoleVO list: backRoleVO_list) {
						if (purviewcontent.trim().equals(list.getPurviewcontent())){
							errorMsgs.add("此管理員權限已存在, 請重新選擇");
						}
					}
				}
				
				
				BackRoleVO backRoleVO = new BackRoleVO();
				backRoleVO.setManpurview(manpurview);
				backRoleVO.setPurviewcontent(purviewcontent);
				
				
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("backRoleVO", backRoleVO);
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/backrolemanager/update_backrolemanager_input.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/*************************** 2.開始修改資料 *****************************************/
				BackRoleService backRoleSvc = new BackRoleService();
				backRoleVO = backRoleSvc.updateBackRole(manpurview, purviewcontent);
				
				/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
				req.setAttribute("backRoleVO", backRoleVO);
				String url = "/front-end/backrolemanager/listOneBackroleManager.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/backrolemanager/update_backrolemanager_input.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("insert".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			BackRoleVO backRoleVO = new BackRoleVO();
			BackRoleService backRoleSvc = new BackRoleService();
			List<BackRoleVO> backRoleVO_list = backRoleSvc.getAll();
			
			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				// 檢查新增管理員權限等級是否存在
				String manpurview = new String(req.getParameter("manpurview").trim());
				if (manpurview == null || manpurview.trim().length() == 0) {
					errorMsgs.add("請輸入管理員權限");
				} else {
					for (BackRoleVO list : backRoleVO_list) {
						if (manpurview.trim().equals(list.getManpurview())) {
							errorMsgs.add("此管理員權限已存在, 請重新選擇");
						}
					}
				}
				
				String purviewcontent = req.getParameter("purviewcontent");
				if (purviewcontent == null || purviewcontent.trim().length() == 0) {
					errorMsgs.add("請輸入權限內容");
				} else {
					for (BackRoleVO list : backRoleVO_list) {
						if (purviewcontent.trim().equals(list.getPurviewcontent())) {
							errorMsgs.add("此權限內容已存在, 請重新選擇");
						}
					}
				}
				
				
				backRoleVO.setManpurview(manpurview);
				backRoleVO.setPurviewcontent(purviewcontent);
				
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("backRoleVO", backRoleVO);
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/backrolemanager/addBackroleManager.jsp");
				failureView.forward(req, res);
				return;
				}
				/*************************** 2.開始新增資料 *****************************************/
				backRoleSvc = new BackRoleService();
				backRoleVO = backRoleSvc.addBackRole(manpurview, purviewcontent);
				
				/*************************** 3.新增完成,準備轉交(Send the Success view) *************/
				req.setAttribute("backRoleVO", backRoleVO);
				String url = "/front-end/backrolemanager/listAllBackroleManager.jsp";
				
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/admininfo/addBackroleManager.jsp");
				failureView.forward(req, res);
			}
		}
		if ("delete".equals(action)) { // listAllBackroleManager.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ***************************************/
				String manpurview = new String(req.getParameter("manpurview"));
				/*************************** 2.開始刪除資料 ***************************************/
				BackRoleService backRoleSvc = new BackRoleService();
				backRoleSvc.deleteBackRole(manpurview);
				/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
				String url = "/front-end/backrolemanager/listAllBackroleManager.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/backrolemanager/listAllBackroleManager.jsp");
				failureView.forward(req, res);
			}
		}
		
	}

}
