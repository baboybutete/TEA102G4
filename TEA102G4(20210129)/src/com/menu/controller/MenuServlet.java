package com.menu.controller;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
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

import com.menu.model.MenuService;
import com.menu.model.MenuVO;

//已使用web.xml設定，因此註解
@WebServlet("/menu/menu.do")
@MultipartConfig
public class MenuServlet extends HttpServlet {
	private MenuService menuService;

	/* 第一次被請求時建立service物件 */
	@Override
	public void init() {
		menuService = new MenuService();
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		/* Get 無法使用mutipart，為了避免錯誤訊息，將使用get進來的重新導回addMenu.jsp */
		res.sendRedirect(req.getContextPath() + "/front-end/res/menu/addMenuForCentre.jsp");
		return;
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		/* 確認請求 get_one_forDisplay */
		if (action != null && "get_one_forDisplay".equals(action)) {
			List<String> errorMsg = new ArrayList<String>();

			req.setAttribute("errorMsg", errorMsg);
			/* 確認請求格式正確 */
			try {
				String mealid = req.getParameter("mealid");
				/* 確認是否為空值(name設錯的情況下)或沒有輸入mealid */
				if (mealid == null || mealid.trim().isEmpty()) {
					errorMsg.add("請輸入mealid");
					RequestDispatcher dispacher = req.getRequestDispatcher("/front-end/res/menu/listResMenuForCentre.jsp");
					dispacher.forward(req, res);
					return;
				}

				/* --確認輸入的資料是否符合格式 EAT+5個數字-- */
				String regex = "^[E][A][T][0-9]{5}$";
				if (!mealid.matches(regex)) {
					errorMsg.add("輸入格式不正確");
				}
				/* 不符合則回到回到select_page */
				if (!errorMsg.isEmpty()) {
					RequestDispatcher dispacher = req.getRequestDispatcher("/front-end/res/menu/listResMenuForCentre.jsp");
					dispacher.forward(req, res);
					return;
				}

				/*--資料皆符合開始查詢--*/
				MenuVO menuVO = new MenuVO();
				menuVO = menuService.getOneMenu(mealid);

				/* 查無此資料 回傳null */
				if (menuVO == null) {
					errorMsg.add("查無此mealid");
				}

				if (!errorMsg.isEmpty()) {
					RequestDispatcher dispacher = req.getRequestDispatcher("/front-end/res/menu/listResMenuForCentre.jsp");
					dispacher.forward(req, res);
					return;
				}

				req.setAttribute("menuVO", menuVO);
				RequestDispatcher dispacher = req.getRequestDispatcher("/front-end/res/menu/listOneMenuForCentre.jsp");
				dispacher.forward(req, res);

			} /* 不明原因的錯誤 */
			catch (Exception e) {
				errorMsg.add("無法取得資料:" + e.getMessage());
				RequestDispatcher dispacher = req.getRequestDispatcher("/front-end/res/menu/listResMenuForCentre.jsp");
				dispacher.forward(req, res);
				return;
			}
		}
		
		
		if ("getOne_For_Insert".equals(action)) {
			List<String> errorMsg = new LinkedList<String>();
			req.setAttribute("errorMsg", errorMsg);

			try {
				/* 取得想要更改的resid */
				String resid = req.getParameter("resid");
				/* 查詢資料 */
				MenuVO menuVO = menuService.addOneMenu(resid);
				req.setAttribute("menuVO", menuVO);

				/* 將資料轉交出去 */
				RequestDispatcher successView = req.getRequestDispatcher("/front-end/res/menu/addMenuForCentre.jsp");
				successView.forward(req, res);
				return;
			} catch (Exception e) {
				errorMsg.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/res/menu/listResMenuForCentre.jsp");
				failureView.forward(req, res);
			}

		}
		/* 處理新增資料請求 */
		if (action != null && "insert".equals(action)) {
			// 錯誤回到addjsp and change ArrayList to LinkedList
			List<String> errorMsg = new LinkedList<String>();
			req.setAttribute("errorMsg", errorMsg);
			try {
				/* 確定每一項資料是否正確 */
				String resid = req.getParameter("resid");
				String regex = "^[R][0-9]{5}$";
				// 用下拉式選單選擇餐廳，不會有錯誤字串
				if (resid == null || resid.trim().isEmpty()) {
					errorMsg.add("請輸入餐廳編號");
				} else if (!resid.matches(regex)) {
					errorMsg.add("餐廳編號輸入格式不正確");
				}

				String classname = req.getParameter("classname");
				if (classname == null || classname.trim().length() == 0) {
					errorMsg.add("請輸入分類");
				}

				String mealname = req.getParameter("mealname");
				if (mealname == null || mealname.trim().length() == 0) {
					errorMsg.add("請輸入餐點名稱");
				}

				String mealpriceString = req.getParameter("mealprice");
				if (mealpriceString == null || mealpriceString.trim().isEmpty()) {
					errorMsg.add("請輸入餐點價錢");
				}

				Integer mealprice = 0;
				try {
					mealprice = Integer.parseInt(mealpriceString);
				} catch (NumberFormatException e) {
					errorMsg.add("價錢不符合數字格式");
				}

				Part partImg = req.getPart("mealimg");
				HttpSession session = req.getSession();
				byte[] mealimg = null;
				if (partImg.getSize() != 0) {
					mealimg = getImgByte(partImg);
					session.setAttribute("mealimg", mealimg);
					
				} else if (session.getAttribute("mealimg") != null) {
					
					mealimg =(byte[])session.getAttribute("mealimg");
				} else {
					
					errorMsg.add("請上傳餐點圖片");
				}
				// 下拉式選單不會有字串格式問題
				String mealstatus = req.getParameter("mealstatus");
				if (mealstatus == null || mealstatus.trim().length() == 0) {
					errorMsg.add("請輸入餐點狀態");
				}

				java.util.Date date = new java.util.Date();
				Timestamp adddate = new Timestamp(date.getTime());// 新增這筆紀錄的當下時間
				
				MenuVO menuVO = new MenuVO();
				menuVO.setResid(resid);
				menuVO.setClassname(classname);
				menuVO.setMealname(mealname);
				menuVO.setMealprice(mealprice);
				menuVO.setMealimg(mealimg);
				menuVO.setMealstatus(mealstatus);
				menuVO.setAdddate(adddate);;

				/* 回到addMenu頁面並顯示錯誤 */
				if (!errorMsg.isEmpty()) {
					req.setAttribute("menuVO", menuVO);
					RequestDispatcher dispatcher = req.getRequestDispatcher("/front-end/res/menu/addMenuForCentre.jsp");
					dispatcher.forward(req, res);
					return;
				}

				/* 確認資料正確後，開始插入資料 */
//				req.setAttribute("menuVO", menuVO);
				menuService.addMenu(resid, classname, mealname, mealprice, mealimg, mealstatus, adddate);
				RequestDispatcher successView = req.getRequestDispatcher("/front-end/res/menu/listResMenuForCentre.jsp");
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsg.add( e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/res/menu/addMenuForCentre.jsp");
				failureView.forward(req, res);
			}
		}

		/* 處理更新請求 來自listAllMenu頁面 */
		if ("getOne_For_Update".equals(action)) {
			List<String> errorMsg = new LinkedList<String>();
			req.setAttribute("errorMsg", errorMsg);

			try {
				/* 取得想要更改的mealid */
				String mealid = req.getParameter("mealid");
				/* 查詢資料 */
				MenuVO menuVO = menuService.getOneMenu(mealid);
				req.setAttribute("menuVO", menuVO);

				/* 將資料轉交出去 */
				RequestDispatcher successView = req.getRequestDispatcher("/front-end/res/menu/updateMenuForCentre.jsp");
				successView.forward(req, res);
				return;
			} catch (Exception e) {
				errorMsg.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/res/menu/listResMenuForCentre.jsp");
				failureView.forward(req, res);
			}

		}

		if ("update".equals(action)) {
			List<String> errorMsg = new LinkedList<String>();
			req.setAttribute("errorMsg", errorMsg);

			/* 檢查格式 */
			try {
				String mealid = req.getParameter("mealid");
				String regexMealid = "^[E][A][T][0-9]{5}$";
				if (mealid == null || mealid.trim().isEmpty()) {
					errorMsg.add("請輸入餐點編號");
				} else if (!mealid.matches(regexMealid)) {
					errorMsg.add("餐點編號輸入格式不正確");
				}

				String resid = req.getParameter("resid");
				String regexResid = "^[R][0-9]{5}$";
				// 用下拉式選單選擇餐廳，不會有錯誤字串
				if (resid == null || resid.trim().isEmpty()) {
					errorMsg.add("請輸入餐廳編號");
				} else if (!resid.matches(regexResid)) {
					errorMsg.add("餐廳編號輸入格式不正確");
				}

				String classname = req.getParameter("classname");
				if (classname == null || classname.trim().length() == 0) {
					errorMsg.add("請輸入分類");
				}

				String mealname = req.getParameter("mealname");
				if (mealname == null || mealname.trim().length() == 0) {
					errorMsg.add("請輸入餐點名稱");
				}

				String mealpriceString = req.getParameter("mealprice");
				if (mealpriceString == null || mealpriceString.trim().isEmpty()) {
					errorMsg.add("請輸入餐點價錢");
				}

				Integer mealprice = 0;
				try {
					mealprice = Integer.parseInt(mealpriceString);

					if (mealprice.intValue() == 0) {
						throw new ArithmeticException();
					}
					if (mealprice.intValue() < 0) {
						throw new IllegalArgumentException();
					}

				} catch (NumberFormatException e) {
					errorMsg.add("價錢不符合數字格式");
				} catch (ArithmeticException e) {
					errorMsg.add("價錢不得為0");
				} catch (IllegalArgumentException e) {
					errorMsg.add("價錢不得小於0");
				}

				/* 再重資料庫拿回來 */
				Part partImg = req.getPart("mealimg");
				String imgString = req.getParameter("imgString");

				InputStream in = partImg.getInputStream();

				byte[] mealimg = null;
				HttpSession session = req.getSession();

				/* 檢查是否有新上傳照片 */
				if (partImg.getSize() == 0) {
					/* 若沒有新上傳照片，檢查是否有舊照片 */
					if (session.getAttribute("mealimg") != null) {
						mealimg = (byte[]) session.getAttribute("mealimg");
					} else if ("hasNoOld".equals(imgString)) {
						// 沒有老照片也沒有上傳照片
						errorMsg.add("請上傳餐廳照片");
					} else {
						/* 從資料庫取出原本的照片 */
						MenuVO menuVO = menuService.getOneMenu(mealid);
						mealimg = menuVO.getMealimg();
					}

				} else {
					// 有上傳餐廳照片， 把byte[]放到session內供下次請求使用
					// 連續按下兩次修改時也可以回填資料
					mealimg = getImgByte(partImg);
					session.setAttribute("mealimg", mealimg);
				}

				// 下拉式選單不會有字串格式問題
				String mealstatus = req.getParameter("mealstatus");
				if (mealstatus == null) {
					errorMsg.add("請輸入餐點狀態");
				}
				java.util.Date date = new java.util.Date();
				Timestamp adddate = new Timestamp(date.getTime());
				/* 將請求參數封裝到VO裡面 */
				MenuVO menuVO = new MenuVO();
				menuVO.setMealid(mealid);
				menuVO.setResid(resid);
				menuVO.setMealprice(mealprice);
				menuVO.setMealname(mealname);
				menuVO.setMealstatus(mealstatus);
				menuVO.setMealimg(mealimg);
				menuVO.setClassname(classname);
				menuVO.setAdddate(adddate);

				if (!errorMsg.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/res/menu/updateMenuForCentre.jsp");
					req.setAttribute("menuVO", menuVO);// 含有錯誤訊息的 vo ，回到update_menu_input.jsp時可以將資料回填
					failureView.forward(req, res);
					return;
				}

				/* 開始更新資料 */

				/* 修改完成後，將結果呈現在listOneEmp */
				menuVO = menuService.updateMenu(mealid, resid, classname, mealname, mealprice, mealimg, mealstatus,
						adddate);

				req.setAttribute("menuVO", menuVO);
				String url = "/front-end/res/menu/listOneMenuForCentre.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsg.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/res/menu/updateMenuForCentre.jsp");
				failureView.forward(req, res);
			}
		}

		/* 處理刪除請求 */
		if ("delete".equals(action)) {
			String mealid = req.getParameter("mealid");
			List<String> errorMsg = new LinkedList<String>();
			req.setAttribute("errorMsg", errorMsg);
			try {
				menuService.deleteMenu(mealid);
				String url = "/front-end/res/menu/listResMenuForCentre.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsg.add("刪除該筆資料失敗" + e.getStackTrace());

				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/res/menu/listResMenuForCentre.jsp");
				failureView.forward(req, res);
			}
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
							.getRequestDispatcher("/front-end/res/menu/listResMenuForCentre.jsp");
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
							.getRequestDispatcher("/front-end/res/menu/listResMenuForCentre.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始查詢資料*****************************************/
				MenuService menuSvc = new MenuService();
				List<MenuVO> menuVO = menuSvc.getOneResMenu(resid);
				if (menuVO == null) {
					errorMsgs.add("查無資料");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/res/menu/listResMenuForCentre.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
//				req.setAttribute("menuVO", menuVO); 
				req.getSession().setAttribute("menuVO", menuVO); 
				String url = "/front-end/res/menu/listResMenuForCentre.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); 
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/res/menu/listResMenuForCentre.jsp");
				failureView.forward(req, res);
			}
		}
	}
	

	/* 將part轉為byte[] */
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
