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

                $("#thisBook option:eq(0)").attr("selected", "selected");
                SchShareApp.data.bookCount();
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
			<select id="thisBook">
				<option value='창'>창세기</option>
				<option value='출'>출애굽기</option>
				<option value='레'>레위기</option>
				<option value='민'>민수기</option>
				<option value='신'>신명기</option>
				<option value='수'>여호수아</option>
				<option value='삿'>사사기</option>
				<option value='룻'>룻기</option>
				<option value='삼상'>사무엘상</option>
				<option value='삼하'>사무엘하</option>
				<option value='왕상'>열왕기상</option>
				<option value='왕하'>열왕기하</option>
				<option value='대상'>역대상</option>
				<option value='대하'>역대하</option>
				<option value='스'>에스라</option>
				<option value='느'>느헤미야</option>
				<option value='에'>에스더</option>
				<option value='욥'>욥기</option>
				<option value='시'>시편</option>
				<option value='잠'>잠언</option>
				<option value='전'>전도서</option>
				<option value='아'>아가</option>
				<option value='사'>이사야</option>
				<option value='렘'>예레미야</option>
				<option value='애'>예레미야 애가</option>
				<option value='겔'>에스겔</option>
				<option value='단'>다니엘</option>
				<option value='호'>호세아</option>
				<option value='욜'>요엘</option>
				<option value='암'>아모스</option>
				<option value='옵'>오바댜</option>
				<option value='욘'>요나</option>
				<option value='미'>미가</option>
				<option value='나'>나훔</option>
				<option value='합'>하박국</option>
				<option value='습'>스바냐</option>
				<option value='학'>학개</option>
				<option value='슥'>스가랴</option>
				<option value='말'>말라기</option>
				<option value='마'>마태복음</option>
				<option value='막'>마가복음</option>
				<option value='눅'>누가복음</option>
				<option value='요'>요한복음</option>
				<option value='행'>사도행전</option>
				<option value='롬'>로마서</option>
				<option value='고전'>고린도전서</option>
				<option value='고후'>고린도후서</option>
				<option value='갈'>갈라디아서</option>
				<option value='엡'>에베소서</option>
				<option value='빌'>빌립보서</option>
				<option value='골'>골로새서</option>
				<option value='살전'>데살로니가전서</option>
				<option value='살후'>데살로니가후서</option>
				<option value='딤전'>디모데전서</option>
				<option value='딤후'>디모데후서</option>
				<option value='딛'>디도서</option>
				<option value='몬'>빌레몬서</option>
				<option value='히'>히브리서</option>
				<option value='약'>야고보서</option>
				<option value='벧전'>베드로전서</option>
				<option value='벧후'>베드로후서</option>
				<option value='요일'>요한1서</option>
				<option value='요이'>요한2서</option>
				<option value='요삼'>요한3서</option>
				<option value='유'>유다서</option>
				<option value='계'>요한계시록</option>
			</select>
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

</div>

</body>

</html>