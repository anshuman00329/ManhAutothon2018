package pageobjects;

import commonutils.GenericUtil;
import commonutils.LoadDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class GooglePageObject extends GenericUtil {

    @FindBy(xpath="//input[@id='lst-ib'] | //input[@type='search']")
    WebElement searchTextBox;

    public void searchMovie(String movieName) throws InterruptedException {
        searchTextBox.sendKeys(movieName,Keys.ENTER);
    }

    public void openMovieWikiPage(String movieName){
        LoadDriverManager.getWebdriver().findElement(By.xpath("//a[@href='https://en.wikipedia.org/wiki/"+movieName.replace(" ","_")+"'] | //div[text()='"+movieName+" - Wikipedia']")).click();
    }

}
