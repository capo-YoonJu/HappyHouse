package happyhouse.model.dao;

import java.util.*;

import happyhouse.model.dto.*;

public interface MarketDAO {
	List<Market> searchAll(String guguncode);
}
