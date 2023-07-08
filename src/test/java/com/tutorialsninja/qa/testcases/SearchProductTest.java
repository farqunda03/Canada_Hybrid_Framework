package com.tutorialsninja.qa.testcases;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.tutorialsninja.qa.RetryFailedTestCases.MyRetry;
import com.tutorialsninja.qa.pages.HomePage;
import com.tutorialsninja.qa.pages.SearchProductPage;
import com.tutorialsninja.qa.testBase.TestBase;

public class SearchProductTest extends TestBase{
	public WebDriver driver;
	SoftAssert softassert = new SoftAssert();
	public SearchProductPage searchproductpage;
	public HomePage homepage;
	
	@BeforeMethod
	public void setup() {
		driver = initializeBrowserAndOpenApplication("chrome");
	}
	@Test(priority = 1, retryAnalyzer = MyRetry.class)
	public void verifyValidProductSearch() {
		homepage = new HomePage(driver);
		homepage.enterProductNameInSearchbox(testDataProp.getProperty("validProduct"));
		searchproductpage = homepage.clickOnSearchButton();//this returns a searchproductpage
		softassert.assertTrue(searchproductpage.validateDisplayOfValidProduct());
		softassert.assertAll();
	}
	
	@Test(priority = 2 , retryAnalyzer = MyRetry.class)
	public void verifyInvalidProductSearch() {
		homepage = new HomePage(driver);
		homepage.enterProductNameInSearchbox(testDataProp.getProperty("invalidProduct"));
		searchproductpage = homepage.clickOnSearchButton();
		softassert.assertFalse(searchproductpage.validateDisplayOfInvalidOrNoProduct());
		softassert.assertAll();
		
	}
	
	@Test(priority = 3, retryAnalyzer = MyRetry.class, dependsOnMethods = "verifyInvalidProductSearch")
	public void VerifySearchWithNoProduct() {
		homepage = new HomePage(driver);
		searchproductpage = homepage.clickOnSearchButton();
		softassert.assertTrue(searchproductpage.validateDisplayOfInvalidOrNoProduct());
		softassert.assertAll();
	}
	
	@AfterMethod
	public void teardown() {
		driver.quit();
	}

}
