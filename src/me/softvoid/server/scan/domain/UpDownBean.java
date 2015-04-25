package me.softvoid.server.scan.domain;

import java.io.Serializable;
import java.util.Date;

import me.softvoid.server.domain.PartnerBean;

public class UpDownBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 入库单主表实体对象
	 */
	private String sjkid;
	private int norderid;
	private Date dzdrq;
	private String swldwid;
	private PartnerBean partBean;
	private String status;
	private String sywymc;

	public String getSywymc() {
		return sywymc;
	}

	public void setSywymc(String sywymc) {
		this.sywymc = sywymc;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public PartnerBean getPartBean() {
		return partBean;
	}

	public void setPartBean(PartnerBean partBean) {
		this.partBean = partBean;
	}
	
	public String getSwldwid() {
		return swldwid;
	}

	public void setSwldwid(String swldwid) {
		this.swldwid = swldwid;
	}

	public String getSjkid() {
		return sjkid;
	}

	public void setSjkid(String sjkid) {
		this.sjkid = sjkid;
	}

	public int getNorderid() {
		return norderid;
	}

	public void setNorderid(int norderid) {
		this.norderid = norderid;
	}

	public Date getDzdrq() {
		return dzdrq;
	}

	public void setDzdrq(Date dzdrq) {
		this.dzdrq = dzdrq;
	}
}
