package me.softvoid.server.scan.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.dbutils.handlers.ScalarHandler;

public abstract class ScanCodeDao implements IScanCodeDao {

	/**
	 * 查询是否存在该单据的监管码
	 * 
	 * @param sqlCount
	 * @param conn
	 * @param orderId
	 * @return
	 * @throws SQLException
	 */
	public int hasCode(String sqlCount, Object... orderId) throws SQLException {
		Number count = (Number) qr.query(sqlCount, new ScalarHandler<>(), orderId);
		return count.intValue();
	}

	/**
	 * 插入监管码
	 * 
	 * @throws SQLException
	 */
	@Override
	public List<String> insertDrugCode(String orderType, String orderId, String drugId,
			String codes, String batchNum) throws SQLException {
		List<String> listRemainCode = new ArrayList<String>();
		// 插入监管码
		String sql = "insert into dzjgm(sjgm,sgsid,ndjbh,nspid,sph,dczrq,nczlx,nsmfs,nsl) select ?,'0101',?,?,?,sysdate,?,0,1 from dual";
		List<String> codeList = Arrays.asList(codes.split(","));
		Object[][] para = new Object[codeList.size()][];
		String sqlCount = "select count(1) from dzjgm where nczlx=? and sgsid='0101' and ndjbh=?";
		/**
		 * PDA或手机扫码的，扫码状态必须改成2，否则WMS界面不显示
		 */
		int count = hasCode(sqlCount, orderType, orderId);
		// 如果没有该单据的监管码，则直接插入（为什么要判断是因为入库和出库都要扫监管码）
		if (count == 0) {
			for (int i = 0; i < codeList.size(); i++) {
				para[i] = new Object[] { codeList.get(i), orderId, drugId, batchNum, orderType };
			}
			qr.batch(sql, para);
		} else { // 如果存在该单据的监管码，则查询是否存在已有的监管码
			sqlCount = "select count(1) from dzjgm where sjgm=? and ndjbh=? and nczlx=?";
			// 查询是否存在的监管码
			List<String> listCode = new ArrayList<String>();
			for (int i = 0; i < codeList.size(); i++) {
				count = hasCode(sqlCount, codeList.get(i), orderId, orderType);
				if (count != 0)
					listRemainCode.add(codeList.get(i));
				else
					listCode.add(codeList.get(i));
			}
			if (listCode.size() != 0) {
				para = new Object[listCode.size()][];
				/**
				 * 用每个监管码去与其它参数关联，生成参数数组，最后批处理执行
				 */
				for (int i = 0; i < listCode.size(); i++)
					para[i] = new Object[] { listCode.get(i), orderId, drugId, batchNum, orderType };
				qr.batch(sql, para);
			}
		}
		String s = "update ckjhd set nsmzt=2 where nckdid=?";
		qr.update(s, orderId);
		return listRemainCode;
	}
}
