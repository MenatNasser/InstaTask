package com.instabug.Pages;

import com.instabug.Base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage extends BaseTest {

    @FindBy(id = "developer_email")
    public WebElement emailField;

    @FindBy(id = "password")
    public WebElement passwordField;

    @FindBy(xpath = "//*[@class='c-button c-button--info c-button--block c-button--xlarge u-bottom-margin--1x']")
    public WebElement loginButton;

    @FindBy(xpath = "//a[@href='/forgot']")
    public WebElement forgetPasswordLink;

    @FindBy(xpath = "//a[@href='/signup']")
    public WebElement signUpLink;

    public LoginPage() {
        try {
            //Initializes annotated by FindBy elements
            PageFactory.initElements(driver, this);
        } catch (
                NoSuchElementException e) {
            System.out.println("Elements not found" + e.getMessage());
        }
    }

    public WebElement getWrongCredErrorMsg() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@ng-show='developerLoginVm.invalidMessage']/p")));
        return driver.findElement(By.xpath("//div[@ng-show='developerLoginVm.invalidMessage']/p"));
    }

    public WebElement getEmailFieldError() {
        WebDriverWait wait = new WebDriverWait(driver, 2);
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("div.u-bottom-margin--3x > form > div:nth-child(2) > p")));
        return driver.findElement(By.cssSelector("div.u-bottom-margin--3x > form > div:nth-child(2) > p"));
    }

    public WebElement getPasswordFieldError() {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("div.u-bottom-margin--3x > form > div:nth-child(4) > p")));
        return driver.findElement(By.cssSelector("div.u-bottom-margin--3x > form > div:nth-child(4) > p"));
    }
}
