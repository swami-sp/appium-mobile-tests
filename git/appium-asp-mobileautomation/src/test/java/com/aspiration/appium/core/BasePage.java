package com.aspiration.appium.core;

import com.aspiration.appium.utilities.ReadConfig;
import com.aspiration.appium.utilities.apiUtils;
import com.aspiration.appium.utilities.commonUtils;
import com.aspiration.appium.utilities.dBUtils;
import io.appium.java_client.Setting;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Properties;

public class BasePage {
    protected Object driver;
    protected static Properties testData = new ReadConfig().getProperties("test.properties");
    protected static Properties waitData = new ReadConfig().getProperties("waitconfig.properties");
    protected com.aspiration.appium.utilities.commonUtils commonUtils = new commonUtils();
    protected com.aspiration.appium.utilities.apiUtils apiUtils = new apiUtils();
    protected com.aspiration.appium.utilities.dBUtils dBUtils = new dBUtils();
    protected WebDriver webDriver;
    protected String deviceType;
    protected WebDriverWait driverWait = null;

    protected String envt = System.getProperty("ExecEnvt"); //to be used later if we want to use Beta or prod version
    public static final int configLowWait = Integer.parseInt((String) waitData.get("lowWaitTime"));//10s
    public static final int configMediumWait = Integer.parseInt((String) waitData.get("mediumWaitTime"));//30s
    public static final int configHighWait = Integer.parseInt((String) waitData.get("highWaitTime"));//60s
    public static final int configImplicitWait =Integer.parseInt((String) waitData.get("implicitwaitTime"));//2

    /**
     * The below Constructor method will build the mobile driver
     *
     */
    public BasePage(String device) {
//        PropertyConfigurator.configure(getClass().getResource("src/test/resources/delere_log4j.xml"));
        this.deviceType = device;
        webDriver = new DeviceDriver().initializeDriver(device);
        if(webDriver instanceof AndroidDriver)
            ((AndroidDriver)webDriver).configuratorSetWaitForIdleTimeout(Duration.ofMillis(Long.parseLong((String) waitData.get("idleWaitTime"))));
        else
            ((IOSDriver)webDriver).setSetting(Setting.FIX_IMAGE_TEMPLATE_SCALE, true);
//        if(utility.isAlertPresent())
//            webDriver.switchTo().alert().accept();
        initializePageFactory();
    }

    /**
     * The below method will initialize the elements with Use of PageFactory
     */
    private void initializePageFactory() {
        AppiumFieldDecorator appiumDecorator = new AppiumFieldDecorator(webDriver, Duration.ofMillis(250));
        PageFactory.initElements(appiumDecorator, this);
    }
}
