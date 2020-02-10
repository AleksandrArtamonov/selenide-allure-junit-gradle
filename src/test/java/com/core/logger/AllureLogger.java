package com.core.logger;

import io.qameta.allure.Step;

public class AllureLogger {

    @Step("{0}")
    public static void logToAllure(String log) {}
}
