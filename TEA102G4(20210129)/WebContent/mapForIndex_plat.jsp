<%@ page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="com.map.controller.*"%>
<%@ page import="com.map.model.*"%>
<%@ include file="/others/getBaseStirng.file"%>

<%
// List<MapVO> listMapVO = (ArrayList<MapVO>) request.getAttribute("listMapVO");
// pageContext.setAttribute("listMapVO",listMapVO);

MapService mapSvc = new MapService();
List<MapVO> list = mapSvc.getAll();
pageContext.setAttribute("list",list);
%>
		
		
<%
// System.out.println("listMapVO:" + listMapVO);
// if(listMapVO != null){
	
// 	for(MapVO mapVO: listMapVO) {
// 		String resid = mapVO.getResid();
// 		String resname = mapVO.getResname();
// 		String resaddid = mapVO.getResaddid();
// 		String seatData = mapVO.getSeatData();
// 		byte[] imgbyte = mapVO.getResimg();
		
// 		System.out.println("resid:" + resid);
// 		System.out.println("resname:" + resname);
// 		System.out.println("resaddid:" + resaddid);
// 		System.out.println("seatData:" + seatData);
// 	}
// }
%>

<html>
<head>
<title>地圖</title>
<script src="https://polyfill.io/v3/polyfill.min.js?features=default"></script>
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyC3QPntU9wpgbFyz4OreKkrc4igaSo2eWc&callback=initMap&libraries=&v=weekly" defer></script>
<script src="https://unpkg.com/@googlemaps/markerclustererplus/dist/index.min.js"></script>
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
<style type="text/css">
#map {
   	height: 90%;
    width: 95%;
    margin-left: 32px;
}
html,
body {
	height: 100%;
	margin: 0;
	padding: 0;
}
h2{
	background-color: yellow;
}
</style>

</head>
<script src="<%=request.getContextPath()+"/js/jquery-3.5.1.min.js"%>"></script>

<body>

    
    <!--------------------------- MAP顯示的欄位------------------------------------>
    
    
	<div id="map"></div>
	

	<!--------------------------- MAP顯示的欄位------------------------------------>


</body>


<script>
let map, infoWindow;
let marker;

function initMap() {
    var markers = [];
    var infoWindows = [];
    const map = new google.maps.Map(document.getElementById("map"), {
        zoom: 13,
        center: { lat: 25.052039837803843, lng: 121.54362158717178 },
        zoomControl: true,
        mapTypeControl: false,
        scaleControl: false,
        streetViewControl: false,
        rotateControl: false,
        fullscreenControl: true,
        mapTypeControl: false,
        //Map Style
//         styles: [
//         	  {
//         		    "elementType": "geometry",
//         		    "stylers": [
//         		      {
//         		        "color": "#242f3e"
//         		      }
//         		    ]
//         		  },
//         		  {
//         		    "elementType": "labels.icon",
//         		    "stylers": [
//         		      {
//         		        "weight": 1
//         		      }
//         		    ]
//         		  },
//         		  {
//         		    "elementType": "labels.text",
//         		    "stylers": [
//         		      {
//         		        "weight": 1.5
//         		      }
//         		    ]
//         		  },
//         		  {
//         		    "elementType": "labels.text.fill",
//         		    "stylers": [
//         		      {
//         		        "color": "#746855"
//         		      }
//         		    ]
//         		  },
//         		  {
//         		    "elementType": "labels.text.stroke",
//         		    "stylers": [
//         		      {
//         		        "color": "#242f3e"
//         		      }
//         		    ]
//         		  },
//         		  {
//         		    "featureType": "administrative.locality",
//         		    "elementType": "labels.text.fill",
//         		    "stylers": [
//         		      {
//         		        "color": "#d59563"
//         		      }
//         		    ]
//         		  },
//         		  {
//         		    "featureType": "poi",
//         		    "elementType": "labels.text.fill",
//         		    "stylers": [
//         		      {
//         		        "color": "#d59563"
//         		      }
//         		    ]
//         		  },
//         		  {
//         		    "featureType": "poi.attraction",
//         		    "elementType": "labels.icon",
//         		    "stylers": [
//         		      {
//         		        "visibility": "off"
//         		      }
//         		    ]
//         		  },
//         		  {
//         		    "featureType": "poi.business",
//         		    "stylers": [
//         		      {
//         		        "visibility": "off"
//         		      }
//         		    ]
//         		  },
//         		  {
//         		    "featureType": "poi.business",
//         		    "elementType": "labels.icon",
//         		    "stylers": [
//         		      {
//         		        "visibility": "off"
//         		      }
//         		    ]
//         		  },
//         		  {
//         		    "featureType": "poi.government",
//         		    "elementType": "labels.icon",
//         		    "stylers": [
//         		      {
//         		        "visibility": "off"
//         		      }
//         		    ]
//         		  },
//         		  {
//         		    "featureType": "poi.medical",
//         		    "elementType": "labels.icon",
//         		    "stylers": [
//         		      {
//         		        "visibility": "off"
//         		      }
//         		    ]
//         		  },
//         		  {
//         		    "featureType": "poi.park",
//         		    "elementType": "geometry",
//         		    "stylers": [
//         		      {
//         		        "color": "#263c3f"
//         		      }
//         		    ]
//         		  },
//         		  {
//         		    "featureType": "poi.park",
//         		    "elementType": "labels.icon",
//         		    "stylers": [
//         		      {
//         		        "visibility": "off"
//         		      }
//         		    ]
//         		  },
//         		  {
//         		    "featureType": "poi.park",
//         		    "elementType": "labels.text",
//         		    "stylers": [
//         		      {
//         		        "visibility": "off"
//         		      }
//         		    ]
//         		  },
//         		  {
//         		    "featureType": "poi.park",
//         		    "elementType": "labels.text.fill",
//         		    "stylers": [
//         		      {
//         		        "color": "#6b9a76"
//         		      }
//         		    ]
//         		  },
//         		  {
//         		    "featureType": "poi.place_of_worship",
//         		    "elementType": "labels.icon",
//         		    "stylers": [
//         		      {
//         		        "visibility": "off"
//         		      }
//         		    ]
//         		  },
//         		  {
//         		    "featureType": "poi.school",
//         		    "elementType": "labels.icon",
//         		    "stylers": [
//         		      {
//         		        "visibility": "off"
//         		      }
//         		    ]
//         		  },
//         		  {
//         		    "featureType": "poi.sports_complex",
//         		    "elementType": "labels.icon",
//         		    "stylers": [
//         		      {
//         		        "visibility": "off"
//         		      }
//         		    ]
//         		  },
//         		  {
//         		    "featureType": "road",
//         		    "elementType": "geometry",
//         		    "stylers": [
//         		      {
//         		        "color": "#38414e"
//         		      }
//         		    ]
//         		  },
//         		  {
//         		    "featureType": "road",
//         		    "elementType": "geometry.stroke",
//         		    "stylers": [
//         		      {
//         		        "color": "#212a37"
//         		      }
//         		    ]
//         		  },
//         		  {
//         		    "featureType": "road",
//         		    "elementType": "labels.text.fill",
//         		    "stylers": [
//         		      {
//         		        "color": "#9ca5b3"
//         		      }
//         		    ]
//         		  },
//         		  {
//         		    "featureType": "road.highway",
//         		    "elementType": "geometry",
//         		    "stylers": [
//         		      {
//         		        "color": "#746855"
//         		      }
//         		    ]
//         		  },
//         		  {
//         		    "featureType": "road.highway",
//         		    "elementType": "geometry.stroke",
//         		    "stylers": [
//         		      {
//         		        "color": "#1f2835"
//         		      }
//         		    ]
//         		  },
//         		  {
//         		    "featureType": "road.highway",
//         		    "elementType": "labels.text.fill",
//         		    "stylers": [
//         		      {
//         		        "color": "#f3d19c"
//         		      }
//         		    ]
//         		  },
//         		  {
//         		    "featureType": "transit",
//         		    "elementType": "geometry",
//         		    "stylers": [
//         		      {
//         		        "color": "#2f3948"
//         		      }
//         		    ]
//         		  },
//         		  {
//         		    "featureType": "transit.station",
//         		    "elementType": "labels.text.fill",
//         		    "stylers": [
//         		      {
//         		        "color": "#d59563"
//         		      }
//         		    ]
//         		  },
//         		  {
//         		    "featureType": "water",
//         		    "elementType": "geometry",
//         		    "stylers": [
//         		      {
//         		        "color": "#17263c"
//         		      }
//         		    ]
//         		  },
//         		  {
//         		    "featureType": "water",
//         		    "elementType": "labels.text.fill",
//         		    "stylers": [
//         		      {
//         		        "color": "#515c6d"
//         		      }
//         		    ]
//         		  },
//         		  {
//         		    "featureType": "water",
//         		    "elementType": "labels.text.stroke",
//         		    "stylers": [
//         		      {
//         		        "color": "#17263c"
//         		      }
//         		    ]
//         		  }
//         		],
            // Map Style
    });

    var geocoder = new google.maps.Geocoder();
 
  //info windows
    var info_config = [
    	<c:forEach items="${list}" var="item" varStatus="loop">
    	
    	<%  
    		MapVO mapVO = (MapVO) pageContext.getAttribute("item");
			if(mapVO != null){
				byte[] imgbyte = mapVO.getResimg();
				String imgString = getBaseString(imgbyte);
				pageContext.setAttribute("imgString",imgString);
			}
		%>
        	'<h1>${item.resname}</h1>' +
        '<span>地址: ${item.resaddid}</span><br/>' +
        	'<a>連絡電話: ${item.contactphon}</a><br/>' +
        '<a>營業時間: <fmt:formatDate value="${item.opening}" pattern="HH:mm"/></a>' +
        '<a> - <fmt:formatDate value="${item.closing}" pattern="HH:mm"/></a><br/>' +
        '<img src="${imgString}" width="200px" height="100px"><br/>' +
        '<h2>剩餘'+ ${item.seatData} +'個座位</h2>'
        ${!loop.last ? ',' : ''} 
       
        </c:forEach>
    ];
  
    //建立地圖 marker 的集合
    <jsp:useBean id="resinfoService" class="com.resinfo.model.ResInfoService"></jsp:useBean>
    
    var marker_config = [
    	<c:forEach items="${list}" var="item" varStatus="loop">
    	<c:if test="${resinfoService.getOneResInfo(item.resid).status eq '使用'}">
    	{
	        position: { lat: ${item.lat}, lng: ${item.lng}},
	        map: map,
	        icon: "<%=request.getContextPath()%>/images/sign_restaurant1.png",
	        title: '${item.resname}'
    	}
	 	${!loop.last ? ',' : ''}
	 	 </c:if>
	 </c:forEach>
	];

    //設定 Info window 內容
    info_config.forEach(function (e, i) {
        infoWindows[i] = new google.maps.InfoWindow({
            content: e
        });
    });


    //標出 marker
    marker_config.forEach(function (e, i) {
        markers[i] = new google.maps.Marker(e);
        markers[i].setMap(map);
        markers[i].addListener('click', function () {
            infoWindows[i].open(map, markers[i]);
        });
    });
    

//     function _geocoder(address) {
//         geocoder.geocode({
//             address: address
//         }, function (results, status) {
//             if (status == google.maps.GeocoderStatus.OK) {
//                 LatLng = results[0].geometry.location;
//                 return results;
//             }
//         });
//     }



    // Create an array of alphabetical characters used to label the markers.
//     const labels = "";
    // const labels = "ABCUVWXYZ";

    // Add some markers to the map.DEFGHIJKLMNOPQRST
    // Note: The code uses the JavaScript Array.prototype.map() method to
    // create an array of markers based on a given "locations" array.
    // The map() method here has nothing to do with the Google Maps API.
//     const markerss = locations.map((location, i) => {
//         return new google.maps.Marker({
//             position: location,
//             label: labels[i % labels.length],
//         });
//     });
    // Add a marker clusterer to manage the markers.
//     new MarkerClusterer(map, markerss, {
//         imagePath:
//             "https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/m",

//     });

//  顯示目前位置
    infoWindow = new google.maps.InfoWindow();
    const locationButton = document.createElement("button");
    locationButton.textContent = "目前位置";
    locationButton.classList.add("custom-map-control-button");
    map.controls[google.maps.ControlPosition.TOP_CENTER].push(locationButton);
    locationButton.addEventListener("click", () => {
        // Try HTML5 geolocation.
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(
                (position) => {
                    const pos = {
                        lat: position.coords.latitude,
                        lng: position.coords.longitude
                    };
                    infoWindow.setPosition(pos);
                    infoWindow.setContent("您目前的位置");
                    infoWindow.open(map);
                    map.setCenter(pos);
                },
                () => {
                    handleLocationError(true, infoWindow, map.getCenter());
                }
            );
        } else {
            // Browser doesn't support Geolocation
            handleLocationError(false, infoWindow, map.getCenter());
        }
    });
    
//     marker = new google.maps.Marker({
//         map,
//         draggable: true,
//         animation: google.maps.Animation.DROP,
//         position: { lat: 25.052039837803843, lng: 121.54362158717178 },
//     });
//     marker.addListener("click", toggleBounce);
    
   
    
}


//Browser是否允許
function handleLocationError(browserHasGeolocation, infoWindow, pos) {
    infoWindow.setPosition(pos);
    infoWindow.setContent(
        browserHasGeolocation
            ? "Error: The Geolocation service failed."
            : "Error: Your browser doesn't support geolocation."
    );
    infoWindow.open(map);
}

// function toggleBounce() {
//     if (marker.getAnimation() !== null) {
//         marker.setAnimation(null);
//     } else {
//         marker.setAnimation(google.maps.Animation.BOUNCE);
//     }
// }
//加入平台的店家經緯度
// const locations = [
//     { lat: 25.052191523686883, lng: 121.54316080912172 },
//     { lat: 25.051882948633164, lng: 121.54335666520946 },
//     { lat: 25.051301534422393, lng: 121.5438325797223 },
//     { lat: 25.05055729387746, lng: 121.54352137675932 },
//     { lat: 25.051563270705447, lng: 121.54204616187285 },
//     { lat: 25.052117342646774, lng: 121.54461346683921 },
//     { lat: 25.051169687821446, lng: 121.54518745953962 },
//     { lat: 25.050392119411477, lng: 121.54452763615168 },
//     { lat: 25.052156220651764, lng: 121.54551468903291 },
//     { lat: 25.052131921909062, lng: 121.54592774920538 },
//     { lat: 25.052136781657918, lng: 121.5459331136231 },
//     { lat: 25.05237490912287, lng: 121.54747270156824 },
//     { lat: 25.048764672114196, lng: 121.54611287101574 },
//     { lat: 25.043081057350022, lng: 121.53959031217447 },
//     { lat: 25.042468682244635, lng: 121.53939182869026 },
//     { lat: 25.040272677270035, lng: 121.53578458283728 },
//     { lat: 25.060054623181653, lng: 121.5330166255714 },
//     { lat: 25.038248359395492, lng: 121.53235068565243 },
//     { lat: 25.04065943600797, lng: 121.5483878353761 },
//     { lat: 25.035628519477005, lng: 121.56305548472744 },
//     { lat: 25.052163292986283, lng: 121.56812161996356 },
//     { lat: 25.069477798314605, lng: 121.57622652607186 },
// ];

// var markerCluster = new MarkerClusterer(map, markers,
//     { imagePath: `${path}/m` });
</script>
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

</html>