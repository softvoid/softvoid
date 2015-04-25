package me.softvoid.server.task.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.softvoid.server.task.domain.TaskBean;
import me.softvoid.server.task.down.domain.DownTaskBean;
import me.softvoid.server.task.down.service.DownTaskService;
import me.softvoid.server.task.exception.NeoTaskException;
import me.softvoid.server.task.service.TaskService;
import me.softvoid.server.task.up.domain.UpTaskBean;
import me.softvoid.server.task.up.service.UpTaskService;
import me.softvoid.server.utils.NeoUrl;
import cn.itcast.servlet.BaseServlet;

public class TaskServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	protected TaskService taskService;

	/**
	 * 索取新的任务
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String getNewTask(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String empNum = request.getParameter("empNum");
		String orderType = request.getParameter("orderType");
		String isGet = request.getParameter("isGet");
		if (empNum != null && !empNum.trim().isEmpty() && orderType != null
				&& !orderType.trim().isEmpty() && isGet != null && !isGet.trim().isEmpty()) {
			List<? extends TaskBean> list = null;
			try {
				if ("1".equals(orderType)) {
					taskService = new DownTaskService();
					list = (List<DownTaskBean>) taskService.getNewTask(empNum, orderType, isGet);
					request.setAttribute("list", list);
					return NeoUrl.FORWORD_CHAR + NeoUrl.URL_OUT_TASK_BEAN;
				} else {
					taskService = new UpTaskService();
					list = (List<UpTaskBean>) taskService.getNewTask(empNum, orderType, isGet);
					request.setAttribute("list", list);
					return NeoUrl.FORWORD_CHAR + NeoUrl.URL_IN_TASK_BEAN;
				}

			} catch (NeoTaskException e) {
				response.getWriter().write("null" + e.getMessage());
				return null;
			}
		}
		response.setStatus(404);
		return null;
	}

	/*
	 * 索取任务，多任务索取
	 */
	public String getTaskList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String empNum = request.getParameter("empNum");
		String orderType = request.getParameter("orderType");
		String isGet = request.getParameter("isGet");
		if (empNum != null && !empNum.trim().isEmpty() && orderType != null
				&& !orderType.trim().isEmpty() && isGet != null && !isGet.trim().isEmpty()) {
			List<? extends TaskBean> list = null;
			try {
				if ("1".equals(orderType)) {
					taskService = new DownTaskService();
					list = (List<DownTaskBean>) taskService.getTaskList(empNum, orderType, isGet);
					request.setAttribute("list", list);
					return NeoUrl.FORWORD_CHAR + NeoUrl.URL_OUT_TASK_BEAN;
				} else {
					taskService = new UpTaskService();
					list = (List<UpTaskBean>) taskService.getTaskList(empNum, orderType, isGet);
					request.setAttribute("list", list);
					return NeoUrl.FORWORD_CHAR + NeoUrl.URL_IN_TASK_BEAN;
				}
			} catch (NeoTaskException e) {
				response.getWriter().write("null" + e.getMessage());
				return null;
			}
		}
		response.setStatus(404);
		return null;
	}

	/**
	 * 由客户端传进来的任务id、人员id、任务类型来获取任务
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String getTaskByRwid(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String empNum = request.getParameter("empNum");
		String orderType = request.getParameter("orderType");
		String srwid = request.getParameter("srwid");
		String isHistory = request.getParameter("isHistory");
		if (empNum != null && !empNum.trim().isEmpty() && orderType != null
				&& !orderType.trim().isEmpty() && srwid != null && !srwid.trim().isEmpty()
				&& isHistory != null && !isHistory.trim().isEmpty()) {
			if ("1".equals(orderType))
				taskService = new DownTaskService();
			else
				taskService = new UpTaskService();
			TaskBean taskBean;
			try {
				taskBean = taskService.getTaskByRwid(empNum, srwid, orderType, isHistory);
				request.setAttribute("taskBean", taskBean);
				if ("1".equals(orderType))
					return NeoUrl.FORWORD_CHAR + NeoUrl.URL_OUT_TASK;
				else
					return NeoUrl.FORWORD_CHAR + NeoUrl.URL_IN_TASK;
			} catch (NeoTaskException e) {
				response.getWriter().print("null" + e.getMessage());
				return null;
			}
		}
		response.setStatus(404);
		return null;
	}

	/**
	 * 历史任务查询，显示一个列表可以查询多个
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String getHistoryTask(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String empNum = request.getParameter("empNum");
		String orderType = request.getParameter("orderType");
		if (empNum != null && !empNum.trim().isEmpty() && orderType != null
				&& !orderType.trim().isEmpty()) {
			if ("1".equals(orderType))
				taskService = new DownTaskService();
			else
				taskService = new UpTaskService();
			List<? extends TaskBean> list = null;
			try {
				list = taskService.getHistoryTask(empNum, orderType);
				request.setAttribute("list", list);
				if ("1".equals(orderType))
					return NeoUrl.FORWORD_CHAR + NeoUrl.URL_OUT_TASK_BEAN;
				else
					return NeoUrl.FORWORD_CHAR + NeoUrl.URL_IN_TASK_BEAN;
			} catch (NeoTaskException e) {
				response.getWriter().print("null" + e.getMessage());
				return null;
			}
		}
		response.setStatus(404);
		return null;
	}
}
