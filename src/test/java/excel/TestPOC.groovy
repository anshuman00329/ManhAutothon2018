package excel

import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.json.JSONObject
import org.json.simple.parser.JSONParser

public class TestPOC {

    public static void main(String[] args)
    {
        // You can specify your excel file path.
        String excelFilePath = "C:\\Users\\vasingh\\Documents\\DCO.xlsx";
        String className = "";
        TestPOC ob = new TestPOC();
        className = ob.getClassName();

        // This method will read each sheet data from above excel file and create a JSON and a text file to save the sheet data.
        creteJSONAndTextFileFromExcel(excelFilePath, className);
        System.out.print("test")


    }

    String getClassName()
    {
        return this.getClass().getSimpleName();
    }


    private static void creteJSONAndTextFileFromExcel(String filePath, String className)
    {
        try{

            System.out.println ("className----->"+className);
            //System.out.println ("className.getClass----->"+this.getClass().getSimpleName().toString());
            FileInputStream fInputStream = new FileInputStream(filePath.trim());

            Workbook excelWookBook = new XSSFWorkbook(fInputStream);

            int totalSheetNumber = excelWookBook.getNumberOfSheets();
            List<String> listOfSheets = new ArrayList<String>();
            String summarySheet = "Summary";
            Sheet sheet = excelWookBook.getSheet("Summary")
            List<String> summSheetDataTable = getSheetList(sheet, className);
            //listOfSheets = getListOfSheets(filePath);


            for(int i=0;i<totalSheetNumber-1;i++)
            {
                String sheetName = summSheetDataTable.get(i);
                sheet = excelWookBook.getSheet(sheetName);
                //sheet.getSheetName();
                System.out.println ("sheetName-----"+sheetName);
                if(sheetName != null && sheetName.length() > 0)
                {

                    List<List<String>> sheetDataTable = getSheetDataList(sheet, className);
                    System.out.println ("ListSheetData"+sheetDataTable);
                    String jsonString = getJSONStringFromList(sheetDataTable);
                    String jsonFileName = sheet.getSheetName() + ".json";
                    writeStringToFile(jsonString, jsonFileName);


                }
            }

        }catch(Exception ex){
            System.err.println(ex.getMessage());
        }
    }

    List<String> getListOfSheets()
    {
        List<String> rowDataList = new ArrayList<String>();

        return rowDataList;
    }

    private static List<String> getSheetList(Sheet sheet, String className)
    {
        List<String> sheetNames = new ArrayList<String>();
        System.out.println ("className----->"+className);

        int firstRowNum = sheet.getFirstRowNum();
        int lastRowNum = sheet.getLastRowNum();
        if(lastRowNum > 0)
        {
            for(int i=firstRowNum; i<lastRowNum + 1; i++)
            {
                Row row = sheet.getRow(i);

                Cell cell = row.getCell(0);

                int cellType = cell.getCellType();
                System.out.println ("cellType-----"+cellType);
                if(cellType == CellType.NUMERIC.getCode())
                {
                    double numberValue = cell.getNumericCellValue();

                    String stringCellValue = BigDecimal.valueOf(numberValue).toPlainString();
                    System.out.println ("stringCellValue"+stringCellValue);
                    sheetNames.add(stringCellValue);

                }else if(cellType == CellType.STRING.getCode())
                {
                    String cellValue = cell.getStringCellValue();
                    System.out.println ("cellValue"+cellValue);
                    sheetNames.add(cellValue);
                }else if(cellType == CellType.BOOLEAN.getCode())
                {
                    boolean numberValue = cell.getBooleanCellValue();

                    String stringCellValue = String.valueOf(numberValue);

                    sheetNames.add(stringCellValue);

                }else if(cellType == CellType.BLANK.getCode())
                {
                    sheetNames.add("");
                }
                //sheetNames.add(this)
            }
        }
        System.out.println("sheetNames---"+sheetNames.toString())
        return sheetNames;

    }

    private static List<List<String>> getSheetDataList(Sheet sheet, String className)
    {
        List<List<String>> ret = new ArrayList<List<String>>();
        System.out.println ("className----->"+className);

        int firstRowNum = sheet.getFirstRowNum();
        int lastRowNum = sheet.getLastRowNum();

        if(lastRowNum > 0)
        {

            for(int i=firstRowNum; i<lastRowNum + 1; i++)
            {

                Row row = sheet.getRow(i);


                int firstCellNum = row.getFirstCellNum();
                int lastCellNum = row.getLastCellNum();


                List<String> rowDataList = new ArrayList<String>();


                for(int j = firstCellNum; j < lastCellNum; j++)
                {
                    System.out.println ("firstCellNum"+j.toString());
                    Cell cell = row.getCell(j);


                    int cellType = cell.getCellType();

                    if(cellType == CellType.NUMERIC.getCode())
                    {
                        double numberValue = cell.getNumericCellValue();

                        String stringCellValue = BigDecimal.valueOf(numberValue).toPlainString();
                        System.out.println ("stringCellValue"+stringCellValue);
                        rowDataList.add(stringCellValue);

                    }else if(cellType == CellType.STRING.getCode())
                    {
                        String cellValue = cell.getStringCellValue();
                        System.out.println ("cellValue"+cellValue);
                        rowDataList.add(cellValue);
                    }else if(cellType == CellType.BOOLEAN.getCode())
                    {
                        boolean numberValue = cell.getBooleanCellValue();

                        String stringCellValue = String.valueOf(numberValue);

                        rowDataList.add(stringCellValue);

                    }else if(cellType == CellType.BLANK.getCode())
                    {
                        rowDataList.add("");
                    }
                }

                ret.add(rowDataList);
            }
        }
        return ret;
    }


    private static String getJSONStringFromList(List<List<String>> dataTable)
    {
        String ret = "";

        if(dataTable != null)
        {
            int rowCount = dataTable.size();

            if(rowCount > 1)
            {

                JSONObject tableJsonObject = new JSONObject();

                List<String> headerRow = dataTable.get(0);

                int columnCount = headerRow.size();

                for(int i=0; i<rowCount; i++)
                {

                    List<String> dataRow = dataTable.get(i);

                    JSONObject rowJsonObject = new JSONObject();

                    for(int j=0;j<columnCount;j++)
                    {
                        String columnName = headerRow.get(j);
                        String columnValue = dataRow.get(j);
                        ArrayList<String> ListOfActualChildJson = new ArrayList<String>();
                        if(columnValue.contains("LINK"))
                        {
                            List<String> colValuesAfterSplit = columnValue.split(":");
                            List<String> childJsonsName = colValuesAfterSplit.get(1).split(",")

                            System.out.println("colValuesAfterSplit" +childJsonsName.get(0))
                            //System.out.println("colValuesAfterSplit" +childJsonsName.get(1))
                            System.out.println("LINK IS PRESENT" + columnValue)

                            for(int k=0;k<childJsonsName.size();k++)
                            {
                                String childJson = "";
                                childJson=MergeChildJson(childJsonsName.get(k)+".json");
                                System.out.println("childJson of child " + childJson.toString())
                                ListOfActualChildJson.add(childJson);
                            }
                            columnValue = ListOfActualChildJson.toString();
                            System.out.println("columnValue of child " + columnValue);
                            rowJsonObject.put(columnName, columnValue);
                        }
                        else
                        {
                            rowJsonObject.put(columnName, columnValue);

                        }
                    }

                    //tableJsonObject.put("Data", rowJsonObject);
                    ret = rowJsonObject.toString();
                    System.out.println (ret);
                }

                //ret = tableJsonObject.toString();



            }
        }
        return ret;
    }

    private static String MergeChildJson(String JsonName)
    {
        jdk.nashorn.internal.parser.JSONParser jsonParser = new JSONParser();

        File file = new File(JsonName);

        Object object = jsonParser.parse(new FileReader(file));
        JSONObject jsonObject =  (JSONObject) jsonParser.parse(object.toString());
        System.out.println("jsonObject---->"+ jsonObject.toString());
        System.out.println("jsonObject---->"+ jsonObject.get("ITEMID"));
        return jsonObject.toString();
    }


    private static void writeStringToFile(String data, String fileName)
    {
        try
        {

            String currentWorkingFolder = System.getProperty("user.dir");

            String filePathSeperator = System.getProperty("file.separator");

            String filePath = currentWorkingFolder + filePathSeperator + fileName;

            File file = new File(filePath);

            FileWriter fw = new FileWriter(file);

            BufferedWriter buffWriter = new BufferedWriter(fw);

            buffWriter.write(data);

            buffWriter.flush();

            buffWriter.close();

            System.out.println(filePath + " has been created.");

        }catch(IOException ex)
        {
            System.err.println(ex.getMessage());
        }
    }
}
