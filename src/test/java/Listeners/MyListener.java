package Listeners;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;


public class MyListener implements ITestListener {
    protected static ExtentReports reports;
    protected static ExtentTest test;
    public void onTestStart(ITestResult result) {
        System.out.println("on test start");
        test = reports.startTest(result.getMethod().getDescription());
    }
    public void onTestSuccess(ITestResult result) {
        System.out.println("on test success");
        test.log(LogStatus.INFO,"On test success");
        test.log(LogStatus.PASS, result.getMethod().getMethodName() + "test has passed");
    }
    public void onTestFailure(ITestResult result) {
        System.out.println("on test failure");
        test.log(LogStatus.FAIL, result.getMethod().getMethodName() + "test is failed", result.getThrowable());
    }
    public void onTestSkipped(ITestResult result) {
        System.out.println("on test skipped");
        test.log(LogStatus.SKIP, result.getMethod().getMethodName() + "test is skipped",result.getThrowable());

    }
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        System.out.println("on test success within percentage");
    }
    public void onStart(ITestContext context) {
        System.out.println("on start");
        reports = new ExtentReports("Reports.html");

    }
    public void onFinish(ITestContext context) {
        System.out.println("on finish");
        reports.endTest(test);
        reports.flush();
    }
}