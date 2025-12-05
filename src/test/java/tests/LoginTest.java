package tests;

import base.DriverFactory;
import engine.KeywordEngine;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

public class LoginTest {

    @Test
    public void testLoginUsingKeywordFramework() throws Exception {
        KeywordEngine engine = new KeywordEngine();
        engine.runTest("TC_LOGIN_001");
    }

    @AfterClass
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}
