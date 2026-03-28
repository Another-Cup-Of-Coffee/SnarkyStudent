package WebDriver;

import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.chrome.ChromeDriver;

public class WebDriver_Class {
    public static void main(String[] args) {
        // Set the system property for ChromeDriver (path to chromedriver executable)
        System.setProperty("webdriver.chrome.driver", "D:/chromedriver_win32/chromedriver.exe");

        // Create an instance of ChromeDriver (launch the Chrome browser)
        WebDriver driver = new WebDriver();

        try {
            // Navigate to the desired website (GeeksforGeeks in this example)
            driver.get("https://www.geeksforgeeks.org/");

            // Get and print the page title
            String pageTitle = driver.getTitle();
            System.out.println("Page Title: " + pageTitle);

            // Wait for a few seconds (for demonstration purposes only)
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // Close the browser
            //driver.quit();
        }
    }
}