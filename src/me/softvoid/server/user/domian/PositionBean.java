package me.softvoid.server.user.domian;

/**
 * 岗位
 * 
 * @author Administrator
 * 
 */
public class PositionBean {
	private String sgzzid;
	private String sgzzmc;
	private int nsm;
	private int nsfrk;
	private int nsfck;

	public int getNsfrk() {
		return nsfrk;
	}

	public void setNsfrk(int nsfrk) {
		this.nsfrk = nsfrk;
	}

	public int getNsfck() {
		return nsfck;
	}

	public void setNsfck(int nsfck) {
		this.nsfck = nsfck;
	}


	public int getNsm() {
		return nsm;
	}

	public void setNsm(int nsm) {
		this.nsm = nsm;
	}

	public String getSgzzid() {
		return sgzzid;
	}

	public void setSgzzid(String sgzzid) {
		this.sgzzid = sgzzid;
	}

	public String getSgzzmc() {
		return sgzzmc;
	}

	public void setSgzzmc(String sgzzmc) {
		this.sgzzmc = sgzzmc;
	}
}
