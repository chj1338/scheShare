<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/common.jsp"%>
<html>
<head>

<title>기사수집3</title>
  <style type="text/css">
  	table, tr, td {
  		border-style:solid;
  		border-collapse:collapse;
  		border-width:1px;
  	}
  	
  	thead tr td {
  	  	font-size:9pt;
  	 	font-weight:bold;
  	 	text-align:center;
  	}
  	
  	tbody tr td {
  		font-size:9pt;
  		font-weight:normal;
  		border-spacing:0px;
  		padding:3px;
  		width:6%;
  	}

  </style>
  
<!--   
<script  src="http://code.jquery.com/jquery-latest.min.js"></script>

    <script type="text/javascript">
    $(document).ready(function(){
        $('#articleSearchBtn').click(function(){
			$.ajax({
				async:"false",
				type: "POST",
				url: "http://localhost/article/searchArticleData",
				datatype: "json",
				data: "keyword=" + $('#keyword').val(),
				success: function(res){
					//alert(res.resultData);
					var object = res.resultData;
					var content = "";
					for(var i=0; i<object.length; i++) {
						content += "<tr>";
						content += "<td align='center'>" + object[i].num + "</td>";
						content += "<td align='center'>" + object[i].site + "</td>";
						content += "<td align='left'><a href='"+object[i].articleLink+"' target='_blank'>" + object[i].article + "</a></td>";
						content += "</tr>";
					}
					
					$('#articleList').html(content);
				},
	          error : function(xhr, status, error) {
	              alert("에러");
	          }
			}); 
        });
    });
    </script>
     -->
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
            
            	articleSearchData: function() {
					var url = "/article/searchArticleData";
	  	            var paramObj = "keyword=" + $('#keyword').val();
	                      
                      SchShareObj.data.ajax(url, {pars: paramObj, async: false, 
                          onsucc: function(res) {
          					if(res.resultCd === "1000") {
          						var object = res.resultData;
          						
          						var content = "";
          						for(var i=0; i<object.length; i++) {
          							content += "<tr>";
          							content += "<td align='center'>" + object[i].num + "</td>";
          							content += "<td align='center'>" + object[i].site + "</td>";
          							content += "<td align='left'><a href='"+object[i].articleLink+"' target='_blank'>" + object[i].article + "</a></td>";
          							content += "</tr>";
          						}

          						$('#articleList').html(content);
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
                    $('#articleSearchBtn').on('click', function() {
                      SchShareApp.data.articleSearchData();
                    });

                    // 검색
                    $('#keyword').on('keydown', function(e) {
                        if (e.keyCode === 13)   SchShareApp.data.articleSearchData();
                    });
                }
            },
            
            popup: {

            }
            
    };

    </script>
 
</head>
<body>
<input type="text" id="keyword" value=""/>
<button id="articleSearchBtn">조회</button>
<br><br>

    <table border="1" width='70%'>
    <thead>
	    <tr bgcolor="gray">
    		<td width='10%'>순번</td>
    		<td width='20%'>사이트</td>
    		<td width='80%'>기사</td>
    	</tr>
    </thead>
     <tbody  id="articleList" >
    	<tr>
    		<td>...</td>
    		<td>...</td>
    		<td>...</td>
    	</tr>
     </tbody>
    </table>

</body>
 
</html>
