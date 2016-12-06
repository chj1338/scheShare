<%@ page pageEncoding='utf-8' %>
<!doctype html>
<head>
	<meta http-equiv=X-UA-Compatible content=IE=EmulateIE7 />
	<meta http-equiv="content-type" content="text/html; charset=EUC-KR"/> 
	
	<title>네이버맵 테스트</title>
	<style>
		#mapArea {
			border:1px solid #ddd;
			display:block;
			width:100%;
			height:700px;
			clear:both; 
		}
	</style>

	<script type="text/javascript" src="https://openapi.map.naver.com/openapi/v3/maps.js?clientId=0hO1POVTd1Gfu7tJvrz7&submodules=geocoder"></script>
	
</head>

<body>
	<div id="headLine">
		<h3>지도찾기(네이버API)</h3>
	</div>
		
	<div id="search">
		주소 : <input type="text" id="adres"/>
		<input type="button" id="searchBtn" value="검색" onClick="findMap()"/>
		(번지나 도로명 번지까지 꼭 입력하세요!!!)<br>
		주소 : <input type="text" id="buld_name"/>
		<input type="button" id="buldBtn" value="지역명검색" onClick="findBuld()"/>
	</div>
	
	<div id="localBtn">
		<input type="button" id="to-jeju" value="제주" />
		<input type="button" id="to-dokdo" value="독도" />
		<input type="button" id="to-busan" value="부산" />
		<input type="button" id="to-seoul" value="서울" />
		<input type="button" id="to-1" value="1" />
	</div>
	
	<div id="mapArea"></div>
	<script>	
		// 지도 생성
		var position = new naver.maps.LatLng(37.3595704, 127.105399);
		var mapOptions = {
				center: position,
				zoom: 10
		};
		
		var map = new naver.maps.Map('mapArea', mapOptions);
 
		// 마커
		var markerOptions = {
			position : position,
			map: map
/* 
			icon: {
				url : '/resources/images/icon.png',
					size: new naver.maps.Size(48,48),
					origin: new naver.maps.Point(0, 0),
					anchor: new naver.maps.Point(11, 35)
			}
 */		
		};
		
		var marker = new naver.maps.Marker(markerOptions);

// 주소검색		http://openapi.map.naver.com/api/geocode.php?key=0hO1POVTd1Gfu7tJvrz7&encoding=utf-8&coord=tm128&query=경기도성남시분당구불정로6
// 지역검색		http://openapi.naver.com/search?key=0hO1POVTd1Gfu7tJvrz7&query=롯데마트+이마트+홈플러스&target=local&start=1&display=99

 		
		var geocodeOptions = {
			address: '불정로 6'				
		};
		
		naver.maps.Service.search
		
		naver.maps.Service.geocode(geocodeOptions , function(status, response) {
	        if (status !== naver.maps.Service.Status.OK) {
	            return alert('Something wrong!');
	        }

	        var result = response.result; // 검색 결과의 컨테이너
	        var items = result.items; 		// 검색 결과의 배열
//	        alert(items[0].address);

	        // do Something
	    });

	    /* 
{
  "result": {
    "total": 1,
    "userquery": "불정로 6",
    "items": [
      {
        "address": "경기도 성남시 분당구 불정로 6 그린팩토리",
        "addrdetail": {
          "country": "대한민국",
          "sido": "경기도",
          "sigugun": "성남시 분당구",
          "dongmyun": "불정로",
          "rest": " 6 그린팩토리"
        },
        "isRoadAddress": true,
        "point": {
          "x": 127.1052133,
          "y": 37.3595316
        }
      }
    ]
  }
}
	     */
 		
		// 버튼 설정
		var jeju = new naver.maps.LatLng(33.3590628, 126.534361),
	    busan = new naver.maps.LatLng(35.1797865, 129.0750194),
	    dokdo = new naver.maps.LatLngBounds(
	                new naver.maps.LatLng(37.2380651, 131.8562652),
	                new naver.maps.LatLng(37.2444436, 131.8786475)),
	    seoul = new naver.maps.LatLngBounds(
	                new naver.maps.LatLng(37.42829747263545, 126.76620435615891),
	                new naver.maps.LatLng(37.7010174173061, 127.18379493229875));

	$("#to-jeju").on("click", function(e) {
	    e.preventDefault();

	    map.setZoom(8);
	    map.setCenter(jeju);
	});

	$("#to-1").on("click", function(e) {
	    e.preventDefault();

	    map.setZoom(1, true);
	});

	$("#to-dokdo").on("click", function(e) {
	    e.preventDefault();

	    map.fitBounds(dokdo);
	});

	$("#to-busan").on("click", function(e) {
	    e.preventDefault();

	    map.panTo(busan);
	});

	$("#to-seoul").on("click", function(e) {
	    e.preventDefault();

	    map.panToBounds(seoul);
	});

	$("#panBy").on("click", function(e) {
	    e.preventDefault();

	    map.panBy(new naver.maps.Point(10, 10));
	});

	 
	 
	 </script>
	 <script>
	 // 주소검색 API
	 function findMap() {
		if($("#adres").val() == "") return;

		var geocodeOptions = {
				address: $("#adres").val()				
			};
			
			naver.maps.Service.geocode(geocodeOptions , function(status, response) {
		        if (status !== naver.maps.Service.Status.OK) {
		            return alert('Address Something wrong!');
		        }

		        var result = response.result; // 검색 결과의 컨테이너
		        var items = result.items; 		// 검색 결과의 배열

		        var position_x = items[0].point.x;
		        var position_y = items[0].point.y;

		        var position = new naver.maps.LatLng(position_y, position_x);

				map.setZoom(10);
				map.setCenter(position);
		    });

	 }
	</script>
	
 	<script type="text/javascript">
	// 지역검색 API
	var NAVER_API_KEY ="0hO1POVTd1Gfu7tJvrz7";
	var NAVER_API_URL ="http://openapi.naver.com/search?";
	
	function findBuld() {
		if($("#build_name").val() == "") return;
		
   		var loader = new URLLoader();
   		var req = new URLRequest();
   		var params = new URLVariables();
   
   		// 매개변수 값을 객체로 정의
   		params.parameters ={
		    "key" : NAVER_API_KEY,
		    "target" : "image",
		    "query" : ("#build_name").val(),
		    "display" : "10",
		    "sort" : "simm",
		    "filter" : "all"
   		};
   
	   //URL 정의
	   req.url = NAVER_API_URL + params.toString();
	   //request 보내기
	   var response = loader.load(req);
	   alert(response);
   
	   //로드 완료시 실행할 이벤트 정의
	   loader.addEvent(URLLoaderEvent.COMPLETE, onSearchCompleteHandler);
  }
 </script>

	
</body>

</html>