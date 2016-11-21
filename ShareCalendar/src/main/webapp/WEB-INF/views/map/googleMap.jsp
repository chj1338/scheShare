<%@ page pageEncoding='utf-8' %>
<!doctype html>
<head>
	<meta http-equiv=X-UA-Compatible content=IE=EmulateIE7 />
	<meta http-equiv="content-type" content="text/html; charset=EUC-KR"/> 
	
	<title>구글맵 테스트</title>
	<style>
		#map {
				width:1027px;
				height:768px;
				margin:10px;
				float:left;
			}
				
		#sidebar {
				margin:10px;
			}
		
		.sb_blue button {
				text-align: left;
				cursor:pointer;
				background-color: #99b3cc; 
				font-family: Verdana; 
				margin: 1px;
		  }
		  
		.sb_blue button:focus {background-color: #eee;}
		.sb_blue button:hover {background-color: #fff;}
	</style>
	
	<script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false"> </script>
<!-- 
	<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false"> </script>
 -->
</head>

<body>
	<div id="headLine">
		<h3>지도찾기(구글API)</h3>
	</div>
		
	<div id="search">주소 : <input type="text" id="adres"/><input type="button" id="searchBtn" value="검색" onClick="findMap()"/></div>
	
	<div id="map"></div>
	<div id="sidebar" class="sb_blue"></div>
	<!-- 
	<a href="makemarker_sidebar.htm">Back</a> 
	 -->
	<div id="map-canvas2-description"></div> 
	 
	<script>
	/**
	 * map
	 */
	var mapOpts = {
		zoom: 13,    
		center: new google.maps.LatLng(37.5172363, 127.0473248),	// 기준좌표(강남)
	  	mapTypeId: google.maps.MapTypeId.ROADMAP,
	
	  	panControl: true, 
	  	zoomControl: true, 			// zoom 컨트롤 버튼 존재 여부
	  	mapTypeControl: true,		// map type 컨트롤 존재여부
	  	streetViewControl: true,	// 스트리트뷰 버튼 존재여부
	  	overviewMapControl: true,
	
	  	scaleControl: true,
	  	scrollwheel: true
	};
	
	var map = new google.maps.Map(document.getElementById("map"), mapOpts);
	//  We set zoom and center later by fitBounds()
	
	/**
	 *   fit viewport to markers
	 */
	//map.fitBounds(markerBounds);
	
	/**
	 * makeMarker() ver 0.2
	 * creates Marker and InfoWindow on a Map() named 'map'
	 * creates sidebar row in a DIV 'sidebar'
	 * saves marker to markerArray and markerBounds
	 * @param options object for Marker, InfoWindow and SidebarItem
	 * @author Esa 2009
	 */
	var infoWindow = new google.maps.InfoWindow();
	var markerBounds = new google.maps.LatLngBounds();
	var markerArray = [];
	 
	function makeMarker(options){
		var pushPin = new google.maps.Marker({map:map});
	  	pushPin.setOptions(options);
	  	google.maps.event.addListener(pushPin, "click", function(){
	    	infoWindow.setOptions(options);
	    	infoWindow.open(map, pushPin);
	    	if(this.sidebarButton)
	    		this.sidebarButton.button.focus();
	  	});
	  	
	  	var idleIcon = pushPin.getIcon();
	  	if(options.sidebarItem){
	    	pushPin.sidebarButton = new SidebarItem(pushPin, options);
	    	pushPin.sidebarButton.addIn("sidebar");
	  	}
	  	
	  	markerBounds.extend(options.position);
	  	markerArray.push(pushPin);
	  	
	  	return pushPin;
	}
	
	google.maps.event.addListener(map, "click", function(){
	  infoWindow.close();
	});
	
	
	/**
	 * Creates an sidebar item 
	 * @constructor
	 * @author Esa 2009
	 * @param marker
	 * @param options object Supported properties: sidebarItem, sidebarItemClassName, sidebarItemWidth,
	 */
	function SidebarItem(marker, opts){
		var tag = opts.sidebarItemType || "button";
		var row = document.createElement(tag);
		row.innerHTML = opts.sidebarItem;
		row.className = opts.sidebarItemClassName || "sidebar_item";  
		row.style.display = "block";
		row.style.width = opts.sidebarItemWidth || "120px";
		
		row.onclick = function(){
			google.maps.event.trigger(marker, 'click');
		};
		
		row.onmouseover = function(){
			google.maps.event.trigger(marker, 'mouseover');
		};
		
		row.onmouseout = function(){
			google.maps.event.trigger(marker, 'mouseout');
		};
		
		this.button = row;
	}
	
	// adds a sidebar item to a <div>
	SidebarItem.prototype.addIn = function(block){
	  if(block && block.nodeType == 1)this.div = block;
	  else
	    this.div = document.getElementById(block)
	    || document.getElementById("sidebar")
	    || document.getElementsByTagName("body")[0];
	  this.div.appendChild(this.button);
	};
	
	// deletes a sidebar item
	SidebarItem.prototype.remove = function(){
	  if(!this.div) return false;
	  this.div.removeChild(this.button);
	  return true;
	};
	
	//
	function locationChg(address, title, content) {
		var geocoder = new google.maps.Geocoder();

		geocoder.geocode( { 'address': address}, function(results, status) {
	        var location = results[0].geometry.location;	
	        var lat = location.lat;
	        var lng = location.lng;
	        
//	        reverseGeocode(lat, lng);
	        
	        var markerOpts = {
		    		  position: location,
		    		  title: title,
		    		  sidebarItem: title,
		    		  content: title + " : " + address + "<br>" + "<br><a href='https://maps.google.com/?q="+ location + "' target='_blank'>View on Google Map</a>"
	        };
	        
			if (status == google.maps.GeocoderStatus.OK) {
		    	makeMarker(markerOpts); 	// 마커찍기
		    	map.fitBounds(markerBounds);
	        } else {
	            $('#map-canvas2-description').html('위도와 경도를 찾을 수 없습니다.');
	        }
			
	      	map.setZoom(17);
	      	map.setCenter(lat, lng);
		});	
	}
	
/*  
// 좌표를 기준으로 주소 가져오기 - 실패
	function reverseGeocode(relat, relng) {
		var regeocoder = new google.maps.Geocoder();
		var relatlng = new google.maps.LatLng(relat, relng);
		var info = new google.maps.InfoWindow({
			map: map,
			position : relatlng
		});

		regeocoder.geocode({ 'latLng' : relatlng	}), function(results, status) {
			alert(status);
			if(status == google.maps.GeocoderStatus.OK) {
				if(results[0]) {
					info.setContent(results[0].formatted_address);
					info.open(map, marker);
				} else {
					alert("Geocoder failed due to : "+ status);
				}
			}
		};		
	}
 	
 	// 좌표를 기준으로 주소 가져오기 - 실패
    function showAddress(response) {
        map.clearOverlays();
        if (!response || response.Status.code != 200) {
          alert("Status Code:" + response.Status.code);
        } else {
          place = response.Placemark[0];
          point = new GLatLng(place.Point.coordinates[1],
                              place.Point.coordinates[0]);
          marker = new GMarker(point);
          map.addOverlay(marker);
          alert(place.address);
          marker.openInfoWindowHtml(
          '<b>orig latlng:</b>' + response.name + '<br/>' + 
          '<b>latlng:</b>' + place.Point.coordinates[1] + "," + place.Point.coordinates[0] + '<br>' +
          '<b>Status Code:</b>' + response.Status.code + '<br>' +
          '<b>Status Request:</b>' + response.Status.request + '<br>' +
          '<b>Address:</b>' + place.address + '<br>' +
          '<b>Accuracy:</b>' + place.AddressDetails.Accuracy + '<br>' +
          '<b>Country code:</b> ' + place.AddressDetails.Country.CountryNameCode);
        }
      }
  */
  
	function findMap() {
		var title = address;
		var content = address;
		var address = "";
		address = document.getElementById("adres").value;
		
		if(address == "") address = "서울역";

		locationChg(address, title, content);
	}
	</script>
	 
</body>

</html>