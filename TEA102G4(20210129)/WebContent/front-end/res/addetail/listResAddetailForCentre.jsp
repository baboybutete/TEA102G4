<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="com.addetail.model.*"%>
<%@ include file="getBaseStirng.file"%>
<%@	page import="com.resinfo.model.ResInfoVO"%>

<%
	ResInfoVO resinfoVO = (ResInfoVO)session.getAttribute("resInfoVO");
	AddetailService addetailSvc = new AddetailService();
	List<AddetailVO> list = addetailSvc.getOneResAddetail(resinfoVO.getResid());
	pageContext.setAttribute("list",list);
%>

<html>
<head>
<title>餐廳廣告/推播</title>
<style>
  table#table-1 {
	background-color: #CCCCCF;
    border: 2px solid black;
    text-align: center;
  }
  table#table-1 h4 {
    color: red;
    display: block;
    margin-bottom: 1px;
  }
  h4 {
  	font-size: 20px;
    color: blue;
    display: inline;
  }
   li{
  	font-size: 30px;
  	color: red;
  }
</style>

<style>
  table {
	width: 1060px;
	background-color: #CCCCCF;
	margin-top: 5px;
	margin-bottom: 5px;
  }
  table, th, td {
  	text-align: center;
    border: 1px solid black;
  }
  th, td {
    padding: 5px;
    text-align: center;
  }
  div.info{
  	position: relative;
    margin-top: -574px;
    margin-left: 201px;
  }
  footer{
	position: relative;
    margin-top: 500px;
}
  div.addmeal{
    margin-left: 202px;
}
  li{
 	list-style-type: none;
}
input[type=submit]{
	padding:5px;
	font-size: 18px;
  	border-radius: 6px;
}
form{
    margin-top: 15px;
}
</style>
</head>
<body>
<div><%@ include file="/front-end/res/res_member_centre_header.jsp"%></div>
    
    
 <div class="info">
<!--  <h3>餐聽菜單資料:</h3> -->
<jsp:useBean id="addetailService" class="com.addetail.model.AddetailService" />
	<table>
		<tr>
			<th>標題</th>
			<th>內容</th>
<!-- 			<th>類型</th> -->
			<th>審核狀態</th>
			<th>上架日期</th>
			<th>下架日期</th>
			<th>申請日期</th>
			<th>圖片</th>
<!-- 			<th>修改</th> -->
<!-- 			<th>刪除</th> -->
		</tr>
		<c:forEach var="addetailVO" items="${list}" >
				
		<%  
			AddetailVO addetailVO = (AddetailVO) pageContext.getAttribute("addetailVO");
			if(addetailVO != null){
				byte[] imgbyte = addetailVO.getAdimg();
				String imgString =  getBaseString(imgbyte);
				pageContext.setAttribute("imgString",imgString);
			}
		%>
		<tr>
				<td>${addetailVO.adtitle}</td>
				<td>${addetailVO.adcontent}</td>
<%-- 				<td>${addetailVO.adtype}</td> --%>
				<td>${addetailVO.reviewstatus}</td>
				<td><fmt:formatDate value="${addetailVO.onshelftime}" pattern="yyyy-MM-dd HH:mm"/></td>
				<td><fmt:formatDate value="${addetailVO.offshelftime}" pattern="yyyy-MM-dd HH:mm"/></td>
				<td><fmt:formatDate value="${addetailVO.addate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td><img src="${imgString}" onerror="this.src='<%=request.getContextPath()%>/front-end/res/resinfo/images/null2.jpg'" width="100px" height="50px"></td>
<!-- 				<td> -->
<%-- 					<form action="<%=request.getContextPath() + "/menu/menu.do"%>" METHOD="POST"> --%>
<!-- 						<input type="submit" value="刪除"> -->
<!-- 						<input type="hidden" name="action" value="delete"> -->
<%-- 						<input type="hidden" name="mealid" value="${menuVO.mealid}"> --%>
<!-- 					</form> -->
<!-- 				</td> -->
<!-- 				<td> -->
<%-- 					<form action="<%=request.getContextPath() + "/menu/menu.do"%>" METHOD="POST"> --%>
<!-- 						<input type="submit" value="修改"> -->
<!-- 						<input type="hidden" name="action" value="getOne_For_Update"> -->
<%-- 						<input type="hidden" name="mealid" value="${menuVO.mealid}"> --%>
<!-- 					</form> -->
<!-- 				</td> -->
		</tr>
		</c:forEach>
	</table>
	</div>
	
	<div class="addmeal">
	<%AddetailVO addetailVO = (AddetailVO) pageContext.getAttribute("addetailVO"); %>
					<form action="<%=request.getContextPath() + "/front-end/addetail/addetail.do"%>" METHOD="POST">
						<input type="submit" value="廣告申請">
						<input type="hidden" name="action" value="getOne_For_Insert">
						<input type="hidden" name=resid value="${addetailVO.resid}">
					</form>
<!-- 		<ul> -->
<%-- 			<li><a href="${pageContext.request.contextPath}/front-end/res/menu/addMenuForCentre.jsp">新增餐點</a></li> --%>
<!-- 		</ul> -->
	</div>
	
	
<div><%@ include file="/front-end/res/res_member_centre_footer.jsp"%></div>
</body>
<script src="<%=request.getContextPath()+"/front-end/js/er.twzipcode.data.js"%>"></script>
  <script src="<%=request.getContextPath()+"/front-end/js/er.twzipcode.min.js"%>"></script>
<script src="<%=request.getContextPath()+"/front-end/js/jquery-3.5.1.min.js"%>"></script>
</html>