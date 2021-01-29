<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.carousel.controller.*"%>
<%@ page import="com.carousel.model.*"%>
<%@ include file="getBaseStirng.file"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
CarouselVO carouselVO = (CarouselVO) request.getAttribute("carouselVO"); 
if(carouselVO != null){
	byte[] imgbyte = carouselVO.getCarouselpic();
	String imgString =  getBaseString(imgbyte);
	pageContext.setAttribute("imgString",imgString);
}
%>

<html>
<head>
<title>餐聽輪播圖資料 - listOneCarousel.jsp</title>
<link href="<%=request.getContextPath()+"/css/slider.css"%>" rel="stylesheet">
<style>
div.info {
    position: relative;
	margin-top: 20px;
	margin-left: 170px;
}
div.container div.row{
    margin-left: 20px;
}
div.container{
}
div.container div.col-3{
	font-size: 25px;
  	text-align: left;
}
div.container div.col-4{
	font-size: 20px;
  	text-align: left;
}
input[type=text]{
	width: 300px;
	padding:5px;
	font-size: 18px;
}
input[type=submit]{
	padding:5px;
	font-size: 18px;
  	border-radius: 6px;
}
footer{
	position: relative;
    margin-top: 100px;
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
            <div class="reel">
              <div class="slide">
                <a class="link" href="#">Full story ...</a> 
                <img src="${imgString}" alt="" class="sliderimg" onerror="this.src='<%=request.getContextPath()%>/front-end/res/carousel/images/null2.jpg'"/> </div>
              <div class="slide">
                <a class="link" href="#">Full story ...</a> 
                <img src="${imgString}" alt="" class="sliderimg" onerror="this.src='<%=request.getContextPath()%>/front-end/res/carousel/images/null2.jpg'"/> </div>
              <div class="slide">
                <a class="link" href="#">Full story ...</a> 
                <img src="${imgString}" alt="" class="sliderimg" onerror="this.src='<%=request.getContextPath()%>/front-end/res/carousel/images/null2.jpg'"/> </div>
            </div>
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
<!-- <h3>餐廳輪播圖:</h3> -->
<div class="info">
<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/resinfo/ResInfoServlet" name="form1" enctype="multipart/form-data">
<div class="container">
<!--    <div class="row"> -->
<!--     <div class="col-3">輪播圖編號:</div> -->
<%--     <div class="col-4"><%=carouselVO.getCarouselid()%></div> --%>
<!--     </div><br> -->
<!--     <div class="row"> -->
<!--     <div class="col-3">餐廳編號:</div> -->
<%--     <div class="col-4"><%=carouselVO.getResid()%></div> --%>
<!--   </div><br> -->
  <div class="row">
  <div class="col-3">輪播圖:</div>
    <div class="col-4">
      <img src="${imgString}" onerror="this.src='<%=request.getContextPath()%>/front-end/carousel/images/null2.jpg'" width="200px" height="100px">
    </div>
    </div><br>
    <div class="row">
  <div class="col-3">加入日期:</div>
    <div class="col-4"><fmt:formatDate value="${carouselVO.adddate}" pattern="yyyy-MM-dd HH:mm"/></div>
  </div>
</div>
</FORM>

<jsp:useBean id="carouselSvc" scope="page" class="com.carousel.model.CarouselService" />
<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/carousel/CarouselServlet" style="margin-bottom: 0px;">
  <div class="container">
  <div class="col-3">
  <div class="row">
  	<input type="submit" value="資料修改">
    <input type="hidden" name="carouselid"  value="${carouselVO.carouselid}">
	<input type="hidden" name="action"	value="getOne_For_Update">
  </div>
  </div>
  </div>
</FORM>
</div>

<div><%@ include file="/front-end/res/res_member_centre_footer.jsp"%></div>
</body>
<script src="<%=request.getContextPath()+"/js/er.twzipcode.data.js"%>"></script>
<script src="<%=request.getContextPath()+"/js/er.twzipcode.min.js"%>"></script>
<script src="<%=request.getContextPath()+"/js/jquery-3.5.1.min.js"%>"></script>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
<script src="<%=request.getContextPath()+"/js/jquery.slidertron-1.1.js"%>"></script>
<script>
$("#p_file").on("change", function() {
	let carouselPic = $("#pic");
	let reader = new FileReader();
	file_picture = this.files[0];
	reader.readAsDataURL(this.files[0]);
	reader.addEventListener("load", function() {
	carouselPic.attr("src", reader.result);
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