package me.softvoid.server.drug.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.softvoid.server.drug.domain.DrugBean;
import me.softvoid.server.drug.domain.GroupTypeBean;
import me.softvoid.server.drug.exception.DrugException;
import me.softvoid.server.drug.service.DrugService;
import me.softvoid.server.utils.NeoUrl;
import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;

public class DrugServlet extends BaseServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DrugService drugService = new DrugService();

	/**
	 * 更新药品信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String updateDrugInfor(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DrugBean drugBean = CommonUtils.toBean(request.getParameterMap(), DrugBean.class);
		try {
			drugService.updateDrugInfor(drugBean);
		} catch (DrugException e) {
			response.setStatus(500);
		}
		return null;
	}

	/**
	 * 查询药品
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findAllDrug(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String param = request.getParameter("param").toUpperCase(Locale.CHINESE);
		if (param == null)
			response.setStatus(404);
		try {
			List<DrugBean> listDrug = drugService.findAllDrug(param);
			List<GroupTypeBean> typeList = drugService.getGroupType(6);
			request.setAttribute("listDrug", listDrug);
			request.setAttribute("typeList", typeList);
			return NeoUrl.FORWORD_CHAR + NeoUrl.URL_DRUG;
		} catch (DrugException e) {
			response.getWriter().write("null" + e.getMessage());
		}
		return null;
	}
}
