package com.demoBlaze.executor;

import java.io.File;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.PropertyConfigurator;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.cucumber.listener.ExtentProperties;
import com.cucumber.listener.Reporter;
import com.demoBlaze.config.Config;
import com.demoBlaze.initdriver.DemoBlazeDriver;
import com.demoBlaze.libraries.GenericLib;
import com.demoBlaze.libraries.Log;
import com.ecommerce.stepdefinition.UniversalSteps;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;

/**
 * Cucumber option annotations for binding the feature files, step definition
 * files. Also defining the extent cucumber report for report generation.
 * 
 * @author Gundeep Singh
 *
 */
@CucumberOptions(

		features = "D:\\DemoBlazeAutomation\\src\\main\\resources\\com\\epp\\feature\\Sanity.feature", glue = "com.ecommerce.stepdefinition", plugin = {
				"com.cucumber.listener.ExtentCucumberFormatter:" }, tags = { "@Sanity"

		}, monochrome = true

)

/**
 * This class will start before our step definition. Will implement all the
 * prerequisites into the methods defined.
 * 
 * @author Gundeep Singh
 *
 */
	public class CukeExecutor extends AbstractTestNGCucumberTests {

	/**
	 * This method will run before each test cases.
	 * 
	 * @author Gundeep Singh
	 * 
	 */

	UniversalSteps steps = null;

	@BeforeSuite

	@BeforeTest
	public void initDriver() {

		try {

			PropertyConfigurator
					.configure(Config.projPath + "\\src\\main\\java\\com\\demoBlaze\\config\\Logger.properties");
			Log.logInfo(CukeExecutor.class, "Initiating drivers");
			DemoBlazeDriver.setUpRDPDriver();
			Log.logInfo(CukeExecutor.class, "Initialisation of drivers is completed successfully");

			if (steps == null) {
				steps = new UniversalSteps();
			}

		} catch (Exception e) {
			Log.logError(CukeExecutor.class, e.getMessage());
		}

	}

	/**
	 * This method will call initially before the start of test case. This will help
	 * user to create the extent reports.
	 * 
	 * @author Gundeep Singh
	 */
	@BeforeClass
	public static void setup() {

		try {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM_dd_yyyy HH_mm_ss");
			LocalDateTime now = LocalDateTime.now();

			String currenTimeFormat = dtf.format(now);
			String cucumberFileName = "DemoBlazeReport" + currenTimeFormat;
			String loggerFileName = "DemoBlazeLogger_" + currenTimeFormat;
			String loggerPropertyAbsolutePath = Config.projPath
					+ "\\src\\main\\java\\com\\DemoBlaze\\config\\Logger.properties";

			PropertiesConfiguration propertiesConfig = new PropertiesConfiguration(loggerPropertyAbsolutePath);
			propertiesConfig.setProperty("log4j.appender.fileAppender.File",
					(Config.projPath + "\\target\\TestLogs\\" + loggerFileName + ".log").replace("\\", File.separator));
			propertiesConfig.save();

			PropertyConfigurator.configure(loggerPropertyAbsolutePath);

			ExtentProperties extentProperties = ExtentProperties.INSTANCE;
			extentProperties
					.setReportPath(Config.projPath + "/target/CucumberTestReports/" + cucumberFileName + ".html");

			Log.logInfo(CukeExecutor.class, "Initiating Loggers Instance with file name: " + loggerFileName);
			Log.logInfo(CukeExecutor.class, "Initiating Extent Reports Instance with file name: " + cucumberFileName);

		} catch (Exception e) {
			Log.logError(CukeExecutor.class, e.getMessage());
		}
	}

	/**
	 * This method will call after test cases are over.This will help user to load
	 * the extent reports.
	 * 
	 * @author Gundeep Singh
	 */

	@AfterClass
	public static void writeExtentReport() {
		Reporter.loadXMLConfig(
				new File(Config.projPath + "\\src\\main\\java\\com\\demoBlaze\\config\\extent-config.xml"));
		Reporter.setSystemInfo("User Name", System.getProperty("user.name"));
		Reporter.setSystemInfo("Time Zone", System.getProperty("user.timezone"));
		Reporter.setSystemInfo("Machine", "Windows 10" + " 64-Bit");
		Reporter.setSystemInfo("Selenium", "3.141.5");
		Reporter.setSystemInfo("Maven", "3.5.2");
		Reporter.setSystemInfo("Java Version", "1.8.0_201");

	}

	@AfterSuite
	public static void teardown() {
		GenericLib.closeBrowser();
	}

	/**
	 * @author Gundeep Singh
	 * 
	 * @param daysBack
	 * @param directoryPath
	 * 
	 *            This method will delete all the files older than daysBack passed
	 *            in parameter.
	 * 
	 */

	public static void deleteFilesOlderThanNdays(final int daysBack, final String directoryPath) {

		Log.logInfo(GenericLib.class, "Files deleting which are older than : " + daysBack + " from current date");
		Log.logInfo(GenericLib.class, "Files deleting from directory : " + directoryPath);

		final File directory = new File(directoryPath);
		if (directory.exists()) {
			System.out.println(" Directory Exists");
			final File[] listFiles = directory.listFiles();

			java.util.Calendar cal = java.util.Calendar.getInstance();
			cal.add(java.util.Calendar.DAY_OF_MONTH, daysBack * -1);
			long purgeTime = cal.getTimeInMillis();
			for (File listFile : listFiles) {

				if (listFile.lastModified() < purgeTime) {
					listFile.deleteOnExit();
					Log.logInfo(GenericLib.class,
							listFile.getName() + ": File deleted from directory : " + directoryPath);
				}
			}
		} else {
			Log.logInfo(GenericLib.class,
					"Directory from which files need to be deleted, does not exist: " + directoryPath);
		}
	}
}
