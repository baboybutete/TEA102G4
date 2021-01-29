<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.custinfo.model.*" %>
<%@ page import="com.resinfo.model.*" %>
<%@ page import="java.util.* " %>
<%@ page import="java.text.SimpleDateFormat" %>
<%
   Date dNow = new Date( );
   SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-d");
   String b = ft.format(dNow);
   System.out.print(b);
%>
<%
	if(request.getParameter("resid") != null){
		session.setAttribute("resid", request.getParameter("resid"));
		System.out.println("");
	}else{
		System.out.println("從session拿");
	}
	

%>
<%
String resid = (String)session.getAttribute("resid");
ResInfoService resSvc = new ResInfoService();
ResInfoVO resInfoVO = resSvc.getOneResInfo(resid);
String resnmae = resInfoVO.getResname();
session.setAttribute("resname",resnmae);
%>
<!DOCTYPE html>
<html>
<head>
<!-- 這裡是TEA102G4以合併 -->
<meta charset="UTF-8">
<title>order process</title>
 <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-3.5.1.min.js"></script>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/123.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/humburger.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.css" />
    <script src="<%=request.getContextPath()%>/datetimepicker/jquery.js"></script>
    <script src="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.full.js"></script>
    

    <style>
        .xdsoft_datetimepicker .xdsoft_datepicker {
            width: 300px;
            /* width:  300px; */
        }

        .xdsoft_datetimepicker .xdsoft_timepicker .xdsoft_time_box {
            height: 151px;
            /* height:  151px; */
        }
    </style>
    
</head>
<body>	

	
	<%@ include file="/front-end/cust/order_process/order_process_header.jsp" %>
	   
 	<div class="sc-AxjAm dVvNhC">
        <div class="sc-fznLPX dtmoaU">
            <span>
                <a href="<%=request.getContextPath()%>/index_plat.jsp">網站首頁</a>
            </span>
            <img src="<%=request.getContextPath()%>/images/back (2).png">
            <span>
                <a href="<%=request.getContextPath()%>/front-end/cust/resinfo/index_res.jsp?action=getOne_For_Display_By_Client&resid=${resid}"><%=resInfoVO.getResname()%></a>
            </span>
            <img src="<%=request.getContextPath()%>/images/back (2).png">
            <span>
                <a href="<%=request.getContextPath()%>/front-end/cust/order_process/order_process.jsp">訂位</a>
            </span>
        </div>
 	</div>
 
 <div id="book-now" class="sc-AxjAm fJmoPd border-b border-grey-200">
        <div id="book-now-selectors" class="sc-AxjAm bbREVI">
            <div id="diner-field" class="sc-AxjAm jfufrK">
                <p class="sc-fzpans FUZHT">用餐人數</p>
                <div class="sc-AxjAm bqrfuB"><select name="people" id="adult-picker" class="sc-fzoLag iYblXa">
                        <option>請選擇用餐人數</option>
                        <option value="1" data-testid="1位">1</option>
                        <option value="2" data-testid="2位">2</option>
                        <option value="3" data-testid="3位">3</option>
                        <option value="4" data-testid="4位">4</option>
                        <option value="5" data-testid="5位">5</option>
                        <option value="6" data-testid="6位">6</option>
                        <option value="7" data-testid="7位">7</option>
                        <option value="8" data-testid="8位">8</option>
                        <option value="9" data-testid="9位">9</option>
                        <option value="10" data-testid="10位">10</option>
                    </select></div>
            </div>
            <div id="date-field" class="sc-AxjAm lkuZcd">
                <p class="sc-fzpans iomkUk">用餐日期</p>
                <input class="f_date2" name="subtime" id="f_date2" type="text" value="<%=b%>">
                <!-- <div id="date-picker" tabindex="0" aria-expanded="false" class="sc-fzoLag iYblXa">1/05 週二</div> -->
            </div>
            <article class="sc-fznKkj fiKPzL markdown">
                <p>可接受 1-10 位訂位，超過 10 人的訂位，請使用電話預約。</p>
            </article>


            <div id="book-now-content" class="sc-AxjAm lkfUl">
                <div class="sc-AxjAm nkbZK"><span id="中午" class="sc-fzoLsD dLvPjI">中午</span>
                    <hr class="sc-AxjAm lajchV">
                </div>
                <div aria-labelledby="中午" class="sc-AxjAm fBPoml"><button class="sc-fznMnq jWfUoz time-slot"><span
                            class="sc-fzoLsD eLfLVX">11:00</span></button><button
                        class="sc-fznMnq jWfUoz time-slot"><span class="sc-fzoLsD eLfLVX">11:15</span></button><button
                        class="sc-fznMnq jWfUoz time-slot"><span class="sc-fzoLsD eLfLVX">11:30</span></button><button
                        class="sc-fznMnq jWfUoz time-slot"><span class="sc-fzoLsD eLfLVX">11:45</span></button><button
                        class="sc-fznMnq jWfUoz time-slot"><span class="sc-fzoLsD eLfLVX">12:00</span></button><button
                        class="sc-fznMnq jWfUoz time-slot"><span class="sc-fzoLsD eLfLVX">12:15</span></button><button
                        class="sc-fznMnq jWfUoz time-slot"><span class="sc-fzoLsD eLfLVX">12:30</span></button><button
                        class="sc-fznMnq jWfUoz time-slot"><span class="sc-fzoLsD eLfLVX">12:45</span></button><button
                        class="sc-fznMnq jWfUoz time-slot"><span class="sc-fzoLsD eLfLVX">13:00</span></button><button
                        class="sc-fznMnq jWfUoz time-slot"><span class="sc-fzoLsD eLfLVX">13:15</span></button><button
                        class="sc-fznMnq jWfUoz time-slot"><span class="sc-fzoLsD eLfLVX">13:30</span></button><button
                        class="sc-fznMnq jWfUoz time-slot"><span class="sc-fzoLsD eLfLVX">13:45</span></button>
                </div>
                <div class="sc-AxjAm nkbZK"><span id="下午" class="sc-fzoLsD dLvPjI">下午</span>
                    <hr class="sc-AxjAm lajchV">
                </div>
                <div aria-labelledby="下午" class="sc-AxjAm fBPoml"><button class="sc-fznMnq jWfUoz time-slot"><span
                            class="sc-fzoLsD eLfLVX">14:00</span></button><button
                        class="sc-fznMnq jWfUoz time-slot"><span class="sc-fzoLsD eLfLVX">14:15</span></button><button
                        class="sc-fznMnq jWfUoz time-slot"><span class="sc-fzoLsD eLfLVX">14:30</span></button><button
                        class="sc-fznMnq jWfUoz time-slot"><span class="sc-fzoLsD eLfLVX">14:45</span></button><button
                        class="sc-fznMnq jWfUoz time-slot"><span class="sc-fzoLsD eLfLVX">15:10</span></button><button
                        class="sc-fznMnq jWfUoz time-slot"><span class="sc-fzoLsD eLfLVX">15:15</span></button><button
                        class="sc-fznMnq jWfUoz time-slot"><span class="sc-fzoLsD eLfLVX">15:30</span></button><button
                        class="sc-fznMnq jWfUoz time-slot"><span class="sc-fzoLsD eLfLVX">15:45</span></button><button
                        class="sc-fznMnq jWfUoz time-slot"><span class="sc-fzoLsD eLfLVX">16:00</span></button><button
                        class="sc-fznMnq jWfUoz time-slot"><span class="sc-fzoLsD eLfLVX">16:15</span></button><button
                        class="sc-fznMnq jWfUoz time-slot"><span class="sc-fzoLsD eLfLVX">16:30</span></button><button
                        class="sc-fznMnq jWfUoz time-slot"><span class="sc-fzoLsD eLfLVX">16:45</span></button>
                </div>
                <div class="sc-AxjAm nkbZK"><span id="晚上" class="sc-fzoLsD dLvPjI">晚上</span>
                    <hr class="sc-AxjAm lajchV">
                </div>
                <div aria-labelledby="晚上" class="sc-AxjAm fBPoml"><button class="sc-fznMnq jWfUoz time-slot"><span
                            class="sc-fzoLsD eLfLVX">17:00</span></button><button
                        class="sc-fznMnq jWfUoz time-slot"><span class="sc-fzoLsD eLfLVX">17:15</span></button><button
                        class="sc-fznMnq jWfUoz time-slot"><span class="sc-fzoLsD eLfLVX">17:30</span></button><button
                        class="sc-fznMnq jWfUoz time-slot"><span class="sc-fzoLsD eLfLVX">17:45</span></button><button
                        class="sc-fznMnq jWfUoz time-slot"><span class="sc-fzoLsD eLfLVX">18:00</span></button><button
                        class="sc-fznMnq jWfUoz time-slot"><span class="sc-fzoLsD eLfLVX">18:15</span></button><button
                        class="sc-fznMnq jWfUoz time-slot"><span class="sc-fzoLsD eLfLVX">18:30</span></button><button
                        class="sc-fznMnq jWfUoz time-slot"><span class="sc-fzoLsD eLfLVX">18:45</span></button><button
                        class="sc-fznMnq jWfUoz time-slot"><span class="sc-fzoLsD eLfLVX">19:00</span></button><button
                        class="sc-fznMnq jWfUoz time-slot"><span class="sc-fzoLsD eLfLVX">19:15</span></button><button
                        class="sc-fznMnq jWfUoz time-slot"><span class="sc-fzoLsD eLfLVX">19:30</span></button><button
                        class="sc-fznMnq jWfUoz time-slot"><span class="sc-fzoLsD eLfLVX">19:45</span></button><button
                        class="sc-fznMnq jWfUoz time-slot"><span class="sc-fzoLsD eLfLVX">20:00</span></button>
                </div>
            </div>
        </div>
        <!-- <div class="next"> -->
        <!-- <button type="button" class="task_add">下一步，填寫聯絡資訊</button> -->
        <!-- <input type="submit" class="task_add" value="下一步，填寫聯絡資訊"> -->
        <!-- </div> -->

        <!-- 座位圖 -->
        
        <canvas width="1180" height="450" style="border: 1px solid black" id="myCanvas"></canvas>
        
        <button type="submit" class="sc-fzoyAV dTkVEz">
            <div hidden="" class="sc-AxjAm sc-fzqMdD gbAuPN">
                <div class="sc-AxjAm sc-fzowVh gSfeHE">
                    <div class="sc-AxjAm sc-fzoJus kTYBqE"></div>
                </div>
            </div>
            <span class="sc-fzoLsD fqEnxe">下一步，填寫聯絡資訊</span>
        </button>
        
   </div>
   <%@ include file="/front-end/cust/order_process/order_process_footer.jsp" %>
    <script>
    
        $(window).on('load', function () {
        	$('.lkuZcd input[type=text]').on('mousewheel', function(e){
                return false;
            });
        	if (sessionStorage.getItem("form_data") != null) {
                var form_data = JSON.parse(sessionStorage.getItem("form_data"));
               let a = form_data.date;
               let b = a.split(" "); 
               console.log(b[0]);
               console.log(b[1]);
               console.log($(".lkuZcd input").attr("value",b[0]));
               for(var i=0;i<37;i++){
            	   if($(".fBPoml button").eq(i).find("span").text() == b[1]){
            		   $(".fBPoml button").eq(i).addClass("hoqQtU selected");
                   }
               }
            }
        	var today=new Date();
        	if(today.getMonth()<9){
    			var todaydate = today.getFullYear()+'-0'+(today.getMonth()+1)+'-'+today.getDate();
    		}else{
    			var todaydate = today.getFullYear()+'-'+(today.getMonth()+1)+'-'+today.getDate();
    		}
        	var currentDateTime = today.getHours()*60+today.getMinutes();
        	if(todaydate == $(".f_date2").val()){
        		for(var i=0;i<37;i++){
            		let a = $(".fBPoml button").eq(i).find("span").text().split(":")[0];
            		let b = $(".fBPoml button").eq(i).find("span").text().split(":")[1];
            		var c = parseInt(a)*60+parseInt(b);
            		console.log(c);
            		if(currentDateTime>=c){
            			$(".fBPoml button").eq(i).addClass("OdjiH full");
            		}
            	}
        	}
        	$(".lkuZcd input").on("change",function(){
        		console.log($(".lkuZcd input").val());
        		$(".lkuZcd input").attr("value",$(".lkuZcd input").val());
        		$(".fBPoml button").attr("class","sc-fznMnq jWfUoz time-slot");
        		
        		var today=new Date();
        		if(today.getMonth()<9){
        			var todaydate = today.getFullYear()+'-0'+(today.getMonth()+1)+'-'+today.getDate();
        		}else{
        			var todaydate = today.getFullYear()+'-'+(today.getMonth()+1)+'-'+today.getDate();
        		}
            	
            	var currentDateTime = today.getHours()*60+today.getMinutes();
            	console.log(todaydate == $(".f_date2").val());
            	if(todaydate == $(".f_date2").val()){
            		for(var i=0;i<37;i++){
                		let a = $(".fBPoml button").eq(i).find("span").text().split(":")[0];
                		let b = $(".fBPoml button").eq(i).find("span").text().split(":")[1];
                		var c = parseInt(a)*60+parseInt(b);
                		console.log(c);
                		if(currentDateTime>=c){
                			$(".fBPoml button").eq(i).addClass("OdjiH full");
                		}
                	}
            	}
        	});
			var preview_func = function () {
        		
                if (sessionStorage.getItem("form_data") != null) {
                    var form_data = JSON.parse(sessionStorage.getItem("form_data"));
                    
                    console.log(form_data);	
                   $(".bqrfuB select").find("option").eq(0).text(form_data.people);
                   let a = form_data.date;
                   let b = a.split(" "); 
                   console.log(b[0]);
                   console.log(b[1]);
                   console.log($(".lkuZcd input").attr("value",b[0]));
                   for(var i=0;i<37;i++){
                	   if($(".fBPoml button").eq(i).find("span").text() == b[1]){
                		   $(".fBPoml button").eq(i).addClass("hoqQtU selected");
                       }
                   }
                }
                if (sessionStorage.getItem("form_data_resid") != null){
                	console.log("123");
                	var form_data2 = JSON.parse(sessionStorage.getItem("form_data_resid"));
                	if(form_data2.resid != "${resid}"){
                   	 sessionStorage.removeItem("form_data2");
                	 sessionStorage.removeItem("form_data");
                	 sessionStorage.removeItem("seats");
                	 sessionStorage.removeItem("form_data_resid");
                	}
                }
                var today=new Date();
                console.log(today.getMonth());
                if(today.getMonth()<9){
        			var todaydate = today.getFullYear()+'-0'+(today.getMonth()+1)+'-'+today.getDate();
        		}else{
        			var todaydate = today.getFullYear()+'-'+(today.getMonth()+1)+'-'+today.getDate();
        		}
            	var currentDateTime = today.getHours()*60+today.getMinutes();
            	console.log(todaydate);
            	console.log($(".f_date2").val());
            	console.log(todaydate == $(".f_date2").val());
            	if(todaydate == $(".f_date2").val()){
            		for(var i=0;i<37;i++){
                		let a = $(".fBPoml button").eq(i).find("span").text().split(":")[0];
                		let b = $(".fBPoml button").eq(i).find("span").text().split(":")[1];
                		var c = parseInt(a)*60+parseInt(b);
                		console.log(c);
                		if(currentDateTime>=c){
                			$(".fBPoml button").eq(i).addClass("OdjiH full");
                		}
                	}
            	}
        	}
        	preview_func();
        	
        	var data_arr2 = {};
            data_arr2.resid="${resid}";
            sessionStorage.setItem("form_data_resid", JSON.stringify(data_arr2));
        	
        	
        	var selectTime;
        	
        	
            $("div.header-btn").on("click", function () {
                $("div.header-dropdown-menu").slideToggle();
            });
			$(".lkfUl button").on("click", function () {
            	
            	var today=new Date();
            	var currentDateTime = today.getHours()*60+today.getMinutes();
            	console.log(currentDateTime);
            	var todaydate = today.getFullYear()+'-'+(today.getMonth()+1)+'-'+today.getDate();
            	$(".fBPoml button").removeClass("hoqQtU selected");
            	if(todaydate == $(".f_date2").val()){
	            	for(var i=0;i<37;i++){
	            		let a = $(".fBPoml button").eq(i).find("span").text().split(":")[0];
	            		let b = $(".fBPoml button").eq(i).find("span").text().split(":")[1];
	            		var c = parseInt(a)*60+parseInt(b);
	            		console.log(c);
	            		if(currentDateTime>c){
	            			$(".fBPoml button").eq(i).addClass("OdjiH full");
	            		}
            		}	
            	}
            	if($(this).attr("class")!="sc-fznMnq jWfUoz time-slot OdjiH full"){
            		$(this).addClass("hoqQtU selected");
            	}
                
                //請求座位狀態
                time = $(".lkuZcd input").val()+" "+$(".lkfUl button.selected").text();
                console.log(time);
                let selectTime = true;
                sendMessage(selectTime,time);
                
            });
            $("button.hamburger").on("click", function () {
                $(this).toggleClass("is-active");
            });
            var a = "";
            var b = "";

            $("button.dTkVEz").on("click", function () {
                if ($(".bqrfuB select").val() == null || $(".bqrfuB select").val() == "請選擇用餐人數") {
                    alert("人數不能為空");
                    return;
                } else if ($(".lkuZcd input").val() == null) {
                    alert("日期不能為空");
                    return;
                } else if ($(".lkfUl button.selected").text() == "") {
                    alert("請選擇時間");
                    return;
                }

                a = $(".lkuZcd input").val() + " " + $(".lkfUl button.selected").text();
                b = $(".bqrfuB select").val();

                // console.log(a);
					
				
					var original_data = JSON.parse(sessionStorage.getItem("form_data"));
	                var data_arr = {};
	                data_arr.people = b;
	                data_arr.date = a;
	                console.log(original_data);
	            if (sessionStorage.getItem("form_data") != null){
	                if(original_data.name != null && (original_data.name).length != 0){
                     	data_arr.name = original_data.name;
                     }
                     if(original_data.ctel != null && (original_data.ctel).length != 0){
                     	data_arr.ctel = original_data.ctel;
                     }
                     if(original_data.email != null && (original_data.email).length != 0){
                     	data_arr.email = original_data.email;
                     }
				}

                
                //選取座位
				data_arr.seats = JSON.parse(sessionStorage.getItem('seats'));//座位可以空嗎?
                console.log(data_arr.seats);
                sessionStorage.setItem("form_data", JSON.stringify(data_arr));
                
         
				let time;
				let host = window.location.host;
				let path = window.location.pathname;
				console.log("host : "+host)
				console.log("path : "+path)
				let webCtx = path.substring(0, path.indexOf('/', 1));
				let endPointURL = "http://" + window.location.host
						+ webCtx + "/front-end/cust/order_process/order_process1.jsp";
                
                console.log(endPointURL)
                webSocket.close();
// 				location.href = 'http://127.0.0.1:5500/index1.html';
				location.href = endPointURL;
            });


            $('#f_date2').datetimepicker({
                theme: '',              //theme: 'dark',
                timepicker: false,       //timepicker:true,
                step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘)
                format: 'Y-m-d',         //format:'Y-m-d H:i:s',
                value: '',
                //disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
                //startDate:	            '2017/07/10',  // 起始日
                //minDate:               '-1970-01-01', // 去除今日(不含)之前
                maxDate:               '+1970-02-01'  // 去除今日(不含)之後
            });
            var somedate1 = new Date();
            $('#f_date2').datetimepicker({
                beforeShowDay: function (date) {
                    if (date.getYear() < somedate1.getYear() ||
                        (date.getYear() == somedate1.getYear() && date.getMonth() < somedate1.getMonth()) ||
                        (date.getYear() == somedate1.getYear() && date.getMonth() == somedate1.getMonth() && date.getDate() < somedate1.getDate())
                    ) {
                        return [false, ""]
                    }
                    return [true, ""];
                }
            });



            // canvas 的js設定
            /*存 input 的value*/
          
              // canvas 的js設定
            /*存 input 的value*/
          
            var resid = "${resid}";
            console.log("el : "+resid);
            var custid = 0;
            var startX = 0;
            var startY = 0;
            var selectedE = -1; //moving element
            var focusIndexOfDataArray = -1;// assigned a number when mousedown trigger;used for delete element, 起始點同是dataArray;
            var moveDistanceX = 0;
            var moveDistanceY = 0;

            var lightBorderColor = 'yellow'
            var chairwidth = 40;
            var chairlength = 40;
            /*Canvas 設定*/
            var borderOfCanvas = 1;
            
            <%--以下拿到的top和left會是固定值，若要做RWD則需要每次都取一次新值，因此註解
            var myCanvasPosition = $("#myCanvas").offset();--%>
            var myCanvas_width = $("#myCanvas").width();
            var myCanvas_height = $("#myCanvas").height();

            var lineWidthOfHollowchair = 1;
            var chairwidthOfHollowchair = chairwidth
                - lineWidthOfHollowchair * 2;
            var chairlengthOfHollowchair = chairlength
                - lineWidthOfHollowchair * 2;

            /*----step1 拿到canvas環境，以下兩步必做---*/
            /*提示Canvas*/
            /** @type {HTMLCanvasElement} */
            var myCanvas = document.getElementById("myCanvas");
            var ctx = myCanvas.getContext('2d');

            var fixArray = [];//初始值定義可被拖和複製的物品
            var dataArray = [];//the data will be changed when moving the itmes. Store fixArray in the beginning and will be followed by seatArray after dragging items in optional place( or the index within the length of fixArray) dataArray = fixArray + seatArray
            var seatArray = [];

            /*fixArray :optional items. 
            the data that never change, which is used to setting any required information.
            x ,y is the origin point
            w , h are stand for width and height,individually */
            fixArray.push({/* table*/
                x: 10,
                y: 80,
                w: 150,
                h: 100,
                color: 'no',
                itemNumber: 0
            })
            // 							fixArray.push({/*unavailable chair*/
            // 								x : 10,
            // 								y : 190,
            // 								w : chairwidth,
            // 								h : chairlength,
            // 								color : 'black',
            // 								itemNumber : 1
            // 							})
            fixArray.push({/*used chair*/
                x: 10,
                y: 250,
                w: chairwidth,
                h: chairlength,
                color: 'red',
                itemNumber: 2
            })
            fixArray.push({/*locked chair (by customer)*/
                x: 10,
                y: 310,
                w: chairwidth,
                h: chairlength,
                color: 'yellow',
                itemNumber: 3
            })
            fixArray.push({/*availble chair (中空的)*/
                x: 10,
                y: 370,
                w: chairwidthOfHollowchair,
                h: chairlengthOfHollowchair,
                color: 'no',
                itemNumber: 4
            })


            draw();

         

            /*----------修改數字----------*/
            $keyInNumber = $("#keyInNumber");

            /*滑鼠點下時，判斷是哪個元素被點擊，然後重新定義該元素的xy座標，再重新繪製整張畫布*/
            myCanvas.addEventListener("mousedown", function (
                event) {
				
            	
                let hittedIndex = handleMouseDown(event);
//                 lightBorder(hittedIndex);
            })
            myCanvas.addEventListener("mousemove", function (
                event) {
                // 								handleMouseMove(event);
            })

            myCanvas.addEventListener("mouseup",
                function (event) {
                    handleMouseUp(event);
                })

        
            /*紀錄滑鼠點擊時的X Y 座標*/
            function handleMouseDown(event) {
				let myCanvasY = myCanvas.getBoundingClientRect().left + window.scrollX;
				console.log("相對位置");
				console.log(myCanvasY);
				console.log("event.pageX");
				console.log(event.pageX);
				console.log("大小");
				console.log($("#myCanvas").width());
                startX = event.pageX - $("#myCanvas").offset().left;//產生對的位置就好了
                
                startY = event.pageY - $("#myCanvas").offset().top;
                selectedE = getHittedElement(dataArray,event);
                console.log("位置");
                console.log(selectedE);
                /*複製選擇欄內的物件。
                                  只有點擊旁邊選擇欄時，才會複製物品並push到seat裡面。當連擊時會連續產生很多個物件*/
                draw();
                focusIndexOfDataArray = selectedE;

                if (selectedE >= 0
                    && selectedE < fixArray.length) {
                    /*setting seat number */
//                     dataArray[selectedE].seatNumber = countNumber;
//                     countNumber++;
//                     seatArray.push(dataArray[selectedE]);

                    return selectedE;
                }
                return selectedE;
            }


            /*跑迴圈判斷是哪個element被點擊，並且回傳該element的index*/
            /*從server得到的是字串要轉成number*/
            function getHittedElement(array,event) {
            	
                for (let i = 0; i < array.length; i++) {
                    let element = array[i];
                    if ((element.x <= startX && startX <= element.x + element.w)
                        && (element.y <= startY && startY <= element.y + element.h)) {

                        return i;
                    }
                }
                return -1;
            }



            function handleMouseUp(event) {
                /*是否排除掉fixArray*/
                if (selectedE != -1) {
                    /*initial dataArray
                    將dataArray值恢復成原本的fix資料,將原本在該索引值的物件替換掉
                     */
                    intialDataArray();
                    changeColor();
                    draw();
                    //selectedE = -1;//恢復-1 初始值，有沒有不影響
                }
            }




            /*change color when mouseup item*/
            function changeColor() {

                if (focusIndexOfDataArray >= fixArray.length) {
                    let tableMaxIndex = 1;/*顏色可以變換到fixArry 的tableMaxIndex以上(不含tableMaxIndex)*/
                    let targetElIndex = focusIndexOfDataArray
                        - fixArray.length;

                    let targetEl = seatArray[targetElIndex];



                    //若座位是itemNumber(紅色，被其他人選過的則不能再改變)
                    if (targetEl.itemNumber === 2) {
                        return;
                    }
                    /*找index 某個顏色在fixArray是屬於哪個index*/
                    let indextOfColor = fixArray.findIndex(x => x.itemNumber === targetEl.itemNumber);
                    
                    /*不改變編號為0的桌子顏色 black is 1 index*/
                    if (targetEl.itemNumber > 0) {
                        let NewitemNumber = (Number(indextOfColor) + 1)
                            % fixArray.length;//4
                        /*跳過不屬於椅子的編號*/
                        while (NewitemNumber <= tableMaxIndex) {
                            NewitemNumber++;
                        }

                        /*改變該物品對應到的fixArray*/
                        targetEl.w = fixArray[NewitemNumber].w;
                        targetEl.h = fixArray[NewitemNumber].h;
                        targetEl.color = fixArray[NewitemNumber].color;
                        targetEl.itemNumber = fixArray[NewitemNumber].itemNumber;

                        draw();
                        storeSession();// 將選取的位置記錄到sessionStorage
                        //intialDataArray();
                        let selectTime = false;
                        sendMessage(selectTime,time);

                    }

                }

            }

            /*計算被點擊的element要被移動到哪裡去*/
            function rePosition() {
                let movingE = dataArray[selectedE];
                /*get the moving distance of mouse*/
                moveDistanceX = event.pageX
                    - $("#myCanvas").offset().left - startX;
                moveDistanceY = event.pageY
                    - $("#myCanvas").offset().top- startY;
                startX = event.pageX - $("#myCanvas").offset().left;//重新定義起始位置X
                startY = event.pageY - $("#myCanvas").offset().top;//重新定義起始位置Y
                /*reset the position of x and y*/
                movingE.x = movingE.x + (moveDistanceX);
                movingE.y = movingE.y + (moveDistanceY);
                draw();
            }

            /*將dataArray 初始化 deep copy*/
            function intialDataArray() {
                dataArray = [];
                for (let i = 0; i < fixArray.length; i++) {
                    let obj = {
                        x: fixArray[i].x,
                        y: fixArray[i].y,
                        w: fixArray[i].w,
                        h: fixArray[i].h,
                        color: fixArray[i].color,
                        itemNumber: fixArray[i].itemNumber
                    }
                    dataArray.push(obj);
                }
                dataArray = $.merge(dataArray, seatArray);
            }
            /*重新繪製canvas*/
            function draw() {

                /*清除畫布(x,y,width,heigth) */
                ctx.clearRect(0, 0, 1000, 1000);

                // drawing dividing line
                // 								ctx.beginPath();
                // 								ctx.strokeStyle = 'red';// 線的顏色
                // 								ctx.lineWidth = 5;//線的粗細
                // 								ctx.moveTo(150, 0);//起點
                // 								ctx.lineTo(150, 500);//第二點
                // 								ctx.stroke();// 執行
                // 								ctx.closePath();
                /*drawing optional items*/
                let light = false;
                // drawingSeat(fixArray, light);

                // 								ctx.beginPath();
                // 								ctx.font = "20px Arial";
                // 								ctx.fillStyle = "black";
                // 								ctx.fillText("Drag them", 10, 50);
                // 								ctx.fillText("Drop here", 180, 50);
                // 								ctx.closePath();

                /*drawing seats*/
                light = true;
                drawingSeat(seatArray, light);
            }

            function drawingSeat(array, light) {
                /*light是布林值，避免item列表在雙擊也被加上亮框*/
                for (let i = 0; i < array.length; i++) {
                    let ele = array[i];

                    /*給中空的圖案*/
                    if (ele.color === "no") {
                        // 										console.log("test color is "+ele.color);
                        ctx.beginPath();
                        ctx.strokeStyle = "black";
                        ctx.lineWidth = lineWidthOfHollowchair;

                        ctx.rect(ele.x, ele.y, ele.w, ele.h);
                        ctx.stroke();
                        ctx.closePath();
                        drawingSeatNumber(ele);

                        if ((selectedE - fixArray.length) === i
                            && light) {
                            if (ele.itemNumber < 2) continue;
                            ctx.beginPath();
                            ctx.strokeStyle = lightBorderColor;
                            ctx.lineWidth = 2;
                            ctx.rect(ele.x, ele.y, ele.w,
                                ele.h);
                            ctx.stroke();
                            ctx.closePath();
                        }

                    }
                    /*給實心的圖案*/
                    else {
                        ctx.fillStyle = ele.color;
                        ctx.fillRect(ele.x, ele.y, ele.w,
                            ele.h);
                        /*drawing number*/
                        drawingSeatNumber(ele);
                        /*亮框focus，當迴圈輪到點擊的元素時將其加上亮框*/
                        if ((selectedE - fixArray.length) === i
                            && light) {
                            ctx.beginPath();
                            ctx.strokeStyle = lightBorderColor;
                            ctx.lineWidth = 2;
                            ctx.rect(ele.x, ele.y, ele.w, ele.h);
                            ctx.stroke();
                            ctx.closePath();
                        }
                    }
                }

            }

            function drawingSeatNumber(ele) {
                if (ele.seatNumber === -1) return;
                ctx.beginPath();
                ctx.font = "20px Arial";
                ctx.fillStyle = "green";
                let relativeX = 10;
                let relativeY = 20;
                let number = ele.seatNumber;
                if (number != null) {
                    let x = ele.x + relativeX;
                    let y = ele.y + relativeY;
                    ctx.fillText(number, x, y);
                    ctx.closePath();
                }

            }

            /*
            1. 當custid沒有
            */
            /*web socket*/
            var custid;
            var MyPoint = "/Resseatstate/" + "${resid}" + "/";//餐廳和消費者改成EL:userid,從request拿
            var time;
            var host = window.location.host;
            var path = window.location.pathname;
            console.log(MyPoint);

            var webCtx = path.substring(0, path.indexOf('/', 1));
            var endPointURL = "ws://" + window.location.host
                + webCtx + MyPoint;
            var webSocket;
            connect();

            function connect() {
                // create a websocket
              
                MyPoint = MyPoint + "${custinfoVO.custid}";
                MyPoint = MyPoint + "/";
                endPointURL = "ws://" + window.location.host
                    + webCtx + MyPoint;
                console.log(endPointURL);
                
                webSocket = new WebSocket(endPointURL);

                webSocket.onopen = function (event) {
                    console.log("Connect Success!");
                };
                webSocket.onmessage = function (event) {
                    //will get message when get connection
                    seatArray = [];
                    
                    seatArray = JSON.parse(event.data);
                    coveringToNumber(seatArray);//covering String to Number
                    intialDataArray();
                    storeSession();//將上一個選的時間的位置取消掉
                    //淺層複製 seatArray 內的物件和dataArry內的後半物件是相同的(same address in memory)
                    draw();


                };

                webSocket.onclose = function (event) {
                    console.log("Disconnected!");
                };
            }
           
//             $(".lkfUl button").on("click", function () {
//                  $(this).parents("div").find(".lkfUl button").attr("class", "sc-fznMnq jWfUoz time-slot");

//                  $(this).addClass("sc-fznMnq hoqQtU time-slot selected").siblings().attr("class", "sc-fznMnq jWfUoz time-slot");
//                  console.log($(this).text());
//             })

            function coveringToNumber(seatArray) {
            	for (let i = 0; i < seatArray.length; i++) {
                    let obj = seatArray[i];
                    obj.x = Number(obj.x);
                    obj.y = Number(obj.y);
                    obj.w = Number(obj.w);
                    obj.h = Number(obj.h);
                    obj.itemNumber = Number(obj.itemNumber);
                    let number = Number(obj.seatNumber);
                    
                    if(! isNaN(number)){
                    	obj.seatNumber = number;
                    }
                }
            }

            function sendMessage(selectTime,time) {
                /*暫時抓custid*/
                let messageObj = {};
                messageObj.resid = "${resid}";
                messageObj.time = time;
                messageObj.custid = "${custinfoVO.custid}";
				if(selectTime){
					messageObj.seatData = [];
				}else{
					messageObj.seatData = seatArray;
				}
//                 let messageObj = {
//                     "resid": resid,//用EL
//                     "time": "now",// 用js改
//                     "seatData": seatArray,
//                     "custid": "C00001"
//                 }
				
				//covering to string
                var messageString = JSON.stringify(messageObj);
                
	
                webSocket.send(messageString);
            }

            // 將被選擇的座位存到session
            function storeSession() {
                let selectedArray = [];
                for (let i = 0; i < seatArray.length; i++) {
                    let obj = seatArray[i];
                    if (obj.itemNumber === 3) {
                        let seat = obj.seatNumber
                        selectedArray.push(seat);
                    }
                }
                //轉成JSON
                let seatJSON = JSON.stringify(selectedArray);
                sessionStorage.setItem('seats', seatJSON);
            }
        })

    </script>
</body>
</html>