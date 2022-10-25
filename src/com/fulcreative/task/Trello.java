package com.fulcreative.task;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Trello {

	WebDriver driver;

	@BeforeClass
	public void initBrowser() {
		WebDriverManager.chromedriver().setup();

//		System.setProperty("webdriver.chrome.driver", "/Users/mansoor/eclipse-workspace/SeleniumBrushUp2022/src/Drivers/chromedriver");

		ChromeOptions options = new ChromeOptions();

		/// Run this command in cmd or Terminal to interact with existing chrome Browser
		// Applications/Google\ Chrome.app/Contents/MacOS/Google\ Chrome
		/// --remote-debugging-port=1998 --user-data-dir="~/ChromeProfile"

		options.setExperimentalOption("debuggerAddress", "localhost:1998");
		driver = new ChromeDriver(options);

		driver.manage().window().maximize();
		WebDriverWait wait = new WebDriverWait(driver, 40);
		driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);

	}

	@Test
	public void testFlow() throws InterruptedException {
		Actions builder = new Actions(driver);
		WebDriverWait wait = new WebDriverWait(driver, 40);

		// Launching Trello URL
		driver.get("https://trello.com/");

		WebElement loginBtn = driver.findElement(By.linkText("Log in"));
		loginBtn.click();

		WebElement emailIdButton = driver
				.findElement(By.xpath("//span[@data-analytics-button='loginWithGmailButton']"));
		emailIdButton.click();

		Thread.sleep(5000); // Not recommended for good practice, But used for flow Execution
							// understandability

		//Creating a new board
		WebElement createNewBoardBtn = driver.findElement(By.xpath("//li[@data-test-id='create-board-tile']//div"));
		wait.until(ExpectedConditions.elementToBeClickable(createNewBoardBtn));
		createNewBoardBtn.click();

		Thread.sleep(5000); // Not recommended for good practice, But used for flow Execution
							// understandability

		WebElement boardTitle = driver.findElement(By.xpath("//input[@type='text']"));
		boardTitle.sendKeys("Full Creative");

		WebElement createBtn = driver.findElement(By.xpath("//button[contains(text(),'Create')]"));
		createBtn.click();

		Thread.sleep(7000); // Not recommended for good practice, But used for flow Execution
							// understandability

		//Creating a List A
		WebElement listA = driver.findElement(By.xpath("//textarea[@aria-label='To Do']"));
		listA.clear();
		wait.until(ExpectedConditions.elementToBeClickable(listA));
		builder.contextClick(listA).sendKeys("List A").tick().build().perform();
		Thread.sleep(5000); // Not recommended for good practice, But used for flow Execution
							// understandability

//		WebElement addListBtn = driver.findElement(By.xpath("//input[@value='Add list']"));
//		addListBtn.click();

		//Creating a List B
		WebElement listB = driver.findElement(By.xpath("//textarea[@aria-label='Doing']"));
		listB.clear();
		wait.until(ExpectedConditions.elementToBeClickable(listB));
		builder.contextClick(listB).sendKeys("List B").tick().click().build().perform();
		Thread.sleep(3000); // Not recommended for good practice, But used for flow Execution
							// understandability

//
//		addListBtn.click();
//
//		WebElement addACard = driver.findElement(By.xpath(
//				"//body[1]/div[1]/div[2]/div[1]/div[1]/main[1]/div[1]/div[2]/div[2]/div[1]/div[1]/div[3]/div[2]/div[1]/div[1]/div[3]/a[1]/span[2]"));
//		addACard.click();

		//Creating a card
		WebElement cardTextEnter = driver
				.findElement(By.xpath("//textarea[@placeholder='Enter a title for this card…']"));
		cardTextEnter.sendKeys("Hello World");

		WebElement addCardBtn = driver.findElement(By.xpath("//input[@value='Add card']"));
		addCardBtn.click();
		Thread.sleep(3000); // Not recommended for good practice, But used for flow Execution
							// understandability

		//Drag and Drop a card from List A to List B
		WebElement listA_sourceCard = driver.findElement(By.xpath("//span[@class='list-card-title js-card-name']"));

		WebElement listB_DestinationCard = driver.findElement(By.xpath(
				"//body[1]/div[1]/div[2]/div[1]/div[1]/main[1]/div[1]/div[2]/div[2]/div[1]/div[1]/div[3]/div[2]/div[2]/div[1]/div[3]/a[1]"));

		builder.dragAndDrop(listA_sourceCard, listB_DestinationCard).build().perform();

		//Getting the x and y coordinates of the card that moved from List A to List B 
		driver.navigate().refresh();
		Thread.sleep(3000);
		wait.until(
				ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@class='list-card-title js-card-name']")));

		/*Faced stale Element exception while trying to get co ordinates of card after moved from List A to List B
		 * As a workaround, So tried to Re locate the element xyCoOrdinatesElement after moving from List A to List B
		 * Handled the exception in order to avoid interruption during execution 
		 * 
		 * */
		
		WebElement xyCoOrdinatesElement = driver.findElement(By.xpath("//span[@class='list-card-title js-card-name']"));

		try {
			Point point = xyCoOrdinatesElement.getLocation();
			System.out.println("Element's Position from left side is: " + point.getX() + " pixels.");
			System.out.println("Element's Position from top is: " + point.getY() + " pixels.");

		} catch (Exception e) {
			e.getMessage();
		}

		// Log out Steps

		WebElement profileIcon = driver.findElement(By.xpath("//span[@title='Mansoor Ahmed (mansoorahmed78)']"));
		profileIcon.click();

		WebElement logOutBtn = driver.findElement(By.xpath("//button[@data-test-id='header-member-menu-logout']"));
		logOutBtn.click();

		WebElement logOutBtn2 = driver.findElement(By.xpath("//button[@id='logout-submit']"));
		logOutBtn2.click();
	
	}

	@AfterClass
	public void tearDown() {
		 driver.close();

	}

}
