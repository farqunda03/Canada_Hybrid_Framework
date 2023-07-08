package com.tutorialsninja.qa.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AccountSucessPage {

	public WebDriver driver;

	@FindBy(xpath = "//p[text() = 'Congratulations! Your new account has been successfully created!']")
	private WebElement accountCreatedSucessMessage;

	public AccountSucessPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public boolean validateDisplayAccountCreatedSucessMessage() {
		boolean displayStatus = accountCreatedSucessMessage.isDisplayed();
		return displayStatus;
	}
}
