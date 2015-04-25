package me.softvoid.server.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.softvoid.server.user.domian.UserBean;

/**
 * Servlet Filter implementation class LoginFilter
 */
public class LoginFilter implements Filter {

	/**
	 * Default constructor.
	 */
	public LoginFilter() {
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		/**
		 * 1、从session中获取用户信息 2、如果用户不存在保存错误信息，转发到login.jsp 3、否则放行
		 */
		HttpServletRequest httpReq = (HttpServletRequest) request;
		UserBean userBean = (UserBean) httpReq.getSession().getAttribute("userBean");
		if (userBean != null)
			chain.doFilter(request, response);
		else {
			httpReq.setAttribute("msg", "您还没有登录！");
			// httpReq.getRequestDispatcher(CommonURL.).forward(request, response);
			((HttpServletResponse) response).setStatus(300);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
