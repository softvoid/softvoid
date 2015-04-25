package me.softvoid.server.query.hwspkpjl.servlet;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import me.softvoid.server.domain.PageBean;
import me.softvoid.server.query.hwspkpjl.domain.HwspkpjlBean;
import me.softvoid.server.query.hwspkpjl.service.HwspkpjlService;
import me.softvoid.server.utils.NeoUrl;
import me.softvoid.server.utils.NeoUtils;
import cn.itcast.servlet.BaseServlet;

/**
 * Servlet implementation class QueryServlet
 */
public class HwspkpjlServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private HwspkpjlService hwkpjlService = new HwspkpjlService();

	/**
	 * 货位商品卡片记录查询
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String queryHwspkpjl(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/**
		 * 1、获得表单数据 2、调用业务层的query方法，得到PageBean<HwspkpjlBean> 3、查询到的数据保存到域中
		 * 4、转发到hwspkpjl.jsp
		 */
		String sspbm = request.getParameter("sspbm");
		String sph = request.getParameter("sph");
		int currentPage = getCurrentPage(request);
		int perPageCount = getPerPageCount();
		PageBean<HwspkpjlBean> pageList = hwkpjlService.query(sspbm, sph, currentPage,
				perPageCount);
		// 设置url
		pageList.setUrl(getUrl(request));
		request.setAttribute("pageList", pageList);
		HttpSession session = request.getSession();
		session.setAttribute("sspbm", sspbm);
		session.setAttribute("sph", sph);
		return NeoUrl.FORWORD + NeoUrl.URL_HWSPKPJL;
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
