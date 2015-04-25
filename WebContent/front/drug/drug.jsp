<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><?xml version="1.0" encoding="UTF-8"?>
<drugInf>
   <c:forEach var="drug" items="${listDrug }" >
    <inf>
      <nspid>${drug.nspid }</nspid>
      <sspbm>${drug.sspbm }</sspbm>
      <sspmc>${drug.sspmc }</sspmc>
      <sspgg>${drug.sspgg }</sspgg>
      <sspcd>${drug.sspcd }</sspcd>
      <ssptm>${drug.ssptm }</ssptm>
      <njlgg>${drug.njlgg }</njlgg>
      <sjldw>${drug.sjldw }</sjldw>
      <nwlsx>${drug.nwlsx }</nwlsx>
      <nlx>${drug.nlx }</nlx>
      <sfzlx>${drug.sfzlx }</sfzlx>
      <nsfjg>${drug.nsfjg }</nsfjg>
      <spzwh>${drug.spzwh }</spzwh>
      <c:forEach items="${typeList }" var="type">
        <type>
          <slxid>${type.slxid }</slxid>
          <slxmc>${type.slxmc }</slxmc>
        </type>
      </c:forEach>
	</inf>
  </c:forEach>
</drugInf>