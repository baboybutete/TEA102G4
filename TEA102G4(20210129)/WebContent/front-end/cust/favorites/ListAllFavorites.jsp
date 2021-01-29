<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.favorites.model.*"%>
<%@ page import="com.custinfo.model.*"%>
<%@ page import="com.resinfo.model.*"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html>

<%
	CustinfoVO custinfoVO1 = (CustinfoVO) session.getAttribute("custinfoVO");
	FavoritesService favoritesSvc = new FavoritesService();
	List<FavoritesVO> list = favoritesSvc.getOneAccount(custinfoVO1.getCustid());
	pageContext.setAttribute("list", list);
%>
<jsp:useBean id="resinfoSvc" scope="page"
	class="com.resinfo.model.ResInfoService" />
<jsp:useBean id="custinfoSvc" scope="page"
	class="com.custinfo.model.CustinfoService" />

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
				<li><a href="<%=request.getContextPath() + "/front-end/cust/favorites/ListAllFavorites.jsp"%>">我的收藏 </a></li>
				<li><a href="<%=request.getContextPath() + "/front-end/cust/orderinfo_orderdet/ListAllOrder.jsp"%>">我的訂單</a></li>
<!-- 				<li><a href="#"> 預約紀錄 </a></li> -->
<!-- 				<li><a href="#">訊息通知 </a></li> -->
<!-- 				<li><a href="#"> 優惠卷 </a></li> -->
				<!-- <li><a href="#">愛睏 <span> &gt; </span> </a></li>
                <li><a href="#">愛睏 <span> &gt; </span> </a></li>
                <li><a href="#">愛睏 <span> &gt; </span> </a></li>
                <li><a href="#">愛睏 <span> &gt; </span> </a></li> -->
			</ul>
		</div>
		<div class="box" style="weight: 10px; height: 425 margin-left: 10px;">
			<c:if test="${not empty errorMsgs}">
				<font style="color: red">請修正以下錯誤:</font>
				<ul>
					<c:forEach var="message" items="${errorMsgs}">
						<li style="color: red">${message}</li>
					</c:forEach>
				</ul>
			</c:if>
			<table style="border: 1px #cccccc solid;" cellpadding="10" >
				<tr>
					<!-- 					<th>收藏編號</th> -->
					<!-- 					<th>顧客ID</th> -->
					<th>餐廳名稱</th>
					<!-- 					<th>收藏狀態</th> -->
					<th>收藏日期</th>
				</tr>
				<%@ include file="page1.file"%>
				<c:forEach var="favoritesVO" items="${list}" begin="<%=pageIndex%>"
					end="<%=pageIndex+rowsPerPage-1%>">
					
					

					<tr>
						<%-- 						<td>${favoritesVO.favoritesid}</td> --%>
						<%-- 						<td>${custinfoSvc.getOneCust(favoritesVO.custid).custname}</td> --%>
						<td><a class="c" href="<%=request.getContextPath()%>/resinfo/ResInfoServlet?action=getOne_For_Display_By_Client&resid=${favoritesVO.resid}">${resinfoSvc.getOneResInfo(favoritesVO.resid).resname}</a></td>
						<%-- 						<td>${favoritesVO.favoritestatus}</td> --%>
						<td><fmt:formatDate value="${favoritesVO.addDate}"
								pattern="yyyy-MM-dd HH:mm:ss" /></td>
						<td>
							<FORM METHOD="post"
								ACTION="<%=request.getContextPath()%>/favorites/favorites.do"
								style="margin-bottom: 0px;">
								<input type="submit" value="取消收藏"> <input type="hidden"
									name="favoritesid" value="${favoritesVO.favoritesid}">
								<input type="hidden" name="action" value="delete">
							</FORM>
						</td>
					</tr>
				</c:forEach>
			</table>
			<%@ include file="page2.file"%>

		</div>
	</div>



	<!-- footer  -->
<%-- 	<%@ include file="/front-end/cust/custindex_footer.jsp" %> --%>

</body>


</html>