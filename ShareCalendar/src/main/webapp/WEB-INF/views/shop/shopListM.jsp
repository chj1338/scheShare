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
  	
  	.pagerGrid {
  		border-style:solid;
  		border-collapse:collapse;
  		border-width:0px;
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
            },
            

            
            data: {
                init: function() {
                },
            
            	shopSelectData: function(page) {
					var url = '/shop/getShopListData.do';
	  	            var paramObj = {
	  	            		keyword : $('#keyword').val(),
	  	            };
	                      
                      SchShareObj.data.ajax(url, {pars: paramObj, async: false, 
                          onsucc: function(res) {
          					if(res.resultCd === "1000") {
          						var shop_content = res.resultData;
          						
          						var display_max = 8;	// 화면 최대 표시 상품수
          						
          						// Paging 처리
          						var page_max = shop_content.length / display_max;
          						if(shop_content.length % display_max != 0) page_max++;
          						
          						var min_page = (page - 1) * display_max + 1;
          						var max_page = (page) * display_max;
          						var tot_page = shop_content.length;
          						if(max_page > tot_page) max_page = tot_page;
          						
          						var pager_width = 3;	// 페이저 숫자 한칸 넓이(%)

          						var page_content = "";
          						page_content += "<tr class='pagerGrid'>";
          						page_content += "<td width='"+((100 - page_max * pager_width) / 2)+"%' align='center' class='pagerGrid'></td>";
          						for(var i=1; i<=page_max; i++) {
          							page_content += "<td width='" + pager_width + "%' align='center' class='pagerGrid'><a href='#' onClick='SchShareApp.data.shopSelectData("+i+");' class='no-uline'>["+i+"]</a></td>";
          						}
          						page_content += "<td width='"+((100 - page_max * pager_width) / 2)+"%' class='pagerGrid' align='right'>("+ min_page + " ~ " + max_page + " / " + tot_page + ")</td>";
          						page_content += "</tr>";
          						
          						
          						$('#page_list').html(page_content);
          						
          						
          						// 화면 Display 시작          						
          						var content = "";	// body에 뿌려줄 그리드
          						var display_cnt = 0;		// 표시된상품 cnt

          						page = (page -1) * display_max;
          						
          						for(var i=0; i<shop_content.length; i++) {
									if(i >= page && display_cnt<display_max) {
	          							content += "<tr>";
	          							content += "<td align='center'><IMG src='" + shop_content[i].prodImg + "' width='40' height='70' /></td>";
	          							content += "<td align='center'>" + shop_content[i].prodId + "</td>";
	          							content += "<td align='center'>" + shop_content[i].prodNm + "</td>";
	          							content += "<td align='center'>" + shop_content[i].prodPrice + "</td>";
	          							content += "<td align='left'>" + shop_content[i].prodCn + "</td>";
	          							content += "<td align='center'>" + shop_content[i].prodMakerNm + "</td>";
	          							content += "</tr>";
	          							
	          							display_cnt++;
									}
          						}
          						
          						if(display_cnt<display_max) {
          							content += "<tr height='" + (560 - ((display_max - display_cnt) * 80)) + "'><td></td><td></td><td></td><td></td><td></td><td></td></tr>";
          						}
				
          						$('#shop_list').html(content);
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
                    // 상품 조회
                    $('#shopSelectBtn').on('click', function() {
                      SchShareApp.data.shopSelectData(1);
                    });
                    
                    // 상품 등록
                    $('#shopRegBtn').on('click', function() {
                      SchShareApp.popup.ShopInsert();
                    });
                    
                    // 검색
                    $('#keyword').on('keydown', function(e) {
                        if (e.keyCode === 13)   SchShareApp.data.shopSelectData(1);
                    });
                }
            },
                    
            popup: {
                ShopInsert: function(rowData) {
                    // 제품 등록
                    SchShareObj.popup.open({
                      url : '/shopInsertM',
                      pars : 'ShopId=',
                      title: '제품등록',
                      method : 'get',
                      width : '500',
                      height : '480'
                    });
                }
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
			검색어 : <input type="text" id="keyword""/>
			<input type="button" id="shopSelectBtn" value="조회"/>
			<input type="button" id="shopRegBtn" value="상품등록"/>
	</div>

	<br>

	<div id="bodyGrid"> 
	    <table border="1" width="80%">
	    <thead>
	    	<tr bgcolor="gray">
	    		<td width="10%">사진</td>
	    		<td width="10%">ID</td>
	    		<td width="10%">상품명</td>
	    		<td width="10%">가격</td>
	    		<td width="50%">제품상세</td>
	    		<td width="10%">제조사</td>
	    	</tr>
	    </thead>
	     <tbody  id="shop_list" >
	    	<tr>
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
	
	<br>
	
	<div id="pagerGrid">
	    <table width="80%" class='pagerGrid'>
	     <tbody  id="page_list" >
	    	<tr class='pagerGrid'>
	    		<td class='pagerGrid'></td>
	    	</tr>
	     </tbody>
	    </table>
	</div>
	
</div>

</body>

</html>