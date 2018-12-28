package pageobjects;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class TrivagoLaunchPage {

    @FindBy (xpath = "//input[@type='search']")
    WebElement searchTextBox;

    public void searchCity(String cityName){
        searchTextBox.sendKeys(cityName, Keys.ENTER);
        }
}
