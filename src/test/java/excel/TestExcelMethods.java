package com.Generic.ExcelUtility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class TestExcelMethods {
	
	static Workbook workbook;
	static String Workbookpath = "C:\\Users\\hsardana\\Workspace\\ExcelToJsonConverter\\TestData\\TestData.xlsx";
	
	

public static void main(String[] args) throws Exception {

		try {
			
			//fn_GetCellContentByRowName(String SheetName, String RowName, String ColName)
			String ParentName = fn_GetCellContentByRowName("ORDER", "TC_01", "FlowName");
			List<String> ChildName = fn_getDataByColumnName("ORDERLINE", "FlowName",ParentName);
			System.out.println(ParentName);
			System.out.println(ChildName);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

private static int getRowNum(Sheet sheet, String ColName){
		
		Row row = null;
		int row_num = 0; 
		int total_rows = sheet.getLastRowNum();
		boolean flag = false;
		for(int i = 0; i<=total_rows; i++){
			
			row = sheet.getRow(i);
			
			for( int j= 0; j<=row.getLastCellNum(); j++){
				
				if(row.getCell(j)!= null)
				{
					if(row.getCell(j).toString().trim().equalsIgnoreCase(ColName))
					{
					flag = true;
					row_num = i;
//					System.out.println("RowNumber" + row_num);
					break;
					}
				}
				
			}
			if(flag==true)
				break;
			
		}
		return row_num;
	}
	
public static String fn_GetCellContentByRowName(String SheetName, String RowName, String ColName) throws IOException{
		
		String CellContent = null;
		Sheet sheet = null;
		
		try {
			sheet = getSheet(Workbookpath, SheetName);
			//System.out.println("SheetName" + SheetName);
			Cell cell = sheet.getRow(getRowNum(sheet, RowName)).getCell(getColNum(sheet, ColName));
			if(cell == null || cell.getCellType() == cell.CELL_TYPE_BLANK){
				CellContent = "";
			}
				else{
					
					CellContent = cell.toString().trim();
					//System.out.println("CellContent:" + CellContent);
					}
		} catch (Exception e) {
			System.out.println("fn_GetCellContentByRowName_EXCEPTION");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return CellContent;
		
}


private static int getColNum(Sheet sheet, String ColName){
	Row row = null; 
	int total_rows = sheet.getLastRowNum();
	int colm_no = 16380;
	boolean flag = false;
	for(int i = 0; i<=total_rows; i++){
		
		row = sheet.getRow(i);
		
		for( int j= 0; j<=row.getLastCellNum(); j++){
			
			if(row.getCell(j)!= null)
			{
				if(row.getCell(j).toString().trim().equalsIgnoreCase(ColName))
				{
				flag = true;
				colm_no = j;
				
				break;
				}
			}
			
		}
		if(flag==true)
			break;
		
	}
	return colm_no;

}

private static Workbook getWorkBook(String Workbookpath) throws IOException{
		
		if(Workbookpath.contains(".xlsx")){
			workbook = new XSSFWorkbook(Workbookpath);
		}
		else{
			
			InputStream inp = new FileInputStream(Workbookpath);
			workbook = new HSSFWorkbook(new POIFSFileSystem(inp));
			
		}
		
		return workbook;
		
	}
	
private static Sheet getSheet(String Workbookpath, String SheetName) throws IOException{
		Sheet sheet = null;
		Workbook workbook =  getWorkBook(Workbookpath);
		sheet = workbook.getSheet(SheetName.trim());
		
		return sheet;
		
	}



//This Function will return the number of childs containing the parent entity

public static List<String> fn_getDataByColumnName(String SheetName, String ColName, String ParentName) throws IOException{
	
	Sheet sheet = null;
	List<String> childNames = new ArrayList<String>();
	
	sheet = getSheet(Workbookpath, SheetName);
	
	Row row = sheet.getRow(0);
	 
    int col_num = -1;

    for(int i=0; i < row.getLastCellNum(); i++)
    {	
        if(row.getCell(i).toString().trim().equalsIgnoreCase(ColName))
            col_num = i;
        
        row = sheet.getRow(i+1);
        Cell cell = (XSSFCell) row.getCell(col_num);

        String value = cell.getStringCellValue();
        childNames.add(value);
        System.out.println(childNames);
        
    }
    return childNames;
  
		
}


}
