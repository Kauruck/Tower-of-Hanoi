package com.kauruck.graphics.sprites;

import java.awt.*;

public class BaseSprite extends Sprite{

    Color renderColor;

    public BaseSprite(){
        float r = (float) Math.random();
        float g = (float) Math.random();
        float b = (float) Math.random();
        renderColor = new Color(r,g,b);
        this.setX(50);
        this.setY(50);
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(renderColor);
        g.fillRect(this.getX(), this.getY(), 50,50);
    }
}
