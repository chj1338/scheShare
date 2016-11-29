<%@ include file="/WEB-INF/views/include/common.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
<head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8">  
<title>일정공유 시스템</title>

  <style type="text/css">
    /* 링크에서 밑줄 없애기 */
    a.no-uline { text-decoration:none ; color : #000000 }
    /* 마우스 지나갈 때만 삭제 + 강제로 없애기 */
    a.noul:hover { text-decoration:none !important }
    html {
      font-family: sans-serif;
      font-size: 80%;
      -webkit-text-size-adjust: 100%;
          -ms-text-size-adjust: 100%;
    }
    .header
    {
      position : absolute;
      width: 100%;
      height : 10% ;
      color : white;
      background-color : #F58023 ;
    }
    .menu_area
    {
      top : 8%;
      left : 0;
      position : absolute;
      height : 90%;
      width : 12%;
      color : black;
      background-color : #FFF;
    }
    .right_menu_area
    {
      top : 8%;
      right : 0;
      position : absolute;
      height : 90%;
      width : 12%;
      color : black;
      background-color : #FFF;
    }
    .userinfo_area
    {
    	vertical-align : top;
      font-size : 120%;
      font-weight : bold;
      text-align : right;
    }
    .content_area
    {
      top : 8%;
      left : 12%;
      position : absolute;
      height : 90%;
      width : 76%;
      color : black;
      background-color : #FFF;
    }
     body 
     { 
        background-color : #FFF;
        margin : 0;   
     }
     .table
     {
        width : 100%;
        height : 70px;
        border-style : solid;
        border-width : 0px;
     }
     .td_10
     {
        width : 10%;
        text-align : left;
        vertical-align : middle;
     }
     .td_85
     {
        width : 85%;
        text-align : right;
        vertical-align : bottom;
        padding-bottom : 11px;
     }
     .td_5
     {
        width : 5%;
        text-align : left;
        vertical-align : bottom;
        padding-bottom : 10px;
     }
  </style>  

</head>      
            
<body style="overflow:hidden;">
	<form id="logoutForm" name="logoutForm" method="POST" action="./logout.do">
		<div id="f_header" class="header" >
			<div id="userinfo" class="userinfo_area">
			
				<table class="table">
					<tr>
						<td class="td_10"><a href="/mainBoard"><img width="65px" height="65px" src="/resources/images/android2.png"/></a></td>
						<td class="td_85">${userNm}님 환영합니다.[ 최종접속일자 : ${lastLoginDt} ]</td>
						<td class="td_5"><input class="ui-state-default ui-corner-all button" type="submit"  value="로그아웃" /></td>
					</tr>
				</table>
								
			</div>		
		</div>
	    <div id="menu" class="menu_area" ><iframe name="menu" src="${ctx}/leftMenu" style="height:100%;width:100%;overflow:hidden;border:0;"></iframe></div>
	    <div id="content" class="content_area"><iframe name="appbody" src="${ctx}/body" style="height:100%;width:100%;overflow:hidden;border:0;"></iframe></div>
	    <div id="right_menu" class="right_menu_area" ><iframe name="right_menu" src="${ctx}/rightMenu" style="height:100%;width:100%;overflow:hidden;border:0;"></iframe></div>
	</form>
</body>

</html>
