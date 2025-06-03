package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger; // ✔️ correct Logger interface
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;


public class BaseClass {
	
	public static WebDriver driver;
	public Logger logger;
	public Properties proper;
	
	
	@BeforeClass(groups = {"regression","master","smoke","sanity"})
	@Parameters({"browser","os"})
	public  void setup(String browser,String os) throws IOException
	{
		logger=LogManager.getLogger(this.getClass()); 
		// firstly lets write the logger commands
		
		//lets write code for to load the properties file
		proper = new Properties();
		FileReader file = new FileReader("./src//test//resources//config.properties");
		proper.load(file);
		
		// Before Execution in terminal
		/*
		 * cd C:\Users\komalr\eclipse-workspace 
		 * java -jar selenium-server-4.33.0.jar standalone --selenium-manager true
		 */		 
		
		/*
		 * driver is still a plain static WebDriver: Even though you're in "remote"
		 * mode, public static WebDriver driver; is problematic for parallel="tests".
		 * When Test, Test2, and Test3 run concurrently, all three threads try to set
		 * this same static driver variable. The last thread to set it might overwrite
		 * what a previous thread set, leading to race conditions and
		 * NullPointerExceptions (or other unpredictable behavior) for the other
		 * threads. This is the primary reason your cross-browser tests aren't working
		 * reliably.
		 */
		
		
		// if the execution is remote
		if(proper.getProperty("Execution_env").equalsIgnoreCase("remote"))
		{
			DesiredCapabilities cap= new DesiredCapabilities();
			switch(os.toLowerCase())
			{
			case "windows":cap.setPlatform(Platform.WINDOWS);break;
			case "LINUX":cap.setPlatform(Platform.LINUX);break;
			default: System.out.println("Invalid OS");return;
			}
			switch(browser.toLowerCase())
			{
			case "chrome":cap.setBrowserName("chrome"); break;
			case "edge": cap.setBrowserName("MicrosoftEdge");break;
			case "firefox":cap.setBrowserName("firefox");break;
			default: System.out.println("Invalid Browser");return;

			}
	        driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), cap);
		}
		
		// if the execution is local
		if(proper.getProperty("Execution_env").equalsIgnoreCase("local"))
		{		switch(browser.toLowerCase())
			{
			case "chrome":driver= new ChromeDriver();break;
			case "edge":driver= new EdgeDriver();break;
			case "firefox":driver= new FirefoxDriver();break;
			default: System.out.println("Invalid Browser"); return;
			}
		}
		

        logger.info("Browser launched successfully");
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get(proper.getProperty("url"));
		driver.manage().window().maximize();
        logger.info("Navigated to URL: " + proper.getProperty("url"));

	}
	@AfterClass(groups = {"regression","master","smoke","sanity"})
	public void teardown()
	{
		driver.quit();
        logger.info("Browser closed successfully");

	}
	// common methods
	@SuppressWarnings("deprecation")
	public String randomString()
	{
		String random_string=RandomStringUtils.randomAlphabetic(7);
		return random_string ;
	}
	public String randomNumber()
	{
		String random_number=RandomStringUtils.randomNumeric(10);
		return random_number;
	}
	public String random_AlphaNumeric()
	{
		String random_string=RandomStringUtils.randomAlphabetic(5);
		String random_number=RandomStringUtils.randomNumeric(5);
		String random_AlphaNumeric=random_string + "@" + random_number;
		return random_AlphaNumeric;
	}
	
	// to captute the path of the failed page, so that we can capture the screenshot
	public String captureScreen(String tname) throws IOException {
		
		  String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		  
		  TakesScreenshot takesScreenshot = (TakesScreenshot) driver; 
		  File sourceFile =takesScreenshot.getScreenshotAs(OutputType.FILE);
		  
		  String targetFilePath = System.getProperty("user.dir") +
		  "\\screenshots\\" + tname + "_" + timeStamp + ".png"; File targetFile = new
		  File(targetFilePath);
		  
		  sourceFile.renameTo(targetFile);
		  
		  return targetFilePath;	
	}


}
