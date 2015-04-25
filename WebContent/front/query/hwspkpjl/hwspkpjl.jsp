<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>货位帐页查询</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my currentPage">
<link rel="stylesheet" type="text/css" href="css/query.css">
</head>
<body>
  <a href="<c:url value='/index.jsp' />">首页</a>
  <h3 align="center">货位帐页查询</h3>
  <form action="<c:url value='/hwspkpjlServlet' />" method="get">
    <input type="hidden" name="method" value="queryHwspkpjl" /> 商品编码：<input type="text" name="sspbm" value="${sspbm }" />
    &nbsp;&nbsp;&nbsp;&nbsp; 批号：<input type="text" name="sph" value="${sph }"> <input type="submit" value="查询" />
  </form>
  <div>
    <table align="center" width="100%" cellpadding="0px" cellspacing="0px">
      <tr>
        <th>行号</th>
        <th>更新时间</th>
        <th>仓库名称</th>
        <th>货位编码</th>
        <th>商品编码</th>
        <th>商品名称</th>
        <th>生产厂家</th>
        <th>计量规格</th>
        <th>商品规格</th>
        <th>批号</th>
        <th>变化数量</th>
        <th>货位数量</th>
        <th>可出库数量</th>
        <th>备注</th>
        <th>接口单号</th>
      </tr>
      <c:forEach items="${pageList.beanList }" var="hwkpjl">
        <tr>
          <td>${hwkpjl.line }</td>
          <td>${hwkpjl.dgxrq }</td>
          <td>${hwkpjl.ckxxBean.sckmc }</td>
          <td>${hwkpjl.shwid }</td>
          <td>${hwkpjl.drugBean.sspbm }</td>
          <td>${hwkpjl.drugBean.sspmc }</td>
          <td>${hwkpjl.drugBean.sspcd }</td>
          <td>${hwkpjl.drugBean.njlgg }</td>
          <td>${hwkpjl.drugBean.sspgg }</td>
          <td>${hwkpjl.sph }</td>
          <td>${hwkpjl.n4bhsl }</td>
          <td>${hwkpjl.n4hwsl }</td>
          <td>${hwkpjl.n4kcksl }</td>
          <td>${hwkpjl.sbz }</td>
          <td>${hwkpjl.sjkid }</td>
        </tr>
      </c:forEach>
    </table>
  </div>
  <br />
  <center>
    <c:if test="${not empty pageList }">
  

    第${pageList.currentPage }页/共${pageList.totalPage }页
    <c:choose>
        <c:when test="${pageList.currentPage eq 1 }">
                    首页
      </c:when>
        <c:otherwise>
          <a href="${pageList.url }&currentPage=1">首页</a>
        </c:otherwise>
      </c:choose>
      <!-- 当前页大于1时才显示上一页 -->
      <c:choose>
        <c:when test="${pageList.currentPage <= 1 }">
                    上一页
      </c:when>
        <c:otherwise>
          <a href="${pageList.url }&currentPage=${pageList.currentPage-1 }">上一页</a>
        </c:otherwise>
      </c:choose>
      <!-- 设置显示页数，参考百度的页数。注意jsp内部使用注释必须使用jsp注释，否则错误！ -->
      <c:choose>
        <%-- 当总页数小于或等于10时，首页就显示1，尾页有多少小时多少 --%>
        <c:when test="${pageList.totalPage <= 10 }">
          <!-- 设置变量begin和end，该变量默认是page域，因此可以在该页面中任何地方使用 -->
          <c:set var="begin" value="1" />
          <c:set var="end" value="${pageList.totalPage }" />
        </c:when>
        <c:otherwise>
          <%-- 当总页数大于10时，通过公式计算出begin和end --%>
          <c:set var="begin" value="${pageList.currentPage-5 }" />
          <c:set var="end" value="${pageList.currentPage+4 }" />
          <%-- 为了避免begin是负数，因此做一个判断，只要begin小于1，还是显示1 --%>
          <c:if test="${begin < 1 }">
            <c:set var="begin" value="1" />
            <c:set var="end" value="10" />
          </c:if>
          <%-- 为了避免end大于pageList.totalPage，因此做一个判断，只要超过end还是显示end --%>
          <c:if test="${end > pageList.totalPage }">
            <c:set var="begin" value="${pageList.totalPage-9 }" />
            <c:set var="end" value="${pageList.totalPage }" />
          </c:if>
        </c:otherwise>
      </c:choose>
      <c:forEach var="i" begin="${begin }" end="${end }">
        <c:choose>
          <c:when test="${i eq pageList.currentPage }">
          [${i }]
        </c:when>
          <c:otherwise>
            <a href="${pageList.url }&currentPage=${i }">[${i }]</a>
          </c:otherwise>
        </c:choose>
      </c:forEach>
      <!-- 当前页小于总共页数时才显示下一页 -->
      <c:choose>
        <c:when test="${pageList.currentPage >= pageList.totalPage }">
                    下一页
      </c:when>
        <c:otherwise>
          <a href="${pageList.url }&currentPage=${pageList.currentPage+1 }">下一页</a>
        </c:otherwise>
      </c:choose>
      <c:choose>
        <c:when test="${pageList.currentPage eq pageList.totalPage }">
                    尾页
      </c:when>
        <c:otherwise>
          <a href="${pageList.url }&currentPage=${pageList.totalPage }">尾页</a>
        </c:otherwise>
      </c:choose>
    </c:if>
  </center>
</body>
</html>
