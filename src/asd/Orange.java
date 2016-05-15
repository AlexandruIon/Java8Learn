package asd;


public class Orange implements Fruit{

	public String color;
	public Integer weight;

	public Orange() {
	}

	public Orange(String color) {
		this.color = color;
	}

	public Orange(Integer weight){
		this.weight = weight;
	}

	public Orange(String color, Integer weight) {
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
