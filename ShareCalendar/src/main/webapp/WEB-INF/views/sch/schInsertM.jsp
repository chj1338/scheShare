<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/common.jsp"%>

<%
	String clientIp = request.getRemoteHost();
%>

<HTML>
<HEAD>
  <title>일정등록</title>
  <style type="text/css">
  	.formTitle {
  		border-style:solid;
  		border-width:0px;
  		font-size:14pt;
  		font-weight:bold;
  		line-height:200%;
  	}
  </style>
  
    <script type="text/javascript">
    var SchShareApp = {
    		schId: '${schId}',	
    		pageTitle : '',
    		
    		pageInit: function() {
                'use strict';
                this.data.init();
                this.event.init();
                
                // 날짜 달력모양 셋팅
                $("#schDt").datepicker({ 
                    dateFormat: 'yy-mm-dd', 
                    changeMonth: true, 
                    changeYear: true, 
                    yearRange: '${fromNS}:${toNS}'
                 });
                
                if(SchShareApp.schId == "") {
                    setToday();
                    $('#schSe').val('P'),
                    $('#schContent').val(SchShareApp.schId);
                    SchShareApp.pageTitle = "일정등록";
                    schSe.val('P');
                } else {
                	SchShareApp.pageTitle = "일정상세";
                	SchShareApp.data.schDetailData();
                }

                // 화면 제목 셋팅
                document.title = SchShareApp.pageTitle;
                $('#scrTitle').val( SchShareApp.pageTitle );
            },
            
            data: {
                init: function() {
                },
                
                //스케쥴 상세조회
                schDetailData: function() {
                    var url = '/sch/getSchDetailData';
  	              	var paramObj = {
	    					schId : SchShareApp.schId
					};
                      
                    SchShareObj.data.ajax(url, {pars: paramObj, async: false, 
                        onsucc: function(res) {
          					if(res.resultCd === "1000") {
								var object = res.resultData;
								schDt : $('#schDt').val( object[0].scheDt.substr(0, 4) + "-" + object[0].scheDt.substr(4, 2) + "-" + object[0].scheDt.substr(6, 2) );
								schSe : $('#schSe').val( object[0].scheSe );
	  	    					schTitle : $('#schTitle').val( object[0].scheTitle ); 
	  	    					schContent : $('#schContent').val( object[0].scheContent );   
          					}
                          },
                          onerr: function(res){
                          	alert(res.resultMsg);
                          }
                      });
                  },
                  
                  // 스케쥴 등록
                  schInsertData: function() {
                    var url = '/sch/schInsertData';
                    var schDt = $.trim($('#schDt').val().replace(/-/g, ''));
                    
  	              	var paramObj = {
  	            		    schId : SchShareApp.schId,
  	    					schDt : schDt,
  	    					schSe : $('#schSe').val(),
   	    					schTitle : $('#schTitle').val(), 
   	    					schContent : $('#schContent').val()         			
  	            	};
                      
                      SchShareObj.data.ajax(url, {pars: paramObj, async: false, 
                          onsucc: function(res) {
          					if(res.resultCd === "1000") {
          						alert("정상적으로 등록되었습니다.");
          						
          						// 부모창 새로고침
       							SchShareApp.data.schRefreshOpener();
          					}
                          },
                          onerr: function(res){
                          	alert(res.resultMsg);
                          }
                      });
                  },
                  
                  // 스케쥴 삭제
                  schDeleteData: function() {
                    var url = '/sch/schDeleteData';
  	              var paramObj = {
  	            		    schId : SchShareApp.schId         			
  	            	};
                      
                      SchShareObj.data.ajax(url, {pars: paramObj, async: false, 
                          onsucc: function(res) {
          					if(res.resultCd === "1000") {
          						alert("정상적으로 삭제되었습니다.");
          						
          						// 부모창 새로고침
       							SchShareApp.data.schRefreshOpener();
          					}
                          },
                          onerr: function(res){
                          	alert(res.resultMsg);
                          }
                      });
                  },
                  
				// 부모창 새로고침
                schRefreshOpener: function() {
                	if(SchShareApp.schId != "") {
						if(opener.SchShareApp.scrID == 'schListM') {
							opener.SchShareApp.data.schSearchData();
							window.open('about:blank', '_self').close();
						} else if(opener.SchShareApp.scrID == 'schDutyM') {
							opener.SchShareApp.data.dutySelectData();
							window.open('about:blank', '_self').close();
						}
                	}
                  }
                  
            },
            
            event: {
                init: function() {
                    // 신규 스케쥴 등록
                    $('#insertBtn').on('click', function() {
                      SchShareApp.data.schInsertData();
                    });
                    
                    // 스케쥴 삭제
                    $('#deleteBtn').on('click', function() {
                      SchShareApp.data.schDeleteData();
                    });  
                }
            },
            
            popup: {

            }
    };
    
    
    
    function setToday() {
		// 오늘날짜 셋팅
		var toDate = new Date();
		var toDay = toDate.getFullYear() + "";
		var nowMonth = toDate.getMonth() + 1;
		var nowDay = toDate.getDate();
		
		if(nowMonth < 10) toDay += "-0" + nowMonth;
		else toDay += "-" + nowMonth;
		
		if(nowDay < 10) toDay += "-0" + nowDay;
		else toDay += "-" + nowDay;
		
		$('#schDt').val(toDay);
    }
    </script>

</HEAD>

<body>

<div id="center">

<div id="effect">
	<div id="formTitle"><textarea type="text" name="scrTitle" id="scrTitle" class='formTitle' value='일정등록' style='overflow-y:hidden;'></textarea></div>
    
  <form id="schInsertForm">
      <div align="left">날짜 : <input type="text" name="schDt" id="schDt"></input>
      						구분 : <select name="schSe" id="schSe">
      									<option value="P">공유</option>
      									<option value="S">개인</option>
      						        </select>
      </div>
      <div align="left">제목 : <input type="text" name="schTitle" id="schTitle"></input></div>
      <div align="left">내용 : <br><textarea name="schContent" id="schContent" cols="58" rows="20"></textarea></div>
      <br>
      <div align="left">
      	<button id="insertBtn">저장</button>  <button id="deleteBtn">삭제</button>
      </div>
  </form>     
</div>

</div>  

</body>

</html>