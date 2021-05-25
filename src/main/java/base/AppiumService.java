package base;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import io.appium.java_client.service.local.AppiumServerHasNotBeenStartedLocallyException;
import org.apache.commons.exec.OS;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

public class AppiumService {

	private AppiumDriverLocalService service;
	private AppiumServiceBuilder builder;
	private DesiredCapabilities cap;
	private  static int port = 4723;
	protected static URL appiumServerURL;
	public static String primary, secondary;
	private static String OS = System.getProperty("os.name").toLowerCase();
	private static String currentDir = System.getProperty("user.dir");



	@BeforeSuite
	public void presetup() {

//		stopServer();
		startServer();
		System.out.println("-----------------------Start Appium Service--------------------------------");

		cap = new DesiredCapabilities();
		cap.setCapability("noReset", false);

		try {
			appiumServerURL=new URL("http://127.0.0.1:4723/wd/hub");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
//		appiumServerURL = service.getUrl();


	}


	public static void startServer() {
		AppiumDriverLocalService service = null;
		if (isLUnix()) {
			service = buildAppiumServiceForLinux();
//			service = AppiumDriverLocalService.buildDefaultService();
		}
		else if (isMac())
			service = buildAppiumServiceForMac();
		else
			service = AppiumDriverLocalService.buildDefaultService();//For Windows

		if (!checkIfServerIsRunnning(port)) {
			service.start();
			appiumServerURL = service.getUrl();
			service.clearOutPutStreams();
			if (service == null || !service.isRunning()) {
				throw new AppiumServerHasNotBeenStartedLocallyException("An appium server node is not started!");
			}
		} else {
			System.out.println("Appium server is already running at " + port);
		}
	}


	/* @AfterSuite public void stopServer() { service.stop(); } */

	public static boolean isWindows() {
		return (OS.indexOf("win") >= 0);
	}

	public static boolean isMac() {
		return (OS.indexOf("mac") >= 0);
	}

	public static boolean isLUnix() {
		return (OS.indexOf("nix") >= 0
				|| OS.indexOf("nux") >= 0
				|| OS.indexOf("aix") > 0);
	}

	public static AppiumDriverLocalService buildAppiumServiceForLinux(){
		Map<String, String> environment = new HashMap<String, String>();
		String javaPath = System.getProperty("java.home");
		environment.put("PATH",
				javaPath + "/bin:" +
						"/home/" + System.getenv("USER") + "/Android/sdk/tools/:" +
						"/home/"+ System.getenv("USER") + "/Android/sdk/platform-tools/:" +
						"/usr/local/bin:"+
						"/usr/local/sbin:"+
						"/usr/bin:/bin:/usr/sbin:/sbin:/bin:/usr/bin"  +
						System.getenv("PATH")
		);
		environment.put("ANDROID_HOME", "/home/" + System.getenv("USER") + "/Android/sdk/");
		environment.put("JAVA_HOME", javaPath);

		String nodePath="/home/linuxbrew/.linuxbrew/bin/node";
		String appiumMainJs="/home/linuxbrew/.linuxbrew/lib/node_modules/appium/lib/main.js";

		return AppiumDriverLocalService
				.buildService(new AppiumServiceBuilder().usingDriverExecutable(new File(nodePath))
						.withAppiumJS(new File(appiumMainJs)).usingPort(port)
						.withArgument(GeneralServerFlag.SESSION_OVERRIDE)
						.withEnvironment(environment)
						.withLogFile(new File(currentDir+"/test-output/ServerLogs/server.log")));

	}
	public static AppiumDriverLocalService buildAppiumServiceForMac() {
		Map<String, String> environment = new HashMap<String, String>();
		String javaPath = System.getProperty("java.home");
		environment.put("PATH",
				javaPath + "/bin:" + "/Users/" + System.getenv("USER") + "/Library/Android/sdk/tools/:" + "/Users/"
						+ System.getenv("USER") + "/Library/Android/sdk/platform-tools/:" + "/usr/local/bin:"
						+ "/usr/bin:/bin:/usr/sbin:/sbin:/Library/Apple/usr/bin" + System.getenv("PATH"));
		environment.put("ANDROID_HOME", "/Users/" + System.getenv("USER") + "/Library/Android/sdk/");
		environment.put("JAVA_HOME", javaPath);

		return AppiumDriverLocalService
				.buildService(new AppiumServiceBuilder().usingDriverExecutable(new File("/usr/local/bin/node"))
						.withAppiumJS(new File("/usr/local/lib/node_modules/appium/build/lib/main.js")).usingPort(port)
						.withArgument(GeneralServerFlag.SESSION_OVERRIDE)
						.withEnvironment(environment)
						.withLogFile(new File(currentDir+"./test-output/ServerLogs/server.log")));
	}


	public static boolean checkIfServerIsRunnning(int port) {
		boolean isServerRunning = false;
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(port);
			serverSocket.close();
		} catch (IOException e) {
			// If control comes here, then it means that the port is in use
			isServerRunning = true;
		} finally {
			serverSocket = null;
		}
		return isServerRunning;
	}

	public void stopServer() {
		String[] commandMAC = { "/usr/bin/killall", "-KILL", "node" };
		String[] commandLinux = { "killall", "node" };
		String cmd = "cmd.exe /c taskkill /F /IM node.exe";
		try {
			if(isMac() )
				Runtime.getRuntime().exec(commandMAC);
			else if (isLUnix())
				Runtime.getRuntime().exec(commandLinux);
			else
				Runtime.getRuntime().exec(cmd);
			System.out.println("Appium server stopped.");
			Thread.sleep(10 * 1000);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
