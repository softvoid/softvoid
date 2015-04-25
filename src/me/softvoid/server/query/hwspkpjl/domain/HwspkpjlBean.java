package me.softvoid.server.query.hwspkpjl.domain;

import java.util.Date;

import me.softvoid.server.drug.domain.DrugBean;
import me.softvoid.server.stock.domain.StockBean;

;

/**
 * 货位帐页
 * 
 * @author 黄校 QQ：599969512 email：softneo@qq.com 创建时间：2014年11月11日 下午3:11:49
 */
public class HwspkpjlBean {
	private String sgsid;
	private String sckid;
	private String shwid;
	private int nspid;
	private String sph;
	private int n4hwsl;
	private int n4kcksl;
	private int n4bhsl;
	private String sdydh;
	private int ndyhh;
	private int ndjlx;
	private Date dgxrq;
	private String sryid;
	private String srymc;
	private String sbmid;
	private String sbmmc;
	private String sbz;
	private int n4bhsl_kck;
	private String sjkid;
	private int nseq_id;
	private int nrwfpid;
	private int nsfckrw;
	private String swldwid;
	private StockBean ckxxBean;
	private DrugBean drugBean;
	private int line;

	public int getLine() {
		return line;
	}

	public void setLine(int line) {
		this.line = line;
	}

	public DrugBean getDrugBean() {
		return drugBean;
	}

	public void setDrugBean(DrugBean drugBean) {
		this.drugBean = drugBean;
	}

	public StockBean getCkxxBean() {
		return ckxxBean;
	}

	public void setCkxxBean(StockBean ckxxBean) {
		this.ckxxBean = ckxxBean;
	}

	public String getSgsid() {
		return sgsid;
	}

	public void setSgsid(String sgsid) {
		this.sgsid = sgsid;
	}

	public String getSckid() {
		return sckid;
	}

	public void setSckid(String sckid) {
		this.sckid = sckid;
	}

	public String getShwid() {
		return shwid;
	}

	public void setShwid(String shwid) {
		this.shwid = shwid;
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

	public int getN4bhsl() {
		return n4bhsl;
	}

	public void setN4bhsl(int n4bhsl) {
		this.n4bhsl = n4bhsl;
	}

	public String getSdydh() {
		return sdydh;
	}

	public void setSdydh(String sdydh) {
		this.sdydh = sdydh;
	}

	public int getNdyhh() {
		return ndyhh;
	}

	public void setNdyhh(int ndyhh) {
		this.ndyhh = ndyhh;
	}

	public int getNdjlx() {
		return ndjlx;
	}

	public void setNdjlx(int ndjlx) {
		this.ndjlx = ndjlx;
	}

	public Date getDgxrq() {
		return dgxrq;
	}

	public void setDgxrq(Date dgxrq) {
		this.dgxrq = dgxrq;
	}

	public String getSryid() {
		return sryid;
	}

	public void setSryid(String sryid) {
		this.sryid = sryid;
	}

	public String getSrymc() {
		return srymc;
	}

	public void setSrymc(String srymc) {
		this.srymc = srymc;
	}

	public String getSbmid() {
		return sbmid;
	}

	public void setSbmid(String sbmid) {
		this.sbmid = sbmid;
	}

	public String getSbmmc() {
		return sbmmc;
	}

	public void setSbmmc(String sbmmc) {
		this.sbmmc = sbmmc;
	}

	public String getSbz() {
		return sbz;
	}

	public void setSbz(String sbz) {
		this.sbz = sbz;
	}

	public int getN4bhsl_kck() {
		return n4bhsl_kck;
	}

	public void setN4bhsl_kck(int n4bhsl_kck) {
		this.n4bhsl_kck = n4bhsl_kck;
	}

	public String getSjkid() {
		return sjkid;
	}

	public void setSjkid(String sjkid) {
		this.sjkid = sjkid;
	}

	public int getNseq_id() {
		return nseq_id;
	}

	public void setNseq_id(int nseq_id) {
		this.nseq_id = nseq_id;
	}

	public int getNrwfpid() {
		return nrwfpid;
	}

	public void setNrwfpid(int nrwfpid) {
		this.nrwfpid = nrwfpid;
	}

	public int getNsfckrw() {
		return nsfckrw;
	}

	public void setNsfckrw(int nsfckrw) {
		this.nsfckrw = nsfckrw;
	}

	public String getSwldwid() {
		return swldwid;
	}

	public void setSwldwid(String swldwid) {
		this.swldwid = swldwid;
	}

}
