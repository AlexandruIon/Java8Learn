package asd;


import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainStreamsTest {

	public static void main(String[] args) {

		List<Dish> menu = Arrays.asList(
				new Dish("pork", false, 800, Dish.Type.MEAT),
				new Dish("beef", false, 700, Dish.Type.MEAT),
				new Dish("chicken", false, 400, Dish.Type.MEAT),
				new Dish("french fries", true, 530, Dish.Type.OTHER),
				new Dish("rice", true, 350, Dish.Type.OTHER),
				new Dish("season fruit", true, 120, Dish.Type.OTHER),
				new Dish("pizza", true, 550, Dish.Type.OTHER),
				new Dish("prawns", false, 300, Dish.Type.FISH),
				new Dish("salmon", false, 450, Dish.Type.FISH));


		// streams from values
		System.out.println("-- Streams form values");
		Stream<String> stringStream = Stream.of("Java8", "lambdas", "In", "Action");
		stringStream.map(String::toUpperCase).forEach(System.out::println);
		System.out.println("values count" + Stream.of("Java8", "lambdas", "In", "Action").count());
		Optional findFirstOptional = Stream.of("Java8", "lambdas", "In", "Action").findFirst();
		System.out.println("Optional findFirst : isPresent() " + findFirstOptional.isPresent());
		System.out.println("Optional findFirst : orElse " + findFirstOptional.orElse("NOTHING"));
		System.out.println("Streams form values -> map to int then sum " + Stream.of("Java8", "lambdas", "In", "Action").mapToInt(String::length).sum());

		// streams from arrays
		System.out.println("-- Streams form arrays");
		int[] numbers = {2, 3, 4, 5, 6};
		int sum = Arrays.stream(numbers).sum();
		System.out.println("array of numbers :sum " + sum);

		// streams from files
//		long uniqueWords = 0;
//		try (Stream<String> lines = Files.lines(Paths.get(""))) {
//			uniqueWords = lines.flatMap(line -> Arrays.stream(line.split(" ")))
//					.distinct()
//					.count();
//		} catch (IOException e) {}

		// streams from functions: creating infinite streams

		System.out.println("-- Streams iterate");
		Stream.iterate(0, n -> n + 2).limit(10).forEach(System.out::println);

		System.out.println("-- Streams iterate2");
		int i = 1;
		int intermediateValue;
//		Stream.iterate(i, new UnaryOperator<Integer>() {
//			@Override
//			public Integer apply(Integer previus) {
//				intermediateValue = previus;
//				return previus + i;
//			}
//		}).limit(10).forEach(System.out::println);

		Stream.generate(Math::random);

		System.out.println("-- Fibonnaci number");
		fibonaciNumber();

		System.out.println("-- Reducing and summarizing");
		long howManyDishes = menu.stream().collect(Collectors.counting());
		long howManyDishes2 = menu.stream().count();

		System.out.println("how many dishes " + howManyDishes);

		System.out.println("-- Maximum and mininum in a stream of values");

		Optional optionalMaximumWithComparator = menu.stream().max(new Comparator<Dish>() {
			@Override
			public int compare(Dish o1, Dish o2) {
				return ((Integer) o1.getCalories()).compareTo((Integer) (o2.getCalories()));
			}
		});

		Optional optionalMaximumWithComparator2 = menu.stream().max(Comparator.comparingInt(Dish::getCalories));
		Optional optionalMaximumWithComparator3 = menu.stream().collect(Collectors.maxBy(Comparator.comparingInt(Dish::getCalories)));

		System.out.println("Maximum1" + optionalMaximumWithComparator);
		System.out.println("Maximum2" + optionalMaximumWithComparator2);
		System.out.println("Maximum3" + optionalMaximumWithComparator3);


		int totalCalories = menu.stream().collect(Collectors.summingInt(Dish::getCalories));
		double avgCalories = menu.stream().collect(Collectors.averagingInt(Dish::getCalories));
		IntSummaryStatistics intSummaryStatistics = menu.stream().collect(Collectors.summarizingInt(Dish::getCalories));
		System.out.println("Calories sum" + totalCalories);
		System.out.println("Calories avg" + avgCalories);
		System.out.println("IntSummaryStatistics" + intSummaryStatistics);


	}

	private static void fibonaciNumber() {

		int previous = 0;
		int now = 0;
		int intermediate;
		for (int i = 0; i < 20; i++) {
			System.out.println(now);
			if (now == 0) {
				now = 1;
				continue;
			}
			intermediate = now;
			now += previous;
			previous = intermediate;
		}
	}
}