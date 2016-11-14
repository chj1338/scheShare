<%@ include file="/WEB-INF/views/include/common.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<HEAD>
  <title>당신의 행운은</title>

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
            
            	lotSelectData: function() {
					var url = '/lot/lotSelectData.do';
	  	            var paramObj = null;
	                      
                      SchShareObj.data.ajax(url, {pars: paramObj, async: false, 
                          onsucc: function(res) {
          					if(res.resultCd === "1000") {
          						var temp_num = res.resultData[0] + ", " + res.resultData[1] + ", " + res.resultData[2] + ", " + res.resultData[3] + ", " + res.resultData[4] + ", " + res.resultData[5];
          						$('#lottery_num').val(temp_num);
          					}
                          },
                          onerr: function(res){
                          	alert(res.resultMsg);
                          }
                      });
                },
                
                lotSelectLotteryAll: function() {
					var url = '/lot/lotSelectLotteryAll2.do';
	  	            var paramObj = null;
	                      
                      SchShareObj.data.ajax(url, {pars: paramObj, async: false, 
                          onsucc: function(res) {
          					if(res.resultCd === "1000") {
          						var temp_num1 = res.lotteryData1[0] + ", " + res.lotteryData1[1] + ", " + res.lotteryData1[2] + ", " + res.lotteryData1[3] + ", " + res.lotteryData1[4] + ", " + res.lotteryData1[5];
          						var temp_num2 = res.lotteryData2[0] + ", " + res.lotteryData2[1] + ", " + res.lotteryData2[2] + ", " + res.lotteryData2[3] + ", " + res.lotteryData2[4] + ", " + res.lotteryData2[5];
          						$('#lottery_num1').val(temp_num1);
          						$('#lottery_num2').val(temp_num2);
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
                    // 랜덤 번호조회
                    $('#lotSelectBtn').on('click', function() {
                      SchShareApp.data.lotSelectData();
                      SchShareApp.data.lotSelectLotteryAll();
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

<div id="effect">
  <h3>당신의 행운은</h3>
    
  <form id="lotSelectForm" name="lotSelectForm" enctype="text/html">
      <input type="button" id="lotSelectBtn" value="번호조회"/>
  </form>
</div>
<br>
<table>
	<tr>
		<td align="right">랜 덤 :</td>
		<td align="left"><input type="text" id="lottery_num"/></td>
	</tr>
	<tr><td> </td></tr>
	<tr>
		<td align="right">B제외 :</td>
		<td align="left"><input type="text" id="lottery_num1"/></td>
	</tr>
	<tr><td> </td></tr>
	<tr>
		<td align="right">B포함 :</td>
		<td align="left"><input type="text" id="lottery_num2"/></td>
	</tr>
</table>

</div>  

</body>

</html>