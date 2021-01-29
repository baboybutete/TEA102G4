<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
<head>
<title>餐廳會員中心</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/css/res_member_centre.css">
</head>
<body>
	<div><%@ include file="/front-end/res/res_member_centre_header.jsp"%></div>

	
    <div class="resinfo"><%@ include file="update_resinfo_input2.jsp"%></div>
<%--     <div class="resinfo"><jsp:include page="update_resinfo_input2.jsp"/></div> --%>
    
    <div><%@ include file="/front-end/res/res_member_centre_footer.jsp"%></div>
</body>
</html>