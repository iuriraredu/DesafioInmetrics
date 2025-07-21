package br.com.inmetrics.iuriraredu.web.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import lombok.Getter;

@Getter
public class HomePage {
    public HomePage(WebDriver driver){
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//a[@title='SEARCH']")
    private WebElement searchBtn;

    @FindBy(xpath = "//input[@id='autoComplete']")
    private WebElement searchInput;

    @FindBy(xpath = "//p[text()='HP PAVILION 15T TOUCH LAPTOP']")
    private WebElement laptopLink;

    @FindBy(xpath = "//div[@data-ng-click='closeSearchForce()']/img")
    private WebElement closeSearchBtn;

    public WebElement getProductByName(String productName, WebDriver driver) {
        String xpath = String.format("//p[text()='%s']", productName);
        return driver.findElement(By.xpath(xpath));
    }
}

