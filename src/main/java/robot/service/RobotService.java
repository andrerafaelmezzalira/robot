package robot.service;

import javax.validation.constraints.Pattern;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class RobotService {

	private static final int MIN_POSITION = -5;
	private static final int MAX_POSITION = 5;
	private static final String NORTH = "N";
	private static final String SOUTH = "S";
	private static final String EAST = "E";
	private static final String WEST = "W";
	private static final char LEFT = 'L';
	private static final char MOVE = 'M';

	/**
	 * Plan cartesian 5 X 5. Returns position of robot
	 * 
	 * x - horizontal cartesian (-5 to 5)
	 * y - vertical cartesian (-5 to 5)
	 * p - cardinal point (north, south, east and west)
	 * 
	 * @param s - command initial. Example: MML
	 * 
	 * @return result format (x, y, p). Example: (0,0,N)
	 * @return IllegalArgumentException if position is out of the 5 X 5
	 * @return ConstraintViolationException if 's' contains character different of the 'L', 'R' ou 'M'
	 * 
	 */
	public String robotPosition(@Pattern(regexp = "^(L|R|M)+") String s) {

		// initialize robot in positions (0,0,N)
		Integer x = MIN_POSITION;
		Integer y = MIN_POSITION;
		String p = NORTH;

		for (Character c : s.toCharArray()) {
			y = getY(y, p, c);
			x = getX(x, p, c);
			p = getP(p, c);
		}

		// verify if x or y is out of the 5 X 5
		if (invalidPosition(x, y)) {
			throw new IllegalArgumentException("Outside specified area");
		}

		// format result to: (x{0 .. 10}, y{0 .. 10}, p(N|S|E|W))
		return formatResult(x, y, p);
	}

	private Integer getY(int y, String p, char c) {
		return c != MOVE ? y : NORTH.equals(p) ? ++y : SOUTH.equals(p) ? --y : y;
	}

	private Integer getX(int x, String p, char c) {
		return c != MOVE ? x : EAST.equals(p) ? ++x : WEST.equals(p) ? --x : x;
	}

	private String getP(String s, char c) {
		return c == MOVE ? s
				: NORTH.equals(s) ? (c == LEFT ? WEST : EAST)
						: WEST.equals(s) ? (c == LEFT ? SOUTH : NORTH)
								: SOUTH.equals(s) ? (c == LEFT ? EAST : WEST) : (c == LEFT ? NORTH : SOUTH);
	}

	private boolean invalidPosition(int x, int y) {
		return (x > MAX_POSITION || x < MIN_POSITION) || (y > MAX_POSITION || y < MIN_POSITION);
	}

	private String formatResult(int x, int y, String p) {
		return "(".concat(new Integer(x + MAX_POSITION).toString()).concat(",")
				.concat(new Integer(y + MAX_POSITION).toString()).concat(",").concat(p).concat(")");
	}

}