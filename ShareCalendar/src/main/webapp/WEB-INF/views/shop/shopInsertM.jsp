<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/common.jsp"%>

<%
	String clientIp = request.getRemoteHost();
%>

<HTML>
<HEAD>
  <title>상품등록</title>
  <style type="text/css">
  	.formTitle {
  		border-style:solid;
  		border-width:0px;
  		font-size:14pt;
  		font-weight:bold;
  		line-height:200%;
  	}
  	
	/*   datepicker 메인크기 및 폰트 */
  	.ui-datepicker {
  		font-size:9pt;
  		width: 12%;
  	}
  	
  	/*   datepicker 월 콤보 크기 및 폰트 */
  	.ui-datepicker select.ui-datepicker-month {
  		width:50%;
  		font-size: 10px;
  	}

	/*   datepicker 년 콤보 크기 및 폰트 */
  	.ui-datepicker select.ui-datepicker-year {
  		width:50%;
  		font-size: 10px;
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
                  },
                  
  				// 파일업로드
                  imgSearch: function() {
                	    // Change this to the location of your server-side upload handler:
                	    var url = '/file/upload.do';  // 사용
                	    $('#fileupload').fileupload({
                	        url: url,
                	        dataType: 'json',
                	        done: function (e, data) {
                	            $.each(data.result.files, function (index, file) {
                	                $('<p/>').text(file.name).appendTo('#files');
                	            });
                	        },

                	        progressall: function (e, data) {
                	            var progress = parseInt(data.loaded / data.total * 100, 10);
                	            $('#progress .progress-bar').css(
                	                'width',
                	                progress + '%'
                	            );
                	        }
                	    }).prop('disabled', !$.support.fileInput).parent().addClass($.support.fileInput ? undefined : 'disabled');                	  
                  }                 
            },
            
            event: {
                init: function() {
                    // 등록
                    $('#insertBtn').on('click', function() {
                      SchShareApp.data.schInsertData();
                    });
                    
                    // 삭제
                    $('#deleteBtn').on('click', function() {
                      SchShareApp.data.schDeleteData();
                    });  
                    
                    // 이미지 찾기
                    $('#imgSearchBtn').on('click', function() {
                      SchShareApp.data.imgSearch();
                    });  
                }
            },
            
            popup: {

            }
    };

    function imgChange(reserve) {
//    	alert($('#fileupload').val());
//    	reserve.imgView.src = $('#fileupload').val();
    	reserve.imgView.src = "\resources\images\atlantis5.jpg";
    	reserve.imgView.refresh();    	
    }
    </script>

</HEAD>

<body>

<div id="center">

<div id="effect">
	<div id="formTitle"><textarea type="text" name="scrTitle" id="scrTitle" class='formTitle' value='일정등록' style='overflow-y:hidden;'></textarea></div>
    
  <form id="schInsertForm" enctype="multipart/form-data">
	  <div align="left">이미지<br><img src="$('#fileupload').val()" width='200' height='200' id='imgView'/>
	  						<img src="\resources\images\atlantis5.jpg" width='200' height='200'/>
	  						<img src="C:/Users/Administrator/Pictures/절당.jpg" width='200' height='200'/>
	  						<br>
	                      <input type="file" name="fileupload" id="fileupload" onChange="imgChange(this.form);"></input>
	  </div>
      <div align="left">제조사 : <select name="schSe" id="schSe">
      									<option value="P">공개</option>
      									<option value="S">비공개</option>
      						        </select>
      </div>
	  <br>
      <div align="left">상품명 : <input type="text" name="schTitle" id="schTitle"></input></div>
      <div align="left">상품상세 : <br><textarea name="schContent" id="schContent" cols="58" rows="20"></textarea></div>
      <br>
      <div align="left">
      	<button id="insertBtn">저장</button>  <button id="deleteBtn">삭제</button>
      </div>
  </form>     
</div>

</div>  

</body>

</html>