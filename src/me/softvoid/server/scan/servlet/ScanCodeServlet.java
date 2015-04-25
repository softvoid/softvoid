package me.softvoid.server.scan.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.softvoid.server.domain.DownItemBean;
import me.softvoid.server.domain.UpItemBean;
import me.softvoid.server.exception.NeoCodeException;
import me.softvoid.server.exception.NeoDataException;
import me.softvoid.server.scan.down.service.DownScanService;
import me.softvoid.server.scan.up.service.UpScanService;
import me.softvoid.server.utils.NeoUrl;
import cn.itcast.servlet.BaseServlet;

/**
 * Servlet implementation class ScanCodeServlet
 */
public class ScanCodeServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * 添加监管码
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String insertDrugCode(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String orderType = request.getParameter("orderType");
		String orderId = request.getParameter("orderId");
		String drugId = request.getParameter("drugId");
		String batchNum = request.getParameter("batchNum");
		String codes = request.getParameter("codes");
		if ((orderId != null && !orderId.trim().isEmpty())
				&& (drugId != null && !drugId.trim().isEmpty())
				&& (batchNum != null && !batchNum.trim().isEmpty())
				&& (codes != null && !codes.trim().isEmpty()))
			try {
				if ("0".equals(orderType))
					new UpScanService().insertDrugCode(orderType, orderId, drugId, codes, batchNum);
				else
					new DownScanService().insertDrugCode(orderType, orderId, drugId, codes,
							batchNum);
			} catch (NeoCodeException e) {
				response.getOutputStream().write(("null" + e.getMessage()).getBytes());
			}
		else
			response.setStatus(404);
		return null;
	}

	/**
	 * 查询入库出库单
	 * 
	 * @param req
	 * @param res
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String queryOrder(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		String orderId = req.getParameter("orderId");
		String orderType = req.getParameter("orderType");
		if (orderId != null && !orderId.trim().isEmpty() && orderType != null
				&& !orderType.trim().isEmpty())
			try {
				if ("0".equals(orderType)) {
					List<UpItemBean> list = new UpScanService().queryItemOrder(orderId);
					req.setAttribute("list", list);
					return NeoUrl.FORWORD_CHAR + NeoUrl.URL_UP_SCAN;
				} else {
					List<DownItemBean> list = new DownScanService().queryItemOrder(orderId);
					req.setAttribute("list", list);
					return NeoUrl.FORWORD_CHAR + NeoUrl.URL_DOWN_SCAN;
				}
			} catch (NeoDataException e) {
				res.getWriter().write("null" + e.getMessage());
			}
		else
			res.setStatus(404);
		return null;
	}
}
