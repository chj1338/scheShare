<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/common.jsp"%>
<html>
<HEAD>
  <title>일정리스트</title>

    <script type="text/javascript">
    var SchShareApp = {
    		pageInit: function() {
                'use strict';
                this.jqgrid.init();
                this.data.init();
                this.event.init();
            },
/*             
            jqgrid: {
                gridXhr: null,    
                
                init: function() {
                    $('#gridList').jqGrid({
                        mtype: 'POST',
                        url: '/sch/getScheduleList',
                        datatype: 'json',
                        colNames:[
                            '일련번호', 
                            '스케쥴날짜',
                            '제목',
                            '내용',
                            '구분',
                            '등록자',
                            '등록일자'
                        ],
                        colModel:[
                            {name:'schId', index:'templSn', width:1, align:'right' },
                            {name:'schDt', index:'jobSe', width:1, align:'center'},
                            {name:'schTitle', index:'templName', align:'left', width:1, classes:'link1'},
                            {name:'schContent', index:'templDt', width:5, align:'center'},
                            {name:'schUseCo', index:'templCha', width:1, align:'center'},
                            {name:'schRegistId', index:'registId', width:1, align:'center'},
                            {name:'schRegistDt', index:'registDt', width:2, align:'center'}
                        ],
                        sortname: 'schId',       // 정렬컬럼
                        sortorder: 'desc',       // 정렬순서
                        sortable: true,     // 컬럼 순서 변경
                        multiselect: true,  // 로우 다중 선택
                        shrinkToFit: true,  // 컬럼 넓이로만 width 설정
                        scrollOffset: 0,    // 우측 스크롤 여부
                        vScrollOffset: 0,    // 우측 스크롤 여부
                        autowidth: true,           // width와 동시에 사용 안됨
                        viewrecords: true,  // records의 View여부
                        gridview: true,     // 처리속도 향상 ==> treeGrid, subGrid, afterInsertRow(event)와 동시 사용불가
                        scroll: 0,      // 휠 페이징 사용 1
                        recordpos: 'right',     // 우측좌측 기준변경 records 카운트의 위치 설정
                        pager: 'gridPager',             // 하단 페이지처리 selector
                        rowList: [100, 200, 300],           // 한번에 가져오는 row개수
                        loadtext: 'Data Loading From Server',     // 로드 되는 Text 문구
                        loadui: 'block',         // 로드 블럭 스타일
                        height: 300,            // 세로높이
                        rowNum: 100,         // 최초 가져올 row 수
                        emptyrecords: '조회된 데이터가 없습니다.',     // 데이터 없을시 표시 
                        jsonReader: {
                            repeatitems: false,
                            root: 'resultData.list',
                            records: 'resultData.records',
                            total: 'resultData.total',
                            page: 'resultData.page',
                            id: 'resultData.list.schId'
                        },
                        loadBeforeSend :function(xhr, settings) {
                            SchShareApp.jqgrid.gridXhr = xhr;
                        },
                        loadComplete: function(result) {
                            if (result.resultCd === SchShareObj.constants.result.FAIL) {
                                alert(result.resultMsg);
                                return false;
                            }
                        },
                        loadError: function(xhr, status, err) {
                            if(xhr.status === '0' || xhr.statusText === 'abort') {
                                return false;
                            }
                            
                            alert(xhr.statusText);
                        },
                        onCellSelect: function(rowid, iCol, cellcontent, e) {
                        	// 템플릿 명칭 상세조회 이벤트
                            if (iCol === 3) {
                                var rowData = $('#gridList').getRowData(rowid);
//                                SchShareApp.popup.cmpTemplDetail(rowData);
                            }
                            
                            if (iCol !== 0) $('#gridList').setSelection(rowid);
                        },
                        onSortCol: SchShareApp.jqgrid.search
                    });
                    
                    SchShareApp.jqgrid.search();
                },
                
                data: function() {

                	
                    SchShareApp.jqgrid.abort();
                    $('#gridList').setGridParam({
                        url: '/sch/getScheduleList',
                        datatype: 'json',
                        page : 0,
                        postData: {
                        	templDtFrom : templDtFrom,
                            templDtTo : templDtTo,
                            sch_title: $('#sch_title').val(),
                            sch_content: $('#sch_content').val()
                        }
                    }).trigger('reloadGrid');
                },
                
            	schSearchData: function() {
                	// validation check
                    var templDtFrom = $.trim($('#templDtFrom').val().replace(/-/g, ''));
                    var templDtTo = $.trim($('#templDtTo').val().replace(/-/g, ''));
                    
                    if (templDtFrom.length > 0 && templDtTo.length === 0) {
                        alert('종료일자를 선택하세요.');
                        return false;
                    }
                    if (templDtFrom.length === 0 && templDtTo.length > 0) {
                        alert('시작일자를 선택하세요.');
                        return false;
                    }
                    if (templDtFrom > templDtTo) {
                    	alert('시작일자를 종료일자 이전으로 선택하세요.');
                        return false;
                    }
                    
					var url = "/sch/getScheduleList";
	  	            var paramObj = "templDtFrom="	+ templDtFrom
			                  + "&" + "templDtTo="	+ templDtTo
			                  + "&" + "sch_title="		+ $('#sch_title').val()
			                  + "&" + "sch_content="	+ $('#sch_content').val();
                    
					SchShareApp.jqgrid.abort();
                    SchShareObj.data.ajax(url, {pars: paramObj, async: false, 
                         onsucc: function(res) {
         					if(res.resultCd === "1000") {

         					}
                         },
                         onerr: function(res){
                         	alert(res.resultMsg);
                         }
                     }).trigger('reloadGrid');
                },
                
                abort: function() {
                    if (SchShareApp.jqgrid.gridXhr) {
                        SchShareApp.jqgrid.gridXhr.abort();
                        SchShareApp.jqgrid.gridXhr = null;
                    }
                }
            },
 */            
            data: {
                init: function() {
                },

	        	schSearchData: function() {
	            	// validation check
	                var templDtFrom = $.trim($('#templDtFrom').val().replace(/-/g, ''));
	                var templDtTo = $.trim($('#templDtTo').val().replace(/-/g, ''));
	                
	                if (templDtFrom.length > 0 && templDtTo.length === 0) {
	                    alert('종료일자를 선택하세요.');
	                    return false;
	                }
	                if (templDtFrom.length === 0 && templDtTo.length > 0) {
	                    alert('시작일자를 선택하세요.');
	                    return false;
	                }
	                if (templDtFrom > templDtTo) {
	                	alert('시작일자를 종료일자 이전으로 선택하세요.');
	                    return false;
	                }
	                
					var url = "/sch/getScheduleList";
	  	            var paramObj = "templDtFrom="	+ templDtFrom
			                  + "&" + "templDtTo="	+ templDtTo
			                  + "&" + "sch_title="		+ $('#sch_title').val()
			                  + "&" + "sch_content="	+ $('#sch_content').val();
	                
	                SchShareObj.data.ajax(url, {pars: paramObj, async: false, 
	                     onsucc: function(res) {
	     					if(res.resultCd === "1000") {
	
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
                    // 스케쥴 리스트 조회
                    $('#searchListBtn').on('click', function() {
                      SchShareApp.data.schSearchData();
                    });        
                	
                	// 신규 스케쥴 등록
                    $('#openPopupBtn').on('click', function() {
                      SchShareApp.popup.SchInsertPop.open();
                    });                    
                }
            },
            
            popup: {
                // 스케쥴 등록
                SchInsertPop: {
                    open: function() {
                        // 스케쥴 등록
                        SchShareObj.popup.open({
                          /* url : '<c:url value="/schInsertM" />', */
                          url : '/sch/schInsertM',
                          pars : '',
                          title: 'title',
                          method : 'get',
                          width : '500',
                          height : '450'
                        });
                    }
                }
            }
    };
    </script>

</HEAD>

<body>

<div id="center">

<div id="effect">
  <h3>일정리스트</h3>
    
  <form id="schListForm" name="schListForm" enctype="text/html">
        제목 : <input type="text" id="sch_title"> 내용 : <input type="text" id="sch_content">
  		시작일자 : <input type="text" id="templDtFrom"> ~ 종료일자 : <input type="text" id="templDtTo"> 
  		<input type="button" id="searchListBtn" value="조회">
		<input type="button" id="openPopupBtn" value="신규등록">
  </form>
</div>
<br>
     <!-- grid area -->
   <div class="grid" style="margin:2% auto 1;">
       <table id="gridList"></table>
       <div id="gridPager"></div>
   </div>
   <!-- /grid area -->

</div>  

</body>

</html>