package gameEngine.util;

import java.util.ArrayList;
import java.util.List;

/**
 * This class allows to match integers to this pattern. This is similar to the
 * java.util.regex.Pattern but for integers
 * 
 * @author Jono Yan
 *
 */
public class PatternInt {
	private List<Integer> pattern;

	/**
	 * Input pattern
	 * 
	 * @param regex
	 */
	public PatternInt(int... regex) {
		this.pattern = new ArrayList<>();
		for (int i : regex) {
			pattern.add(i);
		}
	}

	/**
	 * If input matches the pattern
	 * 
	 * @param input
	 * @return
	 */
	public boolean match(int input) {
		if (this.pattern.contains(input))
			return true;
		return false;
	}

	public int getValue() {
		return pattern.get(0);
	}
}
