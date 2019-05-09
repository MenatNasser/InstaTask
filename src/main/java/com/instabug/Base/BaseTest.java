package com.instabug.Base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.InputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class BaseTest {

    protected WebDriver driver;
    protected final Properties properties;

    /**
     * Constructor
     */
    public BaseTest() {
        properties = new Properties();
        // try with resource //
        try (InputStream input = new FileInputStream("src/main/resources/Config.properties")) {
            // load a properties file
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        driver = new ChromeDriver();
    }

    /**
     * Run before each case
     */
    public void preStart() {
        driver.get(properties.getProperty("url"));
    }

    /**
     * Run after each case
     */
    public void postStop() {
        driver.close();
        driver.quit();
    }

}

