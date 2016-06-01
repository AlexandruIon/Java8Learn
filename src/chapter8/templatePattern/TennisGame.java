package chapter8.templatePattern;

public class TennisGame extends Game {

	@Override
	void initialize() {
		System.out.println("Initialize tennis");
	}

	@Override
	void startPlay() {
		System.out.println("Start tennis");
	}

	@Override
	void endPlay() {
		System.out.println("End tennis");
	}
}
