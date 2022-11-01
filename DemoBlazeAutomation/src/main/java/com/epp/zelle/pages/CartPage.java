package com.epp.zelle.pages;





import com.demoBlaze.webelement.BaseElement;
import com.demoBlaze.webelement.Button;
import com.demoBlaze.webelement.Label;
import com.demoBlaze.webelement.LinkLabel;
import com.demoBlaze.webelement.TextBox;

public class CartPage extends BasePage {

	public LinkLabel lnkDeleteDelli78gb = new LinkLabel("xpath","//td[text()='Dell i7 8gb']/following-sibling::td/a[text()='Delete']");
	public Label lblSonyVaioi5 = new Label("xpath","//td[text()='Sony vaio i5']");
	public Label lblTotalExpectedAmount = new Label("id","totalp");
	public Button btnPlaceOrder = new Button("xpath","//button[text()='Place Order']");
	public TextBox edtName = new TextBox("id","name");
	public TextBox edtCountry = new TextBox("id","country");
	public TextBox edtCity = new TextBox("id","city");
	public TextBox edtCreditCard = new TextBox("id","card");
	public TextBox edtMonth = new TextBox("id","month");
	public TextBox edtyear = new TextBox("id","country");
	
	public Button btnPurchase = new Button("xpath","//button[text()='Purchase']");
	public Button btnClose = new Button("xpath","//button[text()='Purchase']/preceding-sibling::button[text()='Close']");
	
	
	public Button btnOk = new Button("xpath","//button[text()='OK']");
	public Label lbldetails = new Label("xpath","//p[@class='lead text-muted ']");
	
	public BaseElement elementFade = new BaseElement("xpath", "//div[@class='modal fade show']");
	public Label lblSuccessMessage = new Label("xpath","//h2[text()='Thank you for your purchase!']");
	
    
	public CartPage() {
		
		super("", "//h2[text()='Products']", 50000, "xpath");
	}

// **** Methods ***	

	public void navigateTo() {
			
	}
}