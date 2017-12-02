package seleniumframework.tests;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import selenium.framework.GlobalVariables;
import selenium.framework.SeleniumKeywords;
import selenium.framework.TestCaseDetails;
import seleniumframework.utilities.CommonMethods;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class BaseTestClass extends SeleniumKeywords{
	
	@BeforeMethod
	public void beforeMethod(Method method){
		
		//Thread.currentThread().setName(method.getName());
		TestCaseDetails tcDetails = new TestCaseDetails();
		System.out.println(System.getProperty("user.dir")+"/ExtentReports/ExtentReport.html");
		ExtentReports extentReports = new ExtentReports(System.getProperty("user.dir")+"/ExtentReports/"+method.getName()+System.currentTimeMillis()+".html");
		extentReports.loadConfig(new File(System.getProperty("user.dir")+"\\extent-config.xml"));
		ExtentTest logger = extentReports.startTest("Test Automation");
		
		tcDetails.setLogger(logger);
		tcDetails.setReport(extentReports);
		
		logger.log(LogStatus.INFO, "Execution "+method.getName()+" started", method.getName()+" Started");
		GlobalVariables.tcConfig.put(Thread.currentThread().getName(), tcDetails);
	}
	
	@AfterMethod
	public void afterMethod(ITestResult result){
		TestCaseDetails tcDetails = GlobalVariables.tcConfig.get(Thread.currentThread().getName());
		 
		tcDetails.getLogger().log(LogStatus.INFO, "Execution Ended", "Execution of "+result.getMethod().getMethodName()+" ended");
		tcDetails.getReport().endTest(tcDetails.getLogger());
		tcDetails.getReport().flush();
		tcDetails.getDriver().quit();
	}
	
	
	@DataProvider(name="testData")
	public synchronized Iterator testData(Method method) throws InvalidFormatException, IOException{
		List<Map<String,String>> lst = dataReader(method.getName());
		System.out.println(lst.size());
		List lstRet = new ArrayList();
		for(Map map:lst){
			lstRet.add(new Object[]{map});
		}
		System.out.println(lstRet.size());
		return lstRet.iterator();
	}


	private List<Map<String,String>> dataReader(String testCaseName) throws InvalidFormatException, IOException {

		File file = new File(System.getProperty("user.dir")+"/TestDataFiles/"+CommonMethods.getValue("TestDataFile",""));
		Workbook wBook = WorkbookFactory.create(file);
		List<Map<String,String>> lst = new ArrayList<Map<String,String>>();
		Sheet sheet = wBook.getSheetAt(0);
		int totalRows = sheet.getPhysicalNumberOfRows();
		
		for(int row=1;row<totalRows;row++){
			Row rowData = sheet.getRow(row);
			Cell cell = rowData.getCell(0);
			if(new DataFormatter().formatCellValue(cell).equals(testCaseName)){
				int colNumber = sheet.getRow(0).getLastCellNum();
				Map<String,String>  map = new LinkedHashMap<String,String>();
				for(int col=0;col<colNumber;col++){
					map.put(sheet.getRow(0).getCell(col).getStringCellValue(), new DataFormatter().formatCellValue(rowData.getCell(col)));
				}
				lst.add(map);
				//break;
			}
		}
		return lst;
	}

	
}
