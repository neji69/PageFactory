package com.github.neji69;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class TwoFactAuth {

    @FindBy(id = "otp-code")
    private WebElement smsCodeLocator;

    @FindBy(id = "login-otp-button")
    private WebElement smsButtonLocator;

    public TwoFactAuth(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    TwoFactAuth inputSmsCode(String smsCode) {
        smsCodeLocator.clear();
        smsCodeLocator.sendKeys(smsCode);
        return this;
    }

    Home clickButtonSmsAuthorization(WebDriver driver) {
        smsButtonLocator.click();
        return new Home(driver);
    }

//    public Home smsAuth(String smsCode, WebDriver driver) {
//        inputSmsCode(smsCode);
//        clickButtonSmsAuthorization();
//        return new Home(driver);
//    }
}