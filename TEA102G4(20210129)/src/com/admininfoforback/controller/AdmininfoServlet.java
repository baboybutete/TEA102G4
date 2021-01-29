package com.admininfoforback.controller;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.jasper.tagplugins.jstl.core.Remove;

import com.admininfo.model.AdmininfoService;
import com.admininfo.model.AdmininfoVO;

@WebServlet("/admininfo/controller/admininfoServlet.do")
public class AdmininfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");

		String action = req.getParameter("action");

		if ("admin_login".equals(action)) { // 來自adminLogin.jsp的請求
			Map<String, String> errorMsgs = new LinkedHashMap<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);
			AdmininfoService admininfoSvc = new AdmininfoService();
			AdmininfoVO admininfoVO = new AdmininfoVO();

			List<AdmininfoVO> adminLogin = admininfoSvc.getAll();
//			List<AdmininfoVO> loginStatus = admininfoSvc.getUsingAdmin("已停權");

			try {
				/*********************** 檢查是否有輸入管理員的信箱 ***********************/
				String manmail = req.getParameter("manmail");
				if (manmail == null || manmail.trim().length() == 0) {
					errorMsgs.put("manmail_err", "請輸入信箱");
				}
				
				/*********************** 檢查是否有這個管理員信箱 ***********************/
				boolean a = false;
				String tem = null;
				for (AdmininfoVO admininfoVO_list : adminLogin) {
					if ((manmail.trim()).equals(admininfoVO_list.getManmail())) {
						a = true;
						tem = admininfoVO_list.getManpassword();
					}
				}
				if (!a) {
					errorMsgs.put("manmail_err", "此信箱不存在, 請重新輸入");
				}
				
				/*********************** 檢查是否有輸入管理員的密碼 ***********************/
				String manpassword = req.getParameter("manpassword");
				if (manpassword == null || manpassword.trim().length() == 0) {
					errorMsgs.put("manpassword_err", "請輸入密碼");
				}
				boolean b = false;
				if ((manpassword.trim()).equals(tem)) {
					b = true;
				}
				if (!b) {
					errorMsgs.put("manpassword_err", "密碼錯誤, 請重新輸入");
				}
				
				/*********************** 如果都沒有輸入就回到登入頁面 ***********************/
				if (!errorMsgs.isEmpty()) {
					admininfoVO.setManmail(manmail);
//					admininfoVO.setManpassword(manpassword);
					req.setAttribute("admininfoVO", admininfoVO);
					String url = "/back-end/adminLogin.jsp";
					RequestDispatcher successView = req.getRequestDispatcher(url);
					successView.forward(req, res);
					return;
				}

				HttpSession session = req.getSession();
				session.setAttribute("admininfoVO", admininfoVO); // 資料庫取出的admininfoVO物件,存入req
				String url = "/back-end/custinfo/listAllCustinfo.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneAdmininfo.jsp
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.put("errorMsgs", "錯誤" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/adminLogin.jsp");
				failureView.forward(req, res);
			}
		}

		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String str = req.getParameter("manid");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入管理員編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/admininfo/listAllAdmininfo.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				String manid = null;
				try {
					manid = new String(str);
				} catch (Exception e) {
					errorMsgs.add("管理員編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/admininfo/listAllAdmininfo.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 2.開始查詢資料 *****************************************/
				AdmininfoService admininfoSvc = new AdmininfoService();
				AdmininfoVO admininfoVO = admininfoSvc.getOneAdmin(manid);
				if (admininfoVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/admininfo/listAllAdmininfo.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("admininfoVO", admininfoVO); // 資料庫取出的admininfoVO物件,存入req
				String url = "/back-end/admininfo/listOneAdmininfo.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneAdmininfo.jsp
				req.removeAttribute("errorMsgs");
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/admininfo/listAllAdmininfo.jsp");
				failureView.forward(req, res);
			}
		}

		if ("getOne_For_Update".equals(action)) { // 來自listAllAdmininfo.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				String manid = new String(req.getParameter("manid"));

				/*************************** 2.開始查詢資料 ****************************************/
				AdmininfoService admininfoSvc = new AdmininfoService();
				AdmininfoVO admininfoVO = admininfoSvc.getOneAdmin(manid);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("admininfoVO", admininfoVO); // 資料庫取出的admininfoVO物件,存入req
				String url = "/back-end/admininfo/update_admininfo_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交update_admininfo_input.jsp
				req.removeAttribute("errorMsgs");
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/admininfo/listAllAdmininfo.jsp");
				failureView.forward(req, res);
			}
		}

		if ("update".equals(action)) { // 來自update_admininfo_input.jsp的請求
			Map<String, String> errorMsgs = new LinkedHashMap<String, String>();
//			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			AdmininfoService admininfoService = new AdmininfoService();
			List<AdmininfoVO> admininfoVO_list = admininfoService.getAll();

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String manid = new String(req.getParameter("manid").trim());
				// 檢查修改管理員的帳號格式是否正確, 並擋下已經存在的帳號
				String manname = req.getParameter("manname");
//				String mannameReg = "^[(a-zA-Z0-9_)]{5,20}$";
//				if (manname == null || manname.trim().length() == 0) {
//					errorMsgs.put("mannamenull", "請輸入帳號");
//				} else if (!manname.trim().matches(mannameReg)) { // 以下練習正則(規)表示式(regular-expression)
//					errorMsgs.put("mannameerr","只能是英文字母、數字和_, 長度必需在5到20之間");
//				}

				String manpassword = req.getParameter("manpassword");
				String manpasswordReg = "^[(a-zA-Z0-9)]{6,20}$";
				if (manpassword == null || manpassword.trim().length() == 0) {
					errorMsgs.put("manpwdnull", "請輸入密碼");
				} else if (!manpassword.trim().matches(manpasswordReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.put("mannpwderr", "只能英文字母、數字, 長度必需在6到20之間");
				}
				// 檢查修改管理員的信箱格式是否正確, 並擋下已經存在的信箱
				String manmail = req.getParameter("manmail");
				String manmailReg = "^\\w{1,63}@[a-zA-Z0-9]{2,63}\\.[a-zA-Z]{2,63}(\\.[a-zA-Z]{2,63})?$";
				if (manmail == null || manmail.trim().length() == 0) {
					errorMsgs.put("manmailnull", "請輸入信箱");
				} else if (!manmail.trim().matches(manmailReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.put("manmailerr", "信箱格式錯誤");
				}

				String manrealname = req.getParameter("manrealname");
				String manrealnameReg = "^[(\u4e00-\u9fa5)(a-zA-Z)]{2,24}$";
				if (manrealname == null || manrealname.trim().length() == 0) {
					errorMsgs.put("realnamenull", "管理員姓名請勿空白");
				} else if (!manrealname.trim().matches(manrealnameReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.put("realnameerr","只能是中英文字母, 中文上限6個字, 英文上限20個字");
				}

				String mantel = req.getParameter("mantel");
				String mantelReg = "[0-9]{4}[0-9]{6}";
				if (mantel == null || mantel.trim().length() == 0) {
					errorMsgs.put("mantelnull", "管理員電話請勿空白");
				} else if (!mantel.trim().matches(mantelReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.put("mantelerr", "只可輸入手機號碼");
				}

				String manstatus = req.getParameter("manstatus");

				String manpurview = req.getParameter("manpurview");

				AdmininfoVO admininfoVO = new AdmininfoVO();
				admininfoVO.setManid(manid);
				admininfoVO.setManname(manname);
				admininfoVO.setManpassword(manpassword);
				admininfoVO.setManmail(manmail);
				admininfoVO.setManrealname(manrealname);
				admininfoVO.setMantel(mantel);
				admininfoVO.setManstatus(manstatus);
				admininfoVO.setManpurview(manpurview);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("admininfoVO", admininfoVO); // 含有輸入格式錯誤的admininfoVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/admininfo/update_admininfo_input.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}

				/*************************** 2.開始修改資料 *****************************************/
				AdmininfoService admininfoSvc = new AdmininfoService();
				admininfoVO = admininfoSvc.updateAdmininfo(manid, manname, manpassword, manmail, manrealname, mantel,
						manstatus, manpurview);

				/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
				req.setAttribute("admininfoVO", admininfoVO); // 資料庫update成功後,正確的admininfoVO物件,存入req
				String url = "/back-end/admininfo/listOneAdmininfo.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneAdmininfo.jsp
				req.removeAttribute("errorMsgs");
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.put("errorMsgs","新增資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/admininfo/update_admininfo_input.jsp");
				failureView.forward(req, res);
			}
		}

//	======================================新增管理員====================================

		if ("insert".equals(action)) { // 來自addAdmininfo.jsp的請求

			Map<String, String> errorMsgs = new LinkedHashMap<String, String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			AdmininfoVO admininfoVO = new AdmininfoVO();
			AdmininfoService admininfoSvc = new AdmininfoService();
			List<AdmininfoVO> admininfoVO_list = admininfoSvc.getAll();
			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				// 檢查新增管理員的帳號格式是否正確, 並擋下已經存在的帳號
				String manname = req.getParameter("manname");
				
				// 檢查新增管理員的密碼格式是否正確
				String manpassword = req.getParameter("manpassword");
				String manpasswordReg = "^[(a-zA-Z0-9)]{6,20}$";
				if (manpassword == null || manpassword.trim().length() == 0) {
					errorMsgs.put("manpwdnull", "請輸入密碼");
				} else if (!manpassword.trim().matches(manpasswordReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.put("mannpwderr", "只能英文字母、數字, 長度必需在6到20之間");
				}
				// 檢查新增管理員的信箱格式是否正確, 並擋下已存在的信箱
				String manmail = req.getParameter("manmail");
				String manmailReg = "^\\w{1,63}@[a-zA-Z0-9]{5,30}\\.[a-zA-Z]{2,63}(\\.[a-zA-Z]{2,63})?$";
				if (manmail == null || manmail.trim().length() == 0) {
					errorMsgs.put("manmailnull", "請輸入信箱");
				} else if (!manmail.trim().matches(manmailReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.put("manmailerr", "信箱格式錯誤");
				} else {
					for (AdmininfoVO list : admininfoVO_list) {
						if (manmail.trim().equals(list.getManmail())) {
							errorMsgs.put("manmailexist","此管理員信箱已被申請, 請重新輸入");
						}
					}
				}
				// 檢查新增管理員的姓名格式是否正確
				String manrealname = req.getParameter("manrealname");
				String manrealnameReg = "^[(\u4e00-\u9fa5)(a-zA-Z)]{2,10}$";
				if (manrealname == null || manrealname.trim().length() == 0) {
					errorMsgs.put("realnamenull", "管理員姓名請勿空白");
				} else if (!manrealname.trim().matches(manrealnameReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.put("realnameerr","只能是中英文字母, 中文上限6個字, 英文上限20個字");
				}
				// 檢查新增管理員的手機格式是否正確
				String mantel = req.getParameter("mantel");
				String mantelReg = "[0-9]{4}[0-9]{6}";
				if (mantel == null || mantel.trim().length() == 0) {
					errorMsgs.put("mantelnull", "管理員電話請勿空白");
				} else if (!mantel.trim().matches(mantelReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.put("mantelerr", "只可輸入手機號碼");
				}

				String manstatus = req.getParameter("manstatus");

				String manpurview = req.getParameter("manpurview");

				admininfoVO.setManname(manname);
				admininfoVO.setManpassword(manpassword);
				admininfoVO.setManmail(manmail);
				admininfoVO.setManrealname(manrealname);
				admininfoVO.setMantel(mantel);
				admininfoVO.setManstatus(manstatus);
				admininfoVO.setManpurview(manpurview);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("admininfoVO", admininfoVO); // 含有輸入格式錯誤的admininfoVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/admininfo/addAdmininfo.jsp");

					failureView.forward(req, res);
					return; // 程式中斷
				}

				/*************************** 2.開始新增資料 *****************************************/
				admininfoSvc = new AdmininfoService();
				admininfoVO = admininfoSvc.addAdmininfo(manname, manpassword, manmail, manrealname, mantel, manstatus,
						manpurview);

				/*************************** 3.新增完成,準備轉交(Send the Success view) *************/
				req.setAttribute("admininfoVO", admininfoVO); // 資料庫update成功後,正確的admininfoVO物件,存入req
				String url = "/back-end/admininfo/listAllAdmininfo.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後,轉交listAllAdmininfo.jsp
				req.removeAttribute("errorMsgs");
				successView.forward(req, res);
				

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.put("errorMsgs","新增資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/admininfo/addAdmininfo.jsp");
				failureView.forward(req, res);
			}
		}
		if ("delete".equals(action)) { // listAllAdmininfo.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ***************************************/
				String manid = new String(req.getParameter("manid"));
				/*************************** 2.開始刪除資料 ***************************************/
				AdmininfoService admininfoSvc = new AdmininfoService();
				admininfoSvc.deleteAdmininfo(manid);
				/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
				String url = "/front-end/admininfo/listAllAdmininfo.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/admininfo/listAllAdmininfo.jsp");
				failureView.forward(req, res);
			}
		}

	}

}
