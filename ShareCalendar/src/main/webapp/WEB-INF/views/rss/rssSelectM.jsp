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
                    
                    // 체크박스 전체
                    $('#check_all').click(function() {
                         if( $(this).is(":checked") ) {
                        	 $('#ciokorea').prop('checked', true);
                        	 $('#joins_newsflash').prop('checked', true);
                        	 $('#joins_politics').prop('checked', true);
                        	 $('#joins_economy').prop('checked', true);
                        	 $('#joins_international').prop('checked', true);
                        	 $('#joins_society').prop('checked', true);
                        	 $('#joins_culture').prop('checked', true);
                        	 $('#joins_sports').prop('checked', true);
                        	 $('#joins_entertainment').prop('checked', true);
                        	 $('#joins_newsrank').prop('checked', true);
                        	 $('#joins_newssite').prop('checked', true);
                        	 $('#joins_politicaldesk').prop('checked', true);
                        	 $('#joins_morningand').prop('checked', true);
                        	 $('#joins_fullvideo').prop('checked', true);
                        	 $('#joins_news_list').prop('checked', true);
                        	 $('#nocutnews').prop('checked', true);
                        	 $('#donga').prop('checked', true);
                        	 $('#chosun').prop('checked', true);
                        	 $('#hani').prop('checked', true);
                        	 $('#khan_rss').prop('checked', true);
                        	 $('#joins_ilgan_list').prop('checked', true);
                         }
                         else {
                        	 $('#ciokorea').prop('checked', false);
                        	 $('#joins_newsflash').prop('checked', false);
                        	 $('#joins_politics').prop('checked', false);
                        	 $('#joins_economy').prop('checked', false);
                        	 $('#joins_international').prop('checked', false);
                        	 $('#joins_society').prop('checked', false);
                        	 $('#joins_culture').prop('checked', false);
                        	 $('#joins_sports').prop('checked', false);
                        	 $('#joins_entertainment').prop('checked', false);
                        	 $('#joins_newsrank').prop('checked', false);
                        	 $('#joins_newssite').prop('checked', false);
                        	 $('#joins_politicaldesk').prop('checked', false);
                        	 $('#joins_morningand').prop('checked', false);
                        	 $('#joins_fullvideo').prop('checked', false);
                        	 $('#joins_news_list').prop('checked', false);
                        	 $('#nocutnews').prop('checked', false);
                        	 $('#donga').prop('checked', false);
                        	 $('#chosun').prop('checked', false);
                        	 $('#hani').prop('checked', false);
                        	 $('#khan_rss').prop('checked', false);
                        	 $('#joins_ilgan_list').prop('checked', false);
                         }
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
			<table boder='2'>
				<tr>
					<td colspan="7"><input type="checkbox" class="minimal" id="check_all" name="check_all" > 모두 체크</td>
				</tr>
				<tr>
					<td><input type='checkbox' class='minimal' id='ciokorea' name='agree2' > CIO Korea</td>
					<td><input type='checkbox' class='minimal' id='joins_news_list' name='agree2' > 중앙일보</td>
					<td><input type='checkbox' class='minimal' id='nocutnews' name='agree2' > 노컷뉴스</td>
					<td><input type='checkbox' class='minimal' id='donga' name='agree2' > 동아일보</td>
					<td><input type='checkbox' class='minimal' id='chosun' name='agree2' > 조선일보</td>
					<td><input type='checkbox' class='minimal' id='hani' name='agree2' > 한겨레</td>
					<td><input type='checkbox' class='minimal' id='khan_rss' name='agree2' > 경향신문</td> 
				</tr>
				<tr>
					<td><input type='checkbox' class='minimal' id='joins_ilgan_list' name='agree2' > 일간스포츠</td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td><input type='checkbox' class='minimal' id='joins_newsflash' name='agree2' > JTBC 속보</td>
					<td><input type='checkbox' class='minimal' id='joins_politics' name='agree2' > JTBC 정치</td>
					<td><input type='checkbox' class='minimal' id='joins_economy' name='agree2' > JTBC 경제</td>
					<td><input type='checkbox' class='minimal' id='joins_international' name='agree2' > JTBC 국제</td>
					<td><input type='checkbox' class='minimal' id='joins_society' name='agree2' > JTBC 사회</td>
					<td><input type='checkbox' class='minimal' id='joins_culture' name='agree2' > JTBC 문화</td>
					<td><input type='checkbox' class='minimal' id='joins_sports' name='agree2' > JTBC 스포츠</td>
				</tr>
				<tr>
					<td><input type='checkbox' class='minimal' id='joins_entertainment' name='agree2' > JTBC 연예</td>
					<td><input type='checkbox' class='minimal' id='joins_newsrank' name='agree2' > JTBC 뉴스랭킹</td>
					<td><input type='checkbox' class='minimal' id='joins_newssite' name='agree2' > JTBC 뉴스현장</td>
					<td><input type='checkbox' class='minimal' id='joins_politicaldesk' name='agree2' > JTBC 정치부회의</td>
					<td><input type='checkbox' class='minimal' id='joins_morningand' name='agree2' > JTBC 아침&</td>
					<td><input type='checkbox' class='minimal' id='joins_fullvideo' name='agree2' > JTBC 풀영상</td>
					<td></td>
				</tr>
			</table>
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