package commonutils;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.w3c.dom.Document;
import testdata.Constants;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class DriverUtil extends GenericUtil{
    public  RemoteWebDriver driver;
    public  DesiredCapabilities capabilities;

    public RemoteWebDriver launchDriver(String executionEnv, String browser) throws Exception {
        if(executionEnv.equals("local")) {
            if (browser.equalsIgnoreCase("firefox")) {
                System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + File.separator + "lib" + File.separator + "geckodriver.exe");
                driver = new FirefoxDriver();
                driver.manage().window().maximize();
            } else if (browser.equalsIgnoreCase("chrome")) {
                System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + File.separator + "lib" + File.separator + "chromedriver.exe");
                driver = new ChromeDriver();
                driver.manage().window().maximize();
            } else if (browser.equalsIgnoreCase("mobileEnv")) {
                DesiredCapabilities capabilities = DesiredCapabilities.android();

                // set the capability to execute test in chrome browser
                capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, BrowserType.CHROME);

                // set the capability to execute our test in Android Platform
                capabilities.setCapability(MobileCapabilityType.PLATFORM, Platform.ANDROID);

                // we need to define platform name
                capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");

                // Set the device name as well (you can give any name)
                capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "c9799743");

                // set the android version as well
                capabilities.setCapability(MobileCapabilityType.VERSION, "8.1.0");

                // set the Android app package
                capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.android.chrome");

                // set the android app activity
                capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, "com.google.android.apps.chrome.Main");

                // Create object of URL class and specify the appium server address
                URL url = new URL("http://0.0.0.0:4723/wd/hub");

                // Create object of  AndroidDriver class and pass the url and capability that we created
                driver = new AndroidDriver(url, capabilities);

                //Check whether the device is locked or not
                if (((AndroidDriver<WebElement>) driver).isDeviceLocked())
                    ((AndroidDriver<WebElement>) driver).unlockDevice();
                else
                    ((AndroidDriver<WebElement>) driver).lockDevice();
            }
            else if(browser.equalsIgnoreCase("http")){
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse("<Your xml doc uri>");
                XPathFactory xPathfactory = XPathFactory.newInstance();
                XPath xpath = xPathfactory.newXPath();
                XPathExpression expr = xpath.compile("//Type[@type_id=\"4218\"]");

            }
        }
        else if(executionEnv.equals("docker")){
            if (browser.equalsIgnoreCase("firefox")) {
                capabilities = new DesiredCapabilities().firefox();
                driver = new RemoteWebDriver(new URL(Constants.nodeURL), capabilities);
                driver.manage().window().maximize();
            }
            else if (browser.equalsIgnoreCase("chrome")) {
                capabilities = new DesiredCapabilities().chrome();
                driver = new RemoteWebDriver(new URL(Constants.nodeURL), capabilities);
                driver.manage().window().maximize();
            }
        }
        return driver;
    }

    public void setCapabilites(String browser) {
        if(browser.equalsIgnoreCase("firefox")){
            capabilities = new DesiredCapabilities().firefox();
            capabilities.setCapability("marionette", true);
        }
        else if(browser.equalsIgnoreCase("chrome")){
            capabilities = new DesiredCapabilities().chrome();
        }
    }
}
