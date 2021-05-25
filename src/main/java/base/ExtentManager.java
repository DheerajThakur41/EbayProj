package base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {
	
	public static ExtentReports extentReport;
	
	
	public static ExtentReports getInstance() {
		
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("ExtentReports/ebaytest.html");
		htmlReporter.config().setEncoding("utf-8");
		htmlReporter.config().setDocumentTitle("Ebay Automation report");
		htmlReporter.config().setDocumentTitle("Automation report");
		htmlReporter.config().setTheme(Theme.DARK);
		extentReport = new ExtentReports();
		extentReport.attachReporter(htmlReporter);
		return extentReport;
		
		
	}

}
