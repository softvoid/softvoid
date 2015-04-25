<%@ page language="java" contentType="text/xml; charset=UTF-8" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><?xml version="1.0" encoding="UTF-8"?>
<pos><c:forEach var="amount" items="${list }" >
	<inf count="${amount.count }">
		<nspid>${amount.drugBean.nspid }</nspid>
		<sspbm>${amount.drugBean.sspbm }</sspbm>
		<sspmc>${amount.drugBean.sspmc }</sspmc>
		<sspgg>${amount.drugBean.sspgg }</sspgg>
		<sspcd>${amount.drugBean.sspcd }</sspcd>
		<njlgg>${amount.drugBean.njlgg }</njlgg>
		<sjldw>${amount.drugBean.sjldw }</sjldw>
		<sph>${amount.sph }</sph>
		<dscrq>${amount.dscrq }</dscrq>
		<dyxqz>${amount.dyxqz }</dyxqz>
		<n4hwsl>${amount.n4hwsl }</n4hwsl>
		<n4kcksl>${amount.n4kcksl }</n4kcksl>
		<nzj>${amount.nzj }</nzj>
		<nls>${amount.nls }</nls>
		<shwid>${amount.shwid }</shwid>
		<sckid>${amount.stockBean.sckid }</sckid>
		<sckmc>${amount.stockBean.sckmc }</sckmc>
		<c:forEach items="${stockList }" var="stock">
		<stock>
			<ssckid>${stock.sckid }</ssckid>
			<ssckmc>${stock.sckmc }</ssckmc>
		</stock>
	  </c:forEach>
	</inf></c:forEach>
</pos>
