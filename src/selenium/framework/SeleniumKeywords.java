package selenium.framework;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class SeleniumKeywords {
	
	public void initialseBrowser(String browserName){
		TestCaseDetails tcDetails = GlobalVariables.tcConfig.get(Thread.currentThread().getName());
		WebDriver driver= null;
		
		switch (browserName.toUpperCase()) {
		case "FF":
			System.setProperty("webdriver.gecko.driver",System.getProperty("user.dir")+"\\lib\\SeleniumJars\\geckodriver.exe");
			driver = new FirefoxDriver();
			tcDetails.setDriver(driver);
			driver.manage().window().maximize();
			break;
		case "IE":
			System.setProperty("webdriver.ie.driver",System.getProperty("user.dir")+"\\lib\\SeleniumJars\\IEDriverServer.exe");
			driver = new InternetExplorerDriver();
			tcDetails.setDriver(driver);
			driver.manage().window().maximize();
			break;
		case "CHROME":
			System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"\\lib\\SeleniumJars\\chromedriver.exe");
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			tcDetails.setDriver(driver);
			break;
		default:
			System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"\\lib\\SeleniumJars\\chromedriver.exe");
			driver = new ChromeDriver();
			tcDetails.setDriver(driver);
		}
	}

	public void launchApp(String url){
		TestCaseDetails tcDetails = GlobalVariables.tcConfig.get(Thread.currentThread().getName());
		WebDriver driver = tcDetails.getDriver();
		ExtentTest logger = tcDetails.getLogger();
		try{
			driver.get(url);
			logger.log(LogStatus.PASS, "Application is launched", url);
		}catch(Exception e){
			logger.log(LogStatus.FAIL, "Unable to launch the URL", e);
		}
	}
	
	public void clickElement(String locatorType,String locatorPath,String elementName){
		TestCaseDetails tcDetails = GlobalVariables.tcConfig.get(Thread.currentThread().getName());
		WebDriver driver = tcDetails.getDriver();
		ExtentTest logger = tcDetails.getLogger();
		
		try{
		setWebElement(locatorType, locatorPath);
		tcDetails.getElement().click();
		logger.log(LogStatus.PASS, "Click on the element "+elementName, "Clicked on the element "+elementName+" successfully");
		}catch(Exception e){
			
			logger.log(LogStatus.FAIL, "Unable to click on "+elementName+" the element because of the error "+e.getMessage() );
		}
	}
	
	public void enterText(String locatorType,String locatorPath,String valueToBeEntered,String elementName){
		TestCaseDetails tcDetails = GlobalVariables.tcConfig.get(Thread.currentThread().getName());
		WebDriver driver = tcDetails.getDriver();
		ExtentTest logger = tcDetails.getLogger();
		
		try{
		setWebElement(locatorType, locatorPath);
		tcDetails.getElement().sendKeys(valueToBeEntered);
		logger.log(LogStatus.PASS, "Entering the text in the textbox", valueToBeEntered+" is entered in "+elementName+ "field");
		}catch(Exception e){
			logger.log(LogStatus.FAIL, "Unable to enter the text in "+elementName+" because of the error "+e.getMessage() );
			
		}
	}
	
	public String getText(String locatorType,String locatorPath,String elementName){
		TestCaseDetails tcDetails = GlobalVariables.tcConfig.get(Thread.currentThread().getName());
		WebDriver driver = tcDetails.getDriver();
		ExtentTest logger = tcDetails.getLogger();
		
		try{
			
			setWebElement(locatorType, locatorPath);
			return tcDetails.getElement().getText();
		}catch(Exception e){
			logger.log(LogStatus.FAIL, "Unable to get the text from "+elementName, e);
			return null;
		}
	}
	
	public String getPageTitle(){
		TestCaseDetails tcDetails = GlobalVariables.tcConfig.get(Thread.currentThread().getName());
		WebDriver driver = tcDetails.getDriver();
		ExtentTest logger = tcDetails.getLogger();
		
		return driver.getTitle();
	}
	public void setWebElement(String locatorType,String locatorPath){
		TestCaseDetails tcDetails = GlobalVariables.tcConfig.get(Thread.currentThread().getName());
		WebDriver driver = tcDetails.getDriver();
		ExtentTest logger = tcDetails.getLogger();
		
		try{
		switch (locatorType.toUpperCase()) {
		case "ID":
			tcDetails.setElement(driver.findElement(By.id(locatorPath)));
		case "NAME":
			tcDetails.setElement(driver.findElement(By.name(locatorPath)));
		case "CLASSNAME":
			tcDetails.setElement(driver.findElement(By.className(locatorPath)));
		case "LINKTEXT":
			tcDetails.setElement(driver.findElement(By.linkText(locatorPath)));
		case "PARTIALLINKTEXT":
			tcDetails.setElement(driver.findElement(By.partialLinkText(locatorPath)));
		case "XPATH":
			tcDetails.setElement(driver.findElement(By.xpath(locatorPath)));
		case "CSS":
			tcDetails.setElement(driver.findElement(By.cssSelector(locatorPath)));
		default:
			logger.log(LogStatus.FAIL, "In valid Locating strategy");
		}
		
		}catch(Exception e){
			logger.log(LogStatus.FAIL , "WebElement is not identified ", e.getMessage());
		}
		
		
	}
	
}
