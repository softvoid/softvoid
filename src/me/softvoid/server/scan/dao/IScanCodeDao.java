package me.softvoid.server.scan.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import me.softvoid.server.scan.domain.UpDownItemBean;

import org.apache.commons.dbutils.QueryRunner;

import cn.itcast.jdbc.TxQueryRunner;

public interface IScanCodeDao {
	public QueryRunner qr = new TxQueryRunner();

	/**
	 * 扫描监管码
	 * 
	 * @param orderId
	 * @param goodsNum
	 * @param codes
	 * @param batchNum
	 * @return
	 */
	public List<String> insertDrugCode(String orderType, String orderId, String goodsNum,
			String codes, String batchNum) throws SQLException;
	
	/**
	 * 查询单据明细
	 * 
	 * @param param
	 * @return
	 */
	public List<? extends UpDownItemBean> queryItemOrder(Object... params) throws SQLException;

	/**
	 * 把MapList转换成List
	 * 
	 * @param itemListMap
	 * @return
	 */
	public List<? extends UpDownItemBean> toItemList(List<Map<String, Object>> itemListMap);

	/**
	 * 把map对象转成JavaBean
	 * 
	 * @param map
	 * @return
	 */
	public UpDownItemBean toItem(Map<String, Object> itemMap);
}
