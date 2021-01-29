<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.carousel.model.*"%>
<%@ page import="java.util.*"%>
<%@ include file="getBaseStirng.file"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
CarouselVO carouselVO = (CarouselVO) request.getAttribute("carouselVO"); 
%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>餐廳輪播圖修改</title>
<style>
form{
	margin-top: -500px;
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
    margin-top: 220px;
}
#error{
	font-size: 20px;
    color: red;
    margin-left: 182px;
    position: absolute;
    margin-top: 120px;
}
li{
list-style-type: none;
}
</style>

</head>
<body>
<div><%@ include file="/front-end/res/res_member_centre_header.jsp"%></div>
    

<!-- <h3>資料修改:</h3> -->

<%-- 錯誤表列 --%>
<div id="error">
<c:if test="${not empty errorMsgs}">
<!-- 	<font style="color:red">請修正以下錯誤:</font> -->
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>
</div>

<FORM METHOD="post" ACTION="CarouselServlet" name="form1" enctype="multipart/form-data">
<%  
	if(carouselVO != null){
		byte[] imgbyte = carouselVO.getCarouselpic();
		String imgString = getBaseString(imgbyte);
		pageContext.setAttribute("imgString",imgString);
	}
%>

<div class="container">
<!--    <div class="row"> -->
<!--     <div class="col-3">輪播圖編號:</div> -->
<%--     <div class="col-4"><%=carouselVO.getCarouselid()%></div> --%>
<!--     </div><br> -->
<!--     <div class="row"> -->
<!--     <div class="col-3">餐廳編號:</div> -->
<!--     <div class="col-4"> -->
<!--     	<input type="TEXT" name="resid" size="45px" placeholder="請輸入餐廳編號" -->
<%-- 			 value="<%= (carouselVO==null)? "" : carouselVO.getResid()%>" /> --%>
<!--     </div> -->
<!--   </div><br> -->
  <div class="row">
  <div class="col-3">輪播圖:</div>
    <div class="col-4">
      <img id="pic" src="${imgString}" onerror="this.src='<%=request.getContextPath()%>/front-end/res/carousel/images/null2.jpg'" width="200px" height="100px">
 		<input id="p_file" type="file" name="carouselpic" >
    </div>
    </div><br>
    <div class="row">
  <div class="col-3">加入日期:</div>
    <div class="col-4"><fmt:formatDate value="${carouselVO.adddate}" pattern="yyyy-MM-dd HH:mm"/></div>
</div><br>
<div class="col-6">
  <div class="row">
  <input type="hidden" name="action" value="update">
<input type="hidden" name="carouselid" value="<%=carouselVO.getCarouselid()%>">
<input type="hidden" name="resid" value="<%=carouselVO.getResid()%>">
<input type="submit" value="送出修改">
</div>
</div>
</div>
</FORM>
  
  <div><%@ include file="/front-end/res/res_member_centre_footer.jsp"%></div>
</body>



<!-- =========================================以下為 datetimepicker 之相關設定========================================== -->

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.css" />
<script type="text/javascript" src="<%=request.getContextPath()+"/js/jquery-3.5.1.min.js"%>" charset="UTF-8"></script>
<%-- <script src="<%=request.getContextPath()%>/datetimepicker/jquery.js"></script> --%>
<script src="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.full.js"></script>

<style>
  .xdsoft_datetimepicker .xdsoft_datepicker {
           width:  300px;   /* width:  300px; */
  }
  .xdsoft_datetimepicker .xdsoft_timepicker .xdsoft_time_box {
           height: 151px;   /* height:  151px; */
  }
</style>

<script>

	$("#p_file").on("change", function() {
		let carouselpic = $("#pic");
		let reader = new FileReader();
		file_picture = this.files[0];
		reader.readAsDataURL(this.files[0]);
		reader.addEventListener("load", function() {
			carouselpic.attr("src", reader.result);
			$("#originalPic").remove();
		})
	})

	$.datetimepicker.setLocale('zh');
	$('#f_date1').datetimepicker({
		theme : '', //theme: 'dark',
		timepicker : true, //timepicker:true,
		step : 30, //step: 60 (這是timepicker的預設間隔60分鐘)
		format : 'Y-m-d H:i:s', //format:'Y-m-d H:i:s',
		value : '<%=carouselVO.getAdddate()%>',  // value:   new Date(),
           //disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
           //startDate:	            '2017/07/10',  // 起始日
           //minDate:               '-1970-01-01', // 去除今日(不含)之前
           //maxDate:               '+1970-01-01'  // 去除今日(不含)之後
        });
        
        
   
        // ----------------------------------------------------------以下用來排定無法選擇的日期-----------------------------------------------------------

        //      1.以下為某一天之前的日期無法選擇
        //      var somedate1 = new Date('2017-06-15');
        //      $('#f_date1').datetimepicker({
        //          beforeShowDay: function(date) {
        //        	  if (  date.getYear() <  somedate1.getYear() || 
        //		           (date.getYear() == somedate1.getYear() && date.getMonth() <  somedate1.getMonth()) || 
        //		           (date.getYear() == somedate1.getYear() && date.getMonth() == somedate1.getMonth() && date.getDate() < somedate1.getDate())
        //              ) {
        //                   return [false, ""]
        //              }
        //              return [true, ""];
        //      }});

        
        //      2.以下為某一天之後的日期無法選擇
        //      var somedate2 = new Date('2017-06-15');
        //      $('#f_date1').datetimepicker({
        //          beforeShowDay: function(date) {
        //        	  if (  date.getYear() >  somedate2.getYear() || 
        //		           (date.getYear() == somedate2.getYear() && date.getMonth() >  somedate2.getMonth()) || 
        //		           (date.getYear() == somedate2.getYear() && date.getMonth() == somedate2.getMonth() && date.getDate() > somedate2.getDate())
        //              ) {
        //                   return [false, ""]
        //              }
        //              return [true, ""];
        //      }});


        //      3.以下為兩個日期之外的日期無法選擇 (也可按需要換成其他日期)
        //      var somedate1 = new Date('2017-06-15');
        //      var somedate2 = new Date('2017-06-25');
        //      $('#f_date1').datetimepicker({
        //          beforeShowDay: function(date) {
        //        	  if (  date.getYear() <  somedate1.getYear() || 
        //		           (date.getYear() == somedate1.getYear() && date.getMonth() <  somedate1.getMonth()) || 
        //		           (date.getYear() == somedate1.getYear() && date.getMonth() == somedate1.getMonth() && date.getDate() < somedate1.getDate())
        //		             ||
        //		            date.getYear() >  somedate2.getYear() || 
        //		           (date.getYear() == somedate2.getYear() && date.getMonth() >  somedate2.getMonth()) || 
        //		           (date.getYear() == somedate2.getYear() && date.getMonth() == somedate2.getMonth() && date.getDate() > somedate2.getDate())
        //              ) {
        //                   return [false, ""]
        //              }
        //              return [true, ""];
        //      }});
        
</script>
</html>