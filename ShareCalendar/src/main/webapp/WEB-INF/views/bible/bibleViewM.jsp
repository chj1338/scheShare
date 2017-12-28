<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %> 
<%@ include file="/WEB-INF/views/include/common.jsp"%>
 
<!DOCTYPE html>
<html>
<HEAD>
  <title>Bible</title>
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
            scrID : 'bibleViewM',
    
    		pageInit: function() {
                'use strict';
                this.data.init();
                this.event.init();
                
                SchShareApp.data.bibleSearchData();
            },
            
            data: {
                init: function() {
                },
            
            	bibleSearchData: function() {
					var url = '/bible/bibleSearchData.do';
	                var paramObj = {
	                	paramBook : $('#thisBook').val(),
	                	paramPage : $('#thisPage').val()
	                };
 
                	SchShareObj.data.ajax(url, {pars: paramObj, async: false, 
                    	onsucc: function(res) {
                    		if(res.resultCd === "1000") {
          						var duty_content = res.resultData;
          						var content = "";
          						var thisBook = duty_content[0].thisBook;
          						var thisPage = duty_content[0].thisPage;
          						
          						for(var i=0; i<duty_content.length; i++) {
          							content += "<tr>";
          							content += "<td valign='top' align='left'>" + duty_content[i].num + "</td>";
          							content += "<td valign='top' align='left'>" + duty_content[i].bibleContent + "</td>";
          							content += "</tr>";
          						}
          						
          						$('#thisBook').val(thisBook);
          						$('#thisPage').val(thisPage);
          						$('#duty_list').html(content);
          					}
                  	},
                          onerr: function(res){
                          	alert(res.resultMsg);
                          }
                      });
                },
                
                beforBook: function() {
                	var temp = $('#thisBook').val();
                	$('#thisBook').val( Number(temp) - 1);
                },
                
                beforPage: function() {
                	var tempBook = $('#thisBook').val();
                	var tempPage = $('#thisPage').val();
                	tempPage = Number(tempPage) - 1;
                	
                	if(tempPage < 1) {
                		tempPage = 12;
                		tempBook = Number(tempBook) - 1;
                	}
                	
                	$('#thisBook').val( tempBook);
                	$('#thisPage').val(tempPage);
                },
                
                nextPage: function() {
                	var tempBook = $('#thisBook').val();
                	var tempPage = $('#thisPage').val();
                	tempPage = Number(tempPage) + 1;
                	
                	if(tempPage > 12) {
                		tempPage = 1;
                		tempBook = Number(tempBook) + 1;
                	}
                	
                	$('#thisBook').val( tempBook);
                	$('#thisPage').val(tempPage);
                },
                
                nextBook: function() {
                	var temp = $('#thisBook').val();
                	$('#thisBook').val( Number(temp) + 1);
                }
                
            },
  
            event: {
                init: function() {
                    // 조회
                    $('#dutySelectBtn').on('click', function() {
                      SchShareApp.data.bibleSearchData();
                    });
                    
                    // 이전책
                    $('#beforBookBtn').on('click', function() {
                      SchShareApp.data.beforBook();
                      SchShareApp.data.bibleSearchData();
                    });
                    
                    // 이전장
                    $('#beforPageBtn').on('click', function() {
                      SchShareApp.data.beforPage();
                      SchShareApp.data.bibleSearchData();
                    });
                    
                    // 다음장
                    $('#nextPageBtn').on('click', function() {
                      SchShareApp.data.nextPage();
                      SchShareApp.data.bibleSearchData();
                    });
                    
                    // 다음책
                    $('#nextBookBtn').on('click', function() {
                      SchShareApp.data.nextBook();
                      SchShareApp.data.bibleSearchData();
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
	  <h3>Bible읽기</h3>
	</div>

	<div id="condition">
			<input type="button" id="beforBookBtn" value="◀◀"/>
			<input type="button" id="beforPageBtn" value="◀"/>
			<input type="text" id="thisBook" style="text-align:right;" size="4">
			<input type="text" id="thisPage" style="text-align:right;" size="2">장
			<input type="button" id="nextPageBtn" value="▶"/>
			<input type="button" id="nextBookBtn" value="▶▶"/>
			<input type="button" id="dutySelectBtn" value="조회"/>
	</div>

	<br>

	<div id="bodyGrid"> 
	    <table border="1" width="50%">
	    <thead>
	    	<tr bgcolor="gray">
	    		<td width="15%">순번</td>
	    		<td width="85%">내용</td>
	    	</tr>
	    </thead>
	     <tbody  id="duty_list" >
	    	<tr>
	    		<td>...</td>
	    	</tr>
	     </tbody>
	    </table>
	</div>

</div>

</body>

</html>