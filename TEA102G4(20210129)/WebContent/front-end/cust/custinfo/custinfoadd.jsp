<%@page import="com.custinfo.model.CustinfoVO"%>
<%@ page import="java.sql.Timestamp"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.resinfo.model.*"%>
<%@ page import="java.util.*"%>
<%@ include file="getBaseStirng.file"%>
<%
CustinfoVO custinfoVO = (CustinfoVO) request.getAttribute("custinfoVO");


%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>營業時間新增 - addResInfo.jsp</title>
 <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-3.5.1.min.js"></script>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/123.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/humburger.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/index1.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.css" />
    <script src="<%=request.getContextPath()%>/datetimepicker/jquery.js"></script>
    <script src="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.full.js"></script>
	
<style>
#preview{
        border: 1px solid lightgray;
        display: inline-block;
        width: 250px;
        min-height: 180px;
 
        position: relative;
        }
        #preview span.text{
        position: absolute;
        display: inline-block;
        left: 50%;
        top: 50%;
        transform: translate(-50%, -50%);
        z-index: -1;
        color: lightgray;
        }
        #preview img.preview_img{
        width: 100%;
        height:180px;
        }
        .header {
    position: sticky;
    top:0;
    width: 100%;
    background-color: rgb(236, 233, 233);
    /* border-bottom: 1px solid rgb(255, 0, 0); */
    height: 60px;
    z-index: 10;
    
}
.log {
    height: 60px;
    width: 110px;
    margin-left: 20px;
    float: left;
}
.log img{
    margin-top: 1px;
    width: 100%;
    
}
.res-name span{
color:red
}
</style>
</head>
<body>

<%-- 錯誤表列 --%>
<%-- <c:if test="${not empty errorMsgs}"> --%>
<!-- 	<ul> -->
<%-- 		<c:forEach var="message" items="${errorMsgs}"> --%>
<%-- 			<li style="color:red">${message}</li> --%>
<%-- 		</c:forEach> --%>
<!-- 	</ul> -->
<%-- </c:if> --%>

<!-- 頭部 -->
	<div class="header">
		<!-- logo部分 -->
		<div class="log">
		<a href="<%=request.getContextPath()%>/index_plat.jsp"><img
				src="<%=request.getContextPath()%>/images/Asset 19.png"
				alt=""></a>
		</div>
	</div>

<div id="contact-form" class="sc-AxjAm gjUWaj">
        <h2 font-style="h2" color="text01" width="100%" class="sc-fzpans fkGnzT">消費者註冊</h2>
<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/custinfo/custinfo.do" name="form1" enctype="multipart/form-data">
        <section class="sc-fznxsB HZhgf">
            <div class="res-name"><label >名稱：</label>
            	<span>${errorMsgs.get('custname')}</span>
            	<input class="res-name2" name="custname" value="${custinfoVO.custname}">
            </div>
            
            <div class="res-name"><label >信箱/帳號：</label>
          		<span>${errorMsgs.get('custaccount')}</span>	
            	<input class="res-name2" name="custaccount" value="${custinfoVO.custaccount}">
            </div>
            
            <div class="res-name"><label >密碼：</label>
          		<span>${errorMsgs.get('custpassword')}</span>	
            <input type="password" name="custpassword" class="res-name3" value="${custinfoVO.custpassword}">
            </div>
            
            <div class="res-name"><label >電話：</label>
          		<span>${errorMsgs.get('custtel')}</span>	
            <input class="res-name2" name="custtel" value="${custinfoVO.custtel}">
            </div>
     
			<br>
			
			<input type="hidden" name="custstatus" value="使用中">
			<input type="hidden" name="action" value="insert">
			<input type="submit" value="送出新增"></FORM>
	 </section>
</body>
<%-- 
<div id="preview"><span class="text">預覽圖</span></div>
<script>
        var the_file_element = document.getElementById("p_file");
            the_file_element.addEventListener("change", function(){
            var reader=new FileReader();
            reader.readAsDataURL(this.files[0]);
            reader.addEventListener("load",function(e){
                document.getElementById("preview").innerHTML='<img src="'+e.target.result+'" class="preview_img">';
            })
            });
    </script>


<!-- =========================================以下為 datetimepicker 之相關設定========================================== -->

<% 
  Timestamp adddate = null;
  try {
	  adddate = resInfoVO.getAdddate();
   } catch (Exception e) {
	   adddate = new Timestamp(System.currentTimeMillis());
   }
%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.css" />
<%-- <script src="<%=request.getContextPath()%>/datetimepicker/jquery.js"></script> --%>
<script src="<%=request.getContextPath()+"/front-end/js/jquery-3.5.1.min.js"%>"></script>
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
			let resInfoImg = $("#pic");
			let reader = new FileReader();
			file_picture = this.files[0];
			reader.readAsDataURL(this.files[0]);
			reader.addEventListener("load", function() {
			resInfoImg.attr("src", reader.result);
		$("#originalPic").remove();
			})
		})
		
		
        $.datetimepicker.setLocale('zh');
        $('#f_date1').datetimepicker({
	       theme: '',              //theme: 'dark',
	       timepicker:true,       //timepicker:true,
	       step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘)
	       format:'Y-m-d H:i:s',         //format:'Y-m-d H:i:s',
		   value: 'new Date()', // value:   new Date(),
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