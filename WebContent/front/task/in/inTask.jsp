<%@ page language="java" contentType="text/xml; charset=UTF-8" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><?xml version="1.0" encoding="UTF-8"?>
<taskInf>
	<ndjid>${taskBean.ndjid }</ndjid>
	<sjkid>${taskBean.sjkid }</sjkid>
	<nrwid>${taskBean.nrwid }</nrwid>
	<sryid>${taskBean.sryid }</sryid>
	<srymc>${taskBean.srymc }</srymc>
	<srwlx>${taskBean.srwlx }</srwlx>
	<sckid>${taskBean.stockBean.sckid }</sckid>
	<sckmc>${taskBean.stockBean.sckmc }</sckmc>
	<swldwid>${taskBean.partnerBean.swldwid }</swldwid>
	<sdwmc>${taskBean.partnerBean.sdwmc }</sdwmc>
	<sbz>${taskBean.sbz }</sbz>
	<srwid_last>${taskBean.srwid_last }</srwid_last>
	<nrwzt>${taskBean.nrwzt }</nrwzt>
    <c:forEach var="taskItem" items="${taskBean.upItemTaskList }" >
	<inf>
		<nrwid>${taskItem.nrwid }</nrwid>
		<nhh>${taskItem.nhh }</nhh>
		<nspid>${taskItem.drugBean.nspid }</nspid>
		<sspbm>${taskItem.drugBean.sspbm }</sspbm>
		<sspmc>${taskItem.drugBean.sspmc }</sspmc>
		<sspgg>${taskItem.drugBean.sspgg }</sspgg>
		<sspcd>${taskItem.drugBean.sspcd }</sspcd>
		<njlgg>${taskItem.drugBean.njlgg }</njlgg>
		<sjldw>${taskItem.drugBean.sjldw }</sjldw>
		<nsfjg>${taskItem.drugBean.nsfjg }</nsfjg>
		<dscrq>${taskItem.dscrq }</dscrq>
		<dyxqz>${taskItem.dyxqz }</dyxqz>
		<sph>${taskItem.sph }</sph>
		<n4jhsl>${taskItem.n4jhsl }</n4jhsl>
		<n4zjsl>${taskItem.n4zjsl }</n4zjsl>
		<n4lssl>${taskItem.n4lssl }</n4lssl>
		<shwid>${taskItem.shwid }</shwid>
	</inf>
    </c:forEach>
</taskInf>