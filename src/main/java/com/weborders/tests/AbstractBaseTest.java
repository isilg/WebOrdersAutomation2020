package com.weborders.tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.weborders.utilities.BrowserUtilities;
import com.weborders.utilities.ConfigurationReader;
import com.weborders.utilities.Driver;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import java.io.IOException;


public abstract class AbstractBaseTest {
    protected WebDriver driver = Driver.getDriver();
    protected static ExtentReports extentReports;  //we use these extentReports, extentHtmlReporter, and
                                           //extentTest in other classes so it may give error sometimes
                                           //to prevent that we used static
    protected static ExtentHtmlReporter extentHtmlReporter;
    protected static ExtentTest extentTest;


    @BeforeTest
    public void beforeTest(){
        extentReports = new ExtentReports();
        String reportPath = "";

        //For windows and mac operators
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            reportPath = System.getProperty("user.dir") + "\\test-output\\report.html";
        } else {
            reportPath = System.getProperty("user.dir") + "/test-output/report.html";
        }
        extentHtmlReporter = new ExtentHtmlReporter(reportPath);
        extentReports.attachReporter(extentHtmlReporter);
        extentHtmlReporter.config().setReportName("WebOrders Automation");
    }

    @AfterTest
    public void afterMethod(){

    }

    @BeforeMethod
    public void setup(){
        driver.get(ConfigurationReader.getProperty("url"));  //open browser
        driver.manage().window().maximize();
    }

    @AfterMethod
    public void teardown(ITestResult testResult){   //to check test result we give this parameter
        if(testResult.getStatus() == ITestResult.FAILURE){
            String screenshotLocation = BrowserUtilities.getScreenshot(testResult.getName());  //returns screenshot location
            try {
                extentTest.fail(testResult.getName());   //test name that failed
                extentTest.addScreenCaptureFromPath(screenshotLocation);  //screenshot as an evidence
                extentTest.fail(testResult.getThrowable());    //error message
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to attach screenshot");
            }
        } else if (testResult.getStatus() == ITestResult.SUCCESS) {
            extentTest.pass(testResult.getName());
        } else if (testResult.getStatus() == ITestResult.SKIP) {
            extentTest.skip(testResult.getName());
        }

        BrowserUtilities.wait(3);
        Driver.closeDriver();  //we should close driver doesn't matter test passed or failed
    }
}
