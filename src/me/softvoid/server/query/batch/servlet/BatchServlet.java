package me.softvoid.server.query.batch.servlet;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import me.softvoid.server.domain.PageBean;
import me.softvoid.server.query.batch.domain.PhtzBean;
import me.softvoid.server.query.batch.service.PhtzService;
import me.softvoid.server.utils.NeoUrl;
import me.softvoid.server.utils.NeoUtils;
import cn.itcast.servlet.BaseServlet;

/**
 * Servlet implementation class BatchServlet
 */
public class BatchServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	public String queryBatchNum(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		/**
		 * 1、获得表单数据 2、调用业务层的query方法，得到PageBean<PhtzBean> 3、查询到的数据保存到域中
		 * 4、转发到batchNum.jsp
		 */
		String sspmc = request.getParameter("sspmc").trim();
		String sspbm = request.getParameter("sspbm").trim();
		String sph = request.getParameter("sph").trim();
		int currentPage = getCurrentPage(request);
		int perPageCount = getPerPageCount();
		PageBean<PhtzBean> pageList = new PhtzService().query(sspmc, sspbm, sph, currentPage,
				perPageCount);
		// 设置url
		pageList.setUrl(getUrl(request));
		request.setAttribute("pageList", pageList);
		HttpSession session = request.getSession();
		session.setAttribute("sspmc", sspmc);
		session.setAttribute("sspbm", sspbm);
		session.setAttribute("sph", sph);
		return NeoUrl.FORWORD + NeoUrl.URL_BATCH_NUM;
	}

	/**
	 * 得到当前页数
	 * 
	 * @param request
	 * @return
	 */
	public int getCurrentPage(HttpServletRequest request) {
		String currentPage = request.getParameter("currentPage");
		if (currentPage != null && !currentPage.trim().isEmpty())
			return Integer.parseInt(currentPage);
		return 1;
	}

	/**
	 * 获得分文的url的各个部分
	 * 
	 * @param request
	 * @return
	 */
	public String getUrl(HttpServletRequest request) {
		String contextPath = request.getContextPath(); // 项目名
		String servletPath = request.getServletPath(); // servlet路径
		String queryPath = request.getQueryString(); // 部分的参数值，问号之后的
		// 判断参数是否包含之前的页数，如果存在则截取掉，改用新的页数
		if (queryPath.contains("&currentPage=")) {
			int index = queryPath.lastIndexOf("&currentPage=");
			queryPath = queryPath.substring(0, index);
		}
		return contextPath + servletPath + "?" + queryPath;
	}

	private int getPerPageCount() {
		Properties prop = new Properties();
		try {
			prop.load(this.getClass().getClassLoader()
					.getResourceAsStream("pageConfig.properties"));
			return Integer.parseInt(NeoUtils.getProperty(prop, "perPageCount", NeoUtils.UTF8));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
