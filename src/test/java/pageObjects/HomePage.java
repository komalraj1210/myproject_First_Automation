package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
// note; just extend the home page, automatically we will get the super class constructor

public class HomePage extends BasePage {

	public HomePage(WebDriver driver) {
		super(driver);
	}
	// now finding the elements in the home page

	@FindBy(xpath = "//a[@title='My Account']") WebElement lnkMyAccount;
	@FindBy(xpath = "//a[normalize-space()='Register']") WebElement lnkRegister;
	@FindBy(xpath="//a[normalize-space()='Login']") WebElement lnkLogin;
	
	
	public void clickmyaccount()
	{
		lnkMyAccount.click();
	}
	
	public void clickRegister()
	{
		lnkRegister.click();
	}
	public void clicklogin()
	{
		lnkLogin.click();
	}


}
