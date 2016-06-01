package asd;


import chapter6.CollectiongDataWithStreams;
import chapter6.CollectiongDataWithStreams.CaloricLevel;

public class Dish {
	private final String name;
	private final boolean vegetarian;
	private final int calories;
	private final Type type;

	public Dish(String name, boolean vegetarian, int calories, Type type) {
		this.name = name;
		this.vegetarian = vegetarian;
		this.calories = calories;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public boolean isVegetarian() {
		return vegetarian;
	}

	public int getCalories() {
		return calories;
	}

	public Type getType() {
		return type;
	}

	public CaloricLevel getCaloricLevel() {
		if (this.getCalories() <= 400) {
			return CollectiongDataWithStreams.CaloricLevel.DIET;
		} else if (this.getCalories() <= 700) {
			return CollectiongDataWithStreams.CaloricLevel.NORMAL;
		} else return CollectiongDataWithStreams.CaloricLevel.FAT;
	}

	@Override
	public String toString() {
		return name;
	}

	public enum Type {MEAT, FISH, OTHER}
}
