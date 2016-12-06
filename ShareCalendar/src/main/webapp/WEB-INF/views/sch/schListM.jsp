<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/common.jsp"%>
<html>
<head>

  <title>일정리스트</title>
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
  
   
<script  src="/resources/js/jquery/grid/jquery.jqGrid.min.js"></script>

    <script type="text/javascript">
    var SchShareApp = {
            scrID : 'schListM',
    
    		pageInit: function() {
                'use strict';
                this.jqgrid.init();
                this.data.init();
                this.event.init();
                
                // 날짜 달력모양 셋팅
                $("#schDtFrom").datepicker({ 
                    dateFormat: 'yy-mm-dd', 
                    changeMonth: true, 
                    changeYear: true
/*                     ,yearRange: '${fromNS}:${toNS}' */
                 });
                
                // 날짜 달력모양 셋팅
                $("#schDtTo").datepicker({ 
                    dateFormat: 'yy-mm-dd', 
                    changeMonth: true, 
                    changeYear: true
/*                     ,yearRange: '${fromNS}:${toNS}' */
                 });
            },

            jqgrid: {
                gridXhr: null,    
                
                init: function() {
                    $('#gridList').jqGrid({
                        mtype: 'GET',
                        datatype:'',
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
                            {name:'schId', 		index:'schId', 		width:1, align:'center' },
/*                             {name:'schDt', 		index:'schDt', 		width:1, align:'center', formatter: 'date', formatoptions: {'srcformat' : 'Y-m-d H:i:s', 'newformat' : 'Y-m-d H:i' } }, */
                            {name:'schDt', 		index:'schDt', 		width:1, align:'center', formatter: 'date', formatoptions: {'srcformat' : 'Ymd', 'newformat' : 'Y-m-d' } },
                            {name:'schTitle', 		index:'schTitle', 		width:1, align:'center', classes:'link1'},
                            {name:'schContent',	index:'schContent', 	width:5, align:'left'},
/*                             {name:'schUseCo', 	index:'schUseCo', 	width:1, align:'center'}, */                            
                            {name:'schUseCo', 	index:'schUseCo', 	width:1, align:'center', formatter:"select", editable:false, editoptions: {
                            	value:"P:공개;S:비공개"
                            }},
                            {name:'schRegistId',	index:'schRegistId', 	width:1, align:'center'},
                            {name:'schRegistDt',	index:'schRegistDt', 	width:2, align:'center'}
                        ],
                        sortname: 'registDt',       					// 정렬컬럼
                        sortorder: 'desc',       						// 정렬순서
                        sortable: true,     							// 컬럼 순서 변경
                        multiselect: true,  							// 로우 다중 선택
                        shrinkToFit: true,  							// 컬럼 넓이로만 width 설정
                        scrollOffset: 0,    							// 우측 스크롤 여부
                        vScrollOffset: 0,   							// 우측 스크롤 여부
//                        width: 1024,
                        autowidth: true,           					// width와 동시에 사용 안됨
                        viewrecords: true,  							// records의 View여부
                        gridview: true,     							// 처리속도 향상 ==> treeGrid, subGrid, afterInsertRow(event)와 동시 사용불가
                        scroll: 0,     									// 휠 페이징 사용 1
                        recordpos: 'right',     						// 우측좌측 기준변경 records 카운트의 위치 설정
                        pager: 'gridPager',             				// 하단 페이지처리 selector
                        rowList: [10, 20, 30],           			// 한번에 가져오는 row개수
                        loadtext: 'Data Loading From Server',	// 로드 되는 Text 문구
                        loadui: 'block',         						// 로드 블럭 스타일
                        height: 500,            						// 세로높이
                        rowNum: 100,         						// 최초 가져올 row 수
                        emptyrecords: '조회된 데이터가 없습니다.',     // 데이터 없을시 표시 
                        jsonReader: {
                            repeatitems: false,
                            //root: 'resultData.list',
                            root: 'resultData',
                            records: 'resultData.records',
                            total: 'resultData.total',
                            page: 'resultData.page',
                            id: 'resultData.list.templSn'
                        },
                        loadBeforeSend :function(xhr, settings) {
                            SchShareApp.jqgrid.gridXhr = xhr;
                        },
                        loadComplete: function(result) {
                        	/* 
                            if (result.resultCd === MbillObj.constants.result.FAIL) {
                                alert(result.resultMsg);
                                return false;
                            }
                             */
                        },
                        loadError: function(xhr, status, err) {
                            if(xhr.status === '0' || xhr.statusText === 'abort') {
                                return false;
                            }
                            
                            alert(xhr.statusText);
                        },
                        onCellSelect: function(rowid, iCol, cellcontent, e) {
                        	// 스케쥴 상세조회 이벤트
                            if (iCol === 3) {
                                var rowData = $('#gridList').getRowData(rowid);
                                SchShareApp.popup.SchDetail(rowData);
                            }
                            
                            if (iCol !== 0) $('#gridList').setSelection(rowid);
                        },
                        onSortCol: SchShareApp.jqgrid.search
                    });
                    SchShareApp.jqgrid.search();
                },
                
                search: function(object) {
                	
                },
                
                abort: function() {
                    if (SchShareApp.jqgrid.gridXhr) {
                        SchShareApp.jqgrid.gridXhr.abort();
                        SchShareApp.jqgrid.gridXhr = null;
                    }
                }
            },		// jqGrid

            data: {
                init: function() {
                },
            
	            schSearchData: function() {
	        		SchShareApp.jqgrid.abort();
 	        		
                	// validation check
                    var schDtFrom = $.trim($('#schDtFrom').val().replace(/-/g, ''));
                    var schDtTo = $.trim($('#schDtTo').val().replace(/-/g, ''));
                    
                    if (schDtFrom.length > 0 && schDtTo.length === 0) {
                        alert('종료일자를 선택하세요.');
                        return false;
                    }
                    
                    if (schDtFrom.length === 0 && schDtTo.length > 0) {
                        alert('시작일자를 선택하세요.');
                        return false;
                    }
                    
                    if (schDtFrom > schDtTo) {
                    	alert('시작일자를 종료일자 이전으로 선택하세요.');
                        return false;
                    }

					var url = "/sch/getScheduleList";
	  	            var paramObj = "schDtFrom="	+ schDtFrom
			                  + "&" + "schDtTo="	+ schDtTo
			                  + "&" + "schTitle="		+ $('#schTitle').val()
			                  + "&" + "schContent="	+ $('#schContent').val();
	                      
	                  SchShareObj.data.ajax(url, {pars: paramObj, async: false, 
	                      onsucc: function(res) {
	      					if(res.resultCd === "1000") {
								var object =  res.resultData;
								
								// 그리드에 row를 object.length만큼 생성
								$('#gridList').jqGrid('setGridParam', {
									datatype: 'local'
									,data: object
									,page: 1
									,rowNum: object.length
								}).trigger('reloadGrid');

								// 생성된 그리드에 값을 바인딩.(수동방식)
								for(var i=0; i<object.length; i++ ) {
									$('#gridList').jqGrid('setRowData', i+1,
										 {
											schId: 		object[i].scheNo,
											schDt: 		object[i].scheDt.substr(0, 4) + "-" + object[i].scheDt.substr(4, 2) + "-" + object[i].scheDt.substr(6, 2) ,
											schTitle: 		object[i].scheTitle,
											schContent: 	object[i].scheContent,
											schUseCo: 	object[i].scheSe,
											schRegistId: 	object[i].registId,
											schRegistDt:	object[i].registDt
									      });
								}
	      					}
	                      },
	                      onerr: function(res){
	                      	alert(res.resultMsg);
	                      }
	                  });
	           },
            },		// data
  
            event: {
                init: function() {
                    // 조회
                    $('#searchListBtn').on('click', function() {
                      SchShareApp.data.schSearchData();
                    });

                    // 제목검색
                    $('#sch_title').on('keydown', function(e) {
                        if (e.keyCode === 13)   SchShareApp.data.schSearchData();
                    });
                    
                    // 내용검색
                    $('#sch_content').on('keydown', function(e) {
                        if (e.keyCode === 13)   SchShareApp.data.schSearchData();
                    });
                    
                	// 신규 스케쥴 등록
                    $('#openPopupBtn').on('click', function() {
                      SchShareApp.popup.SchInsertPop();
                    });    
                }
            },
            
            popup: {
				SchInsertPop: function() {
                    // 스케쥴 등록
                    SchShareObj.popup.open({
                          /* url : '<c:url value="/schInsertM" />', */
                          url : '/schInsertM',
                          pars : 'schId=',
                          title: 'title',
                          method : 'get',
                          width : '500',
                          height : '480'
                        });
                    },
                    
                    SchDetail: function(rowData) {
                        // 스케쥴 상세조회
                        SchShareObj.popup.open({
                          /* url : '<c:url value="/schInsertM" />', */
                          url : '/schInsertM',
                          pars : 'schId=' + rowData.schId,
                          title: '스케쥴 상세조회',
                          method : 'get',
                          width : '500',
                          height : '480'
                        });
                    }
            }
    };

    </script>
 
</head>
<body>
	<div id="headLine">
	  <h3>일정 리스트</h3>
	</div>

	<div id="condition">
        제목 : <input type="text" id="schTitle"> 내용 : <input type="text" id="schContent">
  		시작일자 : <input type="text" id="schDtFrom"> ~ 종료일자 : <input type="text" id="schDtTo"> 
  		<input type="button" id="searchListBtn" value="조회">
		<input type="button" id="openPopupBtn" value="신규등록">
	</div>
<br><br>

    <table id="gridList"></table>
    <div id="gridPager"></div>

</body>
 
</html>
