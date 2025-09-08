package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ExtentReportManager implements ITestListener {

    public ExtentSparkReporter extentSparkReporter;
    public ExtentReports extentReports;
    public ExtentTest extentTest;

    public void onStart(ITestContext context) {
        extentSparkReporter = new ExtentSparkReporter(System.getProperty("user.dir")+"/reports/myReport.html");

        extentSparkReporter.config().setDocumentTitle("Automation Report");
        extentSparkReporter.config().setReportName("Functional Testing");
        extentSparkReporter.config().setTheme(Theme.DARK);

        extentReports = new ExtentReports();
        extentReports.attachReporter(extentSparkReporter);

        extentReports.setSystemInfo("Computer Name","localhost");
        extentReports.setSystemInfo("Environment","QA");


    }

//    public void onTestStart(ITestResult result) {
//        System.out.println("Test started");
//    }

    public void onTestSuccess(ITestResult result) {
        extentTest = extentReports.createTest(result.getName());
        extentTest.log(Status.PASS, "Test Case Passed: "+ result.getName());
    }

    public void onTestFailure(ITestResult result) {
        extentTest = extentReports.createTest(result.getName());
        extentTest.log(Status.FAIL, "Test Case Failed: "+ result.getName());
        extentTest.log(Status.FAIL, "Test Case Failed due to: "+ result.getThrowable());
    }

    public void onTestSkipped(ITestResult result) {
        extentTest = extentReports.createTest(result.getName());
        extentTest.log(Status.SKIP, "Test Case Passed: "+ result.getName());
    }

    public void onFinish(ITestContext context) {
        extentReports.flush();
        System.out.println("Test cases execution finished.");
    }
}
