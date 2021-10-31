package happyhouse.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import happyhouse.model.dto.DataInfo;
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
		if(subUrl.equals("/searchData")) return searchData(request, response);
		
		return null;
	}
	
	protected PageInfo searchPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		request.setCharacterEncoding("utf-8");
		String sido = request.getParameter("sido");
		String gugun = request.getParameter("gugun");
		String dong = request.getParameter("dong");
		String aptName = request.getParameter("apt");
		request.setAttribute("search", houseService.search(sido, gugun, dong, aptName));
		return new PageInfo("/searchRecentHouseDeal.jsp", true);
	}
	
	protected DataInfo searchData(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		request.setCharacterEncoding("utf-8");
		String sido = request.getParameter("sido");
		String gugun = request.getParameter("gugun");
		String dong = request.getParameter("dong");
		String aptName = request.getParameter("apt");
		return new DataInfo("application/json;charset=utf-8", houseService.search(sido, gugun, dong, aptName));
	}
}
