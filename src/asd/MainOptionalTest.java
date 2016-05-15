package asd;


import java.util.Arrays;
import java.util.Optional;
import java.util.OptionalDouble;

public class MainOptionalTest {


	public static void main(String[] args) {

		int[] numbers = {2, 3, 4, 5, 6};
		System.out.println("--Optional duble:average");
		OptionalDouble average = Arrays.stream(numbers).average();
		System.out.println(average.isPresent());
		System.out.println(average.getAsDouble());

		System.out.println("--Optional empty");
		Optional optional = Optional.empty();
		System.out.println(optional.isPresent());
		System.out.println(optional.orElse("orElse : Alex"));


		System.out.println("--Optional of nullable with value");
		Optional optionalOfNullable = Optional.ofNullable("Ion");
		System.out.println(optionalOfNullable.isPresent());
		System.out.println(optionalOfNullable.orElse("or else: Nicolae"));

		System.out.println("--Optional of nullable with null");
		Optional optionalOfNullable2 = Optional.ofNullable(null);
		System.out.println(optionalOfNullable2.isPresent());
		System.out.println(optionalOfNullable2.orElse("or else :Nicolae"));

		System.out.println("--Optional with null");
		Optional optionalWithNull = Optional.of(null);
		System.out.println(optionalWithNull.isPresent());
		System.out.println(optionalWithNull.orElse("Ncu"));

	}


}
