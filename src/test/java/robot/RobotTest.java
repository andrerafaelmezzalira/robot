package robot;

import static org.junit.Assert.assertEquals;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import robot.service.RobotService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RobotTest {

	@Autowired
	private RobotService service;

	@Test
	public void invalidPosition() {

		try {
			service.robotPosition("MMMMMMMMMMMMMMMMMMMMMMM");
		} catch (IllegalArgumentException e) {
			assertEquals(true, e.getMessage().equals("Outside specified area"));
		}

	}

	@Test
	public void invalidCommand() {

		try {
			service.robotPosition("AAA");
		} catch (ConstraintViolationException e) {
			assertEquals(true, e.getMessage().matches("^.+L\\|R\\|M.+$"));
		}
	}

	@Test
	public void validCommandAndPosition() {
		assertEquals(service.robotPosition("MML"), "(0,2,W)");
	}
}
