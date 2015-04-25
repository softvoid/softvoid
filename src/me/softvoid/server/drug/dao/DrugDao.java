package me.softvoid.server.drug.dao;

import java.sql.SQLException;
import java.util.List;

import me.softvoid.server.drug.domain.DrugBean;
import me.softvoid.server.drug.domain.GroupTypeBean;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

public class DrugDao implements IDrugDao {

	/**
	 * 更新药品属性
	 * 
	 * @param drugBean
	 * @throws SQLException
	 */
	public int updateDrugInfor(DrugBean drugBean) throws SQLException {
		String sql = "update spxx set ssptm=?, nwlsx=?," + "sfzlx=?, nlx=? where nspid=?";
		Object[] params = { drugBean.getSsptm(), drugBean.getNwlsx(), drugBean.getSfzlx(),
				drugBean.getNlx(), drugBean.getNspid() };
		return qr.update(sql, params);
	}

	/**
	 * 查询所有的药品
	 * 
	 * @param param
	 * @return
	 * @throws SQLException
	 */
	public List<DrugBean> findAllDrug(String param) throws SQLException {
		String sql = "select nspid \"nspid\", sspbm \"sspbm\", sspmc \"sspmc\", sspgg \"sspgg\","
				+ "sspcd \"sspcd\", ssptm \"ssptm\", spjwh \"spzwh\",njlgg \"njlgg\", nwlsx \"nwlsx\","
				+ "sfzlx \"sfzlx\", nlx \"nlx\", nsfjg \"nsfjg\", sjldw \"sjldw\" "
				+ "from spxx where sspbm like ? ||'%' or ssptm = ? or "
				+ "szjm like ?||'%' or spjwh like '%'||?";
		Object[] params = { param, param, param, param };
		return qr.query(sql, new BeanListHandler(DrugBean.class), params);
	}

	/**
	 * 根据类型，获得类型名称
	 * 
	 * @param type
	 * @throws SQLException
	 */
	public List<GroupTypeBean> getGroupType(int type) throws SQLException {
		String sql = "select slxid, slxmc from zdylx where nlx=? order by slxid";
		return qr.query(sql, new BeanListHandler(GroupTypeBean.class), type);
	}

	public DrugBean findById(String nspid) throws SQLException {
		String sql = "select * from spxx where nspid = ?";
		return qr.query(sql, new BeanHandler<DrugBean>(DrugBean.class));
	}
}
