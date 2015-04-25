package me.softvoid.server.query.amount.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import me.softvoid.server.drug.domain.DrugBean;
import me.softvoid.server.query.amount.domain.AmountBean;
import me.softvoid.server.stock.domain.StockBean;

import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.JdbcUtils;

public class AmountDao implements IAmountDao {

	@Override
	public int updateBarcode(Object... params) throws SQLException {
		String sql = "update spxx set ssptm=? where sspbm=?";
		return qr.update(sql, params);
	}

	@Override
	public int[] findDrugId(String goodsNum) throws SQLException {
		Connection conn = null;
		try {
			conn = JdbcUtils.getConnection();
			String sqlNum = "select nspid, njlgg from spxx where sspbm=?";
			ResultSet rs = conn.prepareStatement(sqlNum, new String[] { goodsNum })
					.executeQuery();
			int[] goodsInf = new int[2];
			while (rs.next()) {
				goodsInf[0] = rs.getInt(1);
				goodsInf[1] = rs.getInt(2);
			}
			if (goodsInf.length == 0) {
				return null;
			}
			return goodsInf;
		} finally {
			if (conn != null)
				JdbcUtils.releaseConnection(conn);
		}
	}

	@Override
	public List<AmountBean> queryDrugAmount(String... params) throws SQLException {
		String sqlCount = "select count(1) from hwspjc a, spxx b, ck c where a.nspid=b.nspid and "
				+ "a.sckid=c.sckid and n4kcksl!=0 and n4hwsl!=0 AND (b.spjwh like '%'||? or shwid=? "
				+ "OR b.sspbm=? or b.ssptm=? or b.szjm like '%'||?||'%' or a.sph=?)";
		Object[] par = { params[0], params[0], params[0], params[0], params[0], params[0] };
		Connection conn = null;
		try {
			conn = JdbcUtils.getConnection();
			int count = ((Number) qr.query(sqlCount, new ScalarHandler<>(), par)).intValue();
			if (count == 0) {
				return null;
			}
			String sql = "select sckid \"sckid\",sckmc \"sckmc\",nspid \"nspid\",sspbm \"sspbm\","
					+ "sspmc \"sspmc\",sspgg \"sspgg\",sspcd \"sspcd\",sjldw \"sjldw\",njlgg \"njlgg\","
					+ "sph \"sph\",to_char(dscrq,'yyyy-mm-dd') \"dscrq\",to_char(dyxqz,'yyyy-mm-dd') \"dyxqz\","
					+ "n4hwsl \"n4hwsl\",n4kcksl \"n4kcksl\","
					+ "nzj \"nzj\",nls \"nls\",shwid \"shwid\" from (select rownum rn, t.* from (select "
					+ "c.sckid sckid,c.sckmc sckmc,a.shwid shwid,b.nspid, sspbm,"
					+ " sspmc, sspgg, sspcd, sph, njlgg, sjldw, dscrq, dyxqz, n4hwsl, n4kcksl, "
					+ "trunc(n4kcksl/njlgg) nzj, mod(n4kcksl, njlgg) nls, b.ssptm ssptm from hwspjc a,"
					+ " spxx b, ck c where a.n4kcksl!=0 and a.n4hwsl!=0 and a.nspid=b.nspid and "
					+ "a.sckid=c.sckid and (b.spjwh like '%'||? or shwid=? OR b.sspbm=? or b.ssptm=?"
					+ " or b.szjm like '%'||?||'%' or a.sph=?) order by shwid,"
					+ " sspmc) t where rownum<=?) a where rn>=?";

			String start = String.valueOf(params[1]);
			String end = String.valueOf(params[2]);
			Object[] param = { params[0], params[0], params[0], params[0], params[0],
					params[0], end, start };

			List<Map<String, Object>> listMap = qr.query(sql, new MapListHandler(), param);
			return toBeanList(listMap);
		} finally {
			if (conn != null)
				JdbcUtils.releaseConnection(conn);
		}
	}

	/**
	 * 查询的数据转换成多个对象，然后装载在List集合返回
	 * 
	 * @param listMap
	 * @return
	 */
	private List<AmountBean> toBeanList(List<Map<String, Object>> listMap) {
		List<AmountBean> beanList = new ArrayList<AmountBean>();
		for (Map<String, Object> map : listMap) {
			AmountBean amountBean = CommonUtils.toBean(map, AmountBean.class);
			StockBean stockBean = CommonUtils.toBean(map, StockBean.class);
			DrugBean drugBean = CommonUtils.toBean(map, DrugBean.class);
			amountBean.setStock(stockBean);
			amountBean.setDrugBean(drugBean);
			beanList.add(amountBean);
		}
		return beanList;
	}

	public String movePosition(String drugId, String batchCode, String srcZone,
			String srcPos, String desZone, String desPos, int desAmount, String empNum)
			throws SQLException {
		Connection conn = null;
		try {
			conn = JdbcUtils.getConnection();
			CallableStatement callStmt = conn.prepareCall("{call pro_hwyk(?,?,?,?,?,?,?,?,?)}");
			callStmt.setString(1, drugId);
			callStmt.setString(2, srcZone);
			callStmt.setString(3, srcPos);
			callStmt.setString(4, desZone);
			callStmt.setString(5, desPos);
			callStmt.setString(6, batchCode);
			callStmt.setInt(7, desAmount);
			callStmt.setString(8, empNum);
			callStmt.registerOutParameter(9, 12);
			boolean r = callStmt.execute();
			return String.valueOf(r);
		} finally {
			if (conn != null)
				JdbcUtils.releaseConnection(conn);
		}
	}

	/**
	 * 查询零货位是否为空货位，因为零货位只能放一个批号的
	 * 
	 * @param desPos
	 * @return
	 * @throws SQLException
	 */
	public int isZeroPostion(String desPos, String drugId, String batchCode)
			throws SQLException {
		String sqlPos = "select COUNT(1) from ck a, hwspjc b, hwxx c WHERE c.shwid=? "
				+ "AND a.sckid=b.sckid AND nlx=2 AND a.sckid=c.sckid AND b.shwid=c.shwid AND"
				+ " c.nsfjdhw=0 AND c.ndclhw=0 and b.n4kcksl!=0 and b.n4hwsl!=0 and b.nspid!=?"
				+ " and b.sph!=?";
		Number count = (Number) qr.query(sqlPos, new ScalarHandler<>(), desPos, drugId,
				batchCode);
		return count.intValue();
	}
}
