package me.softvoid.server.query.ranking.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import me.softvoid.server.query.ranking.domain.RankingBean;
import me.softvoid.server.stock.domain.StockBean;
import me.softvoid.server.user.domian.EmployeeBean;
import me.softvoid.server.user.domian.MyselfBean;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;

public class RankingDao {
	private QueryRunner qr = new TxQueryRunner();

	/**
	 * 获得工作量排行榜
	 * 
	 * @see 在查询的时候会根据当前帐号的仓库绑定来决定是按条目数还是件数来排序的。 因为整件是按件数算，零散按条目数
	 * 
	 * @param emp
	 * @return
	 * @throws SQLException
	 */
	public List<RankingBean> getRanking(String emp, String startDate, String endDate)
			throws SQLException {
		String sql = "select sp_ckid,nlx, count(*) sbz from ck where sckid in(select sckid from ryckgl where sryid=?) "
				+ "and nlx in(0,1,2) group by sp_ckid,nlx";
		StockBean stockBean = qr.query(sql, new BeanHandler<StockBean>(StockBean.class), emp);
		StringBuilder sb = new StringBuilder(
				"select rownum \"position\",sryid \"sryid\",srymc \"srymc\",sjtms \"sjtms\","
						+ "round(sjjs,2) \"sjjs\",xjtms \"xjtms\",round(xjjs,2) \"xjjs\",bhtms \"bhtms\","
						+ "bhjs \"bhjs\",zdbhtms \"zdbhtms\",zdbhjs \"zdbhjs\",ykrws \"ykrws\","
						+ "yktms \"yktms\",round(ykjs,2) \"ykjs\" from("
						+ "select sryid, x.srymc,nrwlx,sum(decode(nsfckrw,1,0,rws)) sjrws, sum(decode(nsfckrw,1,0,tms)) sjtms,"
						+ "sum(decode(nsfckrw,1,0,js)) sjjs,sum(decode(nsfckrw,0,0,decode(sign(nrwlx - 2),1,0,rws))) xjrws,"
						+ "sum(decode(nsfckrw,0,0,decode(sign(nrwlx - 2),1,0,tms))) xjtms, sum(decode(nsfckrw,0,0,decode(sign(nrwlx - 2),1,0,js))) xjjs,"
						+ "sum(decode(nsfckrw,0,0,decode(nrwlx,3,rws,0))) bhrws,sum(decode(nsfckrw,0,0,decode(nrwlx,3,tms,0))) bhtms,"
						+ "sum(decode(nsfckrw,0,0,decode(nrwlx,3,js,0))) bhjs, sum(decode(nsfckrw,0,0,decode(nrwlx,4,rws,0))) zdbhrws,"
						+ "sum(decode(nsfckrw,0,0,decode(nrwlx,4,tms,0))) zdbhtms,sum(decode(nsfckrw,0,0,decode(nrwlx,4,js,0))) zdbhjs,"
						+ "sum(decode(nsfckrw,0,0,decode(nrwlx,5,rws,0))) ykrws,sum(decode(nsfckrw,0,0,decode(nrwlx,5,tms,0))) yktms,"
						+ "sum(decode(nsfckrw,0,0,decode(nrwlx,5,js,0))) ykjs from ("
						/* 任务工作量 */
						+ "select rq,nsfckrw,nrwlx,srymc,sryid, count(1) rws, sum(tms) tms, sum(js) js  from ("
						/* 上架任务 */
						+ "select a.nrwid, to_char(a.dqrsj,'yyyy-mm-dd') rq, 0 nsfckrw,a.nrwlx,a.ssjrmc srymc,"
						+ "a.ssjrid sryid,a.sckid,count(1) tms,sum(b.n4sssl/b.njlgg) js "
						+ "from sjrwfprwd a, sjrwfpmx b where a.nrwid = b.nrwid  and a.nrwlx <3 and a.nrwzt = 2"
						+ "group by a.nrwid,to_char(a.dqrsj,'yyyy-mm-dd'),a.nrwlx,a.sckid,a.ssjrmc,a.ssjrid "
						/* 下架任务 */
						+ "union all select a.nrwid, to_char(a.dqrsj,'yyyy-mm-dd') rq, 1 nsfckrw,a.nrwlx,a.sxjrmc,"
						+ "a.sxjrid,a.sckid_y,count(1) tms,sum(b.n4sssl/b.njlgg) js from v_xjrwfprwd a, v_xjrwfpmx b "
						+ "where a.nrwid = b.nrwid and a.nrwlx <3 and a.nrwzt >= 2 group by a.nrwid,"
						+ "to_char(a.dqrsj,'yyyy-mm-dd'),a.nrwlx,a.sxjrmc,a.sxjrid,a.sckid_y "
						/* 345类型下架部分 */
						+ "union all select a.nrwid, to_char(a.dqrsj,'yyyy-mm-dd') rq, 1 nsfckrw,a.nrwlx,a.sxjrmc,"
						+ "a.sxjrid,a.sckid_y,count(1) tms,sum(b.n4zjsl+b.n4lssl/b.njlgg) js "
						+ "from v_xjrwfprwd a, v_xjrwfpmx b where a.nrwid = b.nrwid and  a.nrwlx >=3 and a.nrwzt >= 2"
						+ "group by a.nrwid,to_char(a.dqrsj,'yyyy-mm-dd'),a.nrwlx,a.sxjrmc, a.sckid_y,a.sxjrid "
						/* 345类型任务上架部分 */
						+ "union all select a.nrwid, to_char(a.dqrsj,'yyyy-mm-dd') rq, 1 nsfckrw,a.nrwlx,a.ssjrmc,"
						+ "a.ssjrid, a.sckid_m,count(1) tms,sum(b.n4zjsl+b.n4lssl/b.njlgg) js "
						+ "from v_xjrwfprwd a, v_xjrwfpmx b where a.nrwid = b.nrwid and a.nrwlx >=3 and  a.nrwzt = 4 "
						+ "and a.ssjrmc is not null group by a.nrwid,to_char(a.dqrsj,'yyyy-mm-dd'),a.nrwlx,a.ssjrmc,"
						+ "a.sckid_m,a.ssjrid) a where rq between ? and ? "
						+ "group by rq,nsfckrw,nrwlx,srymc,sryid"
						+ ") x group by sryid,x.srymc,nrwlx order by ");

		int count = Integer.parseInt(stockBean.getSbz());
		/**
		 * 如果该帐号存在整库，则统计整库的件数排行榜，否则统计零散的条目数排行榜
		 */
		if ((stockBean.getNlx() == 0 || stockBean.getNlx() == 1) && count > 0) {
			sb.append("xjjs desc,xjtms desc");
		} else {
			sb.append("xjtms desc,xjjs desc");
			count = 0;
		}
		sb.append(") b where rownum<=10");
		return toBeanList(qr.query(sb.toString(), new MapListHandler(), startDate, endDate),
				count);
	}

	/**
	 * 查询的数据转换成多个对象，然后装载在List集合返回
	 * 
	 * @param listMap
	 * @return
	 */
	private List<RankingBean> toBeanList(List<Map<String, Object>> listMap, int count) {
		List<RankingBean> beanList = new ArrayList<RankingBean>();
		for (Map<String, Object> map : listMap) {
			RankingBean rankingBean = CommonUtils.toBean(map, RankingBean.class);
			MyselfBean myselfBean = CommonUtils.toBean(map, MyselfBean.class);
			EmployeeBean empBean = CommonUtils.toBean(map, EmployeeBean.class);
			myselfBean.setEmpBean(empBean);
			rankingBean.setMyselfBean(myselfBean);
			rankingBean.setFlag(count);
			beanList.add(rankingBean);
		}
		return beanList;
	}

	/**
	 * 內复核排行榜
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws SQLException
	 */
	public List<RankingBean> getCheckInRanking(String startDate, String endDate)
			throws SQLException {
		StringBuilder sb = new StringBuilder("select rownum \"position\","
				+ "sryid \"sryid\",srymc \"srymc\",djs \"djs\",tms \"xjtms\",js \"xjjs\" from ("
				+ "select sryid,srymc,count(1) djs,sum(tms) tms, sum(js) js  from ("
				+ "select nrwfpid, to_char(dnfhrq,'yyyy-mm-dd') rq,a.snfhrid sryid,"
				+ "a.snfhrmc srymc, count(1) tms, sum(n4lssl_n/njlgg) js from v_xjrwfpmx a "
				+ "where nnfhzt >=4  and a.snfhrid is not null group by nrwfpid,"
				+ "to_char(dnfhrq,'yyyy-mm-dd'),a.snfhrid,a.snfhrmc ) a "
				+ "where rq between ? and ? group by sryid,srymc order by js desc,tms desc) b");
		//最后一个参数设置成9999纯粹是为了客户端按件数显示
		return toBeanList(qr.query(sb.toString(), new MapListHandler(), startDate, endDate),
				9999);
	}

}
