<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
body {
	margin-top: 50px;
	text-align: center;
}
</style>
<script type="text/javascript">
	function downloadApp() {
		location.href = "${pageContext.request.contextPath }/download/SoQuery.apk";
	}
</script>
</head>
<body>
	<input type="image" src="../images/download_app.png"
		onclick="downloadApp()" />
	<br /> Android版
</body>
</html>