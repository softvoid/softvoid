package me.softvoid.server.query.batch.service;

import java.sql.SQLException;

import me.softvoid.server.domain.PageBean;
import me.softvoid.server.query.batch.dao.PhtzDao;
import me.softvoid.server.query.batch.domain.PhtzBean;

public class PhtzService {
	private PhtzDao phtzDao;

	public PhtzService() {
		this.phtzDao = new PhtzDao();
	}

	public PageBean<PhtzBean> query(String sspmc, String sspbm, String sph,
			int currentPage, int perPageCount) {
		try {
			return phtzDao.query(sspmc, sspbm, sph, currentPage, perPageCount);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
