package selenium;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;




@Test
public class TC_01_SignIn {
	
	public WebDriver driver;
	String driverPath = "/Users/tanhungvo/Documents/SeleniumWebDrider/chromedriver-mac-arm64/chromedriver";

	
	@BeforeClass
	public void beforeClass() {

		System.setProperty("webdriver.chrome.driver", driverPath);
		driver = new ChromeDriver();

		driver.get("https://omillionaire.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
	
	public void TC_01_SignInWithGoogle() throws InterruptedException {
		driver.findElement(By.xpath("//*[text()='Sign in with Google']")).click();
		Thread.sleep(6000);

		String mainWindowHandle = driver.getWindowHandle(); // Store the current window handle
		Set<String> windowHandles = driver.getWindowHandles(); // Get all window handles
		for (String handle : windowHandles) {
			if (!handle.equals(mainWindowHandle)) {
				driver.switchTo().window(handle); // Switch to the pop-up window
				WebElement emailField = driver.findElement(By.xpath("//input[@type='email']"));
				emailField.sendKeys("tester.dad@atherlabs.com");
				driver.findElement(By.xpath("//*[text()='Next']")).click();
				Thread.sleep(9000);

				WebElement passwordField = driver.findElement(By.xpath("//input[@type='password']"));
				passwordField.sendKeys("Playsipher@123");
				driver.findElement(By.xpath("//*[text()='Next']")).click();
				Thread.sleep(3000);

				break;
			}
		}
		driver.switchTo().window(mainWindowHandle);
	}
	
	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}
