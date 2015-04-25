package me.softvoid.server.stock.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;

import cn.itcast.jdbc.TxQueryRunner;
import me.softvoid.server.stock.domain.StockBean;

public interface IStockDao {
	public QueryRunner qr = new TxQueryRunner();

	/**
	 * 获得仓库列表
	 * 
	 * @return
	 */
	public List<StockBean> getStockList() throws SQLException;
	
	/**
	 * 根据货位id获得仓库
	 * @param sckid
	 * @return
	 * @throws SQLException
	 */
	public StockBean getStockByShwid(String shwid) throws SQLException;
}
