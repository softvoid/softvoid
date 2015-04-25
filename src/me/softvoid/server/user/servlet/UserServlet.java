package me.softvoid.server.user.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.softvoid.server.exception.NeoDataException;
import me.softvoid.server.user.domian.MyselfBean;
import me.softvoid.server.user.exception.UserException;
import me.softvoid.server.user.service.UserService;
import me.softvoid.server.utils.NeoUrl;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * 使用注解代替配置文件
 * 
 * @author 黄校 QQ：599969512 email：softneo@qq.com 创建时间：2014年12月31日 下午10:16:16
 */
public class UserServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 获得客户端上传的图片
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		// 获得磁盘文件条目工厂。
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// 获取文件上传需要保存的路径，upload文件夹需存在。
		String path = request.getSession().getServletContext().getRealPath("/images");
		// 设置暂时存放文件的存储室，这个存储室可以和最终存储文件的文件夹不同。因为当文件很大的话会占用过多内存所以设置存储室。
		factory.setRepository(new File(path));
		// 设置缓存的大小，当上传文件的容量超过缓存时，就放到暂时存储室。
		factory.setSizeThreshold(1024 * 1024);
		// 上传处理工具类（高水平API上传处理？）
		ServletFileUpload upload = new ServletFileUpload(factory);

		// 调用 parseRequest（request）方法 获得上传文件 FileItem 的集合list 可实现多文件上传。
		List<FileItem> list;
		try {
			list = (List<FileItem>) upload.parseRequest(request);

			File pic = new File(path); // 创建目录，如果目录不存在则创建
			if (!pic.exists())
				pic.mkdir();
			String userId = null;
			for (FileItem item : list) {
				// 获取表单属性名字。
				String name = item.getFieldName();
				// 如果获取的表单信息是普通的文本信息。即通过页面表单形式传递来的字符串。
				if (item.isFormField()) {
					// 获取用户具体输入的字符串，
					userId = item.getString();
					request.setAttribute(name, userId);
				}
				// 如果传入的是非简单字符串，而是图片，音频，视频等二进制文件。
				else {
					// 获取路径名
					String value = item.getName();
					// 取到最后一个反斜杠。
					int start = value.lastIndexOf("\\");
					// 截取上传文件的 字符串名字。+1是去掉反斜杠。
					String fileName = value.substring(start + 1);
					request.setAttribute(name, fileName);

					/*
					 * 第三方提供的方法直接写到文件中。 item.write(new File(path,filename));
					 */
					// 收到写到接收的文件中。使用“用户名_文件名”的方式命名图片
					pic = new File(pic, userId + "_" + fileName);
					OutputStream out = new FileOutputStream(pic);
					InputStream in = item.getInputStream();

					int length = 0;
					byte[] buf = new byte[1024];
					// System.out.println("获取文件总量的容量:" + item.getSize());

					while ((length = in.read(buf)) != -1) {
						out.write(buf, 0, length);
					}
					in.close();
					out.close();
				}
			}
			try {
				MyselfBean myselfBean = new UserService().savePicture(userId, pic);
				if (myselfBean != null) {
					List<MyselfBean> myselfList = new ArrayList<MyselfBean>();
					myselfList.add(myselfBean);
					request.setAttribute("list", myselfList);
					request.getRequestDispatcher(NeoUrl.MYSELF).forward(request, response);
				} else {
					response.setStatus(500);
				}
			} catch (NeoDataException | UserException e) {
				response.getWriter().print(e.getMessage());
			}
		} catch (FileUploadException e1) {
			response.setStatus(404);
		}
	}

}
