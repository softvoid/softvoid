package me.softvoid.server.user.domian;

import me.softvoid.server.domain.baseinfor.CompanyBean;

/**
 * 人员
 * 
 * @author Administrator
 * 
 */
public class EmployeeBean {
	private String sryid;
	private String srymc;
	private String smm;
	private CompanyBean com;
	private PositionBean pos;

	public String getSrymc() {
		return srymc;
	}

	public void setSrymc(String srymc) {
		this.srymc = srymc;
	}


	public String getSryid() {
		return sryid;
	}

	public void setSryid(String sryid) {
		this.sryid = sryid;
	}

	public String getSmm() {
		return smm;
	}

	public void setSmm(String smm) {
		this.smm = smm;
	}

	public CompanyBean getCom() {
		return com;
	}

	public void setCom(CompanyBean com) {
		this.com = com;
	}

	public PositionBean getPos() {
		return pos;
	}

	public void setPos(PositionBean pos) {
		this.pos = pos;
	}

}
