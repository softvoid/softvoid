package me.softvoid.server.user.domian;

import java.io.InputStream;

/**
 * 个人信息
 * 
 * @author 黄校 QQ：599969512 email：softneo@qq.com 创建时间：2014年12月31日 下午7:23:25
 */
public class MyselfBean {
	private EmployeeBean empBean;
	private String suid;
	private String snickname;
	private String sgender;
	private int nage;
	private String sadd;
	private int nlevel;
	private int nvip;
	private String sfilename;
	private InputStream bimg;
	private String smemo;

	public InputStream getBimg() {
		return bimg;
	}

	public void setBimg(InputStream bimg) {
		this.bimg = bimg;
	}

	public String getSfilename() {
		return sfilename;
	}

	public void setSfilename(String sfilename) {
		this.sfilename = sfilename;
	}

	public EmployeeBean getEmpBean() {
		return empBean;
	}

	public void setEmpBean(EmployeeBean empBean) {
		this.empBean = empBean;
	}

	public String getSuid() {
		return suid;
	}

	public void setSuid(String suid) {
		this.suid = suid;
	}

	public String getSnickname() {
		return snickname;
	}

	public void setSnickname(String snickname) {
		this.snickname = snickname;
	}

	public String getSgender() {
		return sgender;
	}

	public void setSgender(String sgender) {
		this.sgender = sgender;
	}

	public int getNage() {
		return nage;
	}

	public void setNage(int nage) {
		this.nage = nage;
	}

	public String getSadd() {
		return sadd;
	}

	public void setSadd(String sadd) {
		this.sadd = sadd;
	}

	public int getNlevel() {
		return nlevel;
	}

	public void setNlevel(int nlevel) {
		this.nlevel = nlevel;
	}

	public int getNvip() {
		return nvip;
	}

	public void setNvip(int nvip) {
		this.nvip = nvip;
	}

	public String getSmemo() {
		return smemo;
	}

	public void setSmemo(String smemo) {
		this.smemo = smemo;
	}

}
