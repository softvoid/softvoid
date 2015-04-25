package me.softvoid.server.task.down.service;

import me.softvoid.server.factory.DaoFactory;
import me.softvoid.server.task.service.TaskService;

public class DownTaskService extends TaskService {
	public DownTaskService() {
		this.taskDao = DaoFactory.getDownTaskDao();
	}

}
