
package com.ecommerce.stepdefinition;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import org.testng.Assert;
import com.cucumber.listener.Reporter;
import com.demoBlaze.config.Config;
import com.demoBlaze.config.PropertiesCache;
import com.demoBlaze.libraries.GenericLib;
import com.demoBlaze.libraries.GlobalVariables;
import com.demoBlaze.libraries.Log;
import com.demoBlaze.webelement.BaseElement;
import com.epp.zelle.pages.BasePage;
import com.google.common.base.Throwables;
import cucumber.api.java.ContinueNextStepsOnException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class UniversalSteps {

	public String currentPageName;

	StringWriter sw = new StringWriter();
	public static ArrayList<Object> arrayList = new ArrayList<Object>();

	public ArrayList<Object> getParamValues(String params) throws Exception {
		arrayList.clear();
		String str[] = params.split(",");
		for (int i = 0; i < str.length; i++) {

			if (str[i].startsWith("Env") || str[i].startsWith("env")) {
				try {

					String keyValue = PropertiesCache.getProperty(str[i].substring(3, str[i].length())).trim();
					arrayList.add(keyValue);
				} catch (Exception e) {
					e.printStackTrace();
					// TODO: handle exception
				}
			} else {
				arrayList.add(str[i]);
			}
		}
		return arrayList;
	}

	public String getParamObjects(String params) throws Exception {

		String elementObject;
		String str[] = params.split(",");
		elementObject = str[0].trim();

		if (str.length > 0) {

			for (int i = 1; i <= str.length - 1; i++) {

				if (str[i].startsWith("Env")) {

					try {
						GlobalVariables.paramsVal[i - 1] = PropertiesCache
								.getProperty(str[i].substring(3, str[i].length())).trim();
						// arrayList.add(keyValue);
					} catch (Exception e) {
						e.printStackTrace();
						// TODO: handle exception
					}
				} else {

					GlobalVariables.paramsVal[i - 1] = str[i].trim();

				}
			}
		}
		return elementObject;
	}

	public void invokeMethod(String pageName, String methodName, ArrayList<Object> args) throws Exception {
		if (methodName.equals("navigateTo")) {
			currentPageName = pageName;
		}

		Class PageClass = Class.forName("com.epp.zelle.pages." + pageName);
		// Create a Object of Page
		Object PageObject = PageClass.newInstance();
		// Invoke the method
		Method objMethod = PageClass.getMethod(methodName, null);
		objMethod.invoke(PageObject);

	}

	public void invokeElementMethod(String element, String action, ArrayList args) throws Exception {

		Class PageClass = Class.forName("com.epp.zelle.pages." + currentPageName);
		Object PageObject = PageClass.newInstance();
		Field ElementObject = PageClass.getField(element);
		Method objMethod = ElementObject.get(PageObject).getClass().getMethod(action, ArrayList.class);
		objMethod.invoke(ElementObject.get(PageObject), args);
	}

	@ContinueNextStepsOnException
	@Then("^User is on Page \"([^\"]*)\"$")
	public void user_is_on_Page(String pageName) {
		try {
			Log.logInfo(UniversalSteps.class, "UniversalStep: User is on page " + pageName);
			invokeMethod(pageName, "navigateTo", getParamValues(""));
			Assert.assertTrue(GlobalVariables.testStatus);

		} catch (Exception e) {
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			Log.logError(UniversalSteps.class,
					"UniversalStep:  " + pageName + " Failed , " + exceptionAsString + "...");
			Reporter.addStepLog(exceptionAsString.substring(0, 150));

			Assert.fail();

		}
	}

	@ContinueNextStepsOnException
	@Then("^Validate that user is on page \"([^\"]*)\"$")
	public void validate_that_user_is_on_page(String pageName) {

		try {

			Log.logInfo(UniversalSteps.class, "UniversalStep: Validate that user is on page " + pageName);
			invokeMethod(pageName, "isOpen", getParamValues(""));
			if (GlobalVariables.testStatus) {
				Log.logInfo(UniversalSteps.class, "UniversalStep: Validate that user is on page " + pageName);
				currentPageName = pageName;
				Log.logInfo(UniversalSteps.class, "UniversalStep: Changed the current page to " + pageName);
				Assert.assertTrue(GlobalVariables.testStatus);
			} else {
				Log.logError(UniversalSteps.class, "UniversalStep: User is not on " + pageName);
				Reporter.addStepLog("UniversalStep: User is not on " + pageName);
				Assert.assertTrue(GlobalVariables.testStatus);
			}
		} catch (Exception e) {

			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			Log.logError(UniversalSteps.class,
					"UniversalStep:  " + pageName + " Failed , " + exceptionAsString + "...");
			Reporter.addStepLog(exceptionAsString.substring(0, 150));

			Assert.fail();
		}
	}

	@ContinueNextStepsOnException
	@When("^User clicks on element \"([^\"]*)\"$")
	public void user_clicks_on_element(String elementObject) {

		try {

			Log.logInfo(UniversalSteps.class,
					"UniversalStep: User clicks on element " + getParamObjects(elementObject));
			invokeElementMethod(getParamObjects(elementObject), "click", getParamValues(""));
			Assert.assertTrue(GlobalVariables.testStatus);

		} catch (Exception e) {
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			Log.logError(UniversalSteps.class,
					"UniversalStep:  " + elementObject + " Failed , " + exceptionAsString + "...");
			Reporter.addStepLog(exceptionAsString.substring(0, 150));

			Assert.fail();
		}

	}

	@ContinueNextStepsOnException
	@When("^User types \"([^\"]*)\" in textbox \"([^\"]*)\"$")
	public void user_types_in_textbox(String inputText, String elementObject) {

		try {

			Log.logInfo(UniversalSteps.class,
					"UniversalStep: User types " + inputText + " in textbox " + getParamObjects(elementObject) + ".");
			invokeElementMethod(getParamObjects(elementObject), "type", getParamValues(inputText));

			Assert.assertTrue(GlobalVariables.testStatus);

		} catch (Exception e) {

			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			Log.logError(UniversalSteps.class,
					"UniversalStep:  " + elementObject + " Failed , " + exceptionAsString + "...");
			Reporter.addStepLog(exceptionAsString.substring(0, 150));

			Assert.fail();
		}
	}

	
	@ContinueNextStepsOnException
	@Then("^Validate that element \"([^\"]*)\" is present$")
	public void validate_that_element_is_present(String elementObject) {
		try {

			invokeElementMethod(getParamObjects(elementObject), "elementIsPresent", getParamValues("true"));

			Assert.assertTrue(GlobalVariables.testStatus);

		} catch (Exception e) {
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			Log.logError(UniversalSteps.class,
					"UniversalStep:  " + elementObject + " Failed , " + exceptionAsString + "...");
			Reporter.addStepLog(exceptionAsString.substring(0, 150));
			Assert.fail();
		}

	}

	@ContinueNextStepsOnException
	@When("^System waits for \"([^\"]*)\" seconds$")
	public void system_waits_for_seconds(int timeOut) {
		try {

			Log.logInfo(UniversalSteps.class, "UniversalStep: User waited for " + timeOut + " seconds ");
			int timeOut1 = timeOut * 1000;
			Thread.sleep(timeOut1);
			Assert.assertTrue(GlobalVariables.testStatus);

		} catch (Exception e) {

			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			Log.logError(UniversalSteps.class, "UniversalStep:  " + timeOut + " Failed , " + exceptionAsString + "...");
			Reporter.addStepLog(exceptionAsString.substring(0, 150));
			Assert.fail();
		}
	}

	@ContinueNextStepsOnException
	@When("^System takes a screenshot$")
	public void system_takes_a_screenshot() {

		try {

			Log.logInfo(UniversalSteps.class, "UniversalStep: System takes a screenshot and add to report");
			GenericLib.captureScreenShot(currentPageName);
			Assert.assertTrue(GlobalVariables.testStatus);

		} catch (Exception e) {

			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			Log.logError(UniversalSteps.class, "UniversalStep: Failed , " + exceptionAsString + "...");

			Reporter.addStepLog(exceptionAsString.substring(0, 150));
			Assert.fail();
		}
	}

	@ContinueNextStepsOnException
	@When("^System switch to new window/popup$")
	public void system_switch_to_new_window_popup() {
		try {
			GenericLib.switchWindows();
			Assert.assertTrue(GlobalVariables.testStatus);

		} catch (Exception e) {

			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			Log.logError(UniversalSteps.class, "UniversalStep: Failed , " + exceptionAsString + "...");

			Reporter.addStepLog(exceptionAsString.substring(0, 150));
			Assert.fail();
		}
	}

	@ContinueNextStepsOnException
	@Then("^Validate that \"([^\"]*)\" has inner text \"([^\"]*)\"$")
	public void validate_that_has_inner_text(String elementObject, String innerText) {
		try {

			invokeElementMethod(getParamObjects(elementObject), "validateInnerText", getParamValues(innerText));
			Assert.assertTrue(GlobalVariables.testStatus);

		} catch (Exception e) {
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			Log.logError(UniversalSteps.class, "UniversalStep: Failed , " + exceptionAsString + "...");
			Reporter.addStepLog(exceptionAsString.substring(0, 150));

			Assert.fail();
		}
	}

	@ContinueNextStepsOnException
	@Then("^Validate that \"([^\"]*)\" does not have inner text \"([^\"]*)\"$")
	public void validate_that_does_not_have_inner_text(String elementObject, String innerText) {
		try {
			invokeElementMethod(getParamObjects(elementObject), "validateInnerText",
					getParamValues(innerText + ",false"));
			Assert.assertTrue(GlobalVariables.testStatus);

		} catch (Exception e) {

			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			Log.logError(UniversalSteps.class, "UniversalStep: Failed , " + exceptionAsString + "...");
			Reporter.addStepLog(exceptionAsString.substring(0, 150));
			Assert.fail();
		}
	}

	@ContinueNextStepsOnException
	@When("^User waits for \"([^\"]*)\" to be present in screen$")
	public void user_waits_for_to_be_present_in_screen(String elementObject) {
		try {
			Log.logInfo(UniversalSteps.class,
					"UniversalStep: User waits for " + getParamObjects(elementObject) + " to be present in screen");
			invokeElementMethod(getParamObjects(elementObject), "waitfor", getParamValues(""));
			Assert.assertTrue(GlobalVariables.testStatus);

		} catch (Exception e) {

			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			Log.logError(UniversalSteps.class, "UniversalStep: Failed , " + exceptionAsString + "...");
			Reporter.addStepLog(exceptionAsString.substring(0, 150));
			Assert.fail();
		}
	}

	@ContinueNextStepsOnException
	@When("^User select Year \"([^\"]*)\" Month \"([^\"]*)\" Day \"([^\"]*)\" from calendar \"([^\"]*)\"$")
	public void user_select_Year_Month_Day_from_calendar(String year, String month, String day, String elementObject) {

		try {
			Log.logInfo(UniversalSteps.class,
					"UniversalStep: User clicks on element " + getParamObjects(elementObject));
			invokeElementMethod(getParamObjects(elementObject), "selectCalendarDate",
					getParamValues(year + "," + month + "," + day));
			Assert.assertTrue(GlobalVariables.testStatus);

		} catch (Exception e) {
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			Log.logError(UniversalSteps.class, "UniversalStep: Failed , " + exceptionAsString + "...");
			Reporter.addStepLog(exceptionAsString.substring(0, 150));
			Assert.fail();
		}
	}

	@ContinueNextStepsOnException
	@When("^User selects \"([^\"]*)\" from Combobox \"([^\"]*)\"$")
	public void user_selects_from_Combobox(String inputText, String elementObject) {
		try {
			Log.logInfo(UniversalSteps.class, "UniversalStep: User selects " + inputText + " from Combobox "
					+ getParamObjects(elementObject) + " .");
			invokeElementMethod(getParamObjects(elementObject), "selectByText", getParamValues(inputText));
			Assert.assertTrue(GlobalVariables.testStatus);

		} catch (Exception e) {
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			Log.logError(UniversalSteps.class, "UniversalStep: Failed , " + exceptionAsString + "...");
			Reporter.addStepLog(exceptionAsString.substring(0, 150));
			Assert.fail();
		}
	}

	@ContinueNextStepsOnException
	@When("^User clicks cell \"([^\"]*)\" in table \"([^\"]*)\"$")
	public void user_clicks_cell_in_table(String inputText, String elementObject) {
		try {
			Log.logInfo(UniversalSteps.class, "UniversalStep: User clicks cell " + inputText + " in table "
					+ getParamObjects(elementObject) + " .");
			invokeElementMethod(getParamObjects(elementObject), "clickByText", getParamValues("*," + inputText));
			Assert.assertTrue(GlobalVariables.testStatus);

		} catch (Exception e) {

			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			Log.logError(UniversalSteps.class, "UniversalStep: Failed , " + exceptionAsString + "...");
			Reporter.addStepLog(exceptionAsString.substring(0, 150));
			Assert.fail();
		}
	}

	@ContinueNextStepsOnException
	@When("^User doubleclicks cell \"([^\"]*)\" in table \"([^\"]*)\"$")
	public void user_doubleclicks_cell_in_table(String inputText, String elementObject) {
		try {
			Log.logInfo(UniversalSteps.class, "UniversalStep: User doubleclicks cell  " + inputText + " in table "
					+ getParamObjects(elementObject) + " .");
			invokeElementMethod(getParamObjects(elementObject), "doubleclickByText", getParamValues("*," + inputText));
			Assert.assertTrue(GlobalVariables.testStatus);

		} catch (Exception e) {

			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			Log.logError(UniversalSteps.class, "UniversalStep: Failed , " + exceptionAsString + "...");
			Reporter.addStepLog(exceptionAsString.substring(0, 150));
			Assert.fail();
		}
	}

	@ContinueNextStepsOnException
	@When("^User clicks on element \"([^\"]*)\" if present$")
	public void user_clicks_on_element_if_present(String elementObject) {

		try {
			Log.logInfo(UniversalSteps.class,
					"UniversalStep: User clicks on element " + getParamObjects(elementObject) + " if present");
			invokeElementMethod(getParamObjects(elementObject), "click", getParamValues("false"));
			Assert.assertTrue(GlobalVariables.testStatus);

		} catch (Exception e) {

			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			Log.logError(UniversalSteps.class, "UniversalStep: Failed , " + exceptionAsString + "...");
			Reporter.addStepLog(exceptionAsString.substring(0, 150));
			Assert.fail();
		}
	}

	@ContinueNextStepsOnException
	@When("^User types randaom data starting with \"([^\"]*)\" and ends with \"([^\"]*)\" in textbox \"([^\"]*)\"$")
	public void user_types_randaom_data_starting_with_and_ends_with_in_textbox(String inputText, String arg2,
			String elementObject) {

		try {

			inputText = inputText + Integer.toString(new Random().nextInt(1000)) + arg2;
			Log.logInfo(UniversalSteps.class, "UniversalStep: User types random data starting with " + inputText
					+ " in textbox " + getParamObjects(elementObject) + ".");
			invokeElementMethod(getParamObjects(elementObject), "type", getParamValues(inputText));
			Assert.assertTrue(GlobalVariables.testStatus);

		} catch (Exception e) {
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			Log.logError(UniversalSteps.class, "UniversalStep: Failed , " + exceptionAsString + "...");
			Reporter.addStepLog(exceptionAsString.substring(0, 150));
			Assert.fail();
		}

	}

	@ContinueNextStepsOnException
	@Given("^Validate that element \"([^\"]*)\" is not present$")
	public void validate_that_element_is_not_present(String elementObject) {
		try {

			invokeElementMethod(getParamObjects(elementObject), "elementIsPresent", getParamValues("false"));
			Assert.assertTrue(GlobalVariables.testStatus);

		} catch (Exception e) {
			String exceptionString = Throwables.getStackTraceAsString(e).substring(0, 100);
			Reporter.addStepLog(exceptionString);
			Log.logError(UniversalSteps.class,
					"UniversalStep:  " + elementObject + " Failed , " + exceptionString + "...");
			Assert.fail();
		}
	}
	
	@ContinueNextStepsOnException
	@When("^User launches Url with existing URL$")
	public void user_launches_Url_with_existing_URL() throws Throwable {
		try {

			
				GenericLib.openURL(Config.url);
				Log.logInfo(UniversalSteps.class,
						"Excepted : " + "URL is launched" + Config.url);
				Assert.assertTrue(GlobalVariables.testStatus);
				
			}
			
		 catch (Exception e) {
			String exceptionString = Throwables.getStackTraceAsString(e).substring(0, 100);
			Reporter.addStepLog(exceptionString);
			Log.logError(UniversalSteps.class, "UniversalStep: Failed , " + exceptionString + "...");
			Assert.fail();
	}
		}


	

	@ContinueNextStepsOnException
	@When("^User closes the existing browser$")
	public void user_closes_the_existing_browser() {
		try {

			GenericLib.closeBrowser();
			
			Assert.assertTrue(GlobalVariables.testStatus);
		} catch (Exception e) {
			String exceptionString = Throwables.getStackTraceAsString(e).substring(0, 100);
			Reporter.addStepLog(exceptionString);
			Log.logError(UniversalSteps.class, "UniversalStep: Failed , " + exceptionString + "...");
			Assert.fail();
		}
	}

	@ContinueNextStepsOnException
	@Then("^Validate that \"([^\"]*)\" contains inner text \"([^\"]*)\"$")
	public void validate_that_contains_inner_text(String elementObject, String innerText) {
		try {

			invokeElementMethod(getParamObjects(elementObject), "validateInnerText",
					getParamValues(innerText + ",true"));
			Assert.assertTrue(GlobalVariables.testStatus);
		} catch (Exception e) {
			String exceptionString = Throwables.getStackTraceAsString(e).substring(0, 100);
			Reporter.addStepLog(exceptionString);
			Log.logError(UniversalSteps.class,
					"UniversalStep:  " + elementObject + " Failed , " + exceptionString + "...");
			Assert.fail();
		}
	}

	@ContinueNextStepsOnException
	@Then("^Get current url and validate with \"([^\"]*)\"$")
	public void get_current_url_and_validate_with(String updatedURL) {
		try {

			if (updatedURL.startsWith("Env")) {
				String keyValue = PropertiesCache.getInstance()
						.getProperty(updatedURL.substring(3, updatedURL.length())).trim();
				// Log.logInfo(UniversalSteps.class, "UniversalStep: User refresh current page "
				// + refreshCount + " times");
				String actualUrl = GenericLib.getCurrentURL();
				System.out.println("Acutal URl " + actualUrl + " Expected Url " + keyValue);
				if (actualUrl.contains(keyValue)) {
					GlobalVariables.testStatus = true;
					Log.logInfo(BaseElement.class,
							"Excepted : " + keyValue + " Inner text matched in Actual inner text : " + actualUrl);
				} else {
					GlobalVariables.testStatus = false;
					Log.logError(BaseElement.class,
							"Excepted : " + keyValue + " Inner text not matched in Actual inner text : " + actualUrl);
				}

				Assert.assertTrue(GlobalVariables.testStatus);
			}
		} catch (Exception e) {
			String exceptionString = Throwables.getStackTraceAsString(e).substring(0, 100);
			Reporter.addStepLog(exceptionString);
			Log.logError(UniversalSteps.class, "UniversalStep: Failed , " + exceptionString + "...");
			Assert.fail();
		}
	}

	@ContinueNextStepsOnException
	@Then("^Validate that attribute \"([^\"]*)\" has value \"([^\"]*)\" for \"([^\"]*)\"$")
	public void validate_that_attribute_has_value_for(String Attr, String validateAttrValue, String elementObject) {
		try {

			invokeElementMethod(getParamObjects(elementObject), "validateAttributeValue",
					getParamValues(Attr + "," + validateAttrValue));
			Assert.assertTrue(GlobalVariables.testStatus);
		} catch (Exception e) {
			String exceptionString = Throwables.getStackTraceAsString(e).substring(0, 100);
			Reporter.addStepLog(exceptionString);
			Log.logError(UniversalSteps.class,
					"UniversalStep:  " + elementObject + " Failed , " + exceptionString + "...");
			Assert.fail();
		}
	}

	@ContinueNextStepsOnException
	@When("^Generate current date and save date to propertycachefile$")
	public void generate_current_date_and_save_date_to_propertycachefile() {
		try {

			GenericLib.getDate();
			Assert.assertTrue(GlobalVariables.testStatus);
		} catch (Exception e) {
			String exceptionString = Throwables.getStackTraceAsString(e).substring(0, 100);
			Reporter.addStepLog(exceptionString);
			Log.logError(UniversalSteps.class, "UniversalStep:  Failed to set date" + exceptionString + "...");
			Assert.fail();
		}
	}

	@ContinueNextStepsOnException
	@When("^User selects \"([^\"]*)\" webelements from the list \"([^\"]*)\"$")
	public void user_selects_webelements_from_the_list(String noOfElementsToSelect, String elementObject)
			throws Throwable {
		try {

			Log.logInfo(UniversalSteps.class, "UniversalStep: User clicks on element " + elementObject);
			invokeElementMethod(getParamObjects(elementObject), "clickDynamicElements",
					getParamValues(noOfElementsToSelect));
			Assert.assertTrue(GlobalVariables.testStatus);

		} catch (Exception e) {
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			Log.logError(UniversalSteps.class,
					"UniversalStep:  " + elementObject + " Failed , " + exceptionAsString + "...");
			Reporter.addStepLog(exceptionAsString.substring(0, 150));

			Assert.fail();
		}
	}

	@ContinueNextStepsOnException
	@Then("^User captures the amount of the product \"([^\"]*)\"$")
	public void user_captures_the_amount_of_the_product(String elementObject) throws Throwable {
		try {
			Log.logInfo(UniversalSteps.class,
					"UniversalStep: User captures text from " + getParamObjects(elementObject) + ".");
			invokeElementMethod(getParamObjects(elementObject), "getDisplayedAmount", getParamValues(""));
			Assert.assertTrue(GlobalVariables.testStatus);

		} catch (Exception e) {

			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			Log.logError(UniversalSteps.class, "UniversalStep: Failed , " + exceptionAsString + "...");
			Reporter.addStepLog(exceptionAsString.substring(0, 150));
			Assert.fail();
		}

	}

	@ContinueNextStepsOnException
	@Then("^User captures the details of the purchase from element \"([^\"]*)\"$")
	public void user_captures_the_details_of_the_purchase_from_element(String elementObject) throws Throwable {
		try {
			Log.logInfo(UniversalSteps.class,
					"UniversalStep: User captures text from " + getParamObjects(elementObject) + ".");
			invokeElementMethod(getParamObjects(elementObject), "getDisplayedText", getParamValues(""));
			Assert.assertTrue(GlobalVariables.testStatus);

		} catch (Exception e) {

			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			Log.logError(UniversalSteps.class, "UniversalStep: Failed , " + exceptionAsString + "...");
			Reporter.addStepLog(exceptionAsString.substring(0, 150));
			Assert.fail();
		}
	}

	@ContinueNextStepsOnException
	@Then("^User Verifies the popup$")
	public void user_Verifies_the_popup() throws Throwable {
		try {
			GenericLib.verifyAlert();
			Assert.assertTrue(GlobalVariables.testStatus);

		} catch (Exception e) {

			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			Log.logError(UniversalSteps.class, "UniversalStep: Failed , " + exceptionAsString + "...");
			Reporter.addStepLog(exceptionAsString.substring(0, 150));
			Assert.fail();
		}

	}
	
	@When("^User laufrnches Url with existing URL$")
	public void user_laufrnches_Url_with_existing_URL() throws Throwable {
	  
	}

	@Given("^User is on ewrPage \"([^\"]*)\"$")
	public void user_is_on_ewrPage(String arg1) throws Throwable {
	}

}
