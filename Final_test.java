package com.ProjCert;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class Main {

    public static void main(String[] args) {
        try
        {
            // initialise the webdriver
            WebDriver driver = new HtmlUnitDriver();
            //browse to the required URL
            driver.get("http://localhost/");
            //select the About Us link and click it
            WebElement el = driver.findElement(By.linkText("About Us"));
            el.click();
            // Get the text by the tag name "body". content of About Page
            // WebElement s = driver.findElement(By.tagName("body")); // for php website with bugs
            WebElement s = driver.findElement(By.tagName("article"));
            s.getText();
            //Print the text in the body of the About Us Page and quit
            //System.out.println( s.getText());
            String test_output = s.getText();
            if (test_output.contains("About Us"))
            {
                System.out.println("PASS");
            } else
                {
                System.out.println("FAILED");
                driver.quit();
                }

            driver.quit();
        }
        catch (Exception err)
        {
            System.out.println("Test result ::::::: FAILED with Exception");

        }

    }
}