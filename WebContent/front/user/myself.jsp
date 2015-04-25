<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><?xml version="1.0" encoding="UTF-8"?>
<myself> <c:forEach items="${list }" var="myself">
  <sryid>${myself.empBean.sryid }</sryid>
  <suid>${myself.suid }</suid>
  <snickname>${myself.snickname }</snickname>
  <sgender>${myself.sgender }</sgender>
  <nage>${myself.nage }</nage>
  <sadd>${myself.sadd }</sadd>
  <nlevel>${myself.nlevel }</nlevel>
  <nvip>${myself.nlevel }</nvip>
  <!-- 文件路径 -->
  <bimg>${imagePath }</bimg>
  <smemo>${myself.smemo }</smemo>
</c:forEach> </myself>