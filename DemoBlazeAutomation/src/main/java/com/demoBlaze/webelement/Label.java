package com.demoBlaze.webelement;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.demoBlaze.config.Config;
import com.demoBlaze.initdriver.DemoBlazeDriver;
import com.demoBlaze.libraries.GlobalVariables;
import com.demoBlaze.libraries.Log;
import com.demoBlaze.webelement.BaseElement;

public class Label extends BaseElement {

	public Label(String locatorType, String locatorValue) {
		super(locatorType, locatorValue);
	}

	static WebDriverWait webDriverwait = new WebDriverWait(DemoBlazeDriver.getDriver(), Config.objectWaitTime);

	public void click(ArrayList<Object> failOnError) throws Exception{

			if (webDriverwait.until(ExpectedConditions.visibilityOf(this.get())) != null) {
				if (webDriverwait.until(ExpectedConditions.elementToBeClickable(this.get())) != null) {
					this.get().click();
					Log.logInfo(Label.class,
							"Label: Clicked label with " + this.locatorType + " --- " + this.locatorValue);
					GlobalVariables.testStatus = true;
				} else {
					Log.logError(Label.class, "Label: Failed to click label with " + this.locatorType + " --- "
							+ this.locatorValue + " due to element is not " + "clickable");
					GlobalVariables.testStatus = false;
				}

			} else {
				Log.logError(Label.class, "Label: Failed to click label with " + this.locatorType + " --- "
						+ this.locatorValue + " due to element is not visible");
				GlobalVariables.testStatus = false;
			}

	}

	public void clickByText(ArrayList<Object> inputValue) throws Exception{

		Object cellTagClick = inputValue.get(0);
			Object cellText = inputValue.get(1);

			if (webDriverwait.until(ExpectedConditions.visibilityOf(this.get())) != null) {
				if (webDriverwait.until(ExpectedConditions.elementToBeClickable(this.get())) != null) {

					this.get().findElement(
							By.xpath("//" + cellTagClick.toString() + "[text()=" + cellText.toString() + "]"));
					this.get().click();
					Log.logInfo(Label.class,
							"Label: Clicked label text with " + this.locatorType + " --- " + this.locatorValue);
					GlobalVariables.testStatus = true;
				} else {
					Log.logError(Label.class, "Label: Failed to click label with " + this.locatorType + " --- "
							+ this.locatorValue + " due to element is not " + "clickable");
					GlobalVariables.testStatus = false;
				}

			} else {
				Log.logError(Label.class, "Label: Failed to click label text with " + this.locatorType + " --- "
						+ this.locatorValue + " due to element is not visible");
				GlobalVariables.testStatus = false;
			}

	}
	
	

	public void clickDynamicElements(ArrayList<Object> arrayList) throws Exception {
		int noOfElementsToSelect =Integer.valueOf(arrayList.get(0).toString());
		//int sizeOflist = this.getElements().size();
		while (noOfElementsToSelect > 0) {
			if (webDriverwait.until(ExpectedConditions.visibilityOf(this.getElements().get(0))) != null) {
				if (webDriverwait.until(ExpectedConditions.elementToBeClickable(this.getElements().get(0))) != null) {
					this.getElements().get(0).click();
					Thread.sleep(10000);
					this.getElements();
					this.getElements().size();
					noOfElementsToSelect--;
					Log.logInfo(Label.class,
							"Label: Clicked label with " + this.locatorType + " --- " + this.locatorValue);
					GlobalVariables.testStatus = true;
				} else {
					Log.logError(Label.class, "Label: Failed to click label with " + this.locatorType + " --- "
							+ this.locatorValue + " due to element is not visible");
					GlobalVariables.testStatus = false;
				}

				
			} else {
				Log.logError(Label.class, "Label: Failed to click label with " + this.locatorType + " --- "
						+ this.locatorValue + " due to element is not visible");
				GlobalVariables.testStatus = false;

			}
		}
		Log.logInfo(Label.class,
				"Label: Clicked label with " + this.locatorType + " --- " + this.locatorValue);
		GlobalVariables.testStatus = true;
	}

}
