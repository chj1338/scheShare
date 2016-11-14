<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*" %>

<%
	String clientIp = request.getRemoteHost();
%>

<HTML>
<HEAD>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8">
  <title>Calendar Sharing System</title>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <style> 
    .padding {padding: 0.4em; margin: 10px 30px -10px 0px; }
    #effect { width: 280px; height: 170px; padding: 0.4em; position: relative; }
    #effect h3 { margin: 0; padding: 0.4em; text-align: center; }
    #etc { width: 280px; height: 180px; padding: 0.4em; position: relative; }
    #center { position:absolute; top:30%; left:50%; width:400px; height:500px; overflow:hidden; margin-top:-140px; margin-left:-140px;} 
    body {font-family: 맑은 고딕, 돋움, 굴림;}
    .button { padding: 0.4em 1em; text-decoration: none; width: 100px; left: 500px; margin: 20px 35px 0px 0px;}
    p {font-size:11px;}
      
    .ui-widget { font-family: Arial,sans-serif; font-size: 1.1em; }
    .ui-widget .ui-widget { font-size: 1em; }
    .ui-widget input, .ui-widget select, .ui-widget textarea, .ui-widget button { font-family: Arial,sans-serif; font-size: 1em; }
    .ui-widget-content { border: 1px solid #eeeeee; background: #ffffff; color: #333333; }
    .ui-widget-content a { color: #333333; }
    .ui-widget-header { border: 1px solid #e3a1a1; background: #F000FF; color: #ffffff; font-weight: bold; }
    .ui-widget-header a { color: #ffffff; }
    
    .ui-state-default, .ui-widget-content .ui-state-default, .ui-widget-header .ui-state-default { border: 1px solid #d8dcdf; background: #eeeeee; font-weight: bold; color: #004276; }
    .ui-state-default a, .ui-state-default a:link, .ui-state-default a:visited { color: #004276; text-decoration: none; }
    .ui-state-hover, .ui-widget-content .ui-state-hover, .ui-widget-header .ui-state-hover, .ui-state-focus, .ui-widget-content .ui-state-focus, .ui-widget-header .ui-state-focus { border: 1px solid #cdd5da; background: #f6f6f6; font-weight: bold; color: #111111; }
    .ui-state-hover a, .ui-state-hover a:hover, .ui-state-hover a:link, .ui-state-hover a:visited { color: #111111; text-decoration: none; }
    .ui-state-active, .ui-widget-content .ui-state-active, .ui-widget-header .ui-state-active { border: 1px solid #eeeeee; background: #ffffff; font-weight: bold; color: #cc0000; }
    .ui-state-active a, .ui-state-active a:link, .ui-state-active a:visited { color: #cc0000; text-decoration: none; }
    
    .ui-corner-all, .ui-corner-top, .ui-corner-left, .ui-corner-tl { -moz-border-radius-topleft: 6px; -webkit-border-top-left-radius: 6px; -khtml-border-top-left-radius: 6px; border-top-left-radius: 6px; }
    .ui-corner-all, .ui-corner-top, .ui-corner-right, .ui-corner-tr { -moz-border-radius-topright: 6px; -webkit-border-top-right-radius: 6px; -khtml-border-top-right-radius: 6px; border-top-right-radius: 6px; }
    .ui-corner-all, .ui-corner-bottom, .ui-corner-left, .ui-corner-bl { -moz-border-radius-bottomleft: 6px; -webkit-border-bottom-left-radius: 6px; -khtml-border-bottom-left-radius: 6px; border-bottom-left-radius: 6px; }
    .ui-corner-all, .ui-corner-bottom, .ui-corner-right, .ui-corner-br { -moz-border-radius-bottomright: 6px; -webkit-border-bottom-right-radius: 6px; -khtml-border-bottom-right-radius: 6px; border-bottom-right-radius: 6px; }
  </style>
  
</HEAD>

<body>

<div id="center" class="center">

	<div id="effect" class="ui-widget-content ui-corner-all">
	  <h3 class="ui-widget-header ui-corner-all">일정공유 시스템 로그인</h3>
	    
	  <form id="loginForm" name="loginForm" method="POST" action="/sysLoginProc.do" target="mainBoard">
	<!--  <H3>◎ 임시 로그인 (테스트용) ◎</H3> -->    
	      <div class="padding" align="right">아 이 디 : <input type="text" name="loginId" id="loginId" value="UK330" ></input></div>
	      <div class="padding" align="right">비밀번호 : <input type="password" name="loginPW" id="loginPW" value="" ></input></div>
	      <div align="right"><input class="ui-state-default ui-corner-all button" type="submit"  value="로그인" /></div>
	      <input type="input" name="clientIp" id="clientIp" value="<%=clientIp %>"  style="visibility:hidden">
	  </form>     
	</div>

</div>  

  <script type="text/javascript">
	var sysMsg = '${SYS_MSG}';
	if(sysMsg != "") {
  		alert(sysMsg);
	}
</script>

</body>

</html>