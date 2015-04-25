<%@ page language="java" contentType="text/xml; charset=UTF-8" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><?xml version="1.0" encoding="UTF-8"?>
<taskInf><c:forEach var="list" items="${list }">
<inf>
	  <ndjid>${list.ndjid }</ndjid>
	  <sjkid>${list.sjkid }</sjkid>
	  <nrwid>${list.nrwid }</nrwid>
	  <sryid>${list.sryid }</sryid>
	  <srymc>${list.srymc }</srymc>
	  <srwlx>${list.srwlx }</srwlx>
	  <sckid>${list.stockBean.sckid }</sckid>
	  <sckmc>${list.stockBean.sckmc }</sckmc>
	  <sckid_desc>${list.sckid_desc }</sckid_desc>
	  <sckmc_desc>${list.sckmc_desc }</sckmc_desc>
	  <swldwid>${list.partnerBean.swldwid }</swldwid>
	  <sdwmc>${list.partnerBean.sdwmc }</sdwmc>
	  <sbz>${list.sbz }</sbz>
	  <sywymc>${list.sywymc }</sywymc>
	  <sfhtid>${list.sfhtid }</sfhtid>
	  <nrwzt>${list.nrwzt }</nrwzt>
	  <dsqsj>${list.dsqsj }</dsqsj>
	  <dqrsj>${list.dqrsj }</dqrsj>
	</inf>
  </c:forEach></taskInf>