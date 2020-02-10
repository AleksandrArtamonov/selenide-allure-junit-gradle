package com.core.listeners;

import io.qameta.allure.Attachment;
import io.qameta.allure.listener.TestLifecycleListener;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.TestResult;

public class AllureLogListener implements TestLifecycleListener {

    @Attachment(value = "Test Log", type = "text/plain")
    public String stopCatch() {
        String result = MultithreadedConsoleOutputCatcher.getContent();
        MultithreadedConsoleOutputCatcher.stopCatch();
        return result;
    }

    @Override
    public void beforeTestStop(TestResult result) {
        if (result.getStatus() != Status.PASSED)
            stopCatch();
        else
            MultithreadedConsoleOutputCatcher.stopCatch();
    }

    @Override
    public void afterTestStart(TestResult result) {
        MultithreadedConsoleOutputCatcher.startCatch();
    }
}
