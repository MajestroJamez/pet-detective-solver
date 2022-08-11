package com.johny.solver.core;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;

public class MainService extends Service<String> {
    private static final String PICK = "P";
    private static final String DROP = "D";


    private final DirectionNode[][] directionMap;
    private final LinkedList<SolvingState> buffer = new LinkedList<>();
    private final Integer dfsAfter;
    private boolean alreadySorted = false;

    public MainService(String[][] nodeMap, DirectionNode[][] directionMap, Integer maxMoves, Integer dfsAfter) {
        this.directionMap = directionMap;
        SolvingState begin = new SolvingState();
        findAndSetStartingPoint(nodeMap, begin);
        begin.setBasket(new ArrayList<>());
        begin.setRemainingMoves(maxMoves);
        nodeMap[begin.getPosX()][begin.getPosY()] = null;
        begin.setNodeMap(nodeMap);
        this.dfsAfter = dfsAfter;
        addToBuffer(begin);
    }

    private void findAndSetStartingPoint(String[][] nodeMap, SolvingState begin) {
        for (int i = 0; i < nodeMap.length; i++) {
            for (int j = 0; j < nodeMap[0].length; j++) {
                if ("S".equals(nodeMap[i][j])) {
                    begin.setPosX(i);
                    begin.setPosY(j);
                }
            }
        }
    }

    @Override
    protected Task<String> createTask() {
        return new Task<>() {
            @Override
            protected String call() {
                int count = 0;
                int bufferMaxSize = 0;
                while (!buffer.isEmpty()) {
                    SolvingState currentState = buffer.pop();
                    if (currentState.isEnd()) {
                        currentState.setSolution(String.format("%s ,\n %s", currentState.getSolution(), currentState.getRemainingMoves()));
                        return currentState.getSolution();
                    }
                    if (currentState.getRemainingMoves() != 0) {
                        if (directionMap[currentState.getPosX()][currentState.getPosY()].getHasLeft()) {
                            processNode(currentState.getPosX() - 1, currentState.getPosY(), currentState);
                        }
                        if (directionMap[currentState.getPosX()][currentState.getPosY()].getHasRight()) {
                            processNode(currentState.getPosX() + 1, currentState.getPosY(), currentState);
                        }
                        if (directionMap[currentState.getPosX()][currentState.getPosY()].getHasUpper()) {
                            processNode(currentState.getPosX(), currentState.getPosY() - 1, currentState);
                        }
                        if (directionMap[currentState.getPosX()][currentState.getPosY()].getHasLower()) {
                            processNode(currentState.getPosX(), currentState.getPosY() + 1, currentState);
                        }
                    }
                    if (buffer.size() > bufferMaxSize) {
                        bufferMaxSize = buffer.size();
                    }

                    updateMessage("Buffer actual size: " + buffer.size() +
                            ", count: " + count++ +
                            ", actual remaining moves: " + currentState.getRemainingMoves() +
                            ", Buffer max size: " + bufferMaxSize);
                }

                throw new RuntimeException("this has no solution");
            }
        };
    }


    private void processNode(Integer posX, Integer posY, SolvingState previousState) {
        String actualStateNodeString = previousState.getNodeMap()[posX][posY];
        if (null == actualStateNodeString || actualStateNodeString.isEmpty()) {
            addNoActionState(posX, posY, previousState);
        } else if (actualStateNodeString.endsWith(PICK)) {
            addNoActionState(posX, posY, previousState);
            String petLetter = actualStateNodeString.substring(0, 1);
            if (previousState.getBasket().size() < 4) {
                action(posX, posY, PICK, petLetter, previousState);
            }
        } else if (actualStateNodeString.endsWith(DROP)) {
            String petLetter = actualStateNodeString.substring(0, 1);
            if (previousState.getBasket().contains(petLetter)) {
                action(posX, posY, DROP, petLetter, previousState);
            } else {
                addNoActionState(posX, posY, previousState);
            }
        } else throw new RuntimeException("Unexpected state");
    }

    private void addNoActionState(Integer posX, Integer posY, SolvingState previousState) {
        SolvingState newState = previousState.clone();
        prepareNewState(posX, posY, newState);
        addToBuffer(newState);
    }

    private void action(Integer posX, Integer posY, String action, String petLetter, SolvingState previousState) {
        SolvingState newState = previousState.clone();

        prepareNewState(posX, posY, newState);
        if (PICK.equals(action)) {
            newState.getBasket().add(petLetter);
        } else if (DROP.equals(action)) {
            newState.getBasket().remove(petLetter);
        } else throw new RuntimeException("Action " + action + " not defined");
        newState.getNodeMap()[posX][posY] = null;
        newState.incrementScore();
        newState.setSolution(String.format("%s ,\n [%s.%s] - %s", newState.getSolution(), newState.getPosX(), newState.getPosY(), action));
        addToBuffer(newState);
    }

    private void prepareNewState(Integer posX, Integer posY, SolvingState newState) {
        newState.setPosX(posX);
        newState.setPosY(posY);
        newState.setRemainingMoves(newState.getRemainingMoves() - 1);
        newState.incrementDoneMoves();
    }

    private void addToBuffer(SolvingState newState) {
        if (newState.getMovesDone() > dfsAfter) {
            //DFS
            buffer.push(newState);
            //just one sorting before DFS will run
            if (!alreadySorted) {
                buffer.sort(Comparator.comparing(SolvingState::getScore).reversed());
                alreadySorted = true;
            }
        } else {
            //BFS
            buffer.offer(newState);
        }
    }
}
