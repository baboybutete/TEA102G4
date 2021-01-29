package com.addetail.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;

import com.addetail.model.*;
import com.menu.model.MenuService;
import com.menu.model.MenuVO;

@WebServlet("/front-end/addetail/addetail.do")
@MultipartConfig //上傳要素之一
public class AddetailServlet extends HttpServlet {
	private AddetailService addetailService;
	
	public static void readPicture(byte[] bytes) throws IOException {
		FileOutputStream fos = new FileOutputStream("Output/2.png");
		fos.write(bytes);
		fos.flush();
		fos.close();
	}
	
	public void init() {
		addetailService = new AddetailService();
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求
	
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String str = req.getParameter("adid");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入廣告/推播編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/addetail/listAllAddetail.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				String adid = null;
				try {
					adid = new String(str);
				} catch (Exception e) {
					errorMsgs.add("廣告/推播編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/addetail/listAllAddetail.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}
				/***************************2.開始查詢資料*****************************************/
				AddetailService addetailSvc = new AddetailService(); 
				AddetailVO addetailVO = addetailSvc.getOneAddetail(adid);
				if (addetailVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/addetail/listAllAddetail.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}
				
				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("addetailVO", addetailVO); // 資料庫取出的addetailVO物件,存入req
				String url = "/back-end/addetail/listOneAddetail.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneAddetail.jsp
				req.removeAttribute("errorMsgs");
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/addetail/listAllAddetail.jsp");
				failureView.forward(req, res);
			}
		}

if ("getOne_For_Update".equals(action)) { // 來自listAllAddetail.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				String adid = new String(req.getParameter("adid"));

				/*************************** 2.開始查詢資料 ****************************************/
				AddetailService addetailSvc = new AddetailService();
				AddetailVO addetailVO = addetailSvc.getOneAddetail(adid);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("addetailVO", addetailVO); // 資料庫取出的addetailVO物件,存入req
				String url = "/back-end/addetail/update_addetail_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_addetail_input.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/addetail/listAllAddetail.jsp");
				failureView.forward(req, res);
			}
		}

if ("update".equals(action)) { // 來自update_addetail_input.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			AddetailService addetailSvc = new AddetailService();
			List<AddetailVO> addetailVO_list = addetailSvc.getAll();

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
String adid = new String(req.getParameter("adid").trim());
				
// 廣告/推播標題可任意輸入
String adtitle = req.getParameter("adtitle");
				if (adtitle == null || adtitle.trim().length() == 0) {
					errorMsgs.add("標題請勿空白");
				} 
// 廣告/推播內容可任意輸入
String adcontent = req.getParameter("adcontent").trim();
				if (adcontent == null || adcontent.trim().length() == 0) {
					errorMsgs.add("內容請勿空白");
				}
Part part = req.getPart("adimg");		
				byte[] adimg = null;			
				if (part == null || part.getSize() == 0) {
					errorMsgs.add("請上傳餐廳照片");
				} else {
					adimg = getImgByte(part);
				}


String adtype = req.getParameter("adtype");
				if (adtype == null ) {
					errorMsgs.add("請選擇類型");
				}
// 下拉式選單, 不須格式驗證
String reviewstatus = req.getParameter("reviewstatus").trim();
				if (reviewstatus == null || reviewstatus.trim().length() == 0) {
					errorMsgs.add("審核狀態請勿空白");
				} 
			
SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

String offshelftimeString = req.getParameter("offshelftime");
java.sql.Timestamp offshelftime = new Timestamp(new java.util.Date().getTime());// 若發生錯誤訊息則用現在時間替代
				if (offshelftimeString == null || offshelftimeString.isEmpty()) {
					errorMsgs.add("請輸入下架日期");
				}
				try {
					java.util.Date d = formatter.parse(offshelftimeString);
					long date = d.getTime();
					offshelftime = new java.sql.Timestamp(date);
				} catch (ParseException pe) {
					errorMsgs.add("請輸入符合日期格式之字串例如:2020-12-20 10:00:00");
				}			

String onshelftimeString = req.getParameter("onshelftime");
Timestamp onshelftime = new Timestamp(new java.util.Date().getTime());
				if (onshelftimeString == null || onshelftimeString.isEmpty()) {
					errorMsgs.add("請輸入上架日期");
				}
				try {
					java.util.Date d = formatter.parse(onshelftimeString);
					long date = d.getTime();
					onshelftime = new java.sql.Timestamp(date);
				} catch (ParseException pe) {
					errorMsgs.add("請輸入符合日期格式之字串例如:2020-12-20 10:00:00");
				}

Timestamp addate = new Timestamp(System.currentTimeMillis());
				
String resid = req.getParameter("resid");
				
				AddetailVO addetailVO = new AddetailVO();
				addetailVO.setAdid(adid);
				addetailVO.setAdtitle(adtitle);
				addetailVO.setAdcontent(adcontent);
				addetailVO.setAdimg(adimg);
				addetailVO.setAddate(addate);
				addetailVO.setAdtype(adtype);
				addetailVO.setReviewstatus(reviewstatus);
				addetailVO.setOnshelftime(onshelftime);
				addetailVO.setOffshelftime(offshelftime);
				addetailVO.setResid(resid);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("addetailVO", addetailVO); // 含有輸入格式錯誤的addevo物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/addetail/update_addetail_input.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}

				/*************************** 2.開始修改資料 *****************************************/
				AddetailService addetailSvc1 = new AddetailService();
				addetailVO = addetailSvc1.updateAddetail(adid, adtitle, adcontent, adimg, addate, adtype, 
						reviewstatus, onshelftime, offshelftime,resid);

				/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
				req.setAttribute("addetailVO", addetailVO);
				String url = "/back-end/addetail/listOneAddetail.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneAddetail.jsp
				req.removeAttribute("errorMsgs");
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/addetail/update_addetail_input.jsp");
				failureView.forward(req, res);
			}
		}
if ("getOne_For_Insert".equals(action)) {
	List<String> errorMsg = new LinkedList<String>();
	req.setAttribute("errorMsg", errorMsg);

	try {
		/* 取得想要更改的resid */
		String resid = req.getParameter("resid");
		/* 查詢a資料 */
		AddetailVO addetailVO = addetailService.addOneAddetail(resid);
		req.setAttribute("addetailVO", addetailVO);

		/* 將資料轉交出去 */
		RequestDispatcher successView = req.getRequestDispatcher("/front-end/res/addetail/addAddetail.jsp");
		successView.forward(req, res);
		return;
		
	} catch (Exception e) {
		errorMsg.add("無法取得要修改的資料:" + e.getMessage());
		RequestDispatcher failureView = req.getRequestDispatcher("/front-end/res/addetail/listResAddetailForCentre.jsp");
		failureView.forward(req, res);
	}

}	



if (action != null && "insert".equals(action)) { // 來自update_emp_input.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			AddetailService addetailSvc = new AddetailService();
			List<AddetailVO> addetailVO_list = addetailSvc.getAll();
			AddetailVO addetailVO = new AddetailVO();
			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/

String adtitle = req.getParameter("adtitle");
				String adtitleReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9)]{5,60}$";
				if (adtitle == null || adtitle.trim().length() == 0) {
					errorMsgs.add("標題請勿空白");
				} else if(!adtitle.trim().matches(adtitleReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("標題: 只能是中、英文字母、數字,");
				}

String adcontent = req.getParameter("adcontent").trim();
				if (adcontent == null || adcontent.trim().length() == 0) {
					errorMsgs.add("內容請勿空白");
				} 
Part part = req.getPart("adimg");			
				byte[] adimg = null;				
				if (part == null || part.getSize() == 0) {
					errorMsgs.add("請上傳圖片");
				} else {
					adimg = getImgByte(part);
				}

String adtype = req.getParameter("adtype");
//				if (adtype == null ) {
//					errorMsgs.add("類型請勿空白");
//				} 
				
				
String reviewstatus = req.getParameter("reviewstatus");

SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

String onshelftimeString = req.getParameter("onshelftime");

Timestamp onshelftime = new Timestamp(new java.util.Date().getTime());
					if(onshelftimeString == null || onshelftimeString.isEmpty()) {
						errorMsgs.add("請輸入上架日期");
					}
					try {
						java.util.Date d = formatter.parse(onshelftimeString);
						long date = d.getTime();
						onshelftime = new java.sql.Timestamp(date);
					} catch (ParseException pe) {
						errorMsgs.add("請輸入符合日期格式之字串例如:2020-12-20 10:00:00");
					}
	
String offshelftimeString = req.getParameter("offshelftime");

Timestamp offshelftime = new Timestamp(new java.util.Date().getTime());
				if(offshelftimeString == null || offshelftimeString.isEmpty()) {
					errorMsgs.add("請輸入下架日期");
				}
				try {
					java.util.Date d = formatter.parse(offshelftimeString);
					long date = d.getTime();
					offshelftime = new java.sql.Timestamp(date);
				} catch (ParseException pe) {
					errorMsgs.add("請輸入符合日期格式之字串例如:2020-12-20 10:00:00");
				}

Timestamp addate = new Timestamp(System.currentTimeMillis());
				
String resid = req.getParameter("resid");
				
String status = null;
			if (adtype.equals("廣告")) {
				status = "審核中";
			} /*else if (adtype.equals("推播")) {
				status = "不須審核";
			}*/
			
				addetailVO.setAdtitle(adtitle);
				addetailVO.setAdcontent(adcontent);
				addetailVO.setAdimg(adimg);
				addetailVO.setAddate(addate);
				addetailVO.setAdtype(adtype);
				addetailVO.setReviewstatus(status); //根據設定的類型來決定審核狀態, ex:廣告就設定為審核中, 推播送出為不須審核
				addetailVO.setOnshelftime(onshelftime);
				addetailVO.setOffshelftime(offshelftime);
				addetailVO.setResid(resid);
				

				// Send the use back to the form, if there were errors
				//有錯誤就回到addAddetail.jsp
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("addetailVO", addetailVO); // 含有輸入格式錯誤的addetailVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/res/addetail/addAddetail.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}
				
				/*************************** 2.開始新增資料 *****************************************/
				
				addetailVO = addetailSvc.addAddetail(adtitle, adcontent, adimg, addate, adtype, status, onshelftime, offshelftime, resid);

				/*************************** 3.新增完成,準備轉交(Send the Success view) *************/		
				req.setAttribute("addetailVO", addetailVO);
				String url = "/front-end/res/addetail/listResAddetailForCentre.jsp";
				
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後,轉交listAllAddetail.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/res/addetail/addAddetail.jsp");
				failureView.forward(req, res);
			}

		}
		
if ("delete".equals(action)) {
				List<String> errorMsgs = new LinkedList<String>();
				// Store this set in the request scope, in case we need to
				// send the ErrorPage view.
				req.setAttribute("errorMsgs", errorMsgs);
				
				try {
					/*************************** 1.接收請求參數 ***************************************/
					String adid = new String(req.getParameter("adid"));
					/*************************** 2.開始刪除資料 ***************************************/
					AddetailService addetailSvc = new AddetailService();
					addetailSvc.deleteAddetail(adid);
					/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
					String url = "/front-end/addetail/listAllAddetail.jsp";
					RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
					successView.forward(req, res);
					/*************************** 其他可能的錯誤處理 **********************************/
				} catch (Exception e){
					errorMsgs.add("刪除資料失敗:" + e.getMessage());
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/addetail/listAllAddetail.jsp");
					failureView.forward(req, res);
				}
			}

if("update_By_BackEnd".equals(action)) {
	
				    /***************************1.接收請求參數***************************************/
					String adid = req.getParameter("adid");
					String reviewstatus = req.getParameter("reviewstatus");
					AddetailService addetailSvc = new AddetailService();
					AddetailVO vo = addetailSvc.getOneAddetail(adid);
					vo.setReviewstatus(reviewstatus);
					addetailSvc.updateAddetail(adid, vo.getAdtitle(), vo.getAdcontent(), vo.getAdimg(), vo.getAddate(), vo.getAdtype(), reviewstatus, vo.getOnshelftime(), vo.getOffshelftime(), vo.getResid());
					String url = "/back-end/addetail/listAllAddetail.jsp";
					RequestDispatcher successView = req.getRequestDispatcher(url);
					successView.forward(req, res);
				}


if ("getOneResMenu_For_Display".equals(action)) { 

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
					.getRequestDispatcher("/front-end/res/menu/select_page.jsp");
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
					.getRequestDispatcher("/front-end/res/menu/select_page.jsp");
			failureView.forward(req, res);
			return;
		}
		
		/***************************2.開始查詢資料*****************************************/
		AddetailService addetailSvc = new AddetailService();
		List<AddetailVO> addetailVO = addetailSvc.getOneResAddetail(resid);
		if (addetailVO == null) {
			errorMsgs.add("查無資料");
		}
		if (!errorMsgs.isEmpty()) {
			RequestDispatcher failureView = req
					.getRequestDispatcher("/front-end/res/menu/select_page.jsp");
			failureView.forward(req, res);
			return;
		}
		
		/***************************3.查詢完成,準備轉交(Send the Success view)*************/
//		req.setAttribute("menuVO", menuVO); 
		req.getSession().setAttribute("addetailVO", addetailVO); 
		String url = "/front-end/res/menu/listResMenuForCentre.jsp";
		RequestDispatcher successView = req.getRequestDispatcher(url); 
		successView.forward(req, res);

		/***************************其他可能的錯誤處理*************************************/
	} catch (Exception e) {
		errorMsgs.add("無法取得資料:" + e.getMessage());
		RequestDispatcher failureView = req
				.getRequestDispatcher("/front-end/res/menu/select_page.jsp");
		failureView.forward(req, res);
	}
}

			}
	
	public byte[] getImgByte(Part partImg) {
		InputStream in;
		ByteArrayOutputStream byteAppender = null;
		try {
			in = partImg.getInputStream();
			byteAppender = new ByteArrayOutputStream();
			byte[] imgSection = new byte[1024 * 1000];
			while (in.read(imgSection) != -1) {
				byteAppender.write(imgSection);
			}
		} catch (IOException e) {

			e.printStackTrace();
		}
		return byteAppender.toByteArray();
	}
}
