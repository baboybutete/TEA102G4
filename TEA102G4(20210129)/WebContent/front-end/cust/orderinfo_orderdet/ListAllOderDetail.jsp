<%@page import="jdk.internal.org.objectweb.asm.tree.IntInsnNode"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.orderinfo.model.*"%>
<%@ page import="com.custinfo.model.*"%>
<%@ page import="com.resinfo.model.*"%>
<%@ page import="com.orderdet.model.*"%>
<%@ page import="com.menu.model.*"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html>

<%
 	CustinfoVO custinfoVO1 = (CustinfoVO) session.getAttribute("custinfoVO");
 		OrderinfoService orderinfoSvc = new OrderinfoService();
 		List<OrderinfoVO> list = orderinfoSvc.getAllByCustid(custinfoVO1.getCustid());
 		pageContext.setAttribute("list", list);
%>
<%
	List<OrderdetVO> list1 = (List) request.getAttribute("orderDetVO"); //Servlet.java(Concroller), 存入req的物件
	pageContext.setAttribute("list1", list1);
%>
<%
String str = null;
int sum = 0;
for(int i= 0; i< list1.size(); i++){
	OrderdetVO orderDetVO = (OrderdetVO) list1.get(i);
	str = orderDetVO.getMealid();
	int count = orderDetVO.getMealcount();
	MenuVO menuVO = (MenuVO) new MenuService().getOneMenu(str);
	int price = menuVO.getMealprice();
	sum += count*price; 
}
%>
<%-- <c:set var"number" value="${1+ 1}" > --%>

<jsp:useBean id="resinfoSvc" scope="page"
	class="com.resinfo.model.ResInfoService" />
<jsp:useBean id="custinfoSvc" scope="page"
	class="com.custinfo.model.CustinfoService" />
<jsp:useBean id="menuSvc" scope="page"
	class="com.menu.model.MenuService" />

<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>login</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/cust.css">
</head>
<body>
	 <%@ include file="/front-end/cust/custindex_header.jsp" %>
    
          <!-- header -->
	<div class="banner">

		<div class="subnav" style="display: inline-block;">
			<ul>
				<li><a
					href="<%=request.getContextPath() + "/front-end/cust/custinfo/listOneCustAccount.jsp"%>">會員資料
				</a></li>
				<li><a
					href="<%=request.getContextPath() + "/front-end/cust/custinfo/update_custinfo_input.jsp"%>">修改資料
				</a></li>
				<li><a
					href="<%=request.getContextPath() + "/front-end/cust/favorites/ListAllFavorites.jsp"%>">我的收藏
				</a></li>
				<li><a
					href="<%=request.getContextPath() + "/front-end/cust/orderinfo_orderdet/ListAllOrder.jsp"%>">
						我的訂單 </a></li>
				<!-- 				<li><a href="#">預約紀錄 </a></li> -->
				<!-- 				<li><a href="#"> 優惠卷 </a></li> -->
				<!-- <li><a href="#">愛睏 <span> &gt; </span> </a></li>
                <li><a href="#">愛睏 <span> &gt; </span> </a></li>
                <li><a href="#">愛睏 <span> &gt; </span> </a></li>
                <li><a href="#">愛睏 <span> &gt; </span> </a></li> -->
			</ul>
		</div>
		<div style="weight: 10px; height: 425 margin-left: 10px;">
			<c:if test="${not empty errorMsgs}">
				<font style="color: red">請修正以下錯誤:</font>
				<ul>
					<c:forEach var="message" items="${errorMsgs}">
						<li style="color: red">${message}</li>
					</c:forEach>
				</ul>
			</c:if>
			<table style="border: 3px #cccccc solid;" cellpadding="10" border='1'>
				<tr>
					<th>餐點名稱</th>
					<th>餐點數量</th>
					<th>餐點單價</th>
				</tr>

				<c:forEach var="OrderdetVO" items="${list1}">

					<tr>
						<td>${menuSvc.getOneMenu(OrderdetVO.mealid).mealname}</td>
						<td>${OrderdetVO.mealcount}</td>
						<td>${menuSvc.getOneMenu(OrderdetVO.mealid).mealprice}</td>
					</tr>
				</c:forEach>
			</table>
			<div class="sum">總金額: <%=sum %>元</div>
<%-- 			<c:forEach var="OrderdetVO" items="${list1}"> --%>

<!-- 					<tr> -->
<%-- 						<td>${menuSvc.getOneMenu(OrderdetVO.mealid).mealname}</td> --%>
<%-- 						<td>${(OrderdetVO.mealcount)*(${menuSvc.getOneMenu(OrderdetVO.mealid).mealprice})}</td> --%>
<%-- 						<td>${menuSvc.getOneMenu(OrderdetVO.mealid).mealprice}</td> --%>
						

<!-- 					</tr> -->
<%-- 				</c:forEach> --%>



		</div>

	</div>
	



	<!-- footer  -->


</body>


</html>