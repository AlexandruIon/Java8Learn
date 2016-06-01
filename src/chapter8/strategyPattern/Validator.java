package chapter8.strategyPattern;


public class Validator {

	private final ValidationStrategy validationStrategy;

	public Validator(ValidationStrategy v) {
		this.validationStrategy = v;
	}

	public boolean validate(String s) {
		return validationStrategy.execute(s);
	}

}
