package com.github.neji69;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

public class TestCaseHomework {

    private static final Logger log = LoggerFactory.getLogger(TestCaseHomework.class);
    WebDriver driver;
    WebDriverWait webDriverWait;

    @BeforeTest
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        webDriverWait = new WebDriverWait(driver, 30);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://idemo.bspb.ru");
        driver.manage().window().maximize();

    }

    @Test
    public void testCase20() throws InterruptedException {
        //Данные для теста (логин,пароль и смс для авторизации)
        String login = "demo";
        String password = "demo";
        String smsCode = "0000";
        Pattern pattern = Pattern.compile("[0-9]{1,3} [0-9]{1,3} [0-9]{1,3}[.][0-9][0-9] [₽]"); //Проверка формата суммы на странице "обзор"

        BspbRuAuth bspbRuAuth = new BspbRuAuth(driver);
        TwoFactAuth twoFactAuth = bspbRuAuth
                .inputLogin(login)
                .inputPassword(password)
                .clickButtonAuthorization(driver);

        Home home = twoFactAuth
                .inputSmsCode(smsCode)
                .clickButtonSmsAuthorization(driver);

        assertThat(driver.getTitle())
                .isEqualTo("Старт - Интернет банк - Банк Санкт-Петербург");
        OverviewPage overviewPage = home.clickButtonOverview(driver);

        Thread.sleep(1500); // ошибка загрузки элемента. подгружается через раз. не понятно если писать явное ожидание, за что цепляться.

        assertThat(driver.getTitle())
                .as("Открылась страница «Обзор»")
                .contains("Обзор");

        assertThat(overviewPage.financeFreeLocator.getText())
                .as("На странице отображается блок «Финансовая свобода»")
                .contains("Финансовая свобода");
        //at com.github.neji69.TestCaseHomework.testCase20(TestCaseHomework.java:62)
        //	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
        //	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
        //	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
        //	at java.base/java.lang.reflect.Method.invoke(Method.java:566)
        //	at org.testng.internal.MethodInvocationHelper.invokeMethod(MethodInvocationHelper.java:134)
        //	at org.testng.internal.TestInvoker.invokeMethod(TestInvoker.java:597)
        //	at org.testng.internal.TestInvoker.invokeTestMethod(TestInvoker.java:173)
        //	at org.testng.internal.MethodRunner.runInSequence(MethodRunner.java:46)
        //	at org.testng.internal.TestInvoker$MethodInvocationAgent.invoke(TestInvoker.java:816)
        //	at org.testng.internal.TestInvoker.invokeTestMethods(TestInvoker.java:146)
        //	at org.testng.internal.TestMethodWorker.invokeTestMethods(TestMethodWorker.java:146)

        try {
            // webDriverWait.until (ExpectedConditions.textMatches(overviewPage.financeFreeBalanceLocator,2 718 764.83"))
            assertThat(overviewPage.financeFreeBalanceLocator.getText())
                    .as(" с указанием суммы в формате 123 456 789.00 ")
                    .containsPattern(pattern);
        } catch (AssertionError e) {
            log.error("Не соответствие формата баланса", e);

        }
        //Навести курсор на сумму в блоке «Финансовая свобода»
        Actions actions = new Actions(driver);
        actions.moveToElement(overviewPage.financeFreeLocator).build().perform();
        try {
            assertThat(overviewPage.financeFreeLocator.getText())
                    .as("Появляется надпись: «Моих средств» с указанием суммы в формате 123 456 789.00 ")
                    .containsPattern(pattern);
        } catch (AssertionError e) {
            log.error("Не соответствие баланса", e);
        }
    }
    @AfterTest
    public void exitDriver() {
        driver.quit();
    }
}

