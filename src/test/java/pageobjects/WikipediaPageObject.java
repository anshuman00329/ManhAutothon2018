package pageobjects;

import commonutils.LoadDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class WikipediaPageObject {

    @FindBy(xpath="//tr/th[text()=\"Directed by\"]/../td/a")
    WebElement directorNameOnWiki;

    @FindBy(xpath="//a[@href=\"/wiki/IMDb\"]/preceding-sibling::a")
    WebElement imdbLink;

    public String getDirectorNameOnWiki(){
        return directorNameOnWiki.getText();
    }

    public void navigateToIMDB(){
        imdbLink.click();
    }
}
