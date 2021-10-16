package happyhouse.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import happyhouse.model.dto.PageInfo;
import happyhouse.model.service.HouseService;
import happyhouse.model.service.HouseServiceImpl;

public class HouseController implements Controller {

	private HouseService houseService = new HouseServiceImpl();

	@Override
	public Object process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String subDoUrl = request.getServletPath().substring(6);
		String subUrl = subDoUrl.substring(0, subDoUrl.lastIndexOf(".do"));

		System.out.println(subUrl);
		if(subUrl.equals("/search")) return search(request, response);
		
		return null;
	}
	
	protected PageInfo search(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String sido = request.getParameter("sido");
		String gugun = request.getParameter("gugun");
		String dong = request.getParameter("dong");
		String aptName = request.getParameter("apt");
		
		try {
			request.setAttribute("search", houseService.search(sido, gugun, dong, aptName));
			return new PageInfo("/searchRecentHouseDeal.jsp", true);
			
		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("errorMessages", e.getMessage());
			return new PageInfo("/error.jsp", true);
		}
	}
}
