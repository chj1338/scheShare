<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %> 
<%@ include file="/WEB-INF/views/include/common.jsp"%>
 
<!DOCTYPE html>
<html>
<HEAD>
  <title>Bible</title>
  <style type="text/css">
/*   
  	table, tr, td {
  		border-style:solid;
  		border-collapse:collapse;
  		border-width:1px;
  	}
   */	
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
                
                //SchShareApp.data.bookCount();
                SchShareApp.data.bibleComboList();
                $("#thisBook option:eq(0)").attr("selected", "selected");
                
                SchShareApp.data.bibleSearchData();
            },
            
            data: {
                init: function() {
                },
                
                bookCount: function() {
					var url = '/bible/bookCount.do';
	                var paramObj = {
	                	paramBook : $('#thisBook').val()
	                };
 
	                var nowOption = $('#thisPage option').size();
	                for(var i=0; i<nowOption; i++) {
	                	$('#thisPage option:eq(0)').remove();
	                }
	                
	                // 선택한 성경의 최대 장수 찾아서 select에 반영
                	SchShareObj.data.ajax(url, {pars: paramObj, async: false, 
                    	onsucc: function(res) {
                    		if(res.resultCd === "1000") {
          						var maxPage = Number(res.resultData);

								for(var i=1; i<=maxPage; i++ ) {
									$('#thisPage').append("<option value='" + i + "'>" + i + "</option>");
								}
								$("#thisPage").prepend("<option value=''>== 선택 ==</option>");
          					}
                  		  },
                          onerr: function(res){
                          	alert(res.resultMsg);
                          }
                      });
                },
                
             	// 성경 Combo 리스트 가져오기
            	bibleComboList: function() {
					var url = '/bible/bookComboList.do';
					
	                var paramObj = {
	                		paramBook : $('#thisBook').val()
	                };
 
                	SchShareObj.data.ajax(url, {pars: paramObj, async: false, 
                    	onsucc: function(res) {
                    		if(res.resultCd === "1000") {
          						var bookComboList = res.resultData;
          						var listSize = bookComboList.length;
          						
								for(var i=0; i<listSize; i++ ) {
									$('#thisBook').append("<option value='" + bookComboList[i].bookNmKorAbr + "'>" + bookComboList[i].bookNmKor + "</option>");
								}
								$("#thisBook").prepend("<option value=''>== 선택 ==</option>");
								
								var maxPage = bookComboList[0].lastPage;
								for(var i=1; i<=maxPage; i++ ) {
									$('#thisPage').append("<option value='" + i + "'>" + i + "</option>");
								}
								$("#thisPage").prepend("<option value=''>== 선택 ==</option>");
                    		}          						
                  		  },
                          onerr: function(res){
                          	alert(res.resultMsg);
                          }
                      });
                },
                
				// 선택 조건으로 성경 찾기
            	bibleSearchData: function() {
					var url = '/bible/bibleSearchData.do';
					if($('#searchWord').val() != "") {
						url = '/bible/bibleSearchWord.do';
					}
					
	                var paramObj = {
	                	paramBook : $('#thisBook').val(),
	                	paramPage : $('#thisPage').val(),
	                	searchWord : $('#searchWord').val()
	                };
 
                	SchShareObj.data.ajax(url, {pars: paramObj, async: false, 
                    	onsucc: function(res) {
                    		if(res.resultCd === "1000") {
          						var duty_content = res.resultData;
          						var content = "";
          						var thisBook = res.thisBook;
          						var thisPage = res.thisPage;
          						
          						for(var i=0; i<duty_content.length; i++) {
          							content += "<tr>";
          							content += "<td valign='top' align='right'>" + duty_content[i].bookIndex + "</td>";
          							content += "<td valign='top' align='left'>" + duty_content[i].bibleContent + "</td>";
          							content += "</tr>";
          						}
          						
          						//alert(thisBook + ":" + thisPage);
          						if($('#searchWord').val() != "") {
	          						$('#thisBook').val("");
	          						$('#thisPage').val("");
          						} else {
	          						$('#thisBook').val(thisBook);
	          						$('#thisPage').val(thisPage);
          						}
          						
          						$('#duty_list').html(content);
          					}
                  		  },
                          onerr: function(res){
                          	alert(res.resultMsg);
                          }
                      });
                },
                
                
				// 성경읽기 저장
            	bibleSaveData: function() {
					var url = '/bible/bibleSaveData.do';

	                var paramObj = {
	                	paramBook : $('#thisBook').val(),
	                	paramPage : $('#thisPage').val(),
	                };
 
                	SchShareObj.data.ajax(url, {pars: paramObj, async: false, 
                    	onsucc: function(res) {
                    		if(res.resultCd === "1000") {
								alert("저장을 완료했습니다.");
                    		}
                  		  },
                          onerr: function(res){
                          	alert("저장에 실패했습니다.\n" + res.resultMsg);
                          }
                      });
                },
                
                // 이전 책
                beforBook: function() {
                	var index = $("#thisBook option").index($("#thisBook option:selected"));
                	var nextIndex = Number(index) - 1;
                	if(nextIndex >= 0) {	// 첫번째 책일 경우 동작 안함
                    	$("#thisBook option:eq(" + nextIndex + ")").attr("selected", "selected");
                    	$('#thisPage').val("1");
                    	
                        SchShareApp.data.bookCount();
                        SchShareApp.data.bibleSearchData();
                	}
                },
                
                // 이전 장
                beforPage: function() {
                	var tempPage = $('#thisPage').val();
                	tempPage = Number(tempPage) - 1;
                	$('#thisPage').val(tempPage);
                	
                    SchShareApp.data.bibleSearchData();
                },
                
                // 다음 장 
                nextPage: function() {
                	var tempPage = $('#thisPage').val();
                	tempPage = Number(tempPage) + 1;
                	if(tempPage <= $("#thisPage option").size()) {	// 최대 페이지를 넘어갈 경우 동작 안함
	                	$('#thisPage').val(tempPage);
	                	
	                    SchShareApp.data.bibleSearchData();
                	}
                },
                
                
                // 다음 책
                nextBook: function() {
                	var index = $("#thisBook option").index($("#thisBook option:selected"));
                	var nextIndex = Number(index) + 1;
                	if(nextIndex < $("#thisBook option").size()) {	// 마지막 책을 넘어갈 경우 동작 안함
                    	$("#thisBook option:eq(" + nextIndex + ")").attr("selected", "selected");
                    	$('#thisPage').val("1");
                    	
                        SchShareApp.data.bookCount();
                        SchShareApp.data.bibleSearchData();
                	}
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
                    });
                    
                    // 이전장
                    $('#beforPageBtn').on('click', function() {
                      SchShareApp.data.beforPage();
                    });
                    
                    // 다음장
                    $('#nextPageBtn').on('click', function() {
                      SchShareApp.data.nextPage();
                    });
                    
                    // 다음책
                    $('#nextBookBtn').on('click', function() {
                      SchShareApp.data.nextBook();
                    });
                    
                    // 책 변경
                    $('#thisBook').on('change', function() {
                    	$('#thisPage').val("1");
                    	SchShareApp.data.bookCount();
                    	SchShareApp.data.bibleSearchData();
                    });
                    
                    // 장 변경
                    $('#thisPage').on('change', function() {
                    	SchShareApp.data.bibleSearchData();
                    });
                    
                    // 읽기 저장
                    $('#bibleSaveBtn').on('click', function() {
                    	if( $('#thisBook').val() == "" || $('#thisPage').val() == "") {
                    		alert("검색한 내용들은 저장 할 수 없습니다.");
                    	} else {
                      		SchShareApp.data.bibleSaveData();
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
	  <h3>Bible읽기</h3>
	</div>

	<div id="condition">
			<input type="button" id="beforBookBtn" value="◀◀"/>
			<input type="button" id="beforPageBtn" value="◀"/>
			<select id="thisBook"></select>
			<select id="thisPage"></select>
			<input type="button" id="nextPageBtn" value="▶"/>
			<input type="button" id="nextBookBtn" value="▶▶"/>
			&nbsp;&nbsp;검색어&nbsp;<input type="text" id="searchWord" col="15"/>
			<input type="button" id="dutySelectBtn" value="조회"/>
	</div>

	<br>

	<div id="bodyGrid"> 
	    <table width="50%">
	    <thead>
	    	<tr bgcolor="gray">
	    		<td width="10%">구분</td>
	    		<td width="90%">내용</td>
	    	</tr>
	    </thead>
	     <tbody  id="duty_list" >
	    	<tr>
	    		<td>...</td>
	    	</tr>
	     </tbody>
	    </table>
	</div>
	
	<div id="saveData"> 
	<table width="50%">
		<tr><td align="center"><input type="button" id="bibleSaveBtn" value="읽기완료"/></td></tr>
	</div>
</div>

</body>

</html>