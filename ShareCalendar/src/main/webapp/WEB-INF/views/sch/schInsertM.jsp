<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page import="java.util.*" %>

<%
	String clientIp = request.getRemoteHost();
%>

<HTML>
<HEAD>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8">
  <title>일정등록</title>
  <meta http-equiv="X-UA-Compatible" content="IE=edge">

    <script type="text/javascript">
    var SchShareApp = {
    		pageInit: function() {
                'use strict';
                this.data.init();
                this.event.init();
                
                //$('#sch_dt').val(SchShareObj.date.getCurrentDate());
                setToday();
            },
            
            data: {
                init: function() {
                },
                
                // 템플릿 목록
                schInsertData: function() {
                  var url = '/schInsertData';
	              var paramObj = {
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
  <h3>일정등록</h3>
    
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