package br.com.inmetrics.iuriraredu.web.pages;

import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class ProductPage {

    public ProductPage(WebDriver driver){
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//button[@name='save_to_cart']")
    private WebElement addToCartBtn;

    @FindBy(xpath = "//h3[@class='ng-binding']")
    private WebElement productName;

    public WebElement getColorBtn(String colorName, WebDriver driver) {
        return driver.findElement(By.xpath(
                String.format("//span[@title='%s']", colorName.toUpperCase())
        ));
    }

    @FindBy(xpath = "//a[@id='shoppingCartLink']")
    private WebElement cartBtn;
}