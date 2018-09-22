package ca.mcgill.ecse321.hello;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainApp {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("SpringBeans.xml");

		HelloWorld hi = (HelloWorld) context.getBean("helloBean");
		hi.sayHi();
	}
}
