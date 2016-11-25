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
	  	            var paramObj = {
	  	            		keyword : $('#keyword').val(),
	  	            		ciokorea : $('#ciokorea').is(':checked'),
	  	            		jtbc_newsflash : $('#jtbc_newsflash').is(':checked'),
	  	            		jtbc_politics : $('#jtbc_politics').is(':checked'),
	  	            		jtbc_economy : $('#jtbc_economy').is(':checked'),
	  	            		jtbc_international : $('#jtbc_international').is(':checked'),
	  	            		jtbc_society : $('#jtbc_society').is(':checked'),
	  	            		jtbc_culture : $('#jtbc_culture').is(':checked'),
	  	            		jtbc_sports : $('#jtbc_sports').is(':checked'),
	  	            		jtbc_entertainment : $('#jtbc_entertainment').is(':checked'),
	  	            		jtbc_newsrank : $('#jtbc_newsrank').is(':checked'),
	  	            		jtbc_newssite : $('#jtbc_newssite').is(':checked'),
	  	            		jtbc_politicaldesk : $('#jtbc_politicaldesk').is(':checked'),
	  	            		jtbc_morningand : $('#jtbc_morningand').is(':checked'),
	  	            		jtbc_fullvideo : $('#jtbc_fullvideo').is(':checked'),
	  	            		joins_news_list : $('#joins_news_list').is(':checked'),
	  	            		nocutnews : $('#nocutnews').is(':checked'),
	  	            		donga : $('#donga').is(':checked'),
	  	            		chosun : $('#chosun').is(':checked'),
	  	            		hani : $('#hani').is(':checked'),
	  	            		khan_rss : $('#khan_rss').is(':checked'),
	  	            		joins_ilgan_list : $('#joins_ilgan_list').is(':checked')
	  	            };
	                      
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
                        	 $('#jtbc_newsflash').prop('checked', true);
                        	 $('#jtbc_politics').prop('checked', true);
                        	 $('#jtbc_economy').prop('checked', true);
                        	 $('#jtbc_international').prop('checked', true);
                        	 $('#jtbc_society').prop('checked', true);
                        	 $('#jtbc_culture').prop('checked', true);
                        	 $('#jtbc_sports').prop('checked', true);
                        	 $('#jtbc_entertainment').prop('checked', true);
                        	 $('#jtbc_newsrank').prop('checked', true);
                        	 $('#jtbc_newssite').prop('checked', true);
                        	 $('#jtbc_politicaldesk').prop('checked', true);
                        	 $('#jtbc_morningand').prop('checked', true);
                        	 $('#jtbc_fullvideo').prop('checked', true);
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
                        	 $('#jtbc_newsflash').prop('checked', false);
                        	 $('#jtbc_politics').prop('checked', false);
                        	 $('#jtbc_economy').prop('checked', false);
                        	 $('#jtbc_international').prop('checked', false);
                        	 $('#jtbc_society').prop('checked', false);
                        	 $('#jtbc_culture').prop('checked', false);
                        	 $('#jtbc_sports').prop('checked', false);
                        	 $('#jtbc_entertainment').prop('checked', false);
                        	 $('#jtbc_newsrank').prop('checked', false);
                        	 $('#jtbc_newssite').prop('checked', false);
                        	 $('#jtbc_politicaldesk').prop('checked', false);
                        	 $('#jtbc_morningand').prop('checked', false);
                        	 $('#jtbc_fullvideo').prop('checked', false);
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
					<td><input type='checkbox' class='minimal' id='ciokorea' name='ciokorea' > CIO Korea</td>
					<td><input type='checkbox' class='minimal' id='joins_news_list' name='joins_news_list' > 중앙일보</td>
					<td><input type='checkbox' class='minimal' id='nocutnews' name='nocutnews' > 노컷뉴스</td>
					<td><input type='checkbox' class='minimal' id='donga' name='donga' > 동아일보</td>
					<td><input type='checkbox' class='minimal' id='chosun' name='chosun' > 조선일보</td>
					<td><input type='checkbox' class='minimal' id='hani' name='hani' > 한겨레</td>
					<td><input type='checkbox' class='minimal' id='khan_rss' name='khan_rss' > 경향신문</td> 
				</tr>
				<tr>
					<td><input type='checkbox' class='minimal' id='joins_ilgan_list' name='joins_ilgan_list' > 일간스포츠</td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td><input type='checkbox' class='minimal' id='jtbc_newsflash' name='jtbc_newsflash' > JTBC 속보</td>
					<td><input type='checkbox' class='minimal' id='jtbc_politics' name='jtbc_politics' > JTBC 정치</td>
					<td><input type='checkbox' class='minimal' id='jtbc_economy' name='jtbc_economy' > JTBC 경제</td>
					<td><input type='checkbox' class='minimal' id='jtbc_international' name='jtbc_international' > JTBC 국제</td>
					<td><input type='checkbox' class='minimal' id='jtbc_society' name='jtbc_society' > JTBC 사회</td>
					<td><input type='checkbox' class='minimal' id='jtbc_culture' name='jtbc_culture' > JTBC 문화</td>
					<td><input type='checkbox' class='minimal' id='jtbc_sports' name='jtbc_sports' > JTBC 스포츠</td>
				</tr>
				<tr>
					<td><input type='checkbox' class='minimal' id='jtbc_entertainment' name='jtbc_entertainment' > JTBC 연예</td>
					<td><input type='checkbox' class='minimal' id='jtbc_newsrank' name='jtbc_newsrank' > JTBC 뉴스랭킹</td>
					<td><input type='checkbox' class='minimal' id='jtbc_newssite' name='jtbc_newssite' > JTBC 뉴스현장</td>
					<td><input type='checkbox' class='minimal' id='jtbc_politicaldesk' name='jtbc_politicaldesk' > JTBC 정치부회의</td>
					<td><input type='checkbox' class='minimal' id='jtbc_morningand' name='jtbc_morningand' > JTBC 아침&</td>
					<td><input type='checkbox' class='minimal' id='jtbc_fullvideo' name='jtbc_fullvideo' > JTBC 풀영상</td>
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