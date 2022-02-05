package com.aspiration.appium.tests;

import com.aspiration.appium.core.BaseTest;
import com.aspiration.appium.core.DeviceProvider;
import com.aspiration.appium.pages.HomePage;
import com.aspiration.appium.pages.LoginPage;
import com.aspiration.appium.pages.MenuBarPage;
import com.aspiration.appium.pages.SettingsPage;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class LoginTest extends BaseTest {
    @Test(dataProvider = "devices", dataProviderClass = DeviceProvider.class)
    public void testUserLoginAndLogout(String deviceType){
        LoginPage login = new LoginPage(deviceType);
        login.clickLoginButton();
        login.enterLoginCredentials(testData.getProperty("userWithAspZero"),testData.getProperty("loginPassword"));
        HomePage home = new HomePage(deviceType);
        home.waitForHomePage();
        assertTrue(home.verifyHomePageWelcomeBanner(),"Home page not displayed");
        SettingsPage settings = new MenuBarPage(deviceType).clickSettings();
        settings.clickLogout();
    }

}
