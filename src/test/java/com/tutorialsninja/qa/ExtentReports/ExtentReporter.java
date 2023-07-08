package com.tutorialsninja.qa.ExtentReports;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReporter {
	
	public static ExtentReports extentReport;
	public static ExtentSparkReporter sparkReporter;
	public static File extentReportFile;
	public static Properties prop;
	public static FileInputStream ip; 

	public static ExtentReports generateExtentReports() throws Exception {
		//step 1:we have to get the Maven dependency of Extent Reports in pom.xml file
		//step 2: we have to create the object of ExtentReports class
		
	    extentReport = new ExtentReports();
		
		//Step 3: Create the object of File Class and pass the path of .html file inside the constructor
		extentReportFile = new File(System.getProperty("user.dir")+"\\test-output\\ExtentReport\\extentreport.html");
		
		//Step 4: Create the object of ExtentSparkReporter class and pass the file referance in the constructor
	    sparkReporter = new ExtentSparkReporter(extentReportFile);
		
		//Step 5: Using the SparkReporter we can configure lot of things
		sparkReporter.config().setTheme(Theme.DARK);
		sparkReporter.config().setReportName("TN Automation Results");
		sparkReporter.config().setDocumentTitle("TNReportTitle|Automation|Result");
		sparkReporter.config().setTimeStampFormat("MM/dd/yyyy hh:mm:ss");
		
		//Step 6:we need to attach ExtentReport to sparkReporter
		extentReport.attachReporter(sparkReporter);	
		
		//Step 7: Additional information and create a properties file and read from it
		prop = new Properties();
		ip = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\java\\com\\tutorialsninja\\qa\\config\\config.properties");
	    prop.load(ip);
	    
	    //Application url,browser, username, password, Operating system, Java version, name of SDET
	    
	    extentReport.setSystemInfo("application url", prop.getProperty("url"));
	    extentReport.setSystemInfo("browser name", prop.getProperty("browser"));
	    extentReport.setSystemInfo("username", prop.getProperty("ValidEmail"));
	    extentReport.setSystemInfo("password", prop.getProperty("ValidPassword"));
	    extentReport.setSystemInfo("operating system",System.getProperty("os.name"));
	    extentReport.setSystemInfo("operating systemversion",System.getProperty("os.version"));
	    extentReport.setSystemInfo("SDET name",System.getProperty("user.name"));
	    extentReport.setSystemInfo("Java Runtime Version",System.getProperty("java.runtime.version"));
	    
	    //Step 8: return the extentReport
	    return extentReport;
	}
}
