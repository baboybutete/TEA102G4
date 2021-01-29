<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>餐廳座位狀態</title>
</head>
<style>
	div.wrapper_canvas{
		position:absolute;
		top:120px;
		left:250px;
	}
	#myCanvas{
		background-color:white;
		margin-top:25px;
	}
	input {
		border: 1px solid black;
		position: absolute;
		left: 420px;
		top: 130px;
		width: 30px;
	}

	input.hideIt {
		display: none;
	}
	div.canvas_button{
		position:absolute;
		top:0;
		right:0;
	}
		
	
</style>
<body>

	<%@ include file="/front-end/res/res_member_centre_header.jsp" %>
	
	<%-- canvas --%>
	<div class="wrapper_canvas">
		<canvas width="1000" height="440" style="border: 1px solid black" id="myCanvas"></canvas>
	</div>
	

	<%@ include file="/front-end/res/res_member_centre_footer.jsp" %>



<script src="<%=request.getContextPath()%>/js/jquery-3.5.1.js"></script>
 <script>
        $(window).on('load', function () {
        	var selectTime = "now"
        	
        	
//             $("div.header-btn").on("click", function () {
//                 $("div.header-dropdown-menu").slideToggle();
//             });
//             $(".lkfUl button").on("click", function () {
//                 $(this).parents("div").find(".lkfUl button").attr("class", "sc-fznMnq jWfUoz time-slot");

//                 $(this).addClass("sc-fznMnq hoqQtU time-slot selected").siblings().attr("class", "sc-fznMnq jWfUoz time-slot");
//                 console.log($(this).text());
                
//                 //請求座位狀態
//                 time = $(".lkuZcd input").val()+" "+$(".lkfUl button.selected").text();
//                 console.log(time);
//                 let selectTime = true;
//                 sendMessage(selectTime,time);
                
//             });
//             $("button.hamburger").on("click", function () {
//                 $(this).toggleClass("is-active");
//             });
//             var a = "";
//             var b = "";

//             $("button.dTkVEz").on("click", function () {
//                 if ($(".bqrfuB select").val() == null || $(".bqrfuB select").val() == "請選擇用餐人數") {
//                     alert("人數不能為空");
//                     return;
//                 } else if ($(".lkuZcd input").val() == null) {
//                     alert("日期不能為空");
//                     return;
//                 } else if ($(".lkfUl button.selected").text() == "") {
//                     alert("請選擇時間");
//                     return;
//                 }

//                 a = $(".lkuZcd input").val() + " " + $(".lkfUl button.selected").text();
//                 b = $(".bqrfuB select").val();

//                 // console.log(a);


//                 var original_data = JSON.parse(sessionStorage.getItem("form_data"));
//                 var data_arr = {};
//                 data_arr.people = b;
//                 data_arr.date = a;
                
                
//                 //選取座位
// 				data_arr.seats = JSON.parse(sessionStorage.getItem('seats'));//座位可以空嗎?
//                 console.log(data_arr.seats);
//                 sessionStorage.setItem("form_data", JSON.stringify(data_arr));
                
             
// 				let time;
// 				let host = window.location.host;
// 				let path = window.location.pathname;
// 				console.log("host : "+host)
// 				console.log("path : "+path)
// 				let webCtx = path.substring(0, path.indexOf('/', 1));
// 				let endPointURL = "http://" + window.location.host
// 						+ webCtx + "/order_process1.jsp";
                
//                 console.log(endPointURL)
//                 webSocket.close();
// // 				location.href = 'http://127.0.0.1:5500/index1.html';
// 				location.href = endPointURL;
//             });


//             $('#f_date2').datetimepicker({
//                 theme: '',              //theme: 'dark',
//                 timepicker: false,       //timepicker:true,
//                 step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘)
//                 format: 'Y-m-d',         //format:'Y-m-d H:i:s',
//                 value: 'new Date()',
//                 //disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
//                 //startDate:	            '2017/07/10',  // 起始日
//                 //minDate:               '-1970-01-01', // 去除今日(不含)之前
//                 //maxDate:               '+1970-01-01'  // 去除今日(不含)之後
//             });
//             var somedate1 = new Date();
//             $('#f_date2').datetimepicker({
//                 beforeShowDay: function (date) {
//                     if (date.getYear() < somedate1.getYear() ||
//                         (date.getYear() == somedate1.getYear() && date.getMonth() < somedate1.getMonth()) ||
//                         (date.getYear() == somedate1.getYear() && date.getMonth() == somedate1.getMonth() && date.getDate() < somedate1.getDate())
//                     ) {
//                         return [false, ""]
//                     }
//                     return [true, ""];
//                 }
//             });


            // canvas 的js設定
            /*存 input 的value*/
//             $(".getCustid").on("click", function () {
//                 console.log($(".custid").val());
//             })
            var resid = "R00001";
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
            // fixArray.push({/*unavailable chair*/
            // 	x : 10,
            // 	y : 190,
            // 	w : chairwidth,
            // 	h : chairlength,
            // 	color : 'black',
            // 	itemNumber : 1
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



                    //若座位是itemNumber(紅色，被其他人選過的則不能再改變) 餐廳可以改變顏色
//                     if (targetEl.itemNumber === 2) {
//                         return;
//                     }
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
                        sendMessage(selectTime,"now");

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
                startX = event.pageX - myCanvasPosition.left;//重新定義起始位置X
                startY = event.pageY - myCanvasPosition.top;//重新定義起始位置Y
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
                ctx.clearRect(0, 0, myCanvas_width, myCanvas_height);

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
           
            var MyPoint = "/Resseatstate/" + "${resInfoVO.resid}" + "/"+"${resInfoVO.resemail}";//餐廳和消費者改成EL:userid,從request拿
            
            var host = window.location.host;
            var path = window.location.pathname;
            
            var webCtx = path.substring(0, path.indexOf('/', 1));
            
            var endPointURL = "ws://" + host + webCtx + MyPoint;
            
            var webSocket;
            connect();
    
            function connect() {
                // create a websocket
               
     
                console.log(endPointURL);
                
                webSocket = new WebSocket(endPointURL);
                
                webSocket.onopen = function (event) {
                    console.log("Connect Success!");
                    let selecTime = true;
                	sendMessage(selecTime,"now");
                };
                webSocket.onmessage = function (event) {
                    //will get message when get connection
                    seatArray = [];
                    seatArray = JSON.parse(event.data);
                    coveringToNumber(seatArray);//covering String to Number
                    //看到紅色的可以取消掉
 					transRedToYellow(seatArray);
                    intialDataArray();
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

			function transRedToYellow(seatArray){
				for (let i = 0; i < seatArray.length; i++) {
                    let obj = seatArray[i];
                    if(obj.itemNumber ===2){
                    	obj.itemNumber = 3;
                        obj.color = "yellow";
                    }
                
                }
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
                    console.log(number);
                    if(! isNaN(number)){
                    	obj.seatNumber = number;
                    }
                    	 
                    
                }

            }
            $("#send").on("click",function(){
            	let selecTime = true;
            	sendMessage(selecTime,"now");
            })

            function sendMessage(selectTime,time) {
                /*暫時抓custid*/
                let messageObj = {};
                messageObj.resid = "${resInfoVO.resid}";//session;
                messageObj.time = time;
                messageObj.custid = "${resInfoVO.resemail}"//這裡不需要custid
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