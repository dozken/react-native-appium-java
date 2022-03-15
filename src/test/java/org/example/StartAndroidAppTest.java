package org.example;

import com.microsoft.appcenter.appium.EnhancedAndroidDriver;
import com.microsoft.appcenter.appium.Factory;
import io.appium.java_client.MobileElement;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StartAndroidAppTest {

    @Rule
    public TestWatcher watcher = Factory.createWatcher();

    private static EnhancedAndroidDriver<MobileElement> driver;

    public static EnhancedAndroidDriver<MobileElement> startApp() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "android");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "foo");
        capabilities.setCapability(MobileCapabilityType.APP, "myexpo.apk");
        capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 7913);
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiautomator2");

        URL url = new URL("http://localhost:4723/wd/hub");

        return Factory.createAndroidDriver(url, capabilities);
    }


    @Test
    public void canStartAppInTest() throws MalformedURLException, InterruptedException {
        driver = startApp();
        driver.label("Starting App");

        WebDriverWait wait = new WebDriverWait(driver, 10);

        WebElement testText = driver.findElementByAccessibilityId("testText");
        wait.until(ExpectedConditions.visibilityOf(testText));

        assertEquals(testText.getText(), "testText");
        assertTrue(testText.isDisplayed());
    }

    @After
    public void after() throws Exception {
        if (driver != null) {
            driver.label("Stopping App");
            driver.quit();
        }
    }
}
/*
1. Build and Package all your dependencies using Maven

Copy to clipboard
mvn -DskipTests -P prepare-for-upload package
2. Upload and schedule tests

appcenter test run appium --app "dozken/gsg" --devices 6adb7c80 --app-path pathToFile.apk --test-series "master" --locale "en_US" --build-dir target/upload
Getting help
To get help and see advanced options you can run:

Copy to clipboard
appcenter help test
appcenter help test run
appcenter help test run appium
appcenter help test prepare appium


appcenter test run appium --app "dozken/gsg" --devices 6adb7c80 --app-path myexpo.apk --test-series "master" --locale "en_US" --build-dir target/upload

 */
