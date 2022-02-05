package com.aspiration.appium.core;

import io.appium.java_client.MobileElement;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class Wait {

    public int timeout = 0;
    private static final Logger LOG=Logger.getLogger(Wait.class);

    /**
     * This wait is used to Check the element visibility using Fluent wait, polling every 10ms
     */
    public void waitForElementToBeInvisibleFastAndFluent(WebDriver driver, int timeout, WebElement element) {
        FluentWait<WebDriver> fwait =
                new FluentWait<WebDriver>(driver).withTimeout(Duration.ofMillis(timeout)).
                        pollingEvery(Duration.ofMillis(10)).ignoring(NoSuchElementException.class, StaleElementReferenceException.class);
        fwait.until(ExpectedConditions.invisibilityOf(element));
    }

    /**
     * This wait is used to Check the element visibility using Fluent wait, polling every 10ms
     */
    public void waitForElementToBeVisibleFastAndFluent(WebDriver driver, int timeout, WebElement element) {
        LOG.info("Fluent wait" + element);
        FluentWait<WebDriver> fwait =
                new FluentWait<WebDriver>(driver).withTimeout(Duration.ofMillis(timeout)).
                        pollingEvery(Duration.ofMillis(10)).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).ignoring(ElementNotVisibleException.class);;
        fwait.until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * This Wait is used to wait until the element text visible
     * @author sghosh
     */

    public void waitForElementTextToBeVisible(WebDriver driver, int time, WebElement element, String text) {
        int timeout = time / 1000;
        new WebDriverWait(driver, timeout).until(ExpectedConditions.textToBePresentInElementValue(element, text));
    }

    /**
     * This wait is used to Check the element clickable using Fluent wait, polling every 10ms
     */
    public void waitForElementToBeClickableFastAndFluent(int timeout, MobileElement element) {
        LOG.info("Fluent wait" + element);
        FluentWait<WebDriver> fwait =
                new FluentWait<>(element.getWrappedDriver()).withTimeout(Duration.ofMillis(timeout)).
                        pollingEvery(Duration.ofMillis(10)).ignoring(NoSuchElementException.class, StaleElementReferenceException.class).ignoring(ElementNotVisibleException.class);
        fwait.until(ExpectedConditions.elementToBeClickable(element));
    }

}
