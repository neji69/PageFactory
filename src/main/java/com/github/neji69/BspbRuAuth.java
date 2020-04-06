package com.github.neji69;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class BspbRuAuth {

    @FindBy(name = "username")
    public WebElement usernameLocator;

    @FindBy(name = "password")
    public WebElement passwordLocator;

    @FindBy(css = ".btn-primary")
    public WebElement loginButtonLocator;

    public BspbRuAuth(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    BspbRuAuth inputLogin(String login) {
        usernameLocator.clear();
        usernameLocator.sendKeys(login);
        return this;
    }

    BspbRuAuth inputPassword(String password) {
        passwordLocator.clear();
        passwordLocator.sendKeys(password);
        return this;
    }

    TwoFactAuth clickButtonAuthorization(WebDriver driver) {
        loginButtonLocator.click();
        return new TwoFactAuth(driver);
    }

//    public TwoFactAuth authorize(String login, String password, WebDriver driver) {
//        inputLogin(login);
//        inputPassword(password);
//        clickButtonAuthorization(driver);
//        return new TwoFactAuth(driver);
//    }
}
