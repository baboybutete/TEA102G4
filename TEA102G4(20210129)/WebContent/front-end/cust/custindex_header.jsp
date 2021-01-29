<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.custinfo.model.*"%>
<%@ page import="java.util.*"%>
<%
	CustinfoVO custinfoVO = (CustinfoVO) session.getAttribute("custinfoVO"); //CustinfoServlet.java(Concroller), 存入req的empVO物件
%>

<link rel="stylesheet" href="<%=request.getContextPath()%>/css/cust.css" />
    <header class=head>
        <div class="logo"><img src="<%=request.getContextPath()%>/images/Asset 19.png" alt="">
        </div>
        <nav>
            <div class="navitem">
                
                <a class=navitem__text href="<%=request.getContextPath()%>/index_plat.jsp">回首頁</a>
            </div>
            <div class="navitem">
               
                <a class=navitem__text href="<%=request.getContextPath()%>/logoutservlet/logoutservlet.do">登出</a>
            </div>
            <div class="navitem">
                
                <a class=navitem__text href="<%=request.getContextPath()%>/front-end/cust/custindex.jsp">會員中心</a>
                </div>
            <div class="cust_pic">
            
				<%
					String photoString = null;
					try {
						Base64.Encoder encoder = Base64.getEncoder();
						byte[] b = custinfoVO.getCustpicture();
						if(b != null){
							photoString = encoder.encodeToString(custinfoVO.getCustpicture());
							pageContext.setAttribute("photoString",photoString);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				%>
				<c:choose>
					<c:when test="${not empty photoString}">
						<img src="data:image;base64,<%=photoString%>" width="65" height="50">
					</c:when>
					<c:otherwise>
						<img width="65" height="50" src="//d1gpbxqmt7wq2i.cloudfront.net/asset/mobile/images/default_head.png">
					</c:otherwise>
				</c:choose>
			</div>
		</nav>
	</header>