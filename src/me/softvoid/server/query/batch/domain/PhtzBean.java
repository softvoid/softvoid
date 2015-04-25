package me.softvoid.server.query.batch.domain;

import java.util.Date;

import me.softvoid.server.drug.domain.DrugBean;

public class PhtzBean {
	/**
	 * id
	 */
	private int id;
	/**
	 * 接收标记
	 */
	private int njsbj;
	/**
	 * 接收备注
	 */
	private String sjsbz;
	/**
	 * ERP内码
	 */
	private String sjkspid;
	/**
	 * 仓库类型
	 */
	private int ncklx;
	/**
	 * 方式
	 */
	private int nfs;
	/**
	 * 商品内码
	 */
	private int nspid;
	/**
	 * 批号
	 */
	private String sph;
	/**
	 * 有效期至
	 */
	private Date dyxqz;
	/**
	 * 生产日期
	 */
	private Date dscrq;
	/**
	 * 数量
	 */
	private int n4sl;
	/**
	 * 时间戳
	 */
	private String ssjc;
	/**
	 * ERP回写时间戳
	 */
	private String combrain_times;
	
	private int nzh;
	/**
	 * 操作员id
	 */
	private String sczyid;
	/**
	 * 操作员名称
	 */
	private String sczymc;
	private DrugBean drugBean;
	private int line;

	public int getLine() {
		return line;
	}

	public void setLine(int line) {
		this.line = line;
	}

	public int getNzh() {
		return nzh;
	}

	public void setNzh(int nzh) {
		this.nzh = nzh;
	}

	public DrugBean getDrugBean() {
		return drugBean;
	}

	public void setDrugBean(DrugBean drugBean) {
		this.drugBean = drugBean;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNjsbj() {
		return njsbj;
	}

	public void setNjsbj(int njsbj) {
		this.njsbj = njsbj;
	}

	public String getSjsbz() {
		return sjsbz;
	}

	public void setSjsbz(String sjsbz) {
		this.sjsbz = sjsbz;
	}

	public String getSjkspid() {
		return sjkspid;
	}

	public void setSjkspid(String sjkspid) {
		this.sjkspid = sjkspid;
	}

	public int getNcklx() {
		return ncklx;
	}

	public void setNcklx(int ncklx) {
		this.ncklx = ncklx;
	}

	public int getNfs() {
		return nfs;
	}

	public void setNfs(int nfs) {
		this.nfs = nfs;
	}

	public int getNspid() {
		return nspid;
	}

	public void setNspid(int nspid) {
		this.nspid = nspid;
	}

	public String getSph() {
		return sph;
	}

	public void setSph(String sph) {
		this.sph = sph;
	}

	public Date getDyxqz() {
		return dyxqz;
	}

	public void setDyxqz(Date dyxqz) {
		this.dyxqz = dyxqz;
	}

	public Date getDscrq() {
		return dscrq;
	}

	public void setDscrq(Date dscrq) {
		this.dscrq = dscrq;
	}

	public int getN4sl() {
		return n4sl;
	}

	public void setN4sl(int n4sl) {
		this.n4sl = n4sl;
	}

	public String getSsjc() {
		return ssjc;
	}

	public void setSsjc(String ssjc) {
		this.ssjc = ssjc;
	}

	public String getCombrain_times() {
		return combrain_times;
	}

	public void setCombrain_times(String combrain_times) {
		this.combrain_times = combrain_times;
	}

	public String getSczyid() {
		return sczyid;
	}

	public void setSczyid(String sczyid) {
		this.sczyid = sczyid;
	}

	public String getSczymc() {
		return sczymc;
	}

	public void setSczymc(String sczymc) {
		this.sczymc = sczymc;
	}
}
