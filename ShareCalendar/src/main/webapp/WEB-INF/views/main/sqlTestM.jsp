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
/* 
  	#condition {
  		display: table-cell;
  		vertical-align: middle;
  	}
 */  	
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
            
            	sqlSelectData: function() {
					var url = '/main/sqlSelectData.do';
	  	            var paramObj = {
	  	            		sqlContent : $('#sqlContent').val(),
	  	            };
	                      
                      SchShareObj.data.ajax(url, {pars: paramObj, async: false, 
                          onsucc: function(res) {
          					if(res.resultCd === "1000") {
          						var resultContent = res.resultData;
          						
          						$('#resultContent').val(resultContent);
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
                    // 조회
                    $('#sqlSelectBtn').on('click', function() {
                      SchShareApp.data.sqlSelectData();
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
	  <h3>SQL 테스트</h3>
	</div>

	<div id="condition">
			조회쿼리 : <textarea id="sqlContent"" cols='80' rows='20'>select * from LOT_LOTTERY_NUMBER where rownum < 10</textarea>
			<input type="button" id="sqlSelectBtn" value="쿼리실행"/>
	</div>

	<br>

	<div id="bodyGrid"> 
			쿼리결과 : <textarea id="resultContent"" cols='80' rows='20'></textarea>
	</div>

</div>

</body>

</html>