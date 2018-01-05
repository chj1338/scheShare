<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page session="true" %>
    <%
    	String loginId = (String)session.getAttribute("loginId");
//		String clientIp = request.getRemoteHost();
		
//		System.out.println("=== loginId : " + loginId);
//		System.out.println("=== clientIp : " + clientIp);
    %>

<html>
<head>
	<title>메뉴</title>

	<style type="text/css">
		/* 링크에서 밑줄 없애기 */
		a.no-uline { text-decoration:none ; color : #000000 }
		/* 마우스 지나갈 때만 삭제 + 강제로 없애기 */
		a.noul:hover { text-decoration:none !important }
	    td.position:hover {
	    	background-color : #A8F7FF !important;
	    }
	    td.position {
	    	valign : top;
	    	text-align : left; 
	    	font-size: 80%;
	    	padding-left : 30px;
	    }
	    img { border:0px solid black;}
	    .menu_title
	    {
	      height : 5%;
	      width : 100% ;
	      color : black;
	      background-color : #E8F5FF;
	      text-align : left;
	    }  
	    html {
		  font-family: sans-serif;
		  font-size: 90%;
		  -webkit-text-size-adjust: 100%;
		      -ms-text-size-adjust: 100%;
		}  
	    body { background-color : #F9FFFF}   
	</style>

</head>

<body>
	<div id="f_title1" class="menu_title" ><h3>*&nbsp;메  뉴&nbsp;*</h3></div>
	<table width="80%" border="0" height="100%">

<% 
	if(loginId.equals("test") ) {
%>	
	
		<tr><td style="font-weight : bold">
			<a onclick="this.nextSibling.style.display=(this.nextSibling.style.display=='none')?'block':'none';" href="javascript:void(0)"  class="no-uline">	◎ 통신개발1팀
			</a><div style="DISPLAY: block">
				<table>
					<tr><td class="position" style="font-weight : normal"><a href="/main/sinmungoM.do" target="appbody" class="no-uline">신문고</a></td></tr>
				</table>
			</div>
		</td></tr>
		<tr><td>&nbsp;</td></tr>
<% 
	} else {
%>
		<tr><td style="font-weight : bold">
			<a onclick="this.nextSibling.style.display=(this.nextSibling.style.display=='none')?'block':'none';" href="javascript:void(0)"  class="no-uline">	◎ 게시판
			</a><div style="DISPLAY: block">
				<table>
					<tr><td class="position" style="font-weight : normal"><a href="/SmartEditor2.do" target="appbody" class="no-uline">스마트에디터2</a></td></tr>
				</table>
			</div>
		</td></tr>
		<tr><td>&nbsp;</td></tr>
	
		<tr><td style="font-weight : bold">
			<a onclick="this.nextSibling.style.display=(this.nextSibling.style.display=='none')?'block':'none';" href="javascript:void(0)"  class="no-uline">	◎ 일정관리
			</a><div style="DISPLAY: block">
				<table>
					<tr><td class="position" style="font-weight : normal"><a href="/schListM.do" target="appbody" class="no-uline">일정조회</a></td></tr>
					<tr><td class="position" style="font-weight : normal"><a href="/schInsertM.do" target="appbody" class="no-uline">일정등록</a></td></tr>
					<tr><td class="position" style="font-weight : normal"><a href="/schDutyM.do" target="appbody" class="no-uline">일정달력</a></td></tr>
				</table>
			</div>
		</td></tr>
		<tr><td>&nbsp;</td></tr>

		<tr><td style="font-weight : bold">
				<a onclick="this.nextSibling.style.display=(this.nextSibling.style.display=='none')?'block':'none';" href="javascript:void(0)"  class="no-uline">	◎ 기타
				</a><div style="DISPLAY: block">
					<table>
						<tr><td class="position" style="font-weight : normal"><a href="/map/googleMap.do" target="appbody" class="no-uline">구글지도찾기</a></td></tr>
						<tr><td class="position" style="font-weight : normal"><a href="/map/naverMap.do" target="appbody" class="no-uline">네이버지도찾기</a></td></tr>
						<tr><td class="position" style="font-weight : normal"><a href="/lot/lotSelectM.do" target="appbody" class="no-uline">당신의행운</a></td></tr>
						<tr><td class="position" style="font-weight : normal"><a href="/searchArticleList.do" target="appbody" class="no-uline">기사검색</a></td></tr>
						<tr><td class="position" style="font-weight : normal"><a href="/rss/rssSelectM.do" target="appbody" class="no-uline">RSS뉴스</a></td></tr>
						<tr><td class="position" style="font-weight : normal"><a href="/rss/rssWeaSelectM.do" target="appbody" class="no-uline">RSS날씨</a></td></tr>
						<tr><td class="position" style="font-weight : normal"><a href="/main/sqlTestM.do" target="appbody" class="no-uline">SQL 테스트</a></td></tr>
						<tr><td class="position" style="font-weight : normal"><a href="/etc/manCount" target="appbody" class="no-uline">사람찾기</a></td></tr>
						<tr><td class="position" style="font-weight : normal"><a href="/bible/bibleViewM.do" target="appbody" class="no-uline">Bible</a></td></tr>
						<tr><td class="position" style="font-weight : normal"><a href="/bible/bibleReadM.do" target="appbody" class="no-uline">Bible Read</a></td></tr>
					</table>
				</div>
		<tr><td>&nbsp;</td></tr>

		<tr><td style="font-weight : bold">
				<a onclick="this.nextSibling.style.display=(this.nextSibling.style.display=='none')?'block':'none';" href="javascript:void(0)"  class="no-uline">	◎ Shop
				</a><div style="DISPLAY: none">
					<table>
						<tr><td class="position" style="font-weight : normal"><a href="/shopListM.do" target="appbody" class="no-uline">상품리스트조회</a></td></tr>
						<tr><td class="position" style="font-weight : normal"><a href="/shopGridM.do" target="appbody" class="no-uline">상품그리드조회</a></td></tr>
						<tr><td class="position" style="font-weight : normal"><a href="/shopInsertM.do" target="appbody" class="no-uline">상품등록</a></td></tr>
					</table>
				</div>
		</td></tr>
<%
	}
%>		
	</table>

</body>
</html>
