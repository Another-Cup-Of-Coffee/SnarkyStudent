package net.anothercupofcoffee.snarkystudent.snarkystudent;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ScrapeDegreeCatalog {

    public static void testScrape() {
        var result = scrapeCourses();
        System.out.println(result.classCodeToName);
        System.out.println(result.prereqs);

        for (String key : result.classCodeToName.keySet()) {
            var value = result.classCodeToName.get(key);
            System.out.println("Key:[" + key + "], val:[" + value + "]");
        }
    }

    public static class Result {
        public Map<String, String> classCodeToName;
        public Map<String, List<String>> prereqs;

        public Result(Map<String, String> classCodeToName,
                      Map<String, List<String>> prereqs) {
            this.classCodeToName = classCodeToName;
            this.prereqs = prereqs;
        }
    }

    public static Result scrapeCourses() {
        WebDriver driver = new ChromeDriver();

        driver.get("https://catalog.utsa.edu/undergraduate/sciences/mathematics/");

        new WebDriverWait(driver, Duration.ofSeconds(10))
            .until(d -> d.findElements(By.cssSelector(".courseblock")).size() > 0);

        List<WebElement> courses = driver.findElements(By.cssSelector(".courseblock"));

        Map<String, String> classCodeToName = new HashMap<>();
        Map<String, List<String>> prereqs = new HashMap<>();

        Pattern codePattern = Pattern.compile("([A-Z]{3}\\s*\\d{4})");

        for (WebElement course : courses) {
            try {
                // ✅ Get title from <strong>
                List<WebElement> strongs = course.findElements(By.cssSelector(".courseblocktitle strong"));
                if (strongs.isEmpty()) continue;

                String title = strongs.get(0).getAttribute("textContent");
                title = title.replaceAll("\\s+", " ").trim();

                // DEBUG
                System.out.println("TITLE: [" + title + "]");

                // --- EXTRACT CODE ---
                Matcher codeMatcher = codePattern.matcher(title);
                if (!codeMatcher.find()) continue;

                String code = codeMatcher.group(1).replaceAll("\\s+", " ").trim();

                // --- EXTRACT NAME ---
                String remaining = title.substring(codeMatcher.end()).trim();
                remaining = remaining.replaceFirst("^[\\.\\-\\s]+", "");

                int periodIndex = remaining.indexOf(".");
                String name = (periodIndex != -1)
                        ? remaining.substring(0, periodIndex).trim()
                        : remaining.trim();

                if (name.isEmpty()) continue;

                classCodeToName.put(code, name);

                // ✅ Get description normally
                String desc = "";
                List<WebElement> descElements = course.findElements(By.cssSelector(".courseblockdesc"));
                if (!descElements.isEmpty()) {
                    desc = descElements.get(0).getAttribute("textContent");
                }

                // --- PREREQS ---
                List<String> prereqList = new ArrayList<>();

                if (desc.contains("Prerequisite")) {
                    Matcher matcher = codePattern.matcher(desc);

                    while (matcher.find()) {
                        String prereq = matcher.group(1).replaceAll("\\s+", " ").trim();

                        if (!prereq.equals(code)) {
                            prereqList.add(prereq);
                        }
                    }
                }

                prereqs.put(code, prereqList);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        driver.quit();

        // Cleanup invalid prereqs
        for (String course : prereqs.keySet()) {
            prereqs.get(course).removeIf(p -> !classCodeToName.containsKey(p));
        }

        return new Result(classCodeToName, prereqs);
    }

/*
    public static Result scrapeCourses() {
        WebDriver driver = new ChromeDriver();

        driver.get("https://catalog.utsa.edu/undergraduate/sciences/mathematics/");

        // Wait until course blocks are loaded
        new WebDriverWait(driver, Duration.ofSeconds(10))
            .until(d -> d.findElements(By.cssSelector(".courseblock")).size() > 0);

        List<WebElement> courses = driver.findElements(By.cssSelector(".courseblock"));

        for (int i = 0; i < Math.min(3, courses.size()); i++) {
            System.out.println("HTML: " + courses.get(i).getAttribute("innerHTML"));
        }

        Map<String, String> classCodeToName = new HashMap<>();
        Map<String, List<String>> prereqs = new HashMap<>();

        Pattern codePattern = Pattern.compile("([A-Z]{3}\\s*\\d{4})");

        for (WebElement course : courses) {
            try {
                // 🔑 Get ALL text from the course block
                String fullText = course.getText();

                if (fullText == null || fullText.isBlank()) {
                    continue;
                }

                // Normalize whitespace
                fullText = fullText.replaceAll("\\s+", " ").trim();

                // DEBUG (optional)
                // System.out.println("FULL BLOCK: [" + fullText + "]");

                // --- EXTRACT COURSE CODE ---
                Matcher codeMatcher = codePattern.matcher(fullText);
                if (!codeMatcher.find()) {
                    continue;
                }

                String code = codeMatcher.group(1).replaceAll("\\s+", " ").trim();

                // --- EXTRACT COURSE NAME ---
                String remaining = fullText.substring(codeMatcher.end()).trim();

                // Remove leading punctuation
                remaining = remaining.replaceFirst("^[\\.\\-\\s]+", "");

                // Cut at first period (end of course name)
                int periodIndex = remaining.indexOf(".");
                String name = (periodIndex != -1)
                        ? remaining.substring(0, periodIndex).trim()
                        : remaining.trim();

                if (name.isEmpty()) continue;

                classCodeToName.put(code, name);

                // --- EXTRACT PREREQUISITES ---
                List<String> prereqList = new ArrayList<>();

                if (fullText.contains("Prerequisite")) {
                    Matcher matcher = codePattern.matcher(fullText);

                    while (matcher.find()) {
                        String prereq = matcher.group(1).replaceAll("\\s+", " ").trim();

                        if (!prereq.equals(code)) {
                            prereqList.add(prereq);
                        }
                    }
                }

                prereqs.put(code, prereqList);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        driver.quit();

        // Remove prereqs that aren't in our dataset
        for (String course : prereqs.keySet()) {
            prereqs.get(course).removeIf(p -> !classCodeToName.containsKey(p));
        }

        return new Result(classCodeToName, prereqs);
    }*/

    // public static Result scrapeCourses() {
    //     WebDriver driver = new ChromeDriver();

    //     driver.get("https://catalog.utsa.edu/undergraduate/sciences/mathematics/");

    //     // Wait until course blocks are loaded
    //     new WebDriverWait(driver, Duration.ofSeconds(10))
    //         .until(d -> d.findElements(By.cssSelector(".courseblocktitle")).size() > 0);


    //     System.out.println("Course blocks found: " + 
    //           driver.findElements(By.cssSelector(".courseblock")).size());

    //     // NOW it's safe to query
    //     List<WebElement> courses = driver.findElements(By.cssSelector(".courseblock"));

    //     Map<String, String> classCodeToName = new HashMap<>();
    //     Map<String, List<String>> prereqs = new HashMap<>();

    //     Pattern codePattern = Pattern.compile("([A-Z]{3}\\s*\\d{4})");

    //     for (WebElement course : courses) {
    //         try {
    //             String fullText = course.getText().replaceAll("\\s+", " ").trim();

    //             System.out.println("FULL BLOCK: [" + fullText + "]");

    //             // --- EXTRACT COURSE CODE ---
    //             Matcher codeMatcher = codePattern.matcher(fullText);

    //             if (!codeMatcher.find()) {
    //                 continue;
    //             }

    //             String code = codeMatcher.group(1).replaceAll("\\s+", " ").trim();

    //             // --- EXTRACT COURSE NAME ---
    //             String remaining = fullText.substring(codeMatcher.end()).trim();

    //             // Remove leading punctuation
    //             remaining = remaining.replaceFirst("^[\\.\\-\\s]+", "");

    //             // Cut at first period
    //             int periodIndex = remaining.indexOf(".");
    //             String name = (periodIndex != -1)
    //                     ? remaining.substring(0, periodIndex).trim()
    //                     : remaining.trim();

    //             if (name.isEmpty()) continue;

    //             classCodeToName.put(code, name);

    //             // --- DESCRIPTION ---
    //             String desc = "";
    //             List<WebElement> descElements = course.findElements(By.cssSelector(".courseblockdesc"));
    //             if (!descElements.isEmpty()) {
    //                 desc = descElements.get(0).getText();
    //             }

    //             // --- PREREQUISITES ---
    //             List<String> prereqList = new ArrayList<>();

    //             if (desc.contains("Prerequisite")) {
    //                 Matcher matcher = codePattern.matcher(desc);

    //                 while (matcher.find()) {
    //                     String prereq = matcher.group(1).replaceAll("\\s+", " ").trim();

    //                     if (!prereq.equals(code)) {
    //                         prereqList.add(prereq);
    //                     }
    //                 }
    //             }

    //             prereqs.put(code, prereqList);

    //         } catch (Exception e) {
    //             System.out.println("Error processing course block");
    //             e.printStackTrace();
    //         }
    //     }

    //     driver.quit();

    //     // Keep only valid prereqs
    //     for (String course : prereqs.keySet()) {
    //         prereqs.get(course).removeIf(p -> !classCodeToName.containsKey(p));
    //     }

    //     return new Result(classCodeToName, prereqs);
    // }

}
