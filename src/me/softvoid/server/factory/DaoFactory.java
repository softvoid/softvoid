package me.softvoid.server.factory;

import java.io.InputStream;
import java.util.Properties;

import me.softvoid.server.drug.dao.DrugDao;
import me.softvoid.server.query.amount.dao.AmountDao;
import me.softvoid.server.scan.down.dao.DownScanDao;
import me.softvoid.server.scan.up.dao.UpScanDao;
import me.softvoid.server.stock.dao.StockDao;
import me.softvoid.server.task.down.dao.DownTaskDao;
import me.softvoid.server.task.up.dao.UpTaskDao;
import me.softvoid.server.user.dao.UserDao;

/**
 * 数据库访问层工厂，把数据访问层实现类写到配置文件中，这样就无需修改源代码。 把变化的东西写到配置文件中。
 * 
 * @author 黄校 QQ：599969512 email：softvoid@qq.com 创建时间：2014年11月1日 下午7:37:52
 */
public class DaoFactory {

	// 配置属性对象
	private static Properties prop;
	static {
		// 加载配置文件到prop中
		try {

			InputStream inStream = DaoFactory.class.getClassLoader().getResourceAsStream(
					"daoProperty.properties");
			prop = new Properties();
			prop.load(inStream);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 通过配置文件、反射获得UserDao
	 * 
	 * @return
	 */
	public static UserDao getUserDao() {
		/**
		 * 给出配置文件，文件中给出UserDao接口的实现类名称； 然后通过反射完成对象的创建
		 */
		String daoClassName = prop.getProperty("me.softvoid.user.dao.IUserDao");
		// 通过反射创建实现类
		try {
			Class<?> clss = Class.forName(daoClassName);
			return (UserDao) clss.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 通过配置文件、反射获得AmountDao
	 * 
	 * @return
	 */
	public static AmountDao getAmountDao() {
		/**
		 * 给出配置文件，文件中给出UserDao接口的实现类名称； 然后通过反射完成对象的创建
		 */
		String daoClassName = prop.getProperty("me.softvoid.amount.dao.IAmountDao");
		// 通过反射创建实现类
		try {
			Class<?> clss = Class.forName(daoClassName);
			return (AmountDao) clss.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 工厂生成DrugDao类
	 * 
	 * @return
	 */
	public static DrugDao getDrugDao() {
		String className = prop.getProperty("me.softvoid.drug.dao.IDrugDao");
		try {
			Class<?> clss = Class.forName(className);
			return (DrugDao) clss.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 入库任务类
	 * 
	 * @return
	 */
	public UpTaskDao getUpTaskDao() {
		String className = prop.getProperty("me.softvoid.task.dao.IUpTaskDao");
		try {
			Class<?> clss = Class.forName(className);
			return (UpTaskDao) clss.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 出库任务类
	 * 
	 * @return
	 */
	public static DownTaskDao getDownTaskDao() {
		String className = prop.getProperty("me.softvoid.task.dao.IDownTaskDao");
		try {
			Class<?> clss = Class.forName(className);
			return (DownTaskDao) clss.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 入库扫码类
	 * 
	 * @return
	 */
	public static UpScanDao getUpScanDao() {
		String className = prop.getProperty("me.softvoid.scan.up.dao.UpScanDao");
		try {
			Class<?> clss = Class.forName(className);
			return (UpScanDao) clss.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 出库扫码类
	 * 
	 * @return
	 */
	public static DownScanDao getDownScanDao() {
		String className = prop.getProperty("me.softvoid.scan.down.dao.DownScanDao");
		try {
			Class<?> clss = Class.forName(className);
			return (DownScanDao) clss.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 工厂生成DrugDao类
	 * 
	 * @return
	 */
	public static StockDao getStockDao() {
		String className = prop.getProperty("me.softvoid.stock.dao.IStockDao");
		try {
			Class<?> clss = Class.forName(className);
			return (StockDao) clss.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
