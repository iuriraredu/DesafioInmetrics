package br.com.inmetrics.iuriraredu.web.pages;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class ShoppingCartPage {

    public ShoppingCartPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//label[@class='roboto-regular productName ng-binding']")
    private WebElement productName;

//    @FindBy(xpath = "//button[parent::td[@colspan='5']]")
//    @FindBy(xpath = "//button[@id='checkOutPopUp' and parent::td]")
    @FindBy(xpath = "//button[@id='checkOutButton' and parent::td]")
    private WebElement checkoutButton;

}

