package me.softvoid.server.domain;

import me.softvoid.server.scan.domain.UpDownBean;

public class DownBean extends UpDownBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 复核台编码
	 */
	private String snfhzcw;

	public String getSnfhzcw() {
		return snfhzcw;
	}

	public void setSnfhzcw(String snfhzcw) {
		this.snfhzcw = snfhzcw;
	}

}
