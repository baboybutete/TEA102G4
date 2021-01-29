<%@ page import="java.sql.Timestamp"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.resinfo.model.*"%>
<%@ page import="java.util.*"%>
<%@ include file="getBaseStirng.file"%>
<%
ResInfoVO resInfoVO = (ResInfoVO) request.getAttribute("resInfoVO");
List<String> list = (List)request.getAttribute("errorMsgs");

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
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
		<script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyC3QPntU9wpgbFyz4OreKkrc4igaSo2eWc" type="text/javascript"></script>
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
        <h2 font-style="h2" color="text01" width="100%" class="sc-fzpans fkGnzT">餐廳註冊</h2>
<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/resinfo/ResInfoServlet" name="form1" enctype="multipart/form-data">
        <section class="sc-fznxsB HZhgf">
            <div class="res-name"><label >餐廳名稱：</label>
            <%
            int index=0;
           	String b ="";
            if (list != null && (list.size() > 0)) {
            	for (index=0; index < list.size(); index++) {
            		String a="";
            		a = list.get(index).split(":")[0];
            		System.out.println(a);
            		if(a.equals("餐廳名稱")){
            			b = list.get(index).split(":")[1];
            			break;
            		}
            	}
            %>
			<a style="color:red"><%= (index < list.size())? b : ""%></a>
			<%}%>
            <input class="res-name2" name="resname" value="<%= (resInfoVO==null)? "" : resInfoVO.getResname()%>">
            </div>
            <div class="res-name"><label >餐廳地址：</label>
            <%
            int index1=0;
            if (list != null && (list.size() > 0)) {
            	for (index1=0; index1 < list.size(); index1++) {
            		String a="";
            		a = list.get(index1).split(":")[0];
            		System.out.println(a);
            		if(a.equals("餐廳地址")){
            			b = list.get(index1).split(":")[1];
            			break;
            		}
            	}
            	%>
			<a style="color:red"><%= (index1 < list.size())? b : ""%></a>
			<%}%>
            <input id="source" class="res-name2" name="resaddid" value="<%= (resInfoVO==null)? "" : resInfoVO.getResaddid()%>">
            </div>
            <div class="res-name"><label >餐廳信箱：</label>
            <%
            int index2=0;
            if (list != null && (list.size() > 0)) {
            	for (index2=0; index2 < list.size(); index2++) {
            		String a="";
            		a = list.get(index2).split(":")[0];
            		System.out.println(a);
            		if(a.equals("餐廳信箱")){
            			b = list.get(index2).split(":")[1];
            			break;
            		}
            	}
            %>
			<a style="color:red"><%= (index2 < list.size())? b : ""%></a>
			<%}%>
            <input class="res-name2" name="resemail" value="<%= (resInfoVO==null)? "" : resInfoVO.getResemail()%>">
            </div>
            
            <div class="res-name"><label >密碼：</label>
             <%
            int index3=0;
            if (list != null && (list.size() > 0)) {
            	for (index3=0; index3 < list.size(); index3++) {
            		String a="";
            		a = list.get(index3).split(":")[0];
            		System.out.println(a);
            		if(a.equals("密碼")){
            			b = list.get(index3).split(":")[1];
            			break;
            		}
            	}
            %>
			<a style="color:red"><%= (index3 < list.size())? b : ""%></a>
			<%}%>
            <input type="password" name="respassid" class="res-name3" value="<%= (resInfoVO==null)? "" : resInfoVO.getRespassid()%>">
            </div>
            <div class="res-name"><label >聯絡人姓名：</label>
             <%
            int index4=0;
            if (list != null && (list.size() > 0)) {
            	for (index4=0; index4 < list.size(); index4++) {
            		String a="";
            		a = list.get(index4).split(":")[0];
            		System.out.println(a);
            		if(a.equals("聯絡人姓名")){
            			b = list.get(index4).split(":")[1];
            			break;
            		}
            	}
            
            %>
			<a style="color:red"><%= (index4 < list.size())? b : ""%></a>
			<%}%>
            <input class="res-name3" name="contact" value="<%= (resInfoVO==null)? "" : resInfoVO.getContact()%>">
            </div>
            <div class="res-name"><label >聯絡人電話：</label>
            <%
            int index5=0;
            if (list != null && (list.size() > 0)) {
            	for (index5=0; index5 < list.size(); index5++) {
            		String a="";
            		a = list.get(index5).split(":")[0];
            		System.out.println(a);
            		if(a.equals("聯絡人電話")){
            			b = list.get(index5).split(":")[1];
            			break;
            		}
            	}
            
            %>
			<a style="color:red"><%= (index5 < list.size())? b : ""%></a>
			<%}%>
            <input class="res-name3" name="contactphon" value="<%= (resInfoVO==null)? "" : resInfoVO.getContactphon()%>">
            </div>
            <br>
            <label class="res-name1">無障礙空間：</label>
            <td><select name="barrierfree">
            <option value="無" ${(resInfoVO.barrierfree).equals("無")? 'selected':'' }>無
			<option value="有" ${(resInfoVO.barrierfree).equals("有")? 'selected':'' }>有
			</select></td>
			<label class="res-name1">親子空間：</label>
            <td><select name="parentchild">
            <option value="無" ${(resInfoVO.parentchild).equals("無")? 'selected':'' }>無
			<option value="有" ${(resInfoVO.parentchild).equals("有")? 'selected':'' }>有
			</select></td>
			<br>
			<br>
			<%
            int index6=0;
            if (list != null && (list.size() > 0)) {
            	for (index6=0; index6 < list.size(); index6++) {
            		String a="";
            		a = list.get(index6).split(":")[0];
            		System.out.println(a);
            		if(a.equals("請上傳餐廳照片")){
            			break;
            		}
            	}
            
            %>
            <a style="color:red"><%= (index6 < list.size())? list.get(index6) : ""%></a>
            <%}%>
            <%byte[] resimg =(byte[])session.getAttribute("resimg");
            String imgString =  getBaseString(resimg);
			pageContext.setAttribute("imgString",imgString);
            %>
            <div class="res-name"><label >餐廳圖片：</label>
            <img id="pic" src="${imgString}" alt="choose picture" width="300px" height="200px">
            <input  type="file" id="p_file" name="resimg" ></td>
            </div>
			
			<input type="hidden" name="traffic"value="復興南路2號出口">
			<input type="hidden" name="parking" value="無無無">
			<input type="hidden" name="payinfo" value="信用卡">
			<input type="hidden" name="notifcont" value="您預定的時間快到了請準時入座">
			<input type="hidden" name="currentwaitingnum" value="0">
			<input type="hidden" name="subtimediff" value="0">
			<input type="hidden" name="waitdistance" value="0">
			<input type="hidden" name="corrdinate" value="">
			<input type="hidden" name="adddate" id="f_date1">
			<input type="hidden" name="status" value="停權">
			<input type="hidden" name="lat" id="target1">
			<input type="hidden" name="lng" id="target2">
			<input type="hidden" name="action" value="insert">
			<input type="submit" value="送出新增"></FORM>
			
</body>
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
						console.log("經"+results[0].geometry.location.lat());
						
                    }
					else {
                        var content = $("#target").val();
                        $("#target").val(content + addr + "查無經緯度，或系統發生錯誤！" + "\n");
                    }
                });
			}
			
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
        
        
        
        
</script>
</html>