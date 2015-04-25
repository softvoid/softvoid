package me.softvoid.server.query.ranking.domain;

import me.softvoid.server.user.domian.MyselfBean;

/**
 * 
 * @author：黄校
 * @contact：softneo@qq.com
 * @date： 2015-2-27 下午3:25:39
 * @see：工作量排行榜
 */
public class RankingBean {
	private int position; // 名次
	private MyselfBean myselfBean; // 个人头像昵称等
	private int sjtms;
	private double sjjs;
	private int xjtms;
	private double xjjs;
	private int bhtms;
	private double bhjs;
	private int zdbhtms;
	private double zdbhjs;
	private double ykrws;
	private double yktms;
	private double ykjs;
	// 用于标记是否是按整件排序，还是按零散排序
	private int flag;

	public double getYkrws() {
		return ykrws;
	}

	public void setYkrws(double ykrws) {
		this.ykrws = ykrws;
	}

	public double getYktms() {
		return yktms;
	}

	public void setYktms(double yktms) {
		this.yktms = yktms;
	}

	public double getYkjs() {
		return ykjs;
	}

	public void setYkjs(double ykjs) {
		this.ykjs = ykjs;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public MyselfBean getMyselfBean() {
		return myselfBean;
	}

	public void setMyselfBean(MyselfBean myselfBean) {
		this.myselfBean = myselfBean;
	}

	public int getSjtms() {
		return sjtms;
	}

	public void setSjtms(int sjtms) {
		this.sjtms = sjtms;
	}

	public double getSjjs() {
		return sjjs;
	}

	public void setSjjs(double sjjs) {
		this.sjjs = sjjs;
	}

	public int getXjtms() {
		return xjtms;
	}

	public void setXjtms(int xjtms) {
		this.xjtms = xjtms;
	}

	public double getXjjs() {
		return xjjs;
	}

	public void setXjjs(double xjjs) {
		this.xjjs = xjjs;
	}

	public int getBhtms() {
		return bhtms;
	}

	public void setBhtms(int bhtms) {
		this.bhtms = bhtms;
	}

	public double getBhjs() {
		return bhjs;
	}

	public void setBhjs(double bhjs) {
		this.bhjs = bhjs;
	}

	public int getZdbhtms() {
		return zdbhtms;
	}

	public void setZdbhtms(int zdbhtms) {
		this.zdbhtms = zdbhtms;
	}

	public double getZdbhjs() {
		return zdbhjs;
	}

	public void setZdbhjs(double zdbhjs) {
		this.zdbhjs = zdbhjs;
	}

}
