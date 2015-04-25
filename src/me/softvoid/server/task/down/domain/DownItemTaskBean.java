package me.softvoid.server.task.down.domain;

import me.softvoid.server.task.domain.ItemTaskBean;

/**
 * 
 * @author Administrator
 *	出库明细
 */
public class DownItemTaskBean extends ItemTaskBean {
	private String shwid_desc;	//源货位

	public String getShwid_desc() {
		return shwid_desc;
	}

	public void setShwid_desc(String shwid_desc) {
		this.shwid_desc = shwid_desc;
	}
	
}
