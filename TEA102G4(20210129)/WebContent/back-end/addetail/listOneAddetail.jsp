<%@page import="com.addetail.model.AddetailService"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.addetail.model.AddetailVO" %>
<%@ page import="java.util.Base64"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="getBaseStirng.file"%>


<%
	AddetailVO addetailVO = (AddetailVO) request.getAttribute("addetailVO");
		if(addetailVO != null){
			byte[] imgbyte = addetailVO.getAdimg();
			String imgString = getBaseString(imgbyte);
			pageContext.setAttribute("imgString", imgString);
			}
%>


<!DOCTYPE html>
<html lang="en" dir="ltr">
<head>
<meta charset="utf-8">
<title>座位呢 - listOneAllAddetail.jsp</title>
<link rel="stylesheet" href="<%=request.getContextPath() + "/css/listOneAddetail.css"%>">
</head>
<body>
	<jsp:useBean id="addetailService" class="com.addetail.model.AddetailService"></jsp:useBean>
	<div>
		<!-- headerlogo -->
		<%@ include file="/back-end/back_header.jsp" %>

		<!-- 顯示資料的區域 -->
		<div class="custinfo">
		  <div class="custinfo_title">
			<a>餐廳資訊管理</a>
			<img src="<%=request.getContextPath()%>/images/right-arrow.png">
			<a>查詢餐廳廣告/推播</a>
		  </div>
		  <div class="input_adid">
			<FORM class="input_area" METHOD="post" ACTION="<%=request.getContextPath()%>/addetail/controller/addetailServlet.do">
				<b>輸入廣告/推播編號 (例:AD00001):</b>
				<input type="text" name="adid">
				<input type="hidden" name="action" value="getOne_For_Display">
				<input type="submit" value="送出">
				<div class="errormsgs" style="color:red">${errorMsgs}</div>
			</FORM>
		  </div>
		<table>
			<tr>
				<th>廣告編號</th>
				<th>標題</th>
				<th>內容</th>
				<th>圖片</th>
				<th>類型</th>
				<th>審核狀態</th>
				<th>申請日期</th>
				<th>上架日期</th>
				<th>下架日期</th>
				<th>餐廳編號</th>
			</tr>
			<tr> 
				<td><%=addetailVO.getAdid()%></td>
				<td><%=addetailVO.getAdtitle()%></td>
				<td><%=addetailVO.getAdcontent()%></td>
				<td><div class="tableImg"><img src="${imgString}" onerror="this.src='<%=request.getContextPath()%>/front-end/res/resinfo/images/null2.jpg'"></div></td>
				<td><%=addetailVO.getAdtype()%></td>
				<td><%=addetailVO.getReviewstatus()%></td>
				<td><fmt:formatDate value="<%=addetailVO.getAddate()%>" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td><fmt:formatDate value="<%=addetailVO.getOnshelftime()%>" pattern="yyyy-MM-dd HH:mm"/></td>
				<td><fmt:formatDate value="<%=addetailVO.getOffshelftime()%>" pattern="yyyy-MM-dd HH:mm"/></td>
				<td><%=addetailVO.getResid()%></td>
			</tr>
		</table>
	  </div>
	</div>
	<%@ include file="/back-end/back_footer.jsp" %>
</body>
</html>