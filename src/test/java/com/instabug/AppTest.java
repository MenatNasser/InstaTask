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
    public void passwordOnlyfilledCase() {
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
            emailField.sendKeys("mennaforting@gmail.com");

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
            WebDriverWait wait = new WebDriverWait(driver, 5);
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@ng-show='developerLoginVm.invalidMessage']/p")));

            WebElement wrongCredErrorMsg = driver.findElement(By.xpath("//div[@ng-show='developerLoginVm.invalidMessage']/p"));
            assertTrue(wrongCredErrorMsg.isDisplayed());
        }
        catch (TimeoutException e) {
            System.out.println("TimeOut");
            fail();
        }
    }

    //Sixth case: Verifying 'Forget password?' link
    @Test
    public void forgetPasswordCase() {
        //Checking existence of 'Forget password?' link
        try {
            WebElement forgetPasswordLink = driver.findElement(By.xpath("//a[@href='/forgot']"));
            forgetPasswordLink.click();
            WebDriverWait wait = new WebDriverWait(driver, 2);
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("div.u-text--center.u-color-ink-0.u-bottom-margin--6x > div.u-font--xlarge.u-font--semibold")));
            WebElement forgetPasswordPage = driver.findElement(By.cssSelector("div.u-text--center.u-color-ink-0.u-bottom-margin--6x > div.u-font--xlarge.u-font--semibold"));
            assertTrue(forgetPasswordPage.isDisplayed());
        }
        catch (NoSuchElementException e) {
            fail();
        }
    }

    //Seventh case: Verifying 'Sign up' link
    @Test
    public void signUpCase() {
        //Checking existence of 'Sign up' link
        try {
            WebElement signUpLink = driver.findElement(By.xpath("//a[@href='/signup']"));
            signUpLink.click();
            WebDriverWait wait = new WebDriverWait(driver, 2);
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("div.u-text--center.u-color-ink-0 > div.u-font--xlarge.u-font--semibold")));
            WebElement forgetPasswordPage = driver.findElement(By.cssSelector("div.u-text--center.u-color-ink-0 > div.u-font--xlarge.u-font--semibold"));
            assertTrue(forgetPasswordPage.isDisplayed());
        }
        catch (NoSuchElementException e) {
            fail();
        }
    }

    //Eighth case: Invalid mail format
    @Test
    public void invalidEmailCase() {

        WebElement emailField = driver.findElement(By.id("developer_email"));
        emailField.sendKeys("metest.com");


        WebElement PasswordField = driver.findElement(By.id("password"));
        PasswordField.sendKeys("menna_1991");
        assertTrue(PasswordField.isDisplayed());

        WebElement loginButton = driver.findElement(By.xpath("//*[@class='c-button c-button--info c-button--block c-button--xlarge u-bottom-margin--1x']"));
        loginButton.click();

        //Asserting on invalid mail error
        try {
            WebDriverWait wait = new WebDriverWait(driver, 2);
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("div.u-bottom-margin--3x > form > div:nth-child(2) > p")));
            WebElement emailFieldError = driver.findElement(By.cssSelector("div.u-bottom-margin--3x > form > div:nth-child(2) > p"));
            assertTrue(emailFieldError.isDisplayed());

        } catch (TimeoutException e) {
            System.out.println("TimeOut");
            fail();
        }

    }

    //Ninth case: Short password used
    @Test
    public void shortPasswordCase() {

        WebElement emailField = driver.findElement(By.id("developer_email"));
        emailField.sendKeys("mennafortesting@gmail.com");


        WebElement PasswordField = driver.findElement(By.id("password"));
        PasswordField.sendKeys("menn");
        assertTrue(PasswordField.isDisplayed());
        emailField.click();

        WebElement loginButton = driver.findElement(By.xpath("//*[@class='c-button c-button--info c-button--block c-button--xlarge u-bottom-margin--1x']"));
        assertFalse(loginButton.isEnabled());

        //Asserting on error from short password
        try {

            WebDriverWait wait = new WebDriverWait(driver, 5);
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("div.u-bottom-margin--3x > form > div:nth-child(4) > p")));
            WebElement passwordFieldError = driver.findElement(By.cssSelector("div.u-bottom-margin--3x > form > div:nth-child(4) > p"));
            assertTrue(passwordFieldError.isDisplayed());

        }
        catch (TimeoutException e) {
            System.out.println("TimeOut");
            fail();
        }
    }

    // Tenth case: Special characters used in fields
    @Test
    public void specialCharsCase() {

        WebElement emailField = driver.findElement(By.id("developer_email"));
        emailField.sendKeys("!@#$%^%$");
        assertTrue(emailField.isDisplayed());


        WebElement PasswordField = driver.findElement(By.id("password"));
        PasswordField.sendKeys("!@@");
        assertTrue(PasswordField.isDisplayed());
        emailField.click();

        WebElement loginButton = driver.findElement(By.xpath("//*[@class='c-button c-button--info c-button--block c-button--xlarge u-bottom-margin--1x']"));
        assertFalse(loginButton.isEnabled());

        //Asserting for the errors
        try {
            WebDriverWait wait = new WebDriverWait(driver, 5);
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("div.u-bottom-margin--3x > form > div:nth-child(2) > p")));
            WebElement emailFieldError = driver.findElement(By.cssSelector("div.u-bottom-margin--3x > form > div:nth-child(2) > p"));
            assertTrue(emailFieldError.isDisplayed());

            WebElement passwordFieldError = driver.findElement(By.cssSelector("div.u-bottom-margin--3x > form > div:nth-child(4) > p"));
            assertTrue(passwordFieldError.isDisplayed());

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
