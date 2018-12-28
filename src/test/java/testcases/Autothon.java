package testcases;

import commonutils.CustomReportUtil;
import commonutils.DriverUtil;
import commonutils.GenericUtil;
import commonutils.LoadDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import pageobjects.GooglePageObject;
import pageobjects.IMDBPageObject;
import pageobjects.WikipediaPageObject;

import java.io.File;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Autothon {

    private static final int CHROME_INSTANCES = 3;
    static Map<String, List<Object>> dataMapForReport = new HashMap<String, List<Object>>();
    static CustomReportUtil reportUtil = new CustomReportUtil();
    static GenericUtil gutil = new GenericUtil();

    @Test
    public void MovieSearch() throws Exception {

        System.out.println("Fixed thread pool initialisation....");
        ExecutorService executor = Executors.newFixedThreadPool(CHROME_INSTANCES);

        System.out.println("Just a host list for different different instances..");

        String url = "http://www.google.com";

       /* String[] movies = {"The Shawshank Redemption","The Godfather","The Dark Knight","Pulp Fiction","Schindler's List",
                "The Lord of the Rings: The Return of the King", "The Good,The Bad, The Ugly","12 Angry Men", "Inception",
                "Forest Gump","Fight Club","Star Wars:Episode V - The Empire Strikes Back", "Goodfellas","The Matrix"
                ,"One Flew Over The Cuckoo's Nest","Seven Samurai","Avengers:Infinity Wars","Interstellar","Se7en","Non-Existent"};
*/
       String[] movies = {"The Shawshank Redemption","The Matrix"};
        String browserName;
        for (int i = 0; i < movies.length; i++) {
//            if(i==movies.length-1){
//                browserName="mobileEnv";
//            }
//            else{
              browserName="chrome";
 //           }
            String movietobeSerarched = movies[i];
            Runnable worker = new ChromeInstanceThread(url,false,movietobeSerarched,i+1,browserName);
            executor.execute(worker);
        }
        executor.shutdown();

        // Wait until all threads are finish
        while (!executor.isTerminated()) {
            // This will be tereminated after the execution is completed
        }
        System.out.println("\nFinished  assigning all threads with search tags ...");
    }


    public static class ChromeInstanceThread implements Runnable {
        private final String url;
        boolean isLoginBased=false;
        String searchTag=null;
        int movieId;
        String browserName;

        ChromeInstanceThread(String url,boolean isLoginBased,String searchTag,int movieId, String browserName) {
            this.url = url;
            this.isLoginBased = isLoginBased;
            this.searchTag = searchTag;
            this.movieId = movieId;
            this.browserName = browserName;
        }

        @Override
        public void run() {
            WebDriver driver= null;
            try {
                System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "//lib//chromedriver.exe" );
//                GooglePageObject googlePageObject = PageFactory.initElements(LoadDriverManager.getWebdriver(), GooglePageObject.class);
//                WikipediaPageObject wikipediaPageObject = PageFactory.initElements(LoadDriverManager.getWebdriver(), WikipediaPageObject.class);
//                IMDBPageObject imdbPageObject = PageFactory.initElements(LoadDriverManager.getWebdriver(), IMDBPageObject.class);

                String wikiUrl, imdbUrl, actualDirectorNameOnWiki,actualDirectorNameOnIMDB, nameDifference;

                DriverUtil driverUtil = new DriverUtil();
                driver = driverUtil.launchDriver("local",browserName);
                driver.manage().timeouts().implicitlyWait(5000, TimeUnit.MILLISECONDS);  //Optional again
                driver.get(url);

                driver.manage().timeouts().implicitlyWait(5000, TimeUnit.MILLISECONDS);
                WebElement googleSearchTextBox = driver.findElement(By.xpath("//input[@id='lst-ib'] | //input[@type='search']" ));
                googleSearchTextBox.sendKeys(searchTag, Keys.ENTER); // send also a "\n"
                WebElement wikiLink = driver.findElement(By.xpath("//a[@href='https://en.wikipedia.org/wiki/"+searchTag.replace(" ","_")+"'] | //a[@href='https://en.wikipedia.org/wiki/"+searchTag+"'] | //div[text()='"+searchTag+" - Wikipedia']"));
                wikiLink.click();
                wikiUrl = driver.getCurrentUrl();
                gutil.screenCapture(driver,searchTag+" wiki");
                driver.manage().timeouts().implicitlyWait(5000, TimeUnit.MILLISECONDS);
                WebElement directorNameOnWiki = driver.findElement(By.xpath("//tr/th[text()='Directed by']/../td/a"));
                actualDirectorNameOnWiki = directorNameOnWiki.getText();
                WebElement imdbLink = driver.findElement(By.xpath("//a[@href=\"/wiki/IMDb\"]/preceding-sibling::a"));
                imdbLink.click();
                imdbUrl = driver.getCurrentUrl();
                gutil.screenCapture(driver,searchTag+" imdb");
                driver.manage().timeouts().implicitlyWait(5000, TimeUnit.MILLISECONDS);
                WebElement directorNameOnIMDB = driver.findElement(By.xpath("//h4[text()=\"Director:\"]/following-sibling::a | //h3[text()='Director:']/following-sibling::span"));
                actualDirectorNameOnIMDB = directorNameOnIMDB.getText();

                /*googlePageObject.searchMovie(searchTag);
                googlePageObject.openMovieWikiPage(searchTag);
                actualDirectorNameOnWiki = wikipediaPageObject.getDirectorNameOnWiki();
                wikipediaPageObject.navigateToIMDB();
                actualDirectorNameOnIMDB = imdbPageObject.getDirectorNameOnIMDB();*/

                assert actualDirectorNameOnWiki.equalsIgnoreCase(actualDirectorNameOnIMDB): "Director name is not same on wiki and imdb";

                //It takes time so..slow it down.
                Thread.sleep(5000);

                //ADD CODE TO CLICK ON WIKI LINK AND IMDB LINK HERE



                //Add the Movie specific details in this order to the list : Wikipedia URL, Snapshot of Wikipedia page, Wikipedia Director(s),
                //Imdb URL, Snapshot of Imdb page,Imdb Director(s)
                List<Object> movieDetails = new ArrayList<Object>();
                movieDetails.add(wikiUrl);
                movieDetails.add(actualDirectorNameOnWiki);
                movieDetails.add(imdbUrl);
                movieDetails.add(actualDirectorNameOnIMDB);
                movieDetails.add(movieId);
             //   movieDetails.add(nameDifference);

                //TO ADD THE VALUES IN THE DATA STRUCTURE FOR REPORTING PURPOSE
                dataMapForReport.put(searchTag, movieDetails);
                reportUtil.generateReport(dataMapForReport);
                driver.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(url + "\t\t this Chrome instance has been made up:");
        }
    }
}

