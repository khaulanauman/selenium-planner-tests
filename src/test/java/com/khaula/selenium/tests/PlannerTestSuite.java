package com.khaula.selenium.tests;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;


import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PlannerTestSuite {

    private static WebDriver driver;

    @BeforeAll
    public static void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--window-size=1920,1080");
        driver = new ChromeDriver(options);
        driver.get("http://13.218.202.41:8000/");
    }

    @AfterAll
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @Order(1)
    public void testPageTitle() {
        String title = driver.getTitle();
        assertEquals("Khaula's To-Do List", title);
    }

    @Test
    @Order(2)
    public void testTaskInputFieldExists() {
        WebElement input = driver.findElement(By.id("new-task"));
        assertTrue(input.isDisplayed());
    }

    @Test
    @Order(3)
    public void testTodoListContainerExists() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement list = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("todo-list")));
        assertNotNull(list);
    }
 

    @Test
    @Order(4)
    public void testAddValidTask() {
        WebElement input = driver.findElement(By.id("new-task"));
        input.clear();
        input.sendKeys("Selenium Task A");
        input.submit();

        WebElement newTask = driver.findElement(By.xpath("//li[contains(text(), 'Selenium Task A')]"));
        assertTrue(newTask.isDisplayed());
    }

    @Test
    @Order(5)
    public void testAddAnotherTask() {
        WebElement input = driver.findElement(By.id("new-task"));
        input.clear();
        input.sendKeys("Selenium Task B");
        input.submit();

        WebElement newTask = driver.findElement(By.xpath("//li[contains(text(), 'Selenium Task B')]"));
        assertTrue(newTask.isDisplayed());
    }

    @Test
    @Order(6)
    public void testDeleteButtonPresent() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Add a unique task just for this test
        String taskText = "DeleteBtnTest-" + System.currentTimeMillis();
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(By.id("new-task")));
        input.clear();
        input.sendKeys(taskText);
        input.sendKeys(Keys.ENTER);

        // Wait for the task to appear
        WebElement taskItem = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//li[contains(text(), '" + taskText + "')]")
        ));

        // Check for delete button inside that <li>
        WebElement deleteBtn = taskItem.findElement(By.tagName("button"));
        assertTrue(deleteBtn.isDisplayed(), "Delete button should be present");
    }


    @Test
    @Order(7)
    public void testDeleteTask() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // 1️ - Add a unique item
        String uniqueTask = "DeleteTest-" + System.currentTimeMillis();
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(By.id("new-task")));
        input.sendKeys(uniqueTask);
        input.sendKeys(Keys.ENTER); // Submits the task

        // 2️ - Wait until the item appears in the DOM
        WebElement newTask = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//li[contains(text(), '" + uniqueTask + "')]")
        ));

        // 3️ - Click the delete button
        WebElement deleteBtn = newTask.findElement(By.tagName("button"));
        deleteBtn.click();

        // 4️ - Wait until the element disappears
        wait.until(ExpectedConditions.stalenessOf(newTask));

        // 5️ - Assert it's no longer in the DOM
        boolean stillPresent = driver.findElements(
            By.xpath("//li[contains(text(), '" + uniqueTask + "')]")
        ).size() > 0;

        assertFalse(stillPresent, "The task should be removed from the list");
    }




    @Test
    @Order(8)
    public void testSubmitEmptyTask() {
        int before = driver.findElements(By.xpath("//li")).size();

        WebElement input = driver.findElement(By.id("new-task"));
        input.clear();
        input.sendKeys("");
        input.sendKeys(Keys.ENTER); // simulate submit

        // Optional: Wait a little for any unexpected DOM updates
        try {
            Thread.sleep(1000); // 1 second
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int after = driver.findElements(By.xpath("//li")).size();

        assertEquals(before, after, "No new task should be added on empty input");
    }



    @Test
    @Order(9)
    public void testAddDuplicateTask() {
        WebElement input = driver.findElement(By.id("new-task"));
        input.clear();
        input.sendKeys("Selenium Task B");
        input.submit();

        // Check if both instances of the same name are visible
        int occurrences = driver.findElements(By.xpath("//li[contains(text(), 'Selenium Task B')]")).size();
        assertTrue(occurrences >= 2);
    }

    @Test
    @Order(10)
    public void testTaskListHasMultipleItems() {
        int taskCount = driver.findElements(By.xpath("//li")).size();
        assertTrue(taskCount >= 2);
    }
}
