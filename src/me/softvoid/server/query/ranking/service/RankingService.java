package me.softvoid.server.query.ranking.service;

import java.sql.SQLException;
import java.util.List;

import me.softvoid.server.exception.NeoDataException;
import me.softvoid.server.query.ranking.dao.RankingDao;
import me.softvoid.server.query.ranking.domain.RankingBean;

public class RankingService {

	/**
	 * 获得工作量排行榜，对于零散任务客户端决定由条码数还是件数来排行。
	 * 
	 * @param emp
	 * @return
	 * @throws NeoDataException
	 */
	public List<RankingBean> getRanking(String emp, String startDate, String endDate)
			throws NeoDataException {
		try {
			/*
			 * startDate = "2015-04-01"; endDate = "2015-04-01";
			 */
			List<RankingBean> rankingList = new RankingDao()
					.getRanking(emp, startDate, endDate);
			if (rankingList == null || rankingList.size() == 0)
				throw new NeoDataException("没有任何信息");
			return rankingList;
		} catch (SQLException e) {
			throw new NeoDataException("查询失败！");
		}
	}

	/**
	 * 內复核工作量排行榜
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws NeoDataException
	 */
	public List<RankingBean> getCheckInRanking(String startDate, String endDate)
			throws NeoDataException {
		try {
			List<RankingBean> rankingList = new RankingDao().getCheckInRanking(startDate,
					endDate);
			if (rankingList.size() == 0)
				throw new NeoDataException("没有任何信息");
			return rankingList;
		} catch (SQLException e) {
			throw new NeoDataException("查询失败！");
		}
	}

}
