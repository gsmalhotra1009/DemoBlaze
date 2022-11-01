package com.demoBlaze.libraries;

import java.io.File;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.demoBlaze.config.Config;
import com.demoBlaze.config.PropertiesCache;
import com.demoBlaze.initdriver.DemoBlazeDriver;

import com.google.common.io.Files;

/**
 * This class will have Common methods which will be used through out the code.
 **/
public class GenericLib {

	public String currentDate;

	static WebDriverWait webDriverwait = new WebDriverWait(DemoBlazeDriver.getDriver(), Config.objectWaitTime);

	/**
	 * Name: openURL Description: Method is to launch the already created URL above
	 * opening the URL.
	 * 
	 * @author Gundeep SIngh
	 * @param urlName
	 **/

	public static void openURL(String urlName) throws Exception {
		try {
			DemoBlazeDriver.getDriver().get(urlName);
			DemoBlazeDriver.getDriver().manage().timeouts().pageLoadTimeout(Config.pageLoadTime, TimeUnit.SECONDS);
			Log.logInfo(GenericLib.class, "URL launched");
			GlobalVariables.testStatus = true;
		} catch (Exception e) {
			GlobalVariables.testStatus = false;
			Log.logError(GenericLib.class, "Error while launching " + e.getMessage());
		}
	}

	/**
	 * Name: getCurrentURL Description : This function will get the URL of the
	 * current User.
	 * 
	 * @author Gundeep Singh
	 **/
	public static String getCurrentURL() throws Exception {
		Log.logInfo(GenericLib.class, "Current URL is fetched");
		GlobalVariables.testStatus = true;
		return DemoBlazeDriver.getDriver().getCurrentUrl().trim();
	}

	/**
	 * Name: captureScreenShot Description: This method will take one parameter and
	 * saves the screenshot of the page.
	 * 
	 * @author Gundeep Singh
	 * @param pageName
	 **/

	public static String captureScreenShot(String pageName) throws IOException {
		// String screenshotName = scenario.getName().replaceAll(" ", "_");
		TakesScreenshot screen = (TakesScreenshot) DemoBlazeDriver.getDriver();
		File sourcePath = screen.getScreenshotAs(OutputType.FILE);
		System.out.println("This is the source path ************   " + sourcePath);
		String destinationActualPath = System.getProperty("user.dir") + "/DemoBlazeEcommerce/target/" + pageName
				+ ".png";
		File destinationPath = new File(destinationActualPath);
		System.out.println("This is the destination path ***********  " + destinationPath);
		// Copy taken screenshot from source location to destination location
		Files.copy(sourcePath, destinationPath);
		return destinationActualPath;
		// This attach the specified screenshot to the test

	}

	/**
	 * Name: switchWindows Description: This function will switch to the other
	 * window
	 * 
	 * @author Gundeep Singh
	 **/

	public static void switchWindows() {
		String currentWindow = DemoBlazeDriver.getDriver().getWindowHandle();
		System.out.println("Current window is " + currentWindow);
		Set<String> allWindows = DemoBlazeDriver.getDriver().getWindowHandles();
		for (String previousWindow : allWindows) {
			if (!currentWindow.equalsIgnoreCase(previousWindow)) {
				DemoBlazeDriver.getDriver().switchTo().window(previousWindow);
				System.out.println(DemoBlazeDriver.getDriver().switchTo().window(previousWindow).getTitle());
				Log.logInfo(GenericLib.class, "Switched to new Window");
			}
		}
	}

	/**
	 * Name :closeBrowser Description : This method is getting close Browser
	 * 
	 * @author Gundeep Singh
	 **/
	public static void closeBrowser() {
		try {
			DemoBlazeDriver.getDriver().close();
			Log.logInfo(GenericLib.class, "Browser is closed");
		} catch (Exception e) {
			Log.logError(GenericLib.class, "Error is dispalyed while closing the browser: " + e.getMessage());
		}
	}

	/**
	 * Name :VerifyAlert Description : This method is Checks Alert Text and accept
	 * it
	 * 
	 * @author Gundeep Singh
	 * 
	 **/

	public static void verifyAlert() {
		try {
			Alert alert = DemoBlazeDriver.getDriver().switchTo().alert();
			String alertMessage = alert.getText();
			Log.logInfo(GenericLib.class, "Alert message is generated  " + alertMessage);
			alert.accept();
			Log.logInfo(GenericLib.class, "Alert is Accepted");
		} catch (Exception e) {
			Log.logError(GenericLib.class, "Unable to Accept the alert " + e.getMessage());
		}
	}

	/**
	 * MM/dd/yyyy format.
	 * 
	 * @author Gundeep Singh Date
	 **/
	public static void getDate() {
		try {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
			LocalDateTime now = LocalDateTime.now();
			PropertiesCache.setCacheProperty("currentDate", dtf.format(now));
			Log.logInfo(GenericLib.class, "Current date created");
			GlobalVariables.testStatus = true;
		} catch (Exception e) {
			Log.logError(GenericLib.class, "Exception while creating current date: " + e.getMessage());
			GlobalVariables.testStatus = false;
		}

	}

}
