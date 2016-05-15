package asd;


import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class Main {

	public static void main(String[] args) {

		Supplier<Apple> appleSupplier = () -> new Apple();

		Supplier<Apple> appleSupplier2 = Apple::new;
		Apple appleFromSupplier = appleSupplier2.get();

		Function<String, Apple> appleFunction = (String a) -> new Apple(a);

		Function<String, Apple> appleFunction2 = Apple::new;
		Function<String, Apple> appleFunction3 = new Function<String, Apple>() {
			@Override
			public Apple apply(String s) {
				return new Apple(s);
			}
		};

		Function<Integer, Apple> appleFunction4 = new Function<Integer, Apple>() {
			@Override
			public Apple apply(Integer integer) {
				return new Apple(integer);
			}
		};

		Apple appleFromFunction = appleFunction2.apply("SomeApple");
		map(Arrays.asList(1, 2, 3, 4), Apple::new);

		BiFunction<Integer, String, Apple> appleBiFunction = new BiFunction<Integer, String, Apple>() {
			@Override
			public Apple apply(Integer integer, String s) {
				return new Apple(s, integer);
			}
		};
		BiFunction<String, Integer, Apple> applesBiFunction2 = Apple::new;
		Apple apple = applesBiFunction2.apply("asd", 1);
		BiFunction<String, Integer, Apple> applesBiFunction3 = (a, b) -> new Apple(a, b);


		List<Apple> apples = new ArrayList<>();
		apples.sort(Comparator.comparing(Apple::getWeight));
		apples.sort(Comparator.comparing(Apple::getWeight).reversed());
		apples.sort(Comparator.comparing(Apple::getWeight).reversed().thenComparing(Apple::getColor));
		Comparator<Apple> appleComparator = Comparator.comparing(a -> a.getWeight());
		Comparator<Apple> appleComparator2 = Comparator.comparing(new Function<Apple, String>() {
			@Override
			public String apply(Apple apple) {
				return apple.getColor();
			}
		});
		apples.sort(appleComparator);

		Predicate<Apple> applePredicate = (a) -> a.getColor().equals("red");
		Predicate<Apple> applePredicateNegate = applePredicate.negate();
		Predicate<Apple> applePredicateAnd = applePredicate.and(applePredicate);

		Function<Integer,Integer> h = (x->x+1);


		Stream<String> stream = Stream.of("Java 8", "Lambdas", "In", "Action");
		stream.map(String::toUpperCase).forEach(System.out::println);

		int [] numbers = {2,3,4,5,6};
		int sum = Arrays.stream(numbers).sum();
		System.out.println(sum);

	}

	public static List<Apple> map(List<Integer> list, Function<Integer, Apple> f) {
		List<Apple> apples = new ArrayList<>();
		for (Integer e : list) {
			apples.add(f.apply(e));
		}
		return apples;
	}

	static Map<String, Function<Integer, Fruit>> map = new HashMap<>();

	static {
		map.put("apple", Apple::new);
		map.put("orange", Orange::new);
	}

	public static Fruit giveMeFruit(String fruit, Integer weight) {
		return map.get(fruit).apply(weight);
	}

}
