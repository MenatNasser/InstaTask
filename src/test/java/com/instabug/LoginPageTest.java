package com.instabug;

import com.instabug.Pages.LoginPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.*;

public class LoginPageTest extends LoginPage {

    // This is to setup testing execution before each test case
    @Before
    public void setUp() throws Exception
    {
        preStart();
    }

    // First case: Successful login using correct credentials
    @Test
    public void successFullLoginWithCredentialsCase()
    {

        emailField.sendKeys(properties.getProperty("email"));
        passwordField.sendKeys(properties.getProperty("password"));
        loginButton.click();

        //detecting login validation
        try {

            WebDriverWait wait = new WebDriverWait(driver, 20);
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("c-spark-avatar")));

            WebElement validatingLogin = driver.findElement(By.className("c-spark-avatar"));
            assertTrue(validatingLogin.isDisplayed());

            WebElement toggleList = driver.findElement(By.className("-profile"));
            assertTrue(toggleList.isDisplayed());
            toggleList.click();

            WebDriverWait wait2 = new WebDriverWait(driver, 20);
            wait2.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//a[@ng-click='topNavbarMenuVm.onLogout()']")));
            WebElement logoutElement = driver.findElement(By.xpath("//a[@ng-click='topNavbarMenuVm.onLogout()']"));
            System.out.println(logoutElement.getTagName());
            System.out.println(logoutElement);
            logoutElement.click();
        }

        catch (TimeoutException e) {
            System.out.println("TimeOut");
        }
    }

    // Second case: Both Email and Password Fields are blank.
    @Test
    public void blankFieldsCase()
    {
        assertFalse(loginButton.isEnabled());
    }

    // Third case: Email field is filled and Password field is blank
    @Test
    public void onlyEmailCase()
    {

        emailField.sendKeys(properties.getProperty("email"));
        assertFalse(loginButton.isEnabled());
    }

    // Fourth case: Password field is filled and email field is blank
    @Test
    public void onlyPasswordCase()
    {

        passwordField.sendKeys(properties.getProperty("password"));
        assertFalse(loginButton.isEnabled());
    }

    // Fifth case: Email and/or Password are entered wrong
    @Test
    public void wrongCredentialsCase()
    {

        emailField.sendKeys("me@test.com");
        passwordField.sendKeys("mnoeee_1991");
        loginButton.click();
        //Asserting on error message
        try
        {

            WebElement wrongCredErrorMsg = getWrongCredErrorMsg();
            assertTrue(wrongCredErrorMsg.isDisplayed());
        }
        catch (TimeoutException e)
        {
            System.out.println("TimeOut");
            fail();
        }
    }

    //Sixth case: Verifying 'Forget password?' link
    @Test
    public void forgetPasswordCase()
    {
        //Checking existence of 'Forget password?' link
        try
        {
            forgetPasswordLink.click();
            WebDriverWait wait = new WebDriverWait(driver, 15);
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("c-button")));
            WebElement resendPasswordBtn = driver.findElement(By.className("c-button"));
            assertTrue(resendPasswordBtn.isDisplayed());

        }
        catch (NoSuchElementException e)
        {
            System.out.println("Forget password not working " + e.getMessage());
            fail();
        }
    }

    //Seventh case: Verifying 'Sign up' link
    @Test
    public void signUpCase() {
        //Checking existence of 'Sign up' link
        try
        {
            signUpLink.click();
            WebDriverWait wait = new WebDriverWait(driver, 5);
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("div.u-text--center.u-color-ink-0 > div.u-font--xlarge.u-font--semibold")));
            WebElement signUpLink2 = driver.findElement(By.cssSelector("div.u-text--center.u-color-ink-0 > div.u-font--xlarge.u-font--semibold"));
            assertTrue(signUpLink2.isDisplayed());
        }
        catch (NoSuchElementException e)
        {
            System.out.println("Sign up not working " + e.getMessage());
            fail();
        }
    }

    //Eighth case: Invalid mail format
    @Test
    public void invalidEmailCase()
    {

        emailField.sendKeys("metest.com");
        passwordField.sendKeys(properties.getProperty("password"));
        loginButton.click();

        //Asserting on invalid mail error
        try
        {

            WebElement emailError = getEmailFieldError();
            assertTrue(emailError.isDisplayed());

        }
        catch (TimeoutException e)
        {
            System.out.println("TimeOut");
            fail();
        }
    }

    //Ninth case: Short password used
    @Test
    public void shortPasswordCase() {

        emailField.sendKeys(properties.getProperty("email"));
        passwordField.sendKeys("wp");
        emailField.click();
        assertFalse(loginButton.isEnabled());

        //Asserting on error from short password
        try
        {

            WebElement passwordError = getPasswordFieldError();
            assertTrue(passwordError.isDisplayed());

        }
        catch (TimeoutException e)
        {
            System.out.println("TimeOut");
            fail();
        }
    }

    // Tenth case: Special characters used in fields
    @Test
    public void invalidCase()
    {

        emailField.sendKeys("!@#$%^%$");
        passwordField.sendKeys("!@@");
        emailField.click();
        assertFalse(loginButton.isEnabled());

        //Asserting for the errors
        try
        {

            WebElement emailError = getEmailFieldError();
            assertTrue(emailError.isDisplayed());

            WebElement passworderror = getPasswordFieldError();
            assertTrue(passworderror.isDisplayed());

        }
        catch (TimeoutException e)
        {
            System.out.println("TimeOut");
            fail();
        }
    }


    @After
    public void tearDown() throws Exception
    {
        postStop();
    }
}
