package me.softvoid.server.query.batch.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;

import cn.itcast.jdbc.TxQueryRunner;
import me.softvoid.server.domain.PageBean;
import me.softvoid.server.query.batch.domain.PhtzBean;

/**
 * 批号调整接口
 * 
 * @author 黄校 QQ：599969512 email：softneo@qq.com 创建时间：2015年4月3日 下午2:42:51
 */
public interface IPhtzDao {
	public QueryRunner qr = new TxQueryRunner();

	/**
	 * 批号调整
	 * 
	 * @return
	 * @throws SQLException
	 */
	public abstract PageBean<PhtzBean> query(String sspmc, String sspbm, String sph, int currentPage,
			int perPageCount) throws SQLException;

}
