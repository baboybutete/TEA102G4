package com.carousel.controller;

import java.io.*;
import java.sql.Timestamp;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import com.carousel.model.CarouselService;
import com.carousel.model.CarouselVO;
import com.menu.model.MenuVO;
@WebServlet("/carousel/CarouselServlet")
@MultipartConfig
public class CarouselServlet extends HttpServlet{
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
				String str = req.getParameter("carouselid");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入輪播圖編號");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/res/carousel/listResCarouselForCentre.jsp");
					failureView.forward(req, res);
					return;
				}
				
				String carouselid = null;
				try {
					carouselid = new String(str);
				} catch (Exception e) {
					errorMsgs.add("輪播圖編號格式不正確");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/res/carousel/listResCarouselForCentre.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始查詢資料*****************************************/
				CarouselService carouselSvc = new CarouselService();
				CarouselVO carouselVO = carouselSvc.getOneCarousel(carouselid);
				if (carouselVO == null) {
					errorMsgs.add("查無資料");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/res/carousel/listResCarouselForCentre.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("carouselVO", carouselVO); 
				String url = "/front-end/res/carousel/listOneCarouselForCentre.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); 
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/res/carousel/listResCarouselForCentre.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("getOne_For_Update".equals(action)) { 

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數****************************************/
				String carouselid = req.getParameter("carouselid");
				
				/***************************2.開始查詢資料****************************************/
				CarouselService carouselSvc = new CarouselService();
				CarouselVO carouselVO = carouselSvc.getOneCarousel(carouselid);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("carouselVO", carouselVO);         
				String url = "/front-end/res/carousel/updateCarouselForCentre.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/res/carousel/listResCarouselForCentre.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("update".equals(action)) { 
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String carouselid = req.getParameter("carouselid").trim();
				String resid = req.getParameter("resid").trim();
				String residReg = "^[R]{1}[0-9]{5}$";
				if (resid == null || resid.trim().length() == 0) {
					errorMsgs.add("餐廳編號: 請勿空白");
				} else if(!resid.trim().matches(residReg)) { 
					errorMsgs.add("餐廳編號: 只能是'RXXXXX'");
	            }
				
				Part partImg = req.getPart("carouselpic");
				HttpSession session = req.getSession();
				byte[] carouselpic = null;
				if (partImg.getSize() != 0) {
					carouselpic = getImgByte(partImg);
					session.setAttribute("carouselpic", carouselpic);
				} else if (session.getAttribute("carouselpic") != null) {
					carouselpic =(byte[])session.getAttribute("carouselpic");
				} else {
					errorMsgs.add("請上傳輪播圖");
				}
				
				java.util.Date date = new java.util.Date();
				Timestamp adddate = new Timestamp(date.getTime());
				
				CarouselVO carouselVO = new CarouselVO();
				carouselVO.setCarouselid(carouselid);
				carouselVO.setResid(resid);
				carouselVO.setCarouselpic(carouselpic);
				carouselVO.setAdddate(adddate);
				
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("carouselVO", carouselVO); 
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/res/carousel/updateCarouselForCentre.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始修改資料*****************************************/
				CarouselService carouselSvc = new CarouselService();
				carouselVO = carouselSvc.updateCarousel(carouselid, resid, carouselpic, adddate);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("carouselVO", carouselVO); 
				String url = "/front-end/res/carousel/listOneCarouselForCentre.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); 
				successView.forward(req, res);
				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/res/carousel/updateCarouselForCentre.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("getOne_For_Insert".equals(action)) {
			List<String> errorMsg = new LinkedList<String>();
			req.setAttribute("errorMsg", errorMsg);

			try {
				/* 取得想要更改的resid */
				String resid = req.getParameter("resid");
				/* 查詢資料 */
				CarouselService carouselSvc = new CarouselService();
				CarouselVO carouselVO = carouselSvc.addOneCarousel(resid);
				req.setAttribute("carouselVO", carouselVO);

				/* 將資料轉交出去 */
				RequestDispatcher successView = req.getRequestDispatcher("/front-end/res/carousel/addCarouselForCentre.jsp");
				successView.forward(req, res);
				return;
			} catch (Exception e) {
				errorMsg.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/res/carousel/listResCarouselForCentre.jsp");
				failureView.forward(req, res);
			}

		}

        if ("insert".equals(action)) { 
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				String resid = req.getParameter("resid").trim();
				String residReg = "^[R]{1}[0-9]{5}$";
				if (resid == null || resid.trim().length() == 0) {
					errorMsgs.add("餐廳編號: 請勿空白");
				} else if(!resid.trim().matches(residReg)) { 
					errorMsgs.add("餐廳編號: 只能是'RXXXXX'");
	            }
				
				Part partImg = req.getPart("carouselpic");
				HttpSession session = req.getSession();
				byte[] carouselpic = null;
				if (partImg.getSize() != 0) {
					carouselpic = getImgByte(partImg);
					session.setAttribute("carouselpic", carouselpic);
				} else if (session.getAttribute("carouselpic") != null) {
					carouselpic =(byte[])session.getAttribute("carouselpic");
				} else {
					errorMsgs.add("請上傳輪播圖");
				}
				
				java.util.Date date = new java.util.Date();
				Timestamp adddate = new Timestamp(date.getTime());
				

				CarouselVO carouselVO = new CarouselVO();
				carouselVO.setResid(resid);
				carouselVO.setCarouselpic(carouselpic);
				carouselVO.setAdddate(adddate);
				
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("carouselVO", carouselVO); 
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/res/carousel/listResCarouselForCentre.jsp");
					failureView.forward(req, res);
					return;
				}

				/***************************2.開始新增資料***************************************/
				CarouselService carouselSvc = new CarouselService();
				carouselVO = carouselSvc.addCarousel(resid, carouselpic, adddate);
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/front-end/res/carousel/listResCarouselForCentre.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); 
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/res/carousel/listResCarouselForCentre.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("delete".equals(action)) { 

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/***************************1.接收請求參數***************************************/
				String carouselid = new String(req.getParameter("carouselid"));
				
				/***************************2.開始刪除資料***************************************/
				CarouselService carouselSvc = new CarouselService();
				carouselSvc.deleteCarousel(carouselid);
				
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
				String url = "/front-end/res/carousel/listResCarouselForCentre.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/res/carousel/listResCarouselForCentre.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("getOneRes_For_Display".equals(action)) { 

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
							.getRequestDispatcher("/front-end/res/carousel/listResCarouselForCentre.jsp");
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
							.getRequestDispatcher("/front-end/res/carousel/listResCarouselForCentre.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始查詢資料*****************************************/
				CarouselService carouselSvc = new CarouselService();
				List<CarouselVO> carouselVO = carouselSvc.getOneRes(resid);
				if (carouselVO == null) {
					errorMsgs.add("查無資料");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/res/carousel/listResCarouselForCentre.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
//				req.setAttribute("carouselVO", carouselVO); 
				req.getSession().setAttribute("carouselVO", carouselVO); 
				String url = "/front-end/res/carousel/listOneResCarouselForCentre.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); 
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/res/carousel/listResCarouselForCentre.jsp");
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
