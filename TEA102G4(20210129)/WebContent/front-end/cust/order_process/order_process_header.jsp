<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.custinfo.model.*" %>
<%@ page import="java.util.* " %>
<%@ include file="getBaseStirng.file" %>
<%@ page import="com.shoppingcart.model.*"%>
<%@page import="com.resinfo.model.ResInfoVO"%>

	<%
		 session.getAttribute("account");
		 CustinfoVO custinfoVO = (CustinfoVO)session.getAttribute("custinfoVO");
		
	%>
<style>
.header .member-avatar img{
height:40px;
}
</style>
<!DOCTYPE html>
<div class="header">
        <div class="header-logo">
            <a href="<%=request.getContextPath()%>/index_plat.jsp">
                <i class="brand-logo">
                    <img src="<%=request.getContextPath()%>/images/logo.png">
                </i>
            </a>
        </div>
        <div class="header-element header-goback pull-left">
            <a href="javascript:history.go(-1)">
                <div class="header-btn">
                    <img src="<%=request.getContextPath()%>/images/back (1).png">
                    <span class="goback-text show-md-inline">上一頁</span>
                </div>
            </a>
        </div>
        <div class="header-element header-search pull-left">
            <a href="<%=request.getContextPath()%>/index_plat.jsp">
                <button class="searchbut"></button>
                <div class="header-btn search-bar show-md-inline-block">
                    <span>想定哪一間餐廳?</span>
                </div>
            </a>

        </div>
        <div class="header-element header-more header-has-dropdown pull-right show-sm-block">
            <div class="header-btn">
                <button class="hamburger hamburger--arrow-r" type="button">
                    <span class="hamburger-box">
                        <span class="hamburger-inner"></span>
                    </span>
                </button>

            </div>
            <div class="header-dropdown-menu">
                <ul class="menu-list">
                    <li>
                        <a class="nav-link" href="<%=request.getContextPath()%>/index_plat.jsp">
                            <span class="link-text">首頁</span>
                        </a>
                    </li>
                    <li>
                        <a class="nav-link" target="_blank" href="<%=request.getContextPath()%>/member/member.do">
                            <span class="link-text">會員中心</span>
                        </a>
                    </li>
                </ul>
            </div>
</div>
        <div class="shoppingcar">
			<a href="<%=request.getContextPath()%>/front-end/cust/shoppingcart/Shoppingcar.jsp" accesskey="3"><img src="<%=request.getContextPath()%>/images/shopping car.jpg" class="shoppingcarimg" ></a>
		</div>
		<div class="home">
			<c:choose>
				<c:when test="${not empty account}">
					<a
					href="<%=request.getContextPath()%>/logoutservlet/logoutservlet.do">登出</a>
				</c:when>
				<c:otherwise>
					<a
					href="<%=request.getContextPath()%>/login-html/login/custLogin.jsp">登入</a>
				</c:otherwise>
			</c:choose>
		</div>

 <div class="header-element header-member header-has-dropdown pull-right show-sm-block">
            					<%-- 	<%  

			
			if(custinfoVO != null){
				byte[] imgbyte = custinfoVO.getCustpicture();	
				String imgString =  getBaseString(imgbyte);
				pageContext.setAttribute("imgString",imgString);
			}		
			%>--%>
          <div class="header-btn">
            <a href="<%=request.getContextPath()%>/member/member.do">
            <c:choose>
				<c:when test="${not empty sessionScope.custinfoVO}">
					<span class="element-text ez-blue"><%=custinfoVO.getCustname()%></span>
				</c:when>
				<c:when test="${not empty sessionScope.resInfoVO}">
					<span class="element-text ez-blue">${sessionScope.resInfoVO.resname}</span>
				</c:when>
				<c:otherwise >
					<span><img src="//d1gpbxqmt7wq2i.cloudfront.net/asset/mobile/images/default_head.png"></span>
				</c:otherwise>
			</c:choose>
<%-- 			<span class="element-text ez-blue"><%= (custinfoVO==null)? "安安安" : custinfoVO.getCustname()%></span> --%>
			<span class="member-avatar">
			<c:choose>
				<c:when test="${not empty sessionScope.custinfoVO}">
					<img src="<%=request.getContextPath()%>/custinfo/custinfo.do?action=getCustImages&custid=${sessionScope.custinfoVO.custid}" onerror="this.src='<%=request.getContextPath()%>/images/user.jpg'">
				</c:when>
				<c:when test="${not empty sessionScope.resInfoVO}">
					<img src="<%=request.getContextPath()%>/resinfo/ResInfoServlet?action=getOneImg&resid=${sessionScope.resInfoVO.resid}" onerror="this.src='<%=request.getContextPath()%>/images/user.jpg'"> 
				</c:when>
				<c:otherwise >
					<span></span>
				</c:otherwise>
			</c:choose>
			</span>
<%-- 			<span class="member-avatar"> 
 			<img src="${imgString}">
 			</span>  --%>
			</a>
         </div>
            
       </div>
 </div>
 
 
    