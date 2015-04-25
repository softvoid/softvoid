package me.softvoid.server.query.form.domain;

/**
 * 货位
 * 
 * @author 黄校 QQ：599969512 email：softneo@qq.com 创建时间：2015年3月12日 上午8:32:05
 */
public class PlaceBean {
	private String shwid; // 货位编码
	private int nwlsx; // 物流属性
	private int np; // 排
	private int nc; // 层
	private int nl; // 列
	private int nsx; // 货位上限
	private int nxx; // 货位下限

	public String getShwid() {
		return shwid;
	}

	public void setShwid(String shwid) {
		this.shwid = shwid;
	}

	public int getNwlsx() {
		return nwlsx;
	}

	public void setNwlsx(int nwlsx) {
		this.nwlsx = nwlsx;
	}

	public int getNp() {
		return np;
	}

	public void setNp(int np) {
		this.np = np;
	}

	public int getNc() {
		return nc;
	}

	public void setNc(int nc) {
		this.nc = nc;
	}

	public int getNl() {
		return nl;
	}

	public void setNl(int nl) {
		this.nl = nl;
	}

	public int getNsx() {
		return nsx;
	}

	public void setNsx(int nsx) {
		this.nsx = nsx;
	}

	public int getNxx() {
		return nxx;
	}

	public void setNxx(int nxx) {
		this.nxx = nxx;
	}
}
