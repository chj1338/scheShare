<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %> 
<%@ include file="/WEB-INF/views/include/common.jsp"%>
 
<!DOCTYPE html>
<html>
<HEAD>
  <title>RSS 날씨</title>
  <style type="text/css">
  	table, tr, td {
  		border-style:solid;
  		border-collapse:collapse;
  		border-width:1px;
  	}
  	
  	thead tr td {
  	  	font-size:8pt;
  	 	text-align:center;
  	}
  	
  	tbody tr td {
  	 	text-align:center;
  		font-size:8pt;
  		border-spacing:0px;
  		padding:3px;
  		width:6%;
  	}

  </style>
 
    <script type="text/javascript">
    var SchShareApp = {
    		pageInit: function() {
                'use strict';
                this.data.init();
                this.event.init();
                
                SchShareApp.data.rssSelectData();
            },
            
            data: {
                init: function() {
                },
            
            	rssSelectData: function() {
					var url = '/rssWeaSelectData.do';
	  	            var paramObj = "keyword=" + $('#keyword').val();
	                      
                      SchShareObj.data.ajax(url, {pars: paramObj, async: false, 
                          onsucc: function(res) {
          					if(res.resultCd === "1000") {
          						var rss_content = res.resultData;
          						
          						var content = "";
          						for(var i=0; i<rss_content.length; i++) {
          							if( (rss_content[i].day == "0" && i==0) ||								// 오늘
          								(rss_content[i].day == "1" && rss_content[i].hour == "9" ) ||	// 내일
          								(rss_content[i].day == "2" && rss_content[i].hour == "9" ) 	) {	// 모레
          								
          								content += "<tr>";
              							content += "<td>" + rss_content[i].day.replace("0", "오늘").replace("1", "내일").replace("2", "모레") + "</td>";
          								
              							var temp = "";
	          							if(rss_content[i].tmx != "-999.0" && rss_content[i].tmn != "-999.0") {
	          								temp += rss_content[i].tmn + "<br>~<br>" + rss_content[i].tmx + "℃" ;
	          							} else 	if(rss_content[i].tmn != "-999.0") {
	          								temp += rss_content[i].tmn + "℃" ;
	          							} else if(rss_content[i].tmx != "-999.0") {
	          								temp += rss_content[i].tmx + "℃" ;
	          							} else {
	          								temp += "없음";
	          							}
	          							content += "<td>" + temp  + "</td>";
	
										if(rss_content[i].sky == 1) {
											content += "<td>" + "<img src='/resources/images/weather/NB01.png'/>" +"</td>";
										} else if(rss_content[i].sky == 2) {
											content += "<td>" + "<img src='/resources/images/weather/NB02.png'/>" +"</td>";
										} else if(rss_content[i].sky == 3) {
											content += "<td>" + "<img src='/resources/images/weather/NB03.png'/>" +"</td>";
										} else if(rss_content[i].sky == 4) {
											content += "<td>" + "<img src='/resources/images/weather/NB04.png'/>" +"</td>";
										} else {
											content += "<td>" + rss_content[i].wfKor + "</td>";	
										} 
	
	          							content += "<td>" + rss_content[i].pop + "(%)" + "</td>";
	          							content += "<td>" + rss_content[i].ws + "㎧" + "</td>";
	          							content += "<td>" + rss_content[i].reh + "(%)" + "</td>";
	          							content += "</tr>";
          							}
          						}
          						
          						$('#rss_list').html(content);
          					}
                          },
                          onerr: function(res){
                          	alert(res.resultMsg);
                          }
                      });
                },
            },
  
            event: {
                init: function() {
                }
            },
            
            popup: {

            }
            
    };

    </script>
 
</HEAD>

<body>

<div id="center">

	<div id="bodyGrid"> 
	    <table border="1" width="20%">
	    <thead>
	    	<tr bgcolor="gray">
				<td>날짜</td>
				<td>온도</td>
				<td>하늘</td>
				<td>강수</td>
				<td>풍속</td>
				<td>습도</td>
	    	</tr>
	    </thead>
	     <tbody  id="rss_list" >
	    	<tr>
	    		<td>...</td>
	    		<td>...</td>
	    		<td>...</td>
	    		<td>...</td>
	    		<td>...</td>
	    		<td>...</td>
	    	</tr>
	     </tbody>
	    </table>
	</div>

</div>

</body>

</html>