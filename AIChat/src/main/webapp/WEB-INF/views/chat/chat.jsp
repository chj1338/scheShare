<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %> 
<%@ include file="/WEB-INF/views/include/common.jsp"%>

<html>
<HEAD>
  <title>RSS뉴스</title>
 
    <script type="text/javascript">
    var AIChat = {
    		pageInit: function() {
                'use strict';
                this.data.init();
                this.event.init();
            },        

            
            data: {
                init: function() {
                    alert(1);
                },
            
            	shopSelectData: function() {
					var url = '/shop/getShopListData';
	  	            var paramObj = {
	  	            		sendId : $('#sendId').val(),
	  	            		sendMsg : $('#sendMsg').val()
	  	            };
	                      
                      SchShareObj.data.ajax(url, {pars: paramObj, async: false, 
                          onsucc: function(res) {
          					if(res.resultCd === "1000") {
          						var object = res.resultData;
          						alert(res.resultMsg);
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
                    alert(1);
                    // 상품 조회
                    $('#shopSelectBtn').on('click', function() {
                    	alert(1);
                      AIChat.data.shopSelectData();
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
	  <h3>Shop 리스트</h3>
	</div>

	<div id="condition">
			<textarea rows='20' cols='80'></textarea>
			<br>
			ID : <input type="text" id="sendId"/>
			<input type="text" id="sendMsg"/>
			<input type="button" id="shopSelectBtn" value="전송"/>
	</div>
	
</div>

</body>

</html>