package chapter8.observerPattern.book;


//First, you need an Observer interface that groups the different observers. It has just one method
//called notify that will be called by the subject (Feed) when a new tweet is available:
public interface Observer {

	void notify(String tweet);
}
