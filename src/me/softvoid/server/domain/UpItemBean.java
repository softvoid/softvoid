package me.softvoid.server.domain;

import me.softvoid.server.scan.domain.UpDownItemBean;


/**
 * 入库明细
 * @author Administrator
 *
 */
public class UpItemBean extends UpDownItemBean {
	private String nrkdid;

	public String getNrkdid() {
		return nrkdid;
	}

	public void setNrkdid(String nrkdid) {
		this.nrkdid = nrkdid;
	}
	
}
