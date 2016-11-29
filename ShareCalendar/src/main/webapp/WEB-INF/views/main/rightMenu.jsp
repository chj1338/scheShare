<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page session="false" %>
<html>
<head>
	<title>메뉴</title>

	<style type="text/css">
	    .weaApp {
	    	position: absolute;
	    	top:3pt;
	    }
	</style>

</head>

<body>
	<div class="weaApp">
		<a href="/rss/rssWeaSelectM.do" target="appbody" class="no-uline">
			<iframe name="weaAppFrame" src="/main/weatherApple.do" style="height:200px;width:100%;overflow:hidden;border:0;"></iframe>
		</a>
	</div>
	
</body>
</html>
