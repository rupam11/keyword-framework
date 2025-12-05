package utils;

import org.openqa.selenium.By;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ORReader {

    private static Properties or = new Properties();

    static {
        try {
            FileInputStream fis = new FileInputStream(
                "src/test/resources/config/OR.properties"
            );
            or.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("OR.properties not found!");
        }
    }

    public static By getLocator(String objectName) {
        String value = or.getProperty(objectName);
        if (value == null) {
            throw new RuntimeException("No locator found for: " + objectName);
        }

        String[] parts = value.split("=", 2);
        String type = parts[0].trim();
        String locator = parts[1].trim();

        switch (type.toLowerCase()) {
            case "id":       return By.id(locator);
            case "name":     return By.name(locator);
            case "xpath":    return By.xpath(locator);
            case "css":      return By.cssSelector(locator);
            case "linktext": return By.linkText(locator);
            default:
                throw new RuntimeException("Invalid locator type: " + type);
        }
    }
}
