package me.softvoid.server.scan.down.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import me.softvoid.server.domain.DownItemBean;
import me.softvoid.server.domain.PartnerBean;
import me.softvoid.server.domain.baseinfor.FhtBean;
import me.softvoid.server.drug.domain.DrugBean;
import me.softvoid.server.scan.dao.ScanCodeDao;

import org.apache.commons.dbutils.handlers.MapListHandler;

import cn.itcast.commons.CommonUtils;

/**
 * 出库单实现类
 * 
 * @author 黄校 QQ：599969512 email：softneo@qq.com 创建时间：2014年10月31日 下午3:16:09
 */
public class DownScanDao extends ScanCodeDao {

	/**
	 * 出库单只能查询拣货已经确认，并且为监管的药品
	 */
	@Override
	public List<DownItemBean> queryItemOrder(Object... params) throws SQLException {
		StringBuilder sb = new StringBuilder();
		sb.append("select b.sspbm \"sspbm\",b.Sspmc \"sspmc\",a.SPH \"sph\",b.nsfjg \"nsfjg\","
				+ "b.sspgg \"sspgg\",b.NJLGG \"njlgg\",a.n4jhjs \"njhsl\",a.N4ZJSL \"nzj\",a.N4LSSL \"nls\","
				+ "to_char(a.dyxqz,'yyyy-mm-dd') \"dyxqz\",snfhrmc \"snfhrmc\",snfhzcw \"snfhzcw\", "
				+ "(select count(1) from dzjgm where ndjbh=a.njkid and nspid=b.nspid) as \"ncount\","
				+ "c.nckdid \"norderid\",b.nspid \"nspid\",b.sspcd \"sspcd\",b.sjldw \"sjldw\","
				+ "d.sdwmc \"sdwmc\",c.sywymc \"sywymc\",c.sbz \"sbz\" from (select nhh, nspid,njkid, "
				+ "sph, n4jhjs, n4zjsl, n4lssl,dyxqz,snfhrmc,snfhzcw, swldwid from xjrwfpmx a, xjrwfp b "
				+ "where a.nrwfpid=b.nrwfpid and a.nnfhzt>=2 and n4sssl!=0) a "
				+ "inner join spxx b on a.nspid=b.nspid and b.nsfjg=1 left join ckjhd c "
				+ "on a.njkid=c.nckdid and nsfqrwc=1 left join wldw d on c.swldwid=d.swldwid "
				+ "where c.nckdid=? order by a.njkid,a.nhh");
		List<Map<String, Object>> itemListMap = qr.query(sb.toString(), new MapListHandler(),
				params);
		return toItemList(itemListMap);
	}

	@Override
	public List<DownItemBean> toItemList(List<Map<String, Object>> itemListMap) {
		List<DownItemBean> itemList = new ArrayList<>();
		for (Map<String, Object> itemMap : itemListMap) {
			DownItemBean downItemBean = toItem(itemMap);
			itemList.add(downItemBean);
		}
		return itemList;
	}

	@Override
	public DownItemBean toItem(Map<String, Object> itemMap) {
		DownItemBean downItemBean = CommonUtils.toBean(itemMap, DownItemBean.class);
		DrugBean drugBean = CommonUtils.toBean(itemMap, DrugBean.class);
		PartnerBean partnerBean = CommonUtils.toBean(itemMap, PartnerBean.class);
		FhtBean fhtBean = CommonUtils.toBean(itemMap, FhtBean.class);
		downItemBean.setDrugBean(drugBean);
		downItemBean.setPartnerBean(partnerBean);
		downItemBean.setFhtBean(fhtBean);
		return downItemBean;
	}

}
