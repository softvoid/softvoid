package me.softvoid.server.user.dao;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

import me.softvoid.server.user.domian.EmployeeBean;
import me.softvoid.server.user.domian.MyselfBean;
import me.softvoid.server.user.domian.UserBean;

import org.apache.commons.dbutils.QueryRunner;

import cn.itcast.jdbc.TxQueryRunner;

/**
 * 用户数据操作接口
 * 
 * @author 黄校 QQ：599969512 email：softneo@qq.com 创建时间：2014年10月31日 下午3:22:13
 */
public interface IUserDao {
	public QueryRunner qr = new TxQueryRunner();

	/**
	 * 查询用户
	 * 
	 * @param operBean
	 * @return
	 */
	public List<EmployeeBean> queryUser(UserBean operBean) throws SQLException;

	/**
	 * 根据用户编码查询用户
	 * 
	 * @param sryid
	 * @return
	 */
	public EmployeeBean findByUid(String sryid) throws SQLException;

	/**
	 * 保存图片到数据库中
	 * 
	 * @param isUpdate
	 * @param userId
	 * @param pic
	 * @throws SQLException
	 */
	public int savePicture(boolean isUpdate, MyselfBean myself, File file)
			throws SQLException;

	/**
	 * 获得个人信息
	 * 
	 * @param userId
	 * @return
	 */
	public MyselfBean getSelfBean(String userId) throws SQLException;

	/**
	 * 获得最大的用户id
	 * 
	 * @return
	 */
	public Object getMaxUserId() throws SQLException;
}
