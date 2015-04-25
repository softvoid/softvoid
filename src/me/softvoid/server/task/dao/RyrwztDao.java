package me.softvoid.server.task.dao;

import java.sql.SQLException;
import java.util.List;

import me.softvoid.server.task.domain.RyrwztBean;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.itcast.jdbc.TxQueryRunner;

public class RyrwztDao {
	private QueryRunner qr = new TxQueryRunner();

	/**
	 * 根据药品的内码查询存在位确认的任务。
	 * 查询补货、未确认的任务状态
	 * 
	 * @param drugId
	 * @return
	 * @throws SQLException
	 */
	public List<RyrwztBean> findByDrugId(String drugId) throws SQLException {
		String sql = "select * from ryrwzt where nrwid in(select b.nrwid from xjrwfprwd a, "
				+ "xjrwfpmx b where nrwzt=3 and a.nrwid=b.nrwid and a.nrwlx in(3,4) and nspid=?)"
				+ " and nrwlx in(4,3) and nrwzt=3";
		return qr.query(sql, new BeanListHandler<RyrwztBean>(RyrwztBean.class), drugId);
	}
}
