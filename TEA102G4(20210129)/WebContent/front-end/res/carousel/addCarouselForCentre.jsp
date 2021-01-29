<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="com.carousel.model.*"%>
<%@ page import="java.util.*"%>
<%@ include file="getBaseStirng.file"%>
<%@	page import="com.resinfo.model.ResInfoVO"%>

<%
// CarouselVO carouselVO = (CarouselVO) request.getAttribute("carouselVO"); 
%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>新增輪播圖</title>

<style>
form{
	margin-top: 80px;
}
div.container div.row{
    margin-left: 100px;
}
div.container{
	position: relative;
    margin-top: -530px;
    margin-left: 100px;
}
div.container div.col-4{
	font-size: 25px;
  	text-align: justify;
}
input[type=text]{
	padding:5px;
	font-size: 18px;
  	border-radius: 6px;
}
input[type=submit]{
	padding:5px;
	font-size: 18px;
  	border-radius: 6px;
}
input:focus { 
    border-color: #719ECE;
    box-shadow: 0 0 20px #719ECE;
}
</style>

<style>
  table {
	background-color: #CCCCCF;
	margin-top: 1px;
	margin-bottom: 1px;
  }
  table, th, td {
  	width: 400px;
    border: 0px solid #CCCCFF;
  }
  th, td {
  	width: 80px;
    padding: 1px;
  }
</style>
</head>
<div><%@ include file="/front-end/res/res_member_centre_header.jsp"%></div>


<%-- 錯誤表列 --%>
<div id="error">
	<c:if test="${not empty errorMsg}">
		<ul>
			<c:forEach var="msg" items="${errorMsg}">
				<li>${msg}</li>
			</c:forEach>
		</ul>
	</c:if>
	</div>
	
	<%			
				ResInfoVO resinfoVO = (ResInfoVO)session.getAttribute("resInfoVO");
				CarouselVO carouselVO = (CarouselVO) request.getAttribute("carouselVO");
				if (carouselVO != null) {
					byte[] imgbyte = carouselVO.getCarouselpic();
					String imgString = getBaseString(imgbyte);
					pageContext.setAttribute("imgString", imgString);
				}
	%>
<jsp:useBean id="resInfoSercice" class="com.resinfo.model.ResInfoService"></jsp:useBean>
<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/carousel/CarouselServlet" name="form1" enctype="multipart/form-data">

<div class="container">
  <div class="row">
  <div class="col-4">輪播圖:</div>
    <div class="col-6">
      <img id="pic" src="${imgString}" onerror="this.src='<%=request.getContextPath()%>/images/null2.jpg'" width="200px" height="150px">
 		<input id="p_file" type="file" name="carouselpic" multiple/>
    </div>
    </div><br>
    <div class="col-6">
  <div class="row">
  	<input type="hidden" name="resid" value="<%=resinfoVO.getResid()%>">
	<input type="hidden" name="action" value="insert">
	<input type="submit" value="送出新增">
   </div>
  </div>
</div>
</FORM>
<div><%@ include file="/front-end/res/res_member_centre_footer.jsp"%></div>
</body>



<!-- =========================================以下為 datetimepicker 之相關設定========================================== -->

<% 
  Timestamp adddate = null;
  try {
	  adddate = carouselVO.getAdddate();
   } catch (Exception e) {
	   adddate = new Timestamp(System.currentTimeMillis());
   }
%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.css" />
<%-- <script src="<%=request.getContextPath()%>/datetimepicker/jquery.js"></script> --%>
<script src="<%=request.getContextPath()+"/js/jquery-3.5.1.min.js"%>"></script>
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
			let carouselPic = $("#pic");
			let reader = new FileReader();
			file_picture = this.files[0];
			reader.readAsDataURL(this.files[0]);
			reader.addEventListener("load", function() {
				carouselPic.attr("src", reader.result);
		$("#originalPic").remove();
			})
		})
		
		
        $.datetimepicker.setLocale('zh');
        $('#f_date1').datetimepicker({
	       theme: '',              //theme: 'dark',
	       timepicker:true,       //timepicker:true,
	       step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘)
	       format:'Y-m-d H:i:s',         //format:'Y-m-d H:i:s',
		   value: '<%=adddate%>', // value:   new Date(),
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