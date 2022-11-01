package com.demoBlaze.webelement;

import java.util.ArrayList;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.demoBlaze.config.Config;
import com.demoBlaze.initdriver.DemoBlazeDriver;
import com.demoBlaze.libraries.GlobalVariables;
import com.demoBlaze.libraries.Log;

public class ComboBox extends BaseElement {

	public ComboBox(String locatorType, String locatorValue) {
		super(locatorType, locatorValue);
	}

	static WebDriverWait webDriverwait = new WebDriverWait(DemoBlazeDriver.getDriver(), Config.objectWaitTime);

	public void selectByText(ArrayList<Object> selectText) throws Exception{

		Object obj = selectText.get(0);

			if (webDriverwait.until(ExpectedConditions.visibilityOf(this.get())) != null) {

				if (webDriverwait.until(ExpectedConditions.elementToBeClickable(this.get())) != null) {

					Select select = new Select(this.get());
					select.selectByVisibleText(obj.toString());
					Log.logInfo(ComboBox.class, "ComboBox: Selected option" + obj.toString() + " from "
							+ this.locatorType + "---" + this.locatorValue);
					GlobalVariables.testStatus = true;
				} else {
					Log.logError(Button.class, "Button: Failed to click Button with " + this.locatorType + " --- "
							+ this.locatorValue + " due to element is not " + "clickable");
					GlobalVariables.testStatus = false;
				}

			} else {
				Log.logError(ComboBox.class, "ComboBox: Failed to select option " + obj.toString() + " from "
						+ this.locatorType + " --- " + this.locatorValue + " due to element is not visible");
				GlobalVariables.testStatus = false;
			}
		
	}

}
