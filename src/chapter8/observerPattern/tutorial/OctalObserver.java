package chapter8.observerPattern.tutorial;


public class OctalObserver extends Observer {

	public OctalObserver(Subject subject) {
		this.subject = subject;
		subject.attach(this);

	}

	@Override
	public void update() {
		System.out.println("Octal string: " + Integer.toOctalString(subject.getState()));
	}
}
