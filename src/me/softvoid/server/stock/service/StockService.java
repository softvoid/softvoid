package me.softvoid.server.stock.service;

import java.sql.SQLException;
import java.util.List;

import me.softvoid.server.factory.DaoFactory;
import me.softvoid.server.stock.dao.IStockDao;
import me.softvoid.server.stock.domain.StockBean;

public class StockService {
	private IStockDao stockDao;

	public StockService() {
		this.stockDao = DaoFactory.getStockDao();
	}

	public List<StockBean> getStockList() throws NeoStockException {
		try {
			return stockDao.getStockList();
		} catch (SQLException e) {
			throw new NeoStockException("查询失败！");
		}
	}

}
