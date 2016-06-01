package chapter8.templatePattern;


public class FootballGame extends Game {

	@Override
	void initialize() {
		System.out.println("Initialize football");
	}

	@Override
	void startPlay() {
		System.out.println("Start football");
	}

	@Override
	void endPlay() {
		System.out.println("End football");
	}
}
