package robot.api;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import robot.service.RobotService;

@RestController
public class RobotApi {

	@Autowired
	private RobotService service;

	@RequestMapping(value = "/rest/mars/{command}", method = RequestMethod.POST)
	public @ResponseBody String robotPosition(@PathVariable String command) {
		return service.robotPosition(command);
	}

	@ExceptionHandler({ IllegalArgumentException.class, ConstraintViolationException.class })
	void handleException(HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.BAD_REQUEST.value());
	}
}