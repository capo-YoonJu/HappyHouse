package happyhouse.model.service;

import java.sql.SQLException;
import java.util.List;

import happyhouse.model.dao.*;
import happyhouse.model.dto.*;

public class HouseServiceImpl implements HouseService {
	
	private HouseDAO houseDAO = HouseDAOImpl.getHouseDao();

	public List<House> search(String sido, String gugun, String dong, String aptName) throws SQLException{
		return houseDAO.searchAll(sido, gugun, dong, aptName);
	}

}
