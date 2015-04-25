package me.softvoid.server.task.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import me.softvoid.server.task.domain.ItemTaskBean;
import me.softvoid.server.task.domain.TaskBean;
import me.softvoid.server.task.exception.NeoTaskException;
import me.softvoid.server.user.domian.EmployeeBean;

import org.apache.commons.dbutils.QueryRunner;

import cn.itcast.jdbc.TxQueryRunner;

public interface ITaskDao {
	public QueryRunner qr = new TxQueryRunner();

	/**
	 * 多任务重新索取，调用的是EXEC_RWSQ_DB2存储过程
	 * 
	 * @param empNum
	 * @param orderType
	 * @param isGet
	 * @return
	 * @throws SQLException
	 * @throws NeoTaskException
	 */
	public List<? extends TaskBean> getMultiTask(String empNum, String orderType,
			String isGet) throws SQLException, NeoTaskException;

	/**
	 * 获得任务，调用的是EXEC_RWSQ_DB存储过程，已经有多任务索取代替，不建议使用
	 * 
	 * @param empNum
	 * @param type
	 * @return
	 */
	@Deprecated
	public TaskBean getTask(String empNum, String orderType) throws SQLException;

	/**
	 * 上架任务确认 由于参数过多，使用批处理执行。
	 * 
	 * @param itemTaskList
	 * @param empBean
	 * @return
	 * @throws NeoTaskException
	 */
	public boolean taskConfirm(List itemTaskList, EmployeeBean empBean, String ssjc)
			throws SQLException;

	/**
	 * 任务状态
	 * 
	 * @param nrwid
	 * @return
	 */
	public ItemTaskBean getTaskStatus(int nrwid);

	/**
	 * 根据类型索取任务
	 * 
	 * @param empNum
	 * @param type
	 * @param nrwid
	 * @return
	 */
	public TaskBean getTaskByType(String empNum, String orderType, String nrwid);

	/**
	 * 获得任务集合
	 * 
	 * @param empNum
	 * @param orderType
	 * @param nrwid
	 * @return
	 */
	public List<? extends TaskBean> getTaskListByType(String empNum, String orderType,
			String nrwid);

	/**
	 * 把查询的数据转换层TaskBean
	 * 
	 * @param taskMap
	 * @return
	 */
	public TaskBean toTaskBean(Map<String, Object> taskMap);

	/**
	 * 把Map对象转换成JavaBean
	 * 
	 * @param map
	 * @return
	 */
	public ItemTaskBean toItemTask(Map<String, Object> map);

	/**
	 * 1、任务单明细
	 * 
	 * @param nrwid
	 * @return
	 */
	public List<? extends ItemTaskBean> toItemTaskList(String nrwid);

	/**
	 * 2、把任务明细转成JavaBean，由于任务单存在多条明细以及多个JavaBean，需要循环生成多个JavaBean。
	 * 
	 * @param itemMapList
	 * @return
	 */
	public List<? extends ItemTaskBean> toItemTaskMapList(
			List<Map<String, Object>> itemMapList);

	/**
	 * 查询历史任务
	 * 
	 * @param empNum
	 * @param orderType
	 * @return
	 */
	public List<? extends TaskBean> getHistoryTask(String empNum, String orderType)
			throws SQLException;

	/**
	 * 查询已索取未确认的任务
	 * 
	 * @param empNum
	 * @param nrwid
	 * @param nsfckrw
	 * @return
	 */
	public abstract int getRwCount(String empNum, int nrwid, int nsfckrw, String isHistory)
			throws SQLException;

	/**
	 * 获得未确认的任务，入库和出库分开
	 * 
	 * @param empNum
	 *          人员编码
	 * @param nsfckrw
	 *          是否出入任务
	 * @return
	 * @throws SQLException
	 */
	public abstract List<String> getRwidList(String empNum, String nsfckrw)
			throws SQLException;

}
