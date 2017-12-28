<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/common.jsp"%>
 
<!DOCTYPE html>
<html>
<HEAD>
  <title>통신개발1팀 신문고</title>
  <style type="text/css">
/* 
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
            
                sinmungoSave : function() {
					var url = '/main/sinmungoSave.do';
	  	            var paramObj = {
	  	            		sinmungoData : $('#sinmungoData').val()
	  	            };
	                      
                      SchShareObj.data.ajax(url, {pars: paramObj, async: false, 
                          onsucc: function(res) {
          					if(res.resultCd === "1000") {
          						alert("저장에 성공하였습니다.");
          						$('#sinmungoData').val("");
          					} else {
                              	alert("오류가 발생했습니다.");
                              	$('#sinmungoData').val(res.resultMsg);
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
                    $('#sinmungoSaveBtn').on('click', function() {
                      SchShareApp.data.sinmungoSave();
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
	  <h3>통신개발1팀 신문고</h3>
	</div>

	<div id="condition">

		<table border=0>
			<tr><td><H4>▶▶▶  나는 고하노라.  ◀◀◀</H4></td></tr>
			<tr><td> 말로 못다한 사연,
			      <br> PM 때문에 말못했던 사연
			      <br> 가슴에 사무치는 사연,
			      <br> 팀장님께 말하고 싶은데 자기 입으론 도저히 못하겠는 사연 등등....
			      <br> 마구마구 적어주세요.
			      </td></tr>
			<tr><td><br><br>익명성 완전 보장<br></td></tr>
			<tr><td>  </td></tr>
			<tr><td><textarea id="sinmungoData" cols='80' rows='20'></textarea></td></tr>
			<tr><td><input type="button" id="sinmungoSaveBtn" value="저장"/></td></tr>
		</table>

	</div>

</div>
	
</body>

</html>