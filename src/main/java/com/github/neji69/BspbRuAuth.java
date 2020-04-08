package com.github.neji69;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class BspbRuAuth {

    @FindBy(name = "username")
    private WebElement usernameElement;

    @FindBy(name = "password")
    private WebElement passwordElement;

    @FindBy(css = ".btn-primary")
    private WebElement loginButtonElement;

    public BspbRuAuth(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    BspbRuAuth inputLogin(String login) {
        usernameElement.clear();
        usernameElement.sendKeys(login);
        return this;
    }

    BspbRuAuth inputPassword(String password) {
        passwordElement.clear();
        passwordElement.sendKeys(password);
        return this;
    }

    TwoFactAuth clickButtonAuthorization(WebDriver driver) {
        loginButtonElement.click();
        return new TwoFactAuth(driver);
    }

//    public TwoFactAuth authorize(String login, String password, WebDriver driver) {
//        inputLogin(login);
//        inputPassword(password);
//        clickButtonAuthorization(driver);
//        return new TwoFactAuth(driver);
//    }
}
