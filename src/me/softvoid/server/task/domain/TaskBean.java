package me.softvoid.server.task.domain;

import java.util.List;

import me.softvoid.server.domain.PartnerBean;
import me.softvoid.server.stock.domain.StockBean;

/**
 * 任务单主表
 * 
 * @author 作者：黄校 softneo@qq.com
 * @version 时间：2014-11-24 下午2:40:03
 * 
 */
public abstract class TaskBean {
	private String srwid_last; // 上一个任务单
	private int nrwid;
	private String srwlx;
	private String sbz;
	private StockBean stockBean;
	private PartnerBean partnerBean;
	private String nrwfpid;
	private List<ItemTaskBean> itemTaskList; // 用于存放任务明细的
	private String sywyid;
	private String sywymc;
	private String sryid;
	private String srymc;
	private int ndjid;
	private String sjkid;
	private int nrwzt;
	private String dsqsj;
	private String dqrsj;

	public String getDqrsj() {
		return dqrsj;
	}

	public void setDqrsj(String dqrsj) {
		this.dqrsj = dqrsj;
	}

	public String getDsqsj() {
		return dsqsj;
	}

	public void setDsqsj(String dsqsj) {
		this.dsqsj = dsqsj;
	}

	public int getNrwzt() {
		return nrwzt;
	}

	public void setNrwzt(int nrwzt) {
		this.nrwzt = nrwzt;
	}

	public String getSrwid_last() {
		return srwid_last;
	}

	public void setSrwid_last(String srwid_last) {
		this.srwid_last = srwid_last;
	}

	public String getSjkid() {
		return sjkid;
	}

	public void setSjkid(String sjkid) {
		this.sjkid = sjkid;
	}

	public int getNdjid() {
		return ndjid;
	}

	public void setNdjid(int ndjid) {
		this.ndjid = ndjid;
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

	public String getSywyid() {
		return sywyid;
	}

	public void setSywyid(String sywyid) {
		this.sywyid = sywyid;
	}

	public String getSywymc() {
		return sywymc;
	}

	public void setSywymc(String sywymc) {
		this.sywymc = sywymc;
	}

	public List<ItemTaskBean> getItemTaskList() {
		return itemTaskList;
	}

	public void setItemTaskList(List<ItemTaskBean> itemTaskList) {
		this.itemTaskList = itemTaskList;
	}

	public String getNrwfpid() {
		return nrwfpid;
	}

	public void setNrwfpid(String nrwfpid) {
		this.nrwfpid = nrwfpid;
	}

	public PartnerBean getPartnerBean() {
		return partnerBean;
	}

	public void setPartnerBean(PartnerBean partnerBean) {
		this.partnerBean = partnerBean;
	}

	public StockBean getStockBean() {
		return stockBean;
	}

	public void setStockBean(StockBean stockBean) {
		this.stockBean = stockBean;
	}

	public int getNrwid() {
		return nrwid;
	}

	public void setNrwid(int nrwid) {
		this.nrwid = nrwid;
	}

	public String getSrwlx() {
		return srwlx;
	}

	public void setSrwlx(String srwlx) {
		this.srwlx = srwlx;
	}

	public String getSbz() {
		return sbz;
	}

	public void setSbz(String sbz) {
		this.sbz = sbz;
	}
}
