package com.demoBlaze.webelement;

import java.util.ArrayList;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.demoBlaze.config.Config;
import com.demoBlaze.initdriver.DemoBlazeDriver;
import com.demoBlaze.libraries.GlobalVariables;
import com.demoBlaze.libraries.Log;

public class Button extends BaseElement {

	public Button(String locatorType, String locatorValue) {
		super(locatorType, locatorValue);

	}

	static WebDriverWait webDriverwait = new WebDriverWait(DemoBlazeDriver.getDriver(), Config.objectWaitTime);

	public void click(ArrayList<Object> failOnError) throws Exception {

			if (webDriverwait.until(ExpectedConditions.visibilityOf(this.get())) != null) {
			if (webDriverwait.until(ExpectedConditions.elementToBeClickable(this.get())) != null) {
				this.get().click();
				Thread.sleep(2000);
				Log.logInfo(Button.class,
						"Button: Clicked Button with " + this.locatorType + " --- " + this.locatorValue);
				GlobalVariables.testStatus = true;
			} else {
			

				Log.logError(Button.class, "Button: Failed to click Button with " + this.locatorType + " --- "
						+ this.locatorValue + " due to element is not " + "clickable");
				GlobalVariables.testStatus = false;
			}

		} else {
			
			Log.logError(Button.class, "Button: Failed to click Button with " + this.locatorType + " --- "
					+ this.locatorValue + " due to element is not " + "visible");
			GlobalVariables.testStatus = false;

		}

	}
}
