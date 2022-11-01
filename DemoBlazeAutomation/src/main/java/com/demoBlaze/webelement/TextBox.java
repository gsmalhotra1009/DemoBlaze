package com.demoBlaze.webelement;

import java.util.ArrayList;

import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.demoBlaze.config.Config;
import com.demoBlaze.initdriver.DemoBlazeDriver;
import com.demoBlaze.libraries.GlobalVariables;
import com.demoBlaze.libraries.Log;


public class TextBox extends BaseElement {
	
	public static String returnDisplayText = null; // Sahil K

	public TextBox(String locatorType, String locatorValue) {
		super(locatorType, locatorValue);
	}

	static WebDriverWait webDriverwait = new WebDriverWait(DemoBlazeDriver.getDriver(), Config.objectWaitTime);

	public void type(ArrayList<Object> inputText) throws Exception {

		Object obj = inputText.get(0);

		if (webDriverwait.until(ExpectedConditions.visibilityOf(this.get())) != null) {
			if (webDriverwait.until(ExpectedConditions.elementToBeClickable(this.get())) != null) {
				this.get().clear();
				this.get().sendKeys(obj.toString());
				GlobalVariables.testStatus = true;
				Log.logInfo(TextBox.class, "TextBox: Typed " + obj.toString() + " on element with " + this.locatorType
						+ " --- " + this.locatorValue);
			} else {
				GlobalVariables.testStatus = false;
				Log.logError(TextBox.class, "TextBox: Failed to Type " + obj.toString() + " on element with "
						+ this.locatorType + " --- " + this.locatorValue + " due to element is not clickable");
			}

		} else {
			GlobalVariables.testStatus = false;
			Log.logError(TextBox.class, "TextBox: Failed to Type " + obj.toString() + " on element with "
					+ this.locatorType + " --- " + this.locatorValue + " due to element is not visible");

		}
	}
	
	
	public void specialKeys(ArrayList<Object> inputText) throws Exception {

		 if (webDriverwait.until(ExpectedConditions.visibilityOf(this.get())) != null) {
		if (this.get().isEnabled()) {

		 Keys speckey;
		switch (inputText.get(0).toString()) {

		 case "up":
		speckey = Keys.UP;
		break;
		case "down":
		speckey = Keys.DOWN;
		break;
		case "left":
		speckey = Keys.LEFT;
		break;
		case "right":
		speckey = Keys.RIGHT;
		break;
		case "enter":
		speckey = Keys.ENTER;
		break;
		case "backspace":
		speckey = Keys.BACK_SPACE;
		break;
		case "delete":
		speckey = Keys.DELETE;
		break;
		case "tab":
		speckey = Keys.TAB;
		break;
		case "esc":
		speckey = Keys.ESCAPE;
		break;
		case "escape":
		speckey = Keys.ESCAPE;
		break;
		default:
		speckey = null;
		}
		if (speckey != null) {
		this.get().sendKeys(speckey);

		 Log.logInfo(SpecialKey.class, "SpecialKey: Pressed " + inputText + " -on element with- "
		+ this.locatorType + "=" + this.locatorValue);
		GlobalVariables.testStatus = true;
		} else {
		Log.logInfo(SpecialKey.class, "SpecialKey: is Null");
		GlobalVariables.testStatus = false;
		}
		}

		 else {

		 Log.logInfo(SpecialKey.class, "SpecialKey: Failed to enter key " + inputText + " -on element with- "
		+ this.locatorType + "=" + this.locatorValue);

		 GlobalVariables.testStatus = false;
		}

		 } else {
		Log.logInfo(SpecialKey.class, "SpecialKey: Failed to Type " + inputText + " -on element with- "
		+ this.locatorType + "=" + this.locatorValue);

		 GlobalVariables.testStatus = false;
		}

		 }
	
}
