<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.orderinfo.model.*"%>
<%@ page import="com.orderdetail.model.*"%>
<%
	OrderinfoService orderinfoSvc = new OrderinfoService();
	List<OrderinfoVO> list = orderinfoSvc.getAll();
	pageContext.setAttribute("list", list);
%>

<!DOCTYPE html>
<html lang="en" dir="ltr">
  <head>
    <meta charset="utf-8">
    <title>座位呢 - listAllOrderinfo.jsp</title>
    <link rel="stylesheet" href='<%=request.getContextPath()%>/css/listAllOrderinfo.css'>
  </head>
  <body>
  	<jsp:useBean id="orderinfoService" class="com.orderinfo.model.OrderinfoService"></jsp:useBean>
    <div class="">
     <%@ include file="/back-end/back_header.jsp" %>

      <!-- 顯示資料的區域 -->
      <div class="custinfo">
        <div class="custinfo_title">
          <a>消費者資訊管理</a>
          <img src="<%=request.getContextPath()%>/images/right-arrow.png">
          <a>查詢消費者訂單</a>
        </div>
        <div class="input_adid">
          <FORM class="input_area" METHOD="post" ACTION="<%=request.getContextPath()%>/back-end/orderinfo/orderinfo.do">
            <b>輸入消費者訂單編號 (如O00001):</b>
            <input type="text" name="orderid">
            <input type="hidden" name="action" value="getOne_For_Display">
            <input type="submit" value="送出">
            <div class="errormsgs" style="color:red">${errorMsgs}</div>
          </FORM>
          <FORM class="detail" METHOD="post" ACTION="<%=request.getContextPath()%>/back-end/orderdetail/OrderDetail.do" >
	        <b>輸入訂單編號以查詢詳細 (如O00001):</b>
	        <input type="text" name="orderid">
	        <input type="hidden" name="action" value="getOneInfo_For_Display">
	        <input type="submit" value="送出">
	        <div class="error" style="color:red">${error}</div>
    	  </FORM>
        </div>
        <table>
          <tr>
            <th>訂單編號</th>
<!-- 			<th>餐廳編號</th> -->
<!-- 			<th>消費者編號</th> -->
			<th>訂位姓名</th>
			<th>手機號碼</th>
			<th>下單日期</th>
			<th>訂位時間</th>
			<th>人數</th>
			<th>付款狀態</th>
			<th>報到狀態</th>
			<th>座位</th>
          </tr>
          	<%@ include file="page1.file"%>
			<c:forEach var="orderinfoVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
          <tr>
            <td>${orderinfoVO.orderid}</td>
<%-- 			<td>${orderinfoVO.resid}</td> --%>
<%-- 			<td>${orderinfoVO.custid}</td> --%>
			<td>${orderinfoVO.subscriber}</td>
			<td>${orderinfoVO.subphone}</td>
			<td><fmt:formatDate value="${orderinfoVO.ordertime}" pattern="yyyy-MM-dd HH:mm:ss"/></td> 
			<td><fmt:formatDate value="${orderinfoVO.subtime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			<td>${orderinfoVO.subnumber}</td>
			<td>${orderinfoVO.paystatus}</td>
			<td>${orderinfoVO.checkin}</td>
			<td>${orderinfoVO.seat}</td>
          </tr>
         </c:forEach>
       </table>
       <%@ include file="page2.file"%>
      </div>
    </div>
    <!-- footer -->
    <%@ include file="/back-end/back_footer.jsp" %>
    
  </body>
</html>