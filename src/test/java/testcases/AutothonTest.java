package testcases;

import commonutils.DriverUtil;
import commonutils.GenericUtil;
import commonutils.LoadDriverManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageobjects.GooglePageObject;
import pageobjects.IMDBPageObject;
import pageobjects.WikipediaPageObject;
import testdata.Constants;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class AutothonTest extends GenericUtil {

//    static Logger logger = Logger.getLogger(TrivagoBookingTest.class);
//    RemoteWebDriver driver;
//    DriverUtil driverUtil;
//    GooglePageObject googlePageObject;
//    WikipediaPageObject wikipediaPageObject;
//    IMDBPageObject imdbPageObject;
//    Map<Object,Object> dataMap = null;
//    String executionEnv, browser;
//    String directorNameOnWiki, directorNameOnIMDB, movieName;
//
//    @DataProvider(name="movieData", parallel =true)
//    public Object[][] getMovieNames()throws Exception{
//        File file = new File("C:\\Users\\vasingh\\IdeaProjects\\autothon2018\\src\\test\\java\\testdata\\Movies.xlsx");
//        FileInputStream fis = new FileInputStream(file);
//
//        XSSFWorkbook wb = new XSSFWorkbook(fis);
//        XSSFSheet sheet = wb.getSheetAt(0);
//        wb.close();
//        int lastRowNum = sheet.getLastRowNum() ;
//        int lastCellNum = sheet.getRow(0).getLastCellNum();
//        Object[][] obj = new Object[lastRowNum][1];
//
//        for (int i = 0; i < lastRowNum; i++) {
//            Map<Object, Object> datamap = new HashMap<Object, Object>();
//            for (int j = 0; j < lastCellNum; j++) {
//                datamap.put(sheet.getRow(0).getCell(j).toString(), sheet.getRow(i+1).getCell(j).toString());
//            }
//            obj[i][0] = datamap;
//
//        }
//        return  obj;
//
//    }
//
//
//
//    /*@Factory(dataProvider = "MovieData")
//    public static void dataProvider(Map tempData) {
//        tempData = this.dataMap;
//    }*/
//
///*    @DataProvider
//    public static Object[] dp1() {
//        return new Object[] {
//                new Object[]{"The Shawshank Redemption"},
//                new Object[] {"The Godfather" }*//*,
//                new Object[] { "The Dark Knight"},
//                new Object[] { "Pulp Fiction" },
//                new Object[] { "Schindler's List" },
//                new Object[] { "The Lord of the Rings: The Return of the King" },
//                new Object[] { "The Good,The Bad, The Ugly" },
//                new Object[] { "12 Angry Men" },
//                new Object[] { "Inception" },
//                new Object[] { "Forest Gump" },
//                new Object[] { "Fight Club" },
//                new Object[] { "Star Wars:Episode V - The Empire Strikes Back" },
//                new Object[] { "Goodfellas" },
//                new Object[] { "The Matrix" },
//                new Object[] { "One Flew Over The Cuckoo's Nest" },
//                new Object[] { "Seven Samurai" },
//                new Object[] { "Avengers:Infinity Wars" },
//                new Object[] { "Interstellar" },
//                new Object[] { "Se7en" },
//                new Object[] { "Non-Existing" }*//*
//        };
//    }
//
//    @Factory(dataProvider = "dp1")
//    public Object[] createInstances(){
//        return new Object[] {new AutothonTest("The Shwa")};
//    }*/
//
//    /*public AutothonTest(String movieName){
//        this.movieName = movieName;
//    }*/
//
//    /*@BeforeTest
//    @Parameters({"executionEnv","browser"})
//    public void preConfig(String executionEnv, String browser) throws Exception
//    {
//        String log4jConfigFile = System.getProperty("user.dir")
//                + File.separator + "log4j.properties";
//        PropertyConfigurator.configure(log4jConfigFile);
//        logger.info("Preconfig");
//        this.executionEnv =executionEnv;
//        this.browser =browser;
//        driverUtil = new DriverUtil();
//        driver = driverUtil.launchDriver(executionEnv, browser);
//        LoadDriverManager.setWebdriver(driver);
//        LoadDriverManager.getWebdriver().get(Constants.testUrl);
//        waitForPageToLoad();
//        googlePageObject = PageFactory.initElements(LoadDriverManager.getWebdriver(),GooglePageObject.class);
//        wikipediaPageObject = PageFactory.initElements(LoadDriverManager.getWebdriver(),WikipediaPageObject.class);
//        imdbPageObject = PageFactory.initElements(LoadDriverManager.getWebdriver(), IMDBPageObject.class);
//    }*/
//
//    @Test(description = "director name validation", dataProvider = "movieData")
//    public void test(Map<Object, Object> map) throws Exception {
//
//        String log4jConfigFile = System.getProperty("user.dir")
//                + File.separator + "log4j.properties";
//        PropertyConfigurator.configure(log4jConfigFile);
//        logger.info("Preconfig");
//        driverUtil = new DriverUtil();
//        driver = driverUtil.launchDriver("local", "mobileEnv");
//
//        driverUtil.driver.get(Constants.testUrl);
//        driverUtil.driver.manage().timeouts().implicitlyWait(3000, TimeUnit.SECONDS);
////        LoadDriverManager.setWebdriver(driver);
//        googlePageObject = PageFactory.initElements(LoadDriverManager.getWebdriver(),GooglePageObject.class);
//        wikipediaPageObject = PageFactory.initElements(LoadDriverManager.getWebdriver(),WikipediaPageObject.class);
//        imdbPageObject = PageFactory.initElements(LoadDriverManager.getWebdriver(), IMDBPageObject.class);
//
//        System.out.println(map.get("MOVIENAME"));
//        googlePageObject.searchMovie(map.get("MOVIENAME").toString());
//        googlePageObject.openMovieWikiPage("The_Shawshank_Redemption");
//        directorNameOnWiki = wikipediaPageObject.getDirectorNameOnWiki();
//        wikipediaPageObject.navigateToIMDB();
//        directorNameOnIMDB = imdbPageObject.getDirectorNameOnIMDB();
//        assert directorNameOnWiki.equalsIgnoreCase(directorNameOnIMDB): "Director name is not same";
//
//        driverUtil.driver.quit();
//    }
//
//    /*@AfterMethod
//    public void close(){
//        driverUtil.driver.quit();
//    }*/
}
