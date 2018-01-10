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
            scrID : 'bibleAdminM',
    
    		pageInit: function() {
                'use strict';
                this.data.init();
                this.event.init();

                SchShareApp.data.bibleComboList();
                $("#thisBook option:eq(0)").attr("selected", "selected");
            },
            
            data: {
                init: function() {
                },

             	// 성경 Combo 리스트 가져오기 (DB)
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

          						// 책 옵션 셋팅
          						if($("#thisBook option").size() == 0) { 						
									for(var i=0; i<listSize; i++ ) {
										$('#thisBook').append("<option value='" + bookComboList[i].bookNmKorAbr + "'>" + bookComboList[i].bookNmKor + "</option>");
									}
									$("#thisBook").prepend("<option value=''>== 선택 ==</option>");
          						}
								
								// 기존 page 옵션 삭제
				                var nowOption = $('#thisPage option').size();
				                for(var i=0; i<nowOption; i++) {
				                	$('#startPage option:eq(0)').remove();
				                	$('#thisPage option:eq(0)').remove();
				                }
								
				                // 현재 책 index 
								var pageIndex = $("#thisBook option").index($("#thisBook option:selected")) - 1;
				                if(pageIndex <= 0) return;
				                
				             	// 신규 page 옵션 셋팅
				                var maxPage = bookComboList[pageIndex].lastPage;
								for(var i=1; i<=maxPage; i++ ) {
									$('#startPage').append("<option value='" + i + "'>" + i + "</option>");
									$('#thisPage').append("<option value='" + i + "'>" + i + "</option>");
								}
								$("#startPage").prepend("<option value=''>== 선택 ==</option>");
								$("#thisPage").prepend("<option value=''>== 선택 ==</option>");
								
								if($('#thisBook').val() != "") {
									$("#startPage").val(1);
									$("#thisPage").val(maxPage);
                    			}
                    		}          						
                  		  },
                          onerr: function(res){
                          	alert(res.resultMsg);
                          }
                      });
                },
                
                
                
				// 성경읽기 저장 (1장~선택한 장까지 모두 저장)
            	bibleSaveData: function() {
					var url = '/bible/bibleAllSaveData.do';

	                var paramObj = {
	                	paramBook : $('#thisBook').val(),
	                	paramStartPage : $('#startPage').val(),
	                	paramPage : $('#thisPage').val()
	                };
	                
	                if($('#thisBook option selected').val() == "" || $('#startPage option selected').val() == "" || $('#thisPage option selected').val() == "") {
	                	alert("저장할 범위를 지정해주세요.");
	                	('#thisBook').focus();
	                	return;
	                }
 
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
                               
                // 이전 장
                beforPage: function() {
                	var tempPage = $('#startPage').val();
                	tempPage = Number(tempPage) - 1;
                	$('#startPage').val(tempPage);
                },
                
                // 다음 장 
                nextPage: function() {
                	var tempPage = $('#startPage').val();
                	tempPage = Number(tempPage) + 1;
                	if(tempPage <= $("#startPage option").size()) {	// 최대 페이지를 넘어갈 경우 동작 안함
	                	$('#startPage').val(tempPage);
                	}
                },
                
                // 이전 책
                beforBook: function() {
                	var index = $("#thisBook option").index($("#thisBook option:selected"));
                	var nextIndex = Number(index);
                	if(nextIndex >= 0) {	// 첫번째 책일 경우 동작 안함
                		SchShareApp.data.bibleComboList();
                    	$("#thisBook option:eq(" + nextIndex + ")").attr("selected", "selected");
                	}
                },
                
                // 다음 책123
                nextBook: function() {
                	var index = $("#thisBook option").index($("#thisBook option:selected"));
                	var nextIndex = Number(index) + 1;
                	if(nextIndex <= $("#thisBook option").size()) {	// 마지막 책을 넘어갈 경우 동작 안함
                		SchShareApp.data.bookCount();
                		alert(nextIndex);
                    	$("#thisBook option:eq(" + nextIndex + ")").attr("selected", "selected");
                	}
                }
            },
  
            event: {
                init: function() {
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
                    	SchShareApp.data.bibleComboList();
                    	$('#startPage').val("1");
                    	$('#thisPage option:eq('+ ($('#thisPage option').size() - 1) +')').attr("selected", "selected");
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
	  <h3>Bible Admin</h3>
	</div>

	<div id="condition">
			<input type="button" id="beforBookBtn" value="◀◀"/>
			<input type="button" id="beforPageBtn" value="◀"/>
			<select id="thisBook"></select>&nbsp;&nbsp;
			<select id="startPage"></select>~<select id="thisPage"></select>
			<input type="button" id="nextPageBtn" value="▶"/>
			<input type="button" id="nextBookBtn" value="▶▶"/>
			<input type="button" id="bibleSaveBtn" value="전체저장"/>
	</div>

	<br>

</div>

</body>

</html>