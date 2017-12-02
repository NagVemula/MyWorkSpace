package selenium.framework;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class TestCaseDetails {

	private ExtentReports report=null;
	private ExtentTest logger = null;
	private WebDriver driver = null;
	private WebElement element = null;
	
	public WebElement getElement() {
		return element;
	}
	public void setElement(WebElement element) {
		this.element = element;
	}
	public WebDriver getDriver() {
		return driver;
	}
	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}
	public ExtentReports getReport() {
		return report;
	}
	public void setReport(ExtentReports report) {
		this.report = report;
	}
	public ExtentTest getLogger() {
		return logger;
	}
	public void setLogger(ExtentTest logger) {
		this.logger = logger;
	}
	
	
}
