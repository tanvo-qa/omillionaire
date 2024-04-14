package selenium;

import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.annotations.BeforeClass;

import java.awt.AWTException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;

@Test
public class Recipe_33_Image_To_GIF {

	public WebDriver driver;

	@BeforeClass
	public void beforeClass() {

		String driverPath = "/Users/tanhungvo/Documents/SeleniumWebDrider/chromedriver-mac-arm64/chromedriver";
		System.setProperty("webdriver.chrome.driver", driverPath);
		driver = new ChromeDriver();

		driver.get("https://artventure.sipher.gg/workspace");
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

	public void TC_02_OpenRecipesPage() throws InterruptedException {
		driver.findElement(By.xpath("//*[starts-with(text(),'Recipes')]")).click();
		Thread.sleep(5000);

	}

	public void TC_03_Check_Image_To_GIF() throws InterruptedException, AWTException {

		driver.findElement(By.xpath("//p[@class='font-semibold mb-2 line-clamp-2' and text()='Image to GIF']")).click(); // Mở
																															// Image
																															// To
																															// GIF
																															// recipe
																															// page

		Thread.sleep(2000);

		driver.findElement(By.xpath("//input[@type='file']"))
				.sendKeys(System.getProperty("user.dir") + "/src/test/resources/testdatafile/Image to GIF.jpeg"); // Upload
																													// image

		Thread.sleep(2000);

		driver.findElement(By.xpath("//button[@type='submit' and text()='Generate']")).click(); // Click on Generate
																								// button

		WebDriverWait wait = new WebDriverWait(driver, 90);
		WebElement successMsg = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Recipe has finished running!')]")));

		Assert.assertTrue(successMsg.getText().contains("Recipe has finished running!"));

	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}
