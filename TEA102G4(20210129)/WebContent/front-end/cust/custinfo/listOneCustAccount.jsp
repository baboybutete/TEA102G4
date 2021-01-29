<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.custinfo.model.*"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>login</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/cust.css">
</head>
<body>
	<%@ include file="/front-end/cust/custindex_header.jsp"%>

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
				<!-- 				<li><a href="#"> 預約紀錄 </a></li> -->
				<li><a
					href="<%=request.getContextPath() + "/front-end/cust/orderinfo_orderdet/ListAllOrder.jsp"%>">我的訂單</a></li>
				<!-- 				<li><a href="#"> 優惠卷 </a></li> -->
				<!-- <li><a href="#">愛睏 <span> &gt; </span> </a></li>
                <li><a href="#">愛睏 <span> &gt; </span> </a></li>
                <li><a href="#">愛睏 <span> &gt; </span> </a></li>
                <li><a href="#">愛睏 <span> &gt; </span> </a></li> -->
			</ul>
		</div>
		<div class="box" style="weight: 10px; height: 425 margin-left: 10px;">
			<!-- 			<div class="box" -->
			<!-- 				style="weight: 100px; height: 425; border: 3px black dashed"> -->
			  <div>
			<table class="td">
			  
				<caption>
					<h2 id = "tittle">以下是您會員資料</h2>
				</caption>

				<tr>
					<td>會員編號 :</td>
					<td><%=custinfoVO.getCustid()%></td>
				</tr>
				<tr>
					<td>會員姓名 :</td>
					<td><%=custinfoVO.getCustname()%></td>
				</tr>
				<tr>
					<td>會員電話 :</td>
					<td><%=custinfoVO.getCusttel()%></td>
				</tr>
				<tr>
					<td>會員帳號 :</td>
					<td><%=custinfoVO.getCustaccount()%></td>
				</tr>
				<!-- 				<tr> -->
				<!-- 					<td>會員密碼 :</td> -->
				<%-- 					<td><%=custinfoVO.getCustpassword()%></td> --%>
				<!-- 				</tr> -->
				<tr>
					<td>註冊日期 :</td>
					<td><fmt:formatDate value="${custinfoVO.registrationtime}"
							pattern="yyyy-MM-dd HH:mm:ss" /></td>
				</tr>


			</table>

		</div>
	</div>
	</div>
	<!-- 	</div> -->


	<%-- 	<%@ include file="/front-end/cust/custindex_footer.jsp" %> --%>

</body>


</html>