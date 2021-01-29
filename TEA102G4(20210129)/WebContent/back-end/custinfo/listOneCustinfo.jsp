<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.custinfo.model.*"%>
<%@ page import="java.util.*"%>
<%@ include file="getBaseStirng.file"%>
<%
	CustinfoVO custinfoVO = (CustinfoVO) request.getAttribute("custinfoVO"); //CustinfoServlet.java(Concroller), 存入req的empVO物件
	if (custinfoVO != null){
		byte[] imgbyte = custinfoVO.getCustpicture();
		String imgString = getBaseString(imgbyte);
		pageContext.setAttribute("imgString", imgString);
	}
%>

<!DOCTYPE html>
<html lang="en" dir="ltr">
<head>
<meta charset="utf-8">
<title>座位呢 - listOneCustinfo.jsp</title>
<link rel="stylesheet" href="<%=request.getContextPath() + "/css/listOneCustinfo.css"%>">
</head>
<body>
	<jsp:useBean id="addetailService" class="com.addetail.model.AddetailService"></jsp:useBean>
	<div>
		<!-- headerlogo -->
		<%@ include file="/back-end/back_header.jsp" %>

		<!-- 顯示資料的區域 -->
		<div class="custinfo">
		  <div class="custinfo_title">
			<a>消費者資訊管理</a>
			<img src="<%=request.getContextPath()%>/images/right-arrow.png">
			<a>查詢消費者</a>
		  </div>
		  <div class="input_adid">
			<FORM class="input_area" METHOD="post" ACTION="<%=request.getContextPath()%>/back-end/custinfo/custinfo.do">
				<b>輸入消費者編號 (如C00001):</b>
				<input type="text" name="custid">
				<input type="hidden" name="action" value="getOne_For_Display">
				<input type="submit" value="送出">
				<div class="errormsgs" style="color:red">${errorMsgs}</div>
			</FORM>
		  </div>
		<table>
			<tr>
				<th>顧客會員編號</th>
				<th>會員姓名</th>
				<th>會員電話</th>
				<th>會員帳號</th>
				<th>會員密碼</th>
<!-- 				<th>會員狀態</th> -->
				<th>註冊日期</th>
				<th>會員照片</th>
		    </tr>
			<tr>
				<td><%=custinfoVO.getCustid()%></td>
				<td><%=custinfoVO.getCustname()%></td>
				<td><%=custinfoVO.getCusttel()%></td>
				<td><%=custinfoVO.getCustaccount()%></td>
				<td><%=custinfoVO.getCustpassword()%></td>
<%-- 				<td><%=custinfoVO.getCuststatus()%></td> --%>
				<td><fmt:formatDate value="${custinfoVO.registrationtime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
	 			<td><div class="tableImg"><img src="${imgString}" onerror="this.src='<%=request.getContextPath()%>/front-end/res/resinfo/images/null2.jpg'"></div></td>
			</tr>
		</table>
	  </div>
	</div>
	<!-- footer -->
   <%@ include file="/back-end/back_footer.jsp" %>
</body>
</html>