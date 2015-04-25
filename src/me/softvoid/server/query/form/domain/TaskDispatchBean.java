package me.softvoid.server.query.form.domain;

/**
 * 任务下发
 * 
 * @author 黄校 QQ：599969512 email：softneo@qq.com 创建时间：2015年3月12日 上午8:45:10
 */
public abstract class TaskDispatchBean {
	private int nrwfpid; // 任务分配id

	public int getNrwfpid() {
		return nrwfpid;
	}

	public void setNrwfpid(int nrwfpid) {
		this.nrwfpid = nrwfpid;
	}

}
