package me.softvoid.server.task.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import me.softvoid.server.task.domain.ItemTaskBean;
import me.softvoid.server.task.domain.TaskBean;
import me.softvoid.server.task.exception.NeoTaskException;
import me.softvoid.server.user.domian.EmployeeBean;

import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.itcast.jdbc.JdbcUtils;

public abstract class TaskDao implements ITaskDao {

	/**
	 * 查询sql语句
	 */
	protected StringBuilder sbSelect;
	/**
	 * 条件sql语句
	 */
	protected StringBuilder sbWhere;
	/**
	 * 结果查询sql语句
	 */
	protected StringBuilder sbSql;

	@Override
	public List<? extends TaskBean> getMultiTask(String empNum, String orderType,
			String isGet) throws SQLException, NeoTaskException {
		Connection conn = null;
		try {
			conn = JdbcUtils.getConnection();
			CallableStatement callStmt = conn
					.prepareCall("{call EXEC_RWSQ_DB2(?,?,?,?,?,?,?,?,?,?,?)}");
			callStmt.setString(1, "0101");
			callStmt.setString(2, empNum);
			callStmt.setString(3, orderType);
			callStmt.setInt(4, 1);
			callStmt.setInt(5, 1);
			callStmt.setString(6, "-1");
			callStmt.setString(7, isGet);
			// 必须先注册输出参数，否则无法获得输出数据
			callStmt.registerOutParameter(8, Types.VARCHAR);
			callStmt.registerOutParameter(9, Types.VARCHAR);
			callStmt.registerOutParameter(10, Types.INTEGER);
			callStmt.registerOutParameter(11, Types.VARCHAR);
			callStmt.execute();
			String nrwid = callStmt.getString(8);
			if (nrwid == null || "-1".equals(nrwid))
				return null;
			return getTaskListByType(empNum, orderType, nrwid);
		} finally {
			if (conn != null)
				JdbcUtils.releaseConnection(conn);
		}
	}

	/**
	 * 索取任务
	 * 
	 * @param empNum
	 * @param type
	 *          任务类型：入库和出库
	 * @return
	 * @throws SQLException
	 */
	public TaskBean getTask(String empNum, String orderType) throws SQLException {
		Connection conn = null;
		try {
			conn = JdbcUtils.getConnection();
			CallableStatement callStmt = conn
					.prepareCall("{call EXEC_RWSQ_DB(?,?,?,?,?,?,?,?,?,?)}");
			callStmt.setString(1, "0101");
			callStmt.setString(2, empNum);
			callStmt.setString(3, orderType);
			callStmt.setInt(4, 1);
			callStmt.setInt(5, 1);
			callStmt.setInt(6, -1); // -1，表示不指定任务单索取
			// 必须先注册输出参数，否则无法获得输出数据
			callStmt.registerOutParameter(7, Types.VARCHAR);
			callStmt.registerOutParameter(8, Types.VARCHAR);
			callStmt.registerOutParameter(9, Types.INTEGER);
			callStmt.registerOutParameter(10, Types.VARCHAR);
			callStmt.execute();
			String nrwid = callStmt.getString(7);
			if ("-1".equals(nrwid))
				return null;
			return getTaskByType(empNum, orderType, nrwid);
		} finally {
			if (conn != null)
				JdbcUtils.releaseConnection(conn);
		}
	}

	/**
	 * 确认任务
	 * 
	 * @param itemTaskList
	 * @param empBean
	 * @param sqlTime
	 * @param sql
	 * @throws TaskException
	 * @throws SQLException
	 */
	protected final boolean taskConfirm(List<ItemTaskBean> itemTaskList,
			EmployeeBean empBean, String ssjc, String sql, Object[][] params)
			throws SQLException {
		Connection conn = null;
		try {
			conn = JdbcUtils.getConnection();
			qr.batch(sql, params);
			CallableStatement callStmt = conn.prepareCall("{call exec_rwqr(?,?,?,?,?,?,?)}");
			callStmt.setString(1, "0101");
			callStmt.setInt(2, ((ItemTaskBean) itemTaskList.get(0)).getNtype());
			callStmt.setInt(3, ((ItemTaskBean) itemTaskList.get(0)).getNrwid());
			callStmt.setString(4, empBean.getSryid());
			callStmt.setString(5, empBean.getSrymc());
			callStmt.setString(6, ssjc);
			callStmt.setInt(7, -1);
			return callStmt.execute();
		} finally {
			if (conn != null)
				JdbcUtils.releaseConnection(conn);
		}
	}

	/**
	 * 获得任务状态 子类如需获得任务状态，需重写该方法
	 * 
	 * @param nrwid
	 * @return
	 */
	public ItemTaskBean getTaskStatus(int nrwid) {
		return null;
	}

	public int getRwCount(String empNum, int nrwid, int nsfckrw, String isHistory)
			throws SQLException {
		String sql = null;
		if ("0".equals(isHistory)) {
			sql = "select count(1) from ryrwzt where sryid=? and nrwid=? and nsfckrw=? and"
					+ "((nrwlx in (0,1,2) and  nrwzt=1) or (nrwlx in (3,4,5) and (nrwzt=1 or nrwzt=3)))";
		} else
			sql = "select count(1) from ryrwzt where sryid=? and nrwid=? and nsfckrw=?";
		Number count = (Number) qr.query(sql, new ScalarHandler<>(), empNum, nrwid, nsfckrw);
		return count.intValue();
	}

	@Override
	public List<String> getRwidList(String empNum, String nsfckrw) throws SQLException {
		String sql = "select nrwid from ryrwzt where sryid=? and nsfckrw=? and"
				+ "((nrwlx in (0,1,2) and  nrwzt=1) or (nrwlx in (3,4,5) and (nrwzt=1 or nrwzt=3)))";
		Connection conn = null;
		try {
			conn = JdbcUtils.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, empNum);
			ps.setString(2, nsfckrw);
			ResultSet rs = ps.executeQuery();
			List<String> list = new ArrayList<String>();
			while (rs.next()) {
				list.add(rs.getString(1));
			}
			return list;
		} finally {
			if (conn != null)
				JdbcUtils.releaseConnection(conn);
		}
	}

}
