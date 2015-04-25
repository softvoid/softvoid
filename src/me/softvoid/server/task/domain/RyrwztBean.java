package me.softvoid.server.task.domain;

import java.util.Date;

/**
 * 人员任务状态
 * 
 * @author 黄校 QQ：599969512 email：softneo@qq.com 创建时间：2015年3月11日 下午10:10:55
 */
public class RyrwztBean {
	String sgsid; // 公司id
	int nbcid; // 波次id
	int nrwfpid; // 任务分配id
	int nrwid; // 任务id
	int ndjid; // 单据id
	Date dgxsj; // 修改日期
	String sryid; // 人员id
	String srymc; // 人员名称
	int nrwlx; // 任务类型
	int nrwzt; // 任务状态
	int nsfckrw; // 是否出库任务
	String sbz; // 备注

	public String getSgsid() {
		return sgsid;
	}

	public void setSgsid(String sgsid) {
		this.sgsid = sgsid;
	}

	public int getNbcid() {
		return nbcid;
	}

	public void setNbcid(int nbcid) {
		this.nbcid = nbcid;
	}

	public int getNrwfpid() {
		return nrwfpid;
	}

	public void setNrwfpid(int nrwfpid) {
		this.nrwfpid = nrwfpid;
	}

	public int getNrwid() {
		return nrwid;
	}

	public void setNrwid(int nrwid) {
		this.nrwid = nrwid;
	}

	public int getNdjid() {
		return ndjid;
	}

	public void setNdjid(int ndjid) {
		this.ndjid = ndjid;
	}

	public Date getDgxsj() {
		return dgxsj;
	}

	public void setDgxsj(Date dgxsj) {
		this.dgxsj = dgxsj;
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

	public int getNrwlx() {
		return nrwlx;
	}

	public void setNrwlx(int nrwlx) {
		this.nrwlx = nrwlx;
	}

	public int getNrwzt() {
		return nrwzt;
	}

	public void setNrwzt(int nrwzt) {
		this.nrwzt = nrwzt;
	}

	public int getNsfckrw() {
		return nsfckrw;
	}

	public void setNsfckrw(int nsfckrw) {
		this.nsfckrw = nsfckrw;
	}

	public String getSbz() {
		return sbz;
	}

	public void setSbz(String sbz) {
		this.sbz = sbz;
	}

}
