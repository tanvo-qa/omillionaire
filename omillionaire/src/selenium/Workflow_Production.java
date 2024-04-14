package selenium;

import org.testng.Assert;
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

public class Workflow_Production {

	public WebDriver driver;
	

	//private String HOOK_URL = "https://hooks.slack.com/services/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";

	@BeforeClass
	public void beforeClass() throws InterruptedException {

		String driverPath = "/Users/tanhungvo/Documents/SeleniumWebDrider/chromedriver-mac-arm64/chromedriver";
		System.setProperty("webdriver.chrome.driver", driverPath);
		driver = new ChromeDriver();

		driver.get("https://artventure.ai/workspace/workflows");
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

//	private void assertRecipeStatusCompleted(String recipeName) throws Exception {
//		
//		try {
//			WebElement status = driver.findElement(By.xpath("//p[contains(text(),'Recipe completed')]"));
//			if (status != null) {
//				sendSlackMessage("Recipe " + recipeName + " completed");
//			} else {
//				sendSlackMessage("Recipe " + recipeName + " failed");
//			}
//				
//		} catch (Exception e) {
//			
//			sendSlackMessage("Recipe " + recipeName + " failed");
//		}
//		
//	}

//	private void sendSlackMessage(String msg) throws Exception {
//		CloseableHttpClient client = HttpClients.createDefault();
//		HttpPost httpPost = new HttpPost(HOOK_URL);
//
//		try {
//			String json = "{ \"text\" : \"" + msg + "\" }";
//			StringEntity entity = new StringEntity(json);
//			httpPost.setEntity(entity);
//			httpPost.setHeader("Accept", "application/json");
//			httpPost.setHeader("Content-type", "application/json");
//			client.execute(httpPost);
//			client.close();
//		} catch (UnsupportedEncodingException e) {
//			throw new Exception("Error sending slack message", e);
//		} catch (ClientProtocolException e) {
//			throw new Exception("Error sending slack message", e);
//		} catch (IOException e) {
//			throw new Exception("Error sending slack message", e);
//		}
//	}
	
	private void select_Outpainting(String recipe) throws InterruptedException {
		
		driver.findElement(By.xpath("//div[@class='w-4 h-4 text-atherGray-300']")).click(); // Open all recipes
		driver.findElement(By.xpath("//input[@placeholder='Find recipe ...']")).sendKeys(recipe); //Input recipe name
		driver.findElement(By.xpath("//div[@class='cursor-pointer']//p[text()='Outpainting']")).click(); // Select recipe
		
		
		driver.findElement(By.xpath("//input[@type='file']")).sendKeys(System.getProperty("user.dir") + "/src/test/resources/testdatafile/Image01.jpg"); // Upload image

		Thread.sleep(2000);

		driver.findElement(By.xpath("//textarea[@name='prompt']")).sendKeys("A photo of a cat in the forest"); // Input prompt


		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();", driver.findElement(By.xpath("//p[text()='Number of Images']"))); // Scroll down

		driver.findElement(By.xpath("//button[@type='button' and text()='1']")).click(); // Select number of images

		driver.findElement(By.xpath("//div[@class='flex items-center justify-center' and text()='Add Workflow']")).click(); // Click on Add Workflow button

	}
	
	private void select_Sketch_To_Image(String recipe) throws InterruptedException {
		
		driver.findElement(By.xpath("//div[@class='w-4 h-4 text-atherGray-300']")).click(); // Open all recipes
		driver.findElement(By.xpath("//input[@placeholder='Find recipe ...']")).sendKeys(recipe); //Input recipe name
		driver.findElement(By.xpath("//div[@class='cursor-pointer']//p[text()='Sketch To Image']")).click(); // Select recipe
		
		Thread.sleep(3000);
		
		driver.findElement(By.xpath("//input[@type='file']")).sendKeys(System.getProperty("user.dir") + "/src/test/resources/testdatafile/Image01.jpg"); //Upload image
		 
		Thread.sleep(2000);
	 
		driver.findElement(By.xpath("//textarea[@name='prompt']")).sendKeys("A photo of a cat in the forest"); //Input prompt
	 
		Thread.sleep(2000);
	 
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();", driver.findElement(By.xpath("//p[text()='Number of Images']"))); //Scroll down
	 
		driver.findElement(By.xpath("//button[@type='button' and text()='1']")).click(); //Select number of images
	  
		driver.findElement(By.xpath("//div[@class='flex items-center justify-center' and text()='Add Workflow']")).click(); // Click on Add Workflow button

	}
	
	private void select_Upscaler(String recipe) throws InterruptedException {
		
		driver.findElement(By.xpath("//div[@class='w-4 h-4 text-atherGray-300']")).click(); // Open all recipes
		driver.findElement(By.xpath("//input[@placeholder='Find recipe ...']")).sendKeys(recipe); //Input recipe name
		driver.findElement(By.xpath("//div[@class='cursor-pointer']//p[text()='Upscaler']")).click(); // Select recipe
	  
		Thread.sleep(2000);
	  
		driver.findElement(By.xpath("//input[@type='file']")).sendKeys(System.getProperty("user.dir") + "/src/test/resources/testdatafile/Image01.jpg");//Upload image
	  
		Thread.sleep(2000);
	  
		driver.findElement(By.xpath("//div[@class='flex items-center justify-center' and text()='Add Workflow']")).click(); // Click on Add Workflow button
		
	}
	
	private void select_Image_Generator(String recipe) throws InterruptedException {
		
		driver.findElement(By.xpath("//div[@class='w-4 h-4 text-atherGray-300']")).click(); // Open all recipes
		driver.findElement(By.xpath("//input[@placeholder='Find recipe ...']")).sendKeys(recipe); //Input recipe name
		driver.findElement(By.xpath("//div[@class='cursor-pointer']//p[text()='Image Generator']")).click(); // Select recipe
	  
		Thread.sleep(2000);
	  
		driver.findElement(By.xpath("//textarea[@name='prompt']")).clear();
		driver.findElement(By.xpath("//textarea[@name='prompt']")).sendKeys("evening sunset scenery blue sky nature, glass bottle with a galaxy in it"); //Input prompt
	  
		driver.findElement(By.xpath("//textarea[@name='negative_prompt']")).clear();
		driver.findElement(By.xpath("//textarea[@name='negative_prompt']")).
		sendKeys("text, watermark"); //Input nega prompt
	  
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();", driver.findElement(By.xpath("//p[text()='Number of Images']"))); //Scroll down
	  
		driver.findElement(By.xpath("//button[@type='button' and text()='1']")).click(); //Select number of images
	  
		driver.findElement(By.xpath("//div[@class='flex items-center justify-center' and text()='Add Workflow']")).click(); // Click on Add Workflow button
		
	}

	private void select_Image_To_Image(String recipe) throws InterruptedException {
		
		driver.findElement(By.xpath("//div[@class='w-4 h-4 text-atherGray-300']")).click(); // Open all recipes
		driver.findElement(By.xpath("//input[@placeholder='Find recipe ...']")).sendKeys(recipe); //Input recipe name
		driver.findElement(By.xpath("//div[@class='cursor-pointer']//p[text()='Image To Image']")).click(); // Select recipe
	  
		Thread.sleep(2000);
		
		driver.findElement(By.xpath("(//div[@class='space-y-6']//input[@type='file'])[1]")).sendKeys(System.getProperty("user.dir") + "/src/test/resources/testdatafile/Image01.jpg"); //Upload image to generate

		driver.findElement(By.xpath("//textarea[@name='prompt']")).clear();
		driver.findElement(By.xpath("//textarea[@name='prompt']")).sendKeys("masterpiece, best quality"); //Input prompt

		driver.findElement(By.xpath("//button[@type='button' and text()='1']")).click(); //Select number of images
		
		driver.findElement(By.xpath("//div[@class='flex items-center justify-center' and text()='Add Workflow']")).click(); // Click on Add Workflow button

	}
	
	private void select_Spookifyme(String recipe) throws InterruptedException {
		
		driver.findElement(By.xpath("//div[@class='w-4 h-4 text-atherGray-300']")).click(); // Open all recipes
		driver.findElement(By.xpath("//input[@placeholder='Find recipe ...']")).sendKeys(recipe); //Input recipe name
		driver.findElement(By.xpath("//div[@class='cursor-pointer']//p[text()='Spookifyme']")).click(); // Select recipe
		
		Thread.sleep(2000);
		
		driver.findElement(By.xpath("(//input[@type='file'])")).sendKeys(System.getProperty("user.dir") + "/src/test/resources/testdatafile/Image01.jpg");//Upload image to generate
		  
		Thread.sleep(2000);
	  
		driver.findElement(By.xpath("//button[@type='button' and contains(text(),'Male')]")).click();//Select a gender
	  
		driver.findElement(By.xpath("//button[@type='button' and contains(text(),'Middle-Aged Adults (over 31-45)')]")).click(); //Select a age
	  
		driver.findElement(By.xpath("(//div[@class='w-full grid grid-cols-2 gap-2 pr-1 max-h-[20rem] overflow-auto']//button[@type='button'])[12]")).click(); //Select a custome theme
	  
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();", driver.findElement(By.xpath("//p[text()='Number of Images']"))); //Scrolldown
	  
		driver.findElement(By.xpath("//button[@type='button' and text()='1']")).click(); //Select number of images
		
		driver.findElement(By.xpath("//div[@class='flex items-center justify-center' and text()='Add Workflow']")).click(); // Click on Add Workflow button

	}
	
	private void select_90s_Style_Transformer(String recipe) throws InterruptedException {
		
		driver.findElement(By.xpath("//div[@class='w-4 h-4 text-atherGray-300']")).click(); // Open all recipes
		driver.findElement(By.xpath("//input[@placeholder='Find recipe ...']")).sendKeys(recipe); //Input recipe name
		driver.findElement(By.xpath("//div[@class='cursor-pointer']//p[text()='90s Style Transformer']")).click(); // Select recipe
		
		Thread.sleep(2000);
		
		driver.findElement(By.xpath("(//input[@type='file'])")).sendKeys(System.getProperty("user.dir") + "/src/test/resources/testdatafile/Image01.jpg");//Upload image to generate
		  
		Thread.sleep(2000);
	  
		driver.findElement(By.xpath("(//div[@class='w-full grid grid-cols-2 gap-2 pr-1 max-h-[20rem] overflow-auto']//button[@type='button'])[5]")).click(); //Select a template
	  
		driver.findElement(By.xpath("//button[@type='button' and text()='1']")).click(); //Select number of images
		
		driver.findElement(By.xpath("//div[@class='flex items-center justify-center' and text()='Add Workflow']")).click(); // Click on Add Workflow button

	}
	
	private void select_Photos_Mixer(String recipe) throws InterruptedException {
		
		driver.findElement(By.xpath("//div[@class='w-4 h-4 text-atherGray-300']")).click(); // Open all recipes
		driver.findElement(By.xpath("//input[@placeholder='Find recipe ...']")).sendKeys(recipe); //Input recipe name
		driver.findElement(By.xpath("//div[@class='cursor-pointer']//p[text()='Photos Mixer']")).click(); // Select recipe
		
		Thread.sleep(2000);
		
		driver.findElement(By.xpath("(//div[@class='space-y-6']//input[@type='file'])[1]")).sendKeys(System.getProperty("user.dir") + "/src/test/resources/testdatafile/Image01.jpg");//Upload origion face
		  
		driver.findElement(By.xpath("(//div[@class='space-y-6']//input[@type='file'])[2]")).sendKeys(System.getProperty("user.dir") + "/src/test/resources/testdatafile/Image02.jpg");//Upload face image
	  
		Thread.sleep(2000);
	  
		driver.findElement(By.xpath("//textarea[@name='prompt']")).clear(); //Clear text in field
	  
		driver.findElement(By.xpath("//textarea[@name='prompt']")).sendKeys("1 girl, (solo), (wearing Bat girl suit without mask),looking at viewer,"); //Input prompt
	  
		driver.findElement(By.xpath("//button[@type='button' and text()='3:2']")).click(); //Select Aspect Ratio
	  
		driver.findElement(By.xpath("//button[@type='button' and text()='1']")).click(); //Select number of images
		
		driver.findElement(By.xpath("//div[@class='flex items-center justify-center' and text()='Add Workflow']")).click(); // Click on Add Workflow button

	}
	
	private void select_Logo_Art(String recipe) throws InterruptedException {
		
		driver.findElement(By.xpath("//div[@class='w-4 h-4 text-atherGray-300']")).click(); // Open all recipes
		driver.findElement(By.xpath("//input[@placeholder='Find recipe ...']")).sendKeys(recipe); //Input recipe name
		driver.findElement(By.xpath("//div[@class='cursor-pointer']//p[text()='Logo Art']")).click(); // Select recipe
		
		Thread.sleep(2000);
		
		driver.findElement(By.xpath("//input[@type='file']")).sendKeys(System.getProperty("user.dir") + "/src/test/resources/testdatafile/Image01.jpg");//Upload origion face
		  
	  	Thread.sleep(2000);
	  
	  	driver.findElement(By.xpath("(//div[@class='w-full grid grid-cols-2 gap-2 pr-1 max-h-[20rem] overflow-auto']//button[@type='button'])[5]")).click(); //Select a template
	  
	  	driver.findElement(By.xpath("//button[@type='button' and text()='1']")).click(); //Select number of images
	  	
		driver.findElement(By.xpath("//div[@class='flex items-center justify-center' and text()='Add Workflow']")).click(); // Click on Add Workflow button

	}
	
	private void select_Commercial_Photoshoot(String recipe) throws InterruptedException {
		
		driver.findElement(By.xpath("//div[@class='w-4 h-4 text-atherGray-300']")).click(); // Open all recipes
		driver.findElement(By.xpath("//input[@placeholder='Find recipe ...']")).sendKeys(recipe); //Input recipe name
		driver.findElement(By.xpath("//div[@class='cursor-pointer']//p[text()='Commercial Photoshoot']")).click(); // Select recipe
		
		Thread.sleep(2000);
		
		driver.findElement(By.xpath("//input[@type='file']")).sendKeys(System.getProperty("user.dir") + "/src/test/resources/testdatafile/Image01.jpg");//Upload origion face
		  
		Thread.sleep(2000);
	  
		driver.findElement(By.xpath("(//div[@class='w-full grid grid-cols-2 gap-2 pr-1 max-h-[20rem] overflow-auto']//button[@type='button'])[5]")).click(); //Select a template
	  
		driver.findElement(By.xpath("//button[@type='button' and text()='1']")).click(); //Select number of images
		
		driver.findElement(By.xpath("//div[@class='flex items-center justify-center' and text()='Add Workflow']")).click(); // Click on Add Workflow button

	}
	
	private void select_QR_Code_Art(String recipe) throws InterruptedException {
		
		driver.findElement(By.xpath("//div[@class='w-4 h-4 text-atherGray-300']")).click(); // Open all recipes
		driver.findElement(By.xpath("//input[@placeholder='Find recipe ...']")).sendKeys(recipe); //Input recipe name
		driver.findElement(By.xpath("//div[@class='cursor-pointer']//p[text()='QR Code Art']")).click(); // Select recipe
		
		Thread.sleep(2000);
		
		driver.findElement(By.xpath("//textarea[@name='url']")).clear();
	  	driver.findElement(By.xpath("//textarea[@name='url']")).sendKeys("https://artventure.sipher.gg/recipes/ai-qr-code");
	  
	  	driver.findElement(By.xpath("(//div[@class='w-full grid grid-cols-2 gap-2 pr-1 max-h-[20rem] overflow-auto']//button[@type='button'])[5]")).click(); //Select a template
	  
	  	driver.findElement(By.xpath("//button[@type='button' and text()='1']")).click(); //Select number of images
	  	
		driver.findElement(By.xpath("//div[@class='flex items-center justify-center' and text()='Add Workflow']")).click(); // Click on Add Workflow button

	}
	
	private void select_Character_Design_Concept(String recipe) throws InterruptedException {
		
		driver.findElement(By.xpath("//div[@class='w-4 h-4 text-atherGray-300']")).click(); // Open all recipes
		driver.findElement(By.xpath("//input[@placeholder='Find recipe ...']")).sendKeys(recipe); //Input recipe name
		driver.findElement(By.xpath("//div[@class='cursor-pointer']//p[text()='Character Design Concept']")).click(); // Select recipe
		
		Thread.sleep(2000);
		
		driver.findElement(By.xpath("(//div[@class='w-full grid grid-cols-3 gap-2 max-h-[32rem] overflow-auto']//button[@type='button'])[5]")).click(); //Select a template
		  
		driver.findElement(By.xpath("//textarea[@name='prompt']")).clear();
		driver.findElement(By.xpath("//textarea[@name='prompt']")).sendKeys("1girl, 20 years old, military, colorful, intricate sharp details");
	  
		driver.findElement(By.xpath("//textarea[@name='negative_prompt']")).clear();
		driver.findElement(By.xpath("//textarea[@name='negative_prompt']")).sendKeys("text, watermark, NSFW");
	  
		driver.findElement(By.xpath("//button[@type='button' and text()='1']")).click(); //Select number of images
		
		driver.findElement(By.xpath("//div[@class='flex items-center justify-center' and text()='Add Workflow']")).click(); // Click on Add Workflow button

	}
	
	private void select_Sipher_Game_Avatar_Frame(String recipe) throws InterruptedException {
		
		driver.findElement(By.xpath("//div[@class='w-4 h-4 text-atherGray-300']")).click(); // Open all recipes
		driver.findElement(By.xpath("//input[@placeholder='Find recipe ...']")).sendKeys(recipe); //Input recipe name
		driver.findElement(By.xpath("//div[@class='cursor-pointer']//p[text()='Sipher Game Avatar Frame']")).click(); // Select recipe
		
		Thread.sleep(2000);
		
		driver.findElement(By.xpath("//textarea[@name='prompt']")).clear();
		driver.findElement(By.xpath("//textarea[@name='prompt']")).sendKeys("1girl, 20 years old, military, colorful, intricate sharp details");
	  
		driver.findElement(By.xpath("//textarea[@name='negative_prompt']")).clear();
		driver.findElement(By.xpath("//textarea[@name='negative_prompt']")).sendKeys("text, watermark, NSFW");
	  
		driver.findElement(By.xpath("//button[@type='button' and text()='1']")).click(); //Select number of images
		
		driver.findElement(By.xpath("//div[@class='flex items-center justify-center' and text()='Add Workflow']")).click(); // Click on Add Workflow button

	}
	
	private void select_Game_Asset_Hidden_Object(String recipe) throws InterruptedException {
		
		driver.findElement(By.xpath("//div[@class='w-4 h-4 text-atherGray-300']")).click(); // Open all recipes
		driver.findElement(By.xpath("//input[@placeholder='Find recipe ...']")).sendKeys(recipe); //Input recipe name
		driver.findElement(By.xpath("//div[@class='cursor-pointer']//p[text()='Game Asset - Hidden Object']")).click(); // Select recipe
		
		Thread.sleep(2000);
		
		driver.findElement(By.xpath("//button[@type='button' and text()='1']")).click(); //Select number of images
		
		driver.findElement(By.xpath("//div[@class='flex items-center justify-center' and text()='Add Workflow']")).click(); // Click on Add Workflow button

	}
	
	private void select_Sipher_Asset_Gun(String recipe) throws InterruptedException {
		
		driver.findElement(By.xpath("//div[@class='w-4 h-4 text-atherGray-300']")).click(); // Open all recipes
		driver.findElement(By.xpath("//input[@placeholder='Find recipe ...']")).sendKeys(recipe); //Input recipe name
		driver.findElement(By.xpath("//div[@class='cursor-pointer']//p[text()='Sipher Asset - Gun']")).click(); // Select recipe
		
		Thread.sleep(2000);
		
		driver.findElement(By.xpath("//div[@class='line-clamp-1']//p[contains(text(),'Select Lora')]")).click(); 
		driver.findElement(By.xpath("//p[@class='px-2' and text()='Anime Outline v4']")).click();
		driver.findElement(By.xpath("//button[@type='button' and @title='Add lora']")).click();
	  
		driver.findElement(By.xpath("//button[@type='button' and text()='1']")).click(); //Select number of images
		
		driver.findElement(By.xpath("//div[@class='flex items-center justify-center' and text()='Add Workflow']")).click(); // Click on Add Workflow button

	}
	
	private void select_Sipher_Game_Avatar(String recipe) throws InterruptedException {
		
		driver.findElement(By.xpath("//div[@class='w-4 h-4 text-atherGray-300']")).click(); // Open all recipes
		driver.findElement(By.xpath("//input[@placeholder='Find recipe ...']")).sendKeys(recipe); //Input recipe name
		driver.findElement(By.xpath("//div[@class='cursor-pointer']//p[text()='Sipher - Game Avatar']")).click(); // Select recipe
		
		Thread.sleep(2000);
		
		driver.findElement(By.xpath("//button[@type='button' and text()='1']")).click(); //Select number of images
		
		driver.findElement(By.xpath("//div[@class='flex items-center justify-center' and text()='Add Workflow']")).click(); // Click on Add Workflow button

	}
	
	private void select_Reskin_ArtToy(String recipe) throws InterruptedException {
		
		driver.findElement(By.xpath("//div[@class='w-4 h-4 text-atherGray-300']")).click(); // Open all recipes
		driver.findElement(By.xpath("//input[@placeholder='Find recipe ...']")).sendKeys(recipe); //Input recipe name
		driver.findElement(By.xpath("//div[@class='cursor-pointer']//p[text()='Reskin ArtToy']")).click(); // Select recipe
		
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
		
		driver.findElement(By.xpath("//div[@class='flex items-center justify-center' and text()='Add Workflow']")).click(); // Click on Add Workflow button

	}
	
	private void select_GIF_Generator(String recipe) throws InterruptedException {
		
		driver.findElement(By.xpath("//div[@class='w-4 h-4 text-atherGray-300']")).click(); // Open all recipes
		driver.findElement(By.xpath("//input[@placeholder='Find recipe ...']")).sendKeys(recipe); //Input recipe name
		driver.findElement(By.xpath("//div[@class='cursor-pointer']//p[text()='GIF Generator']")).click(); // Select recipe
		
		Thread.sleep(2000);
		
		driver.findElement(By.xpath("//textarea[@name='prompt']")).clear();
		driver.findElement(By.xpath("//textarea[@name='prompt']")).sendKeys("cloudy sky");
	  
		driver.findElement(By.xpath("//button[@type='button' and text()='3:2']")).click(); //Select aspect ratio
	  
		driver.findElement(By.xpath("//button[@type='button' and text()='3s']")).click();
		
		driver.findElement(By.xpath("//div[@class='flex items-center justify-center' and text()='Add Workflow']")).click(); // Click on Add Workflow button

	}
	
	private void select_Remix(String recipe) throws InterruptedException {
		
		driver.findElement(By.xpath("//div[@class='w-4 h-4 text-atherGray-300']")).click(); // Open all recipes
		driver.findElement(By.xpath("//input[@placeholder='Find recipe ...']")).sendKeys(recipe); //Input recipe name
		driver.findElement(By.xpath("//div[@class='cursor-pointer']//p[text()='Remix']")).click(); // Select recipe
		
		Thread.sleep(2000);
		
		driver.findElement(By.xpath("//input[@type='file']")).sendKeys(System.getProperty("user.dir") + "/src/test/resources/testdatafile/Image01.jpg");//Upload image
		  
		driver.findElement(By.xpath("//textarea[@name='prompt']")).sendKeys("A photo of a cat in the forest"); //Input prompt
	  
		driver.findElement(By.xpath("//button[@type='button' and text()='Most Similar']")).click();//Select Similarity
	  
		driver.findElement(By.xpath("//button[@type='button' and text()='1']")).click(); //Select number of images
		
		driver.findElement(By.xpath("//div[@class='flex items-center justify-center' and text()='Add Workflow']")).click(); // Click on Add Workflow button

	}
	
	private void select_Anime_Style_Transformer(String recipe) throws InterruptedException {
		
		driver.findElement(By.xpath("//div[@class='w-4 h-4 text-atherGray-300']")).click(); // Open all recipes
		driver.findElement(By.xpath("//input[@placeholder='Find recipe ...']")).sendKeys(recipe); //Input recipe name
		driver.findElement(By.xpath("//div[@class='cursor-pointer']//p[text()='Anime Style Transformer']")).click(); // Select recipe
		
		Thread.sleep(2000);
		
		driver.findElement(By.xpath("//input[@type='file']")).sendKeys(System.getProperty("user.dir") + "/src/test/resources/testdatafile/Image01.jpg");//Upload origion face
		  
		driver.findElement(By.xpath("//button[@type='button' and text()='Manga']")).click(); //Select number of images
	  
		driver.findElement(By.xpath("//button[@type='button' and text()='1']")).click(); //Select number of images
		
		driver.findElement(By.xpath("//div[@class='flex items-center justify-center' and text()='Add Workflow']")).click(); // Click on Add Workflow button

	}
	
	private void select_Remove_Background(String recipe) throws InterruptedException {
		
		driver.findElement(By.xpath("//div[@class='w-4 h-4 text-atherGray-300']")).click(); // Open all recipes
		driver.findElement(By.xpath("//input[@placeholder='Find recipe ...']")).sendKeys(recipe); //Input recipe name
		driver.findElement(By.xpath("//div[@class='cursor-pointer']//p[text()='Remove Background']")).click(); // Select recipe
		
		Thread.sleep(2000);
		
		driver.findElement(By.xpath("//input[@type='file']")).sendKeys(System.getProperty("user.dir") + "/src/test/resources/testdatafile/Image01.jpg");//Upload image
		  
		Thread.sleep(2000);
		
		driver.findElement(By.xpath("//div[@class='flex items-center justify-center' and text()='Add Workflow']")).click(); // Click on Add Workflow button

	}
	
	private void select_Details_Enhancer_Pro(String recipe) throws InterruptedException {
		
		driver.findElement(By.xpath("//div[@class='w-4 h-4 text-atherGray-300']")).click(); // Open all recipes
		driver.findElement(By.xpath("//input[@placeholder='Find recipe ...']")).sendKeys(recipe); //Input recipe name
		driver.findElement(By.xpath("//div[@class='cursor-pointer']//p[text()='Details Enhancer Pro']")).click(); // Select recipe
		
		Thread.sleep(2000);
		
		driver.findElement(By.xpath("//input[@type='file']")).sendKeys(System.getProperty("user.dir") + "/src/test/resources/testdatafile/Image01.jpg");//Upload image
		  
		Thread.sleep(2000);
	  
		driver.findElement(By.xpath("//textarea[@name='prompt']")).clear();
		driver.findElement(By.xpath("//textarea[@name='prompt']")).sendKeys("masterpiece, best quality"); //Input prompt
	  
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();", driver.findElement(By.xpath("//p[text()='Number of Images']"))); //Scroll down
	  
		driver.findElement(By.xpath("//button[@type='button' and text()='1']")).click(); //Select number of images
		
		driver.findElement(By.xpath("//div[@class='flex items-center justify-center' and text()='Add Workflow']")).click(); // Click on Add Workflow button

	}
	
	private void select_Image_Generator_SDXL(String recipe) throws InterruptedException {
		
		driver.findElement(By.xpath("//div[@class='w-4 h-4 text-atherGray-300']")).click(); // Open all recipes
		driver.findElement(By.xpath("//input[@placeholder='Find recipe ...']")).sendKeys(recipe); //Input recipe name
		driver.findElement(By.xpath("//div[@class='cursor-pointer']//p[text()='Image Generator - SDXL']")).click(); // Select recipe
		
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
		
		driver.findElement(By.xpath("//div[@class='flex items-center justify-center' and text()='Add Workflow']")).click(); // Click on Add Workflow button

	}
	
	@Test
	public void TC_01_Open_New_Workflow() throws InterruptedException, AWTException, Exception {

		driver.findElement(By.xpath("//button[@type='button' and text()='New Workflow']")).click(); // Click on New Workflow button
		
		Thread.sleep(5000);
		
		String actualString = driver.findElement(By.xpath("//p[contains(text(),'New Workflow')]")).getText();
		
		Assert.assertTrue(actualString.contains("New Workflow"));	// Verify the new workflow page is opened successfully

	}
	
	
	@Test
	public void TC_02_Generate_Workflow_With_N_Step() throws InterruptedException, AWTException, Exception {
		
		select_Outpainting("Outpainting");
		
		
		WebElement add_New_Step = driver.findElement(By.xpath("//button[@type='button' and @class='gap-1 text-atherGray-0 px-3 min-h-[2rem] font-semibold relative overflow-hidden select-none text-sm bg-atherPurple-500 hover:bg-atherPurple-400 active:bg-atherPurple-600 disabled:bg-atherPurple-700 w-10 h-10 cursor-pointer flex items-center justify-center rounded-full']"));

		add_New_Step.click(); //Add step 2

		select_Sketch_To_Image("Sketch To Image");
		
		add_New_Step.click(); //Add step 3
		
		select_Upscaler("Upscaler");
//	
//		add_New_Step.click(); //Add step 4
//		
//		select_Image_Generator("Image Generator");

		driver.findElement(By.xpath("//button[@type='submit' and text()='Generate']")).click(); // Click "Generate" button

		Thread.sleep(5000);
	}
	
	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}
