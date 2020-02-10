package com.core;

public interface TestsConfiguration {

    default String getHubUrl() {
        return System.getProperty("selenide.remote");
    }

    default String isHeadless() {
        return System.getProperty("selenide.headless");
    }

    default String getBrowser() {
        return System.getProperty("selenide.browser");
    }
}
