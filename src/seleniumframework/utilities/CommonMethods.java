package seleniumframework.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import selenium.framework.GlobalVariables;
import selenium.framework.TestCaseDetails;

import com.relevantcodes.extentreports.ExtentTest;

public class CommonMethods {

	public static String takeScreenShot(String fileName){
		TestCaseDetails tcDetails = GlobalVariables.tcConfig.get(Thread.currentThread().getName());
		WebDriver driver = tcDetails.getDriver();
		ExtentTest logger = tcDetails.getLogger();
		try{
		String destFilePath = System.getProperty("user.dir")+"\\ScreenShots\\"+fileName+".png";
		TakesScreenshot ts = (TakesScreenshot) driver;
		File srcFile = ts.getScreenshotAs(OutputType.FILE);
		File destFile = new File(destFilePath);
		FileUtils.copyFile(srcFile, destFile);
		return destFilePath;
		
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	public static String getValue(String key, String defaultValue) {

		Properties pro = new Properties();
		String value = null;
		try {
			pro.load(new FileInputStream(new File(System.getProperty("user.dir")+"/config.properties")));
			return pro.getProperty(key, defaultValue);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		return null;
	}
	
	
	
	
}
