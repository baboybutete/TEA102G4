<%@page import="com.resinfo.model.ResInfoVO"%>
<%@page import="com.custinfo.model.CustinfoVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
	<%
		
		CustinfoVO custinfoVO = (CustinfoVO)session.getAttribute("custinfoVO");
	%>
    <!-- 頭部 -->
    <div class="header">
        <!-- logo部分 -->
        <div class="log">
            <a href="<%=request.getContextPath()%>/index_plat.jsp"><img
                    src="<%=request.getContextPath()%>/images/Asset 19.png" alt=""></a>
        </div>

        <!-- 搜索模塊 -->
        <form action="<%=request.getContextPath()%>/map/MapServlet" method="POST" enctype="mutipart/form-data">
         	<div class="searchmap">
            	<div class="lookmap"></div>
            	<div class="searchinput">
                	<input type="text" name="resaddid" placeholder="請輸入想要搜尋的地點">
            	</div>
        	</div>

        	<div class="searchfood">
            	<div class="lookfood"></div>
            	<div class="searchinput">
                	<input type="text" name="classname" placeholder="請輸入食物類型">
            	</div>
        	</div>
        	<button class="searchbut"></button>
        	<input type="hidden" name="action" value="search">
        </form>
       
        
    
		<div class="user">
			<a href="<%=request.getContextPath()%>/member/member.do">
			<c:choose>
				<c:when test="${not empty sessionScope.custinfoVO}">
					<img src="<%=request.getContextPath()%>/custinfo/custinfo.do?action=getCustImages&custid=${sessionScope.custinfoVO.custid}" onerror="this.src='//d1gpbxqmt7wq2i.cloudfront.net/asset/mobile/images/default_head.png'">
				</c:when>
				<c:when test="${not empty sessionScope.resInfoVO}">
					<img src="<%=request.getContextPath()%>/resinfo/ResInfoServlet?action=getOneImg&resid=${sessionScope.resInfoVO.resid}" onerror="this.src='//d1gpbxqmt7wq2i.cloudfront.net/asset/mobile/images/default_head.png'"> 
				</c:when>
				<c:otherwise >
					<span class="123"></span>
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${not empty sessionScope.custinfoVO}">
					<span ><%=custinfoVO.getCustname()%></span>
				</c:when>
				<c:when test="${not empty sessionScope.resInfoVO}">
					<span >${sessionScope.resInfoVO.resname}</span>
				</c:when>
				<c:otherwise >
					<span><img src="//d1gpbxqmt7wq2i.cloudfront.net/asset/mobile/images/default_head.png"></span>
				</c:otherwise>
			</c:choose>
			
			
			
<!-- 			//d1gpbxqmt7wq2i.cloudfront.net/asset/mobile/images/default_head.png -->
			</a>  
		</div>
		<div class="home">
			<c:choose>
				<c:when test="${not empty account}">
					<a
					href="<%=request.getContextPath()%>/logoutservlet/logoutservlet.do">登出</a>
				</c:when>
				<c:otherwise>
					<a href="<%=request.getContextPath()%>/login-html/login/custLogin.jsp">登入</a>
				</c:otherwise>
			</c:choose>
		</div>
		<div class="manager">
			<a href="<%=request.getContextPath()%>/member/member.do">會員中心</a>
		</div>
		<div class="shoppingcar">
			<a href="<%=request.getContextPath()%>/front-end/cust/shoppingcart/Shoppingcar.jsp" accesskey="3"><img src="<%=request.getContextPath()%>/images/shopping car.jpg" class="shoppingcarimg" ></a>
		</div>
    </div>
