package chapter6;


import asd.Dish;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class CollectiongDataWithStreams {

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

        System.out.println("-- Streams iterate");
        Stream.iterate(0, n -> n + 2).limit(10).forEach(System.out::println);

        Stream.generate(Math::random);

        System.out.println("6.2 Reducing and summarizing");
//		reducingAndSummarizing(menu);

        System.out.println("6.3 Grouping");
//		grouping(menu);

        System.out.println("6.4 Partitioning");
//        partitioning(menu);

        System.out.println("6.5 The Collector interface");
        collectorInterface(menu);

    }

    public static void reducingAndSummarizing(List<Dish> menu) {
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

        System.out.println("-- Joining Stings");
        String shortMenu = menu.stream().map(Dish::getName).collect(Collectors.joining());
        String shortMenuWithSeparator = menu.stream().map(Dish::getName).collect(Collectors.joining(" "));
        System.out.println("Short menu: " + shortMenu);
        System.out.println("Short menu with separator: " + shortMenuWithSeparator);

        System.out.println("Generalized summarization with reduction");

        Optional<Dish> mostCalorieDis = menu.stream().collect(Collectors.reducing((d1, d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2));
        int totalCaloriesFromReduction = menu.stream().collect(Collectors.reducing(0, Dish::getCalories, (integer, integer2) -> integer + integer2));

        int totalCaloriesIntegerSum = menu.stream().collect(Collectors.reducing(0, Dish::getCalories, Integer::sum));
        int totalCaloriesMapReduce = menu.stream().map(Dish::getCalories).reduce(Integer::sum).get();
        int totalCaloriesMapToInt = menu.stream().mapToInt(Dish::getCalories).sum();// best solution always use the most specialized one thats general enough to solve it


        String shortMenu1 = menu.stream().map(Dish::getName).reduce((s1, s2) -> s1 + " " + s2).get();
        String shortMenu2 = menu.stream().map(Dish::getName).collect(Collectors.reducing((t, t2) -> t + " " + t2)).get();
        String shortMenu3 = menu.stream().collect(Collectors.reducing("", Dish::getName, String::concat));
        String shortMenu4 = menu.stream().collect(Collectors.reducing("", Dish::getName, (s1, s2) -> s1 + " " + s2));
        System.out.println("Short menu1 " + shortMenu1);
        System.out.println("Short menu2 " + shortMenu2);
        System.out.println("Short menu3 " + shortMenu3);
        System.out.println("Short menu4 " + shortMenu4);
    }

    public static void grouping(List<Dish> menu) {
        Map<Dish.Type, List<Dish>> dishesByType = menu.stream().collect(Collectors.groupingBy(Dish::getType));
        Map<String, List<Dish>> dishesByCalories = menu.stream().collect(Collectors.groupingBy((d) -> {
            if (d.getCalories() < 400) {
                return "diet";
            } else if (d.getCalories() > 400 && d.getCalories() < 700) {
                return "normal";
            } else return "fat";
        }));
        System.out.println("Group by dish type" + dishesByType);
        System.out.println("Group by diet,normal,fat" + dishesByCalories);

        Map<CaloricLevel, List<Dish>> dishesByCaloricLevel = menu.stream().collect(Collectors.groupingBy((d) -> {
            if (d.getCalories() <= 400) {
                return CaloricLevel.DIET;
            } else if (d.getCalories() <= 700) {
                return CaloricLevel.NORMAL;
            } else return CaloricLevel.FAT;
        }));

        System.out.println("Group by caloric level" + dishesByCaloricLevel);

        Map<Dish.Type, Map<CaloricLevel, List<Dish>>> typeMapMap = menu.stream().collect(Collectors.groupingBy(Dish::getType,
                (Collectors.groupingBy((d) -> {
                    if (d.getCalories() <= 400) {
                        return CaloricLevel.DIET;
                    } else if (d.getCalories() <= 700) {
                        return CaloricLevel.NORMAL;
                    } else return CaloricLevel.FAT;
                }))));
        System.out.println("Group by type and caloric level" + typeMapMap);


        System.out.println("6.3.2 Collecting data in subgroups");
        Map<Dish.Type, Long> countingDishTypes = menu.stream().collect(Collectors.groupingBy(Dish::getType, Collectors.counting()));
        System.out.println("Group by type and get number" + countingDishTypes);
        Map<Dish.Type, Optional<Dish>> collectByTypeAndMax = menu.stream().collect(Collectors.groupingBy(Dish::getType, Collectors.maxBy(Comparator.comparingInt(Dish::getCalories))));
        System.out.println("Group by type and max" + collectByTypeAndMax);
        Map<Dish.Type, Optional<Dish>> collectByTypeAndMin = menu.stream().collect(Collectors.groupingBy(Dish::getType, Collectors.minBy(Comparator.comparingInt(Dish::getCalories))));
        System.out.println("Group by type and min" + collectByTypeAndMin);
        Map<Dish.Type, List<Dish>> collectByTypeAndList = menu.stream().collect(Collectors.groupingBy(Dish::getType, Collectors.toList()));
        System.out.println("Group by type and list" + collectByTypeAndList);

        Map<Dish.Type, Dish> mostCaloricByType = menu.stream()
                .collect(Collectors.groupingBy(
                        Dish::getType,
                        Collectors.collectingAndThen(Collectors.maxBy(Comparator.comparingInt(Dish::getCalories)),
                                Optional::get)));

        System.out.println("Highest calorie Dish in each subgroup" + mostCaloricByType);

        Map<Dish.Type, Integer> sumOfCaloriesByType = menu.stream()
                .collect(Collectors.groupingBy(
                        Dish::getType,
                        Collectors.summingInt(Dish::getCalories)
                ));
        System.out.println("Summing calories by type" + sumOfCaloriesByType);


        Map<Dish.Type, Set<CaloricLevel>> dishTypeByCaloricLevel = menu.stream().collect(
                Collectors.groupingBy(Dish::getType,
                        Collectors.mapping((d) -> {
                            if (d.getCalories() <= 400) {
                                return CaloricLevel.DIET;
                            } else if (d.getCalories() <= 700) {
                                return CaloricLevel.NORMAL;
                            } else return CaloricLevel.FAT;
                        }, Collectors.toSet())
                ));
        System.out.println("Dish type by caloric level" + dishTypeByCaloricLevel);


        Map<Dish.Type, Set<CaloricLevel>> dishTypeByCaloricLevelHashSet = menu.stream().collect(
                Collectors.groupingBy(Dish::getType,
                        Collectors.mapping((d) -> {
                            if (d.getCalories() <= 400) {
                                return CaloricLevel.DIET;
                            } else if (d.getCalories() <= 700) {
                                return CaloricLevel.NORMAL;
                            } else return CaloricLevel.FAT;
                        }, Collectors.toCollection(HashSet::new))
                ));
        System.out.println("Dish type by caloric level hash set" + dishTypeByCaloricLevelHashSet);


    }

    public static void partitioning(List<Dish> menu) {

        Map<Boolean, List<Dish>> partitioningByIsVegetarian = menu.stream().collect(Collectors.partitioningBy(Dish::isVegetarian));
        System.out.println("Partitioning by is vegetarian" + partitioningByIsVegetarian);

        List<Dish> filteringByIsVegetarian = menu.stream().filter(Dish::isVegetarian).collect(Collectors.toCollection(ArrayList::new));
        System.out.println("Filtering by is vegetarian" + filteringByIsVegetarian);

        Map<Boolean, Map<Dish.Type, List<Dish>>> partitioningByDishType = menu.stream().collect(Collectors.partitioningBy(Dish::isVegetarian, Collectors.groupingBy(Dish::getType)));
        System.out.println("Partitioning by is vegetarian and dish type" + partitioningByDishType);

        Map<Boolean, Map<Integer, List<Dish>>> partitioningByDishCalories = menu.stream().collect(Collectors.partitioningBy(Dish::isVegetarian, Collectors.groupingBy(Dish::getCalories)));
        System.out.println("Partitioning by is vegetarian and dish calories" + partitioningByDishCalories);

        Map<Boolean, Map<Dish.Type, Map<Integer, List<Dish>>>> partitioningByDishTypesAndCalories = menu.stream()
                .collect(Collectors.partitioningBy(
                        Dish::isVegetarian, Collectors.groupingBy(
                                Dish::getType, Collectors.groupingBy(
                                        Dish::getCalories))));
        System.out.println("Partitioning by is vegetarian and dish type and dish calories" + partitioningByDishTypesAndCalories);

        Map<Boolean, Map<Integer, Map<Dish.Type, List<Dish>>>> partitioningByDishCaloriesAndTypes = menu.stream()
                .collect(Collectors.partitioningBy(
                        Dish::isVegetarian, Collectors.groupingBy(
                                Dish::getCalories, Collectors.groupingBy(
                                        Dish::getType))));
        System.out.println("Partitioning by is vegetarian and dish calories and dish types" + partitioningByDishCaloriesAndTypes);


        Map<Boolean, Optional<Dish>> partitioningByIsVegetarianHighestCalories = menu.stream().collect(
                Collectors.partitioningBy(Dish::isVegetarian, Collectors.maxBy(Comparator.comparingInt(Dish::getCalories))));
        System.out.println("Partitioning by is vegetarian highest calories" + partitioningByIsVegetarianHighestCalories);

        Map<Boolean, Dish> partitioningByIsVegetarianAndHighestCaloriesOptional = menu.stream().collect(Collectors.partitioningBy(
                Dish::isVegetarian, Collectors.collectingAndThen(
                        Collectors.maxBy(Comparator.comparingInt(Dish::getCalories)), Optional::get)));
        System.out.println("Partitioning by is vegetarian highest calories optional" + partitioningByIsVegetarianAndHighestCaloriesOptional);

        Map<Boolean, Map<Dish.Type, Optional<Dish>>> partitioningByIsVegetarianAndTypeAndHighestCalories = menu.stream().collect(
                Collectors.partitioningBy(
                        Dish::isVegetarian, Collectors.groupingBy(
                                Dish::getType, Collectors.maxBy(Comparator.comparingInt(Dish::getCalories)))));
        System.out.println("Partitioning by is vegetarian and type highest calories" + partitioningByIsVegetarianAndTypeAndHighestCalories);

        Map<Boolean, Map<Boolean, List<Dish>>> partitionCaloriesAbove500 = menu.stream().collect(
                Collectors.partitioningBy(
                        Dish::isVegetarian, Collectors.partitioningBy(d -> d.getCalories() > 500)));
        System.out.println("Partitioning by is vegetarian and partition calories" + partitionCaloriesAbove500);

        System.out.println("6.4.2 Partitioning numbers into prime and nonprime");

    }

    private static void collectorInterface(List<Dish> menu) {

    }

    public enum CaloricLevel {DIET, NORMAL, FAT}

    {

    }
}
