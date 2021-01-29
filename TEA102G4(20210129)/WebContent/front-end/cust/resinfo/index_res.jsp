<%@page import="com.favorites.model.FavoritesService"%>
<%@page import="com.favorites.model.FavoritesVO"%>
<%@page import="com.resinfo.model.ResInfoVO"%>
<%@page import="com.resinfo.model.ResInfoService"%>
<%@page import="com.custinfo.model.CustinfoVO"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="com.carousel.controller.*"%>
<%@ page import="com.carousel.model.*"%>

<%@ include file="getBaseStirng.file"%>

<%	

	if(request.getParameter("resid") != null){
		session.setAttribute("resid", request.getParameter("resid"));
		System.out.println("");
	}
	
	String resid = (String)session.getAttribute("resid");

	ResInfoService ResInfoSvc = new ResInfoService();
	
	ResInfoVO resInfovo  =   ResInfoSvc.getOneResInfo(resid);
	pageContext.setAttribute("resInfovo",resInfovo);

	CarouselService carouselSvc = new CarouselService();
	List<CarouselVO> list = carouselSvc.getOneRes(resid);
	pageContext.setAttribute("list",list);
	
%>

<html>
<head>
<title>座位呢-餐廳頁面(消費者看到的)</title>

<link href="<%=request.getContextPath()+"/css/index_res.css"%>" rel="stylesheet">
<%-- <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style1.css"> --%>
<link href="<%=request.getContextPath()+"/css/slider.css"%>" rel="stylesheet">
<script src="<%=request.getContextPath()+"/js/jquery-3.5.1.min.js"%>"></script>
<script src="<%=request.getContextPath()+"/js/jquery.slidertron-1.1.js"%>"></script>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()+"/css/people.css"%>">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/modal.css">
<style>
/* 	彈出視窗 */
	.signform{
		border:1px solid black;
		width:40%;
		margin-left:10px;
		min-width:400px;
	}
	.signform div{
		display:block;
/* 		max-width:180px; */
		margin:10px auto;
		width:50%;
	}
	input.signinput{
		width:100%;
		border: 1px solid black;
		
	}
	
	#login{
		margin:5px 10px;
	}
	div.change{
		width:200px;
	}
	
</style>
</head>
<body>
   <!-- 頭部 -->
	<%@ include file="/index_plat_header.jsp" %>
 <% 


	CustinfoVO custinfVO = (CustinfoVO) session.getAttribute("custinfoVO");
 	String str1= null;
		if(custinfVO != null){
		String custid = custinfVO.getCustid();
		List<FavoritesVO> list1 = new FavoritesService().getOneAccount(custid);
		String[] str = new String[list1.size()];
		int i = 0;
		for (FavoritesVO vo : list1) {
			str[i] =  vo.getResid();
			if(vo.getResid().equals(resid)){
				str1 = vo.getFavoritesid(); 
			}
			i++;
		}
		String ss = null;
		for (int x = 0; x < str.length; x++) {
	 		if (str[x].equals(resid)) {
	 			ss = resid;
	 		}
	 	}
		if((!(ss == null)) && ss.equals(resid)){
			pageContext.setAttribute("ok", 3);
		}
	}
	
	
  %> 
 

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
                <img src="${imgString}" alt="" class="sliderimg" onerror="this.src='<%=request.getContextPath()%>/images/null2.jpg'"/> </div>
            </div>
            </c:forEach>
          </div>
          
          <div class="indicator">
            <ul>
              <li class="active">1</li>
              <li>2</li>
              <li>3</li>
            </ul>
          </div>
          <div class="subnav">
                <ul>

                    <li><a href="<%=request.getContextPath()%>/front-end/cust/order_process/order_process.jsp?resid=<%=resid%>">線上訂位 <span> &gt; </span> </a></li>
                   	<li><a href="<%=request.getContextPath()%>/front-end/cust/shoppingcart/Menu.jsp">菜單 <span> &gt; </span> </a></li>
                   	<c:choose>
						<c:when test="${ok > 2}">
							<li><a
								href="<%=request.getContextPath()%>/favorites/favorites.do?favoritesid=<%=str1%>&action=delete2">取消收藏</a></li>
						</c:when>
						 <%-- //餐廳登入時 --%>
						<c:when test="${empty custinfoVO}">
							
						</c:when>
						
						<c:otherwise>
							<li><a href="<%=request.getContextPath()%>/favorites/favorites.do?resid=<%=resInfovo.getResid()%>&action=insert">加入收藏<span> &gt; </span></a></li>
						</c:otherwise>
					</c:choose>
                </ul>
            </div>
        </div>
        
      </div>
     
      <!-- ----------------------------------------輪播圖區塊------------------------------------ -->
    
     <div class="box w">
        <div class="box-hd">
            <h3>餐廳座位資訊</h3>
            <a href="#">返回頂部</a>
        </div>
        <hr>
        
        <div class="w clearfix">
            <div class="setdown">
<!--             改放座位圖 -->
				<canvas  width="1000" height="440" style="border: 2px solid black" id="myCanvas"></canvas>
            </div>

               <!-- 候位資訊 ok 已複製-->
<%--             <center><a>${param.resid} /<c:out value="${custinfoVO.custaccount}" default="anonymous"/></a></center> --%>
            <div class="waitinginfo">
                <h2 id="openClose"></h2> <!-- 目前候位狀態 -->
                <div class="bd">
                    <ul>
                        <li>
                            <h4>目前叫號號碼</h4>
                            <p><a id="currentNumber"></a></p>
                        </li>
						<li>
                            <h4>你的候位號碼</h4>
                            <p><a id="userNumber"></a></p>
                        </li>
                        <li>
                             <h4>你的候位人數</h4>
                            <p><a id="userSeat"></a></p>
                        </li>
                        <li>
                            <h4>目前等候組數</h4>
                            <p><a id="waitingNumber"></p>
                        </li>
                        <li>
                           <h4>距離你的候位號碼還剩餘</h4>
                            <p><a id="remainingNumber"></a></p>
                        </li>
                    </ul>
                    <a class="more" id="getUserNumber">領取候位號碼</a>
                </div>
            </div>
        </div>
        
        <!-- Modal -->
	<div id="peopleModal" class="modal">
		<!-- Modal content -->
		<div class="modalContent">
			<div class="people">
				<h2>請選擇你要候位的人數</h2>

				<select class="numsty" id="userSeatSelect">
					<option>1</option>
					<option>2</option>
					<option>3</option>
					<option>4</option>
					<option>5</option>
					<option>6</option>
					<option>7</option>
					<option>8</option>
					<option>9</option>
					<option>10</option>
				</select>
				<div class="man">人</div><br>

				<div class="next">
					<div class="determine" id="modalDetermine"><a>確定</a></div>
					<div class="cancel" id="modalCancel"><a>取消</a></div>
				</div>
			</div>
		</div>
	</div>



    <div class="box w">
        <div class="box-hd">
            <h3>餐廳資訊</h3>
            <a href="#">返回頂部</a>
        </div>
        <hr>

        <div class="w clearfix">
            <div class="resinfo">
                <ul>
                    <li>電話 : ${resInfovo.contactphon}</li>
                    <li>親子空間  : ${resInfovo.parentchild}</li>
                    <li>無障礙空間 : ${resInfovo.barrierfree}</li>
                    <li>營業時間 :</li>
                    <li>地址 : ${resInfovo.resaddid}</li>
                    <li>交通資訊 : ${resInfovo.traffic}</li>
                </ul>
            </div>
            <div class="mapdiv">        
                   
            </div>
        </div>
    </div>
    
	
   
    	
	<!-- footer  -->
    <%@ include file="/index_plat_footer.jsp" %>
	
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
<script>
$(window).on('load', function () {
	
    
	

// 	sendMessage(selectTime,time);
	   
    // canvas 的js設定
    /*存 input 的value*/
  
      // canvas 的js設定
    /*存 input 的value*/
  
    var resid = "${param.resid}";
    console.log("el : "+resid);
    
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
    w , h are stand for width and height,individually 
    修正拿到後會是紅色變黃色*/
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
		
    	
//         let hittedIndex = handleMouseDown(event);
//         lightBorder(hittedIndex);
    })
    myCanvas.addEventListener("mousemove", function (
        event) {
        // handleMouseMove(event);
    })

    myCanvas.addEventListener("mouseup",
        function (event) {
            // handleMouseUp(event);
        })


    /*紀錄滑鼠點擊時的X Y 座標*/
    function handleMouseDown(event) {
    	console.log("下面那個是canvas在的地方");
    	console.log($("#myCanvas").offset());
        startX = event.pageX - $("#myCanvas").offset().left;
        startY = event.pageY - $("#myCanvas").offset().top;
        selectedE = getHittedElement(dataArray,event);
        /*複製選擇欄內的物件。
        只有點擊旁邊選擇欄時，才會複製物品並push到seat裡面。當連擊時會連續產生很多個物件*/
        draw();
        focusIndexOfDataArray = selectedE;

        if (selectedE >= 0
            && selectedE < fixArray.length) {
            /*setting seat number */
            dataArray[selectedE].seatNumber = countNumber;
            countNumber++;
            seatArray.push(dataArray[selectedE]);

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
        console.log("畫布的width");
//         console.log($("#myCanvas").width())
//         ctx.clearRect(0, 0, $("#myCanvas").width(), $("#myCanvas").height());
        ctx.clearRect(0, 0, 1000, 1000);//找不到動態取法 先這樣
//         ctx.clearRect(0, 0, canvas.width, canvas.height);

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
                // console.log("test color is "+ele.color);
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
                ctx.fillStyle = ele.color;//要全部換成紅色嗎?
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
    var custid = "<c:out value="${custinfoVO.custid}" default="tourist"/>"
    var MyPoint = "/Resseatstate/" + "${resid}" + "/"+custid;//餐廳和消費者改成EL:userid,從request拿
    var time;
    var host = window.location.host;
    var path = window.location.pathname;
   
    var webCtx = path.substring(0, path.indexOf('/', 1));
    var endPointURL = "ws://" + host + webCtx + MyPoint;
    console.log("endPoint"+MyPoint);
    var webSocket;
    connect();
    

    function connect() {
        // create a websocket
       
        console.log(endPointURL);
        
        webSocket = new WebSocket(endPointURL);

        webSocket.onopen = function (event) {
            console.log("Connect Success!");
            if(custid != "tourist"){
            	sendMessage(false,"now");
            }else{
            	console.log("是遊客等待遊客號碼")
            }
        };
        webSocket.onmessage = function (event) {
        	//如果是遊客第一次拿到的會是號碼
        	if(custid === "tourist"){
        		console.log("拿到遊客號碼 : "+event.data +" 再次傳送message要求座位");
        		custid = event.data;
        		sendMessage(false,"now");
        		return;
        	}
            //will get message when get connection
            seatArray = [];
            console.log("拿回來的 :"+event.data)
            console.log("拿回來的 :"+event)
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
        messageObj.custid = custid;
		if(selectTime){
			messageObj.seatData = [];
		}else{
			messageObj.seatData = seatArray;
		}
		
//         let messageObj = {
//             "resid": resid,//用EL
//             "time": "now",// 用js改
//             "seatData": seatArray,
//             "custid": "C00001"
//         }

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

<script>
	// 宣告 websocket 相關變數
	var resid = '${param.resid}';
	var userName = "<c:out value="${custinfoVO.custaccount}" default="anonymous"/>"
	var uri = "/WaitingWS/"+resid+"/"+userName; // userName 從 LoginServlet 傳入
	var host = window.location.host;
	var path = window.location.pathname;
	var webCtx = path.substring(0, path.indexOf('/', 1));
	var endPointURL = "ws://" + host + webCtx + uri;
	var webSocket;
	var number;
	
	// websocket 事件處理函式
	function webSocketConnect() {
		// 建立 websocket 物件
		webSocket = new WebSocket(endPointURL);
		
		// 執行 websocket 連線
		webSocket.onopen = function(event) {
			console.log("Connect Success!");
		};
		
		// 接收 websocket 回傳的候位狀態
		webSocket.onmessage = function(event) {
			number = JSON.parse(event.data);
			console.log(number);
			<c:choose>
			 	<c:when test="${not empty custinfoVO}">
			 		var waiting = parseInt(number.total)-parseInt(number.current);
					var remaining = "";
					if (number.user !== "") remaining = parseInt(number.user)-parseInt(number.current);
					$("#currentNumber").html(number.current + "號");
					$("#userNumber").html(number.user + "號");
					$("#userSeat").html(number.seat + "位");
					$("#waitingNumber").html(waiting + "組");
					$("#remainingNumber").html(remaining + "組");
			
				// 設定開放 / 不開放候位
					openClose = number.openClose;
					if (openClose === "open") $("#openClose").html("目前候位狀態");
					else $("#openClose").html("目前不開放候位");
				</c:when>
			
			
			<c:otherwise>
			var waiting = parseInt(number.total)-parseInt(number.current)
			$("#currentNumber").html(number.current + "號");
			$("#waitingNumber").html(waiting + "組");
			
			// 設定開放 / 不開放候位
			openClose = number.openClose;
			if (openClose === "open") $("#openClose").html("目前候位狀態");
			else $("#openClose").html("目前不開放候位");
			</c:otherwise>
		</c:choose>
		}
	}
	
	// 領取候位號碼 按鈕事件處理
	
	$("#getUserNumber").click(function() {
		<c:choose>
	 	<c:when test="${not empty custinfoVO}">
		if (openClose === "open") {
			// 開啟 modal
			if ((number.user === "") || (parseInt(number.current) > parseInt(number.user))) {
				$("#peopleModal").attr("style", "display:block");
			} else {
				alert("你已經取過候位號碼了！");
			}
		} else {
			alert("目前不開放候位！")
		}
		</c:when>
		<c:otherwise>
		swal({
			  title: "你尚未登入",
			  text: "你要去登入嗎?",
			  icon: "info",
			  buttons: true,
			  dangerMode: true,
			})
			.then((willDelete) => {
			  if (willDelete) {
			    swal(location.href="<%= request.getContextPath()%>/login-html/login/custLogin.jsp", {
			      icon: "success",
			    });
			  } else {
// 			    swal("Your imaginary file is safe!");
			  }
			});

		
		</c:otherwise>
		</c:choose>
		
	});
	

	// modal 確定 按鈕事件處理
	<c:if test="${not empty custinfoVO}">
	$("#modalDetermine").click(function() {
		// 取得你的候位人數
		var userSeat = $("#userSeatSelect").val();
		
		// 領取候位號碼
		var event = {
			"resid": resid,
			"userName" : userName,
			"userSeat": userSeat,
			"action" : "getUserNumber"
		};
		webSocket.send(JSON.stringify(event));

		// 關閉 modal
		$("#peopleModal").attr("style", "display:none");
	});
	
	// modal 取消 按鈕事件處理
	$("#modalCancel").click(function() {
		// 關閉 modal
		$("#peopleModal").attr("style", "display:none");
	});
	</c:if>
	
	// 等網頁的全部的內容（包括圖片、CSS、JS）載入後才會觸發
	window.onload = function() {
		webSocketConnect();
	};

</script>


	

       
</body>
</html>