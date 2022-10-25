package com.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PageObjects {
	WebDriver driver;

	public PageObjects(WebDriver driver) {

		this.driver = driver;
	}

	By emailIdButton = By.xpath("//span[@data-analytics-button='loginWithGmailButton']");

	public void clickEmaiIdButton() {
		driver.findElement(emailIdButton).click();

	}
}
