package me.softvoid.server.query.hwspkpjl.dao;

import java.sql.SQLException;

import me.softvoid.server.domain.PageBean;
import me.softvoid.server.query.hwspkpjl.domain.HwspkpjlBean;

import org.apache.commons.dbutils.QueryRunner;

import cn.itcast.jdbc.TxQueryRunner;

public interface Ihwspkpjl {
	public QueryRunner qr = new TxQueryRunner();
	/**
	 * 查询流水
	 * 
	 * @param sspbm
	 * @param sph
	 * @param currentPage
	 * @param perPageCount
	 * @return
	 * @throws SQLException
	 */
	public abstract PageBean<HwspkpjlBean> query(String sspbm, String sph, int currentPage,
			int perPageCount) throws SQLException;
}
