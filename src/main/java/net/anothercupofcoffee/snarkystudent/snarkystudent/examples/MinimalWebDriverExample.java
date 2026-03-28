package net.anothercupofcoffee.snarkystudent.snarkystudent.examples;

import org.openqa.selenium.firefox.FirefoxDriver;

public class MinimalWebDriverExample {
    public static void runWebDriver() {
    	var driver = new FirefoxDriver();
		driver.get("https://example.com");
		System.out.println("Title: " + driver.getTitle());

		driver.quit();
    }
}
