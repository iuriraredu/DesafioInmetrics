package br.com.inmetrics.iuriraredu.web.actions;

import br.com.inmetrics.iuriraredu.settings.BaseTest;
import br.com.inmetrics.iuriraredu.web.pages.HomePage;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import static br.com.inmetrics.iuriraredu.utils.SeleniumUtils.jsClick;
import static br.com.inmetrics.iuriraredu.utils.SeleniumUtils.waitForClickable;

public class HomeActions extends BaseTest {

    private final HomePage homePage;

    public HomeActions() {
        this.homePage = new HomePage(getDriver());
    }

    public void clickOnSearchBtn() {
        WebElement searchBtn = waitForClickable(getWait(), this.homePage.getSearchBtn());
        searchBtn.click();
    }

    public void sendTextOnSearchInput(String text) {
        WebElement searchInput = waitForClickable(getWait(), this.homePage.getSearchInput());
        searchInput.sendKeys(text);
        searchInput.sendKeys(Keys.ENTER);
    }

    public String getTextLaptop() {
        WebElement laptopLink = waitForClickable(getWait(), this.homePage.getLaptopLink());
        return laptopLink.getText();
    }

    public void closeSearch() {
        WebElement buttonCloseSearch = waitForClickable(getWait(), this.homePage.getCloseSearchBtn());
        jsClick(getDriver(), buttonCloseSearch);
    }

    public void clickOnProductByName(String productName) {
        WebElement product = waitForClickable(getWait(),  this.homePage.getProductByName(productName, getDriver()));
        product.click();
    }
}