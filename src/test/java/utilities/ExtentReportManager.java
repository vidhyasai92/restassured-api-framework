package utilities;

import java.text.SimpleDateFormat;
import java.util.Date;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

/**
 * ExtentReportManager.java
 * Manages ExtentReports for API test execution
 */
public class ExtentReportManager {
    
    public static ExtentReports extent;
    public static ExtentTest test;
    public static ExtentSparkReporter sparkReporter;
    
    public static ExtentReports setupExtentReport() {
        
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        String reportName = "API-Test-Report-" + timeStamp + ".html";
        
        sparkReporter = new ExtentSparkReporter("./reports/" + reportName);
        
        sparkReporter.config().setDocumentTitle("REST API Automation Report");
        sparkReporter.config().setReportName("API Test Execution Report");
        sparkReporter.config().setTheme(Theme.DARK);
        
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        
        extent.setSystemInfo("Application", "ReqRes API");
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("User", "Vidhyalakshmi");
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));
        
        return extent;
    }
    
    public static void logInfo(String message) {
        test.log(Status.INFO, message);
    }
    
    public static void logPass(String message) {
        test.log(Status.PASS, message);
    }
    
    public static void logFail(String message) {
        test.log(Status.FAIL, message);
    }
    
    public static void logSkip(String message) {
        test.log(Status.SKIP, message);
    }
}
