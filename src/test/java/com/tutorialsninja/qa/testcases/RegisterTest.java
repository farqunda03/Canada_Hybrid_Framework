package com.tutorialsninja.qa.testcases;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import com.tutorialsninja.qa.pages.AccountSucessPage;
import com.tutorialsninja.qa.pages.HomePage;
import com.tutorialsninja.qa.pages.RegisterPage;
import com.tutorialsninja.qa.testBase.TestBase;
import com.tutorialsninja.qa.testData.ExcelData;
import com.tutorialsninja.qa.utilities.Utils;

public class RegisterTest extends TestBase {
	public WebDriver driver;
	SoftAssert softassert = new SoftAssert();
	public RegisterPage registerpage;
	public HomePage homepage;
	public AccountSucessPage accountsucesspage;

	@BeforeMethod
	public void setup() {
		driver = initializeBrowserAndOpenApplication("chrome");
		homepage = new HomePage(driver);
		homepage.clickOnMyAccount();
		registerpage = homepage.selectRegisterOption();// this will return a new register page

	}

	@Test(priority = 1, dataProvider = "TN Register", dataProviderClass = ExcelData.class)
	public void registerWithmandaoryFields(String firstname, String lastname, String email, String telephone,
			String password, String confirmpassword) {

		registerpage.enterFirstname(firstname);
		registerpage.enterLastname(lastname);
		registerpage.enterEmail(Utils.emailWithDateTimeStamp());
		registerpage.enterTelephone(telephone);
		registerpage.enterPassword(password);
		registerpage.enterConfirmPassword(confirmpassword);
		registerpage.clickOnPrivacyPolicyCheckBox();
		accountsucesspage = registerpage.clickOnContinueButton();// returns a AccountSucessPage
		softassert.assertTrue(accountsucesspage.validateDisplayAccountCreatedSucessMessage());
		softassert.assertAll();
	}

	@Test(priority = 2)
	public void registerWithAllFields() {
		RegisterPage registerpage = new RegisterPage(driver);
		registerpage.enterFirstname(testDataProp.getProperty("firstname"));
		registerpage.enterLastname(testDataProp.getProperty("lastname"));
		registerpage.enterEmail(Utils.emailWithDateTimeStamp());
		registerpage.enterTelephone(testDataProp.getProperty("telephone"));
		registerpage.enterPassword(prop.getProperty("ValidPassword"));
		registerpage.enterConfirmPassword(prop.getProperty("ValidPassword"));
		registerpage.selectYesNewsLetterRadioButton();
		registerpage.clickOnPrivacyPolicyCheckBox();
		accountsucesspage = registerpage.clickOnContinueButton();// returns a AccountSucessPage
	    softassert.assertTrue(accountsucesspage.validateDisplayAccountCreatedSucessMessage());
		softassert.assertAll();
		
	}

	@Test(priority = 3)
	public void registerWithExistingEmailId() {

		registerpage.enterFirstname(testDataProp.getProperty("firstname"));
		registerpage.enterLastname(testDataProp.getProperty("lastname"));
		registerpage.enterEmail(prop.getProperty("ValidEmail"));
		registerpage.enterTelephone(testDataProp.getProperty("telephone"));
		registerpage.enterPassword(prop.getProperty("ValidPassword"));
		registerpage.enterPassword(prop.getProperty("ValidPassword"));
		registerpage.selectYesNewsLetterRadioButton();
		registerpage.clickOnPrivacyPolicyCheckBox();
		registerpage.clickOnContinueButton();

		String actualWarningMessage = registerpage.retrieveDuplicateWarningMessage();
		String expectedWarningMessage = testDataProp.getProperty("duplicateEmailWarning");
		softassert.assertEquals(actualWarningMessage, expectedWarningMessage);
		softassert.assertAll();
	}

	@Test(priority = 4)
	public void registerWithNoFields() {

		registerpage.clickOnContinueButton();
		String actualFirstNameWarningMessage = registerpage.retrieveFirstNameWarningMessage();
		String expectedFirstNameWarningMessage = testDataProp.getProperty("firstnameWarning");
		softassert.assertTrue(actualFirstNameWarningMessage.contains(expectedFirstNameWarningMessage));

		String actualLastNameWarningMessage = registerpage.retrieveLastNameWarningMessage();
		String expectedLastNameWarningMessage = testDataProp.getProperty("lastnameWarning");
		softassert.assertTrue(actualLastNameWarningMessage.contains(expectedLastNameWarningMessage));

		String actualEmailWarningMessage = registerpage.retrieveEmailWarningMessage();
		String expectedEmailWarningMessage = testDataProp.getProperty("emailWarning");
		softassert.assertTrue(actualEmailWarningMessage.contains(expectedEmailWarningMessage));

		String actualTelephoneWarningMessage = registerpage.retrieveTelephoneWarningMessage();
		String expectedTelephoneWarningMessage = testDataProp.getProperty("telephoneWarning");
		softassert.assertTrue(actualTelephoneWarningMessage.contains(expectedTelephoneWarningMessage));

		String actualPasswordWarningMessage = registerpage.retrievePasswordWarningMessage();
		String expectedPasswordWarningMessage = testDataProp.getProperty("passwordWarning");
		softassert.assertTrue(actualPasswordWarningMessage.contains(expectedPasswordWarningMessage));

		String actualPrivacyPolicyWarningMessage = registerpage.retrievePrivacyPolicyWarningMessage();
		String expectedPrivacyPolicyWarningMessage = testDataProp.getProperty("privacypolicyWarning");
		softassert.assertTrue(actualPrivacyPolicyWarningMessage.contains(expectedPrivacyPolicyWarningMessage));

		softassert.assertAll();

	}

	@AfterMethod
	public void teardown() {
		driver.quit();
	}
}
