package chapter8;


import asd.Apple;
import asd.Dish;
import chapter6.CollectiongDataWithStreams.CaloricLevel;
import chapter8.observerPattern.book.Feed;
import chapter8.observerPattern.book.Guardian;
import chapter8.observerPattern.book.LeMonde;
import chapter8.observerPattern.book.NYTimes;
import chapter8.observerPattern.tutorial.*;
import chapter8.observerPattern.tutorial.Observer;
import chapter8.strategyPattern.IsAllLowerCase;
import chapter8.strategyPattern.IsNumeric;
import chapter8.strategyPattern.Validator;
import chapter8.templatePattern.FootballGame;
import chapter8.templatePattern.Game;
import chapter8.templatePattern.TennisGame;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class RefactoringExistingCode {


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


		System.out.println("8.1. Refactoring for improved readability and flexibility");
//		refactoringForImprovedReadabilityAndFlexibility(menu);

		System.out.println("8.2. Refactoring object-oriented design patterns with lambdas");
		refatoringDesingPatterns();

	}

	public static void refactoringForImprovedReadabilityAndFlexibility(List<Dish> menu) {


		System.out.println("8.1.2. From anonymous classes to lambda expressions");

		//Second, anonymous classes are allowed to
		//shadow variables from the enclosing class. Lambda expressions can’t
		int a = 10;
		Runnable r1 = () -> {
//			int a = 2; -> Compilation ERROR
			System.out.println(a);
		};

		Runnable r2 = new Runnable() {
			@Override
			public void run() {
				int a = 2;
				System.out.println(a);
			}
		};


		//But converting this anonymous class to a lambda expression results in an ambiguous method
		//call, because both Runnable and Task are valid target types:

		//doSomething(() -> System.out.println("Runnable")); ->> ERROR

		//You can solve the ambiguity by providing an explicit cast (Task):
		doSomething((Task) () -> System.out.println("Runnable"));

		//You can now pass an anonymous class implementing Task without a problem:
		doSomething(new Runnable() {
			@Override
			public void run() {
				System.out.println("Runnable");
			}
		});

		System.out.println("8.1.3. From lambda expressions to method references");

		Map<Dish.Type, Map<CaloricLevel, List<Dish>>> typeMapMap = menu.stream().collect(Collectors.groupingBy(Dish::getType,
				(Collectors.groupingBy((d) -> {
					if (d.getCalories() <= 400) {
						return CaloricLevel.DIET;
					} else if (d.getCalories() <= 700) {
						return CaloricLevel.NORMAL;
					} else return CaloricLevel.FAT;
				}))));

		Map<Dish.Type, Map<CaloricLevel, List<Dish>>> typeCaloricLevel = menu.stream().collect(Collectors.groupingBy(Dish::getType, Collectors.groupingBy(Dish::getCaloricLevel)));

		List<Apple> apples = new ArrayList<>();
		apples.sort((a1, a2) -> a1.getWeight().compareTo(a2.getWeight()));
		apples.sort(Comparator.comparing(Apple::getWeight));

		int totalCalories = menu.stream().map(Dish::getCalories).reduce(0, (c1, c2) -> c1 + c2);
		int totalCalories2 = menu.stream().collect(Collectors.summingInt(Dish::getCalories));

		System.out.println("8.1.4. From imperative data processing to Streams");

		System.out.println("8.1.5. Improving code flexibility");

		System.out.println("Conditional deferred execution");

		Logger logger = null;
		if (logger.isLoggable(Level.FINER)) {
			logger.finer("Problem" + getProblem());
		}

		logger.log(Level.FINER, "Problem" + getProblem());

		logger.log(Level.FINER, () -> getProblem());// use a Supplier to defer the construction of the message


		System.out.println("Execute around");

	}

	public static void refatoringDesingPatterns() {
		System.out.println("8.2.1. Strategy");

		Validator numericValidator = new Validator(new IsNumeric());
		boolean b1 = numericValidator.validate("aaaa");
		Validator lowerCaseValidator = new Validator(new IsAllLowerCase());
		boolean b2 = lowerCaseValidator.validate("bbbb");

		Validator numericValidatorLambdas = new Validator((String s) -> s.matches("[a-z]+"));
		boolean b1Lambas = numericValidatorLambdas.validate("aaaa");
		Validator lowerCaseValidatorLambdas = new Validator((String s) -> s.matches("\\d+"));
		boolean b2Lambdas = lowerCaseValidatorLambdas.validate("bbbb");

		System.out.println("8.2.2. Template method");

		Game footballGame = new FootballGame();
		footballGame.play();

		Game tennisGame = new TennisGame();
		tennisGame.play();

		System.out.println("8.2.3. Observer pattern");

		// tutorial impl
		Subject subject = new Subject();
		Observer observer1 = new BinaryObserver(subject);
		Observer observer2 = new OctalObserver(subject);
		Observer observer3 = new HexaObserver(subject);

		subject.setState(2);
		subject.setState(30);

		//
		Feed f = new Feed();
		f.registerObserver(new NYTimes());
		f.registerObserver(new Guardian());
		f.registerObserver(new LeMonde());
		f.notifyObserver("The queen said her favourite book is Java 8 in Action!");

	}


	public static String getProblem() {
		return "Problem";
	}


	interface Task {
		void execute();
	}

	public static void doSomething(Runnable r) {
		r.run();
	}

	public static void doSomething(Task t) {
		t.execute();
	}

}
