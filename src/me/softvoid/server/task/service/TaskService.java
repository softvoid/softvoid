package me.softvoid.server.task.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import me.softvoid.server.task.dao.ITaskDao;
import me.softvoid.server.task.dao.RyrwztDao;
import me.softvoid.server.task.domain.ItemTaskBean;
import me.softvoid.server.task.domain.RyrwztBean;
import me.softvoid.server.task.domain.TaskBean;
import me.softvoid.server.task.down.domain.DownItemTaskBean;
import me.softvoid.server.task.exception.NeoTaskException;
import me.softvoid.server.task.up.domain.UpItemTaskBean;
import me.softvoid.server.user.dao.UserDao;
import me.softvoid.server.user.domian.EmployeeBean;

import org.dom4j.Document;
import org.dom4j.Element;

import cn.itcast.jdbc.JdbcUtils;

public abstract class TaskService {
	protected ITaskDao taskDao;
	protected TaskBean taskBean;
	protected List<? extends TaskBean> taskBeanList;

	/**
	 * dom4j解析xml
	 * 
	 * @param sis
	 * @throws UpTaskException
	 */
	public void parseXml(Document document) throws NeoTaskException {
		Element rootEmt = document.getRootElement();
		Element ryEmt = rootEmt.element("sryid");
		String sryid = ryEmt.getTextTrim();
		int type = Integer.parseInt(rootEmt.attribute("type").getValue());

		List<ItemTaskBean> itemTaskList = new ArrayList<ItemTaskBean>();
		List<Element> nodess = rootEmt.elements("inf");
		/**
		 * 遍历节点，获取节点数据封装到ItemTaskBean中
		 */
		for (Element elm : nodess) {
			if (type == 0)
				itemTaskList.add(addItemBean(elm, new UpItemTaskBean(), type));
			else
				itemTaskList.add(addItemBean(elm, new DownItemTaskBean(), type));
		}
		taskConfirm(itemTaskList, sryid);
	}

	/**
	 * 封装数据到ItemTaskBean中
	 * 
	 * @param elm
	 * @param itemBean
	 * @param type
	 * @return
	 */
	private ItemTaskBean addItemBean(Element elm, ItemTaskBean itemBean, int type) {
		Element rwEmt = elm.element("nrwid");
		itemBean.setNrwid(Integer.parseInt(rwEmt.getTextTrim()));
		Element hhEmt = elm.element("nhh");
		itemBean.setNhh(Integer.parseInt(hhEmt.getTextTrim()));
		Element phEmt = elm.element("sph");
		itemBean.setSph(phEmt.getTextTrim());
		Element hwEmt = elm.element("shwid");
		itemBean.setShwid(hwEmt.getTextTrim());
		Element zjEmt = elm.element("n4zjsl");
		itemBean.setN4zjsl(Integer.parseInt(zjEmt.getTextTrim()));
		Element lsEmt = elm.element("n4lssl");
		itemBean.setN4lssl(Integer.parseInt(lsEmt.getTextTrim()));
		itemBean.setNtype(type);
		return itemBean;
	}

	/**
	 * 确认任务
	 * 
	 * @param itemTaskList
	 * @param sryid
	 * @return
	 * @throws NeoTaskException
	 */
	protected boolean taskConfirm(List<ItemTaskBean> itemTaskList, String sryid)
			throws NeoTaskException {
		int nrwid = ((ItemTaskBean) itemTaskList.get(0)).getNrwid();
		ItemTaskBean itemBean = taskDao.getTaskStatus(nrwid);
		if (Integer.parseInt(itemBean.getSrwlx()) < 3 && itemBean.getNrwzt() != 1)
			throw new NeoTaskException("该任务已被确认，请不要重复确认！");
		if (Integer.parseInt(itemBean.getSrwlx()) > 3)
			if (itemBean.getNrwzt() == 2 || itemBean.getNrwzt() == 4)
				throw new NeoTaskException("该任务已被确认，请不要重复确认！");
		String ssjc = itemBean.getSsjc();
		if (ssjc == null)
			throw new NeoTaskException("任务时间戳为空，请联系系统管理员！");
		try {
			/**
			 * 确认任务
			 */
			EmployeeBean empBean = new UserDao().findByUid(sryid);
			if (empBean == null)
				throw new NeoTaskException("用户名不存在！");

			JdbcUtils.beginTransaction();
			boolean r = taskDao.taskConfirm(itemTaskList, empBean, ssjc);
			if (!r) {
				JdbcUtils.commitTransaction();
				return true;
			} else
				throw new NeoTaskException("任务确认失败！");
		} catch (SQLException e) {
			try {
				JdbcUtils.rollbackTransaction();
				String msg = e.getMessage();
				if (msg.contains("ORA-20003")) {
					String[] s = msg.split("ORA-20003: ")[1].split("\n");
					msg = "ORA" + s[0];
				} else if (e.getMessage().contains("ORA-20005")) {
					/**
					 * 由于后台问题，存在补货未确认的任务，却能领取到拣货任务，最后拣货确认时报错的提示！ 为了给客户端更清晰更友好的提示，在这里做一个转换！
					 */
					String errorMsg = e.getMessage();
					String nspid = errorMsg.substring(errorMsg.indexOf("[") + 1, e.getMessage()
							.indexOf("]"));
					List<RyrwztBean> ryList = new RyrwztDao().findByDrugId(nspid);
					for (RyrwztBean ry : ryList) {
						if (ry.getNrwlx() == 3)
							msg = "ORA" + ry.getSrymc() + "没有确认被动补货任务，请先确认！";
						else
							msg = "ORA" + ry.getSrymc() + "没有确认主动补货任务，请先确认！";
					}
				}
				throw new NeoTaskException(msg);
			} catch (SQLException e1) {
				throw new RuntimeException(e);
			}
		}
	}

	/**
	 * 获得未确认任务。首先获取已索取未确认的，如果有返回，如果没有索取新的任务。
	 * 
	 * @param empNum
	 * @param orderType
	 * @param isGet
	 * @throws NeoTaskException
	 */
	public List<? extends TaskBean> getTaskList(String empNum, String orderType,
			String isGet) throws NeoTaskException {
		try {
			List<String> list = taskDao.getRwidList(empNum, orderType);
			if (list == null || list.size() == 0) {
				taskBeanList = taskDao.getMultiTask(empNum, orderType, isGet);
				if (taskBeanList == null)
					throw new NeoTaskException("没有可索取的任务！");
			} else {
				String srwid = list.toString().replace(", ", ";").replace("[", "")
						.replace("]", "");
				taskBeanList = taskDao.getTaskListByType(empNum, orderType, srwid);
			}
			return taskBeanList;
		} catch (SQLException e) {
			String msg = e.getMessage();
			if (msg.contains("ORA-20003"))
				msg = "复核台已满，请稍候索取...";
			throw new NeoTaskException(msg);
		}
	}

	/**
	 * 根据任务id等查询未确认的任务
	 * 
	 * @param empNum
	 * @param srwid
	 * @param ssfckrw
	 * @return
	 * @throws NeoTaskException
	 */
	public TaskBean getTaskByRwid(String empNum, String srwid, String ssfckrw,
			String isHistory) throws NeoTaskException {
		int nrwid = Integer.parseInt(srwid);
		int nsfckrw = Integer.parseInt(ssfckrw);
		try {
			int count = taskDao.getRwCount(empNum, nrwid, nsfckrw, isHistory);
			if (count != 1)
				throw new NeoTaskException("任务索取失败，请重试！");
			return taskDao.getTaskByType(empNum, ssfckrw, srwid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * 获得新的任务，如果有的话
	 * 
	 * @param empNum
	 * @param orderType
	 * @param isGet
	 * @return
	 * @throws NeoTaskException
	 */
	public List<? extends TaskBean> getNewTask(String empNum, String orderType, String isGet)
			throws NeoTaskException {
		try {
			taskBeanList = taskDao.getMultiTask(empNum, orderType, isGet);
			if (taskBeanList == null)
				throw new NeoTaskException("没有可索取的任务！");
			return taskBeanList;
		} catch (SQLException e) {
			String msg = e.getMessage();
			if (msg.contains("ORA-20003")) {
				msg = "复核台已满，请稍候索取...";
			}
			throw new NeoTaskException(msg);
		}
	}

	public List<? extends TaskBean> getHistoryTask(String empNum, String orderType)
			throws NeoTaskException {
		try {
			taskBeanList = taskDao.getHistoryTask(empNum, orderType);
			if (taskBeanList == null || taskBeanList.size() == 0)
				throw new NeoTaskException("没有可索取的任务！");
			return taskBeanList;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
