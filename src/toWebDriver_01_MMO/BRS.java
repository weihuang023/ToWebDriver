package toWebDriver_01_MMO; 

import java.io.FileWriter;
import java.util.List;
import java.io.File;
import java.io.PrintWriter;

import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

public class BRS{
	//variables declaration goes here
	public WebDriver driver;
	//Add flag for each page 
	private String page;
	//getPage
	public String getPage(){
		return this.page;
	}
	//setPage
	public void setPage(WebDriver driver,String page){
//		System.out.println("SUBSTRING=================================="+driver.getCurrentUrl().substring(12,17));
		switch(driver.getCurrentUrl().substring(12,17)){
			
			case "1800f":
				this.page = page + "_18F";
				break;
			
			case "1800b":
				this.page = page + "_18B";
				break;
			
			case "fruit":
				this.page = page + "_FBQ";
				break;
			
			case "harry":
				this.page = page + "_HND";
				break;
			
			case "chery":
				this.page = page + "_CCO";
				break;
			
			case "wolfe":
				this.page = page + "_WLF";
				break;
			
			case "stock":
				this.page = page + "_STY";
				break;
				
			case "thepo":
				this.page = page + "_TPF";
				break;
			
			case "perso":
				this.page = page + "_PU";
				break;
				
			case "simpl":
				this.page = page + "_SC";
				break;
				
			default:
				this.page = page;
		
		}
	}
	//Append to file set to true if use getError() 
	private boolean append_to_file = true;
	//getAppend
	public boolean getAppendToFile(){
		return append_to_file;
	}
	//setAppend
	public void setAppendToFile(boolean append_to_file){
		this.append_to_file = append_to_file;
	}
	//No Setup Required 
	@Before
	public void setUp() throws Exception {
		
	}
	//Start GU TEST 		
	@Test
	public void findingGUErrorsOnPage() throws Exception {
		System.out.println("------------------------ Test Start -----------------------------");
		
		int n = 0;

		for ( int i = 0 ; i <= n; i++){
			String home = "https://www.1800flowers.com";
//			String home = "https://www.fruitbouquets.com";
			String collection = "Birthday";
		    String zipCode = "11355";
		    String locationType = "Residence";
		    String deliveryDate = "month1day30"; 
			String title = "Mr.";
			String firstName = "Wei";
			String lastName = "Huang";
			String addressLine1 = "1 Main st.";
//			String email = "whuang@1800flowers.com";
			String telephone = "1234567890";
			
			System.setProperty("webdriver.chrome.driver", "./lib/chromedriver.exe");
			ChromeOptions  options = new ChromeOptions();
			options.addArguments("start-maximized");
			
		    options.addExtensions(new File("./lib/extension_1_0_9_0.crx"));
		    DesiredCapabilities capabilities = new DesiredCapabilities();
		    capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		    ChromeDriver driver = new ChromeDriver(capabilities);

		    System.out.println("Step 1: SETUP WAVE Exetension");
		    driver.get("chrome://extensions-frame/");
		    driver.findElement(By.xpath("//a[@class='extension-commands-config']")).click();
		    driver.findElement(By.xpath("//span[@class='command-shortcut-text']")).sendKeys(Keys.CONTROL+"m");
		    driver.findElement(By.id("extension-commands-dismiss")).click();
			
		    System.out.println("Step 2: GOTO Home Page");
		    home(driver, home);
		    activeWAVE(driver);
		    getERROR(driver);
		    verifyERROR(driver);
		    
		    System.out.println("Step 3: GOTO Collection Page");
		    collection(driver, collection);		    
		    activeWAVE(driver);
		    getERROR(driver);
		    verifyERROR(driver);
		    
		    System.out.println("Step 4: GOTO PRODUCT Page");
		    product(driver);
		   	activeWAVE(driver);
		    getERROR(driver);
		    verifyERROR(driver);
	    
		    System.out.println("Step 5: GOTO CKECK OUT Page");
		    checkout(driver,zipCode,locationType,deliveryDate);
		   	activeWAVE(driver);
		    getERROR(driver);
		    verifyERROR(driver);
		    
		    System.out.println("Step 6: GOTO PLACE ORDER Page");
		    placeOrder(driver,title,firstName,lastName, locationType,addressLine1,telephone);
		   	activeWAVE(driver);
		    getERROR(driver);
		    verifyERROR(driver);
//		    
		    driver.quit();
		}
		 System.out.println("------------------------ Test End -----------------------------");
	}
	//Start RU TEST
	@Test
	public void findingRUErrorsOnPage() throws Exception{
		
	}
	//Home - Landing Page
	public void home(WebDriver driver, String homePage) throws InterruptedException{
	    driver.get(homePage);
	    this.setPage(driver,"PG1_HM");
//	    driver.manage().window().setPosition(new Point(-1000, 0));
	    Thread.sleep(5000);
	}
	//Collection - Birthday and Best Sell, GNAV for 18F and FBQ
	public void collection(WebDriver driver,String collectionPage) throws InterruptedException{
	    this.setPage(driver,"PG2_CL_");
	    driver.findElement(By.linkText(collectionPage)).click();
	    Thread.sleep(5000);
	    
	    if (driver.getCurrentUrl().contains("1800flowers")){
		    System.out.println("SKIPPING GNAV on 18F Products");
		    driver.findElement(By.linkText("skip")).click();
	    };
	    Thread.sleep(5000);
	}
	//Product - Select 1st Product on the left corner
	public void product(WebDriver driver){
	    this.setPage(driver,"PG3_PR_");
	    driver.findElement(By.cssSelector("img.prod-img")).click();
		WebDriverWait wait = new WebDriverWait(driver, 60);
	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("prodimglarge")));		   
	}
	//Checkout - Delivery Date select as 30th of month
	public void checkout(WebDriver driver, String zipCode, String locationType, String deliveryDate)throws InterruptedException{
	    this.setPage(driver,"PG4_CO");
	    System.out.println("Enter Zipcode            :" +zipCode);
        driver.findElement(By.xpath("//input[@id='zipCode']")).click();
        driver.findElement(By.id("zipCode")).click();
        driver.findElement(By.id("zipCode")).clear();
        driver.findElement(By.id("zipCode")).sendKeys(zipCode);
		WebDriverWait wait = new WebDriverWait(driver, 60);

        System.out.println("Select New Location Type :" +locationType);
        driver.findElement(By.id("locationCode")).click();
        new Select(driver.findElement(By.id("locationCode"))).selectByVisibleText(locationType);
        
        System.out.println("Select Delivery Date     :" +deliveryDate);
        driver.findElement(By.cssSelector("img[alt=\"Calendar\"]")).click();
	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(deliveryDate)));	
        driver.findElement(By.id(deliveryDate)).click();
        
        System.out.println("ADD TO CART");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//a[@id='addItemTocartButton'])[2]")));	
        driver.findElement(By.xpath("(//a[@id='addItemTocartButton'])[2]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("img[alt=\"checkout\"]")));	
        
        System.out.println("CHECKOUT");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("img[alt=\"checkout\"]")));	
        driver.findElement(By.cssSelector("img[alt=\"checkout\"]")).click();
        Thread.sleep(5000);
	}
	//Place Order - 1. No Gift Message 2. Skip Wrap
	public void placeOrder(WebDriver driver, String title, String firstName, String lastName, String locationType, String addressLine1, String telephone) throws InterruptedException{
	    this.setPage(driver,"RP");
	    System.out.println("Enter Recipient Shipping Address");

	    new Select(driver.findElement(By.id("addressSelection_1"))).selectByVisibleText(title);
		driver.findElement(By.id("firstName")).clear();
		driver.findElement(By.id("firstName")).sendKeys(firstName);
		driver.findElement(By.id("lastName")).clear();
		driver.findElement(By.id("lastName")).sendKeys(lastName);
		new Select(driver.findElement(By.id("addressTypeSelection"))).selectByVisibleText(locationType);
		if("Business".equalsIgnoreCase(locationType)){driver.findElement(By.id("WC_ShoppingCartAddressEntryForm_FormInput_company_1")).sendKeys("Auto Test Company");}
		driver.findElement(By.id("QAS_lineone")).sendKeys(addressLine1);
	
//		if(Brand.equalsIgnoreCase("FBQ"))
//		{
//			new Select(driver.findElement(By.id("city12"))).selectByVisibleText("Flushing");
//		}
//		else if(Brand.equalsIgnoreCase("FMC")||Brand.equalsIgnoreCase("CCO")||Brand.equalsIgnoreCase("TPF"))
//		{	
//			driver.findElement(By.id("QAS_city")).sendKeys("Flushing");
//			new Select(driver.findElement(By.id("state"))).selectByVisibleText("New York");
//			driver.findElement(By.id("zip")).sendKeys(zipCode);
//		}
		
		driver.findElement(By.id("WC_ShoppingCartAddressEntryForm_FormInput_phone1_1")).sendKeys(telephone);
		driver.findElement(By.name("billingAddress")).click();
		//driver.findElement(By.cssSelector("img[alt=\"continueToDeliveryOptions\"]")).click();
		Thread.sleep(1000);
		//driver.findElement(By.id("ContinueDeliverBtn")).click();
		driver.findElement(By.id("ContinueDeliverBtn")).sendKeys(Keys.RETURN);;
		Thread.sleep(1000);
	
	    this.setPage(driver,"GM");
	    System.out.println("Select No Gift Message");
		driver.findElement(By.id("giftMessages.noCard")).click();
	    
		System.out.println("Continue Place Order");
		driver.findElement(By.id("BP-ContinueReviewPlaceOrderBtn")).click();
		
		 System.out.println("Skip Wrap Up");
		if(driver.findElement(By.cssSelector("div.n-chkWrapupBtn1 > img")).isEnabled()){
			driver.findElement(By.cssSelector("div.n-chkWrapupBtn1 > img")).click();
		}
		
	    this.setPage(driver,"PG5_PO");
	    System.out.println("Review and Place order");
	}
	//Active WAVE - use robot
	public void activeWAVE(WebDriver driver) throws AWTException{
	    System.out.println("ACTIVING WAVE Extension");
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress('M');
		robot.keyRelease('M');
		robot.keyRelease(KeyEvent.VK_CONTROL);
//		new Actions(driver).sendKeys(Keys.CONTROL + "m").build().perform();
		System.out.println("WAITING WAVE Elements");
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[contains(@src,'chrome-extension://jbbplnpkjmmeebjpijfedlgcdilocofh/img/icons/text_justified.png')]")));		   
	}
	//Get Error Count with different error
	public void getERROR(WebDriver driver) throws Exception{
		System.out.println("GETTING ERROR COUNTS");
		List<WebElement> errorTotal = driver.findElements(By.xpath("//img[contains(@alt,'ERRORS: ')]")); 
		
	    int sumOflabel_missing = 0;
		for(int j=0;j<errorTotal.size()-1;j++){
	    	String alt = errorTotal.get(j).getAttribute("alt");
	    	//System.out.println("alt: "+alt+ " Index "+j);
	    	if (alt.equals("ERRORS: Missing form label")) {
	    		sumOflabel_missing = sumOflabel_missing + 1;
	    	}
	    }
		
	    int sumOfalt_text_missing = 0;
		for(int j=0;j<errorTotal.size()-1;j++){
	    	String alt = errorTotal.get(j).getAttribute("alt");
	    	//System.out.println("alt: "+alt+ " Index "+j);
	    	if (alt.equals("ERRORS: Missing alternative text")) {
	    		sumOfalt_text_missing = sumOfalt_text_missing + 1;
	    	}
	    }
		
		
	    int sumOflink_empty = 0;
		for(int j=0;j<errorTotal.size()-1;j++){
	    	String alt = errorTotal.get(j).getAttribute("alt");
	    	//System.out.println("alt: "+alt+ " Index "+j);
	    	if (alt.equals("ERRORS: Empty link")) {
	    		sumOflink_empty = sumOflink_empty + 1;
	    	}
	    }
		
	    int sumOflabel_empty = 0;
		for(int j=0;j<errorTotal.size()-1;j++){
	    	String alt = errorTotal.get(j).getAttribute("alt");
	    	//System.out.println("alt: "+alt+ " Index "+j);
	    	if (alt.equals("ERRORS: Empty form label")) {
	    		sumOflabel_empty = sumOflabel_empty + 1;
	    	}
	    }
		
		
	    int sumOfimg_button_missing = 0;
		for(int j=0;j<errorTotal.size()-1;j++){
	    	String alt = errorTotal.get(j).getAttribute("alt");
	    	//System.out.println("alt: "+alt+ " Index "+j);
	    	if (alt.equals("ERRORS: Image button missing alternative text")) {
	    		sumOfimg_button_missing = sumOfimg_button_missing + 1;
	    	}
	    }
		
	    int sumOfimg_link_missing = 0;
		for(int j=0;j<errorTotal.size()-1;j++){
	    	String alt = errorTotal.get(j).getAttribute("alt");
	    	//System.out.println("alt: "+alt+ " Index "+j);
	    	if (alt.equals("ERRORS: Linked image missing alternative text")){
	    		sumOfimg_link_missing = sumOfimg_link_missing + 1;
	    	}
	    }
			
		int sumOflablels_multiple = 0;
		for(int j=0;j<errorTotal.size()-1;j++){
	    	String alt = errorTotal.get(j).getAttribute("alt");
	    	//System.out.println("alt: "+alt+ " Index "+j);
	    	if (alt.equals("ERRORS: Multiple form labels")) {
	    		sumOflablels_multiple = sumOflablels_multiple + 1;
	    	}
		}
			
		int sumOfspacer_img_missing = 0;
		for(int j=0;j<errorTotal.size()-1;j++){
	    	String alt = errorTotal.get(j).getAttribute("alt");
	    	//System.out.println("alt: "+alt+ " Index "+j);
	    	if (alt.equals("ERRORS: Spacer image missing alternative text")){
	    		sumOfspacer_img_missing = sumOfspacer_img_missing + 1;
	    	}
		}
			
		int sumOfbroken_skip_link = 0;
		for(int j=0;j<errorTotal.size()-1;j++){
	    	String alt = errorTotal.get(j).getAttribute("alt");
	    	//System.out.println("alt: "+alt+ " Index "+j);
	    	if (alt.equals("ERRORS: Broken skip link")) {
	    		sumOfbroken_skip_link = sumOfbroken_skip_link + 1;
	    	}
		}
		
		int sumOfbroken_aria_reference = 0;
		for(int j=0;j<errorTotal.size()-1;j++){
	    	String alt = errorTotal.get(j).getAttribute("alt");
	    	//System.out.println("alt: "+alt+ " Index "+j);
	    	if (alt.equals("ERRORS: Broken ARIA reference")) {
	    		sumOfbroken_aria_reference = sumOfbroken_aria_reference + 1;
	    	}
		}
				
	    int sumOfError = 0;
		for(int j=0;j<errorTotal.size()-1;j++){
	    	String alt = errorTotal.get(j).getAttribute("alt");
//	    	System.out.println("alt: "+alt+ " Index "+j);
	    	if (alt.startsWith("ERRORS: ")) {
	    		sumOfError = sumOfError + 1;
//	    		System.out.println(alt);
	    	}
	    }
		
		int other = sumOfError - (sumOflink_empty
									+sumOflabel_empty
									+sumOflabel_missing
									+sumOfalt_text_missing
									+sumOfimg_link_missing
									+sumOfimg_button_missing
									+sumOflablels_multiple
									+sumOfbroken_skip_link
									+sumOfspacer_img_missing
									+sumOfbroken_aria_reference);
	    System.out.println( "--------------------------------------------------------------------------");
	    if (this.getPage().equals("collection")){
	    	System.out.printf( "%-15s %13s %n", "Current URL: ", driver.getCurrentUrl().substring(0));
	    }
	    else {
	       	System.out.printf( "%-15s %13s %n", "Current URL: ", driver.getCurrentUrl());
	    }
	    System.out.printf( "%-15s %13s %n", "Error TYPE"+"\t\t\t\t", "COUNTS");
	    System.out.printf( "%-15s %13s %n", "Broken ARIA reference"+"\t\t\t", sumOfbroken_aria_reference);
	    System.out.printf( "%-15s %13s %n", "Broken skip link"+"\t\t\t", sumOfbroken_skip_link);
	    System.out.printf( "%-15s %13s %n", "Empty link"+"\t\t\t\t", sumOflink_empty);
	    System.out.printf( "%-15s %13s %n", "Empty form label"+"\t\t\t", sumOflabel_empty);
	    System.out.printf( "%-15s %13s %n", "Missing form label"+"\t\t\t", sumOflabel_missing);
	    System.out.printf( "%-15s %13s %n", "Missing alternative text"+"\t\t", sumOfalt_text_missing);
	    System.out.printf( "%-15s %13s %n", "Linked image missing alternative text"+"\t", sumOfimg_link_missing);
	    System.out.printf( "%-15s %13s %n", "Image button missing alternative text"+"\t", sumOfimg_button_missing);
	    System.out.printf( "%-15s %13s %n", "Spacer image missing alternative text"+"\t", sumOfspacer_img_missing);
	    System.out.printf( "%-15s %13s %n", "Multiple form labels"+"\t\t\t", sumOflablels_multiple);
	    System.out.printf( "%-15s %13s %n", "\t\t\t"+"OTHER", other);
	    System.out.printf( "%-15s %13s %n", "\t\t\t"+"Total", sumOfError);
	    System.out.println( "--------------------------------------------------------------------------");
	    
	    
	    FileWriter fileWriter = new FileWriter("C://Results_"+this.getPage()+".txt", this.getAppendToFile());
		PrintWriter printWriter = new PrintWriter(fileWriter);
	    
	    printWriter.println( "--------------------------------------------------------------------------");
	    if (this.getPage().contains("CL")){
	    	printWriter.printf( "%-15s %13s %n", "Current URL: ", driver.getCurrentUrl().substring(0));
	    }
	    else {
	    	printWriter.printf( "%-15s %13s %n", "Current URL: ", driver.getCurrentUrl());
	    }
	    printWriter.printf( "%-15s %13s %n", "Error TYPE"+"\t\t\t", "COUNTS");
	    printWriter.printf( "%-15s %13s %n", "Broken ARIA reference"+"\t\t", sumOfbroken_aria_reference);
	    printWriter.printf( "%-15s %13s %n", "Broken skip link"+"\t\t\t", sumOfbroken_skip_link);
	    printWriter.printf( "%-15s %13s %n", "Empty link"+"\t\t\t", sumOflink_empty);
	    printWriter.printf( "%-15s %13s %n", "Empty form label"+"\t\t\t", sumOflabel_empty);
	    printWriter.printf( "%-15s %13s %n", "Missing form label"+"\t\t\t", sumOflabel_missing);
	    printWriter.printf( "%-15s %13s %n", "Missing alternative text"+"\t\t", sumOfalt_text_missing);
	    printWriter.printf( "%-15s %13s %n", "Linked image missing alternative text"+"\t", sumOfimg_link_missing);
	    printWriter.printf( "%-15s %13s %n", "Image button missing alternative text"+"\t", sumOfimg_button_missing);
	    printWriter.printf( "%-15s %13s %n", "Spacer image missing alternative text"+"\t", sumOfspacer_img_missing);
	    printWriter.printf( "%-15s %13s %n", "Multiple form labels"+"\t\t\t", sumOflablels_multiple);
	    printWriter.printf( "%-15S %13s %n", "\t\t\t"+"Other", other);
	    printWriter.printf( "%-15S %13s %n", "\t\t\t"+"Total", sumOfError);
	    printWriter.println( "--------------------------------------------------------------------------");
	    printWriter.close();
	}
	//Verify each error on type and HTML
	public void verifyERROR(WebDriver driver) throws Exception
	{
		List<WebElement> image_Error = driver.findElements(By.xpath("//img[contains(@alt,'ERRORS: ')]")); 
		//System.out.println("Number of Errors :"+image_Error.size());
	    FileWriter fileWriter = new FileWriter("c://Results_"+this.getPage()+".txt",this.getAppendToFile());
		PrintWriter printWriter = new PrintWriter(fileWriter);
		
		int ErrorCounter = 0;
        for(int j=0;j<image_Error.size()-1;j++) {
            String alt = image_Error.get(j).getAttribute("alt");
			
            if (alt.startsWith("ERRORS: ")) {
                ErrorCounter = ErrorCounter + 1;
                System.out.println("Error Num#: "+ErrorCounter);
                System.out.println("Error Type: "+alt);
                WebElement ElementWithError = (image_Error.get(j)).findElement(By.xpath(".."));
//              WebElement ElementWithError1 = (link_emptyImage.get(j)).findElement(By.xpath("preceding-sibling::"));
//              WebElement ElementWithError = (link_empty.get(j)).findElement(By.xpath("preceding-sibling::"));
//              String Error = (ElementWithError.getAttribute("outerHTML").split(">")[0])+">";System.out.println("Element HTML -" +Error);
                String ParentTag = ElementWithError.getTagName().toString();
                System.out.println("Parent TAG:   " +ParentTag);
                String Error = (ElementWithError.getAttribute("outerHTML"));
                System.out.println("Element HTML: " +Error);
//             	String ParentId = ElementWithError.getAttribute("id").toString(); System.out.println("ID -" +ParentId);
//		    	String ParentsClass = ElementWithError.getAttribute("class").toString();System.out.println("Class -" +ParentsClass);
//		    	String ParentsTitle = ElementWithError.getAttribute("title").toString();System.out.println("Title -" +ParentsTitle);
//		    	String ParentsHref = parent.getAttribute("alt").toString();System.out.println("Tag -" +ParentTag)
//		    	String ParentsText = ElementWithError.getText().toString();System.out.println("Text -" +ParentsText);
  	
//		    	String fileName = "Link Missing -" + j;
//		    	byte[] screengrab = ElementWithError.getScreenshotAs(OutputType.BYTES);
//		    	BufferedImage img = ImageIO.read(new ByteArrayInputStream(screengrab));
//		    	File screenshotLocation = new File("Images/"+fileName +".png");    
//	    	    ImageIO.write(img, "png", screenshotLocation);
//              /html/body/div[11]/div[4]/div[11]/div[1]/form/div[2]/img
            	System.out.println("\n****************************************\n");
//              String fileName = "err,txt";
                if (ParentTag.equals("body")){
            		printWriter.println("Error Num#: "+"\t"+ErrorCounter);
    				printWriter.println("Error Type: "+"\t"+alt);
    				printWriter.println("Parent TAG: "+"\t" +ParentTag);
    				printWriter.println("======= Ingored Element HTML on Parent Tag BODY =====");
    				printWriter.println("\n****************************************\n");
                }
                else if (alt.equals("ERRORS: Broken ARIA reference")){
        			printWriter.println("Error Num#: "+"\t"+ErrorCounter);
    				printWriter.println("Error Type: "+"\t"+alt);
    				printWriter.println("======= Ingored Element Type "+alt.substring(5)+" =====");
    				printWriter.println("\n****************************************\n");
                }
                else {
                	printWriter.println("Error Num#: "+"\t"+ErrorCounter);
    				printWriter.println("Error Type: "+"\t"+alt);
    				printWriter.println("Parent TAG: "+"\t" +ParentTag);
    				printWriter.println("Element HTML: "+"\t"+Error);
    				printWriter.println("\n****************************************\n");
                }
            }
        }
        
        System.out.printf( "%-15s %13s %n", "Total Error Found "+this.getPage(), ErrorCounter);
       	System.out.println("\n****************************************\n");
        
        printWriter.printf( "%-15s %13s %n", "Total Error Found "+this.getPage(), ErrorCounter);
        printWriter.println("\n****************************************\n");
        printWriter.close();
//		String text = driver.findElement(By.xpath("//img[contains(@src,'chrome-extension://jbbplnpkjmmeebjpijfedlgcdilocofh/img/icons/label_missing.png')]")).getText();
//		String tag = driver.findElement(By.xpath("//img[contains(@src,'chrome-extension://jbbplnpkjmmeebjpijfedlgcdilocofh/img/icons/label_missing.png')]")).getTagName();
	}
	//Driver 
	public void callBrowser()
    {

	//########################################################################################################################
	
	//#########################################################################################################################
	
	/*  ProfilesIni profile = new ProfilesIni();
		FirefoxProfile ffprofile = profile.getProfile("default");*/
	//	ffprofile.addExtension(new File("path/to/my/firebug.xpi"));							
	//	FirefoxProfile profile = new FirefoxProfile();
	//	ffprofile.setPreference("network.proxy.type", 1); //1 for manual proxy, 2 for auto config url
	//	ffprofile.setPreference("network.proxy.http", "cptmg");
		//ffprofile.setPreference("network.proxy.http_port", 4444);
	//System.setProperty("webdriver.gecko.driver","C:\\Selenium Testing 2\\Selenium component\\geckodriver.exe");
	//driver = new FirefoxDriver();
	
	//ProfilesIni profile = new ProfilesIni();
	//FirefoxProfile myprofile = profile.getProfile("default");
	//myprofile.setAcceptUntrustedCertificates(true);
	//myprofile.setAssumeUntrustedCertificateIssuer(false);
	//driver = new FirefoxDriver(myprofile);
	
	//=====================================================================
	// Fire Fox - MJS
	//=====================================================================
	/*
	// This is the current Firefox Driver - 7-10-17 //
	System.setProperty("webdriver.gecko.driver","C:\\Selenium Testing/Selenium component/geckodriver.exe");
	DesiredCapabilities capabilities = new DesiredCapabilities();
	capabilities.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
	driver = new FirefoxDriver(capabilities);
	*/
	//---------------------------------------------------------------------
	//=====================================================================
	// Chrome - MJS
	//=====================================================================
	//System.setProperty("webdriver.chrome.driver", "C:/Selenium Testing/Selenium component/chromedriver.exe");
	//driver = new ChromeDriver();
	// Fixes Disable Developer Extensions Bug
	/*ChromeOptions options = new ChromeOptions();
	options.addArguments("--disable-extensions");
	System.setProperty("webdriver.chrome.driver", "C:/Selenium Testing 2/chromedriver.exe");
	driver = new ChromeDriver(options);
	*/
	// */
	//=====================================================================
	//=====================================================================
					/*System.setProperty("webdriver.firefox.marionette","C:\\Selenium Testing 2\\geckodriver.exe");
				File pathToBinary = new File("C:\\Program Files\\Mozilla Firefox43\\firefox.exe");
				FirefoxBinary ffBinary = new FirefoxBinary(pathToBinary);
				driver = new FirefoxDriver(ffBinary,ffprofile);	*/		
	
	//	
	//	driver = new FirefoxDriver(ffprofile);
	//########################################################################################################################
	//System.setProperty("webdriver.chrome.driver", "C:/Users/loadtest/Documents/GitHub/myorder-selenium/modifymyorder/lib/chromedriver.exe");
	System.setProperty("webdriver.chrome.driver", "./lib/chromedriver.exe");
	ChromeOptions  options = new ChromeOptions();
	options.addArguments("start-maximized");
	driver = new ChromeDriver(options);
	//#########################################################################################################################
	//DesiredCapabilities cap=DesiredCapabilities.safari();
	//cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
	//System.setProperty("webdriver.safari.driver","Safari driver path");							
	//driver = new SafariDriver();
	//##########################################################################################################################
	//	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);//set the default time out for browser
	//	driver.manage().window().maximize();
	
	//		handle security popups		
	//		driver.get("www.google.com");
	//		WebDriverWait wait = new WebDriverWait(driver, 10);      
	//		Alert alert = wait.until(ExpectedConditions.alertIsPresent());     
	//		alert.authenticateUsing(new UserAndPassword("cnishant", ""));
	
    }
    //Clear
	public void deleteCookies() throws InterruptedException {
    	driver.manage().deleteAllCookies();
    }

}