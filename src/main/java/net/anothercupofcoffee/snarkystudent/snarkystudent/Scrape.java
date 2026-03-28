package net.anothercupofcoffee.snarkystudent.snarkystudent;


import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

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
    
    public void fetchProf() {
        WebElement subjectText = driver.findElement(By.xpath("//input[@id='s2id_txt_subject]'"));
        subjectText.sendKeys("Mathematics");
        subjectText.sendKeys(Keys.ARROW_DOWN);
        subjectText.sendKeys(Keys.ENTER);
        WebElement courseNumText = driver.findElement(By.xpath("//input[@id='txt_courseNumber']'"));
        courseNumText.sendKeys("1213");
        subjectText.sendKeys(Keys.ENTER);
    }

}
