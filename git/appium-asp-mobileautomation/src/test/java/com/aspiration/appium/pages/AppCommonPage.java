package com.aspiration.appium.pages;

import com.aspiration.appium.core.BasePage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.apache.log4j.Logger;

public class AppCommonPage extends BasePage {

    private static final Logger LOG=Logger.getLogger(AppCommonPage.class);

    public  AppCommonPage(String deviceType){
        super(deviceType);
    }
    /**
     * To click on Done in iOS to close the keyboard
     * @author swamipadmanabhan
     */
    public void clickDoneToCloseKeyBoard(){
        if(commonUtils.isElementDisplayedWithOutWait(btnDone)) {
            LOG.info("Click on Done");
            commonUtils.clickElement(btnDone);
        }
    }

    @iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeButton[`label == \"Done\"`]")
    private MobileElement btnDone;
}
