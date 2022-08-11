package com.johny.solver.ui;

import com.johny.solver.core.DirectionNode;
import javafx.scene.Group;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

public class ContextUtil {

    public static String[][] createNodesMap(TextField[][] textMatrix) {
        String[][] returnMap = new String[textMatrix.length][textMatrix[0].length];
        for (int i = 0; i < textMatrix.length; i++) {
            for (int j = 0; j < textMatrix[0].length; j++) {
                returnMap[i][j] = textMatrix[i][j].getText();
            }
        }
        return returnMap;
    }

    public static DirectionNode[][] createDirectionMap(Group[][] pathMatrix) {
        Integer maxX = pathMatrix.length / 2;
        Integer maxY = pathMatrix[0].length / 2;
        return fillDirectionMap(maxX, maxY, pathMatrix);
    }

    private static DirectionNode[][] fillDirectionMap(Integer maxX, Integer maxY, Group[][] pathMatrix) {
        DirectionNode[][] returnMap = new DirectionNode[maxX][maxY];
        for (int i = 0; i < maxX; i++) {
            for (int j = 0; j < maxY; j++) {
                returnMap[i][j] = new DirectionNode();
                returnMap[i][j].setHasLeft(i != 0 && pathMatrix[2 * i - 1][2 * j].getOpacity() == 1);
                returnMap[i][j].setHasRight(i != maxX - 1 && pathMatrix[2 * i + 1][2 * j].getOpacity() == 1);
                returnMap[i][j].setHasUpper(j != 0 && pathMatrix[2 * i][2 * j - 1].getOpacity() == 1);
                returnMap[i][j].setHasLower(j != maxY - 1 && pathMatrix[2 * i][2 * j + 1].getOpacity() == 1);
            }
        }
        return returnMap;
    }

    public static void printToConsoleNodeMap(String[][] mapToPrint) {
        System.out.println("Output:");
        for (int j = 0; j < mapToPrint[0].length; j++) {
            for (int i = 0; i < mapToPrint.length; i++) {
                if (mapToPrint[i][j] == null || mapToPrint.equals("")) {
                    System.out.print("   ");
                } else {
                    System.out.print(mapToPrint[i][j] + "  ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void printToConsoleDirectionMap(DirectionNode[][] mapToPrint) {
        System.out.println("Output:");
        for (int j = 0; j < mapToPrint[0].length; j++) {
            for (int i = 0; i < mapToPrint.length; i++) {
                DirectionNode directionNode = mapToPrint[i][j];
                System.out.printf("[%s.%s] left : %s%n", i, j, directionNode.getHasLeft());
                System.out.printf("[%s.%s] right: %s%n", i, j, directionNode.getHasRight());
                System.out.printf("[%s.%s] lower: %s%n", i, j, directionNode.getHasLower());
                System.out.printf("[%s.%s] upper: %s%n", i, j, directionNode.getHasUpper());
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void validateNodeMap(String[][] nodeMap) {
        List<String> existings = new ArrayList<>();
        for (int i = 0; i < nodeMap.length; i++) {
            for (int j = 0; j < nodeMap[0].length; j++) {
                String content = nodeMap[i][j];
                if (!"S".equals(content) && !"".equals(content) && content.length() != 2 && (!content.endsWith("D") || !content.endsWith("P"))) {
                    throw new RuntimeException(String.format("Validation failed for node in [%d.%d] with content %s", i, j, content));
                } else {
                    if (existings.contains(content)){
                        throw new RuntimeException(String.format("Validation failed for node in [%d.%d] with content %s - duplicate content", i, j, content));
                    } else if (!"".equals(content)){
                        existings.add(content);
                    }
                }
            }
        }
    }
}