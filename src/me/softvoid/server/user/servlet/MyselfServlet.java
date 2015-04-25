package me.softvoid.server.user.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.softvoid.server.user.domian.MyselfBean;
import me.softvoid.server.user.exception.UserException;
import me.softvoid.server.user.service.UserService;
import me.softvoid.server.utils.NeoUrl;
import cn.itcast.servlet.BaseServlet;

/**
 * Servlet implementation class MyselfServlet
 */
public class MyselfServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * 根据用户id获得用户的个人信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String getMyself(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userId = request.getParameter("userId");
		try {
			MyselfBean myselfBean = new UserService().getMyself(userId);
			String dir = request.getSession().getServletContext().getRealPath("/images");
			File file = new File(dir);
			if (!file.exists())
				file.mkdir();
			file = new File(dir, myselfBean.getSfilename());
			OutputStream os = new FileOutputStream(file);
			InputStream is = myselfBean.getBimg();
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = is.read(buffer)) != -1)
				os.write(buffer, 0, len);
			is.close();
			os.close();
			List<MyselfBean> list = new ArrayList<MyselfBean>();
			list.add(myselfBean);
			request.setAttribute("list", list);
			// 获得图片所在的项目路径（在上面获取路径后，已经在项目中存在了），然后让客户端去访问该路径
			String imagePath = request.getContextPath() + "/images/"
					+ myselfBean.getSfilename();
			request.setAttribute("imagePath", imagePath);
			// file.delete();
			return NeoUrl.FORWORD_CHAR + NeoUrl.MYSELF;
		} catch (UserException e) {
			// 如果没有该用户信息，则给客户端返回404
			response.getWriter().write("null" + e.getMessage());
		}
		return null;
	}
}
