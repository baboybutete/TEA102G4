package com.shoppingcart.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.xml.bind.ParseConversionEvent;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;

import com.menu.model.MenuService;
import com.menu.model.MenuVO;
import com.orderdet.model.OrderdetService;
import com.orderdet.model.OrderdetVO;
import com.orderinfo.model.OrderinfoService;
import com.orderinfo.model.OrderinfoVO;
import com.shoppingcart.model.MENU;
import com.shoppingcart.model.ShoppingCartService;
import com.shoppingcart.model.ShoppingCartVO;
@WebServlet("/shoppingcart/shoppingcart.do")
public class ShoppingCartServlet extends HttpServlet {

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession();

		@SuppressWarnings("unchecked")
		List<MENU> buylist = (Vector<MENU>) session.getAttribute("shoppingcart");
		String action = req.getParameter("action");
		System.out.println(action);
		if (!action.equals("CHECKOUT")) {
			// 刪除購物車中的餐點
			if (action.equals("DELETE")) {
				String del = req.getParameter("del");
				String mealid = req.getParameter("mealid");
				System.out.println(mealid);
				int d = Integer.parseInt(del);
				int length= buylist.size();
				if(length > d) {
					if(buylist.get(d).getMealid().equals(mealid)) {
						buylist.remove(d);
					}
				}
				
				
				String resid = req.getParameter("resid");
				System.out.println(resid);
				
				
				session.setAttribute("shoppingcart", buylist);
				String url = "/front-end/cust/shoppingcart/Shoppingcar.jsp";
				RequestDispatcher rd = req.getRequestDispatcher(url);
				rd.forward(req, res);
				return;
			}
			// 新增餐點至購物車中
			else if (action.equals("ADD")) {
				System.out.println("123");

				// 取得後來新增的餐點
				MENU amenu = getMenu(req);

				if (buylist == null) {
					System.out.println("1234");
					buylist = new Vector<MENU>();
					buylist.add(amenu);
				} else {
					System.out.println(buylist.contains(amenu));
					if (buylist.contains(amenu)) {
						System.out.println("12345");
						MENU innerMENU = buylist.get(buylist.indexOf(amenu));
						innerMENU.setMealcount(innerMENU.getMealcount() + amenu.getMealcount());
					} else {
						buylist.add(amenu);
					}
				}
			} else if (action.equals("REDUCE")) {
				MENU amenu = getMenu(req);

				if (buylist != null) {
					System.out.println(buylist.contains(amenu));
					if (buylist.contains(amenu)) {
						System.out.println("12345");
						MENU innerMENU = buylist.get(buylist.indexOf(amenu));
						if ((innerMENU.getMealcount() - 1) != 0) {
							innerMENU.setMealcount(innerMENU.getMealcount() - 1);
						} else {
							String del = req.getParameter("del");
							System.out.println(del);
							int d = Integer.parseInt(del);
							System.out.println(d);
							buylist.remove(d);
							String resid = req.getParameter("resid");
							System.out.println(resid);
						}
					}
				}
			}
			session.setAttribute("shoppingcart", buylist);
//			String url = "/front-end/cust/shoppingcart/Menu.jsp";
//			RequestDispatcher rd = req.getRequestDispatcher(url);
//			rd.forward(req, res);
		} else if (action.equals("CHECKOUT")) {
			double total = 0;
			for (int i = 0; i < buylist.size(); i++) {
				MENU order = buylist.get(i);
				Double price = order.getPrice();
				Integer mealcount = order.getMealcount();
				total += (price * mealcount);
			}

			String amount = String.valueOf(total);
			req.setAttribute("amount", amount);
			String url = "/Checkout.jsp";
			RequestDispatcher rd = req.getRequestDispatcher(url);
			rd.forward(req, res);
		}
	}

	private MENU getMenu(HttpServletRequest req) throws IOException, ServletException {

		Integer mealcount = Integer.valueOf(req.getParameter("quantity"));
		System.out.println(mealcount);
		String name = req.getParameter("name");
		System.out.println(name);
		String price = req.getParameter("price");
		System.out.println(price);
		String mealid = req.getParameter("mealid");
		System.out.println(mealid);
		String resid = req.getParameter("resid");
		System.out.println(resid);
		MenuService menuSvc = new MenuService();
		MenuVO menuVO = menuSvc.getOneMenu(mealid);
		byte[] mealimg = menuVO.getMealimg();
		MENU menu = new MENU();
//		System.out.println(imgString);

//		Part partImg = req.getPart("img");
//		System.out.println(partImg);
//		HttpSession session = req.getSession();
//		byte[] mealimg = null;
//		if (partImg.getSize() != 0) {
//			mealimg = getImgByte(partImg);
//			session.setAttribute("mealimg", mealimg);
//			
//		}
		menu.setMealid(mealid);
		menu.setName(name);
		menu.setPrice(new Double(price));
		menu.setMealcount(mealcount);
		menu.setMealimg(mealimg);
		menu.setResid(resid);
		return menu;
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
