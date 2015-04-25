package me.softvoid.server.scan.up.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import me.softvoid.server.domain.PartnerBean;
import me.softvoid.server.domain.UpItemBean;
import me.softvoid.server.drug.domain.DrugBean;
import me.softvoid.server.scan.dao.ScanCodeDao;

import org.apache.commons.dbutils.handlers.MapListHandler;

import cn.itcast.commons.CommonUtils;

public class UpScanDao extends ScanCodeDao {

	@Override
	public List<UpItemBean> queryItemOrder(Object... params) throws SQLException {
		StringBuilder sb = new StringBuilder();
		sb.append("select b.sspbm \"sspbm\",b.Sspmc \"sspmc\",a.SPH \"sph\",b.nsfjg \"nsfjg\","
				+ "b.sspgg \"sspgg\",b.NJLGG \"njlgg\",a.n4jhjs \"njhsl\",a.N4ZJSL \"nzj\",a.N4LSSL \"nls\","
				+ "to_char(a.dyxqz,'yyyy-mm-dd') \"dyxqz\",(select count(1) from dzjgm where "
				+ "ndjbh=a.njkid and nspid=b.nspid) as \"ncount\",r.nrkdid \"norderid\",b.nspid \"nspid\","
				+ "b.sspcd \"sspcd\",b.sjldw \"sjldw\",d.sdwmc \"sdwmc\",r.sywymc \"sywymc\",r.sbz \"sbz\""
				+ " from (select nhh, nspid,njkid,sph, n4jhjs, n4zjsl, n4lssl,dyxqz,swldwid from "
				+ "sjrwfpmx a, sjrwfp b where a.nrwfpid=b.nrwfpid and b.nsfqrwc=1 and n4sssl!=0) a "
				+ "inner join spxx b on a.nspid=b.nspid and b.nsfjg=1 left join rkd r "
				+ "on a.njkid=r.nrkdid and nsfqrwc=1 left join wldw d on r.swldwid=d.swldwid "
				+ "where r.nrkdid=? order by a.njkid,a.nhh");
		List<Map<String, Object>> itemListMap = qr.query(sb.toString(), new MapListHandler(),
				params);
		return toItemList(itemListMap);
	}

	@Override
	public List<UpItemBean> toItemList(List<Map<String, Object>> itemListMap) {
		List<UpItemBean> itemList = new ArrayList<UpItemBean>();
		for (Map<String, Object> itemMap : itemListMap) {
			UpItemBean upItemBean = toItem(itemMap);
			itemList.add(upItemBean);
		}
		return itemList;
	}

	@Override
	public UpItemBean toItem(Map<String, Object> itemMap) {
		UpItemBean upItemBean = CommonUtils.toBean(itemMap, UpItemBean.class);
		DrugBean drugBean = CommonUtils.toBean(itemMap, DrugBean.class);
		PartnerBean partnerBean = CommonUtils.toBean(itemMap, PartnerBean.class);
		upItemBean.setDrugBean(drugBean);
		upItemBean.setPartnerBean(partnerBean);
		return upItemBean;
	}

}
