package com.tutorialsninja.qa.testcases;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.tutorialsninja.qa.pages.AccountPage;
import com.tutorialsninja.qa.pages.HomePage;
import com.tutorialsninja.qa.pages.LoginPage;
import com.tutorialsninja.qa.testBase.TestBase;
import com.tutorialsninja.qa.testData.ExcelData;
import com.tutorialsninja.qa.utilities.Utils;

public class LoginTest extends TestBase {

	public WebDriver driver;
	SoftAssert softassert = new SoftAssert();
	public LoginPage loginpage;
	public AccountPage accountpage;
	public HomePage homepage;

	public LoginTest() {
		super();
	}

	@BeforeMethod
	public void setup() {
		driver = initializeBrowserAndOpenApplication("chrome");
		homepage = new HomePage(driver);
		loginpage = homepage.navigateToLoginPage();
		
	}

	@Test(priority = 1, dataProvider = "TN Login", dataProviderClass = ExcelData.class)
	public void loginWithValidCredentials(String email, String password) {

		accountpage = loginpage.navigateToAccountPage(email, password);
		softassert.assertTrue(accountpage.verifyEditAccountInfoLinkIsDisplayed());
		softassert.assertAll();

	}

	@Test(priority = 2)
	public void loginWithValidEmailInvalidPassword() {

		loginpage.enterEmailInEmailTextBox(prop.getProperty("ValidEmail"));
		loginpage.enterPasswordInPasswordTextBox(testDataProp.getProperty("invalidPassword"));
		loginpage.clickOnLoginButton();
		String actualWarningMessage = loginpage.retrieveEmailPasswordMismatch();
		String expectedWarningMessage = testDataProp.getProperty("emailPasswordMismatch");
		softassert.assertTrue(actualWarningMessage.contains(expectedWarningMessage));
		softassert.assertAll();

	}

	@Test(priority = 3)
	public void loginWithInValidEmailvalidPassword() {

		loginpage.enterEmailInEmailTextBox(Utils.emailWithDateTimeStamp());
		loginpage.enterPasswordInPasswordTextBox(prop.getProperty("ValidPassword"));
		loginpage.clickOnLoginButton();
		String actualWarningMessage = loginpage.retrieveEmailPasswordMismatch();
		String expectedWarningMessage = testDataProp.getProperty("emailPasswordMismatch");
		softassert.assertTrue(actualWarningMessage.contains(expectedWarningMessage));
		softassert.assertAll();

	}

	@Test(priority = 4)
	public void loginWithInvalidCredentials() {

		loginpage.enterEmailInEmailTextBox(Utils.emailWithDateTimeStamp());
		loginpage.enterPasswordInPasswordTextBox(testDataProp.getProperty("invalidPassword"));
		loginpage.clickOnLoginButton();
		String actualWarningMessage = loginpage.retrieveEmailPasswordMismatch();
		String expectedWarningMessage = testDataProp.getProperty("emailPasswordMismatch");
		softassert.assertTrue(actualWarningMessage.contains(expectedWarningMessage));
		softassert.assertAll();

	}

	@Test(priority = 5)
	public void loginWithoutCredentials() {

		loginpage.clickOnLoginButton();
		String actualWarningMessage = loginpage.retrieveEmailPasswordMismatch();
		String expectedWarningMessage = testDataProp.getProperty("emailPasswordMismatch");
		softassert.assertTrue(actualWarningMessage.contains(expectedWarningMessage));
		softassert.assertAll();

	}

	@AfterMethod
	public void teardown() {
		driver.quit();
	}
}
