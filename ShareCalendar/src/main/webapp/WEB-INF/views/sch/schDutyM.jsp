<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %> 
<%@ include file="/WEB-INF/views/include/common.jsp"%>
 
<!DOCTYPE html>
<html>
<HEAD>
  <title>일정달력</title>
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
    		pageInit: function() {
                'use strict';
                this.data.init();
                this.event.init();
                
                SchShareApp.data.dutySelectData();
            },
            
            data: {
                init: function() {
                },
            
            	dutySelectData: function() {
					var url = '/sch/schDutyData.do';
	                var paramObj = {
	                		paramYear : $('#thisYear').val(),
	                		paramMonth : $('#thisMonth').val()
	                };
 
                      SchShareObj.data.ajax(url, {pars: paramObj, async: false, 
                    	  onsucc: function(res) {
                    		  if(res.resultCd === "1000") {
          						var duty_content = res.resultData;
          						var content = "";
          						var thisYear = duty_content[0].thisYear;
          						var thisMonth = duty_content[0].thisMonth;
          						
          						for(var i=0; i<duty_content.length; i++) {
          							
          							
          							content += "<tr>";
          							content += "<td height='80' valign='top' align='left'>" + duty_content[i].sun + "</td>";
            						content += "<td valign='top' align='left'>" + duty_content[i].mon + "</td>";
          							content += "<td valign='top' align='left'>" + duty_content[i].tue + "</td>";
          							content += "<td valign='top' align='left'>" + duty_content[i].wed + "</td>";
          							content += "<td valign='top' align='left'>" + duty_content[i].thu + "</td>";
          							content += "<td valign='top' align='left'>" + duty_content[i].fri + "</td>";
          							content += "<td valign='top' align='left'>" + duty_content[i].sat + "</td>"; 
          							content += "</tr>";
          						}
          						
          						$('#thisYear').val(thisYear);
          						$('#thisMonth').val(thisMonth);
          						$('#duty_list').html(content);
          					}
                          },
                          onerr: function(res){
                          	alert(res.resultMsg);
                          }
                      });
                },
                
                lastYear: function() {
                	var temp = $('#thisYear').val();
                	$('#thisYear').val( Number(temp) - 1);
                },
                
                lastMonth: function() {
                	var tempYear = $('#thisYear').val();
                	var tempMonth = $('#thisMonth').val();
                	tempMonth = Number(tempMonth) - 1;
                	
                	if(tempMonth < 1) {
                		tempMonth = 12;
                		tempYear = Number(tempYear) - 1;
                	}
                	
                	$('#thisYear').val( tempYear);
                	$('#thisMonth').val(tempMonth);
                },
                
                nextMonth: function() {
                	var tempYear = $('#thisYear').val();
                	var tempMonth = $('#thisMonth').val();
                	tempMonth = Number(tempMonth) + 1;
                	
                	if(tempMonth > 12) {
                		tempMonth = 1;
                		tempYear = Number(tempYear) + 1;
                	}
                	
                	$('#thisYear').val( tempYear);
                	$('#thisMonth').val(tempMonth);
                },
                
                nextYear: function() {
                	var temp = $('#thisYear').val();
                	$('#thisYear').val( Number(temp) + 1);
                }
                
            },
  
            event: {
                init: function() {
                    // duty 조회
                    $('#dutySelectBtn').on('click', function() {
                      SchShareApp.data.dutySelectData();
                    });
                    
                    // 전년
                    $('#lastYearBtn').on('click', function() {
                      SchShareApp.data.lastYear();
                      SchShareApp.data.dutySelectData();
                    });
                    
                    // 전월
                    $('#lastMonthBtn').on('click', function() {
                      SchShareApp.data.lastMonth();
                      SchShareApp.data.dutySelectData();
                    });
                    
                    // 차월
                    $('#nextMonthBtn').on('click', function() {
                      SchShareApp.data.nextMonth();
                      SchShareApp.data.dutySelectData();
                    });
                    
                    // 내년
                    $('#nextYearBtn').on('click', function() {
                      SchShareApp.data.nextYear();
                      SchShareApp.data.dutySelectData();
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
	  <h3>일정달력</h3>
	</div>

	<div id="condition">
			<input type="button" id="lastYearBtn" value="◀◀"/>
			<input type="button" id="lastMonthBtn" value="◀"/>
			<input type="text" id="thisYear" style="text-align:right;" size="4">년
			<input type="text" id="thisMonth" style="text-align:right;" size="2">월
			<input type="button" id="nextMonthBtn" value="▶"/>
			<input type="button" id="nextYearBtn" value="▶▶"/>
			<input type="button" id="dutySelectBtn" value="조회"/>
	</div>

	<br>

	<div id="bodyGrid"> 
	    <table border="1" width="50%">
	    <thead>
	    	<tr bgcolor="gray">
	    		<td width="14%">일</td>
	    		<td width="15%">월</td>
	    		<td width="15%">화</td>
	    		<td width="14%">수</td>
	    		<td width="14%">목</td>
	    		<td width="14%">금</td>
	    		<td width="14%">토</td>
	    	</tr>
	    </thead>
	     <tbody  id="duty_list" >
	    	<tr>
	    		<td>...</td>
	    		<td>...</td>
	    		<td>...</td>
	    		<td>...</td>
	    		<td>...</td>
				<td>...</td>
	    		<td>...</td>
	    	</tr>
	     </tbody>
	    </table>
	</div>

</div>

</body>

</html>