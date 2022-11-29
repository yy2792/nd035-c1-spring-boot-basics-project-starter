package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.File;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doMockSignUp(String firstName, String lastName, String userName, String password){
		// Create a dummy account for logging in later.

		// Visit the sign-up page.
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		driver.get("http://localhost:" + this.port + "/signup");
		webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));
		
		// Fill out credentials
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.click();
		inputFirstName.sendKeys(firstName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.click();
		inputLastName.sendKeys(lastName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.click();
		inputUsername.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.click();
		inputPassword.sendKeys(password);

		// Attempt to sign up.
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
		WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
		buttonSignUp.click();

		/* Check that the sign up was successful. 
		// You may have to modify the element "success-msg" and the sign-up 
		// success message below depening on the rest of your code.
		*/
		Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("You successfully signed up!"));
	}

	
	
	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doLogIn(String userName, String password)
	{
		// Log in to our dummy account.
		driver.get("http://localhost:" + this.port + "/login");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement loginUserName = driver.findElement(By.id("inputUsername"));
		loginUserName.click();
		loginUserName.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement loginPassword = driver.findElement(By.id("inputPassword"));
		loginPassword.click();
		loginPassword.sendKeys(password);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
		WebElement loginButton = driver.findElement(By.id("login-button"));
		loginButton.click();

		webDriverWait.until(ExpectedConditions.titleContains("Home"));

	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling redirecting users 
	 * back to the login page after a succesful sign up.
	 * Read more about the requirement in the rubric: 
	 * https://review.udacity.com/#!/rubrics/2724/view 
	 */
	@Test
	public void testRedirection() {
		// Create a test account
		doMockSignUp("Redirection","Test","RT","123");
		
		// Check if we have been redirected to the log in page.
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling bad URLs 
	 * gracefully, for example with a custom error page.
	 * 
	 * Read more about custom error pages at: 
	 * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
	 */
	@Test
	public void testBadUrl() {
		// Create a test account
		doMockSignUp("URL","Test","UT","123");
		doLogIn("UT", "123");
		
		// Try to access a random made-up URL.
		driver.get("http://localhost:" + this.port + "/some-random-page");
		Assertions.assertFalse(driver.getPageSource().contains("Whitelabel Error Page"));
	}


	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling uploading large files (>1MB),
	 * gracefully in your code. 
	 * 
	 * Read more about file size limits here: 
	 * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
	 */
	@Test
	public void testLargeUpload() throws Exception {
		// Create a test account
		doMockSignUp("Large File","Test","LFT","123");
		doLogIn("LFT", "123");

		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		String fileName = "upload5m.zip";

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
		fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();
		try {
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success-msg")));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Large File upload failed");
			throw new Exception("Large File upload failed");
		}
		Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));

	}

	@Test
	public void addNotes() {
		try {
			// Create a test account
			doMockSignUp("Large Note", "Test", "LNT", "123");
			doLogIn("LNT", "123");

			// Create notes
			WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

			WebElement noteTab = driver.findElement(By.id("nav-notes-tab"));
			noteTab.click();

			Thread.sleep(500);

			WebElement showNotes = driver.findElement(By.id("showNotes"));
			showNotes.click();

			webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
			WebElement noteTitle = driver.findElement(By.id("note-title"));
			noteTitle.click();
			noteTitle.sendKeys("Title1");

			webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
			WebElement noteDescription = driver.findElement(By.id("note-description"));
			noteDescription.click();
			noteDescription.sendKeys("Description1");


			WebElement noteButton = driver.findElement(By.id("saveNotes"));
			noteButton.click();

		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}

		WebElement noteTab = driver.findElement(By.id("nav-notes-tab"));
		noteTab.click();

		Assertions.assertTrue(driver.getPageSource().contains("Description1"));
	}

	@Test
	public void editNotes() {
		try {
			addNotes();

			// Create notes
			WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

			WebElement noteTab = driver.findElement(By.id("nav-notes-tab"));
			noteTab.click();

			Thread.sleep(500);

			WebElement editNotes = driver.findElement(By.id("editNotes"));
			editNotes.click();

			webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
			WebElement noteTitle = driver.findElement(By.id("note-title"));
			noteTitle.clear();
			noteTitle.sendKeys("Title2");

			webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
			WebElement noteDescription = driver.findElement(By.id("note-description"));
			noteDescription.clear();
			noteDescription.sendKeys("Description2");


			WebElement saveNotes = driver.findElement(By.id("saveNotes"));
			saveNotes.click();

			// Redirect home page
			driver.get("http://localhost:" + this.port + "/home");

		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Delete notes
	 */
	@Test
	public void deleteNotes() {
		try {
			// Create notes
			addNotes();

			WebElement noteTab = driver.findElement(By.id("nav-notes-tab"));
			noteTab.click();

			Thread.sleep(500);

			WebElement deleteNotes = driver.findElement(By.id("deleteNotes"));
			deleteNotes.click();

			// Redirect home page
			driver.get("http://localhost:" + this.port + "/home");

		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	public void addCredentials() {
		try{
			// Create a test account
			doMockSignUp("Large File", "Test", "LFT", "123");
			doLogIn("LFT", "123");

			// Create notes
			WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

			WebElement credentialsTab = driver.findElement(By.id("nav-credentials-tab"));
			credentialsTab.click();

			Thread.sleep(500);

			WebElement showCredentials = driver.findElement(By.id("newCredentials"));
			showCredentials.click();

			webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
			WebElement credentialUrl = driver.findElement(By.id("credential-url"));
			credentialUrl.click();
			credentialUrl.sendKeys("URL1");

			webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-username")));
			WebElement credentialUsername = driver.findElement(By.id("credential-username"));
			credentialUsername.click();
			credentialUsername.sendKeys("username1");

			webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password")));
			WebElement credentialPassword = driver.findElement(By.id("credential-password"));
			credentialPassword.click();
			credentialPassword.sendKeys("password1");


			WebElement credentialButton = driver.findElement(By.id("saveCredentials"));
			credentialButton.click();
		}
		catch (InterruptedException e) {
			throw new RuntimeException(e);
		}

	}

	@Test
	public void editCredentials() {
		try {
			// Create Credentials
			addCredentials();

			WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

			WebElement noteTab = driver.findElement(By.id("nav-credentials-tab"));
			noteTab.click();

			Thread.sleep(500);

			WebElement editNotes = driver.findElement(By.id("editCredential"));
			editNotes.click();

			webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
			WebElement credentialUrl = driver.findElement(By.id("credential-url"));
			credentialUrl.clear();
			credentialUrl.sendKeys("URL2");

			webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-username")));
			WebElement credentialUsername = driver.findElement(By.id("credential-username"));
			credentialUsername.clear();
			credentialUsername.sendKeys("username2");

			webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password")));
			WebElement credentialPassword = driver.findElement(By.id("credential-password"));
			credentialPassword.clear();
			credentialPassword.sendKeys("password2");


			WebElement saveNotes = driver.findElement(By.id("saveCredentials"));
			saveNotes.click();

			// Redirect home page
			driver.get("http://localhost:" + this.port + "/home");

		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	public void deleteCredentials() {
		try {
			// Create Credentials
			addCredentials();

			WebElement noteTab = driver.findElement(By.id("nav-credentials-tab"));
			noteTab.click();

			Thread.sleep(500);

			WebElement deleteNotes = driver.findElement(By.id("deleteCredentials"));
			deleteNotes.click();

			// Redirect home page
			driver.get("http://localhost:" + this.port + "/home");

		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

}
