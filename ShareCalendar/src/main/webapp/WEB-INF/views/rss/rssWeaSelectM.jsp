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
  	  	font-size:9pt;
  	 	font-weight:bold;
  	 	text-align:center;
  	}
  	
  	tbody tr td {
  	 	text-align:center;
  		font-size:9pt;
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
          							content += "<tr>";
          							content += "<td>" + rss_content[i].day.replace("0", "오늘").replace("1", "내일").replace("2", "모레") + "</td>";
          							content += "<td>" + rss_content[i].hour + ":00" + "</td>";
          							content += "<td>" + rss_content[i].temp + "℃" + "</td>";
          							
          							if(rss_content[i].tmx != "-999.0") {
	          							content += "<td>" + rss_content[i].tmx + "℃" + "</td>";
          							} else {
          								content += "<td>" + "없음" + "</td>";
          							}

          							if(rss_content[i].tmn != "-999.0") {
	          							content += "<td>" + rss_content[i].tmn + "℃" + "</td>";
          							} else {
          								content += "<td>" + "없음" + "</td>";
          							}

									if(rss_content[i].sky == 1) {
										content += "<td>" + "<img src='/resources/images/weather/NB01.png'/>" + "<br>"+ rss_content[i].wfKor +"</td>";
									} else if(rss_content[i].sky == 2) {
										content += "<td>" + "<img src='/resources/images/weather/NB02.png'/>" + "<br>"+ rss_content[i].wfKor +"</td>";
									} else if(rss_content[i].sky == 3) {
										content += "<td>" + "<img src='/resources/images/weather/NB03.png'/>" + "<br>"+ rss_content[i].wfKor +"</td>";
									} else if(rss_content[i].sky == 4) {
										content += "<td>" + "<img src='/resources/images/weather/NB04.png'/>" + "<br>"+ rss_content[i].wfKor +"</td>";
									} else {
										content += "<td>" + rss_content[i].sky + " : " + rss_content[i].wfKor + "</td>";	
									} 
									
									var ptyKor = rss_content[i].pty.replace("0", "없음").replace("1", "비").replace("2", "비 또는 눈").replace("3", "눈");
									if(rss_content[i].pty == 1) {
										content += "<td>" + "<img src='/resources/images/weather/NB08.png'/>" + "<br>"+ ptyKor +"</td>";		// 비
									} else if(rss_content[i].pty == 2) {
										content += "<td>" + "<img src='/resources/images/weather/NB12.png'/>" + "<br>"+ ptyKor +"</td>";		// 비 또는 눈
									} else if(rss_content[i].pty == 3) {
										content += "<td>" + "<img src='/resources/images/weather/NB11.png'/>" + "<br>"+ ptyKor +"</td>";		// 눈
									} else {
										content += "<td>" + ptyKor + "</td>";
									}

          							content += "<td>" + rss_content[i].pop + "(%)" + "</td>";
          							content += "<td>" + rss_content[i].ws + "㎧" + "</td>";
          							content += "<td>" + rss_content[i].wdKor + "</td>";
          							content += "<td>" + rss_content[i].reh + "(%)" + "</td>";
          							content += "<td>" + rss_content[i].r12 + "</td>";
          							content += "<td>" + rss_content[i].s12 + "</td>";
          							content += "<td>" + rss_content[i].r06 + "</td>";
          							content += "<td>" + rss_content[i].s06 + "</td>";
          							content += "<td>" + rss_content[i].pubDate + "</td>";
          							content += "</tr>";
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
                    // rss 조회
                    $('#rssSelectBtn').on('click', function() {
                      SchShareApp.data.rssSelectData();
                    });
                    
                    // rss 연속조회
                    $('#rssSelectCtnBtn').on('click', function() {
                      SchShareApp.data.rssSelectData();
                  	 	setInterval( function() { 	SchShareApp.data.rssSelectData() }, 10000);
                    });
                    
                    // 검색
                    $('#keyword').on('keydown', function(e) {
                        if (e.keyCode === 13)   SchShareApp.data.rssSelectData();
                    });
                }
            },
            
            popup: {

            }
            
    };

    </script>
 
</HEAD>

<body>

<div id="center">

	<div id="headLine">
	  <h3  bgcolor="#E8F5FF">RSS 날씨</h3>
	</div>

	<div id="condition">
			<input type="button" id="rssSelectBtn" value="날씨조회"/>
			<input type="button" id="rssSelectCtnBtn" value="날씨연속조회"/>
	</div>

	<br>

	<div id="bodyGrid"> 
	    <table border="1" width="80%">
	    <thead>
	    	<tr bgcolor="gray">
				<td>날짜</td>
				<td>시간</td>
				<td>온도</td>
				<td>최고 기온</td>
				<td>최저 기온</td>
				<td>하늘 상태</td>
				<td>강수형태</td>
				<td>강수확률</td>
				<td>풍속</td>
				<td>풍향</td>
				<td>습도</td>
				<td>12시간 강수량</td>
				<td>12시간 적설</td>
				<td>6시간 강수량</td>
				<td>6시간 적설</td>
				<td>예보시간</td>
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
	    		<td>...</td>
	    		<td>...</td>
	    		<td>...</td>
	    		<td>...</td>
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