<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.custinfo.model.*" %>
<%@ page import="com.addetail.model.*" %>
<%@ page import="java.util.* " %>
<!DOCTYPE html>
<html>
<head>
<title>sit down please</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/style1.css">
<link href="<%=request.getContextPath()+"/css/slider.css"%>" rel="stylesheet">

<style>
 #slider .viewer .reel .slide { 
     position: relative; 
     width: 1464px;
     height: 400px;
 }
</style>
</head>
<body>
	<%@ include file="/index_plat_header.jsp" %>
	<!-- banner-->
	<div id="banner">
	<jsp:useBean id="addetailService" class="com.addetail.model.AddetailService"></jsp:useBean>
	     <div id="slider">
	          <div class="viewer">
	           <c:forEach var="addetailVO" items="${addetailService.all}">
	           	<c:if test="${addetailVO.reviewstatus eq '審核通過' }">
	       			<div class="reel">
	         			<div class="slide">
			         			<a href="<%=request.getContextPath()%>/resinfo/ResInfoServlet?action=click_oneAdimg_for_Display&resid=${addetailVO.resid}">
								<img class="sliderimg res" src="<%=request.getContextPath()%>/resinfo/ResInfoServlet?action=get_one_addetailImg&adid=${addetailVO.adid}" alt="" >
								</a>
	                	</div>
	            	</div>
	            </c:if>
	            </c:forEach>
	            
	            
	            
	            <c:if test="${not empty addetailService.all}">
	            	<jsp:useBean id="list" class="java.util.ArrayList"/>
<!-- 	           		計算vo裡面有幾個審核通過，為0的話印出if -->
						<c:forEach var="vo" items="${addetailService.all}">
							<c:if test="${vo.reviewstatus eq '審核通過' || list.size() ne 0}">
								<input type="hidden" value="${list.add(vo)}"></input>
							</c:if>
						</c:forEach>
						<c:if test="${empty list}">
							<div class="reel">
	         					<div class="slide">
			         				<a href="<%=request.getContextPath()%>/index_plat.jsp">
								<img class="sliderimg res" src="<%=request.getContextPath()%>/images/111.jpg">
									</a>
	                			</div>
	            			</div>
						</c:if>		
	            </c:if>
	          </div>
	          
	          <div class="indicator">
	            <ul>
	              <li class="active">1</li>
	              <li>2</li>
	              <li>3</li>
	            </ul>
	          </div>
	     </div>
	</div>

	<!--核心區域-->
	<jsp:useBean id="resInfoService" class="com.resinfo.model.ResInfoService"></jsp:useBean>
	<div class="box w">
		<div class="box-hd">
			<h3>優質精選</h3>
			<a href="#">查看全部</a>
		</div>
		<div class="box-bd">
			<ul class="clearfix">
			<c:forEach var="resInVO" items="${resInfoService.all}">
			<c:if test="${resInVO.status eq '使用'}">
				<li><a href="<%=request.getContextPath()%>/resinfo/ResInfoServlet?action=getOne_For_Display_By_Client&resid=${resInVO.resid}">
					<img src="<%=request.getContextPath()%>/resinfo/ResInfoServlet?action=getOneImg&resid=${resInVO.resid}" alt=""> 
					<h4>${resInVO.resname}</h4>
					</a>
				</li>
			</c:if>
			</c:forEach>
				
			</ul>
		</div>
	</div>
	
	
	<%@ include file="/mapForIndex_plat.jsp" %>
	<!--footer -->
	<%@ include file="/index_plat_footer.jsp" %>
	
<script src="<%=request.getContextPath()+"/js/jquery-3.5.1.min.js"%>"></script>
<script src="<%=request.getContextPath()+"/js/jquery.slidertron-1.1.js"%>"></script>
<script type="text/javascript">
          $('#slider').slidertron({
            viewerSelector: '.viewer',
            reelSelector: '.viewer .reel',
            slidesSelector: '.viewer .reel .slide',
            advanceDelay: 3000,
            speed: 'slow',
            navPreviousSelector: '.previous-button',
            navNextSelector: '.next-button',
            indicatorSelector: '.indicator ul li',
            slideLinkSelector: '.link'
          });
</script>
</body>
</html>