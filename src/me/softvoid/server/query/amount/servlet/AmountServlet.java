package me.softvoid.server.query.amount.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.softvoid.server.exception.NeoDataException;
import me.softvoid.server.query.amount.domain.AmountBean;
import me.softvoid.server.query.amount.service.AmountService;
import me.softvoid.server.stock.domain.StockBean;
import me.softvoid.server.stock.service.NeoStockException;
import me.softvoid.server.stock.service.StockService;
import me.softvoid.server.utils.NeoUrl;
import cn.itcast.servlet.BaseServlet;

public class AmountServlet extends BaseServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 查询库存
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String queryAmount(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/**
		 * 获取request的参数
		 */
		String pos = request.getParameter("pos");
		String startNum = request.getParameter("startNum");
		String endNum = request.getParameter("endNum");
		if (pos != null && pos.length() != 0 && startNum != null && endNum != null) {
			try {
				List<AmountBean> list = new AmountService().queryDrugAmount(pos.toUpperCase(),
						startNum, endNum);
				List<StockBean> stockList = new StockService().getStockList();
				/**
				 * 返回xml数据。设置请求的数据。
				 */
				request.setAttribute("list", list);
				request.setAttribute("stockList", stockList);
				return NeoUrl.FORWORD_CHAR + NeoUrl.URL_AMOUNT;
			} catch (NeoDataException | NeoStockException e) {
				response.getWriter().write("null" + e.getMessage());
			}
		} else
			response.setStatus(404);
		return null;
	}

	/**
	 * 更新货位
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String movePosition(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String drugId = request.getParameter("drugId");
		String batchNum = request.getParameter("batchNum");
		String srcZone = request.getParameter("srcZone");
		String srcPos = request.getParameter("srcPos");
		String desPos = request.getParameter("desPos").trim().toUpperCase();
		String desAmount = request.getParameter("desAmount");
		String empNum = request.getParameter("empNum");
		if (drugId != null && batchNum != null && desPos != null && desAmount != null) {
			String r;
			try {
				r = new AmountService().movePosition(drugId, batchNum, srcZone, srcPos, desPos,
						desAmount, empNum);
				if (r != null)
					response.getWriter().write(r);
			} catch (NeoStockException e) {
				response.getWriter().write("null" + e.getMessage());
			}
		}
		return null;
	}

}
