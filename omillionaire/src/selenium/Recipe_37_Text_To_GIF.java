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
public class Recipe_37_Text_To_GIF {

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

	public void TC_03_Check_Text_To_GIF() throws InterruptedException, AWTException {

		driver.findElement(By.xpath("//p[@class='font-semibold mb-2 line-clamp-2' and text()='Text to GIF']")).click(); // Mở
																														// Image
																														// To
																														// GIF
																														// recipe
																														// page

		Thread.sleep(2000);

		for (int i = 1; i <= 29; i++) {

			driver.findElement(By.xpath("(//p[@class='line-clamp-1 flex-1'])[2]")).click();
			driver.findElement(By.xpath(
					"(//div[@class='w-full overflow-auto gap-4 grid grid-cols-[repeat(auto-fill,minmax(8rem,1fr))] p-4 pt-0']//div[@class='cursor-pointer'])["
							+ i + "]"))
					.click();

			// JavascriptExecutor js = (JavascriptExecutor) driver;
			// js.executeScript("arguments[0].scrollIntoView();",
			// driver.findElement(By.xpath("//p[text()='Number of Images']"))); //Scroll
			// down

			// Select Lora
			driver.findElement(By.xpath("(//p[@class='line-clamp-1 flex-1'])[3]")).click();
			driver.findElement(By.xpath(
					"(//div[@class='w-full overflow-auto gap-4 grid grid-cols-[repeat(auto-fill,minmax(8rem,1fr))] p-4 pt-0']//div[@class='cursor-pointer'])[5]"))
					.click();
			driver.findElement(By.xpath("//button[@type='button' and @title='Add lora']")).click();

			driver.findElement(By.xpath("//button[@type='button' and text()='1']")).click(); // Select number of images

			driver.findElement(By.xpath("//button[@type='submit' and text()='Generate']")).click(); // Click on Generate
																									// button

			WebDriverWait wait = new WebDriverWait(driver, 90);
			WebElement successMsg = wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Recipe has finished running!')]")));

			// Assert.assertTrue(successMsg.getText().contains("Recipe has finished
			// running!"));
			Assert.assertTrue(successMsg.getText().contains("\"Text to GIF\" Recipe has finished running!"));
		}

	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}
