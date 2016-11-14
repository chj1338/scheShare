<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %> 
<%@ include file="/WEB-INF/views/include/common.jsp"%>
 
<!DOCTYPE html>
<html>
<HEAD>
  <title>RSS뉴스</title>
  <style type="text/css">
  	table, tr, td {
  		border-style:solid;
  		border-collapse:collapse;
  		border-width:1px;
  	}
  	
  	thead tr td {
  	  	font-size:10pt;
  	 	font-weight:bold;
  	 	text-align:center;
  	}
  	
  	td{
  		font-size:9pt;
  		border-spacing:0px;
  		padding:3px;
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
					var url = '/rssSelectData.do';
	  	            var paramObj = "keyword=" + $('#keyword').val();
	                      
                      SchShareObj.data.ajax(url, {pars: paramObj, async: false, 
                          onsucc: function(res) {
          					if(res.resultCd === "1000") {
          						var rss_content = res.resultData;
          						
          						var content = "";
          						for(var i=0; i<rss_content.length; i++) {
          							content += "<tr>";
          							content += "<td>" + rss_content[i].guid + "</td>";
          							content += "<td><a href='" + rss_content[i].link + "' target='_blank'>" + rss_content[i].title + "</a></td>";
//									content += "<td><a href='javascript:window.open('" + rss_content[i].link + "','"+rss_content[i].title+"','width=800,height=600')'>" + rss_content[i].title + "</a></td>";
//          						content += "<td><a href='#' onClick='window.open('" + rss_content[i].link + "','"+rss_content[i].title+"','width=800,height=600'); return false;>" + rss_content[i].title + "</a></td>";
          							content += "<td>" + rss_content[i].discription + "</td>";
          							content += "<td>" + rss_content[i].author + "</td>";
          							content += "<td>" + rss_content[i].pubDate + "</td>";
          							content += "</tr>";
          						}
          						
//          						$('#keyword').val(rss_content[2].title);
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
	  <h3>RSS 뉴스</h3>
	</div>

	<div id="condition">
			검색어 : <input type="text" id="keyword""/>
			<input type="button" id="rssSelectBtn" value="RSS조회"/>
			<input type="button" id="rssSelectCtnBtn" value="RSS연속조회"/>
	</div>

	<br>

	<div id="bodyGrid"> 
	    <table border="1" width="80%">
	    <thead>
	    	<tr bgcolor="gray">
	    		<td width="10%">구분</td>
	    		<td width="30%">제목</td>
	    		<td width="50%">내용</td>
	    		<td width="5%">작성자</td>
	    		<td width="5%">작성일</td>
	    	</tr>
	    </thead>
	     <tbody  id="rss_list" >
	    	<tr>
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