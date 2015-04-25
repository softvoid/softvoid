package me.softvoid.server.query.amount.domain;

import me.softvoid.server.drug.domain.DrugBean;
import me.softvoid.server.stock.domain.StockBean;

public class AmountBean {
	private String shwid;
	private String sph;
	private String dscrq;
	private String dyxqz;
	private int n4hwsl;
	private int n4kcksl;
	private int nzj;
	private int nls;
	private StockBean stockBean;
	private DrugBean drugBean;
	private int count;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public DrugBean getDrugBean() {
		return drugBean;
	}

	public void setDrugBean(DrugBean drugBean) {
		this.drugBean = drugBean;
	}

	public void setStockBean(StockBean stockBean) {
		this.stockBean = stockBean;
	}

	public StockBean getStockBean() {
		return stockBean;
	}

	public void setStock(StockBean stockBean) {
		this.stockBean = stockBean;
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

	public String getShwid() {
		return shwid;
	}

	public void setShwid(String shwid) {
		this.shwid = shwid;
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

	public int getN4hwsl() {
		return n4hwsl;
	}

	public void setN4hwsl(int n4hwsl) {
		this.n4hwsl = n4hwsl;
	}

	public int getN4kcksl() {
		return n4kcksl;
	}

	public void setN4kcksl(int n4kcksl) {
		this.n4kcksl = n4kcksl;
	}

}
