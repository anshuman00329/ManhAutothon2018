package pageobjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class IMDBPageObject {

    @FindBy(xpath = "//h4[text()=\"Director:\"]/following-sibling::a | //h3[text()='Director:']/following-sibling::span")
    WebElement directorNameOnIMDB;

    public String getDirectorNameOnIMDB(){
        return  directorNameOnIMDB.getText();
    }

}
