<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>403 - No Permission</title>
    <%@ include file="/common/meta.jsp" %>
    <script type="text/javascript">
		if (top.location !== self.location) {
			top.location=self.location;
		}
	</script>
</head>

<body>
<div style="text-align:center">
	<div><span style="font-size:120px">403</span></div>
	<span>Sorry! You don't have the permission</span>
	
	<div style"width:200px; background:red;">
		<a href="javascript:window.history.back(-1);" style="padding-right:50px">Back</a> 	
		<a href="${ctx}/main.html" style="padding-left:50px"">Home</a>
	</div>
</div>
</body>
</html>
