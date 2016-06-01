package chapter8.strategyPattern;


public class IsNumeric implements ValidationStrategy {

	@Override
	public boolean execute(String s) {
		return s.matches("\\d+");
	}
}
