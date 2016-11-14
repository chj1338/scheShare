<%@ include file="/WEB-INF/views/include/common.jsp" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>

<!DOCTYPE html>
<html>
    <head>
        <title><decorator:title default="SchShare" /></title>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <link rel="stylesheet" href="${ctx}/resources/css/jquery/jquery-ui-1.9.1.custom.css">
        <link rel="stylesheet" href="${ctx}/resources/css/jquery/ui.jqgrid.css">
        <link rel="stylesheet" href="${ctx}/resources/css/jquery/ui.multiselect.css">
        <link rel="stylesheet" href="${ctx}/resources/css/common.css">
        
        <script type="text/javascript" src="${ctx}/resources/js/html5.js"></script>
        <!--[if lt IE 8]>
        <script src="${ctx}/resources/js/IE8.js"></script>
        <![endif]-->
        <!--[if lt IE 7]>
        <script src="${ctx}/resources/js/IE7.js"></script>
        <![endif]-->
        <script type="text/javascript" src="${ctx}/resources/js/jquery/jquery-1.8.2.min.js"></script>
        <script type="text/javascript" src="${ctx}/resources/js/jquery/jquery-ui-1.9.1.custom.min.js"></script>
        <script type="text/javascript" src="${ctx}/resources/js/jquery/grid/grid.locale-en.js"></script>
        <script type="text/javascript" src="${ctx}/resources/js/jquery/grid/jquery.jqGrid.min.js"></script>
        <script type="text/javascript" src="${ctx}/resources/js/jquery/jquery.ui.core.js"></script>
        <script type="text/javascript" src="${ctx}/resources/js/jquery/jquery.ui.widget.js"></script>
        <script type="text/javascript" src="${ctx}/resources/js/jquery/jquery.ui.progressbar.js"></script>
        <script type="text/javascript" src="${ctx}/resources/js/json2.js"></script>
        <script type="text/javascript" src="${ctx}/resources/js/common.js"></script>
        <script type="text/javascript" src="${ctx}/resources/js/jquery/jquery.form.js"></script>
 
        <script type="text/javascript">
        
            $(document).ready(function() {
                try {
                    if (typeof SchShareApp === 'undefined') return false; 
                    
                    SchShareApp.pageInit();
                    
                    // 달력 기본 설정 세팅
                    SchShareObj.date.setDatePicker();
                } catch (e) {
                    log(e.toString());
                }
            });
    
        </script>
        
        <decorator:head />
    </head>
    <body>
        <!-- left layout -->
        
        <!-- /left layout -->
        
        <!--main content -->
        <decorator:body />
        <!--/main content-->
    
    </body>
</html>
