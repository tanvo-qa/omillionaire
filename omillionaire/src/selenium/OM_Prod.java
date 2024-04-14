package selenium;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
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
		driver = new ChromeDriver();

		driver.get("https://omillionaire.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		driver.findElement(By.xpath("//a[@class='smt-close-icon smt-edit-close-icon NC_NOT_ACTIVE smt-close' and text()='X']")).click();
	}
	
	public void openSignInPage() throws InterruptedException {
		
		driver.findElement(By.xpath("//div[@class='section-header__buttons__login' and text()= ' SIGN IN ']")).click();
		
		Thread.sleep(6000);
		
		String url = driver.getCurrentUrl();
		
		Assert.assertEquals(url, "https://omillionaire.com/login");
		
	}
	
	public void signInByMobile() throws InterruptedException {
		
		driver.findElement(By.xpath("//input[@id='input-phone-login']")).sendKeys("938799559");
		driver.findElement(By.xpath("//input[@id='input-password']")).sendKeys("Demo@123");
		
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		
		Thread.sleep(6000);
		
		driver.findElement(By.xpath("//div[@class='user-section__info__dropdown']")).click();
		
		WebElement logOut = driver.findElement(By.xpath("//div[text()=' Logout ']"));
		
		String text = logOut.getText();
		
		Assert.assertEquals(text, "Logout");
		
	}
	
	public void openBuyNowPage() {
		
		driver.findElement(By.xpath("//div[text()='  Buy now  ']")).click();
		
		String url = driver.getCurrentUrl();
		
		Assert.assertEquals(url, "https://omillionaire.com/buy/green-certificates");
		
	}
	
	
	public void openNumberPage() {
		
		driver.findElement(By.xpath("//input[@id = '__BVID__135']")).sendKeys("100");
		driver.findElement(By.xpath("//div[@class='buy-now__action-btn btn-dt']//button[text()=' Buy now ']")).click();
		
		String url = driver.getCurrentUrl();
		
		Assert.assertEquals(url, "https://omillionaire.com/numbers");
		
	}
	
	public void openCheckOutPage() {
		
		driver.findElement(By.xpath("//button[text()=' Quick Pick All Entries ']")).click();
		driver.findElement(By.xpath("//button[text()=' Add to Cart ']")).click();
		
		String url = driver.getCurrentUrl();
		
		Assert.assertEquals(url, "https://omillionaire.com/checkout");
		
	}
	
	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}
