package chapter8.templatePattern;


import java.util.function.Consumer;

public abstract class OnlineBanking {

	public void processCustomer(int id) {
		Customer customer = null;
		makeCustomerHappy(customer);
	}

	public void processCustomer(int id, Consumer<Customer> customerConsumer) {
		Customer customer = null;
		customerConsumer.accept(customer);
	}

	abstract void makeCustomerHappy(Customer c);

}

class Customer {

}