package me.softvoid.server.domain;

import java.util.Date;

import me.softvoid.server.drug.domain.DrugBean;
import me.softvoid.server.stock.domain.StockBean;

public class OrderItemBean {
	private int norderid;
	private String sjkid;
	private String sph;
	private String dscrq;
	private Date dyxqz;
	private int nzj;
	private int nls;
	private int njhsl;
	private int nsssl;
	private int nhh; // 行号
	private String sorderlx; // 单据类型
	private String sywymc;
	private String djhrq;
	private String dnfhrq;
	private String dwfhrq;
	private int nrwfpid;
	private int nrwid;
	private DrugBean goodsBean;
	private String sxjrid;
	private String sxjrmc;
	private String snfhrid;
	private String snfhrmc;
	private String swfhyid;
	private String swfhymc;
	private String shwid;
	private StockBean stockBean;
	private String sddy;
	private String dddrq;

	public String getDddrq() {
		return dddrq;
	}

	public void setDddrq(String dddrq) {
		this.dddrq = dddrq;
	}

	public String getSddy() {
		return sddy;
	}

	public void setSddy(String sddy) {
		this.sddy = sddy;
	}

	public StockBean getStockBean() {
		return stockBean;
	}

	public void setStockBean(StockBean stockBean) {
		this.stockBean = stockBean;
	}

	public String getShwid() {
		return shwid;
	}

	public void setShwid(String shwid) {
		this.shwid = shwid;
	}

	public String getSxjrid() {
		return sxjrid;
	}

	public void setSxjrid(String sxjrid) {
		this.sxjrid = sxjrid;
	}

	public String getSxjrmc() {
		return sxjrmc;
	}

	public void setSxjrmc(String sxjrmc) {
		this.sxjrmc = sxjrmc;
	}

	public String getSnfhrid() {
		return snfhrid;
	}

	public void setSnfhrid(String snfhrid) {
		this.snfhrid = snfhrid;
	}

	public String getSnfhrmc() {
		return snfhrmc;
	}

	public void setSnfhrmc(String snfhrmc) {
		this.snfhrmc = snfhrmc;
	}

	public String getSwfhyid() {
		return swfhyid;
	}

	public void setSwfhyid(String swfhyid) {
		this.swfhyid = swfhyid;
	}

	public String getSwfhymc() {
		return swfhymc;
	}

	public void setSwfhymc(String swfhymc) {
		this.swfhymc = swfhymc;
	}

	public int getNrwid() {
		return nrwid;
	}

	public void setNrwid(int nrwid) {
		this.nrwid = nrwid;
	}

	public int getNrwfpid() {
		return nrwfpid;
	}

	public void setNrwfpid(int nrwfpid) {
		this.nrwfpid = nrwfpid;
	}

	public String getDjhrq() {
		return djhrq;
	}

	public void setDjhrq(String djhrq) {
		this.djhrq = djhrq;
	}

	public String getDnfhrq() {
		return dnfhrq;
	}

	public void setDnfhrq(String dnfhrq) {
		this.dnfhrq = dnfhrq;
	}

	public String getDwfhrq() {
		return dwfhrq;
	}

	public void setDwfhrq(String dwfhrq) {
		this.dwfhrq = dwfhrq;
	}

	public int getNorderid() {
		return norderid;
	}

	public void setNorderid(int norderid) {
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

	public Date getDyxqz() {
		return dyxqz;
	}

	public void setDyxqz(Date dyxqz) {
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

	public DrugBean getGoodsBean() {
		return goodsBean;
	}

	public void setGoodsBean(DrugBean goodsBean) {
		this.goodsBean = goodsBean;
	}
}
