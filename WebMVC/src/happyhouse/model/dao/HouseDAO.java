package happyhouse.model.dao;

import java.util.List;

import happyhouse.model.dto.*;

public interface HouseDAO {
	List<House> searchAll(String sido, String gugun, String dong, String aptName);
}
