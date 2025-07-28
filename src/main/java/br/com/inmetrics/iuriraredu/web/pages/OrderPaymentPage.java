package br.com.inmetrics.iuriraredu.web.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class OrderPaymentPage {
    public OrderPaymentPage(WebDriver driver){
        PageFactory.initElements(driver, this);
    }

    public WebElement getProductByName(String productName, WebDriver driver) {
        String xpath = String.format("//h3[ancestor::div[@id='userCart'] and text()='%s']", productName);
        return driver.findElement(By.xpath(xpath));
    }
}

