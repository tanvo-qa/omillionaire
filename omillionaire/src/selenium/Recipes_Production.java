package selenium;

import org.testng.annotations.*;

import java.awt.AWTException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class Recipes_Production {

	public WebDriver driver;

	private String HOOK_URL = "https://hooks.slack.com/services/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";

	@BeforeClass
	public void beforeClass() throws InterruptedException {

		String driverPath = "/Users/tanhungvo/Documents/SeleniumWebDrider/chromedriver-mac-arm64/chromedriver";
		System.setProperty("webdriver.chrome.driver", driverPath);
		driver = new ChromeDriver();

		driver.get("https://artventure.ai/recipes");
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

	private void assertRecipeStatusCompleted(String recipeName) throws Exception {
		
		try {
			WebElement status = driver.findElement(By.xpath("//p[contains(text(),'Recipe completed')]"));
			if (status != null) {
				sendSlackMessage("Recipe " + recipeName + " completed");
			} else {
				sendSlackMessage("Recipe " + recipeName + " failed");
			}
				
		} catch (Exception e) {
			
			sendSlackMessage("Recipe " + recipeName + " failed");
		}
		
	}

	private void sendSlackMessage(String msg) throws Exception {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(HOOK_URL);

		try {
			String json = "{ \"text\" : \"" + msg + "\" }";
			StringEntity entity = new StringEntity(json);
			httpPost.setEntity(entity);
			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-type", "application/json");
			client.execute(httpPost);
			client.close();
		} catch (UnsupportedEncodingException e) {
			throw new Exception("Error sending slack message", e);
		} catch (ClientProtocolException e) {
			throw new Exception("Error sending slack message", e);
		} catch (IOException e) {
			throw new Exception("Error sending slack message", e);
		}
	}

	@Test
	public void TC_01_Check_Outpainting() throws InterruptedException, AWTException, Exception {

		driver.findElement(By.xpath("//p[1][contains(text(),'Outpainting') and not(contains(text(), 'LCM'))]")).click(); // Mở Outpainting page

		Thread.sleep(5000);

		driver.findElement(By.xpath("//input[@type='file']")).sendKeys(System.getProperty("user.dir") + "/src/test/resources/testdatafile/Image01.jpg"); // Upload image

		Thread.sleep(2000);

		driver.findElement(By.xpath("//textarea[@name='prompt']")).sendKeys("A photo of a cat in the forest"); // Input prompt

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();", driver.findElement(By.xpath("//p[text()='Number of Images']"))); // Scroll down

		driver.findElement(By.xpath("//button[@type='button' and text()='1']")).click(); // Select number of images

		driver.findElement(By.xpath("//button[@type='submit' and text()='Generate']")).click(); // Click on Generate button

		driver.findElement(By.xpath("//div[@role='alert']")).click(); // Close "ArtVenture is running your recipe!" alert

		WebDriverWait wait = new WebDriverWait(driver, 90);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Recipe has finished running!')]")));

//		Assert.assertEquals(driver.findElement(By.xpath("//p[contains(text(),'Recipe completed')]")).getText(), "Recipe completed");
		assertRecipeStatusCompleted("Outpainting");

		driver.findElement(By.xpath("//div[@role='alert']")).click(); // Close "Recipe completed!" alert
	}
	
	@Test 
	public void TC_02_Check_Sketch_To_Image() throws InterruptedException, AWTException, Exception {
	 
		//Search and select "Sketch To Image" recipe
		driver.findElement(By.xpath("(//div[@class='max-w-full cursor-pointer'])[1]")).click(); 
		driver.findElement(By.xpath("(//div[@class='max-w-full cursor-pointer']//input[@class='bg-transparent p-2'])[1]")).sendKeys("Sketch To Image");
		driver.findElement(By.xpath("//p[1][contains(text(),'Sketch To Image')]")).click(); // Open Sketch To Image page
	 
		//driver.findElement(By.xpath("//p[1][contains(text(),'Sketch To Image')]")).click(); // Mở Outpainting page
		 
		Thread.sleep(2000);
		driver.findElement(By.xpath("//input[@type='file']")).sendKeys(System.getProperty("user.dir") + "/src/test/resources/testdatafile/Image01.jpg"); //Upload image
	 
		Thread.sleep(2000);
	 
		driver.findElement(By.xpath("//textarea[@name='prompt']")).sendKeys("A photo of a cat in the forest"); //Input prompt
	 
		Thread.sleep(2000);
	 
		driver.findElement(By.xpath("//div[@class='line-clamp-1']//p[contains(text(),'Select Lora')]")).click(); driver.findElement(By.xpath("//p[@class='px-2' and text()='Anime Outline v4']")).click();
		driver.findElement(By.xpath("//button[@type='button' and @title='Add lora']")).click();
	 
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();", driver.findElement(By.xpath("//p[text()='Number of Images']"))); //Scroll down
	 
		driver.findElement(By.xpath("//button[@type='button' and text()='1']")).click(); //Select number of images
		driver.findElement(By.xpath("//button[@type='submit' and text()='Generate']")).click(); //Click on Generate button
	 
		driver.findElement(By.xpath("//div[@role='alert']")).click(); // Close "ArtVenture is running your recipe!" alert
	 
		WebDriverWait wait = new WebDriverWait(driver, 90);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Recipe has finished running!')]")));
	  
		//Assert.assertEquals(driver.findElement(By.xpath("//p[contains(text(),'Recipe completed')]")).getText(), "Recipe completed");
		assertRecipeStatusCompleted("Sketch To Image");
	 
		driver.findElement(By.xpath("//div[@role='alert']")).click(); // Close "Recipe completed!" alert 
	}
	 
	  
	@Test 
	public void TC_03_Check_Face_Swap() throws InterruptedException, AWTException, Exception {
	  
		driver.findElement(By.xpath("(//div[@class='max-w-full cursor-pointer'])[1]")).click(); 
		driver.findElement(By.xpath("(//div[@class='max-w-full cursor-pointer']//input[@class='bg-transparent p-2'])[1]")).sendKeys("Face Swap"); 
		
		Thread.sleep(2000);
		
		driver.findElement(By.xpath("//p[1][contains(text(),'Face Swap')]")).click();// Mở Face Swap page
	  
		Thread.sleep(2000);
	  
		driver.findElement(By.xpath("(//div[@class='space-y-6']//input[@type='file'])[1]")).sendKeys(System.getProperty("user.dir") + "/src/test/resources/testdatafile/Image01.jpg"); //Upload origion face
	  
		driver.findElement(By.xpath("(//div[@class='space-y-6']//input[@type='file'])[2]")).sendKeys(System.getProperty("user.dir") + "/src/test/resources/testdatafile/Image02.jpg"); //Upload face image
	  
		Thread.sleep(2000);
	  
		driver.findElement(By.xpath("//button[@type='submit' and text()='Generate']")).click(); //Click on Generate button
	  
		driver.findElement(By.xpath("//div[@role='alert']")).click(); // Close "ArtVenture is running your recipe!" alert
	  
		WebDriverWait wait = new WebDriverWait(driver, 90);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Recipe has finished running!')]")));
	  
		//Assert.assertEquals(driver.findElement(By.xpath("//p[contains(text(),'Recipe completed')]")).getText(), "Recipe completed");
		assertRecipeStatusCompleted("Face Swap");
		
		driver.findElement(By.xpath("//div[@role='alert']")).click(); // Close "Recipe completed!" alert 
	}
	  
	@Test 
	public void TC_04_Check_Image_To_Image() throws InterruptedException, AWTException, Exception	{
	  
		driver.findElement(By.xpath("(//div[@class='max-w-full cursor-pointer'])[1]")).click(); 
		driver.findElement(By.xpath("(//div[@class='max-w-full cursor-pointer']//input[@class='bg-transparent p-2'])[1]")).sendKeys("Image To Image");
		driver.findElement(By.xpath("//p[1][contains(text(),'Image To Image')]")).click(); // Mở Image To Image recipe page

		Thread.sleep(2000);

		driver.findElement(By.xpath("(//div[@class='space-y-6']//input[@type='file'])[1]")).sendKeys(System.getProperty("user.dir") + "/src/test/resources/testdatafile/Image01.jpg"); //Upload image to generate

		driver.findElement(By.xpath("//textarea[@name='prompt']")).clear();
		driver.findElement(By.xpath("//textarea[@name='prompt']")).sendKeys("masterpiece, best quality"); //Input prompt

		driver.findElement(By.xpath("//div[@class='line-clamp-1']//p[contains(text(),'Select Lora')]")).click(); 
		driver.findElement(By.xpath("//p[@class='px-2' and text()='Anime Outline v4']")).click();
		driver.findElement(By.xpath("//button[@type='button' and @title='Add lora']")).click();

		driver.findElement(By.xpath("//button[@type='button' and text()='1']")).click(); //Select number of images

		driver.findElement(By.xpath("//button[@type='submit' and text()='Generate']")).click(); //Click on Generate button

		driver.findElement(By.xpath("//div[@role='alert']")).click(); // Close "ArtVenture is running your recipe!" alert

		WebDriverWait wait = new WebDriverWait(driver, 90);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Recipe has finished running!')]")));

		//Assert.assertEquals(driver.findElement(By.xpath("//p[contains(text(),'Recipe completed')]")).getText(),"Recipe completed");
		assertRecipeStatusCompleted("Image To Image");
		
		driver.findElement(By.xpath("//div[@role='alert']")).click(); // Close "Recipe completed!" alert
	  
	}
	  
	@Test 
	public void TC_05_Check_Upscaler() throws InterruptedException, AWTException, Exception{
	  
		driver.findElement(By.xpath("(//div[@class='max-w-full cursor-pointer'])[1]")).click(); 
		driver.findElement(By.xpath("(//div[@class='max-w-full cursor-pointer']//input[@class='bg-transparent p-2'])[1]")).sendKeys("Upscaler");
		driver.findElement(By.xpath("//p[1][contains(text(),'Upscaler')]")).click();// Mở Upscaler recipe page
	  
		Thread.sleep(2000);
	  
		driver.findElement(By.xpath("(//input[@type='file'])")).sendKeys(System.getProperty("user.dir") + "/src/test/resources/testdatafile/Image01.jpg");//Upload image to generate
	  
		Thread.sleep(2000);
	  
		driver.findElement(By.xpath("//button[@type='submit' and text()='Generate']")).click(); //Click on Generate button
	  
		driver.findElement(By.xpath("//div[@role='alert']")).click(); // Close	"ArtVenture is running your recipe!" alert
	  
		WebDriverWait wait = new WebDriverWait(driver, 90);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Recipe has finished running!')]")));
	  
		//Assert.assertEquals(driver.findElement(By.xpath("//p[contains(text(),'Recipe completed')]")).getText(),"Recipe completed");
		assertRecipeStatusCompleted("Upscaler");
		
		driver.findElement(By.xpath("//div[@role='alert']")).click(); // Close "Recipe completed!" alert
	  
	}
	  
	@Test 
	public void TC_06_Check_Miss_Universe() throws InterruptedException, AWTException, Exception	{
	  
		driver.findElement(By.xpath("(//div[@class='max-w-full cursor-pointer'])[1]")).click();
		driver.findElement(By.xpath("(//div[@class='max-w-full cursor-pointer']//input[@class='bg-transparent p-2'])[1]")).sendKeys("Miss Universe");
		driver.findElement(By.xpath("//p[1][contains(text(),'Miss Universe')]")).click(); // Mở Miss Universe recipe page
	  
		Thread.sleep(2000);
	  
		driver.findElement(By.xpath("(//input[@type='file'])")).sendKeys(System.getProperty("user.dir") + "/src/test/resources/testdatafile/Image01.jpg");//Upload image to generate
	  
		Thread.sleep(2000);
	  
		driver.findElement(By.xpath("(//div[@class='w-full grid grid-cols-2 gap-2 pr-1 max-h-[20rem] overflow-auto']//button[@type='button'])[5]")).click(); //Select a template
	  
		//driver.findElement(By.xpath("//input[(@type='checkbox') and (@name='Or create your own template')]")).click(); //Turn on your own template check-box
	  
		driver.findElement(By.xpath("//button[@type='button' and text()='1']")).click(); //Select number of images
	  
		driver.findElement(By.xpath("//button[@type='submit' and text()='Generate']")).click(); //Click on Generate button
	  
		driver.findElement(By.xpath("//div[@role='alert']")).click(); // Close "ArtVenture is running your recipe!" alert
	  
		WebDriverWait wait = new WebDriverWait(driver, 90);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Recipe has finished running!')]")));
	  
		//Assert.assertEquals(driver.findElement(By.xpath("//p[contains(text(),'Recipe completed')]")).getText(), "Recipe completed");
		assertRecipeStatusCompleted("Miss Universe");
		
		driver.findElement(By.xpath("//div[@role='alert']")).click(); // Close "Recipe completed!" alert 
	}
	  
	@Test 
	public void TC_07_Check_Spookifyme() throws InterruptedException, AWTException, Exception	{
	  
		driver.findElement(By.xpath("(//div[@class='max-w-full cursor-pointer'])[1]")).click(); 
		driver.findElement(By.xpath("(//div[@class='max-w-full cursor-pointer']//input[@class='bg-transparent p-2'])[1]")).sendKeys("Spookifyme");
		driver.findElement(By.xpath("//p[1][contains(text(),'Spookifyme')]")).click(); // Mở Spookifyme recipe page
	  
		Thread.sleep(2000);
	  
		driver.findElement(By.xpath("(//input[@type='file'])")).sendKeys(System.getProperty("user.dir") + "/src/test/resources/testdatafile/Image01.jpg");//Upload image to generate
	  
		Thread.sleep(2000);
	  
		driver.findElement(By.xpath("//button[@type='button' and contains(text(),'Male')]")).click();//Select a gender
	  
		driver.findElement(By.xpath("//button[@type='button' and contains(text(),'Middle-Aged Adults (over 31-45)')]")).click(); //Select a age
	  
		driver.findElement(By.xpath("(//div[@class='w-full grid grid-cols-2 gap-2 pr-1 max-h-[20rem] overflow-auto']//button[@type='button'])[12]")).click(); //Select a custome theme
	  
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();", driver.findElement(By.xpath("//p[text()='Number of Images']"))); //Scrolldown
	  
		driver.findElement(By.xpath("//button[@type='button' and text()='1']")).click(); //Select number of images
	  
		driver.findElement(By.xpath("//button[@type='submit' and text()='Generate']")).click(); //Click on Generate button
	  
		driver.findElement(By.xpath("//div[@role='alert']")).click(); // Close "ArtVenture is running your recipe!" alert
	  
		WebDriverWait wait = new WebDriverWait(driver, 90);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Recipe has finished running!')]")));
	  
		//Assert.assertEquals(driver.findElement(By.xpath("//p[contains(text(),'Recipe completed')]")).getText(),"Recipe completed");
		assertRecipeStatusCompleted("Spookifyme");
		
		driver.findElement(By.xpath("//div[@role='alert']")).click(); // Close "Recipe completed!" alert
	  
	}
	  
	@Test 
	public void TC_08_Check_AI_Yearbook() throws InterruptedException, AWTException, Exception	{
	  
		driver.findElement(By.xpath("(//div[@class='max-w-full cursor-pointer'])[1]")).click(); 
		driver.findElement(By.xpath("(//div[@class='max-w-full cursor-pointer']//input[@class='bg-transparent p-2'])[1]")).sendKeys("AI Yearbook");
		driver.findElement(By.xpath("//p[1][contains(text(),'AI Yearbook')]")).click(); // Mở AI Yearbook recipe page
	  
		Thread.sleep(2000);
	  
		driver.findElement(By.xpath("(//input[@type='file'])")).sendKeys(System.getProperty("user.dir") + "/src/test/resources/testdatafile/Image01.jpg");//Upload image to generate
	  
		Thread.sleep(2000);
	  
		driver.findElement(By.xpath("(//div[@class='w-full grid grid-cols-2 gap-2 pr-1 max-h-[20rem] overflow-auto']//button[@type='button'])[5]")).click(); //Select a template
	  
	  
		driver.findElement(By.xpath("//button[@type='button' and text()='1']")).click(); //Select number of images
	  
		driver.findElement(By.xpath("//button[@type='submit' and text()='Generate']")).click(); //Click on Generate button
	  
		driver.findElement(By.xpath("//div[@role='alert']")).click(); // Close "ArtVenture is running your recipe!" alert
	  
		WebDriverWait wait = new WebDriverWait(driver, 120);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Recipe has finished running!')]")));
	  
		//Assert.assertEquals(driver.findElement(By.xpath("//p[contains(text(),'Recipe completed')]")).getText(),"Recipe completed");
		assertRecipeStatusCompleted("AI Yearbook");
		
		driver.findElement(By.xpath("//div[@role='alert']")).click();; // Close "Recipe completed!" alert 
	}
	  
	@Test 
	public void TC_09_Check_AI_Mixer() throws InterruptedException, AWTException, Exception {
	  
		driver.findElement(By.xpath("(//div[@class='max-w-full cursor-pointer'])[1]")).click(); 
		driver.findElement(By.xpath("(//div[@class='max-w-full cursor-pointer']//input[@class='bg-transparent p-2'])[1]")).sendKeys("AI Mixer");
		driver.findElement(By.xpath("//p[1][contains(text(),'AI Mixer')]")).click();// Mở AI Mixer recipe page
	  
		Thread.sleep(2000);
	  
		driver.findElement(By.xpath("(//div[@class='space-y-6']//input[@type='file'])[1]")).sendKeys(System.getProperty("user.dir") + "/src/test/resources/testdatafile/Image01.jpg");//Upload origion face
	  
		driver.findElement(By.xpath("(//div[@class='space-y-6']//input[@type='file'])[2]")).sendKeys(System.getProperty("user.dir") + "/src/test/resources/testdatafile/Image02.jpg");//Upload face image
	  
		Thread.sleep(2000);
	  
		driver.findElement(By.xpath("//textarea[@name='prompt']")).clear(); //Clear text in field
	  
		driver.findElement(By.xpath("//textarea[@name='prompt']")).sendKeys("1 girl, (solo), (wearing Bat girl suit without mask),looking at viewer,"); //Input prompt
	  
		driver.findElement(By.xpath("//div[@class='line-clamp-1']//p[contains(text(),'Select Lora')]")).click(); 
		driver.findElement(By.xpath("//p[@class='px-2' and text()='Anime Outline v4']")).click();
		driver.findElement(By.xpath("//button[@type='button' and @title='Add lora']")).click();
	  
		driver.findElement(By.xpath("//button[@type='button' and text()='3:2']")).click(); //Select Aspect Ratio
	  
		driver.findElement(By.xpath("//button[@type='button' and text()='1']")).click(); //Select number of images
	  
		driver.findElement(By.xpath("//button[@type='submit' and text()='Generate']")).click(); //Click on Generate button
	  
		driver.findElement(By.xpath("//div[@role='alert']")).click(); // Close "ArtVenture is running your recipe!" alert
	  
		WebDriverWait wait = new WebDriverWait(driver, 90);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Recipe has finished running!')]")));
	  
		//Assert.assertEquals(driver.findElement(By.xpath("//p[contains(text(),'Recipe completed')]")).getText(),"Recipe completed");
		assertRecipeStatusCompleted("AI Yearbook");
		
		driver.findElement(By.xpath("//div[@role='alert']")).click(); // Close "Recipe completed!" alert 
	}
	  
	@Test 
	public void TC_10_Check_Logo_Art() throws InterruptedException, AWTException, Exception{
	  
		driver.findElement(By.xpath("(//div[@class='max-w-full cursor-pointer'])[1]")).click(); 
	  	driver.findElement(By.xpath("(//div[@class='max-w-full cursor-pointer']//input[@class='bg-transparent p-2'])[1]")).sendKeys("Logo Art");
	  	driver.findElement(By.xpath("//p[1][contains(text(),'Logo Art')]")).click();// Mở Logo Art recipe page
	  
	  	Thread.sleep(2000);
	  
	  	driver.findElement(By.xpath("//input[@type='file']")).sendKeys(System.getProperty("user.dir") + "/src/test/resources/testdatafile/Image01.jpg");//Upload origion face
	  
	  	Thread.sleep(2000);
	  
	  	driver.findElement(By.xpath("(//div[@class='w-full grid grid-cols-2 gap-2 pr-1 max-h-[20rem] overflow-auto']//button[@type='button'])[5]")).click(); //Select a template
	  
	  	driver.findElement(By.xpath("//button[@type='button' and text()='1']")).click(); //Select number of images
	  
	  	driver.findElement(By.xpath("//button[@type='submit' and text()='Generate']")).click(); //Click on Generate button
	  
	  	driver.findElement(By.xpath("//div[@role='alert']")).click(); // Close "ArtVenture is running your recipe!" alert
	  
	  	WebDriverWait wait = new WebDriverWait(driver, 90);
	  	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Recipe has finished running!')]")));
	  
	  	//Assert.assertEquals(driver.findElement(By.xpath("//p[contains(text(),'Recipe completed')]")).getText(), "Recipe completed");
	  	assertRecipeStatusCompleted("Logo Art");
	  	
	  	driver.findElement(By.xpath("//div[@role='alert']")).click(); // Close "Recipe completed!" alert 
	}
	  
	@Test 
	public void TC_11_Check_Commercial_Photoshoot() throws InterruptedException, AWTException, Exception	{
		driver.findElement(By.xpath("(//div[@class='max-w-full cursor-pointer'])[1]")).click(); 
		driver.findElement(By.xpath("(//div[@class='max-w-full cursor-pointer']//input[@class='bg-transparent p-2'])[1]")).sendKeys("Commercial Photoshoot"); 
		driver.findElement(By.xpath("//p[1][contains(text(),'Commercial Photoshoot')]")).click(); // Mở Commercial Photoshoot recipe page
	  
		Thread.sleep(2000);
	  
		driver.findElement(By.xpath("//input[@type='file']")).sendKeys(System.getProperty("user.dir") + "/src/test/resources/testdatafile/Image01.jpg");//Upload origion face
	  
		Thread.sleep(2000);
	  
		driver.findElement(By.xpath("(//div[@class='w-full grid grid-cols-2 gap-2 pr-1 max-h-[20rem] overflow-auto']//button[@type='button'])[5]")).click(); //Select a template
	  
		driver.findElement(By.xpath("//button[@type='button' and text()='1']")).click(); //Select number of images
	  
		driver.findElement(By.xpath("//button[@type='submit' and text()='Generate']")).click(); //Click on Generate button
	  
		driver.findElement(By.xpath("//div[@role='alert']")).click(); // Close "ArtVenture is running your recipe!" alert
	  
		WebDriverWait wait = new WebDriverWait(driver, 90);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Recipe has finished running!')]")));
	  
		//Assert.assertEquals(driver.findElement(By.xpath("//p[contains(text(),'Recipe completed')]")).getText(), "Recipe completed");
		assertRecipeStatusCompleted("Commercial Photoshoot");
		
		driver.findElement(By.xpath("//div[@role='alert']")).click(); // Close "Recipe completed!" alert 
	}
	  
	@Test 
	public void TC_12_Check_Create_QR_Code_with_AI() throws InterruptedException, AWTException, Exception	{
	  
		driver.findElement(By.xpath("(//div[@class='max-w-full cursor-pointer'])[1]")).click(); 
		driver.findElement(By.xpath("(//div[@class='max-w-full cursor-pointer']//input[@class='bg-transparent p-2'])[1]")).sendKeys("Create QR Code with AI"); 
	  	driver.findElement(By.xpath("//p[1][contains(text(),'Create QR Code with AI')]")).click(); // Mở Create QR Code with AI recipe page
	  
	  	Thread.sleep(2000);
	  
	  	driver.findElement(By.xpath("//textarea[@name='url']")).clear();
	  	driver.findElement(By.xpath("//textarea[@name='url']")).sendKeys("https://artventure.sipher.gg/recipes/ai-qr-code");
	  
	  	driver.findElement(By.xpath("(//div[@class='w-full grid grid-cols-2 gap-2 pr-1 max-h-[20rem] overflow-auto']//button[@type='button'])[5]")).click(); //Select a template
	  
	  	driver.findElement(By.xpath("//button[@type='button' and text()='1']")).click(); //Select number of images
	  
	  	driver.findElement(By.xpath("//button[@type='submit' and text()='Generate']")).click(); //Click on Generate button
	  
	  	driver.findElement(By.xpath("//div[@role='alert']")).click(); // Close "ArtVenture is running your recipe!" alert
	  
	  	WebDriverWait wait = new WebDriverWait(driver, 90);
	  	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Recipe has finished running!')]")));
	  
	  	//Assert.assertEquals(driver.findElement(By.xpath("//p[contains(text(),'Recipe completed')]")).getText(),"Recipe completed");
	  	assertRecipeStatusCompleted("Create QR Code with AI");
	  	
	  	driver.findElement(By.xpath("//div[@role='alert']")).click(); // Close "Recipe completed!" alert 
	}

	@Test 
	public void TC_13_Check_Character_Design_Concept() throws InterruptedException, AWTException, Exception	{
	  
		driver.findElement(By.xpath("(//div[@class='max-w-full cursor-pointer'])[1]")).click(); 
		driver.findElement(By.xpath("(//div[@class='max-w-full cursor-pointer']//input[@class='bg-transparent p-2'])[1]")).sendKeys("Character Design Concept"); 
		driver.findElement(By.xpath("//p[1][contains(text(),'Character Design Concept')]")).click(); // Mở Character Design Concept recipe page
	  
		Thread.sleep(2000);
	  
		driver.findElement(By.xpath("(//div[@class='w-full grid grid-cols-3 gap-2 max-h-[32rem] overflow-auto']//button[@type='button'])[5]")).click(); //Select a template
	  
		driver.findElement(By.xpath("//textarea[@name='prompt']")).clear();
		driver.findElement(By.xpath("//textarea[@name='prompt']")).sendKeys("1girl, 20 years old, military, colorful, intricate sharp details");
	  
		driver.findElement(By.xpath("//textarea[@name='negative_prompt']")).clear();
		driver.findElement(By.xpath("//textarea[@name='negative_prompt']")).
		sendKeys("text, watermark, NSFW");
	  
		driver.findElement(By.xpath("//button[@type='button' and text()='1']")).click(); //Select number of images
	  
		driver.findElement(By.xpath("//button[@type='submit' and text()='Generate']")).click(); //Click on Generate button
	  
		driver.findElement(By.xpath("//div[@role='alert']")).click(); // Close "ArtVenture is running your recipe!" alert
	  
		WebDriverWait wait = new WebDriverWait(driver, 90);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Recipe has finished running!')]")));
	  
		//Assert.assertEquals(driver.findElement(By.xpath("//p[contains(text(),'Recipe completed')]")).getText(),"Recipe completed");
		assertRecipeStatusCompleted("Character Design Concept");
		driver.findElement(By.xpath("//div[@role='alert']")).click(); // Close "Recipe completed!" alert 
	}
	  
	@Test 
	public void TC_14_Check_Sipher_Game_Avatar_Frame() throws InterruptedException, AWTException, Exception {
	  
		driver.findElement(By.xpath("(//div[@class='max-w-full cursor-pointer'])[1]")).click(); 
		driver.findElement(By.xpath("(//div[@class='max-w-full cursor-pointer']//input[@class='bg-transparent p-2'])[1]")).sendKeys("Sipher - Game Avatar Frame"); 
		driver.findElement(By.xpath("//p[1][contains(text(),'Sipher - Game Avatar Frame')]")).click(); //Mở Sipher - Game Avatar Frame recipe page
	  
		Thread.sleep(2000);
		//driver.findElement(By.xpath("(//div[@class='w-full grid grid-cols-3 gap-2 max-h-[32rem] overflow-auto']//button[@type='button'])[5]")).click(); //Select a template
	  
		driver.findElement(By.xpath("//textarea[@name='prompt']")).clear();
		driver.findElement(By.xpath("//textarea[@name='prompt']")).sendKeys("1girl, 20 years old, military, colorful, intricate sharp details");
	  
		driver.findElement(By.xpath("//textarea[@name='negative_prompt']")).clear();
		driver.findElement(By.xpath("//textarea[@name='negative_prompt']")).sendKeys("text, watermark, NSFW");
	  
		driver.findElement(By.xpath("//button[@type='button' and text()='1']")).click(); //Select number of images
	  
		driver.findElement(By.xpath("//button[@type='submit' and text()='Generate']")).click(); //Click on Generate button
	  
		driver.findElement(By.xpath("//div[@role='alert']")).click(); // Close "ArtVenture is running your recipe!" alert
	  
		WebDriverWait wait = new WebDriverWait(driver, 120);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Recipe has finished running!')]")));
	  
		//Assert.assertEquals(driver.findElement(By.xpath("//p[contains(text(),'Recipe completed')]")).getText(),"Recipe completed");
		assertRecipeStatusCompleted("Sipher - Game Avatar Frame");
		
		driver.findElement(By.xpath("//div[@role='alert']")).click(); // Close "Recipe completed!" alert 
	}
	  
	@Test 
	public void TC_15_Check_Sipher_Asset_Gun() throws InterruptedException, AWTException, Exception	{
	  
		driver.findElement(By.xpath("(//div[@class='max-w-full cursor-pointer'])[1]")).click(); 
		driver.findElement(By.xpath("(//div[@class='max-w-full cursor-pointer']//input[@class='bg-transparent p-2'])[1]")).sendKeys("Sipher Asset - Gun");
		driver.findElement(By.xpath("//p[1][contains(text(),'Sipher Asset - Gun')]")).click(); // Mở Sipher Asset - Gun recipe page
	  
		Thread.sleep(2000);
	  
		driver.findElement(By.xpath("//div[@class='line-clamp-1']//p[contains(text(),'Select Lora')]")).click(); 
		driver.findElement(By.xpath("//p[@class='px-2' and text()='Anime Outline v4']")).click();
		driver.findElement(By.xpath("//button[@type='button' and @title='Add lora']")).click();
	  
		driver.findElement(By.xpath("//button[@type='button' and text()='1']")).click(); //Select number of images
	  
		driver.findElement(By.xpath("//button[@type='submit' and text()='Generate']")).click(); //Click on Generate button
	  
		driver.findElement(By.xpath("//div[@role='alert']")).click(); // Close "ArtVenture is running your recipe!" alert
	  
		WebDriverWait wait = new WebDriverWait(driver, 90);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Recipe has finished running!')]")));
	  
		//Assert.assertEquals(driver.findElement(By.xpath("//p[contains(text(),'Recipe completed')]")).getText(),"Recipe completed");
		assertRecipeStatusCompleted("Sipher Asset - Gun");
		
		driver.findElement(By.xpath("//div[@role='alert']")).click(); // Close "Recipe completed!" alert 
	}
	  
	@Test 
	public void TC_16_Check_Sipher_Game_Avatar() throws InterruptedException, AWTException, Exception	{
	  
		driver.findElement(By.xpath("(//div[@class='max-w-full cursor-pointer'])[1]")).click(); 
		driver.findElement(By.xpath("(//div[@class='max-w-full cursor-pointer']//input[@class='bg-transparent p-2'])[1]")).sendKeys("Sipher - Game Avatar");
		driver.findElement(By.xpath("//p[1][contains(text(),'Sipher - Game Avatar')]")).click(); // Mở Sipher - Game Avatar recipe page
	  
		Thread.sleep(2000);
	  
		driver.findElement(By.xpath("//button[@type='button' and text()='1']")).click(); //Select number of images
	  
		driver.findElement(By.xpath("//button[@type='submit' and text()='Generate']")).click(); //Click on Generate button
	  
		driver.findElement(By.xpath("//div[@role='alert']")).click(); // Close "ArtVenture is running your recipe!" alert
	  
		WebDriverWait wait = new WebDriverWait(driver, 90);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Recipe has finished running!')]")));
	  
		//Assert.assertEquals(driver.findElement(By.xpath("//p[contains(text(),'Recipe completed')]")).getText(),"Recipe completed");
		assertRecipeStatusCompleted("Sipher - Game Avatar");
		
		driver.findElement(By.xpath("//div[@role='alert']")).click(); // Close "Recipe completed!" alert 
	}
	  
	@Test 
	public void TC_17_Check_Game_Asset_Hidden_Object() throws InterruptedException, AWTException, Exception {
	  
		driver.findElement(By.xpath("(//div[@class='max-w-full cursor-pointer'])[1]")).click(); 
		driver.findElement(By.xpath("(//div[@class='max-w-full cursor-pointer']//input[@class='bg-transparent p-2'])[1]")).sendKeys("Game Asset - Hidden Object"); 
		driver.findElement(By.xpath("//p[1][contains(text(),'Game Asset - Hidden Object')]")).click(); //Mở Game Asset - Hidden Object recipe page
	  
		Thread.sleep(2000);
	  
		driver.findElement(By.xpath("//button[@type='button' and text()='1']")).click(); //Select number of images
	  
		driver.findElement(By.xpath("//button[@type='submit' and text()='Generate']")).click(); //Click on Generate button
	  
		driver.findElement(By.xpath("//div[@role='alert']")).click(); // Close "ArtVenture is running your recipe!" alert
	  
		WebDriverWait wait = new WebDriverWait(driver, 120);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Recipe has finished running!')]")));
	  
		//Assert.assertEquals(driver.findElement(By.xpath("//p[contains(text(),'Recipe completed')]")).getText(),"Recipe completed");
		assertRecipeStatusCompleted("Game Asset - Hidden Object");
		
		driver.findElement(By.xpath("//div[@role='alert']")).click(); // Close "Recipe completed!" alert 
	}
	  
	@Test 
	public void TC_18_Check_AI_GIF_generator() throws InterruptedException, AWTException,Exception {
	  
		driver.findElement(By.xpath("(//div[@class='max-w-full cursor-pointer'])[1]")).click(); 
		driver.findElement(By.xpath("(//div[@class='max-w-full cursor-pointer']//input[@class='bg-transparent p-2'])[1]")).sendKeys("AI GIF generator");
		driver.findElement(By.xpath("//p[1][contains(text(),'AI GIF generator')]")).click(); // Mở AI GIF generator recipe page
	  
		Thread.sleep(2000);
	  
		driver.findElement(By.xpath("//textarea[@name='prompt']")).clear();
		driver.findElement(By.xpath("//textarea[@name='prompt']")).sendKeys("cloudy sky");
	  
		driver.findElement(By.xpath("//button[@type='button' and text()='3:2']")).click(); //Select aspect ratio
	  
		driver.findElement(By.xpath("//button[@type='button' and text()='3s']")).click();
	  
		driver.findElement(By.xpath("//button[@type='submit' and text()='Generate']")).click(); //Click on Generate button
	  
		driver.findElement(By.xpath("//div[@role='alert']")).click(); // Close "ArtVenture is running your recipe!" alert
	  
		WebDriverWait wait = new WebDriverWait(driver, 90);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Recipe has finished running!')]")));
	  
		//Assert.assertEquals(driver.findElement(By.xpath("//p[contains(text(),'Recipe completed')]")).getText(),"Recipe completed");
		assertRecipeStatusCompleted("AI GIF generator");
		
		driver.findElement(By.xpath("//div[@role='alert']")).click(); // Close "Recipe completed!" alert 
	}
	  
	@Test 
	public void TC_19_Check_Art_Toy() throws InterruptedException, AWTException, Exception	{
	  
		driver.findElement(By.xpath("(//div[@class='max-w-full cursor-pointer'])[1]")).click(); 
		driver.findElement(By.xpath("(//div[@class='max-w-full cursor-pointer']//input[@class='bg-transparent p-2'])[1]")).sendKeys("Art Toy");
		driver.findElement(By.xpath("//p[1][contains(text(),'Art Toy')]")).click();// Mở Art Toy recipe page
	  
		Thread.sleep(2000);
	  
		WebElement delete_Icon = driver.findElement(By.xpath("//div[@class='absolute top-2 right-2']//button[@type='button']")); 
		
		if (delete_Icon.isDisplayed()) { 
			delete_Icon.click(); 
		}
	  
		driver.findElement(By.xpath("//input[@type='file']")).sendKeys(System.getProperty("user.dir") + "/src/test/resources/testdatafile/Image01.jpg");//Upload origion face
	  
		driver.findElement(By.xpath("//textarea[@name='prompt']")).clear();
		driver.findElement(By.xpath("//textarea[@name='prompt']")).
		sendKeys("cloudy sky");
		
		driver.findElement(By.xpath("//button[@type='button' and text()='1']")).click(); //Select number of images
	  
		driver.findElement(By.xpath("//button[@type='submit' and text()='Generate']")).click(); //Click on Generate button
	  
		driver.findElement(By.xpath("//div[@role='alert']")).click(); // Close "ArtVenture is running your recipe!" alert
	  
		WebDriverWait wait = new WebDriverWait(driver, 90);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Recipe has finished running!')]")));
	  
		//Assert.assertEquals(driver.findElement(By.xpath("//p[contains(text(),'Recipe completed')]")).getText(),"Recipe completed");
		assertRecipeStatusCompleted("Art Toy");
		
		driver.findElement(By.xpath("//div[@role='alert']")).click(); // Close "Recipe completed!" alert 
	}
	  
	@Test 
	public void TC_20_Check_AI_Anime_Transformer() throws InterruptedException, AWTException, Exception {
	  
		driver.findElement(By.xpath("(//div[@class='max-w-full cursor-pointer'])[1]")).click(); 
		driver.findElement(By.xpath("(//div[@class='max-w-full cursor-pointer']//input[@class='bg-transparent p-2'])[1]")).sendKeys("AI Anime Transformer");
		driver.findElement(By.xpath("//p[1][contains(text(),'AI Anime Transformer')]")).click(); // Mở AI Anime Transformer recipe page
	  
		Thread.sleep(2000);
	  
		driver.findElement(By.xpath("//input[@type='file']")).sendKeys(System.getProperty("user.dir") + "/src/test/resources/testdatafile/Image01.jpg");//Upload origion face
	  
		driver.findElement(By.xpath("//button[@type='button' and text()='Manga']")).click(); //Select number of images
	  
		driver.findElement(By.xpath("//div[@class='line-clamp-1']//p[contains(text(),'Select Lora')]")).click(); 
		driver.findElement(By.xpath("//p[@class='px-2' and text()='Anime Outline v4']")).click();
		driver.findElement(By.xpath("//button[@type='button' and @title='Add lora']")).click();
	  
		driver.findElement(By.xpath("//button[@type='button' and text()='1']")).click(); //Select number of images
	  
		driver.findElement(By.xpath("//button[@type='submit' and text()='Generate']")).click(); //Click on Generate button
	  
		driver.findElement(By.xpath("//div[@role='alert']")).click(); // Close "ArtVenture is running your recipe!" alert
	  
		WebDriverWait wait = new WebDriverWait(driver, 90);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Recipe has finished running!')]")));
	  
		//Assert.assertEquals(driver.findElement(By.xpath("//p[contains(text(),'Recipe completed')]")).getText(),"Recipe completed");
		assertRecipeStatusCompleted("AI Anime Transformer");
		
		driver.findElement(By.xpath("//div[@role='alert']")).click(); // Close "Recipe completed!" alert 
		
	}
	  
	  
	@Test 
	public void TC_21_Check_Remix() throws InterruptedException, AWTException,	Exception {
	  
		driver.findElement(By.xpath("(//div[@class='max-w-full cursor-pointer'])[1]")).click(); 
		driver.findElement(By.xpath("(//div[@class='max-w-full cursor-pointer']//input[@class='bg-transparent p-2'])[1]")).sendKeys("Remix");
		driver.findElement(By.xpath("//p[1][contains(text(),'Remix')]")).click(); //Mở Remix recipe page
	  
		Thread.sleep(2000);
	  
		driver.findElement(By.xpath("//input[@type='file']")).sendKeys(System.getProperty("user.dir") + "/src/test/resources/testdatafile/Image01.jpg");//Upload image
	  
		driver.findElement(By.xpath("//textarea[@name='prompt']")).sendKeys("A photo of a cat in the forest"); //Input prompt
	  
		driver.findElement(By.xpath("//button[@type='button' and text()='Most Similar']")).click();//Select Similarity
	  
		driver.findElement(By.xpath("//button[@type='button' and text()='1']")).click(); //Select number of images
	  
		driver.findElement(By.xpath("//button[@type='submit' and text()='Generate']")).click(); //Click on Generate button
	  
		driver.findElement(By.xpath("//div[@role='alert']")).click(); // Close "ArtVenture is running your recipe!" alert
	  
		WebDriverWait wait = new WebDriverWait(driver, 120);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Recipe has finished running!')]")));
	  
		//Assert.assertEquals(driver.findElement(By.xpath("//p[contains(text(),'Recipe completed')]")).getText(),"Recipe completed");
		assertRecipeStatusCompleted("Remix");
		
		driver.findElement(By.xpath("//div[@role='alert']")).click(); // Close "Recipe completed!" alert 
		
	}
	  
	@Test 
	public void TC_22_Check_Remove_Background() throws InterruptedException, AWTException,	Exception{
	  
		driver.findElement(By.xpath("(//div[@class='max-w-full cursor-pointer'])[1]")).click(); 
		driver.findElement(By.xpath("(//div[@class='max-w-full cursor-pointer']//input[@class='bg-transparent p-2'])[1]")).sendKeys("Remove Background");
		driver.findElement(By.xpath("//p[1][contains(text(),'Remove Background')]")).click(); // Mở Remove Background recipe page
	  
		Thread.sleep(2000);
	  
		driver.findElement(By.xpath("//input[@type='file']")).sendKeys(System.getProperty("user.dir") + "/src/test/resources/testdatafile/Image01.jpg");//Upload image
	  
		Thread.sleep(2000);
	  
		driver.findElement(By.xpath("//button[@type='submit' and text()='Generate']")).click(); //Click on Generate button
	  
		driver.findElement(By.xpath("//div[@role='alert']")).click(); // Close"ArtVenture is running your recipe!" alert
	  
	  	WebDriverWait wait = new WebDriverWait(driver, 90);
	  	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Recipe has finished running!')]")));
	  
	  	//Assert.assertEquals(driver.findElement(By.xpath("//p[contains(text(),'Recipe completed')]")).getText(),"Recipe completed");
	  	assertRecipeStatusCompleted("Remove Background");
	  	
	  	driver.findElement(By.xpath("//div[@role='alert']")).click(); // Close "Recipe completed!" alert 
	  	
	}
	  
	@Test 
	public void TC_23_Check_Tiled_Upscaler() throws InterruptedException, AWTException, Exception {
	  
		driver.findElement(By.xpath("(//div[@class='max-w-full cursor-pointer'])[1]")).click(); 
		driver.findElement(By.xpath("(//div[@class='max-w-full cursor-pointer']//input[@class='bg-transparent p-2'])[1]")).sendKeys("Tiled Upscale"); 
		driver.findElement(By.xpath("//p[1][contains(text(),'Tiled Upscale') and not(contains(text(), 'LCM'))]")).click(); // Mở Tiled Upscale LCM page
	  
		Thread.sleep(2000);
	  
		driver.findElement(By.xpath("//input[@type='file']")).sendKeys(System.getProperty("user.dir") + "/src/test/resources/testdatafile/Image01.jpg");//Upload image
	  
		Thread.sleep(2000);
	  
		driver.findElement(By.xpath("//textarea[@name='prompt']")).clear();
		driver.findElement(By.xpath("//textarea[@name='prompt']")).sendKeys("masterpiece, best quality"); //Input prompt
	  
		driver.findElement(By.xpath("//div[@class='line-clamp-1']//p[contains(text(),'Select Lora')]")).click();
		driver.findElement(By.xpath("//p[@class='px-2' and text()='Anime Outline v4']")).click();
		driver.findElement(By.xpath("//button[@type='button' and @title='Add lora']")).click();
	  
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();", driver.findElement(By.xpath("//p[text()='Number of Images']"))); //Scroll down
	  
		driver.findElement(By.xpath("//button[@type='button' and text()='1']")).click(); //Select number of images
	  
		driver.findElement(By.xpath("//button[@type='submit' and text()='Generate']")).click(); //Click on Generate button
	  
		driver.findElement(By.xpath("//div[@role='alert']")).click(); // Close "ArtVenture is running your recipe!" alert
	  
		WebDriverWait wait = new WebDriverWait(driver, 90);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Recipe has finished running!')]")));
	  
		//Assert.assertEquals(driver.findElement(By.xpath("//p[contains(text(),'Recipe completed')]")).getText(), "Recipe completed");
		assertRecipeStatusCompleted("Tiled Upscale");
		
		driver.findElement(By.xpath("//div[@role='alert']")).click(); // Close "Recipe completed!" alert 
		
	}
	  
	@Test 
	public void TC_24_Check_SDXL_v1() throws InterruptedException, AWTException, Exception {
	  
		driver.findElement(By.xpath("(//div[@class='max-w-full cursor-pointer'])[1]")).click(); 
		driver.findElement(By.xpath("(//div[@class='max-w-full cursor-pointer']//input[@class='bg-transparent p-2'])[1]")).sendKeys("SDXL v1.0");
		driver.findElement(By.xpath("//p[1][contains(text(),'SDXL v1.0')]")).click();// Mở SDXL v1.0 recipe page
	  
		Thread.sleep(2000);
	  
		driver.findElement(By.xpath("//div[@class='line-clamp-1']//p[text()='None']")).click(); 
		driver.findElement(By.xpath("(//div[@class='max-w-full cursor-pointer']//input[@class='bg-transparent p-2'])[2]")).sendKeys("MineCraft"); 
		driver.findElement(By.xpath("//p[@class='px-2' and contains(text(),'Minecraft')]")).click();
	  
		driver.findElement(By.xpath("//div[@class='line-clamp-1']//p[contains(text(),'Select Lora')]")).click(); 
		driver.findElement(By.xpath("//div[@class='max-w-full cursor-pointer flex-1']//input[@class='bg-transparent p-2']")).sendKeys("Stickers Redmond SDXL");
		driver.findElement(By.xpath("//p[@class='px-2' and contains(text(),'Stickers Redmond SDXL')]")).click();
	  
		driver.findElement(By.xpath("//button[@type='button' and @title='Add lora']")
				).click();
	  
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();", driver.findElement(By.xpath("//p[text()='Number of Images']"))); //Scroll down
		driver.findElement(By.xpath("//button[@type='button' and text()='1']")).click(); //Select number of images
	  
		driver.findElement(By.xpath("//button[@type='submit' and text()='Generate']")).click(); //Click on Generate button
	  
		driver.findElement(By.xpath("//div[@role='alert']")).click(); // Close "ArtVenture is running your recipe!" alert
	  
		WebDriverWait wait = new WebDriverWait(driver, 90);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Recipe has finished running!')]")));
	  
		//Assert.assertEquals(driver.findElement(By.xpath("//p[contains(text(),'Recipe completed')]")).getText(),"Recipe completed");
		assertRecipeStatusCompleted("SDXL v1.0");
		
		driver.findElement(By.xpath("//div[@role='alert']")).click(); // Close "Recipe completed!" alert 
		
	}
	  
	@Test 
	public void TC_25_Check_AI_Image_Generator() throws InterruptedException, AWTException, Exception	{
	  
		driver.findElement(By.xpath("(//div[@class='max-w-full cursor-pointer'])[1]")).click(); 
		driver.findElement(By.xpath("(//div[@class='max-w-full cursor-pointer']//input[@class='bg-transparent p-2'])[1]")).sendKeys("AI Image Generator"); 
		driver.findElement(By.xpath("//p[1][contains(text(),'AI Image Generator') and not(contains(text(), 'LCM'))]")).click(); // Mở AI Image Generator page
	  
		Thread.sleep(2000);
	  
		driver.findElement(By.xpath("//textarea[@name='prompt']")).clear();
		driver.findElement(By.xpath("//textarea[@name='prompt']")).sendKeys("evening sunset scenery blue sky nature, glass bottle with a galaxy in it"); //Input prompt
	  
		driver.findElement(By.xpath("//textarea[@name='negative_prompt']")).clear();
		driver.findElement(By.xpath("//textarea[@name='negative_prompt']")).
		sendKeys("text, watermark"); //Input nega prompt
	  
		//Thread.sleep(2000);
	  
		driver.findElement(By.xpath("//div[@class='line-clamp-1']//p[contains(text(),'Select Lora')]")).click(); 
		driver.findElement(By.xpath("//p[@class='px-2' and text()='Anime Outline v4']")).click();
		driver.findElement(By.xpath("//button[@type='button' and @title='Add lora']")).click();
	  
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();", driver.findElement(By.xpath("//p[text()='Number of Images']"))); //Scroll down
	  
		driver.findElement(By.xpath("//button[@type='button' and text()='1']")).click(); //Select number of images
	  
		driver.findElement(By.xpath("//button[@type='submit' and text()='Generate']")).click(); //Click on Generate button
	  
		driver.findElement(By.xpath("//div[@role='alert']")).click(); // Close "ArtVenture is running your recipe!" alert
	  
		WebDriverWait wait = new WebDriverWait(driver, 90);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Recipe has finished running!')]")));
	  
		//Assert.assertEquals(driver.findElement(By.xpath("//p[contains(text(),'Recipe completed')]")).getText(),"Recipe completed");
		assertRecipeStatusCompleted("AI Image Generator");
		
		driver.findElement(By.xpath("//div[@role='alert']")).click(); // Close "Recipe completed!" alert 
		
	}
	  
	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}
