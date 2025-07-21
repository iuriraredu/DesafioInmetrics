package br.com.inmetrics.iuriraredu.web.pages;

import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

@Getter
public class SearchResultPage {

    public SearchResultPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public WebElement getProductByName(String productName, WebDriver driver) {
        String xpath = String.format("//a[text()='%s']", productName);
        return driver.findElement(By.xpath(xpath));
    }
}

