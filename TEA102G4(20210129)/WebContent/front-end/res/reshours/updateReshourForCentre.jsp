<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.reshours.model.*"%>

<%
ResHoursVO resHoursVO = (ResHoursVO) request.getAttribute("resHoursVO"); 
%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>餐廳營業時間修改</title>

<style>
form{
	margin-top: -510px;
	margin-left: 300px;
}
div.container div.row{
    margin-left: 20px;
}
div.container{
}
div.container div.col-3{
	font-size: 25px;
  	text-align: left;
}
div.container div.col-4{
	font-size: 20px;
  	text-align: left;
}
input[type=text]{
	width: 300px;
	padding:5px;
	font-size: 18px;
}
input[type=submit]{
	padding:5px;
	font-size: 18px;
  	border-radius: 6px;
}
#error{
	font-size: 20px;
    color: red;
    margin-left: 310px;
    position: absolute;
    margin-top: 100px;
}
li{
list-style-type: none;
}
div.info {
    position: relative;
	margin-top: 50px;
	margin-left: 0px;
}
</style>
</head>
<body>
     <div><%@ include file="/front-end/res/res_member_centre_header.jsp"%></div>
    
<!-- <h3>資料修改:</h3> -->

<%-- 錯誤表列 --%>
<div id="error">
<c:if test="${not empty errorMsgs}">
<!-- 	<font style="color:red">請修正以下錯誤:</font> -->
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>
</div>

<div class="info">
<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/reshours/ResHoursServlet" name="form1" >
<div class="container">
<!--    <div class="row"> -->
<!--     <div class="col-2">營業時間編號:</div> -->
<%--     <div class="col-4"><%=resHoursVO.getReshourid()%></div> --%>
<!--     </div><br> -->
    <div class="row">
    <div class="col-3">餐廳編號:</div>
    <div class="col-4"><%=resHoursVO.getResid()%></div>
    </div><br>
  <div class="row">
  <div class="col-3">開始營業時間:</div>
    <div class="col-4"><input name="opening" type="time" ></div>
    </div><br>
    <div class="row">
  <div class="col-3">結束營業時間:</div>
    <div class="col-4"><input name="closing" type="time" ></div>
    </div><br>
    <div class="col-6">
  <div class="row">
  <input type="hidden" name="action" value="update">
<input type="hidden" name="reshourid" value="<%=resHoursVO.getReshourid()%>">
<input type="hidden" name="resid" value="<%=resHoursVO.getResid()%>">
<input type="submit" value="送出修改">
  </div>
  	</div>
</div>
</FORM>
</div>
 <div><%@ include file="/front-end/res/res_member_centre_footer.jsp"%></div>
</body>



</html>