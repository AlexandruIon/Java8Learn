package asd;

public class Apple implements Fruit{

	public String color;
	public Integer weight;

	public Apple() {
	}

	public Apple(String color) {
		this.color = color;
	}

	public Apple(Integer weight){
		this.weight = weight;
	}

	public Apple(String color, Integer weight) {
		this.color = color;
		this.weight = weight;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}
}
