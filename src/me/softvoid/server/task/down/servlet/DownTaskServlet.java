package me.softvoid.server.task.down.servlet;

import me.softvoid.server.task.down.service.DownTaskService;
import me.softvoid.server.task.servlet.TaskServlet;

public class DownTaskServlet extends TaskServlet {
	private static final long serialVersionUID = 1L;

	public DownTaskServlet() {
		taskService = new DownTaskService(); // 实例化父类的对象
	}

}
