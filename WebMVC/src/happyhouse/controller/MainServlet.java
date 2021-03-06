package happyhouse.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import happyhouse.model.dto.DataInfo;
import happyhouse.model.dto.PageInfo;

@WebServlet(urlPatterns = {"*.do"}, loadOnStartup = 1)
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Controller houseController = new HouseController();
	private Controller userController = new UserController();
	private Controller favoriteController = new favoriteController();
	
	@Override
	public void init() throws ServletException{
		ServletContext application = getServletContext();
		application.setAttribute("root", application.getContextPath());
	}
	
	protected void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = request.getServletPath();
		Object info = null;
		System.out.println("url : "+url);
		
		try {
			if(url.startsWith("/house")) {
				info = houseController.process(request, response);
			} else if(url.startsWith("/user")) {
				info = userController.process(request, response);
			} else if(url.startsWith("/favorite")) {
				info = favoriteController.process(request, response);
			}
			
			if(info instanceof PageInfo) {
				PageInfo pInfo = (PageInfo)info;
				if(pInfo.isForward()) {
					request.getRequestDispatcher(pInfo.getUrl()).forward(request, response);
					return;
				}
				else {
					response.sendRedirect(request.getContextPath()+pInfo.getUrl());
				}
			}
			else {
				DataInfo dInfo = (DataInfo) info;
				String contentType = dInfo.getContentType();
				response.setContentType(contentType);
				if(dInfo.getContentType().startsWith("application/json")) {
					PrintWriter out = response.getWriter();
					Gson gson = new Gson();
					out.println(gson.toJson(dInfo.getData()));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", e.getMessage());
			request.getRequestDispatcher("/error.jsp").forward(request, response);
			return;
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		process(request, response);
	}
}
