package org.example;

import com.microsoft.appcenter.appium.EnhancedAndroidDriver;
import com.microsoft.appcenter.appium.Factory;
import io.appium.java_client.MobileElement;
import io.appium.java_client.remote.MobileCapabilityType;
import org.apache.commons.codec.binary.Base64;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StartIOSAppTest {

    @Rule
    public TestWatcher watcher = Factory.createWatcher();


    private static EnhancedAndroidDriver<MobileElement> driver;


    public static EnhancedAndroidDriver<MobileElement> startApp() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("bundleId", "com.node.io.apps.myexpo");
//        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "9A9AY1CDPS");
//        capabilities.setCapability(MobileCapabilityType.APP, "myexpo.apk");
//        capabilities.setCapability(MobileCapabilityType.NO_RESET, true);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "android");
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");

        URL url = new URL("http://localhost:4723/wd/hub");
        return Factory.createAndroidDriver(url, capabilities);
        //appcenter test run appium --app "dozken/gsg" --devices 6678f550 --app-path myexpo.apk --test-series "master" --locale "en_US" --build-dir target/upload
    }


    @Before
    public void beforeEach() throws Exception{
        driver.label("App Launched");
        driver = startApp();

        driver.label("App reset");
        driver.resetApp();
        driver.startRecordingScreen();
        Thread.sleep(5000);
    }

    @After
    public void afterEach() throws Exception {
        String base64String = driver.stopRecordingScreen();
        byte[] data = Base64.decodeBase64(base64String);
        String destinationPath = "artefacts/filename.mp4";
        Path path = Paths.get(destinationPath);
        Files.write(path, data);

        driver.label("Stopping App");
        driver.quit();
    }

    @Test
    public void canStartAppInTest() throws MalformedURLException, InterruptedException {
        driver.label("App Launched");
        Thread.sleep(5000);

        WebElement testText = driver.findElementByAccessibilityId("testText");

        Thread.sleep(5000);

        assertEquals(testText.getText(), "testText");
        assertTrue(testText.isDisplayed());
    }

//
//    @Test
//    public void canStartAppInTest2() throws MalformedURLException, InterruptedException {
//
//        WebElement testText = driver.findElementByAccessibilityId("testText");
//        assertEquals(testText.getText(), "testText");
//        assertTrue(testText.isDisplayed());
//    }
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
