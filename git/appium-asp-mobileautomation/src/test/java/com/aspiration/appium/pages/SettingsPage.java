package com.aspiration.appium.pages;

import com.aspiration.appium.core.BasePage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebDriver;

public class SettingsPage extends BasePage {
    /**
     * The below Constructor method will build the mobile driver
     *
     * @param deviceType
     */
    public SettingsPage(String deviceType) {
        super(deviceType);
    }

    public void clickLogout(){
        if(!commonUtils.isElementDisplayedWithOutWait(btnLogOut))
            commonUtils.SwipeDownUsingTouchPoints(webDriver);
        commonUtils.clickElement(btnLogOut);
    }

    @AndroidFindBy(id="btnSettingsLogout")
    private MobileElement btnLogOut;
}
