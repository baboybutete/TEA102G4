<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.orderinfo.model.*" %>


<%
	OrderinfoVO orderinfoVO = (OrderinfoVO) request.getAttribute("orderinfoVO");
%>

<!DOCTYPE html>
<html lang="en" dir="ltr">
  <head>
    <meta charset="utf-8">
    <title>座位呢 - listOneAllOrderinfo.jsp</title>
    <link rel="stylesheet" href='<%=request.getContextPath()%>/css/listOneOrderinfo.css'>
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
          <tr>
			<td><%=orderinfoVO.getOrderid()%></td>
<%-- 			<td><%=orderinfoVO.getResid()%></td> --%>
<%-- 			<td><%=orderinfoVO.getCustid()%></td> --%>
			<td><%=orderinfoVO.getSubscriber()%></td>
			<td><%=orderinfoVO.getSubphone()%></td>
			<td><%=orderinfoVO.getOrdertime()%></td>
			<td><%=orderinfoVO.getSubtime()%></td>
			<td><%=orderinfoVO.getSubnumber()%></td>
			<td><%=orderinfoVO.getPaystatus()%></td>
			<td><%=orderinfoVO.getCheckin()%></td>
			<td><%=orderinfoVO.getSeat()%></td>
		  </tr>
       </table>
      </div>
    </div>
    
  	<%@ include file="/back-end/back_footer.jsp" %>
  </body>
</html>