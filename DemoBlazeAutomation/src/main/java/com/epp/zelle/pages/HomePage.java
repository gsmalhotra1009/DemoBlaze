package com.epp.zelle.pages;


import com.demoBlaze.webelement.Button;
import com.demoBlaze.webelement.Label;
import com.demoBlaze.webelement.LinkLabel;
import com.demoBlaze.webelement.TextBox;

public class HomePage extends BasePage {

	
    public Button btnDecline = new Button("xpath", "//button[text()='DECLINE']");
   
    //Categories
    public LinkLabel lnkLaptops = new LinkLabel("xpath", "//div[@class='list-group']/a[text()='Laptops']");
    public LinkLabel lnkPhones = new LinkLabel("xpath", "//div[@class='list-group']/a[text()='Phones']");
    public LinkLabel lnkMonitors = new LinkLabel("xpath", "//div[@class='list-group']/a[text()='Monitors']");
    
    public LinkLabel lnkSamsungGalaxyS6 = new LinkLabel("xpath","//h4[@class='card-title']/a[text()='Samsung galaxy s6']");
    public LinkLabel lnkSonyVaioI5 = new LinkLabel("xpath", "//h4[@class='card-title']/a[text()='Sony vaio i5']");
    public LinkLabel lnkAppleMonitor24 = new LinkLabel("xpath", "//h4[@class='card-title']/a[text()='Apple monitor 24']");
    public LinkLabel lnkDellI78GB = new LinkLabel("xpath","//h4[@class='card-title']/a[text()='Dell i7 8gb']"); 
    
    
    public Label lblSonyVaioI5 = new Label("xpath","//h2[@class='name' and text()='Sony vaio i5']");
    public Label lblDelli78gb = new Label("xpath","//h2[@class='name' and text()='Dell i7 8gb']");
    public Button btnAddToCart = new Button("xpath","//a[text()='Add to cart']");
    
    public LinkLabel lnkHome = new LinkLabel("xpath", "//a[@class='nav-link'][contains(text(),'Home')]");
    public LinkLabel lnkCart = new LinkLabel("id", "cartur");
    
    
    public TextBox edtMemo = new TextBox("xpath", "//input[@id='MemoNote']");
    public Label lblRequestRowPendingSec = new Label("xpath",
		"//span[text()='$0.01']//preceding::div[@class='pageHeader']//span[text()='PENDING']");
  
 
  	  
	public HomePage() {
		super("", "cat", 70000, "id");
	}

	// **** Methods ***

	public void navigateTo() {
		
	}
}
