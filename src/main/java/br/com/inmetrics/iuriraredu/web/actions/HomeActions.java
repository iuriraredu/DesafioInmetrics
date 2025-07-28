package br.com.inmetrics.iuriraredu.web.actions;

import static br.com.inmetrics.iuriraredu.utils.SeleniumUtils.implicitlyWait;
import static br.com.inmetrics.iuriraredu.utils.SeleniumUtils.jsClick;

import br.com.inmetrics.iuriraredu.settings.BaseTest;
import br.com.inmetrics.iuriraredu.web.pages.HomePage;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

public class HomeActions extends BaseTest {

    private final HomePage homePage;

    public HomeActions() {
        this.homePage = new HomePage(getDriver());
    }

    public void clickOnSearchBtn() {
        implicitlyWait(getDriver());
        this.homePage.getSearchBtn().click();
    }

    public void sendTextOnSearchInput(String text) {
        implicitlyWait(getDriver());
        WebElement searchInput = this.homePage.getSearchInput();
        searchInput.sendKeys(text);
        searchInput.sendKeys(Keys.ENTER);
    }

    public String getTextLaptop() {
        implicitlyWait(getDriver());
        return this.homePage.getLaptopLink().getText();
    }

    public void closeSearch() {
        implicitlyWait(getDriver());
        jsClick(getDriver(), this.homePage.getCloseSearchBtn());
    }

    public void clickOnProductByName(String productName) {
        implicitlyWait(getDriver());
        this.homePage.getProductByName(productName, getDriver()).click();
    }
}