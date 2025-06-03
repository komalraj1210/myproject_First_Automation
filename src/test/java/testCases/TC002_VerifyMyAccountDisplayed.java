package testCases;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccount_Page;
import testBase.BaseClass;


public class TC002_VerifyMyAccountDisplayed extends BaseClass {
	

	@Test(priority=1,groups = {"regression","master"})
	public void verifyVerifyMyAccountDisplayed()
	{
		try {
		logger.info("--------> TC002_VerifyMyAccountDisplayed Started");
		HomePage hp = new HomePage(driver);
		hp.clickmyaccount();
		hp.clicklogin();
		logger.info("--------> Entered Login page");
		LoginPage lp = new LoginPage(driver);
		lp.sendemail(proper.getProperty("UseName2"));
		lp.sendpassword(proper.getProperty("Password2"));
		lp.clicklogin();
		MyAccount_Page my= new MyAccount_Page(driver);
		boolean display_status=my.ismyaccountdisplayed();
		//Assert.assertEquals(display_status, true,"Login is failed");
		// we give message to make sure if the login fails, system has to display the message
		Assert.assertTrue(display_status);
		}
		
		catch(Exception e)
		{
			logger.error("Test Failed");
			logger.debug("Debug logs");
			Assert.fail("Test Failed", e.getCause());
			System.out.println(" Setup failed: " + e.getMessage());
		}
		logger.info("--------> TC002_VerifyMyAccountDisplayed Completed");

	}
}
