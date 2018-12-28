package pageobjects;

import commonutils.DriverUtil;
import commonutils.GenericUtil;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import javax.xml.bind.annotation.W3CDomHandler;

public class FabHotelPage {

    GenericUtil genericUtil = new GenericUtil();

    @FindBy(xpath="//button[text()='SELECT ROOM']")
    WebElement selectRoomButton;

    @FindBy(xpath="//span[text()='PROCEED TO PAY']")
    WebElement proceedToPayButton;

    public void selectRoom(){
        genericUtil.waitClick(selectRoomButton);
    }

    public void clickOnProceedToPayButton(){
        genericUtil.waitClick(proceedToPayButton);
    }
}
