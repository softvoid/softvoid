package me.softvoid.server.query.form.domain;

import java.util.Date;

import me.softvoid.server.query.form.dao.DownTaskDispatchBean;

public class RedBillBean {
	private int ndclid; // 待处理id
	private DownTaskDispatchBean downTaskBean;
	private DownOrderBean downOrderBean; // 出库单
	private Date dzdrq; // 制单日期

	public Date getDzdrq() {
		return dzdrq;
	}

	public void setDzdrq(Date dzdrq) {
		this.dzdrq = dzdrq;
	}

	public int getNdclid() {
		return ndclid;
	}

	public void setNdclid(int ndclid) {
		this.ndclid = ndclid;
	}

	public DownTaskDispatchBean getDownTaskBean() {
		return downTaskBean;
	}

	public void setDownTaskBean(DownTaskDispatchBean downTaskBean) {
		this.downTaskBean = downTaskBean;
	}

	public DownOrderBean getDownOrderBean() {
		return downOrderBean;
	}

	public void setDownOrderBean(DownOrderBean downOrderBean) {
		this.downOrderBean = downOrderBean;
	}
}
