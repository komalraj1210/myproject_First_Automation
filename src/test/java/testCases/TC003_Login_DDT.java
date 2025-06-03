package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccount_Page;
import testBase.BaseClass;
import testBase.BaseClass__mini;
import utilities.DataProviders;

public class TC003_Login_DDT extends BaseClass  {
	
	
	@Test(dataProvider = "Login_data",dataProviderClass =DataProviders.class,groups = {"master","smoke","sanity"})
	
	public void verifyloginddt(String email,String password,String expected)
	{
		logger.info("--------------> TC003_Login_DDT Started");

		try {
		HomePage hp = new HomePage(driver);
		hp.clickmyaccount();
		hp.clicklogin();
		logger.info("--------> Entered Login page");
		LoginPage lp = new LoginPage(driver);
		lp.sendemail(email);
		lp.sendpassword(password);
		lp.clicklogin();
		MyAccount_Page my= new MyAccount_Page(driver);
		boolean display_status=my.ismyaccountdisplayed();
		//Assert.assertEquals(display_status, true,"Login is failed");
		// we give message to make sure if the login fails, system has to display the message
		//		Assert.assertTrue(display_status);
		
		if(expected.equalsIgnoreCase("valid"))
		{
			if(display_status==true)
			{
				my.clicklogout();
				Assert.assertTrue(true);
			}
			else
			{
				Assert.assertTrue(false);

			}
		}
		if(expected.equalsIgnoreCase("invalid"))
		{
			if(display_status==true)
			{
				my.clicklogout();
				Assert.assertTrue(false);
			}
			else
			{
				Assert.assertTrue(true);

			}
		}
		
		}
		
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			Assert.fail();
		}
		
		logger.info("--------------> TC003_Login_DDT Started");
	}
	

}
