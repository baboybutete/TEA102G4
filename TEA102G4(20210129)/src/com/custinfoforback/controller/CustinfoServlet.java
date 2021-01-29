package com.custinfoforback.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.tomcat.util.buf.Utf8Decoder;

import com.addetail.model.AddetailService;
import com.addetail.model.AddetailVO;
import com.custinfo.model.CustinfoService;
import com.custinfo.model.CustinfoVO;
import com.resempmanager.model.ResempmanagerService;
import com.resempmanager.model.ResempmanagerVO;


@WebServlet("/back-end/custinfo/custinfo.do")
@MultipartConfig
public class CustinfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String str = req.getParameter("custid");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入會員編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/custinfo/listAllCustinfo.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				String custid = null;
				if (str.matches("^[C][0-9]{5}$")) {
					custid = new String(str);
				} else {
					errorMsgs.add("會員編號格式不正確");
				}
//				 Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/custinfo/listAllCustinfo.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}
				/*************************** 2.開始查詢資料 *****************************************/
				CustinfoService custinfoSvc = new CustinfoService();
				CustinfoVO custinfoVO = custinfoSvc.getOneCust(custid);
				if (custinfoVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/custinfo/listAllCustinfo.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("custinfoVO", custinfoVO); // 資料庫取出的VO物件,存入req
				String url = "/back-end/custinfo/listOneCustinfo.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneCustinfo.jsp
				req.removeAttribute("errorMsgs");
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/custinfo/listAllCustinfo.jsp");
				failureView.forward(req, res);
			}
		}

if ("getOne_For_Update".equals(action)) { // 來自listAllEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				String custid = req.getParameter("custid").trim();

				/*************************** 2.開始查詢資料 ****************************************/
				CustinfoService custinfoSvc = new CustinfoService();
				CustinfoVO custinfoVO = custinfoSvc.getOneCust(custid);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("custinfoVO", custinfoVO); // 資料庫取出的custinfoVO物件,存入req
				String url = "/back-end/custinfo/update_custinfo_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_custinfo_input.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/custinfo/listAllCustinfo.jsp");
				failureView.forward(req, res);
			}
		}

if ("update".equals(action)) { // 來自update_emp_input.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			CustinfoService custinfoSvc = new CustinfoService();

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
String custid = req.getParameter("custid");

String custname = req.getParameter("custname").trim();
				if (custname == null || custname.trim().length() == 0) {
					errorMsgs.add("姓名請勿空白");
				}
String custstatus = req.getParameter("custstatus").trim();

String custaccount = req.getParameter("custaccount").trim();
				if (custaccount == null || custaccount.trim().length() == 0) {
					errorMsgs.add("帳號請勿空白");
				}

String custpassword = req.getParameter("custpassword").trim();
				if (custpassword == null || custpassword.trim().length() == 0) {
					errorMsgs.add("密碼請勿空白");
				}
String custtel = req.getParameter("custtel").trim();
				if (custtel == null || custtel.trim().length() == 0) {
					errorMsgs.add("電話請勿空白");
				}

				
				
Part part = req.getPart("custpicture");
				byte[] custpicture = null;
				if (part == null || part.getSize() == 0) {
					errorMsgs.add("請上傳圖片");
				} else {
					custpicture = getCustpicture(part);
				}


				CustinfoVO custinfoVO = new CustinfoVO();
				custinfoVO.setCustid(custid);
				custinfoVO.setCustname(custname);
				custinfoVO.setCustaccount(custaccount);
				custinfoVO.setCustpassword(custpassword);
				custinfoVO.setCusttel(custtel);
				custinfoVO.setCuststatus(custstatus);
				custinfoVO.setCustpicture(custpicture);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("custinfoVO", custinfoVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/custinfo/update_custinfo_input.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}

				/*************************** 2.開始修改資料 *****************************************/
//				CustinfoService custinfoSvc = new CustinfoService();
				custinfoVO = custinfoSvc.updateCustinfo(custid, custname, custtel, custpassword, custstatus,
						custaccount, custpicture);

				/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
				req.setAttribute("custinfoVO", custinfoVO); // 資料庫update成功後,正確的的empVO物件,存入req
				String url = "/back-end/custinfo/listOneCustinfo.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				req.removeAttribute("errorMsgs");
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/custinfo/update_custinfo_input.jsp");
				failureView.forward(req, res);
			}
		}

if ("insert".equals(action)) { // 來自addEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			CustinfoVO custinfoVO = new CustinfoVO();
			CustinfoService custinfoSvc = new CustinfoService();
			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				String custname = req.getParameter("custname").trim();
				if (custname == null || custname.trim().length() == 0) {
					errorMsgs.add("姓名請勿空白");
				}
				String custstatus = req.getParameter("custstatus");

				String custaccount = req.getParameter("custaccount").trim();
				if (custaccount == null || custaccount.trim().length() == 0) {
					errorMsgs.add("帳號請勿空白");
				}

				String custpassword = req.getParameter("custpassword").trim();
				if (custpassword == null || custpassword.trim().length() == 0) {
					errorMsgs.add("密碼請勿空白");
				}

				String custtel = req.getParameter("custtel").trim();
				if (custtel == null || custtel.trim().length() == 0) {
					errorMsgs.add("電話請勿空白");
				}

				byte[] custpicture = null;
				
				Part part = req.getPart("custpicture");
				if (part == null || part.getSize() == 0) {
					errorMsgs.add("請上傳圖片");
				} else {
					custpicture = getCustpicture(part);
				}

				
				custinfoVO.setCustname(custname);
				custinfoVO.setCustaccount(custaccount);
				custinfoVO.setCustpassword(custpassword);
				custinfoVO.setCusttel(custtel);
				custinfoVO.setCuststatus(custstatus);
				custinfoVO.setCustpicture(custpicture);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("custinfoVO", custinfoVO); // 含有輸入格式錯誤的custinfoVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/custinfo/addCustinfo.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始新增資料 ***************************************/
				java.util.Date date = new java.util.Date();
				Timestamp registrationtime = new Timestamp(date.getTime());
				//		CustinfoService custinfoSvc = new CustinfoService();
				custinfoVO = custinfoSvc.addCustinfo(custname, custtel, custpassword, custstatus, registrationtime,
						custaccount, custpicture);

				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String url = "/back-end/custinfo/listAllCustinfo.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/custinfo/addCustinfo.jsp");
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
				String custid = req.getParameter("custid");
				/*************************** 2.開始刪除資料 ***************************************/
				CustinfoService custinfoSvc = new CustinfoService();
				custinfoSvc.deleteCustinfo(custid);
				/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
				String url = "/back-end/custinfo/listAllCustinfo.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/custinfo/listAllCustinfo.jsp.jsp");
				failureView.forward(req, res);
			}
		}

		if("update_By_BackEnd".equals(action)) {
	
			    /***************************1.接收請求參數***************************************/
				String custid = req.getParameter("custid");
				String custstatus = req.getParameter("custstatus");
				CustinfoService custinfoSvc = new CustinfoService();
				CustinfoVO vo = custinfoSvc.getOneCust(custid);
				vo.setCuststatus(custstatus);
				custinfoSvc.updateCustinfo(custid, vo.getCustname(), vo.getCusttel(), vo.getCustpassword(), custstatus, vo.getCustaccount(), vo.getCustpicture());
				String url = "/back-end/custinfo/listAllCustinfo.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			}
		}
	
	
	

	public byte[] getCustpicture(Part part) {
		InputStream in;
		ByteArrayOutputStream bos = null;
		try {
			in = part.getInputStream();
			bos = new ByteArrayOutputStream();
			byte[] b = new byte[1024 * 1000];
			while (in.read(b) != -1) {
				bos.write(b);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bos.toByteArray();

	}
}
