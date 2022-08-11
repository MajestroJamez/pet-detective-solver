package com.johny.solver.ui;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.*;

import java.util.function.Supplier;

public class FieldGenerator {
    TextField[][] textMatrix;
    Group[][] pathMatrix;

    public UiContext generateField(Integer maxX, Integer maxY) {
        textMatrix = new TextField[maxX][maxY];
        pathMatrix = new Group[2 * maxX][2 * maxY];


        VBox rows = new VBox();
        rows.setSpacing(10);

        for (int actualY = 0; actualY < maxY; actualY++) {
            rows.getChildren().add(createHorizontalNodesAndLines(maxX, actualY));

            if (actualY < maxY - 1) {
                rows.getChildren().add(createVerticalLines(maxX, actualY));
            }
        }

        UiContext response = new UiContext();
        response.setGraphicOut(rows);
        response.setTextMatrix(textMatrix);
        response.setPathMatrix(pathMatrix);
        return response;
    }

    private HBox createVerticalLines(Integer maxX, Integer actualY) {
        HBox columns;
        columns = new HBox();
        columns.setAlignment(Pos.CENTER);
        columns.setSpacing(80);
        for (int actualX = 0; actualX < maxX; actualX++) {
            Group line = vertLine();
            pathMatrix[2 * actualX][2 * actualY + 1] = line;
            columns.getChildren().add(line);
        }
        return columns;
    }

    private HBox createHorizontalNodesAndLines(Integer maxX, Integer actualY) {
        HBox columns = new HBox();
        columns.setAlignment(Pos.CENTER);
        columns.setSpacing(10);
        for (int actualX = 0; actualX < maxX; actualX++) {
            TextField node = new TextField();
            textMatrix[actualX][actualY] = node;
            node.setMaxWidth(40);

            columns.getChildren().add(node);
            if (actualX < maxX - 1) {
                Group line = horizontLine();
                pathMatrix[2 * actualX + 1][2 * actualY] = line;
                columns.getChildren().add(line);
            }
        }
        return columns;
    }

    private Group horizontLine() {
        //Setting the properties of the path element horizontal line
        HLineTo hLineTo = new HLineTo();
        hLineTo.setX(20.0);
        return line(() -> hLineTo);
    }

    private Group vertLine() {
        //Setting the properties of the path element vertical line
        VLineTo vLineTo = new VLineTo();
        vLineTo.setY(20.0);
        return line(() -> vLineTo);
    }

    private Group line(Supplier<PathElement> lineVector) {
        //Creating an object of the Path class
        Path path = new Path();

        //Moving to the starting point
        MoveTo moveTo = new MoveTo();
        moveTo.setX(0.0);
        moveTo.setY(0.0);

        //Adding the path elements to Observable list of the Path class
        path.getElements().add(moveTo);
        path.getElements().add(lineVector.get());

        path.setStrokeWidth(10);
        //Creating a Group object
        Group group = new Group(path);
        group.setOnMouseClicked(mouseEvent -> {
            if (group.getOpacity() == 1) {
                group.setOpacity(0.2);
            } else {
                group.setOpacity(1);
            }
        });
        return group;
    }


}
