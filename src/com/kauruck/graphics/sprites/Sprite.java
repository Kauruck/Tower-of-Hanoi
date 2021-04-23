package com.kauruck.graphics.sprites;

import java.awt.*;

public abstract class Sprite {

    protected int x;
    protected int y;

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public abstract void draw(Graphics2D g);
}
