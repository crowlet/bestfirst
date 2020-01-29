package com.company;

public class Queen {

    private int xPos;
    private int yPos;

    public Queen(Position position) {
        this.xPos = position.getX();
        this.yPos = position.getY();
    }

    public Queen(Queen queen) {
        this.xPos = queen.getxPos();
        this.yPos = queen.getyPos();
    }

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }
}
