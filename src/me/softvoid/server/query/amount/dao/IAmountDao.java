package me.softvoid.server.query.amount.dao;

import java.sql.SQLException;
import java.util.List;

import me.softvoid.server.query.amount.domain.AmountBean;

import org.apache.commons.dbutils.QueryRunner;

import cn.itcast.jdbc.TxQueryRunner;

public interface IAmountDao {
	public QueryRunner qr = new TxQueryRunner();

	/**
	 * 更新商品条码
	 * 
	 * @param params
	 * @return
	 */
	public int updateBarcode(Object... params) throws SQLException;

	/**
	 * 获得商品内码
	 * 
	 * @param goodsNum
	 * @return
	 */
	public int[] findDrugId(String goodsNum) throws SQLException;

	/**
	 * 库存查询
	 * 
	 * @param params
	 * @return
	 */
	public List<AmountBean> queryDrugAmount(String... params) throws SQLException;

	/**
	 * 修改货位
	 * 
	 * @return
	 */
	public String movePosition(String drugId, String batchCode, String srcZone,
			String srcPos, String desZone, String desPos, int desAmount, String empNum)
			throws SQLException;
}
