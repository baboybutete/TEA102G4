<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.addetail.model.*"%>
<%@ page import="com.resinfo.model.*"%>
<%@	page import="java.sql.Timestamp"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="getBaseStirng.file"%>

<%
	ResInfoVO resinfoVO = (ResInfoVO)session.getAttribute("resInfoVO");
	AddetailVO addetailVO = (AddetailVO) request.getAttribute("addetailVO");
	
		if (addetailVO != null) {
			byte[] imgbyte = addetailVO.getAdimg();
			String imgString = getBaseString(imgbyte);
			pageContext.setAttribute("imgString", imgString);
		}
%>


<html>
<head>
<meta charset="UTF-8">
<title>新增廣告/推播</title>

<style>
form{
	margin-top: -480px;
    margin-left: 150px;
}
div.container div.row{
    margin-left: 100px;
}
div.container{
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
input[type=password]{
	padding:5px;
	font-size: 18px;
  	border-radius: 6px;
}
input[type=submit]{
	padding:5px;
	font-size: 18px;
  	border-radius: 6px;
}
select{
	padding:5px;
  	border-radius: 6px;
}
input:focus { 
    border-color: #719ECE;
    box-shadow: 0 0 20px #719ECE;
}
select:focus { 
    border-color: #719ECE;
    box-shadow: 0 0 20px #719ECE;
}
#error{
    font-size: 20px;
    color: red;
    margin-left: 240px;
    position: absolute;
    margin-top: 100px;
}
li{
	list-style-type:none;
}
</style>
</head>
<body>
 <div><%@ include file="/front-end/res/res_member_centre_header.jsp"%></div>
    
    
	<div id="error">
		<c:if test="${not empty errorMsgs}">
		<ul>
			<c:forEach var="msg" items="${errorMsgs}">
				<li>${msg}</li>
			</c:forEach>
		</ul>
		</c:if>
	</div>
	
	

	<jsp:useBean id="addetailSvc" class="com.addetail.model.AddetailService"></jsp:useBean>
	<jsp:useBean id="resInfoSvc" class="com.resinfo.model.ResInfoService"></jsp:useBean>
	
	
	
	<form action="<%=request.getContextPath() + "/front-end/addetail/addetail.do"%>" enctype="multipart/form-data" method="POST">
		
		
<div class="container">

  <div class="row">
  <div class="col-4">標題:</div>
    <div class="col-6">
      <input type="TEXT" name="adtitle" size="45px" placeholder="請輸入標題" value="<%= (addetailVO==null)? "" : addetailVO.getAdtitle()%>" />
    </div>
  </div><br>
  
  
  <div class="row">
  <div class="col-4">內容:</div>
    <div class="col-6">
      <input type="TEXT" name="adcontent" size="45px" placeholder="請輸入內容" value="<%= (addetailVO==null)? "" : addetailVO.getAdcontent()%>" />
    </div>
  </div><br>
  
  <div class="row">
  <div class="col-4">上架日期:</div>
    <div class="col-6">
		<input name="onshelftime" id="f_date1" type="text"/>
    </div>
  </div><br>
  
  <div class="row">
  <div class="col-4">下架日期:</div>
    <div class="col-6">
		<input name="offshelftime" id="f_date2" type="text"/>
    </div>
  </div><br>
  
  
  <div class="row">
  <div class="col-4">圖片:</div>
    <div class="col-6">
      <img id="pic" src="${imgString}" onerror="this.src='<%=request.getContextPath()%>/front-end/res/resinfo/images/null2.jpg'" width="200px" height="150px">
 		<input id="p_file" type="file" name="adimg" >
    </div>
  </div><br>
  
  
  
  <input type="hidden" name="adtype" value="廣告">
<!--   <div class="row"> -->
<!--   <div class="col-4">類型:</div> -->
<!--     <div class="col-6"> -->
<!--     	<select name="adtype"> -->
<%-- 			<option value="<%= (addetailVO==null)? "廣告" : addetailVO.getAdtype() %>">廣告</option>				 --%>
<%-- 			<option value="<%= (addetailVO==null)? "推播" : addetailVO.getAdtype() %>">推播</option> --%>
<!-- 	    </select> -->
<!--     </div> -->
<!--   </div><br> -->
  
  
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

<% 

java.sql.Timestamp onshelftime = null;
java.sql.Timestamp offshelftime = null;
java.sql.Timestamp addate = null;
  try {
	  onshelftime = addetailVO.getOnshelftime();
	  offshelftime = addetailVO.getOffshelftime();
	  addate = addetailVO.getAddate();
   } catch (Exception e) {
	  onshelftime = new java.sql.Timestamp(System.currentTimeMillis());
	  offshelftime = new java.sql.Timestamp(System.currentTimeMillis());
	  addate = new java.sql.Timestamp(System.currentTimeMillis());
   }
%>
    
	<script type="text/javascript" src="<%=request.getContextPath() + "/js/jquery-3.5.1.min.js"%>" charset="UTF-8"></script>
	<script src="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.full.js"></script>
	<script>
		$("#p_file").on("change", function() {
			let menuImg = $("#pic");

			let reader = new FileReader();
			file_picture = this.files[0];

			reader.readAsDataURL(this.files[0]);
			//預覽
			reader.addEventListener("load", function() {
				menuImg.attr("src", reader.result);
			})
		})
		
		
		$.datetimepicker.setLocale('zh');
        $('#f_date1').datetimepicker({
	       theme: '',              //theme: 'dark',
	       timepicker:true,       //timepicker:true,
	       step: 60,                //step: 60 (這是timepicker的預設間隔60分鐘)
	       format:'Y-m-d H:i',         //format:'Y-m-d H:i:s',
		   value: '<%=onshelftime%>',   // value:   new Date(),
           //disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
           //startDate:	            '2017/07/10',  // 起始日
           minDate:'<%=new java.sql.Timestamp(System.currentTimeMillis())%>',               //'-1970-01-01', // 去除今日(不含)之前
           //maxDate:               '+1970-01-01'  // 去除今日(不含)之後
        });
        
        $.datetimepicker.setLocale('zh');
        $('#f_date2').datetimepicker({
	       theme: '',              //theme: 'dark',
	       timepicker:true,       //timepicker:true,
	       step: 60,                //step: 60 (這是timepicker的預設間隔60分鐘)
	       format:'Y-m-d H:i',         //format:'Y-m-d H:i:s',
		   value: '<%=offshelftime%>',   // value:   new Date(),
           //disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
           //startDate:	            '2017/07/10',  // 起始日
           minDate:'<%=new java.sql.Timestamp(System.currentTimeMillis())%>',               //'-1970-01-01', // 去除今日(不含)之前
           //maxDate:               '+1970-01-01'  // 去除今日(不含)之後
        });
		
	</script>
</body>



<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.css" />
<style>
  .xdsoft_datetimepicker .xdsoft_datepicker {
           width:  300px;   /* width:  300px; */
  }
  .xdsoft_datetimepicker .xdsoft_timepicker .xdsoft_time_box {
           height: 151px;   /* height:  151px; */
  }
</style>
</html>
