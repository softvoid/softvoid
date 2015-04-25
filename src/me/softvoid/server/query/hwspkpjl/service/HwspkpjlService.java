package me.softvoid.server.query.hwspkpjl.service;

import java.sql.SQLException;

import me.softvoid.server.domain.PageBean;
import me.softvoid.server.query.hwspkpjl.dao.HwspkpjlDao;
import me.softvoid.server.query.hwspkpjl.domain.HwspkpjlBean;

public class HwspkpjlService {
	private HwspkpjlDao hwkpjlDao;

	public HwspkpjlService() {
		this.hwkpjlDao = new HwspkpjlDao();
	}

	/**
	 * 分页查询
	 * 
	 * @param sspbm
	 * @param sph
	 * @param currentPage
	 * @param perPageCount
	 * @return
	 */
	public PageBean<HwspkpjlBean> query(String sspbm, String sph, int currentPage,
			int perPageCount) {
		try {
			return hwkpjlDao.query(sspbm, sph, currentPage, perPageCount);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
