package me.softvoid.server.user.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import me.softvoid.server.domain.baseinfor.CompanyBean;
import me.softvoid.server.exception.NeoOperException;
import me.softvoid.server.user.domian.EmployeeBean;
import me.softvoid.server.user.domian.PositionBean;
import me.softvoid.server.user.domian.UserBean;
import me.softvoid.server.user.service.NeoConnException;
import me.softvoid.server.user.service.UserService;
import cn.itcast.commons.CommonUtils;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		/**
		 * 校验验证码 1、从session中的验证码 2、获取表单中填写的验证码 3、两者进行比较
		 * 4、如果相同则继续，如果不同则写错误信息，转发到login.jsp
		 */
		// String sessionCode = (String)
		// request.getSession().getAttribute("verifyCode");
		// String paramCode = request.getParameter("verifyCode");
		/**
		 * tomcat默认使用的编码为ISO8859-1，那么为了不让传进来的参数乱码，则使用EncodingRequestWrapper类转换。
		 * 获得参数时调用EcodingFilter的doFilter方法，进行编码。
		 */
		response.setContentType("text/html;charset=UTF-8");
		String user = request.getParameter("user");
		String pwd = request.getParameter("pwd");

		/**
		 * 设置客户端以utf-8的编码查看。注意使用PrintWriter向客户端输出数据之前，一定要设置正确的编码，否则乱码。
		 * 而ServletOutputStream不受此影响。
		 */
		PrintWriter pw = response.getWriter();
		if ((user != null && user.length() != 0) && (pwd != null && user.length() != 0)) {
			UserBean operBean = CommonUtils.toBean(request.getParameterMap(), UserBean.class);
			List<EmployeeBean> list = null;
			try {
				UserService operService = new UserService();
				list = operService.queryUser(operBean);
				HttpSession session = request.getSession(true);
				session.setAttribute("userBean", operBean);
				session.setAttribute("user", operBean.getUser());
				session.setAttribute("pwd", pwd);
				/**
				 * 返回json数据。json效率更高。
				 */
				response.getWriter().write(responseJson(list));
				return;
			} catch (NeoOperException e) {
				response.getWriter().write("null" + e.getMessage());
				return;
			} catch (NeoConnException e) {
				response.getWriter().write(e.getMessage());
				response.setStatus(500);
				return;
			} catch (Exception e) {
				response.getWriter().write(e.getMessage());
				response.setStatus(500);
				return;
			}
		}
		/**
		 * ServletContext是web应用的全局对象（同ASP.NET的Application），用此对象可以获得应用中共享数据
		 */
		ServletContext application = this.getServletContext();
		Integer count = (Integer) application.getAttribute("count");
		if (count == null)
			application.setAttribute("count", 1);
		else {
			application.setAttribute("count", ++count);
		}
		String msg = String.valueOf("<br />访问次数：" + application.getAttribute("count"));
		pw.print(msg);
	}

	/**
	 * 拼接json数据
	 * 
	 * @param list
	 *          实体对象的结果集
	 * @return 返回拼接后的json数据
	 */
	private String responseJson(List<EmployeeBean> list) {
		StringBuilder sb = new StringBuilder();
		sb.append('[');
		for (EmployeeBean emp : list) {
			CompanyBean com = emp.getCom();
			PositionBean pos = emp.getPos();
			sb.append("{");
			sb.append("sryid:\"").append(emp.getSryid()).append("\",");
			sb.append("srymc:\"").append(emp.getSrymc()).append("\",");
			sb.append("smm:\"").append(emp.getSmm()).append("\",");
			sb.append("sgzzmc:\"").append(pos.getSgzzmc()).append("\",");
			sb.append("nsm:\"").append(pos.getNsm()).append("\",");
			sb.append("sgsmc:\"").append(com.getSgsmc()).append("\",");
			sb.append("nsfrk:\"").append(pos.getNsfrk()).append("\",");
			sb.append("nsfck:\"").append(pos.getNsfck()).append("\"");
			sb.append("},");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append(']');
		return sb.toString();
	}

}
