package me.softvoid.server.user.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import me.softvoid.server.domain.baseinfor.CompanyBean;
import me.softvoid.server.user.domian.EmployeeBean;
import me.softvoid.server.user.domian.MyselfBean;
import me.softvoid.server.user.domian.PositionBean;
import me.softvoid.server.user.domian.UserBean;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.JdbcUtils;

public class UserDao implements IUserDao {

	@Override
	public List<EmployeeBean> queryUser(UserBean operBean) throws SQLException {
		String sql = "select a.sryid \"sryid\", a.srymc \"srymc\", b.smm \"smm\", "
				+ "b.sgzzmc \"sgzzmc\", c.sgsmc \"sgsmc\", d.nsm \"nsm\", e.nsfrk \"nsfrk\", e.nsfck \"nsfck\""
				+ " from ryxx a, czy b, gsxx c, (select COUNT(1) nsm from gzzqx where sgzzid in "
				+ "(select sgzzid from czy where sczyid=?) and srid in('6202','6203','6205','6210')) d,"
				+ "(SELECT sum(nsfrk) nsfrk, sum(nsfck) nsfck FROM ryzygl WHERE sryid=?) e"
				+ " where a.sryid=b.sczyid and nmjbj=1 and sczyid=? and smm=?";
		List<Map<String, Object>> listMap = qr.query(sql, new MapListHandler(), "0101"
				+ operBean.getUser(), "0101" + operBean.getUser(), "0101" + operBean.getUser(),
				operBean.getPwd());
		return toBeanList(listMap);
	}

	/**
	 * 查询的数据转换成多个对象，然后装载在List集合返回
	 * 
	 * @param listMap
	 * @return
	 */
	private List<EmployeeBean> toBeanList(List<Map<String, Object>> listMap) {
		List<EmployeeBean> beanList = new ArrayList<EmployeeBean>();
		for (Map<String, Object> map : listMap) {
			EmployeeBean empBean = CommonUtils.toBean(map, EmployeeBean.class);
			CompanyBean com = CommonUtils.toBean(map, CompanyBean.class);
			PositionBean pos = CommonUtils.toBean(map, PositionBean.class);
			empBean.setCom(com);
			empBean.setPos(pos);
			beanList.add(empBean);
		}
		return beanList;
	}

	/**
	 * 根据用户编码查询用户
	 * 
	 * @param sryid
	 * @return
	 * @throws SQLException
	 */
	@Override
	public EmployeeBean findByUid(String sryid) throws SQLException {
		String sql = "select b.sryid \"sryid\", srymc \"srymc\" from ryxx a, czy b where a.sryid=b.sczyid and a.sryid=?";
		return qr.query(sql, new BeanHandler<EmployeeBean>(EmployeeBean.class), sryid);
	}

	/**
	 * 保存图片到数据库中
	 * 
	 * @param isUpdate
	 * @param userId
	 * @param pic
	 * @throws SQLException
	 */
	@Override
	public int savePicture(boolean isUpdate, MyselfBean myself, File file)
			throws SQLException {
		if (!file.exists())
			return -1;
		String sql = null;
		if (isUpdate)
			sql = "update myself set sryid=?,suid=?,snickname=?,sgender=?,nage=?,"
					+ "sadd=?,nlevel=?,nvip=?,sfilename=?,bimg=?,smemo=?";
		else
			sql = "insert into myself(sryid,suid,snickname,sgender,nage,sadd,nlevel,nvip,bimg,sfilename,smemo) values(?,?,?,?,?,?,?,?,?,?,?)";
		InputStream is = null;
		try {
			// 以流的格式赋值
			is = new FileInputStream(file);
			PreparedStatement ps = JdbcUtils.getConnection().prepareStatement(sql);
			ps.setString(1, myself.getEmpBean().getSryid());
			ps.setString(2, myself.getSuid());
			ps.setString(3, myself.getSnickname());
			ps.setString(4, myself.getSgender());
			ps.setString(5, myself.getNage() + "");
			ps.setString(6, myself.getSadd());
			ps.setString(7, myself.getNlevel() + "");
			ps.setString(8, myself.getNvip() + "");
			// 注意第3个参数必须是int类型，否则报错
			ps.setBinaryStream(9, is, (int) file.length());
			ps.setString(10, myself.getSfilename());
			ps.setString(11, myself.getSmemo());

			return ps.executeUpdate();
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (is != null)
					is.close();
				file.delete(); // 保存完毕后删除文件
			} catch (IOException e) {
			}
		}

	}

	/**
	 * 获得个人信息
	 * 
	 * @param userId
	 * @return
	 */
	public MyselfBean getSelfBean(String userId) {
		String sql = "select * from myself where sryid=?";
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = JdbcUtils.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, userId);
			ResultSet rs = ps.executeQuery();
			MyselfBean myselfBean = null;
			EmployeeBean empBean = new EmployeeBean();
			while (rs.next()) {
				myselfBean = new MyselfBean();
				empBean.setSryid(rs.getString("sryid"));
				myselfBean.setEmpBean(empBean);
				myselfBean.setSuid(rs.getString("suid"));
				myselfBean.setSnickname(rs.getString("snickname"));
				myselfBean.setSgender(rs.getString("sgender"));
				myselfBean.setNage(rs.getInt("nage"));
				myselfBean.setSadd(rs.getString("sadd"));
				myselfBean.setNlevel(rs.getInt("nlevel"));
				myselfBean.setNvip(rs.getInt("nvip"));
				myselfBean.setSfilename(rs.getString("sfilename"));
				myselfBean.setBimg(rs.getBinaryStream("bimg")); // 获取图片对应的流
				myselfBean.setSmemo(rs.getString("smemo"));
			}
			return myselfBean;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (conn != null)
					JdbcUtils.releaseConnection(conn);
				if (ps != null)
					ps.close();
			} catch (SQLException e) {
			}
		}
	}

	/**
	 * 获得最大的用户id
	 * 
	 * @return
	 */
	public Object getMaxUserId() {
		String sql = "select max(suid) from myself";
		try {
			return qr.query(sql, new ScalarHandler<>());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
