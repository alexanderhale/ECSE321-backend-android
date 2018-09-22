package ca.mcgill.ecse321.hello;

/**
 * A Spring Bean
 */
public class HelloWorld {
	private String name;

  /**
   * A setter
   */
	public void setName(String name) {
		this.name = name;
	}

	public void sayHi() {
		System.out.println("Hi, " + name);
	}
}
