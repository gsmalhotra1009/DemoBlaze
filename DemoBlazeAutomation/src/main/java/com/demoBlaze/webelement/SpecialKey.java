package com.demoBlaze.webelement;


import org.openqa.selenium.support.ui.WebDriverWait;

import com.demoBlaze.config.Config;
import com.demoBlaze.initdriver.DemoBlazeDriver;


public class SpecialKey extends BaseElement {

 static WebDriverWait webDriverwait = new WebDriverWait(DemoBlazeDriver.getDriver(), Config.objectWaitTime);

 public SpecialKey(String locatorType, String locatorValue) {
super(locatorType, locatorValue);

 }

}