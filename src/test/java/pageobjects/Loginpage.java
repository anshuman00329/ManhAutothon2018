package pageobjects;

import commonutils.GenericUtil;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Loginpage extends GenericUtil {
    @FindBy(name = "login")
    WebElement usernameTextField;

    @FindBy(id = "password")
    WebElement passwordTextField;

    @FindBy(name = "commit")
    WebElement signInButton;

    public void enterUserName(String userName){
        usernameTextField.sendKeys(userName);
    }

    public void enterPassword(String password){
        passwordTextField.sendKeys(password);
    }

    public void clickOnSignInButton(){
        signInButton.click();
    }
}
