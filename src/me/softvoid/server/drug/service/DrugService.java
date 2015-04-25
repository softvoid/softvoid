package me.softvoid.server.drug.service;

import java.sql.SQLException;
import java.util.List;

import me.softvoid.server.drug.dao.IDrugDao;
import me.softvoid.server.drug.domain.DrugBean;
import me.softvoid.server.drug.domain.GroupTypeBean;
import me.softvoid.server.drug.exception.DrugException;
import me.softvoid.server.factory.DaoFactory;

public class DrugService {
	/**
	 * 面向接口编程。即使实现类变了，只要该类还实现了该接口， 那么只需要通过修改工厂的代码就可以了，否则凡是涉及到该类的代码都要修改
	 */
	private IDrugDao drugDao = DaoFactory.getDrugDao();

	/**
	 * 更新药品信息
	 * 
	 * @param drugBean
	 * @return
	 * @throws DrugException
	 */
	public void updateDrugInfor(DrugBean drugBean) throws DrugException {
		try {
			if (drugDao.updateDrugInfor(drugBean) != 1)
				throw new DrugException("更新数据失败！");
		} catch (SQLException e) {
			throw new DrugException("更新数据失败！");
		}
	}

	/**
	 * 查询
	 * 
	 * @param param
	 * @return
	 * @throws DrugException
	 */
	public List<DrugBean> findAllDrug(String param) throws DrugException {
		try {
			List<DrugBean> listDrug = drugDao.findAllDrug(param);
			if (listDrug == null || listDrug.size() == 0)
				throw new DrugException("没有任何信息！");
			return listDrug;
		} catch (SQLException e) {
			throw new DrugException("查询数据失败！");
		}
	}

	/**
	 * 获得分组类型
	 * 
	 * @return
	 * @throws DrugException
	 */
	public List<GroupTypeBean> getGroupType(int type) throws DrugException {
		try {
			return drugDao.getGroupType(type);
		} catch (SQLException e) {
			throw new DrugException("查询数据失败！");
		}
	}
}
