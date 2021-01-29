<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.addetail.model.*"%>
<%@ page import="com.resinfo.model.*"%>
<%@ page import="java.util.*"%>
<%@ include file="getBaseStirng.file"%>

<%
	AddetailVO addetailVO = (AddetailVO) request.getAttribute("addetailVO");

if (addetailVO != null) {
	byte[] imgbyte = addetailVO.getAdimg();
	String imgString = getBaseString(imgbyte);
	pageContext.setAttribute("imgString", imgString);
}
%>

<!DOCTYPE html>
<html lang="en" dir="ltr">
  <head>
    <meta charset="utf-8">
    <title>座位呢 - update_addetail_input.jsp</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/update_addetail_input.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/back-end/datetimepicker/jquery.datetimepicker.css" />
  </head>
  <body>
    <div>
       <!-- headerlogo -->
    	<%@ include file="/back-end/back_header.jsp" %>

      <!-- 顯示資料的區域 -->
      <div class="custinfo">
        <div class="custinfo_title">
          <a>餐廳資訊管理</a>
            <img src="<%=request.getContextPath()%>/images/right-arrow.png">
          <a>廣告/推播審核</a>
        </div>
        <div class="input_adid">
          <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/addetail/controller/addetailServlet.do" name="form1" enctype="multipart/form-data">
			<table>
				<tr>
					<td>廣告/推播編號:</td>
					<td><%=addetailVO.getAdid()%></td>
				</tr>
				
				<tr>
					<td>標題:</td>
					<td><input type="TEXT" name="adtitle" size="30" value="<%=addetailVO.getAdtitle()%>" readonly/></td>
				</tr>
					
				<tr>
					<td>內容:</td>
					<td><input type="TEXT" name="adcontent" size="30" value="<%=addetailVO.getAdcontent()%>" readonly/></td>
				</tr>
				
				<tr>
					<td>上架日期:</td>
					<td><input name="onshelftime" id="f_date1" type="text" readonly></td>
				</tr>
					
				<tr>
					<td>下架日期:</td>
					<td><input name="offshelftime" id="f_date2" type="text" readonly></td>
				</tr>
				
				<tr>
					<td>類型:</td>
					<td><input type="TEXT" name="adtype" size="30" value="<%=addetailVO.getAdtype()%>" readonly/></td>
				</tr>
				
				<tr>
					<td>審核狀態:</td>
					<td><select name="reviewstatus">
							<option value="審核中" ${addetailVO.reviewstatus.equals("審核中")?'selected':'' }>審核中</option>
							<option value="未審核" ${addetailVO.reviewstatus.equals("未審核")?'selected':'' }>未審核</option>
							<option value="不須審核" ${addetailVO.reviewstatus.equals("不須審核")?'selected':'' }>不須審核</option>
							<option value="審核通過" ${addetailVO.reviewstatus.equals("審核通過")?'selected':'' }>審核通過</option>
						</select>
					</td>
				</tr>	
				
				<jsp:useBean id="resInfoSvc" scope="page" class="com.resinfo.model.ResInfoService" />	
				<tr>
					<td>餐廳名稱:</td>
					<td>
					<select size="1" name="resid">
						<c:forEach var="resInfoVO" items="${resInfoSvc.all}">
						<option value="${resInfoVO.resid}" ${(addetailVO.resid==resInfoVO.resid)? 'selected':'' } >${resInfoVO.resname}
						</c:forEach>
					</select>
					</td>
				</tr>
				
				<tr>
					<td>圖片:</td>
					<td><img id="pic" src="${imgString}" alt="no pic" width="300px" height="200px">
					<br>
					<input id="p_file" type="file" name="adimg"></td>
				</tr>
			</table>
			<br>
			<input type="hidden" name="action" value="update">
			<input type="hidden" name="adid" value="${addetailVO.adid}">
			<input type="submit" value="送出修改">
		   </FORM>
        </div>
      </div>
    </div>
    <!-- footer -->
   <%@ include file="/back-end/back_footer.jsp" %>
    
   
    
	<script type="text/javascript" src="<%=request.getContextPath()+"/js/jquery-3.5.1.min.js"%>" charset="UTF-8"></script>
  	<script type="text/javascript" src="<%=request.getContextPath()%>/js/update_addetail_input.js"></script>
  	<script src="<%=request.getContextPath()%>/back-end/datetimepicker/jquery.datetimepicker.full.js"></script>
	<script type="text/javascript">
	$("#p_file").on("change",function(){
		let menuImg = $("#pic");
		let reader = new FileReader();
	        file_picture = this.files[0];
	        reader.readAsDataURL(this.files[0]);
	        reader.addEventListener("load", function () {
	        	menuImg.attr("src",reader.result);
	        	$("#originalPic").remove();
	        })
	})

    $.datetimepicker.setLocale('zh');
    $('#f_date1').datetimepicker({
       theme: '',              //theme: 'dark',
	       timepicker:true,       //timepicker:true,
	       step: 60,                //step: 60 (這是timepicker的預設間隔60分鐘)
	       format:'Y-m-d H:i',         //format:'Y-m-d H:i:s',
		   value: '<%=addetailVO.getOnshelftime()%>', // value:   new Date(),
       //disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
       //startDate:	            '2017/07/10',  // 起始日
       //minDate:               '-1970-01-01', // 去除今日(不含)之前
       //maxDate:               '+1970-01-01'  // 去除今日(不含)之後
    });
    
    
    $.datetimepicker.setLocale('zh');
    $('#f_date2').datetimepicker({
       theme: '',              //theme: 'dark',
	       timepicker:true,       //timepicker:true,
	       step: 60,                //step: 60 (這是timepicker的預設間隔60分鐘)
	       format:'Y-m-d H:i',         //format:'Y-m-d H:i:s',
		   value: '<%=addetailVO.getOffshelftime()%>', // value:   new Date(),
       //disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
       //startDate:	            '2017/07/10',  // 起始日
       //minDate:               '-1970-01-01', // 去除今日(不含)之前
       //maxDate:               '+1970-01-01'  // 去除今日(不含)之後
    });
	</script>
  </body>
</html>