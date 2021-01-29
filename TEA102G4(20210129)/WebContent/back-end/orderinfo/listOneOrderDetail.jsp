<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="com.orderdetail.model.*"%>

<%-- <% OrderDetailVO orderDetailVO  = (OrderDetailVO) request.getAttribute("orderDetailVO");%> --%>

<!DOCTYPE html>
<html lang="en" dir="ltr">
<head>
<meta charset="utf-8">
<title>座位呢 - listOneOrderDetail.jsp</title>
<link rel="stylesheet" href="<%=request.getContextPath() + "/css/listOneAdmininfo.css"%>">
</head>
<body>
	<jsp:useBean id="orderDetailService" class="com.orderdetail.model.OrderDetailService"></jsp:useBean>
	
	<div>
		<!-- headerlogo -->
		<%@ include file="/back-end/back_header.jsp" %>

		<!-- 顯示資料的區域 -->
		<div class="custinfo">
		  <div class="custinfo_title">
			<a>查詢消費者訂單</a>
			<img src="<%=request.getContextPath()%>/images/right-arrow.png">
			<a>查詢訂單詳細</a>
		  </div>
		  <div class="input_adid">
			<FORM class="input_area" METHOD="post" ACTION="<%=request.getContextPath()%>/back-end/orderdetail/OrderDetail.do" >
		        <b>輸入訂單編號以查詢詳細 (如O00001):</b>
		        <input type="text" name="orderid">
		        <input type="hidden" name="action" value="getOneInfo_For_Display">
		        <input type="submit" value="送出">
    	  	</FORM>
		  </div>
		<table>
			<tr>
	            <th>訂單編號</th>
				<th>報到狀態</th>
				<th>付款狀態</th>
				<th>餐點名稱</th>
				<th>餐點數量</th>
				<th>餐點單價</th>
          	</tr>
          	<c:forEach var="orderDetailVO" items="${orderDetailVO}">
			<tr>
				<td>${orderDetailVO.orderid}</td>
				<td>${orderDetailVO.checkin}</td>
				<td>${orderDetailVO.paystatus}</td>
				<td>${orderDetailVO.mealname}</td>
				<td>${orderDetailVO.mealcount}</td>
				<td>${orderDetailVO.mealprice}</td>
			</tr>
			</c:forEach>
		</table>
	  </div>
	</div>
	<!-- footer -->
   <%@ include file="/back-end/back_footer.jsp" %>
</body>
</html>
    