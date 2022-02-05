package com.aspiration.appium.pages;

import com.aspiration.appium.core.BasePage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidBy;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class HomePage extends BasePage {
    /**
     * The below Constructor method will build the mobile driver
     *
     * @param deviceType
     */
    public HomePage(String deviceType) {
        super(deviceType);
        this.deviceType = deviceType;
    }

    public void waitForHomePage(){
        commonUtils.waitForMultipleElementsDisplayed(lblWelcomeBack);
    }

    public boolean verifyHomePageWelcomeBanner(){
        return commonUtils.isElementDisplayedWithOutWait(lblWelcomeBack);
    }

    @AndroidFindBy(id = "tvWelcomeBackAccountSnapshot")
    private MobileElement lblWelcomeBack;
}
