<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.admininfo.model.*"%>

<%
	AdmininfoVO admininfoVO = (AdmininfoVO)request.getAttribute("admininfoVO");
%>

<!DOCTYPE html>
<html lang="en" dir="ltr">
<head>
<meta charset="utf-8">
<title>座位呢 - listOneAdmininfo.jsp</title>
<link rel="stylesheet" href="<%=request.getContextPath() + "/css/listOneAdmininfo.css"%>">
</head>
<body>
	<jsp:useBean id="addetailService" class="com.addetail.model.AddetailService"></jsp:useBean>
	<div>
		<!-- headerlogo -->
		<%@ include file="/back-end/back_header.jsp" %>

		<!-- 顯示資料的區域 -->
		<div class="custinfo">
		  <div class="custinfo_title">
			<a>後台管理員管理</a>
			<img src="<%=request.getContextPath()%>/images/right-arrow.png">
			<a>查詢後台管理員</a>
		  </div>
		  <div class="input_adid">
			<FORM class="input_area" METHOD="post" ACTION="<%=request.getContextPath()%>/admininfo/controller/admininfoServlet.do">
	            <b>輸入管理員編號 (如MAN00001):</b>
	            <input type="text" name="manid">
	            <input type="hidden" name="action" value="getOne_For_Display">
	            <input type="submit" value="送出">
	            <div class="errormsgs" style="color:red">${errorMsgs}</div>
            </FORM>
		  </div>
		<table>
			<tr>
	            <th>管理員編號</th>
				<th>管理員帳號</th>
				<th>管理員密碼</th>
<!-- 				<th>email</th> -->
				<th>管理員姓名</th>
				<th>管理員電話</th>
<!-- 				<th>管理員狀態</th> -->
<!-- 				<th>管理員權限</th> -->
          	</tr>
			<tr>
				<td><%=admininfoVO.getManid()%></td>
				<td><%=admininfoVO.getManmail()%></td>
<%-- 				<td><%=admininfoVO.getManname()%></td> --%>
				<td><%=admininfoVO.getManpassword()%></td>
				<td><%=admininfoVO.getManrealname()%></td>
				<td><%=admininfoVO.getMantel()%></td>
<%-- 				<td><%=admininfoVO.getManstatus()%></td> --%>
<%-- 				<td><%=admininfoVO.getManpurview()%></td> --%>
			</tr>
		</table>
	  </div>
	</div>
	<!-- footer -->
  <%@ include file="/back-end/back_footer.jsp" %>
</body>
</html>