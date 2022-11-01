package com.demoBlaze.initdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import com.demoBlaze.config.Config;

import com.demoBlaze.libraries.Log;
import com.paulhammant.ngwebdriver.NgWebDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * This Class will initiate the explorer drivers. Config class is extended by
 * this class.
 * 
 * @author Gundeep Singh
 *
 */
public class DemoBlazeDriver extends Config {

	public static DemoBlazeDriver oRDPDriver;
	public static NgWebDriver oNJSDriver;
	public static WebDriver driver;

	/**
	 * This Constructor will initiate different explorer based on the browser name
	 * passed into INI file.
	 * 
	 * @author Gundeep Singh
	 */
	private DemoBlazeDriver() {

		try {

			if (browserName.equalsIgnoreCase("CHROME")) {
			//	System.setProperty("webdriver.chrome.driver", projPath + driverRelativePath + "chromedriver.exe");
				 WebDriverManager.chromedriver().setup();
				
				DemoBlazeDriver.driver = new ChromeDriver();
				Log.logInfo(DemoBlazeDriver.class, "Created Chrome driver instace");

			} else if (browserName.equalsIgnoreCase("IE")) {
				System.setProperty("webdriver.ie.driver", projPath + driverRelativePath + "IEDriverServer.exe");
				DemoBlazeDriver.driver = new InternetExplorerDriver();
				Log.logInfo(DemoBlazeDriver.class, "Created IE driver instace");

			} else if (browserName.equalsIgnoreCase("FIREFOX")) {
				System.setProperty("webdriver.gecko.driver", projPath + driverRelativePath + "geckodriver.exe");
				DemoBlazeDriver.driver = new FirefoxDriver();
				Log.logInfo(DemoBlazeDriver.class, "Created FireFox driver instace");
			}

			DemoBlazeDriver.driver.manage().window().maximize();
			Log.logInfo(DemoBlazeDriver.class, "Maximize the explorer window");
			DemoBlazeDriver.driver.manage().timeouts().implicitlyWait(objectWaitTime, TimeUnit.SECONDS);

		} catch (Exception e) {
			Log.logError(DemoBlazeDriver.class, e.getMessage());
		}
	}

	/**
	 * This method will help to call the constructor of this class only.
	 * 
	 * @author Gundeep Singh
	 */
	public static void setUpRDPDriver() {

		try {

			if (DemoBlazeDriver.driver == null) {
				Log.logInfo(DemoBlazeDriver.class, "Setup the new driver");
				oRDPDriver = new DemoBlazeDriver();
			}
		} catch (Exception e) {
			Log.logError(DemoBlazeDriver.class, e.getMessage());
		}
	}

	/**
	 * This method will initiate NJ drivers, which is further used in Angular
	 * application automation.
	 * 
	 * @author Gundeep Singh
	 * @return - Returning the NgWebDriver Instance.
	 */
	public static NgWebDriver getNJSDriver() {
		try {
			oNJSDriver = new NgWebDriver((JavascriptExecutor) DemoBlazeDriver.getDriver());
			return oNJSDriver;
		} catch (Exception e) {
			Log.logError(DemoBlazeDriver.class, e.getMessage());
			return null;
		}

	}

	/**
	 * Getting the driver instance.
	 * 
	 * @author Gundeep Singh
	 * @return Returning the driver instance.
	 */
	public static WebDriver getDriver() {
		return DemoBlazeDriver.driver;
	}

}
