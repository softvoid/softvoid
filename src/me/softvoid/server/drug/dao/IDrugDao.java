package me.softvoid.server.drug.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;

import cn.itcast.jdbc.TxQueryRunner;
import me.softvoid.server.drug.domain.DrugBean;
import me.softvoid.server.drug.domain.GroupTypeBean;

public interface IDrugDao {
	public QueryRunner qr = new TxQueryRunner();

	/**
	 * 更新药品属性
	 * 
	 * @param drugBean
	 * @throws SQLException
	 */
	public int updateDrugInfor(DrugBean drugBean) throws SQLException;

	/**
	 * 查询所有的药品
	 * 
	 * @param param
	 * @return
	 * @throws SQLException
	 */
	public List<DrugBean> findAllDrug(String param) throws SQLException;

	/**
	 * 根据类型，获得类型名称
	 * 
	 * @param type
	 * @throws SQLException
	 */
	public List<GroupTypeBean> getGroupType(int type) throws SQLException;
}
