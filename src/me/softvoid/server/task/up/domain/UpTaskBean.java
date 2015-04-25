package me.softvoid.server.task.up.domain;

import java.util.List;

import me.softvoid.server.task.domain.TaskBean;

/**
 * @author 作者：黄校 softneo@qq.com
 * @version 时间：2014-11-24 下午2:45:52
 * 
 */
public class UpTaskBean extends TaskBean {

	private List<UpItemTaskBean> upItemTaskList; // 用于存放任务明细的

	public List<UpItemTaskBean> getUpItemTaskList() {
		return upItemTaskList;
	}

	public void setUpItemTaskList(List<UpItemTaskBean> upItemTaskList) {
		this.upItemTaskList = upItemTaskList;
	}

}
