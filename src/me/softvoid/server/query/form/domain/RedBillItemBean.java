package me.softvoid.server.query.form.domain;

import me.softvoid.server.query.form.dao.DownTaskDispatchBean;

/**
 * 冲红的实体类
 * 
 * @author 黄校 QQ：599969512 email：softneo@qq.com 创建时间：2015年3月12日 上午8:28:07
 */
public class RedBillItemBean {
	// 待处理类型(0：任务生成库存异常 1：拣货确认异常 2：零货复核异常 3:：集货校验异常)
	private String schzt; // 冲红状态
	private String sph; // 批号
	private int njhsl; // 计划数量
	private int nchsl; // 冲红数量
	private PlaceBean placeBean; // 冲红时所在货位
	private DownTaskDispatchBean downTaskDispatch; // 下架任务分配

	public DownTaskDispatchBean getDownTaskDispatch() {
		return downTaskDispatch;
	}

	public void setDownTaskDispatch(DownTaskDispatchBean downTaskDispatch) {
		this.downTaskDispatch = downTaskDispatch;
	}

	public PlaceBean getPlaceBean() {
		return placeBean;
	}

	public void setPlaceBean(PlaceBean placeBean) {
		this.placeBean = placeBean;
	}

	public String getSchzt() {
		return schzt;
	}

	public void setSchzt(String schzt) {
		this.schzt = schzt;
	}

	public String getSph() {
		return sph;
	}

	public void setSph(String sph) {
		this.sph = sph;
	}

	public int getNjhsl() {
		return njhsl;
	}

	public void setNjhsl(int njhsl) {
		this.njhsl = njhsl;
	}

	public int getNchsl() {
		return nchsl;
	}

	public void setNchsl(int nchsl) {
		this.nchsl = nchsl;
	}
}
