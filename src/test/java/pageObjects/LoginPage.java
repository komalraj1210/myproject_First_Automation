package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage{

	public LoginPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	@FindBy(xpath="//input[@id='input-email']") WebElement txtEmail;
	@FindBy(xpath="//input[@id='input-password']") WebElement txtPassword;
	@FindBy(xpath="//input[@value='Login']") WebElement BtnLogin;

	
	public void sendemail( String email)
	{
		txtEmail.sendKeys(email);
	}
	public void sendpassword(String Password)
	{
		txtPassword.sendKeys(Password);
	}
	public void clicklogin()
	{
		BtnLogin.click();
	}

}
