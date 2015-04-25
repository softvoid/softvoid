package me.softvoid.server.filter;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * 编码包装类。对参数进行编码，返回正确的编码值。
 * 
 * 是你、有你、一切拜托你！--包装类的法则
 * @author Administrator
 *
 */
public class EncodingRequestWrapper extends HttpServletRequestWrapper {
	private HttpServletRequest request;

	public EncodingRequestWrapper(HttpServletRequest request) {
		super(request);
		this.request = request;
	}

	/**
	 * 获得编码后的参数值
	 */
	@Override
	public String getParameter(String name) {
		String value = request.getParameter(name);
		if (value != null) {
			try {
				value = new String(value.getBytes("ISO-8859-1"), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return value;
	}

}
