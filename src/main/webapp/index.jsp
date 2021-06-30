<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>首页</title>
<script src="/js/jquery.min.js"></script>
</head>
<script type="text/javascript">
	//异步请求接口
	// $.ajax({
	// 	url: 'http://www.80049.vip/whiteIP/illegal',
	// 	type: 'POST',
	// 	data:{id:1},
	// 	success:function(response) {
	// 		if (response.status == 0){
	// 			window.location.href=response.location;
	// 		}
	// 	}
	// });
</script>
<body>
	<jsp:forward page="/index/login" />
</body>
</html>
