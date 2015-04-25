<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
	<%
		//获得request域中属性的值，然后显示在页面上
		String massage = "";
		String msg = (String) request.getAttribute("msg");
		if (msg != null)
			massage = msg;
	%>
</head>
<body>
${msg } 
</body>
</html>