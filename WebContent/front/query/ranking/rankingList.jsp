<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><?xml version="1.0" encoding="UTF-8"?>
<rankingList>
   <c:forEach var="ranking" items="${rankingList }" >
    <rankingBean>
      <position>${ranking.position }</position>
      <sjtms>${ranking.sjtms }</sjtms>
      <sjjs>${ranking.sjjs }</sjjs>
      <xjtms>${ranking.xjtms }</xjtms>
      <xjjs>${ranking.xjjs }</xjjs>
      <bhtms>${ranking.bhtms }</bhtms>
      <bhjs>${ranking.bhjs }</bhjs>
      <zdbhtms>${ranking.zdbhtms }</zdbhtms>
      <zdbhjs>${ranking.zdbhjs }</zdbhjs>
      <snickname>${ranking.myselfBean.snickname }</snickname>
      <sryid>${ranking.myselfBean.empBean.sryid }</sryid>
      <srymc>${ranking.myselfBean.empBean.srymc }</srymc>
      <bimg>${ranking.myselfBean.bimg }</bimg>
      <flag>${ranking.flag }</flag>
	</rankingBean>
  </c:forEach>
</rankingList>