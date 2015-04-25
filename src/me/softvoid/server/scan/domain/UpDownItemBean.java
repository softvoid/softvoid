package me.softvoid.server.scan.domain;

import me.softvoid.server.domain.PartnerBean;
import me.softvoid.server.drug.domain.DrugBean;

public class UpDownItemBean {
	private String norderid;
	private String sjkid;
	private String sph;
	private String dscrq;
	private String dyxqz;
	private int nzj;
	private int nls;
	private int njhsl;
	private int nsssl;
	private int nhh; // 行号
	private String sorderlx; // 单据类型
	private String sywymc;
	private DrugBean drugBean;
	private PartnerBean partnerBean;
	private int ncount; // 已扫的监管码个数

	public int getNcount() {
		return ncount;
	}

	public void setNcount(int ncount) {
		this.ncount = ncount;
	}

	public PartnerBean getPartnerBean() {
		return partnerBean;
	}

	public void setPartnerBean(PartnerBean partnerBean) {
		this.partnerBean = partnerBean;
	}

	public String getNorderid() {
		return norderid;
	}

	public void setNorderid(String norderid) {
		this.norderid = norderid;
	}

	public String getSjkid() {
		return sjkid;
	}

	public void setSjkid(String sjkid) {
		this.sjkid = sjkid;
	}

	public String getSph() {
		return sph;
	}

	public void setSph(String sph) {
		this.sph = sph;
	}

	public String getDscrq() {
		return dscrq;
	}

	public void setDscrq(String dscrq) {
		this.dscrq = dscrq;
	}

	public String getDyxqz() {
		return dyxqz;
	}

	public void setDyxqz(String dyxqz) {
		this.dyxqz = dyxqz;
	}

	public int getNzj() {
		return nzj;
	}

	public void setNzj(int nzj) {
		this.nzj = nzj;
	}

	public int getNls() {
		return nls;
	}

	public void setNls(int nls) {
		this.nls = nls;
	}

	public int getNjhsl() {
		return njhsl;
	}

	public void setNjhsl(int njhsl) {
		this.njhsl = njhsl;
	}

	public int getNsssl() {
		return nsssl;
	}

	public void setNsssl(int nsssl) {
		this.nsssl = nsssl;
	}

	public int getNhh() {
		return nhh;
	}

	public void setNhh(int nhh) {
		this.nhh = nhh;
	}

	public String getSorderlx() {
		return sorderlx;
	}

	public void setSorderlx(String sorderlx) {
		this.sorderlx = sorderlx;
	}

	public String getSywymc() {
		return sywymc;
	}

	public void setSywymc(String sywymc) {
		this.sywymc = sywymc;
	}

	public DrugBean getDrugBean() {
		return drugBean;
	}

	public void setDrugBean(DrugBean drugBean) {
		this.drugBean = drugBean;
	}
}
