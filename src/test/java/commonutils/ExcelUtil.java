package commonutils;

import org.apache.poi.ss.usermodel.*;
import org.testng.Reporter;

import java.io.File;
import java.io.FileInputStream;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

public class ExcelUtil {

    String inputFileName=System.getProperty("user.dir")+"src\\test\\java\\testdata\\InputData.xlsx";

    public String getInputData(String sheetName, String inputData){
        Cell value=null;
        Map<LinkedList<String>, String> expectedTagAndValue = new LinkedHashMap<LinkedList<String>, String>();
        int rowNum = 1;
        try{
            Workbook workBook = WorkbookFactory.create(new FileInputStream(new File(inputFileName)));
            Sheet sheet = workBook.getSheet(sheetName);
            for(Row row = sheet.getRow(rowNum); row != null; row = sheet.getRow(rowNum)){
                Cell tag = row.getCell(1);
                if(tag.toString().equals(inputData)){
                    value = row.getCell(2);
                }
                ++rowNum;
            }
        }
        catch(Exception e){
            Reporter.log("Error occusred while accessing input data");
        }

        return value.toString();
    }
}
