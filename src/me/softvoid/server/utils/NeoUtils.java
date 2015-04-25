package me.softvoid.server.utils;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;

public class NeoUtils {

	public static final String UTF8 = "UTF-8";

	/**
	 * 获得当前页的最大记录数
	 * 
	 * @param perPageCount
	 * @param currentPage
	 * @return
	 */
	public static int getCurrentPageMaxNum(int perPageCount, int currentPage) {
		return perPageCount * currentPage;
	}

	/**
	 * 获得当前页的最小记录数
	 * 
	 * @param perPageCount
	 * @param currentPage
	 * @return
	 */
	public static int getCurrentPageMinNum(int perPageCount, int currentPage) {
		return (currentPage - 1) * perPageCount;
	}

	/**
	 * 设置编码，获得正常的字符串。Properties的getProperty方法默认采用ISO8859-1编码，因此只要含有非ISO8859-1
	 * 的文字则会乱码
	 * 
	 * @param properties
	 * @param key
	 * @param encoding
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getProperty(Properties properties, String key, String encoding)
			throws UnsupportedEncodingException {
		if (properties == null)
			return null;
		// 如果此时value是中文，则应该是乱码
		String value = properties.getProperty(key);
		if (value == null)
			return null;
		// 编码转换，从ISO8859-1转向指定编码
		value = new String(value.getBytes("ISO8859-1"), encoding);
		return value;
	}

	/**
	 * 获取Ip地址
	 * 
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	

	/**
	 * 获得UUID。生成不重复的32位长度大写字符串
	 * 
	 * @return
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "").toUpperCase();
	}

	/**
	 * 把Map集合数据填充到指定类型的JavaBean对象，并返回对象
	 * 
	 * @param map
	 *          集合（字典）数据
	 * @param clss
	 *          要指定的JavaBean对象
	 * @return 返回填充后的JavaBean对象
	 */
	public static <T> T toBean(Map<String, ?> map, Class<T> clss) {
		try {
			/**
			 * 创建指定类型的JavaBean
			 */
			T bean = clss.newInstance();
			/**
			 * 把数据封装到JavaBean对象中
			 */
			BeanUtils.populate(bean, map);
			return bean;
		} catch (Exception e) {
			throw new RuntimeException(e); // 抛出运行时异常（调用者不需要try或throws）
		}
	}

	/**
	 * 拼接字符串
	 * 
	 * @param listString
	 *          要拼接的List字符串集合
	 * @param ch
	 *          拼接的字符
	 * @return
	 */
	public static String joinString(List<String> listString, String ch) {
		if (listString.size() == 0)
			return "";
		StringBuilder sb = new StringBuilder();
		for (String s : listString) {
			sb.append(s).append(ch);
		}
		sb.deleteCharAt(sb.lastIndexOf(ch));
		return sb.toString();
	}

	/**
	 * 判断字符串是否为数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		if (str.length() == 0)
			return false;
		final String number = "0123456789";
		for (int i = 0; i < str.length(); i++) {
			if (number.indexOf(str.charAt(i)) == -1) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 用户id，该id类似QQ号码。
	 * 按顺序生成
	 * 
	 * @return
	 */
	public static String getUserId(int num) {
		return String.valueOf(++num);
	}

}
