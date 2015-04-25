package me.softvoid.server.query.batch.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import me.softvoid.server.domain.PageBean;
import me.softvoid.server.drug.domain.DrugBean;
import me.softvoid.server.query.batch.domain.PhtzBean;
import me.softvoid.server.utils.NeoUtils;

import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.itcast.commons.CommonUtils;

public class PhtzDao implements IPhtzDao {

	@Override
	public PageBean<PhtzBean> query(String sspmc, String sspbm, String sph,
			int currentPage, int perPageCount) throws SQLException {
		/**
		 * 1、创建PageBean对象 2、设置已有的属性，currentPage和perPageCount 3、得到totalCount
		 * 4、得到beanList
		 */
		PageBean<PhtzBean> pageBean = new PageBean<PhtzBean>();
		pageBean.setCurrentPage(currentPage);
		pageBean.setPerPageCount(perPageCount);
		/**
		 * 连接接口批号调整表，查询记录个数
		 */
		StringBuilder sbSelect = new StringBuilder(
				"select count(1) from (select * from cjk_dj_ph_l a, spxx b where a.nspid=b.nspid "
						+ "union all select * from cjk_dj_ph a, spxx b where a.nspid=b.nspid)");
		StringBuilder whereSql = new StringBuilder(" where 1=1 ");
		List<Object> params = new ArrayList<Object>();
		if (sspmc != null && !sspmc.trim().isEmpty()) {
			whereSql.append(" and sspmc like ? ||'%' ");
			params.add(sspmc);
		}
		if (sspbm != null && !sspbm.trim().isEmpty()) {
			whereSql.append(" and sspbm=?");
			params.add(sspbm);
		}
		if (sph != null && !sph.trim().isEmpty()) {
			whereSql.append(" and sph=?");
			params.add(sph);
		}
		sbSelect.append(whereSql);
		/**
		 * 得到总记录数。注意params.toArray()要这样转换层数组，否则报错
		 */
		Number num = (Number) qr.query(sbSelect.toString(), new ScalarHandler<>(),
				params.toArray());
		pageBean.setTotalCount(num.intValue());
		/**
		 * 查询明细
		 */
		Object nspid = null;
		if (sspbm != null && !sspbm.trim().isEmpty()) {
			String sql = "select distinct nspid from spxx where sspbm=?";
			nspid = qr.query(sql, new ScalarHandler<>(), sspbm);
		}
		whereSql = new StringBuilder(" where 1=1 ");
		params.clear();
		if (sspmc != null && !sspmc.trim().isEmpty()) {
			whereSql.append(" and sspmc like ''||?||'%'");
			params.add(sspmc);
			params.add(sspmc);
		}
		if (nspid != null) {
			whereSql.append(" and a.nspid=?");
			params.add(nspid);
			params.add(nspid);
		}
		if (sph != null && !sph.trim().isEmpty()) {
			whereSql.append(" and sph=?");
			params.add(sph);
			params.add(sph);
		}
		sbSelect = new StringBuilder(
				"select rn \"line\",njsbj \"njsbj\",sjsbz \"sjsbz\",ncklx \"ncklx\",sph \"sph\",dyxqz \"dyxqz\","
						+ "dscrq \"dscrq\",n4sl \"n4sl\",ssjc \"ssjc\",combrain_times \"combrain_times\",nzh \"nzh\","
						+ "sczyid \"sczyid\",sczymc \"sczymc\",nspid \"nspid\",sspbm \"sspbm\",sspmc \"sspmc\","
						+ "sspgg \"sspgg\",sspcd \"sspcd\" from(select rownum rn,a.* from (select id,njsbj,sjsbz,b.sjkspid,ncklx,nfs,sph,dyxqz,"
						+ "dscrq,a.n4sl,ssjc,combrain_times,nzh,sczyid,sczymc,b.nspid,sspbm,sspmc,sspgg,sspcd "
						+ "from cjk_dj_ph_l a inner join spxx b on a.nspid=b.nspid");
		StringBuilder postSql = new StringBuilder(
				" union all select id,njsbj,sjsbz,b.sjkspid,ncklx,nfs,sph,dyxqz,dscrq,a.n4sl,ssjc,"
						+ "combrain_times,nzh,sczyid,sczymc,b.nspid,sspbm,sspmc,sspgg,sspcd "
						+ "from cjk_dj_ph a inner join spxx b on a.nspid=b.nspid");
		StringBuilder sb = new StringBuilder(
				" order by ssjc) a where rownum <= ?) b where b.rn>=?");
		sbSelect.append(whereSql).append(postSql).append(whereSql).append(sb);
		/**
		 * 把查询的结果填充到ListMap对象中，然后遍历集合转换成JavaBean
		 */
		Object[] par = new Object[params.size() + 2];
		if (params.size() == 2) {
			par[0] = params.get(0);
			par[1] = params.get(1);
		} else if (params.size() == 4) {
			par[0] = params.get(0);
			par[1] = params.get(2);
			par[2] = params.get(1);
			par[3] = params.get(3);
		} else if (params.size() == 6) {
			par[0] = params.get(0);
			par[1] = params.get(2);
			par[2] = params.get(4);
			par[3] = params.get(1);
			par[4] = params.get(4);
			par[5] = params.get(5);
		}
		par[par.length - 2] = NeoUtils.getCurrentPageMaxNum(perPageCount, currentPage);
		par[par.length - 1] = NeoUtils.getCurrentPageMinNum(perPageCount, currentPage);
		List<Map<String, Object>> map = qr.query(sbSelect.toString(), new MapListHandler(),
				par);
		pageBean.setBeanList(toBean(map));
		return pageBean;
	}

	public List<PhtzBean> toBean(List<Map<String, Object>> listMap) {
		List<PhtzBean> phtzBeanList = new ArrayList<PhtzBean>();
		for (Map<String, Object> map : listMap) {
			PhtzBean phtzBean = CommonUtils.toBean(map, PhtzBean.class);
			DrugBean drugBean = CommonUtils.toBean(map, DrugBean.class);
			phtzBean.setDrugBean(drugBean);
			phtzBeanList.add(phtzBean);
		}
		return phtzBeanList;
	}
}
