package com.examples;

import com.codeborne.selenide.SelenideElement;
import com.core.CommonTestCase;
import com.core.logger.AllureLogger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.By;
import org.tinylog.Logger;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


public class InternetBankTest extends CommonTestCase {


    @BeforeEach
    private void login(TestInfo info) {
        Logger.info("Running test with class '{}' and method '{}'.", info.getTestClass(), info.getTestMethod());
        open("/");
        $(By.name("username")).val("demo");
        $(By.name("password")).val("demo").pressEnter();
        enter2ndFactor();
    }

    private void enter2ndFactor() {
        $("#otp-code-text").shouldBe(visible);
        if ($("#login-crypto-button").isDisplayed()) {
            $("#login-crypto-button").click();
        }
        else {
            $(By.name("otpCode")).val("0000").pressEnter();
        }
    }

    @Test
    public void userCanLoginToBspbDemo() {
        $("#user-greeting").shouldHave(text("Hello World!"));
    }

    @Test
    public void userCanLoadStatementForLastMonth() {
        open("/bank/overview");

        $("#accounts .account a").click();
        $(".page-header").shouldHave(text("Statement"));
        AllureLogger.logToAllure("Download Statement For Last Month");
        $("#defined-periods").find(byText("Beginning of last month until today")).click();
    }

    @Test
    public void userCanViewTransactionDetails() {
        openStatement();
        $(".statement-container .transaction-row.tx-debit .counterparty-name").scrollTo().click();
        checkTransactionDetails();
    }

    @Test
    public void userCanAssignAliasForAccount() {
        open("/bank/overview");
        SelenideElement account = $("#accounts .account", 2).scrollTo();

        account.find("a.alias")
                .shouldHave(or("account or alias", text("50817 810 0 4800 0104467"), text("Это типа счёт"))
                        .because("the alias could already be changed by previous test run"))
                .hover();

        account.find(".icon-edit").should(appear).click();
        account.find(By.name("alias")).val("Это типа счёт").pressEnter();

        account.shouldHave(text("Это типа счёт"));

        account.shouldNotHave(text("50817 810 0 4800 0104467"));
    }

    private void checkTransactionDetails() {
        $("#transaction-dialog #transaction-header").shouldHave(text("Transaction details"));
        $("#transaction-dialog #payment-beneficiary").shouldHave(text("Beneficiary"));
        $("#transaction-dialog #beneficiary-account").shouldHave(
                text("Beneficiary account"),
                text("***** *** * **** ***0000")
        );
    }

    private void openStatement() {
        open("/statement");
        $("#defined-periods").find(byText("Beginning of last month until today")).click();
    }
}
