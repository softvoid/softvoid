package me.softvoid.server.query.ranking.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.softvoid.server.exception.NeoDataException;
import me.softvoid.server.query.ranking.domain.RankingBean;
import me.softvoid.server.query.ranking.service.RankingService;
import me.softvoid.server.utils.NeoUrl;
import cn.itcast.servlet.BaseServlet;

/**
 * Servlet implementation class RankingListServlet
 */
public class RankingListServlet extends BaseServlet implements Servlet {
	private static final long serialVersionUID = 1L;

	/**
	 * 获得拣货排行榜
	 * 
	 * @param req
	 * @param res
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String getRanking(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		try {
			String emp = req.getParameter("emp");
			String startDate = req.getParameter("startDate");
			String endDate = req.getParameter("endDate");
			if (emp == null || emp.trim().isEmpty()) {
				res.setStatus(404);
				res.getWriter().print("该帐号不存在，查询失败");
				return null;
			}
			List<RankingBean> rankingList = new RankingService().getRanking(emp, startDate,
					endDate);
			req.setAttribute("rankingList", rankingList);
			return NeoUrl.FORWORD_CHAR + NeoUrl.URL_RANKING;
		} catch (NeoDataException e) {
			res.getWriter().print("null" + e.getMessage());
			return null;
		}
	}

	/**
	 * 获得內复核排行榜
	 * 
	 * @param req
	 * @param res
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String getCheckInRanking(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		try {
			String emp = req.getParameter("emp");
			String startDate = req.getParameter("startDate");
			String endDate = req.getParameter("endDate");
			if (emp == null || emp.trim().isEmpty()) {
				res.setStatus(404);
				res.getWriter().print("该帐号不存在，查询失败");
				return null;
			}
			List<RankingBean> rankingList = new RankingService().getCheckInRanking(startDate,
					endDate);
			req.setAttribute("rankingList", rankingList);
			return NeoUrl.FORWORD_CHAR + NeoUrl.URL_RANKING;
		} catch (NeoDataException e) {
			res.getWriter().print("null" + e.getMessage());
			return null;
		}
	}
}
