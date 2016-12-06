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
  	
	  bodyGrid {
	    	overflow: hidden;
	    }
    </style>
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
					var url = '/rssSelectData.do';
	  	            var paramObj = {
	  	            		keyword : "",
	  	            		ciokorea : false,
	  	            		jtbc_newsflash : true,
	  	            		jtbc_politics : false,
	  	            		jtbc_economy : false,
	  	            		jtbc_international : false,
	  	            		jtbc_society : false,
	  	            		jtbc_culture : false,
	  	            		jtbc_sports : false,
	  	            		jtbc_entertainment : false,
	  	            		jtbc_newsrank : false,
	  	            		jtbc_newssite : false,
	  	            		jtbc_politicaldesk : false,
	  	            		jtbc_morningand : false,
	  	            		jtbc_fullvideo : false,
	  	            		joins_news_list : false,
	  	            		nocutnews : false,
	  	            		donga : false,
	  	            		chosun : false,
	  	            		hani : false,
	  	            		khan_rss : false,
	  	            		joins_ilgan_list : false
	  	            };
	                      
                      SchShareObj.data.ajax(url, {pars: paramObj, async: false, 
                          onsucc: function(res) {
          					if(res.resultCd === "1000") {
          						var rss_content = res.resultData;
          						var maxLine = 11;	// 화면 표시라인 수
          						
          						var content = "";
          						for(var i=0; i<maxLine; i++) {
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

	<div id="bodyGrid" align="right">
		<a href="/rss/rssSelectM.do" target="appbody" class="no-uline">[더보기]</a>
	    <table border="1" width="100%" bgcolor='white'>
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