<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
/* 由于inout与img标签在一起的时候，img总是高一个头，用此方法设置同样的高度。 */
input, img {
	vertical-align: middle;
}

table {
	margin: auto;
	font-family: 微软雅黑;
}

.btn {
	width: 75px;
	height: 30px;
}

.txt {
	width: 150px;
	height: 25px;
}

.code {
	width: 75px;
	height: 25px;
}
</style>
<script type="text/javascript">
	/*换验证码
	1、获取img的id
	2、修改img的src属性，即重新访问VerifyCodeServlet
	 */
	function changeCode() {
		var img = document.getElementById("imgCode");
		//修改img的src属性，注意浏览器有缓存功能，因此在后面加一个参数表示每次请求的不是唯一的
		img.src = "${pageContext.request.contextPath}/verify?time="
				+ new Date().getTime();
	}
</script>
</head>
<body onload="changeCode()">
  <!-- 页面加载完后就开始显示验证码，避免session最开始没有存放验证码字符 -->
  <%-- <%
		/*
		读取名为user的Cookie，然后把读取到Cookie显示在用户名框中
		 */
		String user = "";
		Cookie[] cookies = request.getCookies();
		if (cookies != null)
			for (Cookie c : cookies)
				if ("user".equals(c.getName()))
					user = c.getValue();
	%>
	<%
		//获得request域中属性的值，然后显示在页面上
		String massage = "";
		String msg = (String) request.getAttribute("msg");
		if (msg != null)
			massage = msg;
	%> --%>
  <form action="${pageContext.request.contextPath }/login" method="post">
    <table>
      <tr>
        <td>用户名： <input type="text" name="user" value="${user }" class="txt" />
        </td>
      </tr>
      <tr>
        <td>密&nbsp;&nbsp;&nbsp;&nbsp;码： <input type="password" name="pwd" class="txt" />
        </td>
      </tr>
      <tr>
        <td>验证码： <input type="text" name="verifyCode" class="code" /> <img alt="验证码" src="${pageContext.request.contextPath }/verify"
          id="imgCode" onclick="changeCode()"
        />
        </td>
      </tr>
      <tr>
        <td><input type="submit" value="登录" class="btn" /> <font color="red"><b>${msg } </b> </font></td>
      </tr>
    </table>
  </form>
</body>
</html>