package me.softvoid.server.task.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.softvoid.server.task.down.service.DownTaskService;
import me.softvoid.server.task.exception.NeoTaskException;
import me.softvoid.server.task.up.service.UpTaskService;

import org.apache.commons.io.IOUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

/**
 * Servlet implementation class TaskConfirmServlet
 */
public class TaskConfirmServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TaskConfirmServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	/**
	 * post提交方式不能使用自定义的BaseServlet
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		String confirmData = request.getParameter("confirmData");
		String orderType = request.getParameter("orderType");
		try {
			Document document = new SAXReader().read(IOUtils.toInputStream(confirmData));
			if ("0".equals(orderType))
				new UpTaskService().parseXml(document);
			else
				new DownTaskService().parseXml(document);
			response.getWriter().write("true");
		} catch (NeoTaskException e) {
			String msg = e.getMessage();
			if (!msg.startsWith("ORA"))
				msg = "null" + msg;
			response.getWriter().write(msg);
		} catch (DocumentException e) {
			response.setStatus(500);
		}
	}

}
