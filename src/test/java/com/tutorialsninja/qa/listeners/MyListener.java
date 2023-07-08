package com.tutorialsninja.qa.listeners;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.tutorialsninja.qa.ExtentReports.ExtentReporter;

public class MyListener implements ITestListener {
	public ExtentReports extentReport;
	public ExtentTest extentTest;
	public WebDriver driver;

	@Override
	public void onStart(ITestContext context) {
		System.out.println("Project Execution Started");
		try {
			extentReport = ExtentReporter.generateExtentReports();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}
	@Override
	public void onTestStart(ITestResult result) {
		extentTest =extentReport.createTest(result.getName());
		extentTest.log(Status.INFO, result.getName() + "started executing");
	}
		

	@Override
	public void onTestSuccess(ITestResult result) {
	
		extentTest.log(Status.PASS, result.getName() + "passed");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		
		try {
			driver = (WebDriver) result.getTestClass().getRealClass().getDeclaredField("driver").get(result.getInstance());
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			
			e.printStackTrace();
		}
		File source = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		String destinationPath = System.getProperty("user.dir")+"\\test-output\\Screenshots\\"+ result.getName() +".png";
		try {
			FileHandler.copy(source, new File(destinationPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		extentTest.log(Status.FAIL, result.getName() + "-> failed");
	}
	@Override
	public void onTestSkipped(ITestResult result) {
	
		extentTest.log(Status.SKIP, result.getName() + "-> skipped");
	}

	@Override
	public void onFinish(ITestContext context) {
		System.out.println("Project Execution Finished");
		extentReport.flush();
		String pathofExtentReport = System.getProperty("user.dir")+"\\test-output\\ExtentReport\\extentreport.html";
		File extentReportpath = new File(pathofExtentReport);
		try {
			Desktop.getDesktop().browse(extentReportpath.toURI());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
