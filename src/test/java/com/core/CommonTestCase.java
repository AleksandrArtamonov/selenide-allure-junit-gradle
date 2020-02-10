package com.core;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.tinylog.Logger;

import static com.codeborne.selenide.Selenide.close;

public class CommonTestCase {

    private final static TestsConfiguration configuration = new TestsConfiguration() {};

    @BeforeAll
    private static void setConfiguration() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
//        If you work with selenoid
//        DesiredCapabilities capabilities = new DesiredCapabilities();
//        capabilities.setCapability("enableVNC", true);
//        Configuration.browserCapabilities = capabilities;
//        Configuration.browser = configuration.getBrowser() == null ? "chrome" : configuration.getBrowser();
//        Configuration.headless = configuration.isHeadless() == null  && Boolean.getBoolean(configuration.isHeadless());
//        Configuration.remote = configuration.getHubUrl() == null ? "http://localhost:4444/wd/hub" : configuration.getHubUrl();

        Configuration.baseUrl = "https://idemo.bspb.ru";
        Configuration.browser = "chrome";
    }

    @AfterEach
    public void closeBrowser() {
        Logger.info("Close browser.");
        close();
    }

    @AfterAll
    private static void tearDown() {
        SelenideLogger.removeListener("AllureSelenide");
    }

}
