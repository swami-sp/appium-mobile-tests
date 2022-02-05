package com.aspiration.appium.core;

import com.aspiration.appium.utilities.ReadConfig;
import org.apache.log4j.Logger;
import org.testng.annotations.DataProvider;

public class DeviceProvider {

    private static final Logger LOG = Logger.getLogger(DeviceProvider.class);
    private static String locationToSaveFile;

    /**
     * The below method returns the device type when ran from local or AWS according
     * to the flag set in the Constants
     *
     * @return Object[][]
     */
    @DataProvider(name = "devices")
    public static Object[][] testRun() {
        String devices;
        ReadConfig config = new ReadConfig();
        try {
            devices = config.getProperties("ios.properties", "device");
        } catch (Exception e) {
            devices = "android";
        }

        if(devices.contains("remote"))
            locationToSaveFile= System.getenv("DEVICEFARM_LOG_DIR");
        else
            locationToSaveFile= System.getProperty("user.dir");
        LOG.info("The test device is: " + devices);
        return  new Object[][]{{devices}};
    }

    /**
     * This method is used to return location to save files based on platform
     *
     * @return String
     */
    public static String getLocationToSaveFile() {
        return locationToSaveFile;
    }
}
