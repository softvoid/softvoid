package me.softvoid.server.task.domain;

import me.softvoid.server.drug.domain.DrugBean;

/**
 * 任务单明细表
 * 
 * @author 黄校 QQ：599969512 email：softneo@qq.com 创建时间：2014年11月25日 上午10:18:42
 */
public abstract class ItemTaskBean {
	private int nrwid;
	private String sph;
	private String dscrq;
	private String dyxqz;
	private int n4zjsl;
	private int n4lssl;
	private int n4jhsl;
	private int n4sssl;
	private String shwid;
	private DrugBean drugBean;
	private String sjkph; // 接口批号
	private int nrwfpid;
	private int nhh;
	private int ntype;	//0表示入库、1表示出库
	private String ssjc;	//时间戳
	private String srwlx;
	private int nrwzt;

	public int getNrwzt() {
		return nrwzt;
	}

	public void setNrwzt(int nrwzt) {
		this.nrwzt = nrwzt;
	}

	public String getSrwlx() {
		return srwlx;
	}

	public void setSrwlx(String srwlx) {
		this.srwlx = srwlx;
	}

	public String getSsjc() {
		return ssjc;
	}

	public void setSsjc(String ssjc) {
		this.ssjc = ssjc;
	}

	public int getNtype() {
		return ntype;
	}

	public void setNtype(int ntype) {
		this.ntype = ntype;
	}

	public int getNhh() {
		return nhh;
	}

	public void setNhh(int nhh) {
		this.nhh = nhh;
	}

	public String getSjkph() {
		return sjkph;
	}

	public void setSjkph(String sjkph) {
		this.sjkph = sjkph;
	}

	public int getNrwfpid() {
		return nrwfpid;
	}

	public void setNrwfpid(int nrwfpid) {
		this.nrwfpid = nrwfpid;
	}

	public DrugBean getDrugBean() {
		return drugBean;
	}

	public void setDrugBean(DrugBean drugBean) {
		this.drugBean = drugBean;
	}

	public String getShwid() {
		return shwid;
	}

	public void setShwid(String shwid) {
		this.shwid = shwid;
	}

	public int getNrwid() {
		return nrwid;
	}

	public void setNrwid(int nrwid) {
		this.nrwid = nrwid;
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

	public int getN4zjsl() {
		return n4zjsl;
	}

	public void setN4zjsl(int n4zjsl) {
		this.n4zjsl = n4zjsl;
	}

	public int getN4lssl() {
		return n4lssl;
	}

	public void setN4lssl(int n4lssl) {
		this.n4lssl = n4lssl;
	}

	public int getN4jhsl() {
		return n4jhsl;
	}

	public void setN4jhsl(int n4jhsl) {
		this.n4jhsl = n4jhsl;
	}

	public int getN4sssl() {
		return n4sssl;
	}

	public void setN4sssl(int n4sssl) {
		this.n4sssl = n4sssl;
	}

}
