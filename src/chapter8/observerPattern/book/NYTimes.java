package chapter8.observerPattern.book;


public class NYTimes implements Observer {

	@Override
	public void notify(String tweet) {
		if (tweet != null && tweet.contains("money")) {
			System.out.println("Yet another news in London..." + tweet);
		}
	}
}
