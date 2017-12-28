<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/common.jsp"%>
<html>
<head>

  <title>사람검색</title>
  <style type="text/css">
/*   
  	table, tr, td {
  		border-style:solid;
  		border-collapse:collapse;
  		border-width:1px;
  	}
 */  
 table {
 	width: 98%;
/* 
 	border-style:solid;
 	border-width:1px;
 */
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
  	
  	paginate {
  		width: 80%;
  		border-width:0px;
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
  
   
<script  src="/resources/js/jquery/grid/jquery.jqGrid.min.js"></script>
<script  src="/resources/js/jqgrid-paging.js"></script>


    <script type="text/javascript">
    var SchShareApp = {
            scrID : 'manCountListM',
    
    		pageInit: function() {
                'use strict';
                this.jqgrid.init();
                this.event.init();               
            },

            jqgrid: {
                gridXhr: null,    
                
                init: function() {
                	// 콤보데이터 셋팅
					var url = "/etc/initData";
	  	            var paramObj = "";
	                      
	                  SchShareObj.data.ajax(url, {pars: paramObj, async: false, 
	                      onsucc: function(res) {
	      					if(res.resultCd === "1000") {
								// 소속 회사
	      						var companyData =  res.companyData;
								for(var i=0; i< companyData.length; i++) {
									$("<option value='" + companyData[i].COM_CD + "'>" + companyData[i].COM_NM + "</option>").appendTo('#company');
								}
								
								// 등급
	      						var gradeData =  res.gradeData;
								for(var i=0; i< gradeData.length; i++) {
									$("<option value='" + gradeData[i].GRADE_CD + "'>" + gradeData[i].GRADE_NM + "</option>").appendTo('#grade');
								}
								
								// mid
	      						var midData =  res.midData;
								for(var i=0; i< midData.length; i++) {
									$("<option value='" + midData[i].MID_CD + "'>" + midData[i].MID_NM + "</option>").appendTo('#mid');
								}
								
								// 고객사
	      						var coclcdData =  res.coclcdData;
								for(var i=0; i< coclcdData.length; i++) {
									$("<option value='" + coclcdData[i].CO_CL_CD + "'>" + coclcdData[i].CO_CL_NM + "</option>").appendTo('#coclcd');
								}
 
	      					}
	                      },
						  onerr: function(res){
	                      	alert(res.resultMsg);
	                      }
	                  });
                	
                	// jqGrid 시작
                    $('#gridList').jqGrid({
                        mtype: 'GET',
                        datatype:'json',
                        colNames:[
							'소속', 
							'이름',
							'등급',
							'경력',
							'MID',
							'수행 PJT',
							'역량',
							'고객사'
                        ],
                        colModel:[
                            {name:'company', 	index:'company', 	width:1, align:'center' },
                            {name:'name', 		index:'name', 		width:1, align:'center' },
                            {name:'grade', 		index:'grade', 		width:1, align:'center' },
                            {name:'career_year', index:'career_year', 	width:1, align:'center' },
                            {name:'mid', 			index:'mid', 			width:1, align:'center' },
                            {name:'experience', 	index:'experience', 	width:3, align:'left' },
                            {name:'capability', 	index:'capability', 	width:1, align:'left' },
                            {name:'coclcd', 		index:'coclcd', 		width:1, align:'left' }
                        ],
                        caption: '사람찾기',
                        sortname: 'registDt',       					// 정렬컬럼
                        sortorder: 'desc',       						// 정렬순서
                        sortable: true,     							// 컬럼 순서 변경
                        multiselect: true,  							// 로우 다중 선택
                        shrinkToFit: true,  							// 컬럼 넓이로만 width 설정
                        scrollOffset: 0,    							// 스크롤 여부
                        vScrollOffset: 0,   							// 우측 스크롤 여부
                        width: 1300,
//                        autowidth: true,           					// width와 동시에 사용 안됨
                        viewrecords: true,  							// records의 View여부
                        gridview: true,     							// 처리속도 향상 ==> treeGrid, subGrid, afterInsertRow(event)와 동시 사용불가
                        scroll: 0,     									// 휠 페이징 사용 1
                        recordpos: 'right',     						// 우측좌측 기준변경 records 카운트의 위치 설정
                        pager: 'gridPager',             				// 하단 페이지처리 selector
                        rowList: [10, 20, 30],           				// 한번에 가져오는 row개수
                        loadtext: 'Data Loading From Server',	// 로드 되는 Text 문구
                        loadui: 'block',         						// 로드 블럭 스타일
                        loadonce: false,
                        height: 590,            						// 세로높이
                        rowNum: 100,         						// 최초 가져올 row 수
                        rownumbers: true,
                        emptyrecords: '조회된 데이터가 없습니다.',     // 데이터 없을시 표시 
                        jsonReader: {
                            repeatitems: false,
                            root: 'resultData',
                            records: 'resultRecords',
                            total: 'resultTotal',
                            page: 'resultPage'
                        },
                        loadBeforeSend :function(xhr, settings) {
                            SchShareApp.jqgrid.gridXhr = xhr;
                        },
                        loadComplete: function(result) {
                        	initPage("gridList", "paginate", true, "TOT");
                        },
                        loadError: function(xhr, status, err) {
                            if(xhr.status === '0' || xhr.statusText === 'abort') {
                                return false;
                            }
                            
                            alert(xhr.statusText);
                        },
                        onSortCol: SchShareApp.jqgrid.search
                    });
                    SchShareApp.jqgrid.search();
                },
                
                search: function(object) {
	        		SchShareApp.jqgrid.abort();

					var url = "/etc/manSearchList";
	  	            var paramObj = "company="	+ $('#company').val()
			                  + "&" + "name="		+ $('#name').val()
			                  + "&" + "grade="		+ $('#grade').val()
			                  + "&" + "mid="		+ $('#mid').val()
			                  + "&" + "coclcd="	+ $('#coclcd').val()
			                  + "&" + "capa="		+ $('#capa').val()
	  	          			  + "&" + "experience="	+ $('#experience').val()
	  	          			  + "&" + "page="		+ $('#gridList').getGridParam('page')
	  	          			  + "&" + "rowNum="	+ $('#gridList').getGridParam('rowNum') ;
	                      
	                  SchShareObj.data.ajax(url, {pars: paramObj, async: false, 
	                      onsucc: function(res) {
	      					if(res.resultCd === "1000") {
								var object = res.resultData;								
								var rowNum = $('#gridList').getGridParam('rowNum');

								// 그리드에 row를 object.length만큼 생성
								$('#gridList').jqGrid('setGridParam', {
									datatype: 'local'
									,data: object
									,records: res.resultRecords
		                            ,total: res.resultTotal
		                            ,page: res.resultPage
									,rowNum: rowNum	// 한페이지에 보여줄 레코드 수
								}).trigger('reloadGrid');

								// 생성된 그리드에 값을 바인딩.(수동방식)
								for(var i=0; i<object.length; i++ ) {
									$('#gridList').jqGrid('setRowData', i+1,
										 {
											company: 	object[i].COMPANY,
											name: 		object[i].NAME,
											grade: 		object[i].GRADE,
											career_year: 	object[i].CAREER_YEAR,
											mid: 			object[i].MID,
											experience: 	object[i].EXPERIENCE,
											capability:		object[i].CAPABILITY,
											coclcd:		object[i].CO_CL_CD
									      });
								}
	      					}
	                      },
	                      onerr: function(res){
	                      	alert(res.resultMsg);
	                      }
	                  });
                },
                
                abort: function() {
                    if (SchShareApp.jqgrid.gridXhr) {
                        SchShareApp.jqgrid.gridXhr.abort();
                        SchShareApp.jqgrid.gridXhr = null;
                    }
                }
            },		// jqGrid
  
            event: {
                init: function() {
                    // 조회
                    $('#searchListBtn').on('click', function() {
                    	SchShareApp.jqgrid.search();
                    });

                    // 이름검색
                    $('#name').on('keydown', function(e) {
                        if (e.keyCode === 13)   SchShareApp.jqgrid.search();
                    });
                }
            }
    };

    </script>
 
</head>
<body>
	<div id="headLine">
	  <h3>사람찾기</h3>
	</div>

	<div id="condition">
        소속 : <select id = "company"><option value="">전체</option></select>
        등급 : <select id = "grade"><option value="">전체</option></select>
        MID : <select id = "mid"><option value="">전체</option></select>
        고객사 : <select id = "coclcd"><option value="">전체</option></select><br>
        이름 : <input type="text" id="name">
  		역량 : <input type="text" id="capa">
  		수행PJT : <input type="text" id="experience">
  		<input type="button" id="searchListBtn" value="조회">
	</div>
<br><br>

    <table id="gridList"></table>
    <!-- <div id="gridPager"></div> -->
	<div id="paginate"></div>

</body>
 
</html>
