package utilities;

import java.io.IOException;
import org.testng.annotations.DataProvider;

/**
 * DataProviders.java
 * Contains data providers for data-driven testing
 */
public class DataProviders {
    
    @DataProvider(name = "UserData")
    public String[][] getUserData() throws IOException {
        
        String path = System.getProperty("user.dir") + "/testData/UserTestData.xlsx";
        
        ExcelUtility xlutil = new ExcelUtility(path);
        
        int totalrows = xlutil.getRowCount("Sheet1");
        int totalcols = xlutil.getCellCount("Sheet1", 1);
        
        String apidata[][] = new String[totalrows][totalcols];
        
        for (int i = 1; i <= totalrows; i++) {
            for (int j = 0; j < totalcols; j++) {
                apidata[i - 1][j] = xlutil.getCellData("Sheet1", i, j);
            }
        }
        
        return apidata;
    }
}
