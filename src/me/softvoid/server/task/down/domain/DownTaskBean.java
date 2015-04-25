package me.softvoid.server.task.down.domain;

import java.util.List;

import me.softvoid.server.task.domain.TaskBean;

/**
 * @author 作者：黄校 softneo@qq.com
 * @version 时间：2014-11-24 下午2:45:52
 * 
 *          出库任务
 */
public class DownTaskBean extends TaskBean {
	private String sfhtid; // 复核台id
	private String sckid_desc; // 目的仓库，父类是源仓库
	private String sckmc_desc; // 目的仓库，父类是源仓库
	private List<DownItemTaskBean> downItemTaskList; // 用于存放任务明细的
	private String ssjrid;
	private String ssjrmc;

	public String getSsjrid() {
		return ssjrid;
	}

	public void setSsjrid(String ssjrid) {
		this.ssjrid = ssjrid;
	}

	public String getSsjrmc() {
		return ssjrmc;
	}

	public void setSsjrmc(String ssjrmc) {
		this.ssjrmc = ssjrmc;
	}

	public String getSckid_desc() {
		return sckid_desc;
	}

	public void setSckid_desc(String sckid_desc) {
		this.sckid_desc = sckid_desc;
	}

	public List<DownItemTaskBean> getDownItemTaskList() {
		return downItemTaskList;
	}

	public void setDownItemTaskList(List<DownItemTaskBean> downItemTaskList) {
		this.downItemTaskList = downItemTaskList;
	}

	public String getSckmc_desc() {
		return sckmc_desc;
	}

	public void setSckmc_desc(String sckmc_desc) {
		this.sckmc_desc = sckmc_desc;
	}

	public String getSfhtid() {
		return sfhtid;
	}

	public void setSfhtid(String sfhtid) {
		this.sfhtid = sfhtid;
	}

}
