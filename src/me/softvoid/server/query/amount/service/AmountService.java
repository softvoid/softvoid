package me.softvoid.server.query.amount.service;

import java.sql.SQLException;
import java.util.List;

import me.softvoid.server.drug.exception.DrugException;
import me.softvoid.server.exception.NeoDataException;
import me.softvoid.server.factory.DaoFactory;
import me.softvoid.server.query.amount.dao.AmountDao;
import me.softvoid.server.query.amount.domain.AmountBean;
import me.softvoid.server.stock.dao.StockDao;
import me.softvoid.server.stock.domain.StockBean;
import me.softvoid.server.stock.service.NeoStockException;
import cn.itcast.jdbc.JdbcUtils;

public class AmountService {
	private AmountDao amountDao;

	public AmountService() {
		amountDao = DaoFactory.getAmountDao();
	}

	/**
	 * 库存查询
	 * 
	 * @param pos
	 * @param startNum
	 * @param endNum
	 * @return
	 * @throws NeoDataException
	 */
	public List<AmountBean> queryDrugAmount(String pos, String startNum, String endNum)
			throws NeoDataException {
		List<AmountBean> list;
		try {
			list = amountDao.queryDrugAmount(pos, startNum, endNum);
			if (list == null || list.size() == 0)
				throw new NeoDataException("没有任何数据！");
			return list;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * 更新商品条码
	 * 
	 * @param goodsNum
	 * @param barcode
	 * @return
	 * @throws DrugException
	 */
	public boolean updateBarcode(String barcode, String goodsNum) throws DrugException {
		int count;
		try {
			count = amountDao.updateBarcode(goodsNum, barcode);
			if (count == 1)
				return true;
			else
				return false;
		} catch (SQLException e) {
			throw new DrugException("修改错误！");
		}
	}

	/**
	 * 移动货位
	 * 
	 * @param goodsNum
	 * @param batchCode
	 * @param srcZone
	 * @param srcPos
	 * @param desZone
	 * @param desPos
	 * @param desAmount
	 * @param empNum
	 * @return
	 * @throws NeoStockException
	 */
	public String movePosition(String drugId, String batchCode, String srcZone,
			String srcPos, String desPos, String desAmount, String empNum)
			throws NeoStockException {
		int amount = Integer.valueOf(desAmount);
		try {
			/**
			 * 客户端无需传入目的仓库id，直接用货位获得id
			 */
			StockBean stock = new StockDao().getStockByShwid(desPos);
			if (stock == null)
				throw new NeoStockException("输入的货位不存在！");
			int count = amountDao.isZeroPostion(desPos, drugId, batchCode);
			if (count != 0)
				throw new NeoStockException("该货位已满！");
			JdbcUtils.beginTransaction();
			String r = amountDao.movePosition(drugId, batchCode, srcZone, srcPos,
					stock.getSckid(), desPos, amount, empNum);
			JdbcUtils.commitTransaction();
			return r;
		} catch (SQLException e) {
			try {
				JdbcUtils.rollbackTransaction();
				throw new NeoStockException("数据库异常，请联系管理员！");
			} catch (SQLException e1) {
				throw new RuntimeException(e);
			}
		}
	}
}
