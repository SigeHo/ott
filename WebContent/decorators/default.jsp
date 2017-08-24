<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>

<html lang="en">
<head>
<title><decorator:title/></title>
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Expires" content="0" />

<link rel="stylesheet" type="text/css" href="${ctx}/resources/jquery-easyui-1.5.2/themes/bootstrap/easyui.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/resources/jquery-easyui-1.5.2/themes/icon.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/base.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/demo.css" />
<script type="text/javascript" src="${ctx}/resources/jquery-easyui-1.5.2/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/jquery-easyui-1.5.2/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/common.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/easyui_extend.js"></script>
<decorator:head />
</head>

<body<decorator:getProperty property="body.id" writeEntireProperty="true"/><decorator:getProperty property="body.class" writeEntireProperty="true"/>>
<decorator:body/>
</body>
</html>
