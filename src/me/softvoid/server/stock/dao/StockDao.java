package me.softvoid.server.stock.dao;

import java.sql.SQLException;
import java.util.List;

import me.softvoid.server.stock.domain.StockBean;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

public class StockDao implements IStockDao {

	@Override
	public List<StockBean> getStockList() throws SQLException {
		String sql = "select sckid \"sckid\", sckmc \"sckmc\" from ck where nmjbj=1 "
				+ "and nyxbj=1 order by sckid";
		return qr.query(sql, new BeanListHandler<StockBean>(StockBean.class));
	}

	@Override
	public StockBean getStockByShwid(String shwid) throws SQLException {
		String sql = "select * from ck where sckid=(select distinct sckid from hwxx where shwid=?)";
		return qr.query(sql, new BeanHandler<StockBean>(StockBean.class), shwid);
	}

}
