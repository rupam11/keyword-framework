package keywords;

import base.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.testng.Assert;
import utils.ORReader;

public class KeywordLibrary {

    private WebDriver driver;

    public KeywordLibrary() {
        this.driver = DriverFactory.getDriver();
    }

    // No parameters
    public void OPEN_BROWSER() {
        driver = DriverFactory.getDriver();
    }

    // 1 parameter – URL
    public void NAVIGATE(String url) {
        driver.get(url);
    }

    // 1 parameter – objectName
    public void CLICK(String objectName) {
        By locator = ORReader.getLocator(objectName);
        WebElement element = driver.findElement(locator);
        element.click();
    }

    // 2 parameters – objectName + data
    public void TYPE(String objectName, String data) {
        By locator = ORReader.getLocator(objectName);
        WebElement element = driver.findElement(locator);
        element.clear();
        element.sendKeys(data);
    }

    public void VERIFY_TEXT(String objectName, String expectedText) {
        By locator = ORReader.getLocator(objectName);
        WebElement element = driver.findElement(locator);
        String actual = element.getText().trim();
        Assert.assertTrue(
            actual.contains(expectedText),
            "Expected text to contain: '" + expectedText + "', but was: '" + actual + "'"
        );
    }

    public void CLOSE_BROWSER() {
        DriverFactory.quitDriver();
    }
}
