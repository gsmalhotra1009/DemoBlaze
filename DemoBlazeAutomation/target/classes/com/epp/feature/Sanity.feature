Feature: Request

Background: 
	 When User launches Url with existing URL

  @Sanity
  Scenario: Validate that user is able to select the item from each category
    Given User is on Page "HomePage"
  #  When User enters log in credentials
    |test_user1 | Test@12|
    When User clicks on element "lnkPhones"
    Then Validate that element "lnkSamsungGalaxyS6" is present
    When User clicks on element "lnkLaptops"
    Then Validate that element "lnkSonyVaioI5" is present
    When User clicks on element "lnkMonitors"
    Then Validate that element "lnkAppleMonitor24" is present
    
    
    @Sanity
  	Scenario Outline: Validate that user is able to select the item, delete the item and order the item and order id generated 
    Given User is on Page "HomePage"
    When User clicks on element "lnkLaptops"
    Then Validate that element "lnkSonyVaioI5" is present
    When User clicks on element "lnkSonyVaioI5"
  	Then Validate that element "lblSonyVaioI5" is present 
    When User clicks on element "btnAddToCart"
    Then User Verifies the popup
    When User clicks on element "lnkHome"
    When User clicks on element "lnkLaptops"
    Then Validate that element "lnkDellI78GB" is present
    When User clicks on element "lnkDellI78GB"
    Then Validate that element "lblDelli78gb" is present
    When User clicks on element "btnAddToCart"
    Then User Verifies the popup
   	Then User is on Page "HomePage"
    Then User clicks on element "lnkCart"
    Then User is on Page "CartPage"
    When User clicks on element "lnkDeleteDelli78gb"
    Then Validate that element "lnkDeleteDelli78gb" is not present
    Then User captures the amount of the product "lblTotalExpectedAmount"
    When User clicks on element "btnPlaceOrder"
    Then Validate that element "elementFade" is present
    When User types "<Name>" in textbox "edtName"
    When User types "<Country>" in textbox "edtCountry"
    When User types "<City>" in textbox "edtCity"
    When User types "<CreditCard>" in textbox "edtCreditCard"
    When User types "<Month>" in textbox "edtMonth"
    When User types "<Year>" in textbox "edtyear"
    Then User clicks on element "btnPurchase"
    Then User captures the details of the purchase from element "lbldetails"
    Then Validate that "lbldetails" has inner text "EnvAmount"
    When User clicks on element "btnOk"
    Then User is on Page "HomePage"
   
   
    Examples:
    |Name		 |Country|City      |CreditCard        |Month    |Year|
    |Cucumber|India  |Chandigarh|456789123456789632|September|2056|
    
    
    
    @Sanity
  	Scenario: Validate that Pop up appears if user clicks on purchase without entering form details
    Given User is on Page "HomePage"
    When User clicks on element "lnkLaptops"
    Then Validate that element "lnkSonyVaioI5" is present
    When User clicks on element "lnkSonyVaioI5"
  	Then Validate that element "lblSonyVaioI5" is present 
    When User clicks on element "btnAddToCart"
    Then User Verifies the popup
    When User clicks on element "lnkHome"
    When User clicks on element "lnkLaptops"
    Then Validate that element "lnkDellI78GB" is present
    When User clicks on element "lnkDellI78GB"
    Then Validate that element "lblDelli78gb" is present
    When User clicks on element "btnAddToCart"
    Then User Verifies the popup
   	Then User is on Page "HomePage"
    Then User clicks on element "lnkCart"
    Then User is on Page "CartPage"
    When User clicks on element "lnkDeleteDelli78gb"
    Then Validate that element "lnkDeleteDelli78gb" is not present
    Then User captures the amount of the product "lblTotalExpectedAmount"
    When User clicks on element "btnPlaceOrder"
    Then Validate that element "elementFade" is present
    Then User clicks on element "btnPurchase"
 		Then User Verifies the popup
    
    
    @Sanity
  	Scenario Outline: Validate that user moves to cart page if cancel is clicked on submitform
    Given User is on Page "HomePage"
    When User clicks on element "lnkLaptops"
    Then Validate that element "lnkSonyVaioI5" is present
    When User clicks on element "lnkSonyVaioI5"
  	Then Validate that element "lblSonyVaioI5" is present 
    When User clicks on element "btnAddToCart"
    Then User Verifies the popup
    When User clicks on element "lnkHome"
    When User clicks on element "lnkLaptops"
    Then Validate that element "lnkDellI78GB" is present
    When User clicks on element "lnkDellI78GB"
    Then Validate that element "lblDelli78gb" is present
    When User clicks on element "btnAddToCart"
    Then User Verifies the popup
   	Then User is on Page "HomePage"
    Then User clicks on element "lnkCart"
    Then User is on Page "CartPage"
    When User clicks on element "lnkDeleteDelli78gb"
    Then Validate that element "lnkDeleteDelli78gb" is not present
    Then User captures the amount of the product "lblTotalExpectedAmount"
    When User clicks on element "btnPlaceOrder"
    Then Validate that element "elementFade" is present
    When User types "<Name>" in textbox "edtName"
    When User types "<Country>" in textbox "edtCountry"
    When User types "<City>" in textbox "edtCity"
    When User types "<CreditCard>" in textbox "edtCreditCard"
    When User types "<Month>" in textbox "edtMonth"
    When User types "<Year>" in textbox "edtyear"
    Then User clicks on element "btnClose"
 	  Then User is on Page "CartPage"
   
   
    Examples:
    |Name		 |Country|City      |CreditCard        |Month    |Year|
    |Java    |India  |Chandigarh|459189123456789632|September|2085|
   
    
    