<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.resinfo.model.*"%>
<%@ page import="java.util.*"%>
<%@ include file="getBaseStirng.file"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
ResInfoVO resInfoVO = (ResInfoVO) request.getAttribute("resInfoVO");
%>

<style>
form{
	margin-top: 45px;
}
div.container div.row{
    margin-left: 20px;
}
div.container{
}
div.container div.col-2{
	font-size: 20px;
  	text-align: justify;
}
div.container div.col-1{
	border: 1px solid lightgray;
	font-size: 20px;
  	text-align: justify;
}
div.container div.col-4{
	font-size: 20px;
  	text-align: justify;
}
div.container div.col-5{
	font-size: 25px;
  	text-align: justify;
}

input[type=text]{
	width: 300px;
	padding:5px;
	font-size: 18px;
}
input[type=submit]{
/* 	padding:5px; */
	font-size: 18px;
}
select{
	padding:5px;
	float: left;
}
input:focus { 
    border-color: #719ECE;
    box-shadow: 0 0 20px #719ECE;
}
select:focus { 
    border-color: #719ECE;
    box-shadow: 0 0 20px #719ECE;
}
input[type=submit]{
	padding:5px;
	font-size: 18px;
  	border-radius: 6px;
}
input[type=password]{
	width: 300px;
	padding:5px;
	font-size: 18px;
}
#error{
	font-size: 20px;
    color: red;
    margin-left: 12px;
    position: relative;
    margin-top: 30px;
}
li{
	list-style-type:none;
}
</style>

<link href="<%=request.getContextPath()+"/css/bootstrap.min.css"%>" rel="stylesheet">
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-3.5.1.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
		<script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyC3QPntU9wpgbFyz4OreKkrc4igaSo2eWc" type="text/javascript"></script>

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

<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/resinfo/ResInfoServlet" name="form1" enctype="multipart/form-data">
<%  
			if(resInfoVO != null){
				byte[] imgbyte = resInfoVO.getResimg();
				String imgString = getBaseString(imgbyte);
				pageContext.setAttribute("imgString",imgString);
			}
		%>

<div class="container">
   <div class="row">
<!--     <div class="col-2">餐廳編號:</div> -->
<%--     <div class="col-4"><%=resInfoVO.getResid()%></div> --%>
    <div class="col-2">餐廳名稱:</div>
    <div class="col-4"><input type="TEXT" name="resname"  value="<%=resInfoVO.getResname()%>" /></div>
  </div><br>
  <div class="row">
  <div class="col-2">餐廳地址:</div>
    <div class="col-4"><input id="source" type="TEXT" name="resaddid" value="<%=resInfoVO.getResaddid()%>" /></div>
  <div class="col-2">餐廳圖片:</div>
    <div class="col-3">
      <img id="pic" src="${imgString}" onerror="this.src='<%=request.getContextPath()%>/front-end/res/resinfo/images/null2.jpg'" width="200px" height="150px">
 		<input id="p_file" type="file" name="resimg" >
    </div>
  </div><br>
   <div class="row">
  <div class="col-2">無障礙空間:</div>
    <div class="col-4">
    	<select name="barrierfree">
			<option value="有" ${(resInfoVO.barrierfree).equals("有")? 'selected':'' }>有
			<option value="無" ${(resInfoVO.barrierfree).equals("無")? 'selected':'' }>無
 		</select>
    </div>
  <div class="col-2">親子空間:</div>
    <div class="col-4">
    	<select name="parentchild">
			<option value="有" ${(resInfoVO.parentchild).equals("有")? 'selected':'' }>有
			<option value="無" ${(resInfoVO.parentchild).equals("無")? 'selected':'' }>無
 		</select>
    </div>
  </div><br>
  <div class="row">
  <div class="col-2">交通資訊:</div>
    <div class="col-4"><input type="TEXT" name="traffic" value="<%=resInfoVO.getTraffic()%>" /></div>
  <div class="col-2">停車資訊:</div>
    <div class="col-4"><input type="TEXT" name="parking" value="<%=resInfoVO.getParking()%>" /></div>
  </div><br>
  <div class="row">
  <div class="col-2">付款資訊:</div>
    <div class="col-4"><input type="TEXT" name="payinfo" value="<%=resInfoVO.getPayinfo()%>" /></div>
<!--   <div class="col-2">訂位通知設定:</div> -->
<%--     <div class="col-4"><input type="TEXT" name="notifcont" value="<%=resInfoVO.getNotifcont()%>" /></div> --%>
<div class="col-2">密碼:</div>
    <div class="col-4"><input type="password" name="respassid" value="<%=resInfoVO.getRespassid()%>" /></div>  
  </div><br>
  <div class="row">
<!--   <div class="col-2">餐廳信箱:</div> -->
<%--     <div class="col-4"><input type="TEXT" name="resemail" value="<%=resInfoVO.getResemail()%>" /></div> --%>
  
<!--   <div class="col-2">訂位通知時間差:</div> -->
<%--     <div class="col-4"><input type="TEXT" name="subtimediff" value="<%=resInfoVO.getSubtimediff()%>" /></div> --%>
  </div><br>
<!--   <div class="row"> -->
<!--   <div class="col-2">目前候位號碼:</div> -->
<%--     <div class="col-4"><%=resInfoVO.getCurrentwaitingnum()%></div> --%>
<!--   </div><br> -->
  <div class="row">
<!--   <div class="col-2">候位號碼差:</div> -->
<%--     <div class="col-4"><input type="TEXT" name="waitdistance" value="<%=resInfoVO.getWaitdistance()%>" /></div> --%>
  <div class="col-2">聯絡人姓名:</div>
    <div class="col-4"><input type="TEXT" name="contact" value="<%=resInfoVO.getContact()%>" /></div>
     <div class="col-2">聯絡人電話:</div>
    <div class="col-4"><input type="TEXT" name="contactphon" value="<%=resInfoVO.getContactphon()%>" /></div>
  </div><br>
<!--   <div class="row"> -->
 
<!--   <div class="col-2">座位屬性+座標:</div> -->
<%--     <div class="col-4"><input type="TEXT" name="corrdinate" value=<%=resInfoVO.getCorrdinate()%> disabled/></div> --%>
<!--   </div><br> -->
<!--   <div class="row"> -->
<!--   <div class="col-2">加入日期:</div> -->
<%--     <div class="col-4"><fmt:formatDate value="${resInfoVO.adddate}" pattern="yyyy-MM-dd HH:mm"/></div> --%>
<!--   <div class="col-2">會員狀態:</div> -->
<%--     <div class="col-4"><%=resInfoVO.getStatus()%></div> --%>
<!--   </div><br> -->
<!--   <div class="row"> -->
<!--   <div class="col-2">經度:</div> -->
<%--     <div class="col-4"><input type="TEXT" name="lat" value="<%=resInfoVO.getLat()%>" /></div> --%>
<!--   <div class="col-2">緯度:</div> -->
<%--     <div class="col-4"><input type="TEXT" name="lng" value="<%=resInfoVO.getLng()%>" /></div> --%>
<!--   </div><br> -->
    <div class="col-6">
  <div class="row">
  <input type="hidden" name="action" value="update">
<input type="hidden" name="resid" value="<%=resInfoVO.getResid()%>">
<input type="hidden" name="resemail" value="<%=resInfoVO.getResemail()%>">
<input type="hidden" name="currentwaitingnum" value="5">
<input type="hidden" name="corrdinate" value=<%=resInfoVO.getCorrdinate()%>>
<input type="hidden" name="notifcont" value=<%=resInfoVO.getNotifcont()%>>
<input type="hidden" name="subtimediff" value="5">
<input type="hidden" name="status" value="<%=resInfoVO.getStatus()%>">
<input type="hidden" name="waitdistance" value="5">
<input type="hidden" name="lat" id="target1">
<input type="hidden" name="lng" id="target2">
<input type="submit" value="送出修改">
</div>
</div>
</div>
</FORM>




<!-- =========================================以下為 datetimepicker 之相關設定========================================== -->

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.css" />
<script src="<%=request.getContextPath()+"/js/er.twzipcode.data.js"%>"></script>
<script src="<%=request.getContextPath()+"/js/er.twzipcode.min.js"%>"></script>
<script src="<%=request.getContextPath()+"/js/jquery-3.5.1.min.js"%>"></script>
<%-- <script type="text/javascript" src="<%=request.getContextPath()+"/front-end/js/jquery-3.5.1.min.js"%>" charset="UTF-8"></script> --%>
<%-- <script src="<%=request.getContextPath()%>/datetimepicker/jquery.js"></script> --%>
<script src="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.full.js"></script>
 <script>
            var i;
            var split;

            function trans() {
                i = 0;
                
				$("#target1").val("");
                $("#target2").val("");
                var content = $("#source").val();
                split = content.split("\n");
                delayedLoop();
			}

            function delayedLoop() {
                addressToLatLng(split[i]);
                if (++i == split.length) {
                    return;
                }
                window.setTimeout(delayedLoop, 500);
            }
            $("#source").on("blur",function(){
            	trans();
            })

            function addressToLatLng(addr) {
                var geocoder = new google.maps.Geocoder();
                geocoder.geocode({
                    "address": addr
                }, function (results, status) {
					if ($("#c").attr('checked'))
					{
						addr = addr + "=";
					}
					else {
						addr = "";
					}
                    if (status == google.maps.GeocoderStatus.OK) {
						var content1 = $("#target1").val();
                        var content2 = $("#target2").val();
						
						$("#target1").val(content1 + results[0].geometry.location.lat() + "\n");
						$("#target2").val(content2 + results[0].geometry.location.lng() + "\n");
						
                    } else {
                        var content = $("#target").val();
                        $("#target").val(content + addr + "查無經緯度，或系統發生錯誤！" + "\n");
                    }
                });
			}
			
        </script>
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
		let resImg = $("#pic");
		let reader = new FileReader();
		file_picture = this.files[0];
		reader.readAsDataURL(this.files[0]);
		reader.addEventListener("load", function() {
			resImg.attr("src", reader.result);
			$("#originalPic").remove();
		})
	})

	$.datetimepicker.setLocale('zh');
	$('#f_date1').datetimepicker({
		theme : '', //theme: 'dark',
		timepicker : true, //timepicker:true,
		step : 30, //step: 60 (這是timepicker的預設間隔60分鐘)
		format : 'Y-m-d H:i:s', //format:'Y-m-d H:i:s',
		value : '<%=resInfoVO.getAdddate()%>', // value:   new Date(),
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