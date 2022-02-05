package com.aspiration.appium.pages;

import com.aspiration.appium.core.BasePage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.apache.log4j.Logger;

public class LoginPage extends BasePage {

    private static final Logger LOG=Logger.getLogger(LoginPage.class);
    /**
     * The below Constructor method will build the mobile driver
     *
     * @param deviceType
     */
    public LoginPage(String deviceType) {
        super(deviceType);
        this.deviceType = deviceType;
    }

    /**
     * To enter login info and click on biometrics dialog
     * @param emailAddress
     * @param pwd
     * @author spadmanabhan
     */
    public void enterLoginCredentials(String emailAddress, String pwd){
        commonUtils.waitForMultipleElementsDisplayed(txtEmail);
        commonUtils.enterInput(txtEmail, emailAddress, deviceType);
        commonUtils.enterInput(txtPassword, pwd, deviceType);
        commonUtils.clickElement(btnLogin);
        if(commonUtils.waitForMultipleElementsDisplayed(new MenuBarPage(deviceType).getBtnHome(), cancelBiometricsDialog).equals("2"))
            commonUtils.clickElement(cancelBiometricsDialog);

    }

    /**
     * click on LOG IN on the welcome screen
     * @author spadmanbhan
     */
    public void clickLoginButton(){
        commonUtils.clickElement(btnGotoLogin);
    }

   //Page objects
    @AndroidFindBy(xpath = "//android.widget.Button[@text=\"LOG IN\"]")
    private MobileElement btnGotoLogin;

    @AndroidFindBy(id = "email")
    private MobileElement txtEmail;

    @AndroidFindBy(id = "password")
    private MobileElement txtPassword;

    @AndroidFindBy(id = "imgBtnLogin")
    private MobileElement btnLogin;

    @AndroidFindBy(id="actionNegative")
    private MobileElement cancelBiometricsDialog;

}
