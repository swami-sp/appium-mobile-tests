package com.aspiration.appium.core;

import com.aspiration.appium.utilities.ReadConfig;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

public class DeviceDriver {
    static AppiumDriver<MobileElement> mobileDriver;

    public WebDriver initializeDriver(String device){
        if (mobileDriver!=null) {
            return mobileDriver;
        }
        mobileDriver = createDriverForDevice(device);
        return mobileDriver;
    }

    private AppiumDriver<MobileElement> createDriverForDevice(String deviceType) {
        try {
            switch (deviceType) {
                case "cloud_ios":
                    return createCloudIOSDriver(new DesiredCapabilities());
                case "android":
                    return createAndroidDriver();
                case "ios":
                    return createIOSDriver();
                default:
                    return createCloudAndroidDriver(new DesiredCapabilities());
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    private IOSDriver<MobileElement> createIOSDriver() throws MalformedURLException {
        DesiredCapabilities capabilitiesIphone = createCapabilitiesFor("ios");
        return createCloudIOSDriver(capabilitiesIphone);
    }

    private AndroidDriver<MobileElement> createCloudAndroidDriver(DesiredCapabilities capabilities) throws MalformedURLException {
        URL appiumUrl = new URL("http://127.0.0.1:4723/wd/hub");
        return new AndroidDriver<>(appiumUrl, capabilities);

    }

    private AndroidDriver<MobileElement> createAndroidDriver() throws MalformedURLException {
        DesiredCapabilities capabilitiesAndroid = createCapabilitiesFor("android");
        return createCloudAndroidDriver(capabilitiesAndroid);
    }

    private IOSDriver<MobileElement> createCloudIOSDriver(DesiredCapabilities capabilities) throws MalformedURLException {
        URL appiumUrl = new URL("http://127.0.0.1:4723/wd/hub");
        return new IOSDriver<>(appiumUrl, capabilities);
    }

    private DesiredCapabilities createCapabilitiesFor(String deviceType) {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        String fileName = deviceType.equalsIgnoreCase("android")?"androidCap.properties":"iosCap.properties";
        Properties properties = new ReadConfig().getProperties(fileName);
        properties.forEach((key, value) -> desiredCapabilities.setCapability(key.toString(), value));
        return desiredCapabilities;
    }

    public static void quitDrivers() {
        mobileDriver.quit();

    }
}
