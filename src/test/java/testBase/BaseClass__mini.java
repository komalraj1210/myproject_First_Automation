package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseClass__mini {

    // Change to ThreadLocal for WebDriver for parallel execution safety
    private static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();

    // Change logger to static final for proper and early initialization
    // This resolves the "this.logger is null" error if accessed before setup()
    public static final Logger logger = LogManager.getLogger(BaseClass__mini.class);

    public Properties proper; // No change needed here

    @BeforeClass(groups = {"Regression", "Master", "smoke"})
    @Parameters({"browser", "os"})
    public void setup(String br, String os) throws IOException {
        // No need to re-initialize logger here as it's static final now
        logger.info("Initializing setup for browser: " + br + " on OS: " + os);

        proper = new Properties();
        FileReader file = new FileReader("./src//test//resources//config.properties");
        proper.load(file);

        switch (br.toLowerCase()) {
            case "chrome":
                tlDriver.set(new ChromeDriver()); // Set driver to ThreadLocal
                break;
            case "edge":
                tlDriver.set(new EdgeDriver()); // Set driver to ThreadLocal
                break;
            case "firefox":
                tlDriver.set(new FirefoxDriver()); // Set driver to ThreadLocal
                break;
            default:
                logger.error("Invalid Browser specified: " + br);
                System.out.println("Invalid Browser");
                return;
        }

        getDriver().manage().deleteAllCookies(); // Use getDriver() to access the driver
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        getDriver().get(proper.getProperty("url"));
        getDriver().manage().window().maximize();

        logger.info(br + " browser initialized successfully and navigated to " + proper.getProperty("url"));
    }

    // Static getter method for WebDriver
    public static synchronized WebDriver getDriver() {
        return tlDriver.get(); // Get driver from ThreadLocal
    }

    @AfterClass(groups = {"Regression", "Master", "smoke"})
    public void teardown() {
        if (getDriver() != null) {
            logger.info("Quitting browser.");
            getDriver().quit(); // Use getDriver() to quit
            tlDriver.remove(); // Remove driver from ThreadLocal after use
            logger.info("Browser quit and ThreadLocal removed.");
        } else {
            logger.warn("Driver was already null or not initialized during teardown.");
        }
    }

    // Common methods (no changes needed here for the errors)
    @SuppressWarnings("deprecation")
    public String randomString() {
        String random_string = RandomStringUtils.randomAlphabetic(7);
        return random_string;
    }

    public String randomNumber() {
        String random_number = RandomStringUtils.randomNumeric(10);
        return random_number;
    }

    public String random_AlphaNumeric() {
        String random_string = RandomStringUtils.randomAlphabetic(5);
        String random_number = RandomStringUtils.randomNumeric(5);
        String random_AlphaNumeric = random_string + "@" + random_number;
        return random_AlphaNumeric;
    }

    // Modified captureScreen method
    // Make it static so ExtentReportManager can call it directly without creating a new BaseClass instance
    public static String captureScreen(String tname) throws IOException {
        logger.info("Attempting to capture screenshot for: " + tname);
        String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());

        // Get the driver for the current thread
        WebDriver currentDriver = getDriver();
        if (currentDriver == null) {
            logger.error("WebDriver instance is null for screenshot. Cannot take screenshot for: " + tname);
            return null; // Return null if driver is not available
        }

        TakesScreenshot takesScreenshot = (TakesScreenshot) currentDriver; // Use currentDriver
        File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);

        String targetFilePath = System.getProperty("user.dir") + "\\screenshots\\" + tname + "_" + timeStamp + ".png";
        File targetFile = new File(targetFilePath);

        try {
            // Using Files.copy for more robust file operations than renameTo
            java.nio.file.Files.copy(sourceFile.toPath(), targetFile.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            logger.info("Screenshot saved to: " + targetFilePath);
        } catch (IOException e) {
            logger.error("Failed to save screenshot to: " + targetFilePath, e);
            throw e; // Rethrow the exception
        }
        return targetFilePath;
    }
}