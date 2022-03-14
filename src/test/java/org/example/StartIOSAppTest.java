package org.example;

import com.microsoft.appcenter.appium.EnhancedAndroidDriver;
import com.microsoft.appcenter.appium.Factory;
import io.appium.java_client.MobileElement;
import io.appium.java_client.remote.MobileCapabilityType;
import org.apache.commons.codec.binary.Base64;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
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

        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "android");

//        Commented Out lines below are only used for running local tests, not in App Center where they will be ignored
//        1. Run 'xcrun instruments -s devices' in terminal
//        2. Uncomment & paste the device or simulator ID you need to replace <Device ID> below
//        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone 13 Max Pro");
//        capabilities.setCapability(MobileCapabilityType.UDID, "<Device ID>");
//
//        3. (Use only when running on a local device) Set your Team ID for your signing identity and uncomment below
//        capabilities.setCapability("xcodeOrgId", "<Team ID>");
//        capabilities.setCapability("xcodeSigningId", "iPhone Developer");
//        capabilities.setCapability("showXcodeLog", true);

//        4. Uncomment the lines for either the iOS simulator or iOS device below
//        (Simulator) *Make sure to unzip the included file
//        String appPath = "/Users/kentgreen/Projects/AppCenter-Test-Samples/Appium/iOS/UITestDemo.iOS.app";
//        capabilities.setCapability(MobileCapabilityType.APP, appPath);

//        (Device)
//        String ipaPath = "/Users/kentgreen/Projects/AppCenter-Test-Samples/Appium/iOS/UITestDemo.ipa";
//        capabilities.setCapability(MobileCapabilityType.APP, ipaPath);


        capabilities.setCapability("bundleId", "com.node.io.apps.myexpo");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "9A9AY1CDPS");
        capabilities.setCapability(MobileCapabilityType.APP, "myexpo.apk");
        capabilities.setCapability(MobileCapabilityType.NO_RESET, true);
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
//        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");

        //4

        URL url = new URL("http://localhost:4723/wd/hub");
        return Factory.createAndroidDriver(url, capabilities);
        /*
        appcenter test run appium --app "dozken/gsg" --devices 6678f550 --app-path myexpo.apk --test-series "master" --locale "en_US" --build-dir target/upload
         */
    }


    @BeforeClass
    public static void beforeClass() throws Exception {
        driver.label("App Launched");
        driver = startApp();
    }

    @AfterClass
    public static void afterClass() throws Exception {
        driver.label("Stopping App");
        driver.quit();
    }

    @Before
    public void beforeEach() {
        driver.label("App reset");
        driver.resetApp();
        driver.startRecordingScreen();
    }

    @After
    public void afterEach() throws Exception {
        String base64String = driver.stopRecordingScreen();
        byte[] data = Base64.decodeBase64(base64String);
        String destinationPath = "artefacts/filename.mp4";
        Path path = Paths.get(destinationPath);
        Files.write(path, data);
    }

    @Test
    public void canStartAppInTest() throws MalformedURLException, InterruptedException {
        driver.label("App Launched");

        WebElement testText = driver.findElementByAccessibilityId("testText");
        assertEquals(testText.getText(), "testText");
        assertTrue(testText.isDisplayed());
    }


    @Test
    public void canStartAppInTest2() throws MalformedURLException, InterruptedException {

        WebElement testText = driver.findElementByAccessibilityId("testText");
        assertEquals(testText.getText(), "testText");
        assertTrue(testText.isDisplayed());
    }
}
