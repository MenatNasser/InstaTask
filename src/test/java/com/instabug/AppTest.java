package com.instabug;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    private WebDriver driver;

    @Before
    public void setup() {

        //close browser

        driver = new ChromeDriver();

//        driver.get("https://instabug.com");
//        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
//        WebElement link= driver.findElements(By.linkText("Login")).get(0);
//        WebElement element = driver.findElements(By.cssSelector("a[href=https://dashboard.instabug.com/login]")).get(0);
//        link.click();

        driver.get("https://dashboard.instabug.com/login");
        //logout

    }

    // write login successful
    @Test
    public void successFullLoginWithCredentialsCase() {
        try {

            WebElement emailField = driver.findElement(By.id("developer_email"));
            emailField.sendKeys("mennafortesting@gmail.com");

            WebElement PasswordField = driver.findElement(By.id("password"));
            PasswordField.sendKeys("menna_1991");

            WebElement loginButton = driver.findElement(By.xpath("//*[@class='c-button c-button--info c-button--block c-button--xlarge u-bottom-margin--1x']"));
            loginButton.click();

        } catch (NoSuchElementException e) {
            fail();
        }


        try {
            //detecting login validation
            WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("c-spark-avatar")));

            WebElement validatingLogin = driver.findElement(By.className("c-spark-avatar"));
            assertTrue(validatingLogin.isDisplayed());

            WebElement toggleList = driver.findElement(By.className("-profile"));
            assertTrue(toggleList.isDisplayed());
            toggleList.click();

            WebDriverWait wait2 = new WebDriverWait(driver, 10);
            wait2.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//a[@ng-click='topNavbarMenuVm.onLogout()']")));
            WebElement logoutElement = driver.findElement(By.xpath("//a[@ng-click='topNavbarMenuVm.onLogout()']"));
            System.out.println(logoutElement.getTagName());
            System.out.println(logoutElement);
            logoutElement.click();


        } catch (TimeoutException e) {
            System.out.println("TimeOut");
            fail();
        }


    }


    // Both Email and Password Fields are blank.
    @Test
    public void blankFieldsCase() {

        WebElement loginButton = driver.findElement(By.xpath("//*[@class='c-button c-button--info c-button--block c-button--xlarge u-bottom-margin--1x']"));
        if (loginButton.isEnabled()) {
            loginButton.click();
        } else {
            System.out.println("Can't leave fields empty");
        }

    }


    //Email field is filled and Password field is blank
    @Test
    public void emailOnlyfilledCase() {

        WebElement emailField = driver.findElement(By.id("developer_email"));
        if (emailField.isDisplayed()) {
            emailField.sendKeys("mennafortesting@gmail.com");
        } else {
            System.out.println("email field doesn't exists");
        }
        WebElement loginButton = driver.findElement(By.xpath("//*[@class='c-button c-button--info c-button--block c-button--xlarge u-bottom-margin--1x']"));
        if (loginButton.isEnabled()) {
            loginButton.click();
        } else {
            System.out.println("Can't password fields empty");
        }
    }

    //Password field is filled and email field is blank
    @Test
    public void passwordOverfilledCase() {

        WebElement PasswordField = driver.findElement(By.id("password"));
        if (PasswordField.isDisplayed()) {
            PasswordField.sendKeys("menna_1991");
        } else {

            WebElement loginButton = driver.findElement(By.xpath("//*[@class='c-button c-button--info c-button--block c-button--xlarge u-bottom-margin--1x']"));
            if (loginButton.isEnabled()) {
                loginButton.click();
            } else {
                System.out.println("Can't email fields empty");
            }
        }
    }

    //Email and/or Password are entered wrong
    @Test
    public void wrongCredentialsCase() {

        WebElement emailField = driver.findElement(By.id("developer_email"));
        emailField.sendKeys("me@test.com");
        assertTrue(emailField.isDisplayed());

        WebElement PasswordField = driver.findElement(By.id("password"));
        PasswordField.sendKeys("mnoeee_1991");
        assertTrue(PasswordField.isDisplayed());

        WebElement loginButton = driver.findElement(By.xpath("//*[@class='c-button c-button--info c-button--block c-button--xlarge u-bottom-margin--1x']"));
        loginButton.click();


        try {

            WebDriverWait wait = new WebDriverWait(driver, 2);
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@ng-show='developerLoginVm.invalidMessage']/p")));

            WebElement wrongCredErrorMsg = driver.findElement(By.xpath("//div[@ng-show='developerLoginVm.invalidMessage']/p"));
            assertTrue(wrongCredErrorMsg.isDisplayed());

        } catch (TimeoutException e) {
            System.out.println("TimeOut");
            fail();
        }

    }


    @After
    public void close() {
        //driver.close();
        //driver.quit();
    }
}
