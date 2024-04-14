package selenium;

import org.testng.Assert;
import org.testng.annotations.*;

import java.awt.AWTException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@Test
public class All_Recipes_Dev {

	public WebDriver driver;

	@BeforeClass
	public void beforeClass() throws InterruptedException {

		String driverPath = "/Users/tanhungvo/Documents/SeleniumWebDrider/chromedriver-mac-arm64/chromedriver";
		System.setProperty("webdriver.chrome.driver", driverPath);
		driver = new ChromeDriver();

		driver.get("https://artventure.sipher.gg/workspace/recipes");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

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

	@Test
	public void TC_01_Check_Outpainting_LCM() throws InterruptedException, AWTException {

		driver.findElement(By.xpath("//p[1][starts-with(text(),'Outpainting LCM')]")).click(); // Mở Outpainting LCM
																								// page

		Thread.sleep(2000);

		driver.findElement(By.xpath("//input[@type='file']"))
				.sendKeys(System.getProperty("user.dir") + "/src/test/resources/testdatafile/Image01.jpg"); // Upload
																											// image

		Thread.sleep(2000);

		driver.findElement(By.xpath("//textarea[@name='prompt']")).sendKeys("A photo of a cat in the forest"); // Input
																												// prompt

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();",
				driver.findElement(By.xpath("//p[text()='Number of Images']"))); // Scroll down

		driver.findElement(By.xpath("//button[@type='button' and text()='1']")).click(); // Select number of images

		driver.findElement(By.xpath("//button[@type='submit' and text()='Generate']")).click(); // Click on Generate
																								// button

		WebDriverWait wait = new WebDriverWait(driver, 90);
		WebElement successMsg = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Recipe has finished running!')]")));

		Assert.assertTrue(successMsg.getText().contains("Recipe has finished running!"));

	}

	@Test
	public void TC_02_Check_Outpainting() throws InterruptedException, AWTException {
		// Search and select "Outpainting" recipe
		driver.findElement(By.xpath("(//div[@class='max-w-full cursor-pointer'])[1]")).click();
		driver.findElement(
				By.xpath("(//div[@class='max-w-full cursor-pointer']//input[@class='bg-transparent p-2'])[1]"))
				.sendKeys("Outpainting");
		driver.findElement(By.xpath("//p[1][contains(text(),'Outpainting') and not(contains(text(), 'LCM'))]")).click(); // Mở
																															// Outpainting
																															// LCM
																															// page

		Thread.sleep(2000);

		driver.findElement(By.xpath("//input[@type='file']"))
				.sendKeys(System.getProperty("user.dir") + "/src/test/resources/testdatafile/Image01.jpg"); // Upload
																											// image

		Thread.sleep(2000);

		driver.findElement(By.xpath("//textarea[@name='prompt']")).sendKeys("A photo of a cat in the forest"); // Input
																												// prompt

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();",
				driver.findElement(By.xpath("//p[text()='Number of Images']"))); // Scroll down

		driver.findElement(By.xpath("//button[@type='button' and text()='1']")).click(); // Select number of images

		driver.findElement(By.xpath("//button[@type='submit' and text()='Generate']")).click(); // Click on Generate
																								// button

		WebDriverWait wait = new WebDriverWait(driver, 90);
		WebElement successMsg = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Recipe has finished running!')]")));

		Assert.assertTrue(successMsg.getText().contains("Recipe has finished running!"));

	}

	@Test
	public void TC_03_Check_Sketch_To_Image() throws InterruptedException, AWTException {

		// Search and select "Outpainting" recipe
		driver.findElement(By.xpath("(//div[@class='max-w-full cursor-pointer'])[1]")).click();
		driver.findElement(
				By.xpath("(//div[@class='max-w-full cursor-pointer']//input[@class='bg-transparent p-2'])[1]"))
				.sendKeys("Sketch To Image");
		driver.findElement(By.xpath("//p[1][contains(text(),'Sketch To Image')]")).click(); // Mở Sketch To Image page

		Thread.sleep(2000);

		driver.findElement(By.xpath("//input[@type='file']"))
				.sendKeys(System.getProperty("user.dir") + "/src/test/resources/testdatafile/Image01.jpg"); // Upload
																											// image

		Thread.sleep(2000);

		driver.findElement(By.xpath("//textarea[@name='prompt']")).sendKeys("A photo of a cat in the forest"); // Input
																												// prompt

		Thread.sleep(2000);

		driver.findElement(By.xpath("//div[@class='line-clamp-1']//p[contains(text(),'Select Lora')]")).click();
		driver.findElement(By.xpath("//p[@class='px-2' and text()='Anime Outline v4']")).click();
		driver.findElement(By.xpath("//button[@type='button' and @title='Add lora']")).click();

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();",
				driver.findElement(By.xpath("//p[text()='Number of Images']"))); // Scroll down

		driver.findElement(By.xpath("//button[@type='button' and text()='1']")).click(); // Select number of images

		driver.findElement(By.xpath("//button[@type='submit' and text()='Generate']")).click(); // Click on Generate
																								// button

		WebDriverWait wait = new WebDriverWait(driver, 90);
		WebElement successMsg = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Recipe has finished running!')]")));

		Assert.assertTrue(successMsg.getText().contains("Recipe has finished running!"));

	}

	@Test
	public void TC_04_Check_Tiled_Upscaler_LCM() throws InterruptedException, AWTException {

		driver.findElement(By.xpath("(//div[@class='max-w-full cursor-pointer'])[1]")).click();
		driver.findElement(
				By.xpath("(//div[@class='max-w-full cursor-pointer']//input[@class='bg-transparent p-2'])[1]"))
				.sendKeys("Tiled Upscaler LCM");
		driver.findElement(By.xpath("//p[1][contains(text(),'Tiled Upscaler LCM')]")).click(); // Mở Tiled Upscaler LCM
																								// page

		Thread.sleep(2000);

		driver.findElement(By.xpath("//input[@type='file']"))
				.sendKeys(System.getProperty("user.dir") + "/src/test/resources/testdatafile/Image01.jpg"); // Upload
																											// image

		Thread.sleep(2000);

		// driver.findElement(By.xpath("//textarea[@name='prompt']")).sendKeys("A photo
		// of a cat in the forest"); //Input prompt

		driver.findElement(By.xpath("//div[@class='line-clamp-1']//p[contains(text(),'Select Lora')]")).click();
		driver.findElement(By.xpath("//p[@class='px-2' and text()='Anime Outline v4']")).click();
		driver.findElement(By.xpath("//button[@type='button' and @title='Add lora']")).click();

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();",
				driver.findElement(By.xpath("//p[text()='Number of Images']"))); // Scroll down

		driver.findElement(By.xpath("//button[@type='button' and text()='1']")).click(); // Select number of images

		driver.findElement(By.xpath("//button[@type='submit' and text()='Generate']")).click(); // Click on Generate
																								// button

		WebDriverWait wait = new WebDriverWait(driver, 90);
		WebElement successMsg = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Recipe has finished running!')]")));

		Assert.assertTrue(successMsg.getText().contains("Recipe has finished running!"));

	}

	@Test
	public void TC_05_Check_AI_Image_Generator_LCM() throws InterruptedException, AWTException {

		driver.findElement(By.xpath("(//div[@class='max-w-full cursor-pointer'])[1]")).click();
		driver.findElement(
				By.xpath("(//div[@class='max-w-full cursor-pointer']//input[@class='bg-transparent p-2'])[1]"))
				.sendKeys("AI Image Generator LCM");
		driver.findElement(By.xpath("//p[1][contains(text(),'AI Image Generator LCM')]")).click(); // Mở AI Image
																									// Generator LCM
																									// page

		Thread.sleep(2000);

		// driver.findElement(By.xpath("//textarea[@name='prompt']")).sendKeys("A photo
		// of a cat in the forest"); //Input prompt
		// driver.findElement(By.xpath("//textarea[@name='negative_prompt']")).sendKeys("A
		// photo of a cat in the forest"); //Input prompt

		driver.findElement(By.xpath("//div[@class='line-clamp-1']//p[contains(text(),'Select Lora')]")).click();
		driver.findElement(By.xpath("//p[@class='px-2' and text()='Anime Outline v4']")).click();
		driver.findElement(By.xpath("//button[@type='button' and @title='Add lora']")).click();

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();",
				driver.findElement(By.xpath("//p[text()='Number of Images']"))); // Scroll down

		driver.findElement(By.xpath("//button[@type='button' and text()='1']")).click(); // Select number of images

		driver.findElement(By.xpath("//button[@type='submit' and text()='Generate']")).click(); // Click on Generate
																								// button

		WebDriverWait wait = new WebDriverWait(driver, 90);
		WebElement successMsg = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Recipe has finished running!')]")));

		Assert.assertTrue(successMsg.getText().contains("Recipe has finished running!"));

	}

	@Test
	public void TC_06_Check_Face_Swap() throws InterruptedException, AWTException {

		driver.findElement(By.xpath("(//div[@class='max-w-full cursor-pointer'])[1]")).click();
		driver.findElement(
				By.xpath("(//div[@class='max-w-full cursor-pointer']//input[@class='bg-transparent p-2'])[1]"))
				.sendKeys("Face Swap");
		driver.findElement(By.xpath("//p[1][contains(text(),'Face Swap')]")).click(); // Mở Face Swap page

		Thread.sleep(2000);

		driver.findElement(By.xpath("(//div[@class='space-y-6']//input[@type='file'])[1]"))
				.sendKeys(System.getProperty("user.dir") + "/src/test/resources/testdatafile/Image01.jpg"); // Upload
																											// origion
																											// face

		driver.findElement(By.xpath("(//div[@class='space-y-6']//input[@type='file'])[2]"))
				.sendKeys(System.getProperty("user.dir") + "/src/test/resources/testdatafile/Image02.jpg"); // Upload
																											// face
																											// image

		Thread.sleep(2000);

		driver.findElement(By.xpath("//button[@type='submit' and text()='Generate']")).click(); // Click on Generate
																								// button

		WebDriverWait wait = new WebDriverWait(driver, 90);
		WebElement successMsg = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Recipe has finished running!')]")));

		Assert.assertTrue(successMsg.getText().contains("Recipe has finished running!"));
	}

	@Test
	public void TC_07_Check_Game_Character_Concept_Explore() throws InterruptedException, AWTException {

		driver.findElement(By.xpath("(//div[@class='max-w-full cursor-pointer'])[1]")).click();
		driver.findElement(
				By.xpath("(//div[@class='max-w-full cursor-pointer']//input[@class='bg-transparent p-2'])[1]"))
				.sendKeys("Game Character Concept Explore");
		driver.findElement(By.xpath("//p[1][contains(text(),'Game Character Concept Explore')]")).click(); // Mở Game
																											// Character
																											// Concept
																											// Explore
																											// page

		Thread.sleep(2000);

		// driver.findElement(By.xpath("//textarea[@name='prompt']")).sendKeys("A photo
		// of a cat in the forest"); //Input prompt

		driver.findElement(By.xpath("(//div[@class='space-y-6']//input[@type='file'])[2]"))
				.sendKeys(System.getProperty("user.dir") + "/src/test/resources/testdatafile/Image01.jpg"); // Upload
																											// your own
																											// theme

		driver.findElement(By.xpath("//button[@type='button' and text()='1']")).click(); // Select number of images

		driver.findElement(By.xpath("//button[@type='submit' and text()='Generate']")).click(); // Click on Generate
																								// button

		WebDriverWait wait = new WebDriverWait(driver, 90);
		WebElement successMsg = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Recipe has finished running!')]")));

		Assert.assertTrue(successMsg.getText().contains("Recipe has finished running!"));

	}

	@Test
	public void TC_08_Check_Image_To_Image() throws InterruptedException, AWTException {

		driver.findElement(By.xpath("(//div[@class='max-w-full cursor-pointer'])[1]")).click();
		driver.findElement(
				By.xpath("(//div[@class='max-w-full cursor-pointer']//input[@class='bg-transparent p-2'])[1]"))
				.sendKeys("Image To Image");
		driver.findElement(By.xpath("//p[1][contains(text(),'Image To Image')]")).click(); // Mở Image To Image recipe
																							// page

		Thread.sleep(2000);

		driver.findElement(By.xpath("(//div[@class='space-y-6']//input[@type='file'])[1]"))
				.sendKeys(System.getProperty("user.dir") + "/src/test/resources/testdatafile/Image01.jpg"); // Upload
																											// image to
																											// generate

		driver.findElement(By.xpath("//textarea[@name='prompt']")).clear();
		driver.findElement(By.xpath("//textarea[@name='prompt']")).sendKeys("masterpiece, best quality"); // Input
																											// prompt

		driver.findElement(By.xpath("//div[@class='line-clamp-1']//p[contains(text(),'Select Lora')]")).click();
		driver.findElement(By.xpath("//p[@class='px-2' and text()='Anime Outline v4']")).click();
		driver.findElement(By.xpath("//button[@type='button' and @title='Add lora']")).click();

		driver.findElement(By.xpath("//button[@type='button' and text()='1']")).click(); // Select number of images

		driver.findElement(By.xpath("//button[@type='submit' and text()='Generate']")).click(); // Click on Generate
																								// button

		WebDriverWait wait = new WebDriverWait(driver, 90);
		WebElement successMsg = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Recipe has finished running!')]")));

		Assert.assertTrue(successMsg.getText().contains("Recipe has finished running!"));

	}

	@Test
	public void TC_09_Check_Upscaler() throws InterruptedException, AWTException {

		driver.findElement(By.xpath("(//div[@class='max-w-full cursor-pointer'])[1]")).click();
		driver.findElement(
				By.xpath("(//div[@class='max-w-full cursor-pointer']//input[@class='bg-transparent p-2'])[1]"))
				.sendKeys("Upscaler");
		driver.findElement(By.xpath("//p[1][contains(text(),'Upscaler') and not(contains(text(), 'LCM'))]")).click(); // Mở
																														// Upscaler
																														// recipe
																														// page

		Thread.sleep(2000);

		driver.findElement(By.xpath("(//input[@type='file'])"))
				.sendKeys(System.getProperty("user.dir") + "/src/test/resources/testdatafile/Image01.jpg"); // Upload
																											// image to
																											// generate

		Thread.sleep(2000);

		driver.findElement(By.xpath("//button[@type='submit' and text()='Generate']")).click(); // Click on Generate
																								// button

		WebDriverWait wait = new WebDriverWait(driver, 90);
		WebElement successMsg = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Recipe has finished running!')]")));

		Assert.assertTrue(successMsg.getText().contains("Recipe has finished running!"));

	}

	@Test
	public void TC_10_Check_Miss_Universe() throws InterruptedException, AWTException {

		driver.findElement(By.xpath("(//div[@class='max-w-full cursor-pointer'])[1]")).click();
		driver.findElement(
				By.xpath("(//div[@class='max-w-full cursor-pointer']//input[@class='bg-transparent p-2'])[1]"))
				.sendKeys("Miss Universe");
		driver.findElement(By.xpath("//p[1][contains(text(),'Miss Universe')]")).click(); // Mở Miss Universe recipe
																							// page

		Thread.sleep(2000);

		driver.findElement(By.xpath("(//input[@type='file'])"))
				.sendKeys(System.getProperty("user.dir") + "/src/test/resources/testdatafile/Image01.jpg"); // Upload
																											// image to
																											// generate

		Thread.sleep(2000);

		driver.findElement(By.xpath(
				"(//div[@class='w-full grid grid-cols-2 gap-2 pr-1 max-h-[20rem] overflow-auto']//button[@type='button'])[5]"))
				.click(); // Select a template

		// driver.findElement(By.xpath("//input[(@type='checkbox') and (@name='Or create
		// your own template')]")).click(); //Turn on your own template check-box

		driver.findElement(By.xpath("//button[@type='button' and text()='1']")).click(); // Select number of images

		driver.findElement(By.xpath("//button[@type='submit' and text()='Generate']")).click(); // Click on Generate
																								// button

		WebDriverWait wait = new WebDriverWait(driver, 90);
		WebElement successMsg = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Recipe has finished running!')]")));

		Assert.assertTrue(successMsg.getText().contains("Recipe has finished running!"));

	}

	@Test
	public void TC_11_Check_Coherent_Expression() throws InterruptedException, AWTException {

		driver.findElement(By.xpath("(//div[@class='max-w-full cursor-pointer'])[1]")).click();
		driver.findElement(
				By.xpath("(//div[@class='max-w-full cursor-pointer']//input[@class='bg-transparent p-2'])[1]"))
				.sendKeys("Coherent Expression");
		driver.findElement(By.xpath("//p[1][contains(text(),'Coherent Expression')]")).click(); // Mở Cohenrent
																								// Expression recipe
																								// page

		Thread.sleep(2000);

		driver.findElement(By.xpath("//button[@type='submit' and text()='Generate']")).click(); // Click on Generate
																								// button

		WebDriverWait wait = new WebDriverWait(driver, 90);
		WebElement successMsg = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Recipe has finished running!')]")));

		Assert.assertTrue(successMsg.getText().contains("Recipe has finished running!"));

	}

	@Test
	public void TC_12_Check_Spookifyme() throws InterruptedException, AWTException {

		driver.findElement(By.xpath("(//div[@class='max-w-full cursor-pointer'])[1]")).click();
		driver.findElement(
				By.xpath("(//div[@class='max-w-full cursor-pointer']//input[@class='bg-transparent p-2'])[1]"))
				.sendKeys("Spookifyme");
		driver.findElement(By.xpath("//p[1][contains(text(),'Spookifyme')]")).click(); // Mở Spookifyme recipe page

		Thread.sleep(2000);

		driver.findElement(By.xpath("(//input[@type='file'])"))
				.sendKeys(System.getProperty("user.dir") + "/src/test/resources/testdatafile/Image01.jpg"); // Upload
																											// image to
																											// generate

		Thread.sleep(2000);

		driver.findElement(By.xpath("//button[@type='button' and contains(text(),'Male')]")).click(); // Select a gender

		driver.findElement(By.xpath("//button[@type='button' and contains(text(),'Middle-Aged Adults (over 31-45)')]"))
				.click(); // Select a age

		driver.findElement(By.xpath(
				"(//div[@class='w-full grid grid-cols-2 gap-2 pr-1 max-h-[20rem] overflow-auto']//button[@type='button'])[12]"))
				.click(); // Select a custome theme

		/*
		 * WebElement custom_Prompt = driver.findElement(By.
		 * xpath("//div[@class='flex items-center']//input[@type='checkbox']"));
		 * 
		 * custom_Prompt.click();
		 * 
		 * if(custom_Prompt.isSelected()) {
		 * 
		 * driver.findElement(By.xpath("//textarea[@name='prompt']")).clear(); //Clear
		 * text in field
		 * 
		 * driver.findElement(By.xpath("//textarea[@name='prompt']")).
		 * sendKeys("1 girl, (solo), (wearing Bat girl suit without mask),looking at viewer,"
		 * ); //Input prompt
		 * 
		 * }else { custom_Prompt.click(); }
		 */

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();",
				driver.findElement(By.xpath("//p[text()='Number of Images']"))); // Scroll down

		driver.findElement(By.xpath("//button[@type='button' and text()='1']")).click(); // Select number of images

		driver.findElement(By.xpath("//button[@type='submit' and text()='Generate']")).click(); // Click on Generate
																								// button

		WebDriverWait wait = new WebDriverWait(driver, 90);
		WebElement successMsg = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Recipe has finished running!')]")));

		Assert.assertTrue(successMsg.getText().contains("Recipe has finished running!"));

	}

	@Test
	public void TC_13_Check_AI_Yearbook() throws InterruptedException, AWTException {

		driver.findElement(By.xpath("(//div[@class='max-w-full cursor-pointer'])[1]")).click();
		driver.findElement(
				By.xpath("(//div[@class='max-w-full cursor-pointer']//input[@class='bg-transparent p-2'])[1]"))
				.sendKeys("AI Yearbook");
		driver.findElement(By.xpath("//p[1][contains(text(),'AI Yearbook')]")).click(); // Mở AI Yearbook recipe page

		Thread.sleep(2000);

		driver.findElement(By.xpath("(//input[@type='file'])"))
				.sendKeys(System.getProperty("user.dir") + "/src/test/resources/testdatafile/Image01.jpg"); // Upload
																											// image to
																											// generate

		Thread.sleep(2000);

		driver.findElement(By.xpath(
				"(//div[@class='w-full grid grid-cols-2 gap-2 pr-1 max-h-[20rem] overflow-auto']//button[@type='button'])[5]"))
				.click(); // Select a template

		/*
		 * WebElement custom_Prompt = driver.findElement(By.
		 * xpath("//div[@class='flex items-center']//input[@type='checkbox']"));
		 * 
		 * custom_Prompt.click();
		 * 
		 * if(custom_Prompt.isSelected()) {
		 * 
		 * driver.findElement(By.xpath("//textarea[@name='prompt']")).clear(); //Clear
		 * text in field
		 * 
		 * driver.findElement(By.xpath("//textarea[@name='prompt']")).
		 * sendKeys("1 girl, (solo), (wearing Bat girl suit without mask),looking at viewer,"
		 * ); //Input prompt
		 * 
		 * }else { custom_Prompt.click(); }
		 */

		driver.findElement(By.xpath("//button[@type='button' and text()='1']")).click(); // Select number of images

		driver.findElement(By.xpath("//button[@type='submit' and text()='Generate']")).click(); // Click on Generate
																								// button

		WebDriverWait wait = new WebDriverWait(driver, 90);
		WebElement successMsg = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Recipe has finished running!')]")));

		Assert.assertTrue(successMsg.getText().contains("Recipe has finished running!"));

	}

	@Test
	public void TC_14_Check_AI_Mixer() throws InterruptedException, AWTException {

		driver.findElement(By.xpath("(//div[@class='max-w-full cursor-pointer'])[1]")).click();
		driver.findElement(
				By.xpath("(//div[@class='max-w-full cursor-pointer']//input[@class='bg-transparent p-2'])[1]"))
				.sendKeys("AI Mixer");
		driver.findElement(By.xpath("//p[1][contains(text(),'AI Mixer')]")).click(); // Mở AI Mixer recipe page

		Thread.sleep(2000);

		driver.findElement(By.xpath("(//div[@class='space-y-6']//input[@type='file'])[1]"))
				.sendKeys(System.getProperty("user.dir") + "/src/test/resources/testdatafile/Image01.jpg"); // Upload
																											// origion
																											// face

		driver.findElement(By.xpath("(//div[@class='space-y-6']//input[@type='file'])[2]"))
				.sendKeys(System.getProperty("user.dir") + "/src/test/resources/testdatafile/Image02.jpg"); // Upload
																											// face
																											// image

		Thread.sleep(2000);

		driver.findElement(By.xpath("//textarea[@name='prompt']")).clear(); // Clear text in field

		driver.findElement(By.xpath("//textarea[@name='prompt']"))
				.sendKeys("1 girl, (solo), (wearing Bat girl suit without mask),looking at viewer,"); // Input prompt

		driver.findElement(By.xpath("//div[@class='line-clamp-1']//p[contains(text(),'Select Lora')]")).click();
		driver.findElement(By.xpath("//p[@class='px-2' and text()='Anime Outline v4']")).click();
		driver.findElement(By.xpath("//button[@type='button' and @title='Add lora']")).click();

		driver.findElement(By.xpath("//button[@type='button' and text()='3:2']")).click(); // Select Aspect Ratio

		driver.findElement(By.xpath("//button[@type='button' and text()='1']")).click(); // Select number of images

		driver.findElement(By.xpath("//button[@type='submit' and text()='Generate']")).click(); // Click on Generate
																								// button

		WebDriverWait wait = new WebDriverWait(driver, 90);
		WebElement successMsg = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Recipe has finished running!')]")));

		Assert.assertTrue(successMsg.getText().contains("Recipe has finished running!"));

	}

	@Test
	public void TC_15_Check_Logo_Art() throws InterruptedException, AWTException {

		driver.findElement(By.xpath("(//div[@class='max-w-full cursor-pointer'])[1]")).click();
		driver.findElement(
				By.xpath("(//div[@class='max-w-full cursor-pointer']//input[@class='bg-transparent p-2'])[1]"))
				.sendKeys("Logo Art");
		driver.findElement(By.xpath("//p[1][contains(text(),'Logo Art')]")).click(); // Mở Logo Art recipe page

		Thread.sleep(2000);

		driver.findElement(By.xpath("//input[@type='file']"))
				.sendKeys(System.getProperty("user.dir") + "/src/test/resources/testdatafile/Image01.jpg"); // Upload
																											// origion
																											// face

		Thread.sleep(2000);

		driver.findElement(By.xpath(
				"(//div[@class='w-full grid grid-cols-2 gap-2 pr-1 max-h-[20rem] overflow-auto']//button[@type='button'])[5]"))
				.click(); // Select a template

		driver.findElement(By.xpath("//button[@type='button' and text()='1']")).click(); // Select number of images

		driver.findElement(By.xpath("//button[@type='submit' and text()='Generate']")).click(); // Click on Generate
																								// button

		WebDriverWait wait = new WebDriverWait(driver, 90);
		WebElement successMsg = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Recipe has finished running!')]")));

		Assert.assertTrue(successMsg.getText().contains("Recipe has finished running!"));

	}

	@Test
	public void TC_16_Check_Commercial_Photoshoot() throws InterruptedException, AWTException {

		driver.findElement(By.xpath("(//div[@class='max-w-full cursor-pointer'])[1]")).click();
		driver.findElement(
				By.xpath("(//div[@class='max-w-full cursor-pointer']//input[@class='bg-transparent p-2'])[1]"))
				.sendKeys("Commercial Photoshoot");
		driver.findElement(By.xpath("//p[1][contains(text(),'Commercial Photoshoot')]")).click(); // Mở Commercial
																									// Photoshoot recipe
																									// page

		Thread.sleep(2000);

		driver.findElement(By.xpath("//input[@type='file']"))
				.sendKeys(System.getProperty("user.dir") + "/src/test/resources/testdatafile/Image01.jpg"); // Upload
																											// origion
																											// face

		Thread.sleep(2000);

		driver.findElement(By.xpath(
				"(//div[@class='w-full grid grid-cols-2 gap-2 pr-1 max-h-[20rem] overflow-auto']//button[@type='button'])[5]"))
				.click(); // Select a template

		driver.findElement(By.xpath("//button[@type='button' and text()='1']")).click(); // Select number of images

		driver.findElement(By.xpath("//button[@type='submit' and text()='Generate']")).click(); // Click on Generate
																								// button

		WebDriverWait wait = new WebDriverWait(driver, 90);
		WebElement successMsg = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Recipe has finished running!')]")));

		Assert.assertTrue(successMsg.getText().contains("Recipe has finished running!"));

	}

	@Test
	public void TC_17_Check_AI_Colorize_Photo() throws InterruptedException, AWTException {

		driver.findElement(By.xpath("(//div[@class='max-w-full cursor-pointer'])[1]")).click();
		driver.findElement(
				By.xpath("(//div[@class='max-w-full cursor-pointer']//input[@class='bg-transparent p-2'])[1]"))
				.sendKeys("AI Colorize Photo");
		driver.findElement(By.xpath("//p[1][contains(text(),'AI Colorize Photo')]")).click(); // Mở AI Colorize Photo
																								// recipe page

		Thread.sleep(2000);

		driver.findElement(By.xpath("//input[@type='file']"))
				.sendKeys(System.getProperty("user.dir") + "/src/test/resources/testdatafile/Image01.jpg"); // Upload
																											// origion
																											// face

		Thread.sleep(2000);

		driver.findElement(By.xpath("//button[@type='submit' and text()='Generate']")).click(); // Click on Generate
																								// button

		WebDriverWait wait = new WebDriverWait(driver, 90);
		WebElement successMsg = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Recipe has finished running!')]")));

		Assert.assertTrue(successMsg.getText().contains("Recipe has finished running!"));

	}

	@Test
	public void TC_18_Check_Create_QR_Code_with_AI() throws InterruptedException, AWTException {

		driver.findElement(By.xpath("(//div[@class='max-w-full cursor-pointer'])[1]")).click();
		driver.findElement(
				By.xpath("(//div[@class='max-w-full cursor-pointer']//input[@class='bg-transparent p-2'])[1]"))
				.sendKeys("Create QR Code with AI");
		driver.findElement(By.xpath("//p[1][contains(text(),'Create QR Code with AI')]")).click(); // Mở Create QR Code
																									// with AI recipe
																									// page

		Thread.sleep(2000);

		driver.findElement(By.xpath("//textarea[@name='url']")).clear();
		driver.findElement(By.xpath("//textarea[@name='url']"))
				.sendKeys("https://artventure.sipher.gg/recipes/ai-qr-code");

		driver.findElement(By.xpath(
				"(//div[@class='w-full grid grid-cols-2 gap-2 pr-1 max-h-[20rem] overflow-auto']//button[@type='button'])[5]"))
				.click(); // Select a template

		driver.findElement(By.xpath("//button[@type='button' and text()='1']")).click(); // Select number of images

		driver.findElement(By.xpath("//button[@type='submit' and text()='Generate']")).click(); // Click on Generate
																								// button

		WebDriverWait wait = new WebDriverWait(driver, 90);
		WebElement successMsg = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Recipe has finished running!')]")));

		Assert.assertTrue(successMsg.getText().contains("Recipe has finished running!"));

	}

	@Test
	public void TC_19_Check_Character_Design_Concept() throws InterruptedException, AWTException {

		driver.findElement(By.xpath("(//div[@class='max-w-full cursor-pointer'])[1]")).click();
		driver.findElement(
				By.xpath("(//div[@class='max-w-full cursor-pointer']//input[@class='bg-transparent p-2'])[1]"))
				.sendKeys("Character Design Concept");
		driver.findElement(By.xpath("//p[1][contains(text(),'Character Design Concept')]")).click(); // Mở Character
																										// Design
																										// Concept
																										// recipe page

		Thread.sleep(2000);

		driver.findElement(By.xpath(
				"(//div[@class='w-full grid grid-cols-3 gap-2 max-h-[32rem] overflow-auto']//button[@type='button'])[5]"))
				.click(); // Select a template

		driver.findElement(By.xpath("//textarea[@name='prompt']")).clear();
		driver.findElement(By.xpath("//textarea[@name='prompt']"))
				.sendKeys("1girl, 20 years old, military, colorful, intricate sharp details");

		driver.findElement(By.xpath("//textarea[@name='negative_prompt']")).clear();
		driver.findElement(By.xpath("//textarea[@name='negative_prompt']")).sendKeys("text, watermark, NSFW");

		driver.findElement(By.xpath("//button[@type='button' and text()='1']")).click(); // Select number of images

		driver.findElement(By.xpath("//button[@type='submit' and text()='Generate']")).click(); // Click on Generate
																								// button

		WebDriverWait wait = new WebDriverWait(driver, 90);
		WebElement successMsg = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Recipe has finished running!')]")));

		Assert.assertTrue(successMsg.getText().contains("Recipe has finished running!"));

	}

	@Test
	public void TC_20_Check_Sipher_Game_Avatar_Frame() throws InterruptedException, AWTException {

		driver.findElement(By.xpath("(//div[@class='max-w-full cursor-pointer'])[1]")).click();
		driver.findElement(
				By.xpath("(//div[@class='max-w-full cursor-pointer']//input[@class='bg-transparent p-2'])[1]"))
				.sendKeys("Sipher - Game Avatar Frame");
		driver.findElement(By.xpath("//p[1][contains(text(),'Sipher - Game Avatar Frame')]")).click(); // Mở Sipher -
																										// Game Avatar
																										// Frame recipe
																										// page

		Thread.sleep(2000);

		driver.findElement(By.xpath(
				"(//div[@class='w-full grid grid-cols-3 gap-2 max-h-[32rem] overflow-auto']//button[@type='button'])[5]"))
				.click(); // Select a template

		driver.findElement(By.xpath("//textarea[@name='prompt']")).clear();
		driver.findElement(By.xpath("//textarea[@name='prompt']"))
				.sendKeys("1girl, 20 years old, military, colorful, intricate sharp details");

		driver.findElement(By.xpath("//textarea[@name='negative_prompt']")).clear();
		driver.findElement(By.xpath("//textarea[@name='negative_prompt']")).sendKeys("text, watermark, NSFW");

		driver.findElement(By.xpath("//button[@type='button' and text()='1']")).click(); // Select number of images

		driver.findElement(By.xpath("//button[@type='submit' and text()='Generate']")).click(); // Click on Generate
																								// button

		WebDriverWait wait = new WebDriverWait(driver, 90);
		WebElement successMsg = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Recipe has finished running!')]")));

		Assert.assertTrue(successMsg.getText().contains("Recipe has finished running!"));

	}

	@Test
	public void TC_21_Check_Sipher_Asset_Gun() throws InterruptedException, AWTException {

		driver.findElement(By.xpath("(//div[@class='max-w-full cursor-pointer'])[1]")).click();
		driver.findElement(
				By.xpath("(//div[@class='max-w-full cursor-pointer']//input[@class='bg-transparent p-2'])[1]"))
				.sendKeys("Sipher Asset - Gun");
		driver.findElement(By.xpath("//p[1][contains(text(),'Sipher Asset - Gun')]")).click(); // Mở Sipher Asset - Gun
																								// recipe page

		Thread.sleep(2000);

		driver.findElement(By.xpath("//div[@class='line-clamp-1']//p[contains(text(),'Select Lora')]")).click();
		driver.findElement(By.xpath("//p[@class='px-2' and text()='Anime Outline v4']")).click();
		driver.findElement(By.xpath("//button[@type='button' and @title='Add lora']")).click();

		driver.findElement(By.xpath("//button[@type='button' and text()='1']")).click(); // Select number of images

		driver.findElement(By.xpath("//button[@type='submit' and text()='Generate']")).click(); // Click on Generate
																								// button

		WebDriverWait wait = new WebDriverWait(driver, 90);
		WebElement successMsg = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Recipe has finished running!')]")));

		Assert.assertTrue(successMsg.getText().contains("Recipe has finished running!"));

	}

	@Test
	public void TC_22_Check_Sipher_Game_Avatar() throws InterruptedException, AWTException {

		driver.findElement(By.xpath("(//div[@class='max-w-full cursor-pointer'])[1]")).click();
		driver.findElement(
				By.xpath("(//div[@class='max-w-full cursor-pointer']//input[@class='bg-transparent p-2'])[1]"))
				.sendKeys("Sipher - Game Avatar");
		driver.findElement(By.xpath("//p[1][contains(text(),'Sipher - Game Avatar')]")).click(); // Mở Sipher - Game
																									// Avatar recipe
																									// page

		Thread.sleep(2000);

		driver.findElement(By.xpath("//button[@type='button' and text()='1']")).click(); // Select number of images

		driver.findElement(By.xpath("//button[@type='submit' and text()='Generate']")).click(); // Click on Generate
																								// button

		WebDriverWait wait = new WebDriverWait(driver, 90);
		WebElement successMsg = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Recipe has finished running!')]")));

		Assert.assertTrue(successMsg.getText().contains("Recipe has finished running!"));

	}

	@Test
	public void TC_23_Check_Game_Asset_Hidden_Object() throws InterruptedException, AWTException {

		driver.findElement(By.xpath("(//div[@class='max-w-full cursor-pointer'])[1]")).click();
		driver.findElement(
				By.xpath("(//div[@class='max-w-full cursor-pointer']//input[@class='bg-transparent p-2'])[1]"))
				.sendKeys("Game Asset - Hidden Object");
		driver.findElement(By.xpath("//p[1][contains(text(),'Game Asset - Hidden Object')]")).click(); // Mở Game Asset
																										// - Hidden
																										// Object recipe
																										// page

		Thread.sleep(2000);

		driver.findElement(By.xpath("//button[@type='button' and text()='1']")).click(); // Select number of images

		driver.findElement(By.xpath("//button[@type='submit' and text()='Generate']")).click(); // Click on Generate
																								// button

		WebDriverWait wait = new WebDriverWait(driver, 90);
		WebElement successMsg = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Recipe has finished running!')]")));

		Assert.assertTrue(successMsg.getText().contains("Recipe has finished running!"));

	}

	@Test
	public void TC_24_Check_AI_GIF_generator() throws InterruptedException, AWTException {

		driver.findElement(By.xpath("(//div[@class='max-w-full cursor-pointer'])[1]")).click();
		driver.findElement(
				By.xpath("(//div[@class='max-w-full cursor-pointer']//input[@class='bg-transparent p-2'])[1]"))
				.sendKeys("AI GIF generator");
		driver.findElement(By.xpath("//p[1][contains(text(),'AI GIF generator')]")).click(); // Mở AI GIF generator
																								// recipe page

		Thread.sleep(2000);

		driver.findElement(By.xpath("//textarea[@name='prompt']")).clear();
		driver.findElement(By.xpath("//textarea[@name='prompt']")).sendKeys("cloudy sky");

		driver.findElement(By.xpath("//button[@type='button' and text()='3:2']")).click(); // Select aspect ratio

		driver.findElement(By.xpath("//button[@type='button' and text()='3s']")).click();

		driver.findElement(By.xpath("//button[@type='submit' and text()='Generate']")).click(); // Click on Generate
																								// button

		WebDriverWait wait = new WebDriverWait(driver, 90);
		WebElement successMsg = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Recipe has finished running!')]")));

		Assert.assertTrue(successMsg.getText().contains("Recipe has finished running!"));

	}

	@Test
	public void TC_25_Check_Art_Toy() throws InterruptedException, AWTException {

		driver.findElement(By.xpath("(//div[@class='max-w-full cursor-pointer'])[1]")).click();
		driver.findElement(
				By.xpath("(//div[@class='max-w-full cursor-pointer']//input[@class='bg-transparent p-2'])[1]"))
				.sendKeys("Art Toy");
		driver.findElement(By.xpath("//p[1][contains(text(),'Art Toy')]")).click(); // Mở Art Toy recipe page

		Thread.sleep(2000);

		WebElement delete_Icon = driver
				.findElement(By.xpath("//div[@class='absolute top-2 right-2']//button[@type='button']"));
		if (delete_Icon.isDisplayed()) {
			delete_Icon.click();
		}

		driver.findElement(By.xpath("//input[@type='file']"))
				.sendKeys(System.getProperty("user.dir") + "/src/test/resources/testdatafile/Image01.jpg"); // Upload
																											// origion
																											// face

		driver.findElement(By.xpath("//textarea[@name='prompt']")).clear();
		driver.findElement(By.xpath("//textarea[@name='prompt']")).sendKeys("cloudy sky");

		driver.findElement(By.xpath("//button[@type='button' and text()='1']")).click(); // Select number of images

		driver.findElement(By.xpath("//button[@type='submit' and text()='Generate']")).click(); // Click on Generate
																								// button

		WebDriverWait wait = new WebDriverWait(driver, 90);
		WebElement successMsg = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Recipe has finished running!')]")));

		Assert.assertTrue(successMsg.getText().contains("Recipe has finished running!"));

	}

	@Test
	public void TC_26_Check_AI_Anime_Transformer() throws InterruptedException, AWTException {

		driver.findElement(By.xpath("(//div[@class='max-w-full cursor-pointer'])[1]")).click();
		driver.findElement(
				By.xpath("(//div[@class='max-w-full cursor-pointer']//input[@class='bg-transparent p-2'])[1]"))
				.sendKeys("AI Anime Transformer");
		driver.findElement(By.xpath("//p[1][contains(text(),'AI Anime Transformer')]")).click(); // Mở AI Anime
																									// Transformer
																									// recipe page

		Thread.sleep(2000);

		driver.findElement(By.xpath("//input[@type='file']"))
				.sendKeys(System.getProperty("user.dir") + "/src/test/resources/testdatafile/Image01.jpg"); // Upload
																											// origion
																											// face

		driver.findElement(By.xpath("//button[@type='button' and text()='Manga']")).click(); // Select number of images

		driver.findElement(By.xpath("//div[@class='line-clamp-1']//p[contains(text(),'Select Lora')]")).click();
		driver.findElement(By.xpath("//p[@class='px-2' and text()='Anime Outline v4']")).click();
		driver.findElement(By.xpath("//button[@type='button' and @title='Add lora']")).click();

		driver.findElement(By.xpath("//button[@type='button' and text()='1']")).click(); // Select number of images

		driver.findElement(By.xpath("//button[@type='submit' and text()='Generate']")).click(); // Click on Generate
																								// button

		WebDriverWait wait = new WebDriverWait(driver, 90);
		WebElement successMsg = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Recipe has finished running!')]")));

		Assert.assertTrue(successMsg.getText().contains("Recipe has finished running!"));

	}

	@Test
	public void TC_27_Check_Remix() throws InterruptedException, AWTException {

		driver.findElement(By.xpath("(//div[@class='max-w-full cursor-pointer'])[1]")).click();
		driver.findElement(
				By.xpath("(//div[@class='max-w-full cursor-pointer']//input[@class='bg-transparent p-2'])[1]"))
				.sendKeys("Remix");
		driver.findElement(By.xpath("//p[1][contains(text(),'Remix')]")).click(); // Mở Remix recipe page

		Thread.sleep(2000);

		driver.findElement(By.xpath("//input[@type='file']"))
				.sendKeys(System.getProperty("user.dir") + "/src/test/resources/testdatafile/Image01.jpg"); // Upload
																											// image

		driver.findElement(By.xpath("//textarea[@name='prompt']")).sendKeys("A photo of a cat in the forest"); // Input
																												// prompt

		driver.findElement(By.xpath("//button[@type='button' and text()='Most Similar']")).click(); // Select Similarity

		driver.findElement(By.xpath("//button[@type='button' and text()='1']")).click(); // Select number of images

		driver.findElement(By.xpath("//button[@type='submit' and text()='Generate']")).click(); // Click on Generate
																								// button

		WebDriverWait wait = new WebDriverWait(driver, 90);
		WebElement successMsg = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Recipe has finished running!')]")));

		Assert.assertTrue(successMsg.getText().contains("Recipe has finished running!"));

	}

	/*
	 * @Test public void TC_28_Check_Image_Editing() throws InterruptedException,
	 * AWTException{
	 * 
	 * driver.findElement(By.xpath("(//div[@class='max-w-full cursor-pointer'])[1]")
	 * ).click(); driver.findElement(By.
	 * xpath("(//div[@class='max-w-full cursor-pointer']//input[@class='bg-transparent p-2'])[1]"
	 * )).sendKeys("Image Editing");
	 * driver.findElement(By.xpath("//p[1][contains(text(),'Image Editing')]")).
	 * click(); // Mở Image Editing recipe page
	 * 
	 * Thread.sleep(2000);
	 * 
	 * driver.findElement(By.xpath("//input[@type='file']")).sendKeys(System.
	 * getProperty("user.dir") + "/src/test/resources/testdatafile/Image01.jpg");
	 * //Upload image
	 * 
	 * Thread.sleep(2000);
	 * 
	 * driver.findElement(By.xpath("//textarea[@name='prompt']")).
	 * sendKeys("A photo of a cat in the forest"); //Input prompt
	 * 
	 * driver.findElement(By.xpath("//button[@type='submit' and text()='Generate']")
	 * ).click(); //Click on Generate button
	 * 
	 * driver.findElement(By.
	 * xpath("//div[@class='max-w-full cursor-pointer']//p[@class='px-2' and text()='Remove Object']"
	 * )).click(); //Switch to Remove Object
	 * 
	 * driver.findElement(By.xpath("//input[@type='file']")).sendKeys(System.
	 * getProperty("user.dir") + "/src/test/resources/testdatafile/Image01.jpg");
	 * //Upload image
	 * 
	 * Thread.sleep(2000);
	 * 
	 * driver.findElement(By.xpath("//button[@type='submit' and text()='Generate']")
	 * ).click(); //Click on Generate button
	 * 
	 * WebDriverWait wait = new WebDriverWait(driver, 90); WebElement
	 * successMsg_RemoveObject =
	 * wait.until(ExpectedConditions.visibilityOfElementLocated(By.
	 * xpath("//p[contains(text(),'Recipe has finished running!')]")));
	 * Assert.assertTrue(successMsg_RemoveObject.getText().
	 * contains("Recipe has finished running!"));
	 * 
	 * }
	 */

	@Test
	public void TC_29_Check_Remove_Background() throws InterruptedException, AWTException {

		driver.findElement(By.xpath("(//div[@class='max-w-full cursor-pointer'])[1]")).click();
		driver.findElement(
				By.xpath("(//div[@class='max-w-full cursor-pointer']//input[@class='bg-transparent p-2'])[1]"))
				.sendKeys("Remove Background");
		driver.findElement(By.xpath("//p[1][contains(text(),'Remove Background')]")).click(); // Mở Remove Background
																								// recipe page

		Thread.sleep(2000);

		driver.findElement(By.xpath("//input[@type='file']"))
				.sendKeys(System.getProperty("user.dir") + "/src/test/resources/testdatafile/Image01.jpg"); // Upload
																											// image

		Thread.sleep(2000);

		driver.findElement(By.xpath("//button[@type='submit' and text()='Generate']")).click(); // Click on Generate
																								// button

		WebDriverWait wait = new WebDriverWait(driver, 90);
		WebElement successMsg = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Recipe has finished running!')]")));

		Assert.assertTrue(successMsg.getText().contains("Recipe has finished running!"));
	}

	@Test
	public void TC_30_Check_Tiled_Upscaler() throws InterruptedException, AWTException {

		driver.findElement(By.xpath("(//div[@class='max-w-full cursor-pointer'])[1]")).click();
		driver.findElement(
				By.xpath("(//div[@class='max-w-full cursor-pointer']//input[@class='bg-transparent p-2'])[1]"))
				.sendKeys("Tiled Upscaler");
		driver.findElement(By.xpath("//p[1][contains(text(),'Tiled Upscaler') and not(contains(text(), 'LCM'))]"))
				.click(); // Mở Tiled Upscaler LCM page

		Thread.sleep(2000);

		driver.findElement(By.xpath("//input[@type='file']"))
				.sendKeys(System.getProperty("user.dir") + "/src/test/resources/testdatafile/Image01.jpg"); // Upload
																											// image

		Thread.sleep(2000);

		driver.findElement(By.xpath("//textarea[@name='prompt']")).clear();
		driver.findElement(By.xpath("//textarea[@name='prompt']")).sendKeys("masterpiece, best quality"); // Input
																											// prompt

		driver.findElement(By.xpath("//div[@class='line-clamp-1']//p[contains(text(),'Select Lora')]")).click();
		driver.findElement(By.xpath("//p[@class='px-2' and text()='Anime Outline v4']")).click();
		driver.findElement(By.xpath("//button[@type='button' and @title='Add lora']")).click();

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();",
				driver.findElement(By.xpath("//p[text()='Number of Images']"))); // Scroll down

		driver.findElement(By.xpath("//button[@type='button' and text()='1']")).click(); // Select number of images

		driver.findElement(By.xpath("//button[@type='submit' and text()='Generate']")).click(); // Click on Generate
																								// button

		WebDriverWait wait = new WebDriverWait(driver, 30);
		WebElement successMsg = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Recipe has finished running!')]")));

		Assert.assertTrue(successMsg.getText().contains("Recipe has finished running!"));

	}

	@Test
	public void TC_31_Check_Area_Composition() throws InterruptedException, AWTException {

		driver.findElement(By.xpath("(//div[@class='max-w-full cursor-pointer'])[1]")).click();
		driver.findElement(
				By.xpath("(//div[@class='max-w-full cursor-pointer']//input[@class='bg-transparent p-2'])[1]"))
				.sendKeys("Area Composition");
		driver.findElement(By.xpath("//p[1][contains(text(),'Area Composition')]")).click(); // Mở Area Composition
																								// recipe page

		Thread.sleep(2000);

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();",
				driver.findElement(By.xpath("//p[text()='Number of Images']"))); // Scroll down

		driver.findElement(By.xpath("//p[@class='text-atherGray-300']")).click();
		driver.findElement(
				By.xpath("(//div[@class='max-w-full cursor-pointer']//input[@class='bg-transparent p-2'])[2]"))
				.sendKeys("Anime Like 2.5D");
		driver.findElement(By.xpath("//p[@class='px-2' and contains(text(),'Anime Like 2.5D')]")).click();

		driver.findElement(By.xpath("//button[@type='button' and text()='1']")).click(); // Select number of images

		driver.findElement(By.xpath("//button[@type='submit' and text()='Generate']")).click(); // Click on Generate
																								// button

		WebDriverWait wait = new WebDriverWait(driver, 90);
		WebElement successMsg = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Recipe has finished running!')]")));

		Assert.assertTrue(successMsg.getText().contains("Recipe has finished running!"));

	}

	@Test
	public void TC_32_Check_SDXL_v1() throws InterruptedException, AWTException {

		driver.findElement(By.xpath("(//div[@class='max-w-full cursor-pointer'])[1]")).click();
		driver.findElement(
				By.xpath("(//div[@class='max-w-full cursor-pointer']//input[@class='bg-transparent p-2'])[1]"))
				.sendKeys("SDXL v1.0");
		driver.findElement(By.xpath("//p[1][contains(text(),'SDXL v1.0')]")).click(); // Mở SDXL v1.0 recipe page

		Thread.sleep(2000);

		driver.findElement(By.xpath("//div[@class='line-clamp-1']//p[text()='None']")).click();
		driver.findElement(
				By.xpath("(//div[@class='max-w-full cursor-pointer']//input[@class='bg-transparent p-2'])[2]"))
				.sendKeys("MineCraft");
		driver.findElement(By.xpath("//p[@class='px-2' and contains(text(),'Minecraft')]")).click();

		driver.findElement(By.xpath("//div[@class='line-clamp-1']//p[contains(text(),'Select Lora')]")).click();
		driver.findElement(
				By.xpath("//div[@class='max-w-full cursor-pointer flex-1']//input[@class='bg-transparent p-2']"))
				.sendKeys("Stickers Redmond SDXL");
		driver.findElement(By.xpath("//p[@class='px-2' and contains(text(),'Stickers Redmond SDXL')]")).click();

		driver.findElement(By.xpath("//button[@type='button' and @title='Add lora']")).click();

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();",
				driver.findElement(By.xpath("//p[text()='Number of Images']"))); // Scroll down
		driver.findElement(By.xpath("//button[@type='button' and text()='1']")).click(); // Select number of images

		driver.findElement(By.xpath("//button[@type='submit' and text()='Generate']")).click(); // Click on Generate
																								// button

		WebDriverWait wait = new WebDriverWait(driver, 90);
		WebElement successMsg = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Recipe has finished running!')]")));

		Assert.assertTrue(successMsg.getText().contains("Recipe has finished running!"));

	}

	@Test
	public void TC_33_Check_Image_To_GIF() throws InterruptedException, AWTException {

		driver.findElement(By.xpath("(//div[@class='max-w-full cursor-pointer'])[1]")).click();
		driver.findElement(
				By.xpath("(//div[@class='max-w-full cursor-pointer']//input[@class='bg-transparent p-2'])[1]"))
				.sendKeys("Image To GIF");
		driver.findElement(By.xpath("//p[1][contains(text(),'Image To GIF')]")).click(); // Mở Image To GIF recipe page

		Thread.sleep(2000);

		driver.findElement(By.xpath("//input[@type='file']"))
				.sendKeys(System.getProperty("user.dir") + "/src/test/resources/testdatafile/Image01.jpg"); // Upload
																											// image

		driver.findElement(By.xpath("//button[@type='submit' and text()='Generate']")).click(); // Click on Generate
																								// button

		WebDriverWait wait = new WebDriverWait(driver, 90);
		WebElement successMsg = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Recipe has finished running!')]")));

		Assert.assertTrue(successMsg.getText().contains("Recipe has finished running!"));

	}

	@Test
	public void TC_34_Check_Hand_Detailer() throws InterruptedException, AWTException {

		driver.findElement(By.xpath("(//div[@class='max-w-full cursor-pointer'])[1]")).click();
		driver.findElement(
				By.xpath("(//div[@class='max-w-full cursor-pointer']//input[@class='bg-transparent p-2'])[1]"))
				.sendKeys("Hand Detailer");
		driver.findElement(By.xpath("//p[1][contains(text(),'Hand Detailer')]")).click(); // Mở Image To GIF recipe page

		Thread.sleep(2000);

		driver.findElement(By.xpath("//input[@type='file']"))
				.sendKeys(System.getProperty("user.dir") + "/src/test/resources/testdatafile/Image01.jpg"); // Upload
																											// image

		driver.findElement(By.xpath("//button[@type='button' and text()='1']")).click(); // Select number of images

		driver.findElement(By.xpath("//button[@type='submit' and text()='Generate']")).click(); // Click on Generate
																								// button

		WebDriverWait wait = new WebDriverWait(driver, 90);
		WebElement successMsg = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Recipe has finished running!')]")));

		Assert.assertTrue(successMsg.getText().contains("Recipe has finished running!"));

	}

	@Test
	public void TC_35_Check_Face_Detailer() throws InterruptedException, AWTException {

		driver.findElement(By.xpath("(//div[@class='max-w-full cursor-pointer'])[1]")).click();
		driver.findElement(
				By.xpath("(//div[@class='max-w-full cursor-pointer']//input[@class='bg-transparent p-2'])[1]"))
				.sendKeys("Face Detailer");
		driver.findElement(By.xpath("//p[1][contains(text(),'Face Detailer')]")).click(); // Mở Face Detailer recipe
																							// page

		Thread.sleep(2000);

		driver.findElement(By.xpath("//input[@type='file']"))
				.sendKeys(System.getProperty("user.dir") + "/src/test/resources/testdatafile/Image01.jpg"); // Upload
																											// image

		driver.findElement(By.xpath("//textarea[@name='prompt']")).clear();
		driver.findElement(By.xpath("//textarea[@name='prompt']"))
				.sendKeys("Original prompt to create the image above or prompt for new face"); // Input prompt

		driver.findElement(By.xpath("//button[@type='button' and text()='1']")).click(); // Select number of images

		driver.findElement(By.xpath("//button[@type='submit' and text()='Generate']")).click(); // Click on Generate
																								// button

		WebDriverWait wait = new WebDriverWait(driver, 90);
		WebElement successMsg = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Recipe has finished running!')]")));

		Assert.assertTrue(successMsg.getText().contains("Recipe has finished running!"));

	}

	@Test
	public void TC_36_Check_AI_Image_Generator() throws InterruptedException, AWTException {

		driver.findElement(By.xpath("//p[1][contains(text(),'AI Image Generator') and not(contains(text(), 'LCM'))]"))
				.click(); // Mở AI Image Generator LCM page

		Thread.sleep(2000);

		// driver.findElement(By.xpath("//textarea[@name='prompt']")).sendKeys("A photo
		// of a cat in the forest"); //Input prompt
		// driver.findElement(By.xpath("//textarea[@name='negative_prompt']")).sendKeys("A
		// photo of a cat in the forest"); //Input prompt

		// Thread.sleep(2000);

		driver.findElement(By.xpath("//div[@class='line-clamp-1']//p[contains(text(),'Select Lora')]")).click();
		driver.findElement(By.xpath("//p[@class='px-2' and text()='Anime Outline v4']")).click();
		driver.findElement(By.xpath("//button[@type='button' and @title='Add lora']")).click();

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();",
				driver.findElement(By.xpath("//p[text()='Number of Images']"))); // Scroll down

		driver.findElement(By.xpath("//button[@type='button' and text()='2']")).click(); // Select number of images

		driver.findElement(By.xpath("//button[@type='submit' and text()='Generate']")).click(); // Click on Generate
																								// button

		WebDriverWait wait = new WebDriverWait(driver, 50);
		WebElement successMsg = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Recipe has finished running!')]")));

		Assert.assertTrue(successMsg.getText().contains("Recipe has finished running!"));

	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}
