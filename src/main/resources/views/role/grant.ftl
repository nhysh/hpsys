<html>
<head>
	<#assign ctx=request.contextPath/>
	<link rel="stylesheet" href="${ctx}/js/zTree_v3-3.5.32/css/zTreeStyle/zTreeStyle.css" type="text/css">
	<script type="text/javascript" src="${request.contextPath}/lib/jquery-3.4.1/jquery-3.4.1.min.js"></script>
	<script type="text/javascript" src="${ctx}/js/zTree_v3-3.5.32/js/jquery.ztree.core.js"></script>
	<script type="text/javascript" src="${ctx}/js/zTree_v3-3.5.32/js/jquery.ztree.excheck.js"></script>
	<script type="text/javascript">
		var ctx="${ctx}";
	</script>
</head>
<body>
<div id="test1" class="ztree"></div>
<input id="roleId" value="${roleId!}" type="hidden">
<script type="text/javascript" src="${ctx}/js/role/grant.js"></script>
</body>
</html>