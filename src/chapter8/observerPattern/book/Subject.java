package chapter8.observerPattern.book;


public interface Subject {

	void registerObserver(Observer o);

	void notifyObserver(String tweet);
}
