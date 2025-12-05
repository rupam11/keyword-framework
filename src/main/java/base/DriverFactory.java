package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class DriverFactory {

    private static WebDriver driver;

    private DriverFactory() {
        // private constructor
    }

    public static WebDriver getDriver() {
        if (driver == null) {
            // For simplicity, directly using Chrome
            // You can add switch for browser later using config.properties
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            driver.manage().timeouts()
                  .implicitlyWait(Duration.ofSeconds(10));
        }
        return driver;
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
