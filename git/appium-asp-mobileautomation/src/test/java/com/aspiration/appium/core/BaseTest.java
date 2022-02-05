package com.aspiration.appium.core;

import com.aspiration.appium.utilities.ReadConfig;
import com.aspiration.appium.utilities.apiUtils;
import com.aspiration.appium.utilities.commonUtils;
import com.aspiration.appium.utilities.dBUtils;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.util.Properties;

public class BaseTest {

    protected com.aspiration.appium.utilities.commonUtils commonUtils = new commonUtils();
    protected com.aspiration.appium.utilities.apiUtils apiUtils = new apiUtils();
    protected com.aspiration.appium.utilities.dBUtils dBUtils = new dBUtils();
    protected Properties testData;
    protected String envt = System.getProperty("ExecEnvt"); // env variable to be used later

    //Setup required across the tests
    @BeforeMethod(alwaysRun = true)
    public void setUp() {
         testData = new ReadConfig().getProperties("test.properties");
    }

    //General teardown method across all  the tests .
    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        DeviceDriver.quitDrivers();
    }

    //Cleanup method.
    @AfterClass
    public void cleanUp() {

    }

}
