<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<div class="header w">
			<div class="logo">
				<a href='<%=request.getContextPath()%>/back-end/custinfo/listAllCustinfo.jsp'><img src="<%=request.getContextPath()%>/images/Asset 19.png"></a>
				<a class="login_in_out" href='<%=request.getContextPath()%>/logout/logout.do'>登出</a>
			</div>
</div>
		<!-- 側邊攔 -->
<div class="subnav">
		<ul>
			<h3>消費者資訊管理</h3>
			<li><a href='<%=request.getContextPath()%>/back-end/custinfo/listAllCustinfo.jsp'>查詢消費者</a></li>
			<li><a href='<%=request.getContextPath()%>/back-end/orderinfo/listAllOrderinfo.jsp'>查詢消費者訂單 </a></li>
			<h3>餐廳資訊管理</h3>
			<li><a href='<%=request.getContextPath()%>/back-end/resinfo/listAllResInfo.jsp'>查詢餐廳</a></li>
			<li><a href='<%=request.getContextPath()%>/back-end/resinfo/res_coordinate.jsp'>查詢餐廳座位圖</a></li>
			<li><a href='<%=request.getContextPath()%>/back-end/addetail/listAllAddetail.jsp'>查詢餐廳廣告/推播</a></li>
			<h3>後台管理員管理</h3>
			<li><a href="<%=request.getContextPath()%>/back-end/admininfo/listAllAdmininfo.jsp">查詢後台管理員</a></li>
			<li><a href="<%=request.getContextPath()%>/back-end/admininfo/addAdmininfo.jsp">新增後台管理員</a></li>
		</ul>
</div>