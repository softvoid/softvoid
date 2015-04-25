package me.softvoid.server.task.up.service;

import me.softvoid.server.factory.DaoFactory;
import me.softvoid.server.task.service.TaskService;

public class UpTaskService extends TaskService {
	public UpTaskService() {
		this.taskDao = new DaoFactory().getUpTaskDao();
	}

}
