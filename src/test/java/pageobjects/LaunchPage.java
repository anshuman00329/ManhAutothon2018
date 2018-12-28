package pageobjects;

import commonutils.GenericUtil;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LaunchPage extends GenericUtil{

    @FindBy(xpath = "//a[text()='Sign in']")
    WebElement signInButton;

    public void clickOnSignInButton(){
        waitClick(signInButton);
    }

}
