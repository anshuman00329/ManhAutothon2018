package commonutils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import testdata.Constants;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class GenericUtil {

    WebDriverWait wait;
    Select select;
    Actions actions;
    Set<String> windowIDs;
    String parentWindowID,childWindowID;
    static Properties configProp;

    public void waitForPageToLoad(){
        LoadDriverManager.getWebdriver().manage().timeouts().implicitlyWait(Constants.maxWait, TimeUnit.SECONDS);
    }

    public void waitForPageToLoad(int waitTime){
        LoadDriverManager.getWebdriver().manage().timeouts().implicitlyWait(waitTime, TimeUnit.SECONDS);
    }

    public void waitForElementAvailablility(WebElement element){
        wait = new WebDriverWait(LoadDriverManager.getWebdriver(),5);
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void select(WebElement element, String text){
        select = new Select(element);
        select.selectByVisibleText(text);
    }

    public void hoverOnElement(WebElement element){
        actions =new Actions(LoadDriverManager.getWebdriver());
        actions.moveToElement(element).perform();
    }

    public void sendMultipleKeys(){

    }

    public void switchToChildWindow(){
        windowIDs = LoadDriverManager.getWebdriver().getWindowHandles();
        Iterator<String> idIterator = windowIDs.iterator();
        parentWindowID = idIterator.next();
        childWindowID = idIterator.next();
        LoadDriverManager.getWebdriver().switchTo().window(childWindowID);
    }

    public void waitSendKeys(WebElement element, String inputText){
        waitForElementAvailablility(element);
        element.sendKeys(inputText);
    }

    public void waitClick(WebElement element){
        waitForElementAvailablility(element);
        element.click();
    }

    public void waitHover(WebElement element){
        waitForElementAvailablility(element);
        hoverOnElement(element);
    }

    public String fetchData(String inputData) {
        configProp = new Properties();
        try {
            configProp.load(new FileInputStream(new File(Constants.testDataPropertyPath)));

        }
        catch(IOException exception){
            exception.getStackTrace();
        }
        return configProp.getProperty(inputData);
    }

    public Map<String, String> getMoviesList() {

        Properties prop = new Properties();
        InputStream input = null;
        Map<String, String> mapVar = new TreeMap<String, String>();

        try {

            String filename = Constants.testDataPropertyPath;
            input = getClass().getClassLoader().getResourceAsStream(filename);
            if (input == null) {
                System.out.println("Sorry, unable to find " + filename);
            }

            prop.load(new FileInputStream(new File(filename)));

            Enumeration<?> e = prop.propertyNames();
            while (e.hasMoreElements()) {
                String key = (String) e.nextElement();
                String value = prop.getProperty(key);
                mapVar.put(key,value);
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return mapVar;
    }

    public static void screenCapture(WebDriver driver, String movieName) throws Exception {
        File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile,  new File(System.getProperty("user.dir")+"//ScreenShot/"+movieName+".jpeg"));      //your screenshot path and convert date string to SimpleDateFormat because windows can't capture screenshot with(:)
    }




}
