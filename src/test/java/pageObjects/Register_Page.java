package pageObjects;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Register_Page extends BasePage{

	public Register_Page(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
		// Your Account Has Been Created!
	}
	
	@FindBy(xpath="//input[@id='input-firstname']") WebElement txtFirstName;
	@FindBy(xpath="//input[@id='input-lastname']") WebElement txtLastName ;
	@FindBy(xpath="//input[@id='input-email']") WebElement txtEmail;
	@FindBy(xpath="//input[@id='input-telephone']") WebElement txtphone;
	@FindBy(xpath="//input[@id='input-password']") WebElement txtpassword;
	@FindBy(xpath="//input[@id='input-confirm']") WebElement txtconfirm;
	@FindBy(xpath="//input[@name='agree']") WebElement rdbagree;
	@FindBy(xpath="//input[@value='Continue']") WebElement btncontinue ;
	@FindBy(xpath="//h1[normalize-space()='Your Account Has Been Created!']") WebElement msgconfirmation ;


	public void setfirstname(String fname)
	{
		txtFirstName.sendKeys(fname);
	}
	
	public void setlastname(String lname)
	{
		txtLastName.sendKeys(lname);
	}
	
	public void setemail(String email)
	{
		txtEmail.sendKeys(email);
	}
	
	public void setphone(String phone)
	{
		txtphone.sendKeys(phone);
	}
	public void setpassword(String password)
	{
		txtpassword.sendKeys(password);
	}
	public void confirmpassword(String password)
	{
		txtconfirm.sendKeys(password);
	}
	public void clickagree()
	{
		rdbagree.click();
	}
	public void clickcontinue()
	{
		btncontinue.click();}
		/*
		 * // different approaches 
		 * btncontinue.submit(); 
		 * // mouse actions 
		 * Actions act= new Actions(driver); act.moveToElement(btncontinue).click().perform(); //
		 * keyboard action 
		 * btncontinue.sendKeys(Keys.RETURN); 
		 * // explicit wait
		 * WebDriverWait mywait= new WebDriverWait(driver,Duration.ofSeconds(10));
		 * mywait.until(ExpectedConditions.elementToBeClickable(btncontinue)); 
		 * // java script executor 
		 * JavascriptExecutor js = (JavascriptExecutor) driver;
		 * js.executeScript("arguments[0].click()", btncontinue);
		 */
		
		public String getcnfmmessage()
		{
			try {
				
				return(msgconfirmation.getText());
			}
			catch(Exception e)
			{
				return e.getMessage();

			}
			 
			
		}
	
	
	

}
