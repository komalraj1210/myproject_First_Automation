package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
// import java.net.URL; // This import is commented out in the original image.
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseClass;
import testBase.BaseClass__mini; // Assuming BaseClass is in testBase package

public class ExtentReportManager implements ITestListener {

    public ExtentSparkReporter sparkReporter;
    public ExtentReports extent;
    public ExtentTest test;

    String repName; // Variable to store the report name

    @Override
	public void onStart(
			ITestContext testContext) {
        // Generate a timestamp for the report name
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        repName = "Test-Report-" + timeStamp + ".html";

        // Initialize ExtentSparkReporter with the report path
        sparkReporter = new ExtentSparkReporter(".\\reports\\" + repName); // Specify location of the report

        // Configure the Spark Reporter
        sparkReporter.config().setDocumentTitle("opencart Automation Testing"); // Title of report
        sparkReporter.config().setReportName("opencart Functional Testing"); // Name of report
        sparkReporter.config().setTheme(Theme.DARK); // Set the theme of the report

        // Initialize ExtentReports
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter); // Attach the Spark Reporter to ExtentReports

        // Set system information for the report
        extent.setSystemInfo("Application", "opencart");
        extent.setSystemInfo("Module", "Admin");
        extent.setSystemInfo("Sub Module", "Customers");
        extent.setSystemInfo("User Name", System.getProperty("user.name")); // Get the current user's name
        extent.setSystemInfo("Environment", "QA");

        // Get and set OS information
        String os = testContext.getCurrentXmlTest().getParameter("os");
        extent.setSystemInfo("Operating System", os);

        // Get and set Browser information
        String browser = testContext.getCurrentXmlTest().getParameter("browser");
        extent.setSystemInfo("Browser", browser);

        // Get and set included groups
        List<String> includedGroups = testContext.getCurrentXmlTest().getIncludedGroups();
        if (!includedGroups.isEmpty()) {
            extent.setSystemInfo("Groups", includedGroups.toString());
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        // Create a new test entry in the report for a successful test
        test = extent.createTest(result.getTestClass().getName());
        test.assignCategory(result.getMethod().getGroups()); // To display groups in report
        test.log(Status.PASS, result.getName() + " got successfully executed"); // Log success status
    }

    @Override
    public void onTestFailure(ITestResult result) {
        // Create a new test entry in the report for a failed test
        test = extent.createTest(result.getTestClass().getName());
        test.assignCategory(result.getMethod().getGroups());
        test.log(Status.FAIL, result.getName() + " got failed"); // Log failure status
        test.log(Status.INFO, result.getThrowable().getMessage()); // Log the exception message

        try {
            // Capture a screenshot on failure
            String imgPath = new BaseClass().captureScreen(result.getName());
            test.addScreenCaptureFromPath(imgPath); // Add the screenshot to the report
        } catch (IOException e) {
            e.printStackTrace(); // Print stack trace if screenshot capture fails
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        // Create a new test entry in the report for a skipped test
        test = extent.createTest(result.getTestClass().getName());
        test.assignCategory(result.getMethod().getGroups());
        test.log(Status.SKIP, result.getName() + " got skipped"); // Log skipped status
        test.log(Status.INFO, result.getThrowable().getMessage()); // Log the skip reason
    }

    @Override
    public void onFinish(ITestContext testContext) {
        extent.flush(); // Flush all logs to the report

        // Open the generated report automatically
        String pathOfExtentReport = System.getProperty("user.dir") + "\\reports\\" + repName;
        File extentReport = new File(pathOfExtentReport);

        try {
            Desktop.getDesktop().browse(extentReport.toURI()); // Open the report in the default browser
        } catch (IOException e) {
            e.printStackTrace(); // Print stack trace if opening report fails
        }

        
        // Code for sending email (for future reference)
		/*
		 * try { URL url = new URL("file:///" + System.getProperty("user.dir") +
		 * "\\reports\\" + repName); // Create the email message ImageHtmlEmail email =
		 * new ImageHtmlEmail(); email.setDataSourceResolver(new
		 * DataSourceUrlResolver(url)); email.setHostName("smtp.googlemail.com");
		 * email.setSmtpPort(465); email.setAuthenticator(new
		 * DefaultAuthenticator("pavanoltraining@gmail.com", "XXXXX"));
		 * email.setSSLOnConnect(true); email.setFrom("pavanoltraining@gmail.com"); //
		 * Sender email.addTo("pavankumar.busyqa@gmail.com"); // Receiver
		 * email.setSubject("Test Results");
		 * email.setMsg("Please find Attached Report..."); email.attach(url,
		 * "extent report", "please check report..."); email.send(); // send the email }
		 * catch (Exception e) { e.printStackTrace(); }
		 */
        
    }
}