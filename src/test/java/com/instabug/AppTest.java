package com.instabug;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import static org.junit.Assert.*;

/**
 * Automated test cases for Login page.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    private WebDriver driver;

    // This is to setup testing execution before each test case
    @Before
    public void setupTestExec() {

        //Closing any open webdriver opens
//          if (driver.getWindowHandles().isEmpty())
//        {
//            System.out.println("New driver will be initiated");
//        }
//          else
//          {
//              driver.quit();
//          }


        //Initiating new driver for test cases
        driver = new ChromeDriver();

        //Enter login  page url to use in executing test cases
        driver.get("https://dashboard.instabug.com/login");

    }

    // First case: Successful login using correct credentials
    @Test
    public void successFullLoginWithCredentialsCase() {

        //Checking availability of web elements to enter credentials values in it
        try {
            //Email field
            WebElement emailField = driver.findElement(By.id("developer_email"));
            emailField.sendKeys("mennafortesting@gmail.com");

            //Password field
            WebElement PasswordField = driver.findElement(By.id("password"));
            PasswordField.sendKeys("menna_1991");

            // Login button
            WebElement loginButton = driver.findElement(By.xpath("//*[@class='c-button c-button--info c-button--block c-button--xlarge u-bottom-margin--1x']"));
            loginButton.click();

        }
        // Catching exception in case any of fields no existing
        catch (NoSuchElementException e) {
            fail();
        }

        //Verifying successful login
        try {
            //Wait time till login page loads successfully
            WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("c-spark-avatar")));

            //Asserting on Profile logo and toggle list after login
            WebElement validatingLogin = driver.findElement(By.className("c-spark-avatar"));
            assertTrue(validatingLogin.isDisplayed());

            WebElement toggleList = driver.findElement(By.className("-profile"));
            assertTrue(toggleList.isDisplayed());
            toggleList.click();

            WebDriverWait wait2 = new WebDriverWait(driver, 10);
            wait2.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//a[@ng-click='topNavbarMenuVm.onLogout()']")));
            WebElement logoutElement = driver.findElement(By.xpath("//a[@ng-click='topNavbarMenuVm.onLogout()']"));
            logoutElement.click();
        }

        //In case of failed login page load
        catch (TimeoutException e) {
            System.out.println("TimeOut");
            fail();
        }
    }


    // Second case: Both Email and Password Fields are blank.
    @Test
    public void blankFieldsCase() {

        //Checking that login button can't be enabled with empty fields for email and password
        try {
            // Login button
            WebElement loginButton = driver.findElement(By.xpath("//*[@class='c-button c-button--info c-button--block c-button--xlarge u-bottom-margin--1x']"));
            assertFalse(loginButton.isEnabled());
        }
        // Catching exception in case any of fields no existing
        catch (NoSuchElementException e) {
            fail();
        }
    }


    // Third case: Email field is filled and Password field is blank
    @Test
    public void emailOnlyEnteredCase() {
        //Entering value to email only
        try {
            //Email field
            WebElement emailField = driver.findElement(By.id("developer_email"));
            emailField.sendKeys("mennafortesting@gmail.com");

            //Password field
            WebElement PasswordField = driver.findElement(By.id("password"));
            PasswordField.sendKeys("");

            // Login button
            WebElement loginButton = driver.findElement(By.xpath("//*[@class='c-button c-button--info c-button--block c-button--xlarge u-bottom-margin--1x']"));
            assertFalse(loginButton.isEnabled());
        }

        // Catching exception in case any of fields no existing
        catch (NoSuchElementException e) {
            fail();
        }
    }

    // Fourth case: Password field is filled and email field is blank
    @Test
    public void passwordOverfilledCase() {
        //Entering value to password only
        try {
            //Email field
            WebElement emailField = driver.findElement(By.id("developer_email"));
            emailField.sendKeys("");

            //Password field
            WebElement PasswordField = driver.findElement(By.id("password"));
            PasswordField.sendKeys("menna_1991");

            // Login button
            WebElement loginButton = driver.findElement(By.xpath("//*[@class='c-button c-button--info c-button--block c-button--xlarge u-bottom-margin--1x']"));
            assertFalse(loginButton.isEnabled());
        }

        // Catching exception in case any of fields no existing
        catch (NoSuchElementException e) {
            fail();
        }
    }

    // Fifth case: Email and/or Password are entered wrong
    @Test
    public void wrongCredentialsCase() {
        try {
            //Email field entered with wrong value
            WebElement emailField = driver.findElement(By.id("developer_email"));
            emailField.sendKeys("mennafortting@gmail.com");

            //Password field entered with wrong value
            WebElement PasswordField = driver.findElement(By.id("password"));
            PasswordField.sendKeys("menna1991");

            // Login button
            WebElement loginButton = driver.findElement(By.xpath("//*[@class='c-button c-button--info c-button--block c-button--xlarge u-bottom-margin--1x']"));
            assertTrue(loginButton.isEnabled());
            loginButton.click();
        }
        // Catching exception in case any of fields no existing
        catch (NoSuchElementException e) {
            fail();
        }
        //Asserting on error message returns in case of any wrong credentials
        try {
            //Wait time till error message appears
            WebDriverWait wait = new WebDriverWait(driver, 2);
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@ng-show='developerLoginVm.invalidMessage']/p")));

            WebElement wrongCredErrorMsg = driver.findElement(By.xpath("//div[@ng-show='developerLoginVm.invalidMessage']/p"));
            assertTrue(wrongCredErrorMsg.isDisplayed());
        }
        catch (TimeoutException e) {
            System.out.println("TimeOut");
            fail();
        }
    }

    @After
    public void close() {
        driver.close();
        driver.quit();
    }
}
