package me.softvoid.server.domain;

import java.util.List;

/**
 * 分页实体类，用于封装当前页、总页数、总记录数、每页记录数
 * 
 * @author 黄校 QQ：599969512 email：softneo@qq.com 创建时间：2014年11月9日 下午1:26:05
 * @param <T>
 */
public class PageBean<T> {
	private int currentPage; // 当前页
	private int perPageCount; // 每页的记录数
	private int totalCount; // 总记录数
	private int totalPage;
	private List<T> beanList; // 当前页的记录
	private String url;	//存放查询条件的

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<T> getBeanList() {
		return beanList;
	}

	public void setBeanList(List<T> beanList) {
		this.beanList = beanList;
	}

	/**
	 * 计算总页数。通过总记录数和每页的记录数得出总页数。
	 * 
	 * @return
	 */
	public int getTotalPage() {
		totalPage = totalCount / perPageCount;
		return totalCount % perPageCount == 0 ? totalPage : totalPage + 1;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPerPageCount() {
		return perPageCount;
	}

	public void setPerPageCount(int perPageCount) {
		this.perPageCount = perPageCount;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

}
