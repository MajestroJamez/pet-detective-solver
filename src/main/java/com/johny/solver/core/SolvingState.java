package com.johny.solver.core;

import java.util.ArrayList;
import java.util.List;

public class SolvingState implements Cloneable {
    private Integer posX;
    private Integer posY;
    private String[][] nodeMap;
    private List<String> basket;
    private Integer remainingMoves;
    private Integer movesDone = 0;
    private String solution;
    private Integer score = 0;


    @Override
    public SolvingState clone() {
        SolvingState clone = null;
        try {
            clone = (SolvingState)super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        clone.setNodeMap(copyMatrix(clone.getNodeMap()));
        clone.setBasket(new ArrayList<>(clone.getBasket()));
        return clone;
    }

    public static String[][] copyMatrix(String[][] matrix) {
        String[][] copy = new String[matrix.length][matrix[0].length];
        for (int idx = 0; idx < matrix.length; ++idx)
            copy[idx] = matrix[idx].clone();
        return copy;
    }

    public Integer getPosX() {
        return posX;
    }

    public void setPosX(Integer posX) {
        this.posX = posX;
    }

    public Integer getPosY() {
        return posY;
    }

    public void setPosY(Integer posY) {
        this.posY = posY;
    }

    public String[][] getNodeMap() {
        return nodeMap;
    }

    public void setNodeMap(String[][] nodeMap) {
        this.nodeMap = nodeMap;
    }

    public List<String> getBasket() {
        return basket;
    }

    public void setBasket(List<String> basket) {
        this.basket = basket;
    }

    public Integer getRemainingMoves() {
        return remainingMoves;
    }

    public void setRemainingMoves(Integer remainingMoves) {
        this.remainingMoves = remainingMoves;
    }

    public Integer getMovesDone() {
        return movesDone;
    }

    public void incrementDoneMoves() {
        movesDone++;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public Integer getScore() {
        return score;
    }

    public void incrementScore() {
        score++;
    }



    public boolean isEnd() {
        for (int i = 0; i < nodeMap.length; i++) {
            for (int j = 0; j < nodeMap[0].length; j++) {
                String content = nodeMap[i][j];
                if (content != null && !"".equals(content)) {
                    return false;
                }
            }
        }
        return true;
    }
}
