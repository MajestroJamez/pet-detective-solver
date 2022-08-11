package com.johny.solver.core;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class UtilizationService extends Service<Void> {

    @Override
    protected Task<Void> createTask() {
        return new Task<>() {
            @Override
            protected Void call() throws Exception {
                while (true) {
                    updateMessage("Free: " + Runtime.getRuntime().freeMemory() + " ,total: " +
                            Runtime.getRuntime().totalMemory());
                    Thread.sleep(5000);
                }
            }
        };
    }
}
