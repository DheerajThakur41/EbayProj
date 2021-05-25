package base;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Reporting  extends Driver implements ITestListener {
	
	public static ExtentReports extent = ExtentManager.getInstance();
	public static ThreadLocal<ExtentTest> extenttest = new ThreadLocal<ExtentTest>();
	Logger logger = LoggerFactory.getLogger(Reporting.class);

	private static String getTestMethodName(ITestResult iTestResult) {
		return iTestResult.getMethod().getConstructorOrMethod().getName();
	}
	public void onTestStart(ITestResult result) {
		
		ExtentTest test = extent.createTest(result.getTestClass().getName() +"::"+ result.getMethod().getMethodName());
		extenttest.set(test);
		
	}

	public void onTestSuccess(ITestResult result) {
		
		String logText = "<b> Test Method" + result.getMethod().getMethodName() +" Sucessful</b>";
		Markup m = MarkupHelper.createLabel(logText, ExtentColor.GREEN);
		extenttest.get().log(Status.PASS, m);
		
	}

	public void onTestFailure(ITestResult result) {
		logger.info(getTestMethodName(result) + " test is failed.");

		Object testClass = result.getInstance();
		String base64Screenshotn =
				"data:image/png;base64," + ((TakesScreenshot) Objects.requireNonNull(aDriver)).getScreenshotAs(OutputType.BASE64);


		try {
			extenttest.get().log(Status.FAIL, (Markup) extenttest.get().addScreenCaptureFromPath(base64Screenshotn));
		} catch (IOException e) {
			logger.info("Error during Take screenshot",true);
			e.printStackTrace();
		}


		String logText = "<b> Test Method" + result.getMethod().getMethodName() +"Failed</b>";
		Markup m = MarkupHelper.createLabel(logText, ExtentColor.RED);
		extenttest.get().log(Status.FAIL, m);
		
	}

	public void onFinish(ITestContext context) {
		
		extent.flush();
		
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		
	}


}
