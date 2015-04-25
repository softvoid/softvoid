package me.softvoid.server.stock.domain;

public class StockBean {
	private String sckid;
	private String sckmc;
	private int nlx;
	private String sbz;
	
	public int getNlx() {
		return nlx;
	}

	public void setNlx(int nlx) {
		this.nlx = nlx;
	}

	public String getSbz() {
		return sbz;
	}

	public void setSbz(String sbz) {
		this.sbz = sbz;
	}

	public String getSckid() {
		return sckid;
	}

	public void setSckid(String sckid) {
		this.sckid = sckid;
	}

	public String getSckmc() {
		return sckmc;
	}

	public void setSckmc(String sckmc) {
		this.sckmc = sckmc;
	}
}
