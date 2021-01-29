package com.resinfoforback.controller;

import java.io.*;
import java.sql.Timestamp;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import com.resinfo.model.ResInfoService;
import com.resinfo.model.ResInfoVO;
@WebServlet("/back-end/resinfo/resinfo.do")
@MultipartConfig
public class ResInfoServlet extends HttpServlet{
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
				String str = req.getParameter("resid");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入餐廳編號");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/res/resinfo/select_page.jsp");
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
							.getRequestDispatcher("/front-end/res/resinfo/select_page.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始查詢資料*****************************************/
				ResInfoService resinfosSvc = new ResInfoService();
				ResInfoVO resInfoVO = resinfosSvc.getOneResInfo(resid);
				if (resInfoVO == null) {
					errorMsgs.add("查無資料");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/res/resinfo/select_page.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
//				req.setAttribute("resInfoVO", resInfoVO); 
			req.getSession().setAttribute("resInfoVO", resInfoVO);
				String url = "/front-end/res/resinfo/listOneResInfoForCentre.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); 
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/res/resinfo/select_page.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("getOne_For_Display_By_Client".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String resid = req.getParameter("resid");
				
				
				
				/***************************2.開始查詢資料*****************************************/
				ResInfoService resinfosSvc = new ResInfoService();
				ResInfoVO resInfoVO = resinfosSvc.getOneResInfo(resid);
				
			
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
//				req.setAttribute("resInfoVO", resInfoVO); 
				req.getSession().setAttribute("resInfoVO", resInfoVO);
String url = "/front-end/cust/resinfo/index_res.jsp";

				RequestDispatcher successView = req.getRequestDispatcher(url); 
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/res/resinfo/select_page.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		
		
		if ("getOne_For_Update".equals(action)) { 

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數****************************************/
				String resid = req.getParameter("resid");
				
				/***************************2.開始查詢資料****************************************/
				ResInfoService resinfosSvc = new ResInfoService();
				ResInfoVO resInfoVO = resinfosSvc.getOneResInfo(resid);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("resInfoVO", resInfoVO);         
				String url = "/front-end/res/resinfo/updateResinfoForCentre.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/res/resinfo/listAllResInfo.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("update".equals(action)) { 
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String resid = req.getParameter("resid").trim();
				String resname = req.getParameter("resname");
				String resnameReg = "^[(\\u4e00-\\u9fa5)(a-zA-Z0-9_)]{1,10}$";
				if (resname == null || resname.trim().length() == 0) {
					errorMsgs.add("餐廳名稱: 請勿空白");
				} else if(!resname.trim().matches(resnameReg)) { 
					errorMsgs.add("餐廳名稱: 只能是中、英文字母、數字和_ , 且長度必需在1到10之間");
	            }
				
				String resaddid = req.getParameter("resaddid");
				String resaddidReg = "^[(\\u4e00-\\u9fa5)(a-zA-Z0-9_)]{6,30}$";
				if (resaddid == null || resaddid.trim().length() == 0) {
					errorMsgs.add("餐廳地址: 請勿空白");
				} else if(!resaddid.trim().matches(resaddidReg)) { 
					errorMsgs.add("餐廳地址: 只能是中、英文字母、數字和_ , 且長度必需在6到30之間");
	            }
				
				Part partImg = req.getPart("resimg");
				HttpSession session = req.getSession();
				byte[] resimg = null;
				if (partImg.getSize() != 0) {
					resimg = getImgByte(partImg);
					session.setAttribute("resimg", resimg);
				} else if (session.getAttribute("resimg") != null) {
					resimg =(byte[])session.getAttribute("resimg");
				} else {
					errorMsgs.add("請上傳餐廳照片");
				}
					
				String barrierfree = req.getParameter("barrierfree");
				String barrierfreeReg = "^[(\\u6709,\\u7121)]{1}$";
				if (barrierfree == null || barrierfree.trim().length() == 0) {
					errorMsgs.add("無障礙空間: 請勿空白");
				} else if(!barrierfree.trim().matches(barrierfreeReg)) { 
					errorMsgs.add("無障礙空間: 只能是'有'或'無'");
	            }
				
				String parentchild = req.getParameter("parentchild");
				String parentchildReg = "^[(\\u6709,\\u7121)]{1}$";
				if (parentchild == null || parentchild.trim().length() == 0) {
					errorMsgs.add("親子空間: 請勿空白");
				} else if(!parentchild.trim().matches(parentchildReg)) { 
					errorMsgs.add("親子空間: 只能是'有'或'無'");
	            }
				
				String traffic = req.getParameter("traffic");
				String trafficReg = "^[(\\u4e00-\\u9fa5)(a-zA-Z0-9_)]{6,30}$";
				if (traffic == null || traffic.trim().length() == 0) {
					errorMsgs.add("餐廳地址: 請勿空白");
				} else if(!traffic.trim().matches(trafficReg)) { 
					errorMsgs.add("餐廳地址: 只能是中、英文字母、數字和_ , 且長度必需在6到30之間");
	            }
				
				String parking = req.getParameter("parking");
				String parkingReg = "^[(\\u4e00-\\u9fa5)(a-zA-Z0-9_)]{3,30}$";
				if (parking == null || parking.trim().length() == 0) {
					errorMsgs.add("停車資訊: 請勿空白");
				} else if(!parking.trim().matches(parkingReg)) { 
					errorMsgs.add("停車資訊: 只能是中、英文字母、數字和_ , 且長度必需在3到30之間");
	            }
				
				String payinfo = req.getParameter("payinfo");
				String payinfoReg = "^[(\\u4e00-\\u9fa5)(a-zA-Z0-9_)]{3,30}$";
				if (payinfo == null || payinfo.trim().length() == 0) {
					errorMsgs.add("付款資訊: 請勿空白");
				} else if(!payinfo.trim().matches(payinfoReg)) { 
					errorMsgs.add("付款資訊: 只能是中、英文字母、數字和_ , 且長度必需在3到30之間");
	            }
				
				String notifcont = req.getParameter("notifcont");
				String notifcontReg = "^[(\\u4e00-\\u9fa5)(a-zA-Z0-9_)]{6,30}$";
				if (notifcont == null || notifcont.trim().length() == 0) {
					errorMsgs.add("餐廳設定的訂位通知: 請勿空白");
				} else if(!notifcont.trim().matches(notifcontReg)) { 
					errorMsgs.add("餐廳設定的訂位通知: 只能是中、英文字母、數字和_ , 且長度必需在6到30之間");
	            }
				
				String resemail = req.getParameter("resemail");
				String resemailReg = "^[(a-zA-Z0-9@.)]{11,30}$";
				if (resemail == null || resemail.trim().length() == 0) {
					errorMsgs.add("餐廳信箱: 請勿空白");
				} else if(!resemail.trim().matches(resemailReg)) { 
					errorMsgs.add("餐廳信箱: 只能是英文字母、數字和@. , 且長度必需在11到30之間");
	            }
				
				String respassid = req.getParameter("respassid");
				String respassidReg = "^[(a-zA-Z0-9_)]{6,30}$";
				if (respassid == null || respassid.trim().length() == 0) {
					errorMsgs.add("密碼: 請勿空白");
				} else if(!respassid.trim().matches(respassidReg)) { 
					errorMsgs.add("密碼: 只能是英文字母、數字和_ , 且長度必需在6到30之間");
	            }
				
				Integer currentwaitingnum = new Integer(req.getParameter("currentwaitingnum").trim());
				
				Integer subtimediff = new Integer(req.getParameter("subtimediff").trim());
				
				Integer waitdistance = new Integer(req.getParameter("waitdistance").trim());
				
				String contact = req.getParameter("contact");
				String contactReg = "^[(\\u4e00-\\u9fa5)(a-zA-Z_)]{2,30}$";
				if (contact == null || contact.trim().length() == 0) {
					errorMsgs.add("聯絡人姓名: 請勿空白");
				} else if(!contact.trim().matches(contactReg)) { 
					errorMsgs.add("聯絡人姓名: 只能是中、英文字母 , 且長度必需在2到30之間");
	            }
				
				String contactphon = req.getParameter("contactphon");
				String contactphonReg = "^[(0-9)]{8,10}$";
				if (contactphon == null || contactphon.trim().length() == 0) {
					errorMsgs.add("聯絡人電話: 請勿空白");
				} else if(!contactphon.trim().matches(contactphonReg)) { 
					errorMsgs.add("聯絡人電話: 只能是數字 , 且長度必需在8到10之間");
	            }
				
				String corrdinate = req.getParameter("corrdinate");
//				String corrdinateReg = "^[(a-zA-Z0-9_)]{1,30}$";
//				if (corrdinate == null || corrdinate.trim().length() == 0) {
//					errorMsgs.add("座位屬性+座標: 請勿空白");
//				} else if(!corrdinate.trim().matches(corrdinateReg)) { 
//					errorMsgs.add("座位屬性+座標: 只能是英文字母、數字和_ , 且長度必需在1到30之間");
//	            }
				
				java.util.Date date = new java.util.Date();
				Timestamp adddate = new Timestamp(date.getTime());
				
				String status = req.getParameter("status");
				
				String lat = req.getParameter("lat");
				
				String lng = req.getParameter("lng");
				
				ResInfoVO resInfoVO = new ResInfoVO();
				resInfoVO.setResid(resid);
				resInfoVO.setResname(resname);
				resInfoVO.setResaddid(resaddid);
				resInfoVO.setResimg(resimg);
				resInfoVO.setBarrierfree(barrierfree);
				resInfoVO.setParentchild(parentchild);
				resInfoVO.setTraffic(traffic);
				resInfoVO.setParking(parking);
				resInfoVO.setPayinfo(payinfo);
				resInfoVO.setNotifcont(notifcont);
				resInfoVO.setResemail(resemail);
				resInfoVO.setRespassid(respassid);
				resInfoVO.setCurrentwaitingnum(currentwaitingnum);
				resInfoVO.setSubtimediff(subtimediff);
				resInfoVO.setWaitdistance(waitdistance);
				resInfoVO.setContact(contact);
				resInfoVO.setContactphon(contactphon);
				resInfoVO.setCorrdinate(corrdinate);
				resInfoVO.setAdddate(adddate);
				resInfoVO.setStatus(status);
				resInfoVO.setLat(lat);
				resInfoVO.setLng(lng);
				
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("resInfoVO", resInfoVO); 
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/res/resinfo/updateResinfoForCentre.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始修改資料*****************************************/
				ResInfoService resinfosSvc = new ResInfoService();
				resInfoVO = resinfosSvc.updateResInfo(resid, resname, resaddid, resimg, barrierfree, parentchild, traffic, parking, payinfo, notifcont, resemail, respassid, currentwaitingnum, subtimediff, waitdistance, contact, contactphon, corrdinate, adddate, status, lat, lng);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("resInfoVO", resInfoVO); 
				String url = "/front-end/res/resinfo/listOneResInfoForCentre.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); 
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/res/resinfo/updateResinfoForCentre.jsp");
				failureView.forward(req, res);
			}
		}

        if ("insert".equals(action)) { 
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				String resname = req.getParameter("resname");
				String resnameReg = "^[(\\u4e00-\\u9fa5)(a-zA-Z0-9_)]{2,10}$";
				if (resname == null || resname.trim().length() == 0) {
					errorMsgs.add("餐廳名稱: 請勿空白");
				} else if(!resname.trim().matches(resnameReg)) { 
					errorMsgs.add("餐廳名稱: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
	            }
				
				String resaddid = req.getParameter("resaddid");
				String resaddidReg = "^[(\\u4e00-\\u9fa5)(a-zA-Z0-9_)]{6,30}$";
				if (resaddid == null || resaddid.trim().length() == 0) {
					errorMsgs.add("餐廳地址: 請勿空白");
				} else if(!resaddid.trim().matches(resaddidReg)) { 
					errorMsgs.add("餐廳地址: 只能是中、英文字母、數字和_ , 且長度必需在6到30之間");
	            }
				
				Part partImg = req.getPart("resimg");
				HttpSession session = req.getSession();
				byte[] resimg = null;
				if (partImg.getSize() != 0) {
					resimg = getImgByte(partImg);
					session.setAttribute("resimg", resimg);
				} else if (session.getAttribute("resimg") != null) {
					resimg =(byte[])session.getAttribute("resimg");
				} else {
					errorMsgs.add("請上傳餐廳照片");
				}
				
				String barrierfree = req.getParameter("barrierfree");
				String barrierfreeReg = "^[(\\u6709,\\u7121)]{1}$";
				if (barrierfree == null || barrierfree.trim().length() == 0) {
					errorMsgs.add("無障礙空間: 請勿空白");
				} else if(!barrierfree.trim().matches(barrierfreeReg)) { 
					errorMsgs.add("無障礙空間: 只能是'有'或'無'");
	            }
				
				String parentchild = req.getParameter("parentchild");
				String parentchildReg = "^[(\\u6709,\\u7121)]{1}$";
				if (parentchild == null || parentchild.trim().length() == 0) {
					errorMsgs.add("親子空間: 請勿空白");
				} else if(!parentchild.trim().matches(parentchildReg)) { 
					errorMsgs.add("親子空間: 只能是'有'或'無'");
	            }
				
				String traffic = req.getParameter("traffic");
				String trafficReg = "^[(\\u4e00-\\u9fa5)(a-zA-Z0-9_)]{6,30}$";
				if (traffic == null || traffic.trim().length() == 0) {
					errorMsgs.add("交通資訊: 請勿空白");
				} else if(!traffic.trim().matches(trafficReg)) { 
					errorMsgs.add("交通資訊: 只能是中、英文字母、數字和_ , 且長度必需在6到30之間");
	            }
				
				String parking = req.getParameter("parking");
				String parkingReg = "^[(\\u4e00-\\u9fa5)(a-zA-Z0-9_)]{3,30}$";
				if (parking == null || parking.trim().length() == 0) {
					errorMsgs.add("停車資訊: 請勿空白");
				} else if(!parking.trim().matches(parkingReg)) { 
					errorMsgs.add("停車資訊: 只能是中、英文字母、數字和_ , 且長度必需在3到30之間");
	            }
				
				String payinfo = req.getParameter("payinfo");
				String payinfoReg = "^[(\\u4e00-\\u9fa5)(a-zA-Z0-9_)]{3,30}$";
				if (payinfo == null || payinfo.trim().length() == 0) {
					errorMsgs.add("付款資訊: 請勿空白");
				} else if(!payinfo.trim().matches(payinfoReg)) { 
					errorMsgs.add("付款資訊: 只能是中、英文字母、數字和_ , 且長度必需在3到30之間");
	            }
				
				String notifcont = req.getParameter("notifcont");
				String notifcontReg = "^[(\\u4e00-\\u9fa5)(a-zA-Z0-9_)]{6,30}$";
				if (notifcont == null || notifcont.trim().length() == 0) {
					errorMsgs.add("餐廳設定的訂位通知: 請勿空白");
				} else if(!notifcont.trim().matches(notifcontReg)) { 
					errorMsgs.add("餐廳設定的訂位通知: 只能是中、英文字母、數字和_ , 且長度必需在6到30之間");
	            }
				
				String resemail = req.getParameter("resemail");
				String resemailReg = "^[(a-zA-Z0-9@.)]{11,30}$";
				if (resemail == null || resemail.trim().length() == 0) {
					errorMsgs.add("餐廳信箱: 請勿空白");
				} else if(!resemail.trim().matches(resemailReg)) { 
					errorMsgs.add("餐廳信箱: 只能是英文字母、數字和@., 且長度必需在11到30之間");
	            } 		
				
				String respassid = req.getParameter("respassid");
				String respassidReg = "^[(a-zA-Z0-9_)]{6,30}$";
				if (respassid == null || respassid.trim().length() == 0) {
					errorMsgs.add("密碼: 請勿空白");
				} else if(!respassid.trim().matches(respassidReg)) { 
					errorMsgs.add("密碼: 只能是英文字母、數字和_ , 且長度必需在6到30之間");
	            }
				
				Integer currentwaitingnum = new Integer(req.getParameter("currentwaitingnum").trim());
				
				Integer subtimediff = new Integer(req.getParameter("subtimediff").trim());
				
				Integer waitdistance = new Integer(req.getParameter("waitdistance").trim());
				
				String contact = req.getParameter("contact");
				String contactReg = "^[(\\u4e00-\\u9fa5)(a-zA-Z_)]{2,30}$";
				if (contact == null || contact.trim().length() == 0) {
					errorMsgs.add("聯絡人姓名: 請勿空白");
				} else if(!contact.trim().matches(contactReg)) { 
					errorMsgs.add("聯絡人姓名: 只能是中、英文字母 , 且長度必需在2到30之間");
	            }
				
				String contactphon = req.getParameter("contactphon");
				String contactphonReg = "^[(0-9)]{8,10}$";
				if (contactphon == null || contactphon.trim().length() == 0) {
					errorMsgs.add("聯絡人電話: 請勿空白");
				} else if(!contactphon.trim().matches(contactphonReg)) { 
					errorMsgs.add("聯絡人電話: 只能是數字 , 且長度必需在8到10之間");
	            }
				
				String corrdinate = req.getParameter("corrdinate");
//				String corrdinateReg = "^[(a-zA-Z0-9_)]{1,30}$";
//				if (corrdinate == null || corrdinate.trim().length() == 0) {
//					errorMsgs.add("座位屬性+座標: 請勿空白");
//				} else if(!corrdinate.trim().matches(corrdinateReg)) { 
//					errorMsgs.add("座位屬性+座標: 只能是英文字母、數字和_ , 且長度必需在1到30之間");
//	            }
				
				java.util.Date date = new java.util.Date();
				Timestamp adddate = new Timestamp(date.getTime());
				
//				String status = req.getParameter("status");
				
//				String lat = req.getParameter("lat");
//				
//				String lng = req.getParameter("lng");

				ResInfoVO resInfoVO = new ResInfoVO();
				resInfoVO.setResname(resname);
				resInfoVO.setResaddid(resaddid);
				resInfoVO.setResimg(resimg);
				resInfoVO.setBarrierfree(barrierfree);
				resInfoVO.setParentchild(parentchild);
				resInfoVO.setTraffic(traffic);
				resInfoVO.setParking(parking);
				resInfoVO.setPayinfo(payinfo);
				resInfoVO.setNotifcont(notifcont);
				resInfoVO.setResemail(resemail);
				resInfoVO.setRespassid(respassid);
				resInfoVO.setCurrentwaitingnum(currentwaitingnum);
				resInfoVO.setSubtimediff(subtimediff);
				resInfoVO.setWaitdistance(waitdistance);
				resInfoVO.setContact(contact);
				resInfoVO.setContactphon(contactphon);
				resInfoVO.setCorrdinate(corrdinate);
				resInfoVO.setAdddate(adddate);
//				resInfoVO.setStatus(status);
//				resInfoVO.setLat(lat);
//				resInfoVO.setLng(lng);
				
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("resInfoVO", resInfoVO); 
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/resinfo/addResInfo.jsp");
					failureView.forward(req, res);
					return;
				}

				/***************************2.開始新增資料***************************************/
				ResInfoService resinfosSvc = new ResInfoService();
				resInfoVO = resinfosSvc.addResInfo(resname, resaddid, resimg, barrierfree, parentchild, traffic, parking, payinfo, notifcont, resemail, respassid, currentwaitingnum, subtimediff, waitdistance, contact, contactphon, corrdinate, adddate);
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/front-end/res/resinfo/listAllResInfo.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); 
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/res/resinfo/addResInfo.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("delete".equals(action)) { 

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/***************************1.接收請求參數***************************************/
				String resid = new String(req.getParameter("resid"));
				
				/***************************2.開始刪除資料***************************************/
				ResInfoService resinfosSvc = new ResInfoService();
				resinfosSvc.deleteResInfo(resid);
				
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
				String url = "/front-end/res/resinfo/listAllResInfo.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/res/resinfo/listAllResInfo.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("Login_For_Display".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String str = req.getParameter("resmail");
				String str2 = req.getParameter("respassid");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入帳號");
				}
				if (str2 == null || (str2.trim()).length() == 0) {
					errorMsgs.add("請輸入密碼");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/res/resinfo/login.jsp");
					failureView.forward(req, res);
					return;
				}
				
				String resmail = null;
				String respassid = null;
				try {
					resmail = new String(str);
					respassid = new String(str2);
				} catch (Exception e) {
					errorMsgs.add("帳號或密碼格式不正確");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/res/resinfo/login.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始查詢資料*****************************************/
				ResInfoService resinfosSvc = new ResInfoService();
				ResInfoVO resInfoVO = resinfosSvc.checkEmail(resmail,respassid);
				if (resInfoVO == null) {
					errorMsgs.add("帳號或密碼錯誤");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/res/resinfo/login.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("resInfoVO", resInfoVO); 
				String url = "/front-end/res/resinfo/listOneResInfo.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); 
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/res/resinfo/login.jsp");
				failureView.forward(req, res);
			}
		}
		




		  //停權用的
		 if("update_By_BackEnd".equals(action)) {
		        	
		    
		     /***************************1.接收請求參數***************************************/
		      String resid = req.getParameter("resid");
		      String status = req.getParameter("status");
		      ResInfoService resinfosSvc = new ResInfoService();
		      ResInfoVO vo = resinfosSvc.getOneResInfo(resid);
		      vo.setStatus(status);    //String resid, String resname, String resaddid, byte[] resimg, String barrierfree, String parentchild, String traffic, String parking, String payinfo, String notifcont, String resemail, String respassid, Integer currentwaitingnum, Integer subtimediff, Integer waitdistance, String contact, String contactphon, String corrdinate, Timestamp adddate, String status, String lat, String lng
		      resinfosSvc.updateResInfo(resid, vo.getResname(), vo.getResaddid(), vo.getResimg(), vo.getBarrierfree(), vo.getParentchild(), vo.getTraffic(), vo.getParking(), vo.getPayinfo(), vo.getNotifcont(), vo.getResemail(), vo.getRespassid(), vo.getCurrentwaitingnum(), vo.getSubtimediff(), vo.getWaitdistance(), vo.getContact(), vo.getContactphon(), vo.getCorrdinate(), vo.getAdddate(), status,vo.getLat(),vo.getLng());
		      String url = "/back-end/resinfo/listAllResInfo.jsp";
		      RequestDispatcher successView = req.getRequestDispatcher(url); 
		      successView.forward(req, res);				
		        	
		        	
		}


		 		//給後台管理查詢用的(回傳list)
				if ("Search_For_Display_From_Backend".equals(action)) { 

					List<String> errorMsgs = new LinkedList<String>();
					req.setAttribute("errorMsgs", errorMsgs);

					try {
						/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
						String keyWord = req.getParameter("resid");
						if (keyWord == null || (keyWord.trim()).length() == 0) {
							errorMsgs.add("請輸入餐廳編號或名稱");
						}
						if (!errorMsgs.isEmpty()) {
							RequestDispatcher failureView = req
									.getRequestDispatcher("/back-end/resinfo/listAllResInfo.jsp");
							failureView.forward(req, res);
							return;
						}
						
						
						/***************************2.開始查詢資料*****************************************/
						ResInfoService resinfosSvc = new ResInfoService();
						
						List<ResInfoVO> result = resinfosSvc.getKeyWordResInfo(keyWord);
						if (result.isEmpty()) {
							errorMsgs.add("查無資料");
						}
						
						
						/***************************3.查詢完成,準備轉交(Send the Success view)*************/
						req.setAttribute("result", result); 
						String url = "/back-end/resinfo/listAllResInfo.jsp";
						RequestDispatcher successView = req.getRequestDispatcher(url);
						req.removeAttribute("errorMsgs");
						successView.forward(req, res);

						/***************************其他可能的錯誤處理*************************************/
					} catch (Exception e) {
						errorMsgs.add("無法取得資料:" + e.getMessage());
						RequestDispatcher failureView = req
								.getRequestDispatcher("/back-end/resinfo/listAllResInfo.jsp");
						failureView.forward(req, res);
					}
				}
		
	}
//	 將part轉為byte[]
	private byte[] getImgByte(Part part) {
		byte[] pic = null;
		try (InputStream in = part.getInputStream()) {
			pic = new byte[in.available()];
			in.read(pic);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return pic;
	}
}