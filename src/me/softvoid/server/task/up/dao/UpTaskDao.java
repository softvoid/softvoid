package me.softvoid.server.task.up.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.softvoid.server.domain.PartnerBean;
import me.softvoid.server.drug.domain.DrugBean;
import me.softvoid.server.stock.domain.StockBean;
import me.softvoid.server.task.dao.TaskDao;
import me.softvoid.server.task.domain.ItemTaskBean;
import me.softvoid.server.task.domain.TaskBean;
import me.softvoid.server.task.exception.NeoTaskException;
import me.softvoid.server.task.up.domain.UpItemTaskBean;
import me.softvoid.server.task.up.domain.UpTaskBean;
import me.softvoid.server.user.dao.UserDao;
import me.softvoid.server.user.domian.EmployeeBean;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.JdbcUtils;

public class UpTaskDao extends TaskDao {
	{
		sbSelect = new StringBuilder(
				"select a.nrwid as \"nrwid\",b.nrkdid as \"ndjid\",b.sjkid as \"sjkid\",a.nrwzt as \"nrwzt\", "
						+ "r.sryid as \"sryid\", r.srymc as \"srymc\","
						+ "b.Sbz as \"sbz\", c.sckid as \"sckid\", c.sckmc as \"sckmc\",b.swldwid as \"swldwid\",sywymc as \"sywymc\","
						+ "b.sdwmc as \"sdwmc\",a.nrwlx||'。'||decode(a.nrwlx, 0, '整件入库', 1,'零散入库',  2,'整零入库') as \"srwlx\","
						+ "to_char(a.dsqsj,'yyyy-mm-dd hh24:mi:ss') as \"dsqsj\",to_char(a.dqrsj,'yyyy-mm-dd hh24:mi:ss') as \"dqrsj\" "
						+ "from SJRWFPRWD a left join (select SJRWFP.SBMID, BM.SBMMC, SJRWFP.SYWY, YWYXX.SRYMC as SYWYMC, "
						+ "SJRWFP.SZDR, ZDRXX.SRYMC as SZDRMC,RKD.DZDRQ, SJRWFP.NSFQRWC, SJRWFP.NRWFPID, SJRWFP.SWLDWID, "
						+ "SJRWFP.SGSID, WLDW.SDWMC,RKD.SJKID,RKD.NRKLX,nrkdid,((select SDJQZ from zddjbh where "
						+ "sbm = 'RKD' and zddjbh.sgsid = SJRWFP.sgsid)||lpad(RKD.NRKDID, 8, '0')) as SSHDH,"
						+ " SJRWFP.Sbz from SJRWFP left join WLDW on SJRWFP.SWLDWID = WLDW.SWLDWID and SJRWFP.SGSID = WLDW.SGSID "
						+ "left join RKD on SJRWFP.NJKID = RKD.NRKDID and SJRWFP.SGSID = RKD.SGSID"
						+ " left join RYXX YWYXX on YWYXX.SRYID = SJRWFP.SYWY and SJRWFP.SGSID = YWYXX.SGSID"
						+ " left join RYXX ZDRXX on ZDRXX.SRYID = SJRWFP.SZDR and SJRWFP.SGSID = ZDRXX.SGSID"
						+ " left join BM on BM.SBMID = SJRWFP.SBMID and BM.SGSID = SJRWFP.SGSID) b "
						+ "on a.nrwfpid = b.nrwfpid and b.sgsid = a.sgsid left join ck c on a.sckid = c.sckid "
						+ "and c.sgsid = a.sgsid left join ryrwzt r on a.nrwid = r.nrwid and r.sgsid = a.sgsid "
						+ "and r.NSFCKRW = 0 ");
		sbWhere = new StringBuilder("where a.nrwid=? ");
	}

	/**
	 * 根据类型索取任务
	 * 
	 * @param empNum
	 * @param type
	 * @param nrwid
	 * @return
	 */
	@Override
	public UpTaskBean getTaskByType(String empNum, String orderType, String nrwid) {
		try {
			sbSql = sbSelect.append(sbWhere);
			// 由于查询出两个任务单id，分割后查询第一个
			String[] rw = nrwid.split(",");
			Map<String, Object> taskMap = qr.query(sbSql.toString(), new MapHandler(), rw[0]);
			if (taskMap == null)
				return null;
			UpTaskBean upTaskBean = toTaskBean(taskMap);
			if (rw.length == 2)
				upTaskBean.setSrwid_last(rw[1]);
			upTaskBean.setUpItemTaskList(toItemTaskList(rw[0]));

			// 由于在任务索取时后台没有更新人员ID和名称，因此在这里单独更新
			String s = "update sjrwfprwd set ssjrid=?, ssjrmc=? where nrwid=?";
			EmployeeBean empBean = new UserDao().findByUid(empNum);
			qr.update(s, empBean.getSryid(), empBean.getSrymc(), rw[0]);
			return upTaskBean;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 把查询出来的数据转成JavaBean，由于有多个JavaBean因此需要生成多个JavaBean。
	 * 
	 * @param taskMap
	 * @return
	 */
	@Override
	public UpTaskBean toTaskBean(Map<String, Object> taskMap) {
		UpTaskBean upTaskBean = CommonUtils.toBean(taskMap, UpTaskBean.class);
		StockBean stockBean = CommonUtils.toBean(taskMap, StockBean.class);
		PartnerBean partnerBean = CommonUtils.toBean(taskMap, PartnerBean.class);
		upTaskBean.setStockBean(stockBean);
		upTaskBean.setPartnerBean(partnerBean);
		return upTaskBean;
	}

	/**
	 * 任务单明细
	 * 
	 * @param nrwid
	 * @return
	 */
	@Override
	public List<UpItemTaskBean> toItemTaskList(String nrwid) {
		try {
			sbSql = new StringBuilder(
					"select a.nhh as \"nhh\", a.nrwfpid as \"nrwfpid\",a.nrwid as \"nrwid\",a.sfphwid as \"sfphwid\","
							+ "a.shwid as \"shwid\",a.sjkph as \"sjkph\",a.sph as \"sph\",a.n4zjsl as \"n4zjsl\","
							+ "a.n4lssl \"n4lssl\",a.n4jhjs as \"n4jhsl\",n4sssl as \"n4sssl\","
							+ "to_char(a.dscrq,'yyyy-mm-dd') as \"dscrq\",to_char(a.dyxqz,'yyyy-mm-dd') as \"dyxqz\", b.nspid as \"nspid\","
							+ "b.nsfjg as \"nsfjg\", b.SSPBM as \"sspbm\",b.SJLDW as \"sjldw\", b.sspmc as \"sspmc\","
							+ "b.sspgg as \"sspgg\", b.sspcd as \"sspcd\",b.njlgg as \"njlgg\" "
							+ "from SJRWFPMX a left join SPXX b on a.nspid = b.nspid "
							+ "left join hwxx d on a.sgsid = d.sgsid and a.shwid = d.shwid "
							+ "where a.nrwid = ? order by d.np,d.nl,d.nc,d.shwid");
			List<Map<String, Object>> itemMapList = qr.query(sbSql.toString(),
					new MapListHandler(), nrwid);
			return toItemTaskMapList(itemMapList);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 把任务明细转成JavaBean，由于任务单存在多条明细以及多个JavaBean，需要循环生成多个JavaBean。
	 * 
	 * @param itemMapList
	 * @return
	 */
	@Override
	public List<UpItemTaskBean> toItemTaskMapList(List<Map<String, Object>> itemMapList) {
		List<UpItemTaskBean> itemTaskList = new ArrayList<UpItemTaskBean>();
		for (Map<String, Object> map : itemMapList) {
			UpItemTaskBean itemBean = toItemTask(map);
			itemTaskList.add(itemBean);
		}
		return itemTaskList;
	}

	/**
	 * 把Map对象转换成JavaBean
	 * 
	 * @param map
	 * @return
	 */
	@Override
	public UpItemTaskBean toItemTask(Map<String, Object> map) {
		UpItemTaskBean upItemTaskBean = CommonUtils.toBean(map, UpItemTaskBean.class);
		DrugBean goodsBean = CommonUtils.toBean(map, DrugBean.class);
		upItemTaskBean.setDrugBean(goodsBean);
		return upItemTaskBean;
	}

	/**
	 * 上架任务确认 由于参数过多，使用批处理执行。
	 * 
	 * @param itemTaskList
	 * @param empBean
	 * @return
	 * @throws NeoTaskException
	 * @throws SQLException
	 */
	@Override
	public boolean taskConfirm(List itemTaskList, EmployeeBean empBean, String ssjc)
			throws SQLException {
		String sql = "update sjrwfpmx set N4LSSL=?,N4ZJSL=?,SHWID=?,SPH=? where NRWID=? and NHH=?";
		// 二维数组，左边表示二维数组的1维数组的个数，右边表示1维数组的元素的个数
		Object[][] params = new Object[itemTaskList.size()][];
		for (int i = 0; i < itemTaskList.size(); i++) {
			ItemTaskBean itemTaskBean = (ItemTaskBean) itemTaskList.get(i);
			params[i] = new Object[] { itemTaskBean.getN4lssl(), itemTaskBean.getN4zjsl(),
					itemTaskBean.getShwid(), itemTaskBean.getSph(), itemTaskBean.getNrwid(),
					itemTaskBean.getNhh() };
		}
		return super.taskConfirm(itemTaskList, empBean, ssjc, sql, params);
	}

	/**
	 * 获得已索取任务数和未索取的任务数
	 * 
	 * @param empNum
	 * @return
	 */
	public List<Map<String, String>> getTaskCount(String empNum) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> otherMap = getOtherTasks();
		if (!otherMap.isEmpty())
			list.add(otherMap);
		return list;
	}

	/**
	 * 查询未索取的任务
	 * 
	 * @return
	 */
	public Map<String, String> getOtherTasks() {
		/**
		 * 查询为索取的任务
		 */
		String sql = "select nrwzt as \"nrwzt\", count(1) as \"c\" from sjrwfprwd "
				+ "where nrwzt =0 group by nrwzt";
		try {
			PreparedStatement ps = JdbcUtils.getConnection().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			Map<String, String> map = new HashMap<String, String>();
			if (rs.next()) {
				map.put("nrwzt", rs.getString(1));
				map.put("c", rs.getString(2));
			} else {
				map.put("nrwzt", "0");
				map.put("c", "0");
			}
			return map;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public ItemTaskBean getTaskStatus(int nrwid) {
		String sql = "select ssjc \"ssjc\", to_char(nrwlx) \"srwlx\", nrwzt \"nrwzt\" from sjrwfprwd where nrwid=?";
		try {
			return qr.query(sql, new BeanHandler<UpItemTaskBean>(UpItemTaskBean.class), nrwid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<? extends TaskBean> getTaskListByType(String empNum, String orderType,
			String nrwid) {
		try {
			sbSql = sbSelect.append(sbWhere);
			// 由于查询出两个任务单id，分割后查询第一个
			String[] rw = nrwid.split(";");
			List<UpTaskBean> list = new ArrayList<UpTaskBean>();
			for (String srwid : rw) {
				Map<String, Object> taskMap = qr.query(sbSql.toString(), new MapHandler(), srwid);
				if (taskMap == null)
					return null;
				UpTaskBean upTaskBean = this.toTaskBean(taskMap);
				list.add(upTaskBean);
			}
			return list;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<? extends TaskBean> getHistoryTask(String empNum, String orderType)
			throws SQLException {
		// 查询条件
		sbWhere = new StringBuilder(
				" where a.nrwlx in(0,1,2) and a.nrwzt>=2 and ssjrid=? and trunc(sysdate-a.dsqsj)<=7 order by dqrsj desc");
		// 添加查询条件
		sbSelect = sbSelect.append(sbWhere);
		// 外层查询
		sbSql = new StringBuilder("select \"ndjid\", \"sckid\", \"sckmc\","
				+ "\"nrwzt\",\"srwlx\",\"dsqsj\",\"dqrsj\",\"nrwid\","
				+ "\"swldwid\",\"sdwmc\",\"sywymc\",\"sjkid\",\"sbz\",\"sryid\",\"srymc\" from (");
		// 外层查询条件
		String sqlWhere = ") where rownum<=20";
		sbSql.append(sbSelect).append(sqlWhere);
		List<UpTaskBean> upTaskBeanList = new ArrayList<UpTaskBean>();
		for (Map<String, Object> map : qr.query(sbSql.toString(), new MapListHandler(),
				empNum)) {
			upTaskBeanList.add(toTaskBean(map));
		}
		return upTaskBeanList;
	}
}
