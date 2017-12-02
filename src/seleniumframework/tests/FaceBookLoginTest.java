package seleniumframework.tests;

import java.util.Map;

import org.testng.annotations.Test;

import selenium.framework.GlobalVariables;
import selenium.framework.TestCaseDetails;
import seleniumframework.elements.Facebook;
import seleniumframework.utilities.CommonMethods;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class FaceBookLoginTest extends BaseTestClass implements Facebook{
	
	@SuppressWarnings("unused")
	@Test(dataProvider="testData")
	public void testFBLogin(Map<String,String> data) throws InterruptedException{
		System.out.println(data);
		TestCaseDetails tcDetails = GlobalVariables.tcConfig.get(Thread.currentThread().getName());
		initialseBrowser(data.get("Browser"));
		ExtentTest logger = tcDetails.getLogger();
		tcDetails.getLogger().log(LogStatus.INFO, "Launch the application");
		launchApp("https://www.facebook.com/");
		logger.log(LogStatus.INFO, "Enter the Email or Phone number to log into fb");
		enterText("id", id_FbEmail, data.get("UserName"), "Email/Phone Number");
		logger.log(LogStatus.INFO, "Enter the passowrd to log into fb");
		enterText("id", id_FbPwd, data.get("UserName"), "Password");
		logger.log(LogStatus.INFO, "Click on Login button");
		clickElement("id", id_FbLoginBtn, "Login Button");
		Thread.sleep(2000); 
		logger.log(LogStatus.INFO, "Verify user is logged in successfully");
		String verifyText = getText("xpath", xpth_FbHome, "Home");
		if(verifyText!=null && verifyText.contains("Home")){
			logger.log(LogStatus.PASS, "Verify user is logged in sucessfully", "User is successfully logged in");
		}else{
			logger.log(LogStatus.FAIL, "User is not successfully logged in", logger.addScreenCapture(CommonMethods.takeScreenShot("testFbLogin")));
			
		}
	}
	
	@SuppressWarnings("unused")
	@Test(dataProvider="testData")
	public void testFBLoginInValidData(Map<String,String> data) throws InterruptedException{
		System.out.println(data);
		TestCaseDetails tcDetails = GlobalVariables.tcConfig.get(Thread.currentThread().getName());
		initialseBrowser(data.get("Browser"));
		ExtentTest logger = tcDetails.getLogger();
		logger.log(LogStatus.INFO, "Launch the application");
		launchApp("https://www.facebook.com/");
		logger.log(LogStatus.INFO, "Enter the Email or Phone number to log into fb");
		enterText("id", id_FbEmail, data.get("UserName"), "Email/Phone Number");
		logger.log(LogStatus.INFO, "Enter the passowrd to log into fb");
		enterText("id", id_FbPwd, data.get("Password"), "Password");
		logger.log(LogStatus.INFO, "Click on Login button");
		clickElement("id", id_FbLoginBtn, "Login Button");
		logger.log(LogStatus.INFO, "Verify user is logged in successfully");
		Thread.sleep(2000);
		String verifyText = getText("xpath", xpth_LoginError, "Login Error Message");
		if(verifyText!=null && verifyText.contains("The password that you've entered is incorrect")){
			logger.log(LogStatus.PASS, "Verify fb login with invalid credentials", "Error message is displayed");
		}else{
			logger.log(LogStatus.FAIL, "Verify fb login with invalid credentials", "Error message is not displayed");
		}
	}
	
	
}
