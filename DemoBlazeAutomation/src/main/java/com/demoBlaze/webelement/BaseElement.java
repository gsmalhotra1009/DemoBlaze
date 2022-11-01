package com.demoBlaze.webelement;

import java.util.ArrayList;



import java.util.List;

import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.demoBlaze.config.Config;
import com.demoBlaze.config.PropertiesCache;
import com.demoBlaze.initdriver.DemoBlazeDriver;
import com.demoBlaze.libraries.GlobalVariables;
import com.demoBlaze.libraries.Log;



public class BaseElement {

	protected String locatorValue;
	protected String locatorType;
	protected WebDriver webDriver;

	public static String returnDisplayText = null;

	static WebDriverWait webDriverwait = new WebDriverWait(DemoBlazeDriver.getDriver(), Config.objectWaitTime);

	public BaseElement(String locatorType, String locatorValue) {
		this.locatorType = locatorType;
		this.locatorValue = locatorValue;
		webDriver = DemoBlazeDriver.getDriver();

	}

	public WebElement get() throws Exception {
		switch (this.locatorType.toLowerCase()) {
		case "css":
			return webDriver.findElement((By.cssSelector(this.locatorValue)));
		case "classname":
			return webDriver.findElement((By.className(this.locatorValue)));
		case "linktext":
			return webDriver.findElement((By.linkText(this.locatorValue)));
		case "tagname":
			return webDriver.findElement((By.tagName(this.locatorValue)));
		case "xpath":
			return webDriver.findElement((By.xpath(this.locatorValue)));
		case "name":
			return webDriver.findElement((By.name(this.locatorValue)));
		case "partiallinktext":
			return webDriver.findElement((By.partialLinkText(this.locatorValue)));
		case "id":
			return webDriver.findElement((By.id(this.locatorValue)));

		case "text":
			return webDriver.findElement((By.xpath("//*[text()='${this.locatorValue}']")));

		default:
			return webDriver.findElement((By.id(this.locatorValue)));
		}
	}

	public void validateInnerText(ArrayList<Object> validationText) throws Exception {

		String actInnerText = this.get().getText().trim();
		Object objOne = validationText.get(0);

		String expInnerText = objOne.toString();
		if (validationText.size() > 1) {
			Object objTwo = validationText.get(1);
			boolean secondParam = Boolean.parseBoolean(objTwo.toString());

			if (!secondParam) {
				if (!actInnerText.contains(expInnerText)) {
					GlobalVariables.testStatus = true;
					Log.logInfo(BaseElement.class, "Excepted : " + expInnerText
							+ " Inner text not matched in Actual inner text : " + actInnerText);
				} else {
					GlobalVariables.testStatus = false;
					Log.logError(BaseElement.class, "Excepted : " + expInnerText
							+ " Inner text matched in Actual inner text : " + actInnerText);
				}
			} else {
				if (actInnerText.contains(expInnerText)) {
					GlobalVariables.testStatus = true;
					Log.logInfo(BaseElement.class, "Excepted : " + expInnerText
							+ " Inner text matched in Actual inner text : " + actInnerText);
				} else {
					GlobalVariables.testStatus = false;
					Log.logError(BaseElement.class, "Excepted : " + expInnerText
							+ " Inner text not matched in Actual inner text : " + actInnerText);
				}
			}

		} else {

			if (actInnerText.contains(expInnerText)) {
				GlobalVariables.testStatus = true;
				Log.logInfo(BaseElement.class, "Inner text matched. \n\t" + actInnerText + "\n\t" + expInnerText);
			} else {
				GlobalVariables.testStatus = false;
				Log.logError(BaseElement.class,
						"Inner text matched failed. \n\t" + actInnerText + "\n\t" + expInnerText);
			}

		}
	}

	public void elementIsDisabled(ArrayList<Object> booleanFlag) throws Exception {
		Object obj = booleanFlag.get(0);
		boolean flag = Boolean.parseBoolean(obj.toString());
		boolean isDisabledVar = false;
		String disabledAttr = this.get().getAttribute("disabled");
		String classAttr = this.get().getAttribute("class");

		if (disabledAttr.length() > 0) {
			isDisabledVar = true;
		}
		if (!isDisabledVar) {
			if (classAttr.length() > 0) {
				if (disabledAttr.indexOf("disabled") > 0) {
					isDisabledVar = true;
				}
			}
		}
		if (flag) {
			if (isDisabledVar) {
				GlobalVariables.testStatus = true;
				Log.logInfo(BaseElement.class, "Element:" + this.locatorValue + "is disabled in the screen");
			} else {
				GlobalVariables.testStatus = false;
				Log.logError(BaseElement.class, "Element:" + this.locatorValue + "is not disabled in the screen");
			}
		} else {
			if (!isDisabledVar) {
				GlobalVariables.testStatus = true;
				Log.logInfo(BaseElement.class, "Element:" + this.locatorValue + "is enabled in the screen");
			} else {
				GlobalVariables.testStatus = false;
				Log.logError(BaseElement.class, "Element:" + this.locatorValue + "is not enabled in the screen");
			}
		}

	}

	public void getDisplayedAmount(ArrayList<Object> arrayList) throws Exception {
		waitfor();
		if (this.get().isDisplayed()) {
			returnDisplayText = this.get().getText();
			Log.logInfo(BaseElement.class, "Get display Text : " + returnDisplayText);
			GlobalVariables.testStatus = true;
			Log.logInfo(BaseElement.class, "Get display Text : " + returnDisplayText + "Text is found");
			PropertiesCache.setCacheProperty("Amount", returnDisplayText.trim());
		} else {
			GlobalVariables.testStatus = false;
			Log.logError(BaseElement.class, "BaseElement: Text is not found" + "\\n\\t");
		}
	}
	
	public void getDisplayedText(ArrayList<Object> arrayList) throws Exception {
		waitfor();
		if (this.get().isDisplayed()) {
			returnDisplayText = this.get().getText();
			Log.logInfo(BaseElement.class, "Get display Text : " + returnDisplayText);
			GlobalVariables.testStatus = true;
			Log.logInfo(BaseElement.class, "Get display Text : " + returnDisplayText + "Text is found");
			PropertiesCache.setCacheProperty("OrderDetails", returnDisplayText.trim());
		} else {
			GlobalVariables.testStatus = false;
			Log.logError(BaseElement.class, "BaseElement: Text is not found" + "\\n\\t");
		}
	}

	
	public void waitfor() throws Exception {
		if (webDriverwait.until(ExpectedConditions.visibilityOf(this.get())) != null) {
			GlobalVariables.testStatus = true;
			Log.logInfo(BaseElement.class, "Waited till " + this.locatorValue + " is present in screen");
		} else {
			GlobalVariables.testStatus = false;
			Log.logError(BaseElement.class,
					"Action to wait till " + this.locatorValue + " is displayed in screen failed");
		}
	}

	public void clearTextField() throws Exception {
		this.get().clear();

		Log.logInfo(BaseElement.class, "Text of " + this.locatorValue + " is cleared");
		GlobalVariables.testStatus = true;
	}

	public void elementIsPresent(ArrayList<Object> booleanFlag) throws Exception {
		Object obj = booleanFlag.get(0);
		boolean flag = Boolean.parseBoolean(obj.toString());
		if (flag) {
			if (webDriverwait.until(ExpectedConditions.visibilityOf(this.get())) != null) {
				Log.logInfo(BaseElement.class, "Element" + this.locatorValue + " is present/displayed in screen ");
				GlobalVariables.testStatus = true;
			} else {

				Log.logError(BaseElement.class, "Element" + this.locatorValue + " is not present/displayed in screen ");
				GlobalVariables.testStatus = false;
			}
		} else {
			try {

				if (webDriverwait.until(ExpectedConditions.invisibilityOf(this.get())) != null) {

					Log.logInfo(BaseElement.class,
							"Element" + this.locatorValue + " is not present/displayed in screen ");
					GlobalVariables.testStatus = true;
				} else {

					Log.logError(BaseElement.class, "Element" + this.locatorValue + " is present/displayed in screen ");
					GlobalVariables.testStatus = false;
				}

			} catch (Exception e) {

				// e.printStackTrace();
				if (e.getMessage().contains("no such element")) {
					Log.logInfo(BaseElement.class,
							"Element" + this.locatorValue + " is not present/displayed in screen ");
					GlobalVariables.testStatus = true;
				} else {
					Log.logError(BaseElement.class,
							"Element" + this.locatorValue + " Exception while checking invisibility of element ");
					GlobalVariables.testStatus = false;
				}

				// TODO: handle exception
			}

		}

	}

	/**
	 * Name: getElements Description: Method is fetching list of elements from the
	 * xpath
	 * 
	 * @author Gundeep Singh
	 * @param list
	 *            of Webelements -
	 */

	public List<WebElement> getElements() throws Exception {
		switch (this.locatorType.toLowerCase()) {
		case "xpath":
			return webDriver.findElements((By.xpath(this.locatorValue)));
		default:
			return webDriver.findElements((By.xpath(this.locatorValue)));
		}
	}

}