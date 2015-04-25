package me.softvoid.server.task.down.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import me.softvoid.server.domain.PartnerBean;
import me.softvoid.server.drug.domain.DrugBean;
import me.softvoid.server.stock.domain.StockBean;
import me.softvoid.server.task.dao.TaskDao;
import me.softvoid.server.task.domain.ItemTaskBean;
import me.softvoid.server.task.domain.TaskBean;
import me.softvoid.server.task.down.domain.DownItemTaskBean;
import me.softvoid.server.task.down.domain.DownTaskBean;
import me.softvoid.server.task.exception.NeoTaskException;
import me.softvoid.server.user.domian.EmployeeBean;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import cn.itcast.commons.CommonUtils;

public class DownTaskDao extends TaskDao {

	{
		sbSelect = new StringBuilder(
				"select g.nckdid as \"ndjid\", a.sckid_y as \"sckid\", a.sckid_m as \"sckid_desc\", e.sckmc as \"sckmc\", "
						+ "(select sckmc from ck where a.sgsid=sgsid and a.SCKID_M=sckid) as \"sckmc_desc\",a.nrwzt \"nrwzt\", "
						+ "a.nrwlx||'。'||decode(a.nrwlx, 0,'整件拣货', 1,'零货拣货',  2,'整零合一', 3,'波次补货',"
						+ " 4,'主动补货', 5,'货位移库') as \"srwlx\",a.ssjrid as \"ssjrid\",a.ssjrmc \"ssjrmc\",a.sxjrid as \"sryid\",a.sxjrmc \"srymc\","
						+ "to_char(a.dsqsj,'yyyy-mm-dd hh24:mi:ss') as \"dsqsj\",to_char(a.dqrsj,'yyyy-mm-dd hh24:mi:ss') as \"dqrsj\", "
						+ "to_char(a.dsqsj_sj,'yyyy-mm-dd hh24:mi:ss') as \"dsqsj_sj\",to_char(a.dqrsj_sj,'yyyy-mm-dd hh24:mi:ss') as \"dqrsj_sj\", "
						+ "((select SDJQZ from zddjbh where sbm = 'XJRWFPRWD' and zddjbh.sgsid = a.sgsid)||lpad(a.nrwid, 8, '0')) as \"nrwid\","
						+ "g.swldwid as \"swldwid\", g.sdwmc as \"sdwmc\", (case when a.nrwlx = 1 then g.snfhzcw else ' ' end)  as \"sfhtid\","
						+ "g.YWYMC as \"sywymc\", g.sjkid as \"sjkid\",g.sbz as \"sbz\" "
						+ " from XJRWFPRWD a left join ck e on a.sckid_y = e.sckid and a.sgsid = e.sgsid "
						+ "left join ck f on a.sckid_m = f.sckid and a.sgsid = f.sgsid "
						+ "left join (select CKJHD.SJKID, ckjhd.nckdid, CKJHD.DZDRQ,CKJHD.SYWYMC as YWYMC,"
						+ " XJRWFP.SYWY, XJRWFP.SZDR, RYXX.SRYMC as SZDRMC, XJRWFP.SYWYMC, XJRWFP.snfhzcw,"
						+ "XJRWFP.NRWFPID, XJRWFP.SWLDWID, XJRWFP.SGSID, WLDW.SDWMC, BM.SBMID, BM.SBMMC, XJRWFP.SFHTBH,CKJHD.SBZ  from "
						+ " XJRWFP left join WLDW on XJRWFP.SWLDWID = WLDW.SWLDWID and XJRWFP.SGSID = WLDW.SGSID "
						+ "left join BM on XJRWFP.SBMID = BM.SBMID and XJRWFP.SGSID = BM.SGSID "
						+ "left join RYXX on XJRWFP.SZDR = RYXX.SRYID and XJRWFP.SGSID = RYXX.SGSID "
						+ "left join CKJHD on XJRWFP.NJKID = CKJHD.NCKDID and XJRWFP.NRWFPLX IN (0,1) and XJRWFP.SGSID = CKJHD.SGSID ) g"
						+ " on a.nrwfpid = g.nrwfpid and a.sgsid = g.sgsid ");
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
	public DownTaskBean getTaskByType(String empNum, String orderType, String nrwid) {
		try {
			sbSql = sbSelect.append(sbWhere);
			// 由于查询出两个任务单id，分割后查询第一个
			String[] rw = nrwid.split(",");
			Map<String, Object> taskMap = qr.query(sbSql.toString(), new MapHandler(), rw[0]);
			if (taskMap == null)
				return null;
			DownTaskBean downTaskBean = this.toTaskBean(taskMap);
			if (rw.length == 2)
				downTaskBean.setSrwid_last(rw[1]);
			downTaskBean.setDownItemTaskList(this.toItemTaskList(rw[0]));
			return downTaskBean;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 出库任务
	 * 
	 * @param taskMap
	 * @return
	 */
	@Override
	public DownTaskBean toTaskBean(Map<String, Object> taskMap) {
		DownTaskBean downTaskBean = CommonUtils.toBean(taskMap, DownTaskBean.class);
		StockBean stockBean = CommonUtils.toBean(taskMap, StockBean.class);
		PartnerBean partnerBean = CommonUtils.toBean(taskMap, PartnerBean.class);
		downTaskBean.setStockBean(stockBean);
		downTaskBean.setPartnerBean(partnerBean);
		return downTaskBean;
	}

	/**
	 * 任务单明细
	 * 
	 * @param nrwid
	 * @return
	 */
	@Override
	public List<DownItemTaskBean> toItemTaskList(String nrwid) {
		try {
			sbSql = new StringBuilder(
					"select a.nrwfpid as \"nrwfpid\",a.nhh as \"nhh\",b.NSPID as \"nspid\",b.SSPBM as \"sspbm\","
							+ " trim(b.sspmc) as \"sspmc\", trim(b.sspgg) as \"sspgg\", to_char(a.dscrq,'YYYY-MM-DD') as \"dscrq\", "
							+ " trim(b.sspcd) as \"sspcd\",b.njlgg as \"njlgg\", trim(b.sjldw) as \"sjldw\", b.nsfjg as \"nsfjg\", "
							+ " to_char(a.dyxqz,'YYYY-MM-DD') as \"dyxqz\",trim(a.sph) as \"sph\", "
							+ " a.n4zjsl as \"n4zjsl\", a.n4lssl as \"n4lssl\",a.n4jhjs as \"n4jhsl\",trim(a.shwid) as \"shwid\", "
							+ " c.sckid_y as \"sckid\",trim(d.sckmc) as \"sckmc\", c.sckid_m as \"sckid_desc\", "
							+ " a.N4ZJSL_JHQR as \"n4zjsl_qr\", a.N4LSSL_JHQR as \"n4lssl_qr\",a.SHWID_BHSJ as \"shwid_desc\","
							+ " e.sckmc as \"sckmc_desc\",c.nrwid as \"nrwid\","
							+ " c.nrwlx||'。'||decode(c.nrwlx, 0, '整件下架', 1, '零货下架', 2, '整零合一下架',3,'波次补货',4,'主动补货',5,'移库拣货') AS \"srwlx\""
							+ " from xjrwfpmx a, spxx b, xjrwfprwd c, ck d, ck e, hwxx f,xjrwfp g"
							+ " WHERE a.nspid = b.nspid "
							+ " AND a.nrwid = c.nrwid(+) AND a.sgsid = c.sgsid(+) "
							+ " AND a.sckid = d.sckid(+) AND a.sgsid = d.sgsid(+) "
							+ " AND a.sckid_m = e.sckid(+) AND a.sgsid = e.sgsid(+) "
							+ " and a.sgsid = f.sgsid(+) and a.shwid = f.shwid(+) and a.nrwfpid=g.nrwfpid(+) "
							+ " AND c.nrwid =? order by a.nrwfpid,f.np,f.nl,f.nc,f.shwid");
			List<Map<String, Object>> itemMapList = qr.query(sbSql.toString(),
					new MapListHandler(), nrwid);
			return this.toItemTaskMapList(itemMapList);
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
	public List<DownItemTaskBean> toItemTaskMapList(List<Map<String, Object>> itemMapList) {
		List<DownItemTaskBean> itemTaskList = new ArrayList<DownItemTaskBean>();
		for (Map<String, Object> map : itemMapList) {
			DownItemTaskBean itemBean = this.toItemTask(map);
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
	public DownItemTaskBean toItemTask(Map<String, Object> map) {
		DownItemTaskBean downItemTaskBean = CommonUtils.toBean(map, DownItemTaskBean.class);
		DrugBean goodsBean = CommonUtils.toBean(map, DrugBean.class);
		downItemTaskBean.setDrugBean(goodsBean);
		return downItemTaskBean;
	}

	/**
	 * 上架任务确认 由于参数过多，使用批处理执行。
	 * 
	 * @param itemTaskList
	 * @param empBean
	 * @return
	 * @throws NeoTaskException
	 */
	@Override
	public boolean taskConfirm(List itemTaskList, EmployeeBean empBean, String ssjc)
			throws SQLException {
		String sql = "update xjrwfpmx set N4LSSL_JHQR=?,N4ZJSL_JHQR=? where NRWID=? and NHH=?";
		// 二维数组，左边表示二维数组的1维数组的个数，右边表示1维数组的元素的个数
		Object[][] params = new Object[itemTaskList.size()][];
		for (int i = 0; i < itemTaskList.size(); i++) {
			ItemTaskBean itemTaskBean = (ItemTaskBean) itemTaskList.get(i);
			params[i] = new Object[] { itemTaskBean.getN4lssl(), itemTaskBean.getN4zjsl(),
					itemTaskBean.getNrwid(), itemTaskBean.getNhh() };
		}
		return super.taskConfirm(itemTaskList, empBean, ssjc, sql, params);
	}

	@Override
	public ItemTaskBean getTaskStatus(int nrwid) {
		String sql = "select nrwlx \"srwlx\", ssjc \"ssjc\", nrwzt \"nrwzt\" from xjrwfprwd where nrwid=?";
		try {
			return qr.query(sql, new BeanHandler<DownItemTaskBean>(DownItemTaskBean.class),
					nrwid);
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
			List<DownTaskBean> list = new ArrayList<DownTaskBean>();
			for (String srwid : rw) {
				Map<String, Object> taskMap = qr.query(sbSql.toString(), new MapHandler(), srwid);
				if (taskMap == null)
					return null;
				DownTaskBean downTaskBean = this.toTaskBean(taskMap);
				list.add(downTaskBean);
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
				" where ((a.nrwlx in(0,1,2) and a.nrwzt>=2) or (a.nrwlx in(3,4,5) and a.nrwzt in(2,4)))"
						+ "and (sxjrid=? or ssjrid=?) and trunc(sysdate-a.dsqsj)<=7 order by dqrsj desc,dqrsj_sj desc");
		// 添加查询条件
		sbSelect = sbSelect.append(sbWhere);
		// 外层查询
		sbSql = new StringBuilder("select \"ndjid\", \"sckid\", \"sckid_desc\",\"sckmc\","
				+ "\"sckmc_desc\",\"nrwzt\",\"srwlx\",\"dsqsj\","
				+ "\"dqrsj\",\"dsqsj_sj\",\"dqrsj_sj\",\"nrwid\","
				+ "\"swldwid\",\"sdwmc\",\"sfhtid\",\"sywymc\",\"sjkid\","
				+ "\"sbz\",\"ssjrid\",\"ssjrmc\",\"sryid\",\"srymc\" from (");
		// 外层查询条件
		String sqlWhere = ") where rownum<=20";
		// 最后拼接
		sbSql.append(sbSelect).append(sqlWhere);
		List<DownTaskBean> downTaskBeanList = new ArrayList<DownTaskBean>();
		for (Map<String, Object> map : qr.query(sbSql.toString(), new MapListHandler(),
				empNum, empNum)) {
			downTaskBeanList.add(toTaskBean(map));
		}
		return downTaskBeanList;
	}

}
