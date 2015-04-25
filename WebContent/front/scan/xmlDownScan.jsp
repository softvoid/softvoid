<%@ page language="java" contentType="text/xml; charset=UTF-8" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><?xml version="1.0" encoding="UTF-8"?>
<OrderItem><c:forEach var="item" items="${list }">
	<item>
		<norderid>${item.norderid }</norderid>
		<nspid>${item.drugBean.nspid }</nspid>
		<sspbm>${item.drugBean.sspbm }</sspbm>
		<sspmc>${item.drugBean.sspmc }</sspmc>
		<sspcd>${item.drugBean.sspcd }</sspcd>
		<sspgg>${item.drugBean.sspgg }</sspgg>
		<sjldw>${item.drugBean.sjldw }</sjldw>
		<njlgg>${item.drugBean.njlgg }</njlgg>
		<nsfjg>${item.drugBean.nsfjg }</nsfjg>
		<sdwmc>${item.partnerBean.sdwmc }</sdwmc>
		<snfhzcw>${item.fhtBean.snfhzcw }</snfhzcw>
		<snfhrmc>${item.fhtBean.snfhrmc }</snfhrmc>
		<ncount>${item.ncount }</ncount>
		<sjkid>${item.sjkid }</sjkid>
		<sph>${item.sph }</sph>
		<dscrq>${item.dscrq }</dscrq>
		<dyxqz>${item.dyxqz }</dyxqz>
		<nls>${item.nls }</nls>
		<nzj>${item.nzj }</nzj>
		<njhsl>${item.njhsl }</njhsl>
		<nhh>${item.nhh }</nhh>
		<sorderlx>${item.sorderlx }</sorderlx>
		<sywymc>${item.sywymc }</sywymc>
	</item></c:forEach>
</OrderItem>
