package com.demoBlaze.webelement;

import java.util.ArrayList;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.demoBlaze.config.Config;
import com.demoBlaze.initdriver.DemoBlazeDriver;
import com.demoBlaze.libraries.GlobalVariables;
import com.demoBlaze.libraries.Log;

public class CheckBox extends BaseElement {

	public CheckBox(String locatorType, String locatorValue) {
		super(locatorType, locatorValue);
	}

	static WebDriverWait webDriverwait = new WebDriverWait(DemoBlazeDriver.getDriver(), Config.objectWaitTime);

	public void click(ArrayList<Object> failOnError) throws Exception {

		if (webDriverwait.until(ExpectedConditions.visibilityOf(this.get())) != null) {
			if (webDriverwait.until(ExpectedConditions.elementToBeClickable(this.get())) != null) {
				this.get().click();
				
				Log.logInfo(CheckBox.class,
						"CheckBox: Checked the Box with " + this.locatorType + " --- " + this.locatorValue);
				GlobalVariables.testStatus = true;
			} else {
				Log.logError(CheckBox.class, "CheckBox: Failed to Check the Box with " + this.locatorType + " --- "
						+ this.locatorValue + " due to element is not " + "clickable");
				GlobalVariables.testStatus = false;
			}

		} else {
			Log.logError(CheckBox.class, "CheckBox: Failed to Check the Box with " + this.locatorType + " --- "
					+ this.locatorValue + " due to element is not " + "visible");
			GlobalVariables.testStatus = false;

		}
	}

}
