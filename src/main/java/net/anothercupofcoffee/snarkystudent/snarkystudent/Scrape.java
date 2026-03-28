package net.anothercupofcoffee.snarkystudent.snarkystudent;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Scrape {
    private WebDriver driver;
    private WebDriverWait wait;
    private int pageNumber;
    private int resultsPerPage = 120;
    private boolean nextIsEnabled = true;

    public void registrationScraper() {
        driver = new ChromeDriver();
        driver.get("https://ssbprod.utsa.edu/StudentRegistrationSsb/ssb/classSearch/classSearch");
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        
    }

}
