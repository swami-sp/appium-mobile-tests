package com.aspiration.appium.utilities;

import org.apache.commons.io.IOUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.apache.log4j.Logger;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.Properties;

public class ReadConfig {

    private final static Logger LOG = Logger.getLogger(ReadConfig.class);


    /**
     * Gets a property file using the given filename/path
     *
     * @param fileName
     * @return Properties
     * @author spadmanabhan
     */
    public Properties getProperties(String fileName) {
        //InputStream inputStream = this.getClass().getResourceAsStream(fileName);
        final String filePath = "/" + fileName;
        InputStream inputStream = this.getClass().getResourceAsStream(filePath);
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return properties;
    }

    /**
     * Loads a property file and returns a property from it
     *
     * @param fileName
     * @param propertyName
     * @return String
     * @author spadmanabhan
     */
    public String getProperties(String fileName, String propertyName) {
        Properties properties = getProperties(fileName);
        return properties.getProperty(propertyName);

    }

    /**
     * Takes an input stream and attempts to load it as a Properties file
     *
     * @param inputStream
     * @return Optional<Properties>
     * @author spadmanabhan
     */
    public Optional<Properties> loadPropertiesFromInputStream(InputStream inputStream) {
        try {
            Properties properties = new Properties();
            properties.load(inputStream);
            return Optional.of(properties);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    /**
     * Gets an input stream from the given full qualified path
     *
     * @param fullFilePath
     * @return InputStream
     * @throws IOException
     * @author spadmanabhan
     */
    public InputStream getInputStreamOfFullFilePath(String fullFilePath) throws IOException {
        File file = new File(fullFilePath);
        return new FileInputStream(file);
    }

    /**
     * Gets an InputStream of the given file name
     *
     * @param fileName
     * @return InputStream
     * @author spadmanabhan
     */
    public InputStream getInputStreamOfFile(String fileName) {
        Optional<InputStream> inputStream = getInputStreamFromUserHome(fileName);
        return inputStream.orElseGet(() -> getInputStreamFromResources(fileName));
    }

    /**
     * Gets an InputStream from a file located on the project resources
     *
     * @param fileName
     * @return InputStream
     * @author spadmanabhan
     */
    private InputStream getInputStreamFromResources(String fileName) {
        final String filePath = "/" + fileName;
        InputStream inputStream = this.getClass().getResourceAsStream(filePath);
        if (inputStream != null) {
            LOG.info(String.format("Loaded file InputStream [%s] from resources folder", fileName));
            return inputStream;
        } else {
            try {
                throw new Exception(String.format("File [%s] not found on resources folder", filePath));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Gets an InputStream of a file located under the user home
     *
     * @param fileName
     * @return Optiona<InputStream>
     * @author spadmanabhan
     */
    public Optional<InputStream> getInputStreamFromUserHome(String fileName) {
        try {
            String path = System.getProperty("user.home") + File.separator + "mobileAutomation" + File.separator;
            Optional<InputStream> inputStreamOfPath = Optional.of(getInputStreamOfFullFilePath(path + fileName));
            return inputStreamOfPath;
        } catch (IOException e) {
            return Optional.empty();
        }
    }
}
