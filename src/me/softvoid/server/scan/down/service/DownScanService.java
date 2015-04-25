package me.softvoid.server.scan.down.service;

import java.sql.SQLException;
import java.util.List;

import me.softvoid.server.domain.DownItemBean;
import me.softvoid.server.exception.NeoDataException;
import me.softvoid.server.factory.DaoFactory;
import me.softvoid.server.scan.service.ScanCodeService;

public class DownScanService extends ScanCodeService {
	public DownScanService() {
		this.scanCodeDao = DaoFactory.getDownScanDao();
	}

	/**
	 * 查询单据
	 * 
	 * @param orderId
	 * @return
	 * @throws NeoDataException
	 */
	@Override
	public List<DownItemBean> queryItemOrder(String orderId) throws NeoDataException {
		try {
			List<DownItemBean> list = (List<DownItemBean>) scanCodeDao.queryItemOrder(orderId);
			if (list == null || list.size() == 0)
				throw new NeoDataException("没有" + orderId + "的出库单！");
			return list;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
