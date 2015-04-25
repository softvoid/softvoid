package me.softvoid.server.user.service;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

import me.softvoid.server.exception.NeoDataException;
import me.softvoid.server.exception.NeoOperException;
import me.softvoid.server.factory.DaoFactory;
import me.softvoid.server.user.dao.IUserDao;
import me.softvoid.server.user.domian.EmployeeBean;
import me.softvoid.server.user.domian.MyselfBean;
import me.softvoid.server.user.domian.UserBean;
import me.softvoid.server.user.exception.UserException;
import me.softvoid.server.utils.NeoUtils;

/**
 * 用来处理操作员的类
 * 
 * @author Administrator
 * 
 */
public class UserService {
	private IUserDao userDao = DaoFactory.getUserDao();

	/**
	 * 查询用户信息
	 * 
	 * @param user
	 * @param pwd
	 * @return
	 * @throws NeoOperException
	 * @throws NeoConnException
	 */
	public List<EmployeeBean> queryUser(UserBean userBean) throws NeoOperException,
			NeoConnException {
		List<EmployeeBean> listEmp;
		try {
			listEmp = userDao.queryUser(userBean);
			if (listEmp == null || listEmp.size() == 0)
				throw new NeoOperException("用户名或密码错误！");
			return listEmp;
		} catch (SQLException e) {
			throw new NeoConnException(e);
		}

	}

	/**
	 * 保存图片到数据库中，并生成一个类似QQ号
	 * 
	 * @param userId
	 * @param pic
	 * @throws UserException
	 * @throws NeoDataException
	 */
	public MyselfBean savePicture(String userId, File pic) throws UserException,
			NeoDataException {
		try {
			MyselfBean myself = userDao.getSelfBean(userId);
			boolean isUpdate = true;
			if (myself == null) {
				isUpdate = false; // 不存在时表示是插入，否则更新
				myself = new MyselfBean();
			}
			String uid = (String) userDao.getMaxUserId();
			int num = 0;
			// 如果还不存在帐号，就设置默认10000，并从10001开始顺延
			if (uid == null || uid.trim().isEmpty()) {
				num = 10000;
				myself.setSuid(NeoUtils.getUserId(num));
			} else { // 如果存在，则在该基础上加1；并且只有在该用户的id不存在时才加1
				String myid = myself.getSuid();
				if (myid == null || myid.trim().isEmpty())
					myself.setSuid(NeoUtils.getUserId(Integer.valueOf(uid)));
			}
			EmployeeBean emp = new EmployeeBean();
			emp.setSryid(userId);
			myself.setEmpBean(emp);
			myself.setSfilename(pic.getName());

			int r = userDao.savePicture(isUpdate, myself, pic);
			if (r == 0)
				return userDao.getSelfBean(userId);
			return null;
		} catch (SQLException e) {
			throw new NeoDataException("更换失败！");
		}
	}

	public MyselfBean getMyself(String userId) throws UserException {
		try {
			MyselfBean myselfBean = userDao.getSelfBean(userId);
			if (myselfBean == null)
				throw new UserException("不存在该用户的个人信息！");
			return myselfBean;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
