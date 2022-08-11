package com.johny.solver.controller;

import com.johny.solver.core.DirectionNode;
import com.johny.solver.core.MainService;
import com.johny.solver.core.UtilizationService;
import com.johny.solver.ui.ContextUtil;
import com.johny.solver.ui.FieldGenerator;
import com.johny.solver.ui.UiContext;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@FxmlView("/fxml/main.fxml")
public class MainController {

    @FXML
    public Button createButton;

    @FXML
    public Button executeButton;

    @FXML
    public TextField x;

    @FXML
    public TextField y;

    @FXML
    public TextField moves;

    @FXML
    public TextField dfsSwitch;

    @FXML
    public VBox dataContainer;

    @FXML
    public Label buffer;

    @FXML
    public Label result;
    @FXML
    public Label utilization;

    private TextField[] [] textMatrix;
    private Group[] [] pathMatrix;

    @FXML
    private void initialize() {
        fillDataContainer(null);
        createButton.setOnAction(this::fillDataContainer);
        executeButton.setOnAction(this::createContextAndExecute);
        autoUpdateUtilization();
    }

    private void autoUpdateUtilization(){
        UtilizationService utilizationService = new UtilizationService();
        utilization.textProperty().bind(utilizationService.messageProperty());
        utilizationService.start();
    }

    private void createContextAndExecute(ActionEvent actionEvent) {
        String[][] nodeMap = ContextUtil.createNodesMap(textMatrix);
        ContextUtil.validateNodeMap(nodeMap);

        DirectionNode[][] directionMap = ContextUtil.createDirectionMap(pathMatrix);
        ContextUtil.printToConsoleNodeMap(nodeMap);
        ContextUtil.printToConsoleDirectionMap(directionMap);
        MainService mainService = new MainService(nodeMap, directionMap, Integer.valueOf(moves.getText()), Integer.valueOf(dfsSwitch.getText()));
        buffer.textProperty().bind(mainService.messageProperty());
        //TODO: result.textProperty().bind(Bindings.createStringBinding(mainExecutor.se));
        mainService.setOnSucceeded(evt -> result.setText(mainService.getValue()));
        mainService.setOnFailed(evt -> {
            System.err.println("The task failed with the following exception:");
            result.setText(mainService.getException().getMessage());
            mainService.getException().printStackTrace(System.err);
        });
        mainService.start();
    }

    private void fillDataContainer(ActionEvent event) {
        dataContainer.getChildren().clear();

        FieldGenerator fieldGenerator = new FieldGenerator();
        UiContext generatedOutput = fieldGenerator.generateField(Integer.valueOf(x.getText()), Integer.valueOf(y.getText()));

        dataContainer.getChildren().add(generatedOutput.getGraphicOut());
        textMatrix = generatedOutput.getTextMatrix();
        pathMatrix = generatedOutput.getPathMatrix();
    }
}
