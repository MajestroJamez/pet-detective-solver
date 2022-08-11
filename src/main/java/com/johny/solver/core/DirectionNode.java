package com.johny.solver.core;

public class DirectionNode {
    private Boolean hasLeft;
    private Boolean hasRight;
    private Boolean hasUpper;
    private Boolean hasLower;

    public Boolean getHasLeft() {
        return hasLeft;
    }

    public void setHasLeft(Boolean hasLeft) {
        this.hasLeft = hasLeft;
    }

    public Boolean getHasRight() {
        return hasRight;
    }

    public void setHasRight(Boolean hasRight) {
        this.hasRight = hasRight;
    }

    public Boolean getHasUpper() {
        return hasUpper;
    }

    public void setHasUpper(Boolean hasUpper) {
        this.hasUpper = hasUpper;
    }

    public Boolean getHasLower() {
        return hasLower;
    }

    public void setHasLower(Boolean hasLower) {
        this.hasLower = hasLower;
    }
}
