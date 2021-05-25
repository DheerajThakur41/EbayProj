package base;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;


public class Driver extends AppiumService {
	
	Logger logger = LoggerFactory.getLogger(Driver.class);

	public static DesiredCapabilities capabilities;
	public static AndroidDriver<AndroidElement> aDriver;

	@Parameters({ "deviceName_","udid_","platformVersion_","App_Package_Name_","App_Activity_Name_"})
	@BeforeTest(alwaysRun=true)
	public void init(String deviceName_,String udid_,String platformVersion_,String App_Package_Name_,String App_Activity_Name_) throws InterruptedException{


		try {

			File apkPath = new File("APK/Amazon_shopping1.apk");

			capabilities = new DesiredCapabilities();
			capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName_);
			capabilities.setCapability(MobileCapabilityType.UDID, udid_);
			capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.ANDROID_UIAUTOMATOR2);
			capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion_);
			capabilities.setCapability("autoGrantPermissions", true);
			capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 200);
			//capabilities.setCapability(MobileCapabilityType.FULL_RESET, true);
			capabilities.setCapability("autoGrantPermissions", true);

			capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
			capabilities.setCapability(MobileCapabilityType.APP, apkPath.getAbsolutePath());

			capabilities.setCapability("appPackage", App_Package_Name_);
			capabilities.setCapability("appActivity", App_Activity_Name_);
			aDriver = new AndroidDriver<AndroidElement>(appiumServerURL, capabilities);
			aDriver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
			
			

		} catch (Exception e) {

			logger.error(e.getMessage());
			quit();
			System.exit(1);

		}
	}


	/**
	 * This method quits the app and kills androidDriver process.
	 */
	public void quit() {
		try {

			if ( aDriver != null ) {
				aDriver.closeApp();
				aDriver.quit();
			}

		} catch (Exception e) {

			logger.error(e.getMessage());
		}
	}













}
