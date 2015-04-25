package me.softvoid.server.task.up.servlet;

import me.softvoid.server.task.servlet.TaskServlet;
import me.softvoid.server.task.up.service.UpTaskService;

public class UpTaskServlet extends TaskServlet {
	private static final long serialVersionUID = 1L;

	public UpTaskServlet() {
		taskService = new UpTaskService(); // 实例化父类的对象
	}
}
