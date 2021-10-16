package happyhouse.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Controller {
	Object process(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
