package me.softvoid.server.domain;

import me.softvoid.server.domain.baseinfor.FhtBean;
import me.softvoid.server.scan.domain.UpDownItemBean;

/**
 * 出库明细
 * 
 * @author Administrator
 * 
 */
public class DownItemBean extends UpDownItemBean {

	private FhtBean fhtBean;

	public FhtBean getFhtBean() {
		return fhtBean;
	}

	public void setFhtBean(FhtBean fhtBean) {
		this.fhtBean = fhtBean;
	}

}
