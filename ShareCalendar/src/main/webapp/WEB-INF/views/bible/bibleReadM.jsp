<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %> 
<%@ include file="/WEB-INF/views/include/common.jsp"%>
 
<!DOCTYPE html>
<html>
<HEAD>
  <title>Bible읽기표</title>
  <style type="text/css">
 
  	table, tr, td {
  		border-style:solid;
  		border-collapse:collapse;
  		border-width:1px;
  	}
 
  	thead {
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
            scrID : 'bibleReadM',
    
    		pageInit: function() {
                'use strict';
                this.data.init();
                this.event.init();

                SchShareApp.data.bibleSeqCombo();
                SchShareApp.data.bibleRead();
            },
            
            data: {
                init: function() {
                },

             	// 성경 읽기 차수 select 셋팅
            	bibleSeqCombo: function() {
					var url = '/bible/bibleRead.do';
					
	                var paramObj = {
	                		paramSeq : $('#thisReadSeq').val()
	                };
 
                	SchShareObj.data.ajax(url, {pars: paramObj, async: false, 
                    	onsucc: function(res) {
                    		if(res.resultCd === "1000") {
          						var bibleReadList = res.resultReadData;
          						var maxSeq = 1;
          						if(bibleReadList.length != 0) maxSeq = bibleReadList[0].maxSeq;
	         					
          						// 읽기 차수 셋팅
          						for(var i=1; i<=maxSeq; i++ ) {
									$('#thisReadSeq').append("<option value='" + i + "'>" + i + "</option>");
								}
                    		}
                  		  },
                          onerr: function(res){
                          	alert(res.resultMsg);
                          }
                      });
                },                
                
             	// 성경 읽기 리스트 가져오기
            	bibleRead: function() {
					var url = '/bible/bibleRead.do';

	                var paramObj = {
	                		paramSeq : $('#thisReadSeq').val()
	                };
 
                	SchShareObj.data.ajax(url, {pars: paramObj, async: false, 
                    	onsucc: function(res) {
                    		if(res.resultCd === "1000") {
          						var bibleList = res.resultData;
          						var listSize = bibleList.length;
          						var bibleReadList = res.resultReadData;

          						// 읽기표 셋팅
          						for(var z=1; z<=2; z++) {
              						var content = "";
              						
          							for(var i=((z - 1) * 24); i<24 + ((z - 1) * 42); i++ ) {	// 다단으로 처리, 첫번째 단은 23권, 두번째 단은 43권
	//								for(var i=0; i<10; i++ ) {								
										var maxPage = bibleList[i].lastPage;
	
										// 성경별 이름을 rowspan으로 채움
										var row_cnt = maxPage / 20;
										if( (maxPage % 20) != 0 ) row_cnt++;								
										content += "<tr><td width='85px' rowspan="+row_cnt+" align='center' valign='middle' bgcolor='gray'><b>" + bibleList[i].bookNmKor + "</b></td>";
										
										// 각 페이지 숫자를 20칸씩 셋팅
										var col_cnt = 0;								
										for(var j=1; j<=maxPage; j++ ) {
											if(col_cnt == 20) {
												content += "</tr><tr>";
												col_cnt = 1;
											} else {
												col_cnt++;
											}
											
											//읽은 곳 체크하여 색깔로 표시
											var bgcolor='white';
											for(var read=0; read<bibleReadList.length; read++) {
												if(bibleReadList[read].bookNmKorAbr == bibleList[i].bookNmKorAbr && bibleReadList[read].bookPage == j) {
													bgcolor='purple';
												}
											}
											content += "<td align='center' bgcolor='" + bgcolor + "'>" + j + "</td>";
										}
										
										//맨 마지막 줄을 빈칸으로 20칸까지 채움
										var last_col = 20 - (maxPage % 20);									
										if(last_col != 20) {
											for(var k=0; k<last_col; k++ ) {
												content += "<td></td>";
											}
										}
										
										content += "</tr>";
									}

									if(z==2) {	//칸 크기를 동일하게 하기위함 꼼수
										content += "<tr height='55'><td></td>";
										content += "	<td><font color='white'>000</font></td><td><font color='white'>000</font></td><td><font color='white'>000</font></td><td><font color='white'>000</font></td>";
										content += "<td><font color='white'>000</font></td><td><font color='white'>000</font></td><td><font color='white'>000</font></td><td><font color='white'>000</font></td>";
										content += "	<td><font color='white'>000</font></td><td><font color='white'>000</font></td><td><font color='white'>000</font></td><td><font color='white'>000</font></td>";
										content += "<td><font color='white'>000</font></td><td><font color='white'>000</font></td><td><font color='white'>000</font></td><td><font color='white'>000</font></td>";
										content += "<td><font color='white'>000</font></td><td><font color='white'>000</font></td><td><font color='white'>000</font></td><td><font color='white'>000</font></td>";
										content += "</tr>";
									}
									$('#bible_list_'+z).html(content);
								}
                    		}
                  		  },
                          onerr: function(res){
                          	alert(res.resultMsg);
                          }
                      });
                },

                // 이전 장
                beforSeq: function() {
                	var tempPage = $('#thisReadSeq').val();
                	tempPage = Number(tempPage) - 1;
                	$('#thisReadSeq').val(tempPage);
                	
                    SchShareApp.data.bibleRead();
                },
                
                // 다음 장 
                nextSeq: function() {
                	var tempPage = $('#thisReadSeq').val();
                	tempPage = Number(tempPage) + 1;
                	if(tempPage <= $("#thisReadSeq option").size()) {	// 최대 페이지를 넘어갈 경우 동작 안함
	                	$('#thisReadSeq').val(tempPage);
	                	
	                    SchShareApp.data.bibleRead();
                	}
                }           
            },
  
            event: {
                init: function() {
                    // 조회
                    $('#dutySelectBtn').on('click', function() {
                      SchShareApp.data.bibleRead();
                    });
                    
                    // 이전장
                    $('#beforSeqBtn').on('click', function() {
                      SchShareApp.data.beforSeq();
                    });
                    
                    // 다음장
                    $('#nextSeqBtn').on('click', function() {
                      SchShareApp.data.nextSeq();
                    });
                    
                    // 차수 변경
                    $('#thisReadSeq').on('change', function() {
                    	SchShareApp.data.bibleRead();
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
	  <h3>Bible Reader</h3>
	</div>

	<div id="condition">
			<input type="button" id="beforSeqBtn" value="◀"/>
			<select id="thisReadSeq"></select>
			<input type="button" id="nextSeqBtn" value="▶"/>
	</div>

	<br>
	
<table width='90%'>
<tr>
<td align="center">
	<div id="bodyGrid_1"> 
	    <table>
	     <tbody  id="bible_list_1" >
	    	<tr>
	    		<td>...</td>
	    	</tr>
	     </tbody>
	    </table>
	</div>
</td>
<td align="center">
	<div id="bodyGrid_2"> 
	    <table>
	     <tbody  id="bible_list_2" >
	    	<tr>
	    		<td>...</td>
	    	</tr>
	     </tbody>
	    </table>
	</div>
</td>
</tr>
</table>

	


</div>

</body>

</html>