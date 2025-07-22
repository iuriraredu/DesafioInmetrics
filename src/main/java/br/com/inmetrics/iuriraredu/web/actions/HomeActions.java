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
        WaitUtils.implicitlyWait(getDriver());
        this.homePage.getSearchBtn().click();
    }

    public void sendTextOnSearchInput (String text){
        WaitUtils.implicitlyWait(getDriver());
        WebElement searchInput = this.homePage.getSearchInput();
        searchInput.sendKeys(text);
        searchInput.sendKeys(Keys.ENTER);
    }

    public String getTextLaptop (){
        WaitUtils.implicitlyWait(getDriver());
        return this.homePage.getLaptopLink().getText();
    }

    public void closeSearch(){
        WaitUtils.implicitlyWait(getDriver());
        ((JavascriptExecutor)getDriver()).executeScript(
                "arguments[0].click();", this.homePage.getCloseSearchBtn()
        );
    }

    public void clickOnProductByName(String productName){
        WaitUtils.implicitlyWait(getDriver());
        this.homePage.getProductByName(productName, getDriver()).click();
    }
}