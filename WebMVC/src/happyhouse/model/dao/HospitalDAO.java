package happyhouse.model.dao;

import java.util.*;

import happyhouse.model.dto.*;

public interface HospitalDAO {
	List<Hospital> searchAll(String guguncode);
}
