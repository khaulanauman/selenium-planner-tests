package com.khaula.selenium.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

public class HomePageTest {
    public static void main(String[] args) {
        // Setup ChromeDriver using WebDriverManager
        WebDriverManager.chromedriver().setup();

        // Set Chrome to headless mode
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");  // Use "--headless=new" for Chrome 109+
        options.addArguments("--window-size=1920,1080");

        // Launch browser
        WebDriver driver = new ChromeDriver(options);

        // Navigate to your app (replace with your local or deployed URL)
        driver.get("http://13.218.202.41:8000/");

        // Print page title
        System.out.println("Page title is: " + driver.getTitle());

        // Close browser
        driver.quit();
    }
}
