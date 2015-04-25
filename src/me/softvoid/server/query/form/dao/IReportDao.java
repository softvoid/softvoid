package me.softvoid.server.query.form.dao;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import me.softvoid.server.query.form.domain.RedBillItemBean;

import org.apache.commons.dbutils.QueryRunner;

import cn.itcast.jdbc.TxQueryRunner;

/**
 * 报表查询接口
 * 
 * @author 黄校 QQ：599969512 email：softneo@qq.com 创建时间：2015年3月12日 上午8:23:18
 */
public interface IReportDao {
	public QueryRunner qr = new TxQueryRunner();

	/**
	 * 查询冲红的明细
	 * 
	 * @param dzdrq
	 *          制单日期
	 * @return
	 * @throws SQLException
	 */
	public List<RedBillItemBean> redBill(Date dzdrq) throws SQLException;
}
