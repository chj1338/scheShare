<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page session="false" %>
<html>
<head>
	<title>바디</title>
	
	<style type="text/css">
    html {
      background-image:url("/resources/images/atlantis5.jpg");
/*       background-size: cover; */
      background-size:70%;
      background-repeat:no-repeat;
    }
    
	  .rssApp {
	    	position: absolute;
	    	top:5pt;
	    	overflow: hidden;
	    }
    </style>

</head>
<body>

	<div id='mainTable'>
		<table width="50%" border="0">
			<tr><td align="center"><h1>일정 공유 포털</h1></td></tr>
			<tr>
				<td align="center"><img src="/resources/images/10.gif"></td>
				<td align="center"><img src="/resources/images/20.gif"></td>
				<td align="center"><img src="/resources/images/30.gif"></td>
			</tr>
		<!-- 	<tr><td align="center" colspan="3"><img src="/resources/images/atlantis5.jpg"></td></tr> -->
		</table>
	</div>

	<div class="rssApp">
		<iframe name="weaAppFrame" src="/main/rssApple.do" style="width:870px;height:605px;overflow:hidden;border:0;"></iframe>
	</div>

</body>
</html>
