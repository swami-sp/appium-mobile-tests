package com.aspiration.appium.pages;

import com.aspiration.appium.core.BasePage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class MenuBarPage extends BasePage {
    /**
     * The below Constructor method will build the mobile driver
     *
     * @param deviceType
     */
    public MenuBarPage(String deviceType) {
        super(deviceType);
        this.deviceType = deviceType;
    }

    /**
     * Return the Home button
     * @return MobileElement
     * @author spadmanabhan
     */
    public MobileElement getBtnHome(){
        return btnHome;
    }

    /**
     * to click on settins button
     * @return Settins page
     * @author spadmanabhan
     */
    public SettingsPage clickSettings(){
        commonUtils.clickElement(btnSettings);
        return new SettingsPage(deviceType);
    }

    //Page elements
    @AndroidFindBy(accessibility = "Home")
    @iOSXCUITFindBy(accessibility = "Home")
    private MobileElement btnHome;

    @AndroidFindBy(accessibility = "Settings")
    @iOSXCUITFindBy(accessibility = "Settings")
    private MobileElement btnSettings;

}
