package br.com.inmetrics.iuriraredu.web.actions;

import br.com.inmetrics.iuriraredu.settings.BaseTest;
import br.com.inmetrics.iuriraredu.utils.WaitUtils;
import br.com.inmetrics.iuriraredu.web.pages.HomePage;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomeActions extends BaseTest {

    private final HomePage homePage;

    public HomeActions(){
        this.homePage = new HomePage(getDriver());
    }

    public void clickOnSearchBtn (){
        WebElement searchBtn = WaitUtils.waitForClickable(getDriver(), this.homePage.getSearchBtn());
        searchBtn.click();
    }

    public void sendTextOnSearchInput (String text){
        WebElement searchInput = WaitUtils.waitForClickable(getDriver(), this.homePage.getSearchInput());
        searchInput.sendKeys(text);
        searchInput.sendKeys(Keys.ENTER);
    }

    public String getTextLaptop (){
        WebElement laptopText = WaitUtils.waitForClickable(getDriver(), this.homePage.getLaptopLink());
        return laptopText.getText();
    }

    public void closeSearch(){
        WebElement closeSearchBtn = WaitUtils.waitForClickable(getDriver(), this.homePage.getCloseSearchBtn());
        getWait().until(ExpectedConditions.elementToBeClickable(closeSearchBtn));
        ((JavascriptExecutor)getDriver()).executeScript("arguments[0].click();", closeSearchBtn);
    }

    public void clickOnProductByName(String productName){
        WebElement product = this.homePage.getProductByName(productName, getDriver());
        WaitUtils.waitForClickable(getDriver(), product).click();
    }
}