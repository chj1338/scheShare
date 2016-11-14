<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/common.jsp"%>

<%
	String clientIp = request.getRemoteHost();
%>

<HTML>
<HEAD>
  <title>일정등록</title>

    <script type="text/javascript">
    var SchShareApp = {
    		schId: '${schId}',	
    		
    		pageInit: function() {
                'use strict';
                this.data.init();
                this.event.init();
                
                if(SchShareApp.schId == "") {
                    setToday();
                    $('#sch_content').val(SchShareApp.schId);
                } else {
                	document.title = "일정조회";
                	$('#scrTitle').val("일정조회");
                	SchShareApp.data.schDetailData();
                }
            },
            
            data: {
                init: function() {
                },
                
                // 스케쥴 등록
                schInsertData: function() {
                  var url = '/sch/schInsertData';
	              var paramObj = {
	            		    schId : SchShareApp.schId,
	    					sch_dt : $('#sch_dt').val(), 
  	    					sch_title : $('#sch_title').val(), 
  	    					sch_content : $('#sch_content').val()         			
	            	};
                    
                    SchShareObj.data.ajax(url, {pars: paramObj, async: false, 
                        onsucc: function(res) {
        					if(res.resultCd === "1000") {
        						alert("정상적으로 등록되었습니다.");
        						window.close();
        					}
                        },
                        onerr: function(res){
                        	alert(res.resultMsg);
                        }
                    });
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
								sch_dt : $('#sch_dt').val( object[0].scheDt );
	  	    					sch_title : $('#sch_title').val( object[0].scheTitle ); 
	  	    					sch_content : $('#sch_content').val( object[0].scheContent );   
          					}
                          },
                          onerr: function(res){
                          	alert(res.resultMsg);
                          }
                      });
                  }
            },
            
            event: {
                init: function() {
                    // 신규 스케쥴 등록
                    $('#insertBtn').on('click', function() {
                      SchShareApp.data.schInsertData();
                    });                    
                }
            },
            
            popup: {

            }
    };
    
    
    
    function setToday() {
		// 오늘날짜 셋팅
		var toDate = new Date();
		var toDay = toDate.getFullYear();
		var nowMonth = toDate.getMonth() + 1;
		var nowDay = toDate.getDate();
		
		if(nowMonth < 10) toDay += "0" + nowMonth;
		else toDay += nowMonth;
		
		if(nowDay < 10) toDay += "0" + nowDay;
		else toDay += nowDay;
		
		$('#sch_dt').val(toDay);
    }
    </script>

</HEAD>

<body>

<div id="center">

<div id="effect">
	<div id="scrTitle"><h3>일정등록</h3></div>
    
  <form id="schInsertForm">
      <div align="left">날짜 : <input type="text" name="sch_dt" id="sch_dt"></input></div>
      <div align="left">제목 : <input type="text" name="sch_title" id="sch_title"></input></div>
      <div align="left">내용 : <br><textarea name="sch_content" id="sch_content" cols="50" rows="20"></textarea></div>
      <button id="insertBtn">저장</button>
  </form>     
</div>

</div>  

</body>

</html>