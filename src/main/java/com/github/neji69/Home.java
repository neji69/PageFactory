package com.github.neji69;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Home {

    @FindBy(id = "bank-overview")
    public WebElement buttonOverview;

    public Home(WebDriver driver) {
        PageFactory.initElements( driver, this);
    }
    public OverviewPage clickButtonOverview(WebDriver driver) {
        buttonOverview.click();
        return new OverviewPage(driver);
    }
}

