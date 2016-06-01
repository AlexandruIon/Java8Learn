package chapter8.chainOfResponsibility;


public class ConsoleLogger extends AbstractLogger{

	public ConsoleLogger(int level){
		this.level = level;
	}

	@Override
	protected void write(String message) {
		System.out.println("standard Console::Logger: " + message);
	}
}
