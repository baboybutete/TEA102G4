<%@page import="com.resinfo.model.ResInfoVO"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="com.carousel.controller.*"%>
<%@ page import="com.carousel.model.*"%>
<%@ include file="getBaseStirng.file"%>

<%
// 	CarouselService carouselSvc = new CarouselService();
// 	String str = request.getParameter("resid");
// 	List<CarouselVO> list = carouselSvc.getOneRes(str);
// 	pageContext.setAttribute("list",list);
%>
<%
	
	CarouselService carouselSvc = new CarouselService();
 	ResInfoVO resinfoVO =(ResInfoVO)session.getAttribute("resInfoVO");
	List<CarouselVO> list = carouselSvc.getOneRes(resinfoVO.getResid());
	pageContext.setAttribute("list",list);

%>
<html>
<head>
<title>所有餐廳輪播圖資料</title>
<link href="<%=request.getContextPath()+"/css/slider.css"%>" rel="stylesheet">

<style>
  table {
    width: 1050px;
    background-color: #CCCCCF;
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
 div.info {
    position: relative;
    margin-top: 10px;
    margin-left: 205px;
}
  footer{
	position: relative;
    margin-top: 200px;
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
#banner{
    position: relative;
    margin-top: -565px;
    width: 1050px;
    height: 300px;
    margin-left: 205px;
  
  }
#slider .viewer .reel .slide {
	width: 1050px;
    height: 300px;
}
</style>
</head>
<body>
<div><%@ include file="/front-end/res/res_member_centre_header.jsp"%></div>
    <!-- ----------------------------------------輪播圖區塊------------------------------------ -->
 	 
    <div id="banner">
        <div id="slider">
          <div class="viewer">
          <c:forEach var="carouselVO" items="${list}">
		
		<%  
			CarouselVO carouselVO = (CarouselVO)pageContext.getAttribute("carouselVO");
			if(carouselVO != null){
				byte[] imgbyte = carouselVO.getCarouselpic();
				String imgString =  getBaseString(imgbyte);
				pageContext.setAttribute("imgString",imgString);
			}
		%>  
		
           <div class="reel">
              <div class="slide">
                <a class="link" href="#">Full story ...</a> 
                <img src="${imgString}" alt="" class="sliderimg" onerror="this.src='<%=request.getContextPath()%>/front-end/res/carousel/images/null2.jpg'"/> </div>
            </div>
            </c:forEach >
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
      
      <!-- ----------------------------------------輪播圖區塊------------------------------------ -->

<div class="info">
		<table>
		<tr>
			<th>輪播圖</th>
			<th>修改日期</th>
			<th>圖片刪除</th>
			<th>圖片修改</th>
		</tr>
	<c:forEach var="carouselVO" items="${list}" >
		<%  
			CarouselVO carouselVO = (CarouselVO)pageContext.getAttribute("carouselVO");
			if(carouselVO != null){
				byte[] imgbyte = carouselVO.getCarouselpic();
				String imgString =  getBaseString(imgbyte);
				pageContext.setAttribute("imgString",imgString);
			}
		%>
		<tr>
			<td><img src="${imgString}" onerror="this.src='<%=request.getContextPath()%>/front-end/res/resinfo/images/null2.jpg'" width="200px" height="100px"></td>
			<td><fmt:formatDate value="${carouselVO.adddate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			<td>
					<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/carousel/CarouselServlet" style="margin-bottom: 0px;">
						<input type="submit" value="圖片刪除">
						<input type="hidden" name="action" value="delete">
						<input type="hidden" name="carouselid" value="${carouselVO.carouselid}">
					</form>
				</td>
			<td>
				<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/carousel/CarouselServlet" style="margin-bottom: 0px;">
  				<input type="submit" value="圖片修改">
    			<input type="hidden" name="carouselid"  value="${carouselVO.carouselid}">
				<input type="hidden" name="action"	value="getOne_For_Update">
				</FORM>
			</td>	
		</tr>

	</c:forEach>
	</table>
</div>
<div class="addmeal">
	
					<%CarouselVO carouselVO = (CarouselVO)pageContext.getAttribute("carouselVO"); %>
					<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/carousel/CarouselServlet" style="margin-bottom: 0px;">
						<input type="submit" value="新增輪播圖">
						<input type="hidden" name="action" value="getOne_For_Insert">
						<input type="hidden" name="resid" value="${carouselVO.resid}">
					</form>
	</div>

<!-- 先藏footer不然浮來浮去我很無助 -->
<div><%@ include file="/front-end/res/res_member_centre_footer.jsp"%></div>
</body>
<script src="<%=request.getContextPath()+"/js/er.twzipcode.data.js"%>"></script>
<script src="<%=request.getContextPath()+"/js/er.twzipcode.min.js"%>"></script>
<script src="<%=request.getContextPath()+"/js/jquery-3.5.1.min.js"%>"></script>

<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
<script src="<%=request.getContextPath()+"/js/jquery.slidertron-1.1.js"%>"></script>
<script>
$("#p_file").on("change", function() {
	let resInfoImg = $("#pic");
	let reader = new FileReader();
	file_picture = this.files[0];
	reader.readAsDataURL(this.files[0]);
	reader.addEventListener("load", function() {
	resInfoImg.attr("src", reader.result);
$("#originalPic").remove();
	})
})
</script>
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
</html>