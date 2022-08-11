package com.johny.solver.ui;

import javafx.scene.Group;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class UiContext {
    private VBox graphicOut;
    private TextField[] [] textMatrix;
    private Group[] [] pathMatrix;

    public VBox getGraphicOut() {
        return graphicOut;
    }

    public void setGraphicOut(VBox graphicOut) {
        this.graphicOut = graphicOut;
    }

    public TextField[][] getTextMatrix() {
        return textMatrix;
    }

    public void setTextMatrix(TextField[][] textMatrix) {
        this.textMatrix = textMatrix;
    }

    public Group[][] getPathMatrix() {
        return pathMatrix;
    }

    public void setPathMatrix(Group[][] pathMatrix) {
        this.pathMatrix = pathMatrix;
    }
}
