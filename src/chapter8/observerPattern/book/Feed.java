package chapter8.observerPattern.book;


import java.util.ArrayList;
import java.util.List;

public class Feed implements Subject {

	private List<Observer> observerList = new ArrayList<>();

	@Override
	public void registerObserver(Observer o) {
		observerList.add(o);
	}

	@Override
	public void notifyObserver(String tweet) {
		observerList.stream().forEach(o -> o.notify(tweet));
	}
}
