package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MyAccount_Page extends BasePage {

	public MyAccount_Page(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
@FindBy(xpath="//h2[normalize-space()='My Account']") WebElement MyAccount;
@FindBy(xpath="//a[@class='list-group-item'][normalize-space()='Logout']") WebElement btnLogout;

public boolean ismyaccountdisplayed()
{
	try {
	return( MyAccount.isDisplayed());
	}
	catch(Exception e)
	{
		return false;
	}
}
	
public void clicklogout()
{
	btnLogout.click();
}
}
