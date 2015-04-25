package me.softvoid.server.scan.service;

import java.sql.SQLException;
import java.util.List;

import me.softvoid.server.exception.NeoCodeException;
import me.softvoid.server.exception.NeoDataException;
import me.softvoid.server.scan.dao.IScanCodeDao;
import me.softvoid.server.scan.domain.UpDownItemBean;
import me.softvoid.server.utils.NeoUtils;
import cn.itcast.jdbc.JdbcUtils;

public abstract class ScanCodeService {
	protected IScanCodeDao scanCodeDao;

	/**
	 * 事务添加监管码
	 * 
	 * @param orderId
	 * @param drugNum
	 * @param codes
	 * @param batchNum
	 * @return
	 * @throws NeoCodeException
	 */
	public void insertDrugCode(String orderType, String orderId, String drugId,
			String codes, String batchNum) throws NeoCodeException {
		try {
			JdbcUtils.beginTransaction();
			List<String> listRemainCode = scanCodeDao.insertDrugCode(orderType, orderId,
					drugId, codes, batchNum);
			JdbcUtils.commitTransaction();
			if (listRemainCode.size() != 0)
				throw new NeoCodeException(NeoUtils.joinString(listRemainCode, ","));
		} catch (SQLException e) {
			try {
				JdbcUtils.rollbackTransaction();
			} catch (SQLException e1) {
				throw new RuntimeException(e1);
			}
			String msg = "扫入监管码失败！";
			if (e.getMessage().contains("ORA-00001"))
				msg = "监管码不能重复！";
			throw new NeoCodeException(msg);
		}
	}

	/**
	 * 查询单据
	 * 
	 * @param orderId
	 * @return
	 * @throws NeoDataException
	 */
	public abstract List<? extends UpDownItemBean> queryItemOrder(String orderId)
			throws NeoDataException;
}
