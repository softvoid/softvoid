package me.softvoid.server.query.hwspkpjl.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import me.softvoid.server.domain.PageBean;
import me.softvoid.server.drug.domain.DrugBean;
import me.softvoid.server.query.hwspkpjl.domain.HwspkpjlBean;
import me.softvoid.server.stock.domain.StockBean;
import me.softvoid.server.utils.NeoUtils;

import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.itcast.jdbc.JdbcUtils;

/**
 * 数据库层
 * 
 * @author 黄校 QQ：599969512 email：softneo@qq.com 创建时间：2014年11月6日 下午9:04:15
 */
public class HwspkpjlDao implements Ihwspkpjl {
	/**
	 * 多条件查询，也会分页
	 * 
	 * @param cstmBean
	 * @param currentPage
	 * @param perPageCount
	 * @return
	 * @throws SQLException
	 */
	@Override
	public PageBean<HwspkpjlBean> query(String sspbm, String sph, int currentPage,
			int perPageCount) throws SQLException {
		/**
		 * 1、创建PageBean对象 2、设置已有的属性，currentPage和perPageCount 3、得到totalCount
		 * 4、得到beanList
		 */
		PageBean<HwspkpjlBean> pageBean = new PageBean<HwspkpjlBean>();
		pageBean.setCurrentPage(currentPage);
		pageBean.setPerPageCount(perPageCount);

		StringBuilder countSql = new StringBuilder("select count(1) from hwspkpjl a, spxx b");
		StringBuilder whereSql = new StringBuilder(" where 1=1 and a.nspid=b.nspid ");
		List<Object> params = new ArrayList<Object>();

		if (sspbm != null && !sspbm.trim().isEmpty()) {
			whereSql.append(" and sspbm=?");
			params.add(sspbm);
		}
		if (sph != null && !sph.trim().isEmpty()) {
			whereSql.append(" and sph=?");
			params.add(sph);
		}
		// 得到总记录数
		Number num = (Number) qr.query(countSql.append(whereSql).toString(),
				new ScalarHandler<>(), params.toArray());
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
		if (nspid != null) {
			whereSql.append(" and a.nspid=?");
			params.add(nspid);
		}
		if (sph != null && !sph.trim().isEmpty()) {
			whereSql.append(" and sph=?");
			params.add(sph);
		}

		// 得到beanList。通过拼接sql来达到分页查询
		StringBuilder preSql = new StringBuilder(
				"SELECT * FROM (SELECT ROWNUM rn, a.* FROM (SELECT * FROM hwspkpjl a "
						+ "LEFT JOIN ck b ON a.sckid=b.sckid "
						+ "LEFT JOIN spxx c ON a.nspid=c.nspid ");
		StringBuilder postSql = new StringBuilder(" ORDER BY a.dgxrq) a"
				+ " WHERE ROWNUM <= ?) b WHERE b.rn>=?");
		ResultSet rs = null;
		PreparedStatement ps = JdbcUtils.getConnection().prepareStatement(
				preSql.append(whereSql).append(postSql).toString());
		params.add(NeoUtils.getCurrentPageMaxNum(perPageCount, currentPage));
		params.add(NeoUtils.getCurrentPageMinNum(perPageCount, currentPage));
		for (int i = 0; i < params.size(); i++)
			ps.setObject(i + 1, params.get(i));
		rs = ps.executeQuery();
		List<HwspkpjlBean> hwkpjlBeanList = new ArrayList<HwspkpjlBean>();
		while (rs.next()) {
			HwspkpjlBean hwkpjlBean = new HwspkpjlBean();
			StockBean ckxxBean = new StockBean();
			DrugBean drugBean = new DrugBean();
			hwkpjlBean.setCkxxBean(ckxxBean);
			hwkpjlBean.setDrugBean(drugBean);
			ckxxBean.setSckmc(rs.getString("sckmc"));
			drugBean.setSspbm(rs.getString("sspbm"));
			drugBean.setSspmc(rs.getString("sspmc"));
			drugBean.setSspcd(rs.getString("ssccj"));
			drugBean.setSspgg(rs.getString("sspgg"));
			drugBean.setNjlgg(rs.getInt("njlgg"));
			hwkpjlBean.setShwid(rs.getString("shwid"));
			hwkpjlBean.setSph(rs.getString("sph"));
			hwkpjlBean.setN4bhsl(rs.getInt("n4bhsl"));
			hwkpjlBean.setN4hwsl(rs.getInt("n4jcsl"));
			hwkpjlBean.setN4kcksl(rs.getInt("n4kcksl"));
			hwkpjlBean.setSbz(rs.getString("sbz"));
			hwkpjlBean.setSjkid(rs.getString("sjkid"));
			hwkpjlBean.setDgxrq(rs.getTimestamp("dgxrq")); // 用时间戳能显示数据库Date类型中的时间
			hwkpjlBean.setLine(rs.getInt("rn"));
			hwkpjlBeanList.add(hwkpjlBean);
		}
		pageBean.setBeanList(hwkpjlBeanList);
		return pageBean;
	}
}
