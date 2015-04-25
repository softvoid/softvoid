package me.softvoid.server.scan.up.service;

import java.sql.SQLException;
import java.util.List;

import me.softvoid.server.domain.UpItemBean;
import me.softvoid.server.exception.NeoDataException;
import me.softvoid.server.factory.DaoFactory;
import me.softvoid.server.scan.service.ScanCodeService;

public class UpScanService extends ScanCodeService {

	public UpScanService() {
		this.scanCodeDao = DaoFactory.getUpScanDao();
	}

	@Override
	public List<UpItemBean> queryItemOrder(String orderId) throws NeoDataException {
		try {
			List<UpItemBean> list = (List<UpItemBean>) scanCodeDao.queryItemOrder(orderId);
			if (list == null || list.size() == 0)
				throw new NeoDataException("没有" + orderId + "的入库单！");
			return list;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
