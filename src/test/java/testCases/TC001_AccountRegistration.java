package testCases;

import static org.testng.Assert.assertTrue;
import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.Register_Page;
import testBase.BaseClass;
import testBase.BaseClass__mini;
public class TC001_AccountRegistration extends BaseClass  {
	
	
	@Test(priority=1,groups = {"smoke","master"})
	public void Verify_Account_Registration()
	{
		try {
		logger.info("--------> Test Started");
		HomePage hp = new HomePage(driver);
		hp.clickmyaccount();
		hp.clickRegister();
		logger.info("---------> clicked on the login");
		Register_Page rp = new Register_Page(driver);
		rp.setfirstname(randomString().toUpperCase());
		rp.setlastname(randomString().toUpperCase());
		String email=(randomString()+"@gmail.com");
		rp.setemail(email);
		rp.setphone(randomNumber());
		String password=random_AlphaNumeric();
		rp.setpassword(password);
		rp.confirmpassword(password);
		rp.clickagree();
		rp.clickcontinue();
		System.out.println("UseName: "+ email );
		System.out.println("Password: "+ password );
		logger.info("---------> clicking on continue in registration page");
		String confirmmessage=rp.getcnfmmessage();
		//assertEquals(confirmmessage, "Your Account Has Been Created!");
		if(confirmmessage.equals("Your Account Has Been Created!")) {
			assertTrue(true);
			logger.info("--------->Account created");
		}
		
		else {
			assertTrue(false);
			logger.info("--------->Account not created");
		}
		logger.info("--------->Test completed");

	}
	catch(Exception e)
	{
		logger.error("Test Failed");
		logger.debug("Debug logs");
		Assert.fail("Test Failed", e.getCause());
		System.out.println(" Setup failed: " + e.getMessage());
        e.printStackTrace();
	}
	
	}
	
}
