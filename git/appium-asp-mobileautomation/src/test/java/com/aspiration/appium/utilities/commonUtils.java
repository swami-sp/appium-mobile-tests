package com.aspiration.appium.utilities;

import com.aspiration.appium.core.DeviceDriver;
import com.aspiration.appium.core.Wait;
import com.aspiration.appium.pages.AppCommonPage;
import com.aventstack.extentreports.Status;
import io.appium.java_client.MobileElement;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.touch.offset.PointOption;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import static io.appium.java_client.touch.WaitOptions.waitOptions;
import static io.appium.java_client.touch.offset.PointOption.point;
import static java.time.Duration.ofMillis;

public class commonUtils {
    private static final Logger LOG = Logger.getLogger(commonUtils.class);
    protected Wait wait = new Wait();

    /**
     * This method is used to enter text on the element
     *
     * @param element
     * @param inputText
     * @param deviceType
     * @author spadmanabhan
     */
    public void enterInput(MobileElement element, String inputText, String deviceType) {
        try {
            wait.waitForElementToBeVisibleFastAndFluent(element.getWrappedDriver(), Integer.parseInt(new ReadConfig().getProperties("waitconfig.properties", "mediumWaitTime")), element);
            element.clear();
            LOG.info("Enter text  -> " + inputText);
            if (element.getWrappedDriver() instanceof IOSDriver) {
                element.sendKeys(inputText);
                new AppCommonPage(deviceType).clickDoneToCloseKeyBoard();
            } else {
                element.click();
                Actions action = new Actions(element.getWrappedDriver());
                action.sendKeys(inputText).perform();// element.setValue(inputText); //This also did not work
            }

        } catch (Exception e) {

            throw e;
        }
    }

    /**
     * This method is used to click on the element
     *
     * @author spadmanabhan
     */
    public void clickElement(MobileElement element) {
        try {
            wait.waitForElementToBeClickableFastAndFluent(Integer.parseInt(new ReadConfig().getProperties("waitconfig.properties", "mediumWaitTime")), element);
            element.click();
            LOG.info("Clicked on Element  -> " + element);
        } catch (Exception e) {
            tapElementUsingCoordinates(element.getWrappedDriver(), element);
        }
    }

    /**
     * This method is used to tap element using coordinates
     *
     * @author spadmanabhan
     * @param driver
     * @param element
     *
     *
     */

    public void tapElementUsingCoordinates(WebDriver driver, MobileElement element) {
        try {
            int leftX = element.getLocation().getX();
            int rightX = leftX + element.getSize().getWidth();
            int middleX = (rightX + leftX) / 2;
            int upperY = element.getLocation().getY();
            int lowerY = upperY + element.getSize().getHeight();
            int middleY = (upperY + lowerY) / 2;
            TouchAction action = new TouchAction((PerformsTouchActions) driver);
            action.tap(new PointOption().point(middleX, middleY)).release().perform();
            LOG.info("tapElementUsingCoordinates equals true  -> "+element);
        } catch (Exception e) {
            LOG.info("tapElementUsingCoordinates equals false  -> "+element);
            throw e;
        }
    }



    /**
     * To wait for mutliple elements and check for element Displayed
     *
     * @param elem
     * @return String
     * @author swamipadmanabhan
     */
    public String waitForMultipleElementsDisplayed(MobileElement... elem){
        for (int index = 0; index < 30; index++) {
            LOG.info("index value: " + index);
            for (int elementIndex = 0; elementIndex < elem.length; elementIndex++) {
                if (isElementDisplayedWithOutWait(elem[elementIndex])) {
                    LOG.info("waitForMultiScreensToBeDisplayed:element-" + elementIndex + " present: True");
                    return String.valueOf(elementIndex + 1);
                }
            }
        }
        return "none";
    }


    /**
     * To immediately return the isDisplayedValue
     *
     * @param element
     * @return boolean
     * @author spadmanabhan
     */
    public boolean isElementDisplayedWithOutWait(MobileElement element) {
        try {
            element.isDisplayed();
            LOG.info("Yes -> " + element);
            return true;
        } catch (Exception e) {
            LOG.info("No -> " + element);
            return false;
        }
    }

    /**
     * To swipe using touch points, to using element text
     * @param driver
     * @param elemText
     *
     * @author swami.padmanabhan
     */
    public void scrollAnyOSToElementText(WebDriver driver, String elemText) {
        int i = 0;
        if (driver instanceof AndroidDriver) {
            while (isElementNotDisplayedWithOutWait(driver, "//android.widget.TextView[contains(normalize-space(@text),\"" + elemText + "\")]") && i < 10) {
                i++;
                SwipeDownUsingTouchPoints(driver);
            }
        } else {
            while (!isElementVisibleWithXpathWithoutWait(driver, "//XCUIElementTypeStaticText[contains(normalize-space(@label),\"" + elemText + "\")]") && i < 10) {
                i++;
                SwipeDownUsingTouchPoints(driver);
            }
        }
    }

    /**
     * To immediately return the visible property for an xpath element
     *
     * @param driver
     * @param xPathVal
     * @return boolean
     * @author swamipadmanabhan
     */
    public boolean isElementVisibleWithXpathWithoutWait(WebDriver driver, String xPathVal) {
        try {
            WebElement element=driver.findElement(By.xpath(xPathVal));
            boolean rc =element.getAttribute("visible").equals("true");
            LOG.info("Get visible equals true: ->" + rc + ":" + xPathVal);
            return rc;
        } catch (Exception e) {
            LOG.info("Get visible equals true: -> false:" + xPathVal);
            return false;
        }
    }


        /**
         * To swipe using touch points, to avoid pull to refresh
         * @param driver
         *
         * @author swami.padmanabhan
         */
    public void SwipeDownUsingTouchPoints(WebDriver driver) {
        double startPercentage = 0.6;
        double endPercentage = 0.3;
        double anchorPercentage = 0.2;
        Dimension size = driver.manage().window().getSize();
        LOG.info("size value ht:'" + size.height + "' size value wd:'" + size.width +"'");
        int anchor = (int) (size.width * anchorPercentage);
        int startPoint = (int) (size.height * startPercentage);
        int endPoint = (int) (size.height * endPercentage);
        LOG.info("anchor:'" + anchor +"', start:'" + startPoint + "', end:'" + endPoint+"'");

        new TouchAction((PerformsTouchActions) driver)
                .press(point(anchor, startPoint))
                .waitAction(waitOptions(ofMillis(1000)))
                .moveTo(point(anchor, endPoint))
                .release().perform();
        LOG.info("Scrolling DOWN using the screen size - touch points");
    }

    /**
     * To compare ui values with given value with equals
     *
     * @param mElement
     * @param elementAttribute
     * @param valueToCheck
     * @return boolean
     * @author swami.padmanabhan
     */
    public boolean uiValueEqualsCheck(MobileElement mElement, String elementAttribute, String valueToCheck) {
        wait.waitForElementToBeVisibleFastAndFluent(DeviceDriver.get(), Integer.parseInt(new ReadConfig().getProperties("waitconfig.properties", "mediumWaitTime")), mElement);
        if (mElement.getAttribute(elementAttribute).toLowerCase().equals(valueToCheck.toLowerCase())) {
            LOG.info(valueToCheck + ": matches with elem : " + mElement.getAttribute(elementAttribute));
            return true;
        } else {
            LOG.info(valueToCheck + ": NOT Matching with elem : " + mElement.getAttribute(elementAttribute));
            return false;
        }

    }

    /**
     * To compare ui values with given value with contains
     *
     * @param mElement
     * @param elementAttribute
     * @param valueToCheck
     * @return boolean
     * @author swami.padmanabhan
     */
    public boolean uiValueContainsCheck(MobileElement mElement, String elementAttribute, String valueToCheck) {
        wait.waitForElementToBeVisibleFastAndFluent(DeviceDriver.get(), Integer.parseInt(new ReadConfig().getProperties("waitconfig.properties", "mediumWaitTime")), mElement);
        if (mElement.getAttribute(elementAttribute).toLowerCase().contains(valueToCheck.toLowerCase())) {
            LOG.info(valueToCheck + ": contains -> elem : " + mElement.getAttribute(elementAttribute));
            return true;
        } else {
            LOG.info(valueToCheck + ": NOT contains -> elem : " + mElement.getAttribute(elementAttribute));
            return false;
        }

    }

    /**
     * To compare ui values with given value with contains
     * @param mElement
     * @param elementAttribute
     * @param  valueToCheck
     * @return boolean
     * @author swami.padmanabhan
     */
    public boolean uiValueContainsCheckWithoutWait(MobileElement mElement, String elementAttribute, String valueToCheck){
        if(mElement.getAttribute(elementAttribute).toLowerCase().contains(valueToCheck.toLowerCase())){
            LOG.info(valueToCheck + ": contains -> elem : " + mElement.getAttribute(elementAttribute));
            return true;
        }else {
            LOG.info(valueToCheck + ": NOT contains -> elem : " + mElement.getAttribute(elementAttribute));
            return false;
        }

    }


}
