package selenium;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;





@Test
public class OM_Dev {
	
	public WebDriver driver;
	
	String driverPath = "/Users/tanhungvo/Documents/SeleniumWebDrider/chromedriver-mac-arm64/chromedriver";
	
	
	@BeforeClass
	
	public void beforeClass() throws InterruptedException {

		System.setProperty("webdriver.chrome.driver", driverPath);
		
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.addArguments(new String[] {"--incognito"});
		
		driver = new ChromeDriver(chromeOptions);
		
		chromeOptions.addArguments("user-data-dir=/Users/tanhungvo/Library/Application Support/Google/Chrome/Profile 6");
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		String url = "https://dev-omillionaire.bitville.dev/";
		
		driver.get(url);
		
		driver.findElement(By.xpath("//button[@class='btn btn-button primary btn-secondary' and text() =' accept all ']")).click();

				
	}
	
	public void openSignUpPage() throws InterruptedException {
		
		driver.findElement(By.xpath("//div[@class='section-header__buttons__register' and text()= ' register ']")).click();
		
		Thread.sleep(2000);
		
		String url = driver.getCurrentUrl();
		
		driver.findElement(By.xpath("//button[@class='btn btn-button primary btn-secondary' and text() =' accept all ']")).click();
		
		Assert.assertEquals(url, "https://dev-omillionaire.bitville.dev/register");
		
	}
	
	public void signUpByMobile() throws InterruptedException {
		
		driver.findElement(By.xpath("//input[@id='input-full-name']")).sendKeys("Nang No");
		driver.findElement(By.xpath("//input[@id='register-phone']")).sendKeys("909566566");
		driver.findElement(By.xpath("//input[@id='input-password']")).sendKeys("Demo@123");
		
		driver.findElement(By.xpath("//input[@id='checkbox']")).click();
		
		driver.findElement(By.xpath("//button[@type='button' and @class='btn btn-button primary btn-secondary']")).click();
		
		Thread.sleep(7000);
		
		driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@title='reCAPTCHA']")));
		
		driver.findElement(By.id("recaptcha-anchor")).click();
				
		driver.switchTo().defaultContent();
		
		driver.findElement(By.xpath("//div[@class='submit-recaptcha']//button")).click();
		
		Thread.sleep(5000);
		
	}
	
	
	/*public void openSignInPage() throws InterruptedException {
		
		driver.findElement(By.xpath("//div[@class='section-header__buttons__login' and text()= ' LOGIN ']")).click();
				
		String url = driver.getCurrentUrl();
		
		//button[@class='btn btn-button primary btn-secondary' and text() =' accept all ']
						
		Assert.assertEquals(url, "https://dev-omillionaire.bitville.dev/login");
				
	}
	
	public void signInByMobile() throws InterruptedException {
		
		driver.findElement(By.xpath("//input[@id='input-phone-login']")).sendKeys("984777777");
				
		driver.findElement(By.xpath("//input[@id='input-password']")).sendKeys("Demo@123");
		
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		
		Thread.sleep(3000);
		
		driver.switchTo().frame(1);
		
		driver.findElement(By.id("recaptcha-anchor")).click();
		
		Thread.sleep(5000);
				
		driver.switchTo().defaultContent();
		
		driver.findElement(By.xpath("//div[@class='submit-recaptcha']//button")).click();
				
		//Assert.assertEquals(driver.findElement(By.xpath("//div[@class='toast__content__title errorToast']")).getText(), "robot verification failed, please try again.");
		
	}*/
	
	/*public void updateProfile() throws InterruptedException {
	
		driver.get("https://dev-omillionaire.bitville.dev/personal-detail");
		
		//Thread.sleep(3000);
		
		//driver.findElement(By.xpath("//input[@id='input-email']")).sendKeys("recaptcha01@mailinator.com");
		
		driver.findElement(By.xpath("//input[@id='input-phone-personal']")).clear();
		
		driver.findElement(By.xpath("//input[@id='input-phone-personal']")).sendKeys("939557557");
		
		//input[@id='input-phone-personal']
		
		Thread.sleep(3000);
		
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		
		Thread.sleep(3000);
		
		driver.findElement(By.xpath("//input[@id='input-password']")).sendKeys("Demo@123");
		
		driver.findElement(By.xpath("//input[@id='input-confirmPassword']")).sendKeys("Demo@123");
	
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		
		Thread.sleep(10000);
	}*/
	
	/*public void deleteAccount() throws InterruptedException {
		
		driver.get("https://dev-omillionaire.bitville.dev/personal-detail");
		
		Thread.sleep(3000);
		
		driver.findElement(By.xpath("//div[text()=' Delete Account ']")).click();
		
		Thread.sleep(3000);
				
		driver.findElement(By.xpath("//input[@id='input-password']")).sendKeys("Demo@123");
				
		driver.findElement(By.xpath("//input[@id='input-confirmPassword']")).sendKeys("Demo@123");
			
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		
		driver.switchTo().frame(1);
		
		driver.findElement(By.id("recaptcha-anchor")).click();
		
		Thread.sleep(5000);
				
		driver.switchTo().defaultContent();
		
		driver.findElement(By.xpath("//div[@class='submit-recaptcha']//button")).click();
		
		Thread.sleep(10000);
	}*/
	
	
	/*public void forgotPassword() throws InterruptedException {
		
		driver.get("https://dev-omillionaire.bitville.dev/forgot-password");
		
		driver.findElement(By.xpath("//input[@id='input-phone-login']")).sendKeys("935455455");
		
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		
		driver.switchTo().frame(1);
		
		driver.findElement(By.id("recaptcha-anchor")).click();
		
		Thread.sleep(5000);
				
		driver.switchTo().defaultContent();
		
		driver.findElement(By.xpath("//div[@class='submit-recaptcha']//button")).click();
		
		Thread.sleep(5000);
		
	}*/
	
	
	/*@AfterClass
	public void afterClass() {
		driver.quit();
	}*/

}
