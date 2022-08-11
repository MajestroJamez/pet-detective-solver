package com.johny.solver;

import com.johny.solver.SolverApplication.StageReadyEvent;
import com.johny.solver.controller.MainController;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class StageInitializer implements ApplicationListener<StageReadyEvent> {
    private final String applicationTitle;
    private final FxWeaver fxWeaver;

    public StageInitializer(@Value("${spring.application.ui.title}") String applicationTitle,
                            FxWeaver fxWeaver) {
        this.applicationTitle = applicationTitle;
        this.fxWeaver = fxWeaver;
    }

    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        Stage stage = event.getStage();
        stage.setScene(new Scene(fxWeaver.loadView(MainController.class), 800, 600));
        stage.setTitle(applicationTitle);
        stage.show();
    }
}
