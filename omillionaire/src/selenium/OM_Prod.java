package selenium;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


@Test
public class OM_Prod {
	
	public WebDriver driver;
	
	String driverPath = "/Users/tanhungvo/Documents/SeleniumWebDrider/chromedriver-mac-arm64/chromedriver";
	
	
	@BeforeClass
	public void beforeClass() {

		System.setProperty("webdriver.chrome.driver", driverPath);
		
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.addArguments(new String[] {"--incognito"});
		
		driver = new ChromeDriver(chromeOptions);
		

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		String url = "https://omillionaire.com/";
		
		driver.get(url);
		
	}
	
	public void openSignUpPage() throws InterruptedException {
		
		driver.findElement(By.xpath("//div[@class='section-header__buttons__register' and text()= ' register ']")).click();
		
		Thread.sleep(2000);
		
		String url = driver.getCurrentUrl();
		
		Assert.assertEquals(url, "https://omillionaire.com/register");
		
	}
	
	public void signUpByMobile() throws InterruptedException {
		
		driver.findElement(By.xpath("//input[@id='input-full-name']")).sendKeys("Nang No");
		driver.findElement(By.xpath("//input[@id='register-phone']")).sendKeys("909566566");
		driver.findElement(By.xpath("//input[@id='input-password']")).sendKeys("Demo@123");
		
		driver.findElement(By.xpath("//input[@id='checkbox']")).click();
		
		driver.findElement(By.xpath("//button[@type='button' and text()=' register ']")).click();
		
		Thread.sleep(5000);
		
		//String text = driver.findElement(By.xpath("//p[text()=' OTP Verification ']")).getText();
		
		//Assert.assertEquals(text, "OTP VERIFICATION");
		
	}
	
	
	/*public void openSignInPage() throws InterruptedException {
		
		driver.findElement(By.xpath("//div[@class='section-header__buttons__login' and text()= ' LOGIN ']")).click();
		
		Thread.sleep(3000);
		
		String url = driver.getCurrentUrl();
				
		Assert.assertEquals(url, "https://omillionaire.com/login");		
		
	}
	
	public void signInByMobile() throws InterruptedException {
		
		driver.findElement(By.xpath("//input[@id='input-phone-login']")).sendKeys("938799559");
		driver.findElement(By.xpath("//input[@id='input-password']")).sendKeys("Demo@123");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
				
		Thread.sleep(3000);
		
		
	}*/
	
	/*public void openBuyNowPage() {
		
		driver.findElement(By.xpath("//div[text()='  Buy now  ']")).click();
		
		String url = driver.getCurrentUrl();
		
		Assert.assertEquals(url, "https://dev-omillionaire.bitville.dev/buy/green-certificates");
		
	}
	
	
	public void openNumberPage() {
		
		driver.findElement(By.xpath("//input[@id = '__BVID__135']")).sendKeys("100");
		driver.findElement(By.xpath("//div[@class='buy-now__action-btn btn-dt']//button[text()=' Buy now ']")).click();
		
		String url = driver.getCurrentUrl();
		
		Assert.assertEquals(url, "https://dev-omillionaire.bitville.dev/numbers");
		
	}
	
	public void openCheckOutPage() {
		
		driver.findElement(By.xpath("//button[text()=' Quick Pick All Entries ']")).click();
		driver.findElement(By.xpath("//button[text()=' Add to Cart ']")).click();
		
		String url = driver.getCurrentUrl();
		
		Assert.assertEquals(url, "https://dev-omillionaire.bitville.dev/checkout");
		
	}*/
	
	/*public void forgotPassword() throws InterruptedException {
	
	driver.get("https://omillionaire.com/forgot-password");
	
	driver.findElement(By.xpath("//input[@id='input-phone-login']")).sendKeys("935455455");
	
	driver.findElement(By.xpath("//button[@type='submit']")).click();
	
	}*/
	
	/*@AfterClass
	public void afterClass() {
		driver.quit();
	}*/

}
